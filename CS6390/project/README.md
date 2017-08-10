# Final Project of CS6390 2017 Summer

This is the final project for CS6390 Advanced Computer Network.

## Deliverable Artifact

    .
    ├── ID:Hanlin.He(hxh160630)
    ├── README.md
    ├── src
    │   ├── clock.py
    │   ├── controller.py
    │   ├── node.py
    │   ├── route.py
    │   └── topology.py
    └── test
        ├── multiple_scenario.sh    # Correspod to Scenario 1 in ProjectScenario.pdf
        ├── multiple_scenario1.sh   # Correspod to Scenario 2 in ProjectScenario.pdf
        ├── multiple_scenario2.sh   # Correspod to scenario in SampleScenario.txt
        ├── remove_old_file.sh
        ├── scenario.sh             # Correspod to Scenario 1 in ProjectScenario.pdf
        ├── scenario1.sh            # Correspod to Scenario 2 in ProjectScenario.pdf
        ├── scenario2.sh            # Correspod to scenario in SampleScenario.txt
        ├── topology.txt            # Topology used by scenario.sh  & multiple_scenario.sh
        ├── topology1.txt           # Topology used by scenario1.sh & multiple_scenario1.sh
        └── topology2.txt           # Topology used by scenario2.sh & multiple_scenario2.sh

    2 directories, 17 files

## Test Environment

The program was tested on campus Unix virtual machine `dc10`, `dc11`, up to
`dc20`.

