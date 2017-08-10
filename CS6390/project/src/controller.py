from time import sleep
from sys import argv
from threading import Thread
from _thread import interrupt_main
from queue import Queue
from topology import Topology

class Controller:

    def __init__(self, _network_conf = None):
        self.topology = Topology(_network_conf)
        self._update_topology_thread = Thread(target=self._update_topology,
                args=())
        # print("Topology file is parsed as:\n{}\n".format(self.topology.topo))
        # for i in self.topology.topo:
            # self.topology.update(i)
            # print("Topology at timestamp {} is:\n{}\n".format(i,
                # self.topology.get_current_topology()))

    def _update_topology(self):
        for i in range(125):
            self.topology.update(i)
            sleep(1)
        interrupt_main()

    def _broadcast_message(self, dsts, msg):
        """Copy msg to all node in dsts.
        Simply call unicast_message function for all dst in dsts.
        """
        for dst in dsts:
            self._unicast_message(dst, msg)

    def _unicast_message(self, dst, msg):
        """Copy msg to specific dst."""
        with open("to"+dst+".txt", "a") as toX:
            toX.write(msg)

    def _forward_message(self, dsts, msg):
        if msg[0] == '*':
            self._broadcast_message(dsts, msg)
        else:
            self._unicast_message(msg[0], msg)

    def _follow_from_file(self, senders):
        file_handler = dict()
        for node in senders:
            filename = 'from' + node + '.txt'
            try:
                thefile = open(filename,"r")
                thefile.seek(0,2)
            except IOError:
                open(filename, 'w').close()
                thefile = open(filename,"r")
            file_handler[node] = thefile

        while True:
            for node in senders:
                line = file_handler[node].readline()
                if not line:
                    continue
                yield node, line
            try:
                sleep(0.1)
            except TypeError:
                break

    def start(self):
        self._update_topology_thread.start()
        messages = self._follow_from_file(self.topology.sender)
        try:
            for sender, message in messages:
                self._forward_message(self.topology.get_connected_node(sender),
                        message)
        except KeyboardInterrupt:
            print("END.")

def main(): 
    c = Controller() if len(argv) == 1 else Controller(argv[1])
    c.start()

if __name__ == '__main__':
    main()
