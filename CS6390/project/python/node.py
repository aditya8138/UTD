import threading
from enum import Enum
from sys import argv


class LinkState(Enum):
    BIDIRECTIONAL = 1
    UNIDIRECTIONAL = 2

class Node:

    def __init__(self, node_id, message, timestamp):
        self.nid = node_id
        self.message = message
        self.timestamp = timestamp
        self.one_hop_neigh = dict()
        self.two_hop_neigh = set()

    def send_hello(self):
        pass

    def send_tc(self):
        pass

    def send_message(self):
        pass

    def message_generator(self):
        for i in range(120):
            # Each node sends a hello message every 5 seconds.
            if i % 5 == 0:
                self.send_hello()

            # Every node with a non-empty MS sets creates and floods a TC
            # message every 10 seconds.
            if i % 10 == 0:
                self.send_tc()

            # Send message at specific timestamp.
            if i == self.timestamp:
                self.send_message()

def main():
    c = Node(argv[1], argv[2], int(argv[3]))
    print(c.nid, c.message, c.timestamp)

if __name__ == '__main__':
    main()
