# Script Filename: multiple_scenario1.sh
# To execute: bash multiple_scenario1.sh <user-id>

# Start Controller first.
# Topology configuration file is set as 'topology.txt'
ssh $1@dc20 ' python3 controller.py "topology1.txt" ' &

# Start all Nodes.
ssh $1@dc10 ' python3 node.py 0 1 "message from 0" 50 ' &
ssh $1@dc11 ' python3 node.py 1 1 ' &
ssh $1@dc12 ' python3 node.py 2 2 ' &
ssh $1@dc13 ' python3 node.py 3 2 "message from 3" 90 ' &
ssh $1@dc14 ' python3 node.py 4 4 ' & 
ssh $1@dc16 ' python3 node.py 6 6 ' &
ssh $1@dc17 ' python3 node.py 7 7 ' &
ssh $1@dc18 ' python3 node.py 8 8 ' &
ssh $1@dc19 ' python3 node.py 9 2 "message from 9" 25 ' &
