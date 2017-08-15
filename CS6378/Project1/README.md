# Project 1: Ricart-Agrawala Mutex


To run a node, execute

`java Main 1 10`

The first parameter is the `id` of the node, the second parameter is the total number of nodes.

The node would write to the home directory with file name `record.txt`.

## Status

The first version of the program doesn't work properly. The following part was
finished:

- Connections establishment between each pair node.
- The first node has the right to enter critical zone at the very beginning if no requests by other nodes is made.
- Logical clock class and coresponding increment method.
- Message parsing and corresponding socket channel's state changing.
- Messages can be delivered between nodes and parsed.
- Request message generation and compareTo interface used in forming the min priority queue.
- RequestQueue maintained by node itself, add request operation, check id of the first request in queue, and extract the first request from the queue.
- Critical zone execution function.

~~The following is the issues I'm aware and not able to solve.~~

- ~~The proper way to inform the specific socket thread to send Req or Ack to the specific node was not implemented.~~
- ~~The status changes of the socket channel is not working properly.~~
- ~~Some while-wait() function causing infinite loop since the shared object is not changed.~~
- ~~Once the other node acquire all the ACKs, it did not enter the critical zone as expected.~~

Currently, all functions run properly.
