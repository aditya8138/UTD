# Script Filename: scenario.sh
# To execute: bash scenario.sh

# Start Controller first.
# Topology configuration file is set as '../test/topology1.txt'
python3 controller.py "../test/topology1.txt" &

# Start all Nodes.
python3 node.py 0 1 "message from 0" 50 &
python3 node.py 1 1 &
python3 node.py 2 2 &
python3 node.py 3 2 "message from 3" 90 &
python3 node.py 4 4 & 
python3 node.py 6 6 &
python3 node.py 7 7 &
python3 node.py 8 8 &
python3 node.py 9 2 "message from 9" 25 &