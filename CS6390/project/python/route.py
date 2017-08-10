import unittest
from clock import Clock
from copy import deepcopy
from threading import RLock

class Route:

    __lock__ = RLock()

    # If a node does not receive a hello message from one of its neighbors in
    # 15 seconds, it assume the neighbor is dead or has moved away, so it
    # removes it from its list of neighbors.
    __neighbor_timeout_setting__ = 15

    @property
    def neighbor_timeout_stamp(self):
        return Clock().time - self.__neighbor_timeout_setting__

    # If you do not receive a TC message from a node during a period of 45
    # seconds you remove all TC information that you received from that node.
    __topo_timeout_setting__ = 45

    @property
    def topo_timeout_stamp(self):
        return Clock().time - self.__topo_timeout_setting__


    def __init__(self, nid):

        # @PreviousDesign
        # # One hop neighbor, Dict() with:
        # #   - key: neighbor id
        # #   - val: Tuple(List[unidir neighbors],List[bidir neighbors])
        # self.one_hop_neigh = dict()

        # Current node id.
        self.__nid__ = nid

        # Note that, there is no need to store the unidir neighbors list of a
        # one-hop neighbor. Only the bidirectional neighbors list is useful.
        # When generating HELLO message, we need the list of all unidir/bidir
        # neighbor, instead of a dict with key of neighbor id, use two set of
        # neighbor ids for each link state.
        self.__unidir__ = set()
        self.__bidir__  = set()

        # # Two hop neighbor, Set() with all two hop neighbors.
        # # Generated by combining all values in the neighbor_map.
        # self.two_hop = set()

        # A helper dict to store the mapping of two-hop neighbor and
        # corresponding one-hop neighbor with:
        #   - key: one-hop neighbor id
        #   - val: Set(two-hop neighbor ids)
        # Only bidir links neighbor is included.
        self.__neighbor_map__ = dict()
        self.__neighbor_timestamp__ = dict()

        # MPR (Multipoint relays), Set() with all MPRs' id, which is a subset
        # of self.one_hop_neigh, and MPR set corresponding sequence number.
        self.__mpr__ = set()
        self.__mpr_seqno__ = 0

        # MS (MPR Selector Set), Set() with all MS id, and MS set corresponding
        # sequence number.
        self.__ms__ = set()
        self.__ms_seqno__ = 0

        # Topology Table, Dict() with
        #   - key: Tuple(Destination_Addr, Last-Hop)
        #   - val: Tuple(MPR_SeqNo, Received_Timestamp)
        self.__topo__ = dict()

        # Routing Table, Dict() with
        #   - key: Destination_Addr
        #   - val: Tuple(Next-Hop, Distance)
        self.__route__ = dict()

    # Getter method
    @property
    def nid(self):
        return self.__nid__

    @property
    def bidir(self):
        return self.__bidir__

    @property
    def unidir(self):
        return self.__unidir__

    @property
    def neighbor_map(self):
        return deepcopy(self.__neighbor_map__)

    @property
    def neighbor_timestamp(self):
        return self.__neighbor_timestamp__

    @property
    def mpr(self):
        return self.__mpr__

    @property
    def mpr_seqno(self):
        return self.__mpr_seqno__

    @property
    def ms(self):
        return self.__ms__

    @property
    def ms_seqno(self):
        return self.__ms_seqno__

    @property
    def topo(self):
        return deepcopy(self.__topo__)

    @property
    def topo_tuple(self):
        self.__lock__.acquire()
        try:
            return set(self.topo.keys())
        finally:
            self.__lock__.release()

    @property
    def route(self):
        return self.__route__

    @property
    # Two hop neighbor, Set() with all two hop neighbors. Generated by
    # combining all values in the neighbor_map.
    def two_hop(self):
        self.__lock__.acquire()
        try:
            if self.__neighbor_map__ == {}:
                return set()
            return set.union(*list(self.__neighbor_map__.values()))
        finally:
            self.__lock__.release()

    def mpr_of(self, neighbor):
        return neighbor in self.__ms__

    def hello_update(self, neighbor, unidir, bidir, mpr):
        """ Update triggered by HELLO message.
            1. Invoke _update_neighbor to update neighbor table.
            2. Invoke select_mpr to reselect mpr set.
            3. Invode _update_ms to update MS set.
        """
        # print(self.unidir, self.bidir, self.mpr, self.mpr_seqno)
        self._update_neighbor(neighbor, unidir, bidir)
        self._select_mpr()
        self._update_ms(neighbor, mpr)
        self.calc_route_table()

    def _update_neighbor(self, neighbor, unidir, bidir):

        """ Update neighbor information of specific neighbor.
        Args:
            - neighbor (str): The id of the neighbor, which is the originator
              of the HELLO message.
            - unidir (Set[str]): The ids of node which is unidirectional
              neighbor of the HELLO message originator.
            - bidir (Set[str]): The ids of node which is bidirectional
              neighbor of the HELLO message originator.
        Returns:
            Self.
        """
        cur_time = Clock().time

        # If the neighbor is currently a bidirectional neighbor,
        #   1. Refresh the timestamp of its neighbor_map,
        #   2. Regenerated two-hop neighbor set based on new neighbor_map.
        if neighbor in self.__bidir__:
            self.__neighbor_timestamp__[neighbor] = cur_time
            bidir.discard(self.nid)
            self.__neighbor_map__[neighbor] = bidir
            return self

        # If current node is listed in the unidir or bidir, move (or add) the
        # neighbor to bidirectional neighbor set and add bidir to neighbor_map
        # and timestamp.
        if self.__nid__ in bidir | unidir:
            self.__unidir__.discard(neighbor)
            self.__bidir__.add(neighbor)
            bidir.discard(self.nid)
            self.__neighbor_map__[neighbor] = bidir
            self.__neighbor_timestamp__[neighbor] = cur_time
            return self

        # Otherwise, neither the neighbor is currently neither a bidirectional
        # neighbor nor current node was listed in the bidir or unidir (i.e. the
        # neighbor was not aware of current node), add neighbor to
        # unidirectional neighbor (or remain the same) and refresh timestamp.
        self.__unidir__.add(neighbor)
        self.__neighbor_timestamp__[neighbor] = cur_time
        return self

    def _select_mpr(self):
        """ Select MPR set based on current bidir/two-hop neighbor set, using
        basic OLSR greedy strategy:

        >   Select first the neighbor that can reach the largest number of two
        >   hop neighbors, then the next neighbor that can select the most
        >   remaining two-hop neighbors, etc.

        Args:
            Self.
        Returns:
            Self.
        """

        # A helper function, recursively calculate MPR set.
        def __select_mpr__(neighbor_map, two_hop_set):
            if two_hop_set == set():
                return set()
            interset_count = lambda x: len(x & two_hop_set)
            convert = lambda x: (interset_count(x[1]), x[0])
            interset = set(map(convert, neighbor_map.items()))
            _,mpr = max(interset)
            two_hop_set = two_hop_set - neighbor_map[mpr]
            neighbor_map.pop(mpr)
            return {mpr} | __select_mpr__(neighbor_map, two_hop_set)

        two_hop_set = self.two_hop
        neighbor_map = self.neighbor_map
        new_mpr = __select_mpr__(neighbor_map, two_hop_set)
        if new_mpr == self.__mpr__:
            return
        self.__mpr__ = new_mpr
        self.__mpr_seqno__ += 1

    def _update_ms(self, neighbor, mpr):
        """ Update MS set, add neighbor id to MS set if current node is the
            neighbor's MPR, or delete the neighbor in MS set if current node
            was no longer in the neighbor's MPR.
        Args:
            - neighbor (str): The id of the neighbor, which is the originator
              of the HELLO message.
            - mpr (Set(str)): The ids of MPR of the neighbor.
        Returns:
            Self
        """
        if self.nid in mpr:
            if neighbor in self.__ms__:
                return
            self.__ms__.add(neighbor)
            self.__ms_seqno__ += 1
            return
        if neighbor in self.__ms__:
            self.__ms__.discard(neighbor)
            self.__ms_seqno__ += 1
            return

    def tc_update(self, last_hop, ms, ms_seqno):
        """ Update triggered by TC message.
        Args:
            - last_hop (str): Originator of the TC message, last-hop to dst.
            - ms (Set[str]): The MS set of the message originator.
            - ms_seqno (int): Sequence number of the MS set.
        """
        cur_time = Clock().time

        for (dst, _last_hop),(prev_seq, prev_ts) in self.topo.items():
            # If there exist some entry in the topology table whose last-hop
            # address corresponds to the originator of the TC message and the
            # MPR selector sequence number in that entry is greater than the
            # sequence number in the received message, then no further
            # processing of this TC message is done and it is silently
            # discarded.
            if _last_hop == last_hop and prev_seq > ms_seqno:
                return

            # If there exist some entry in the topology table whose last-hop
            # address corresponds to the originator of the TC message and the
            # MPR selector sequence number in that entry is smaller than the
            # sequence number in the received message, then that topology table
            # entry is removed.
            # Will re-add the entries again.
            if _last_hop == last_hop and prev_seq <= ms_seqno:
                self.__topo__.pop((dst,_last_hop))

        # If there exist some entry in the topology table whose destination
        # address corresponds to the MPR selector address and the last-hop
        # address of that entry corresponds to the originator address of the TC
        # message, then the holding time of that entry is refreshed.
        # Otherwise, a new topology entry is recorded in the topology table.
        # Basically, just add all entry in MS set to topology table.
        for dst in ms:
            self.__topo__[(dst, last_hop)] = (ms_seqno, cur_time)

        self.calc_route_table()
        # print(self.topo)

    def __remove_neighbor__(self, neighbors):
        """ Remove neighbor information in the specific set.
        Args:
            - neighbors (Set[str]): neighbor ids to be removed.
        Returns:
            - True:  If some information was deleted.
            - False: If nothing was changed.
        """
        # if neighbors:
        #     print(self.nid, 'deleting neighbors:', neighbors)
        if neighbors == set():
            return False
        for node_id in neighbors:
            self.__neighbor_map__.pop(node_id)
            self.__neighbor_timestamp__.pop(node_id)
            self.__bidir__.discard(node_id)
            self.__unidir__.discard(node_id)
            self.__ms__.discard(node_id)
        return True

    def __remove_topo__(self, last_hops):
        """ Remove neighbor information in the specific set.
        Args:
            - last_hops (Set[str]): last_hop node ids to be removed.
        Returns:
            - True:  If some information was deleted.
            - False: If nothing was changed.
        """
        # if last_hops:
        #     print(self.nid, 'deleting last_hops:', last_hops)
        deleted = False
        for last_hop in last_hops:
            for (dst,_last_hop),(_,_) in self.topo.items():
                if _last_hop == last_hop:
                    self.__topo__.pop((dst,_last_hop))
                    deleted = deleted or True
        return deleted

    def check_timeout(self):
        neighbor_timeout_timestamp = self.neighbor_timeout_stamp
        neighbor_filter = lambda x: x[1] < neighbor_timeout_timestamp
        neighbor_status = self.__remove_neighbor__(set(map(lambda x:x[0],
            filter(neighbor_filter, self.neighbor_timestamp.items()))))
        if neighbor_status:
            self._select_mpr()

        topo_timeout_timestamp = self.topo_timeout_stamp
        topo_filter = lambda x: x[1][1] < topo_timeout_timestamp
        topo_status = self.__remove_topo__(set(map(lambda x:x[0][1],
            filter(topo_filter, self.topo.items()))))

        if neighbor_status or topo_status:
            # print("Route table change at " + str(Clock().time))
            self.calc_route_table()

    def __calc_route_table__(self, topo, bidir):
        """ Internal implementation of calculating routing table.
        Args:
            - topo (Set[(str,str)]: Set of (dst,last_hop) tuple from TC table.
            - bidir (Set(str)): Set of bidirectional neighbor.
        Returns:
            Calculated rout table (Dict(dst:(next_hop, hops))).
        """

        # All the entries of routing table are removed.
        route = dict()

        # The new entries are recorded in the table starting with one hop
        # neighbors (h=1) as destination nodes. For each neighbor entry in the
        # neighbor table, whose link status is not unidirectional, a new route
        # entry is recorded in the routing table where destination and next-hop
        # addresses are both set to address of the neighbor and distance is set
        # to 1.
        for node in bidir:
            route[node] = (node, 1)

        # Then the new route entries for destination nodes h+1 hops away are
        # recorded in the routing table.
        # For each topology entry in topology table, if its destination address
        # does not corresponds to destination address of any route entry in the
        # routing table AND its last-hop address corresponds to destination
        # address of a route entry with distance equal to h, then a new route
        # entry is recorded in the routing table where:
        #   - destination is set to destination address in topology table;
        #   - next-hop is set to next-hop of the route entry whose destination
        #     is equal to above mentioned last-hop address; and
        #   - distance is set to h+1.
        h = 1
        changed = True
        while changed:
            changed = False
            for dst,last_hop in topo:
                if (dst not in route and last_hop in route
                        and route[last_hop][1] == h and dst != self.nid):
                    route[dst] = (route[last_hop][0], h+1)
                    changed = changed or True
            h += 1

        return route

    def calc_route_table(self):
        topo = self.topo_tuple
        bidir = self.bidir

        self.__lock__.acquire()
        try:
            self.__route__ = self.__calc_route_table__(topo, bidir)
        finally:
            self.__lock__.release()

    def get_route(self, dst):
        try:
            return self.route[dst][0]
        except KeyError:
            return None

