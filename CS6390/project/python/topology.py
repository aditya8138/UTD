from threading import RLock

class Topology:
    current_topology = dict()
    lock = RLock()

    def __init__(self, _network_conf = None):
        self.topo = dict()
        self.sender = set()
        self.receiver = set()
        if _network_conf is None:
            _network_conf = "net/topology.txt"
        with open(_network_conf, "r") as netconf:
            for line in netconf:
                time, status, node1, node2 = line.split()
                i_time = int(time)
                self.sender.add(node1)
                self.receiver.add(node2)
                if i_time in self.topo:
                    self.topo[i_time] = (self.topo[i_time]
                                        + [(status,node1,node2)])
                else:
                    self.topo[i_time] = [(status,node1,node2)]

    def add_link(self, node1, node2):
        """Add specific link to current_topology."""
        if node1 in self.current_topology:
            self.current_topology[node1].add(node2)
        else:
            self.current_topology[node1] = {node2}

    def del_link(self, node1, node2):
        """Delete specific link to current_topology."""
        try:
            self.current_topology[node1].remove(node2)
            if self.current_topology[node1] == set():
                del sel.current_topology[node1]
        except KeyError:
            print("Invalid link to remove.")

    def update(self, timestamp):
        self.lock.acquire()
        try:
            for status,node1,node2 in self.topo[timestamp]:
                if status == 'UP':
                    self.add_link(node1, node2)
                    pass
                elif status == "DOWN":
                    self.del_link(node1, node2)
                    pass
                else:
                    print("Topology file is broken.")
        except KeyError:
            # print("No change at this time.")
            pass
        finally:
            self.lock.release()

def main():
    t = Topology()
    print("Topology file is parsed as:\n{}\n".format(t.topo))
    for i in t.topo:
        t.update(i)
        print("Topology at timestamp {} is:\n{}\n".format(i,
            t.current_topology))

if __name__ == '__main__':
    main()