Controller was executed on virtual machine `dc20`, and Node with id _i_ was
executed on virtual machine `dc{10+i}`. Detail test script see section
[Multi-machine Execution](#multi-machine-execution). Detail test result see
section [Execution Result](#execution-result).

For single module, `node.py` module and `route.py` module were unit test on
local machine and campus Unix virtual machine.

```bash
> python3 -m unittest node.TestNode
.....
----------------------------------------------------------------------
Ran 5 tests in 0.127s

OK
> python3 -m unittest route.TestRoute
.......
----------------------------------------------------------------------
Ran 7 tests in 0.011s

OK
```

## Compilation

The program was written in Python 3, requiring Python 3.4.1 (which is the
version on the Unix machine on campus) or above.

The program was not compiled to a single executable file (due to some technical
problem I can't solve). Hence, there is no need to compile, simply running the
python script via python interpreter. For detailed execution instruction, see
section [Execution Instruction](#execution-instruction).

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

## Execution Instruction

### Program Parameter

To execute program, run the following commands:

- __Node__

    ```bash
    python3 node.py <node_id> [destination] [message_with_quote] [timestamp] &
    ```

    Note that parameters `[destination]`, `[message_with_quote]` and
    `[timestamp]` are optional. If not specified, `destination` would be set to
    the node itself and no message would be sent. Furthermore, if the node should
    send some messages, all these three optional parameters must be specified.
    Otherwise, the node would discard the message and act normally. For
    example, with command (missing destination):

    ```bash
    python3 node.py 0 "message from 0" 50 &
    ```

    the node would be act like:

    ```bash
    python3 node.py 0
    ```

    To have node act normally, command examples specified in requirement are
    also accepted though.

    For example: __`python3 node.py 2 2 &`__.

- __Controller__

    ```bash
    python3 controller.py [topology_config] &
    ```

    Note that parameter `[topology_config]` is optional, if not specified, the
    default value `topology.txt` would be used.

### Bash Execution Example

An example script is as follows:

```bash
# Script Filename: scenario.sh
# To execute: bash scenario.sh

# Start all Nodes.
# Start Controller first.
# Topology configuration file is set as '../test/topology.txt'
python3 controller.py "../test/topology.txt" &

# Start all Nodes.
python3 node.py 0 1 "message from 0" 50 &
python3 node.py 1 1 &
python3 node.py 2 2 &
python3 node.py 3 2 "message from 3" 50 &
python3 node.py 4 4 & 
python3 node.py 6 6 &
python3 node.py 7 7 &
python3 node.py 8 8 &
python3 node.py 9 2 "message from 9" 25 &
```

Note that __the controller was started before nodes__, while script example in
the requirement controller was started after nodes. This modification was
adapted for debug efficiency: each time a Controller/Node started, they would
seek to the end of `fromX.txt`/`toX.txt`, discard all existing contents and
process any new line thereafter. Therefore, if controller was started after
nodes, some messages might be lost if they were sent before controller started.

## Multi-machine Execution

To deal with the limit of 100 threads per machine on campus Unix. A
multi-machine script is used.
For each node, the script connect to another virtual server and run the script.
An example script is as follows:

```bash
# Script Filename: multiple_scenario.sh
# To execute: bash multiple_scenario.sh <user-id>

# Start Controller first.
# Topology configuration file is set as 'topology.txt'

# Start all Nodes.
ssh $1@dc20 ' python3 controller.py "topology.txt" '&
ssh $1@dc10 ' python3 node.py 0 1 "message from 0" 50 ' &
ssh $1@dc11 ' python3 node.py 1 1 ' &
ssh $1@dc12 ' python3 node.py 2 2 ' &
ssh $1@dc13 ' python3 node.py 3 2 "message from 3" 50 ' &
ssh $1@dc14 ' python3 node.py 4 4 ' & 
ssh $1@dc16 ' python3 node.py 6 6 ' &
ssh $1@dc17 ' python3 node.py 7 7 ' &
ssh $1@dc18 ' python3 node.py 8 8 ' &
ssh $1@dc19 ' python3 node.py 9 2 "message from 9" 25 ' &
```

To execute: `bash multiple_scenario.sh <user-id>`, where `<user-id>` is
mandatory to remote login.

Note that, there is no change directory (`cd`) command in the script. Before
execution, all files in `src` and `test` directory should be copy to home
directory (`~`).

## Execution Result

Only `DATA` message illustrating the actual routing choice by each node is
recorded here.

- Scenario 1 in `ProjectScenario.pdf`:

    ```bash
    > cat from* | grep DATA
    3 0 DATA 0 1 message from 0
    6 3 DATA 3 2 message from 3
    1 3 DATA 0 1 message from 0
    8 6 DATA 3 2 message from 3
    2 8 DATA 3 2 message from 3
    2 9 DATA 9 2 message from 9
    ```

    We can see that:

    + Message from `0` to `1` took path `0 => 3 => 1`.

    + Message from `3` to `2` took path `3 => 6 => 8 => 2`.

    + Message from `9` to `2` took path `9 => 2`.

- Scenario 2 in `ProjectScenario.pdf`:

    ```bash
    > cat from* | grep DATA
    3 0 DATA 0 1 message from 0
    1 3 DATA 0 1 message from 0
    6 3 DATA 3 2 message from 3
    7 6 DATA 3 2 message from 3
    9 7 DATA 3 2 message from 3
    2 9 DATA 9 2 message from 9
    2 9 DATA 3 2 message from 3
    ```

    We can see that:

    + Message from `0` to `1` took path `0 => 3 => 1`.

    + Message from `3` to `2` took path `3 => 6 => 7 => 9 => 2`, which is
      differet from Scenario 1, since in this network, links between `1 & 8`
      and `6 & 8` were `DOWN`.

    + Message from `9` to `2` took path `9 => 2`.

- Scenario in `SampleScenario.txt`:

    ```bash
    > cat from* |grep DATA
    5 0 DATA 0 4 A message from 0 to 4
    0 1 DATA 4 0 Message from 4 to 0
    1 2 DATA 4 0 Message from 4 to 0
    2 3 DATA 4 0 Message from 4 to 0
    3 4 DATA 4 0 Message from 4 to 0
    4 5 DATA 0 4 A message from 0 to 4
    ```

    We can see that:

    + Message from `4` to `0` took path `4 => 5 => 0`.

    + Message from `0` to `4` took path `0 => 1 => 2 => 3 => 4`, since in this
      network, links between `4 & 5` was `DOWN`.
