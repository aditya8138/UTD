ssh hxh160630@dc20 ' python3 controller.py "topology2.txt" ' &
ssh hxh160630@dc10 ' python3 node.py 0 4 "A message from 0 to 4"   40   ' &
ssh hxh160630@dc11 ' python3 node.py 1 1 ' &
ssh hxh160630@dc12 ' python3 node.py 2 2 ' &
ssh hxh160630@dc13 ' python3 node.py 3 3 ' &
ssh hxh160630@dc14 ' python3 node.py 4 0 "Message from 4 to 0"  80 ' & 
ssh hxh160630@dc15 ' python3 node.py 5  5  ' &
