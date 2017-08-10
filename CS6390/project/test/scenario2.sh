# Script Filename: multiple_scenario2.sh
# To execute: bash multiple_scenario2.sh <user-id>

# Start Controller first.
# Topology configuration file is set as '../test/topology2.txt'
python3 controller.py "../test/topology2.txt" &

# Start all Nodes.
python3 node.py 0 4 "A message from 0 to 4"   40   &
python3 node.py 1 1 &
python3 node.py 2 2 &
python3 node.py 3 3 &
python3 node.py 4 0 "Message from 4 to 0"  80 & 
python3 node.py 5  5  &
