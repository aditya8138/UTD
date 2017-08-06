
class Route:

    def __init__(self):
        ''' One hop neighbor is stored as:
            Dict() with
            - key: neighbor id
            - val: Tuple(List[unidir neighbors],List[bidir neighbors])
        '''
        self.one_hop_neigh = dict()

        ''' Two hop neighbor is stored as:
            Set() with all two hop neighbors.
        '''
        self.two_hop_neigh = set()

        ''' MPR (Multipoint relays) is stored as:
            Set() with all MPRs' id, which is a subset of self.one_hop_neigh.
        '''
        self.mpr = set()

        ''' MS (MPR Selector Set) is stored as:
            Set() with all MS id.
        '''
        self.ms = set()

        ''' Topology Table is stored as:
            Dict() with
            - key: Tuple(Destination_Addr, Last-Hop)
            - val: Tuple(MPR_SeqNo, Received_Timestamp)
        '''
        self.topo = dict()

        ''' Routing Table is stored as:
            Dict() with
            - key: Destination_Addr
            - val: Tuple(Next-Hop, Distance)
        '''
        self.route = dict()

    def update_neighbor(self, neighbor, unidir, bidir):
        pass
