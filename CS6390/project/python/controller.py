import time
import sys

# def follow(filename):
    # try:
        # thefile = open(filename,"r")
    # except IOError:
        # open(filename, 'w').close()
        # thefile = open(filename,"r")
    # while True:
        # line = thefile.readline()
        # if not line:
            # time.sleep(0.1)
            # continue
        # yield line

def parse_topology_file(_network_conf=None):
    topo = dict()
    send_nodes = set()
    receive_nodes = set()
    if _network_conf is None:
        _network_conf = "topology.txt"
    netconf = open(_network_conf, "r")
    for line in netconf:
        time, status, node1, node2 = line.split()
        send_nodes.add(node1)
        receive_nodes.add(node2)
        if int(time) in topo:
            topo[int(time)] = topo[int(time)] + [(status,node1,node2)]
        else:
            topo[int(time)] = [(status,node1,node2)]
    return topo, send_nodes, receive_nodes

def follow_from_file(send_nodes):
    file_handler = dict()
    for node in send_nodes:
        filename = 'from' + node + '.txt'
        try:
            thefile = open(filename,"r")
            thefile.seek(0,2)
        except IOError:
            open(filename, 'w').close()
            thefile = open(filename,"r")
        file_handler[node] = thefile

    while True:
        for node in send_nodes:
            line = file_handler[node].readline()
            if not line:
                continue
            yield node, line
        time.sleep(0.1)


def follow_to_file(a):
    pass

def update_topology(network_changes, current_topology=None):

    def add_link(cur_topo, node1, node2):
        """Add specific link to current_topology."""
        if node1 in cur_topo:
            cur_topo[node1].add(node2)
        else:
            cur_topo[node1] = {node2}

    def del_link(cur_topo, node1, node2):
        """Delete specific link to current_topology."""
        try:
            cur_topo[node1].remove(node2)
            if cur_topo[node1] == set():
                del cur_topo[node1]
        except KeyError:
            print("Invalid link to remove.")

    if current_topology is None:
        current_topology = dict()
    for status,node1,node2 in network_changes:
        # print(status,node1,node2)
        if status == 'UP':
            add_link(current_topology, node1, node2)
            pass
        elif status == "DOWN":
            del_link(current_topology, node1, node2)
            pass
        else:
            print("no")
    return current_topology

def broadcast_message(dsts, msg):
    pass

def unicast_message(dst, msg):
    pass

def forward_message(dsts, msg):
    if msg[0] == '*':
        broadcast_message(dsts, msg)
    else:
        unicast_message(msg[0], msg)
    pass

def main():
    topo,send_nodes,receive_nodes = parse_topology_file()
    # print(topo)
    # print(send_nodes)
    # print(receive_nodes)
    # topo = parse_topology_file("topology1.txt")

    # current_topology = update_topology(topo["0"])
    # print(current_topology)
    # print(update_topology(topo["80"], current_topology))

    current_topology = None
    messages = follow_from_file(send_nodes)
    # to_file_generators = follow_to_file(receive_nodes)
    for i in range(120): # 120 seconds execution.
        # First update network topology if changes happened.
        try:
            current_topology = update_topology(topo[i], current_topology)
            # print(current_topology)
        except KeyError:
            # print("No change in network at time", i)
            continue
        for sender,message in messages:
            forward_message(current_topology[sender], message)


    print("END.")



    # for i in range(120):
        # print(i)


if __name__ == '__main__':
    main()