class TestRoute(unittest.TestCase):
    """ This class is for testing the function of Route class. """

    def test_update_neighbor(self):
        Clock().reset()
        route = Route('a')
        route._update_neighbor('b', {'c', 'd'}, {'e','f'})
        self.assertEqual(route.unidir, {'b'})
        self.assertEqual(route.bidir, set())
        self.assertEqual(route.neighbor_timestamp, dict([('b', 0)]))
        self.assertEqual(route.two_hop, set())

        route._update_neighbor('c', {'d', 'a'}, {'g'})
        self.assertEqual(route.unidir, {'b'})
        self.assertEqual(route.bidir, {'c'})
        self.assertEqual(route.neighbor_timestamp, dict([('b', 0), ('c', 0)]))
        self.assertEqual(route.two_hop, {'g'})

        route._update_neighbor('b', {'c','d'}, {'e','f','a'})
        self.assertEqual(route.unidir, set())
        self.assertEqual(route.bidir, {'c', 'b'})
        self.assertEqual(route.neighbor_timestamp, dict([('b', 0), ('c', 0)]))
        self.assertEqual(route.two_hop, {'e','f', 'g'})
        self.assertEqual(route.neighbor_map,
                dict([('c', {'g'}), ('b', {'e', 'f'})]))

    def test_select_mpr(self):
        Clock().reset()
        route = Route('a')
        route._update_neighbor('b', {'c','d'}, {'e','f'})
        route._select_mpr()
        self.assertEqual((route.mpr, route.mpr_seqno), (set(),0))

        route._update_neighbor('c', {'d','a'}, {'g'})
        route._select_mpr()
        self.assertEqual((route.mpr, route.mpr_seqno), ({'c'},1))

        route._update_neighbor('b', {'c','d'}, {'e','f','a','p'})
        route._select_mpr()
        self.assertEqual((route.mpr, route.mpr_seqno), ({'c','b'},2))

        route._update_neighbor('d', {'d','a'}, {'x'})
        route._select_mpr()
        self.assertEqual((route.mpr, route.mpr_seqno), ({'c','b','d'},3))

        route._update_neighbor('d', {'d','a'}, {'x','g'})
        route._select_mpr()
        self.assertEqual((route.mpr, route.mpr_seqno), ({'b','d'},4))

        route._update_neighbor('e', {'d','a'}, {'f'})
        route._select_mpr()
        self.assertEqual((route.mpr, route.mpr_seqno), ({'b','d'},4))

    def test_hello_update(self):
        Clock().reset()
        route = Route('a')

        route.hello_update('b', {'c','d'}, {'e','f'}, {})
        self.assertEqual((route.mpr, route.mpr_seqno), (set(),0))
        self.assertEqual((route.ms, route.ms_seqno), (set(),0))

        route.hello_update('c', {'d','a'}, {'g'}, {'g'})
        self.assertEqual((route.mpr, route.mpr_seqno), ({'c'},1))
        self.assertEqual((route.ms, route.ms_seqno), (set(),0))

        route.hello_update('b', {'c','d'}, {'e','f','a','p'}, {'a'})
        self.assertEqual((route.mpr, route.mpr_seqno), ({'c','b'},2))
        self.assertEqual((route.ms, route.ms_seqno), ({'b'},1))

        route.hello_update('d', {'d','a'}, {'x'}, {'x'})
        self.assertEqual((route.mpr, route.mpr_seqno), ({'c','b','d'},3))
        self.assertEqual((route.ms, route.ms_seqno), ({'b'},1))

        route.hello_update('d', {'d'}, {'a','x','g'}, {'g','a'})
        self.assertEqual((route.mpr, route.mpr_seqno), ({'b','d'},4))
        self.assertEqual((route.ms, route.ms_seqno), ({'b','d'},2))

        route.hello_update('e', {'d','a'}, {'f'}, {'f'})
        self.assertEqual((route.mpr, route.mpr_seqno), ({'b','d'},4))
        self.assertEqual((route.ms, route.ms_seqno), ({'b','d'},2))

        route.hello_update('d', {'d'}, {'a','x','g'}, {'g','x'})
        self.assertEqual((route.mpr, route.mpr_seqno), ({'b','d'},4))
        self.assertEqual((route.ms, route.ms_seqno), ({'b'},3))

    def test_tc_update(self):
        Clock().reset()
        route = Route('a')

        route.hello_update('b', {'c','d'}, {'e','f'}, {})
        route.hello_update('c', {'d','a'}, {'g'}, {'g'})
        route.hello_update('b', {'c','d'}, {'e','f','a','p'}, {'a'})
        route.hello_update('d', {'d','a'}, {'x'}, {'x'})
        route.hello_update('d', {'d'}, {'a','x','g'}, {'g','a'})
        route.hello_update('e', {'d','a'}, {'f'}, {'f'})
        route.hello_update('d', {'d'}, {'a','x','g'}, {'g','x'})
        route.hello_update('f', {'a'}, {'e'}, {})

        route.tc_update('f', {'e'}, 1)
        self.assertEqual(route.topo, {('e','f'):(1,0)})

        route.tc_update('f', {'e', 'b'}, 3)
        self.assertEqual(route.topo, {('e','f'):(3,0), ('b','f'):(3,0)})

        route.tc_update('f', {'e','d'}, 2)
        self.assertEqual(route.topo, {('e','f'):(3,0), ('b','f'):(3,0)})
        self.assertEqual(route.topo_tuple, {('e','f'),('b','f')})

        route.tc_update('f', {'b'}, 5)
        self.assertEqual(route.topo, {('b','f'):(5,0)})
        self.assertEqual(route.topo_tuple, {('b','f')})

    def test_check_timeout(self):
        Clock().reset() # time = 0
        route = Route('a')

        route.hello_update('b', {'c','d'}, {'e','f'}, {})
        Clock().tick() # time = 1
        route.hello_update('c', {'d','a'}, {'g'}, {'g'})
        Clock().tick() # time = 2
        route.hello_update('b', {'c','d'}, {'e','f','a','p'}, {'a'})
        Clock().tick() # time = 3
        route.hello_update('d', {'d','a'}, {'x'}, {'x'})
        Clock().tick() # time = 4
        route.hello_update('d', {'d'}, {'a','x','g'}, {'g','a'})
        Clock().tick() # time = 5
        route.hello_update('e', {'d','a'}, {'f'}, {'f'})
        Clock().tick() # time = 6
        route.hello_update('d', {'d'}, {'a','x','g'}, {'g','x'})
        Clock().tick() # time = 7
        route.hello_update('f', {'a'}, {'e'}, {})
        Clock().tick() # time = 8
        self.assertEqual(route.neighbor_map, {'c':{'g'}, 'b':{'e','f','p'},
            'd':{'x','g'}, 'e':{'f'}, 'f':{'e'}})
        self.assertEqual(route.neighbor_timestamp, {'c':1, 'b':2, 'd':6, 'e':5,
            'f':7})

        self.assertEqual((route.mpr, route.mpr_seqno), ({'b','d'},4))
        self.assertEqual((route.ms, route.ms_seqno), ({'b'},3))

        Clock().tick() # time = 9
        route.tc_update('f', {'e'}, 1)
        Clock().tick() # time = 10
        route.tc_update('f', {'e', 'b'}, 3)
        Clock().tick() # time = 11
        route.tc_update('f', {'e','d'}, 2)
        Clock().tick() # time = 12
        route.tc_update('f', {'b'}, 5)
        Clock().tick() # time = 13
        route.tc_update('f', {'b', 'q'}, 6)

        self.assertEqual(route.topo, {('b','f'):(6,13), ('q','f'):(6,13)})

        route.check_timeout()
        self.assertEqual(route.neighbor_map, {'c':{'g'}, 'b':{'e','f','p'},
            'd':{'x','g'}, 'e':{'f'}, 'f':{'e'}})
        self.assertEqual(route.neighbor_timestamp, {'c':1, 'b':2, 'd':6, 'e':5,
            'f':7})

        # Ticktock time to 12
        Clock().tick().tick().tick().tick().tick().tick()
        route.check_timeout()
        self.assertEqual(route.neighbor_map, {'d':{'x','g'}, 'e':{'f'},
            'f':{'e'}})
        self.assertEqual(route.neighbor_timestamp, {'d':6, 'e':5, 'f':7})

        Clock().tick().tick() # time = 21
        route.check_timeout()
        self.assertEqual(route.neighbor_map, {'f':{'e'}, 'd':{'x','g'}})
        self.assertEqual(route.neighbor_timestamp, {'f':7, 'd':6})

        Clock().tick() # time = 22
        route.check_timeout()
        self.assertEqual(route.neighbor_map, {'f':{'e'}})
        self.assertEqual(route.neighbor_timestamp, {'f':7})

        route.tc_update('e', {'f'}, 1)

        self.assertEqual(route.topo, {('b','f'):(6,13), ('f', 'e'):(1,22), ('q','f'):(6,13)})

        # Ticktock time to 60
        for _ in range(38):
            Clock().tick()

        route.check_timeout()
        self.assertEqual(route.topo, {('f', 'e'):(1,22)})

    def test_calc_route_table(self):
        Clock().reset()
        route = Route('a')
        route.hello_update('b', {'d'}, {'c','a','f'}, {'c'})
        route.hello_update('c', {'f'}, {'b','d','e'}, {'b','e'})
        route.hello_update('d', set(), {'c','a','e'}, {'c','e'})
        self.assertEqual(route.unidir, {'c'})
        self.assertEqual(route.bidir, {'b','d'})
        self.assertEqual((route.mpr, route.mpr_seqno), ({'b','d'},2))
        self.assertEqual((route.ms, route.ms_seqno), (set(),0))

        route.tc_update('b',{'c','f','a'},1)
        self.assertEqual(route.route, 
                        {'b':('b',1), 'd':('d',1),
                         'c':('b',2), 'f':('b',2)})

        route.tc_update('c',{'b','e'},1)
        self.assertEqual(route.route, 
                        {'b':('b',1), 'd':('d',1),
                         'c':('b',2), 'f':('b',2),
                         'e':('b',3)})

        route.tc_update('e',{'c','d','f'},1)
        self.assertEqual(route.route, 
                        {'b':('b',1), 'd':('d',1),
                         'c':('b',2), 'f':('b',2),
                         'e':('b',3)})

        route.tc_update('d',{'e','a'},1)
        self.assertEqual(route.route,
                        {'b':('b',1), 'd':('d',1),
                         'c':('b',2), 'e':('d',2),
                         'f':('b',2)})

        self.assertEqual(route.topo_tuple, 
                        {('f','b'), ('c','b'), ('b','c'), ('e','c'),
                         ('c','e'), ('d','e'), ('f','e'), ('e','d'),
                         ('a','d'), ('a', 'b')})

    def test_get_route(self):
        Clock().reset()
        route = Route('a')
        route.hello_update('b', {'d'}, {'c','a','f'}, {'c'})
        route.hello_update('c', {'f'}, {'b','d','e'}, {'b','e'})
        route.hello_update('d', set(), {'c','a','e'}, {'c','e'})
        route.tc_update('b',{'c','f','a'},1)
        route.tc_update('c',{'b','e'},1)
        route.tc_update('e',{'c','d','f'},1)
        route.tc_update('d',{'e','a'},1)
        self.assertEqual(route.get_route('b'), 'b')
        self.assertEqual(route.get_route('c'), 'b')
        self.assertEqual(route.get_route('d'), 'd')
        self.assertEqual(route.get_route('e'), 'd')
        self.assertEqual(route.get_route('f'), 'b')
        self.assertEqual(route.get_route('x'), None)


if __name__ == '__main__':
    unittest.main()
