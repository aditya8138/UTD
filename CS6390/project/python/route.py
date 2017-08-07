
class Route:

    def __init__(self):

        # @PreviousDesign
        # # One hop neighbor, Dict() with:
        # #   - key: neighbor id
        # #   - val: Tuple(List[unidir neighbors],List[bidir neighbors])
        # self.one_hop_neigh = dict()

        # Note that, there is no need to store the unidir neighbors list of a
        # one-hop neighbor. Only the bidirectional neighbors list is useful.
        # When generating HELLO message, we need the list of all unidir/bidir
        # neighbor, instead of a dict with key of neighbor id, use two set of
        # neighbor ids for each link state.
        self.unidir = set()
        self.bidir  = set()

        # Two hop neighbor, Set() with all two hop neighbors.
        self.two_hop = set()

        # A helper dict to store the mapping of two-hop neighbor and
        # corresponding one-hop neighbor with:
        #   - key: one-hop neighbor id
        #   - val: Set(two-hop neighbor ids)
        # Only bidir links neighbor is included.
        self.neighbor_map = dict()

        # MPR (Multipoint relays), Set() with all MPRs' id, which is a subset
        # of self.one_hop_neigh.
        self.mpr = set()

        # MS (MPR Selector Set), Set() with all MS id.
        self.ms = set()

        # Topology Table, Dict() with
        #   - key: Tuple(Destination_Addr, Last-Hop)
        #   - val: Tuple(MPR_SeqNo, Received_Timestamp)
        self.topo = dict()

        # Routing Table, Dict() with
        #   - key: Destination_Addr
        #   - val: Tuple(Next-Hop, Distance)
        self.route = dict()

    def update_neighbor(self, neighbor, unidir, bidir):
        
        pass
