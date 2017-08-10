# Final Project of CS6390 2017 Summer

This is the final project for CS6390 Advanced Computer Network.

## Compilation

The program was written in Python 3, requiring Python 3.4.1 (which is the
version on the Unix machine on campus) or above.

The program was not compiled to a single executable file (due to some technical
problem I can't solve). Hence, there is no need to compile, simply running the
python script via python interpreter.

## Basic Program Structure

The project requires two components: __Node__ and __Controller__.

- __Node__ was implemented in `node.py`, which include:

    + module `clock.py` as a singleton timestamp generator,

    + module `route.py` as route/neighbor information maintainer.

  In general, there are three thread running in Node.

  The main thread of Node checks timer periodically to determine whether to
  send HELLO/TC/DATA message. (For each message to be sent, a new thread is
  assigned to the task. Therefore, if two messages are to be sent at the same
  timestamp, they are processed concurrently. After sending the message, the
  thread ended and resource released.)

  The other two thread forms a Producer/consumer pattern. One thread
  continuously checks `toX.txt` file to fetch new messages and yields them into
  a generator. And the other thread consumes the generator, processing messages
  whenever they coming in.

  After main thread timer reach timestamp 122, the entire node ended.

- __Controller__ was implemented in `controller.py`, which include:

    + module `topology.py` as network topology maintainer.

  The controller has two threads. The main thread follows all `fromX.txt`
  files, whenever a new line is detected, copy it to destination `toX.txt` file
  based on current network topology. The other thread periodically check timer,
  and maintain network topology based on specified network configuration file.

## Execution

To execute program, run the following commands:

- __Node__

            python3 node.py <node_id> [destination] [message_with_quote] [timestamp] &

    Note that parameters `[destination]`, `[message_with_quote]` and
    `[timestamp]` are optional. If not specified, `destination` would be set to
    the node itself and no message would be sent. Furthermore, if the node should
    send some messages, all these three optional parameters must be specified.
    Otherwise, the node would discard the message and act normally. For
    example, with command (missing destination):

            python3 node.py 0 "message from 0" 50 &

    the node would be act like:

            python3 node.py 0

    (To have node act normally, command examples specified in requirement are
    also accepted though. For example: __`python3 node.py 2 2 &`__.)
