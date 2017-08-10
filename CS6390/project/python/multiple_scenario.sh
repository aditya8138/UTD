ssh hxh160630@dc20 ' python3 controller.py "topology.txt" '&
ssh hxh160630@dc10 ' python3 node.py 0 1 "message from 0" 50 ' &
ssh hxh160630@dc11 ' python3 node.py 1 1 ' &
ssh hxh160630@dc12 ' python3 node.py 2 2 ' &
ssh hxh160630@dc13 ' python3 node.py 3 2 "message from 3" 50 ' &
ssh hxh160630@dc14 ' python3 node.py 4 4 ' & 
ssh hxh160630@dc16 ' python3 node.py 6 6 ' &
ssh hxh160630@dc17 ' python3 node.py 7 7 ' &
ssh hxh160630@dc18 ' python3 node.py 8 8 ' &
ssh hxh160630@dc19 ' python3 node.py 9 2 "message from 9" 25 ' &