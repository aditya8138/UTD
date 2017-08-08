# from threading import Thread
from concurrent.futures import ThreadPoolExecutor
from enum import Enum
from sys import argv
from time import sleep
from clock import Clock


class LinkState(Enum):
    BIDIRECTIONAL = 1
    UNIDIRECTIONAL = 2


class Node:

    _executor = ThreadPoolExecutor(max_workers=6)

    def __init__(self, node_id, message, timestamp):
        self.nid = node_id
        self.message = message
        self.timestamp = timestamp
        # self.msg_generator_thread = Thread(target = self._msg_generator,
                # args=())
        self.msg_queue = (self._executor.submit(self._follow_to_file)).result()
        self._message_processor = {'HELLO' : self._receive_hello,
                                   'TC'    : self._receive_tc,
                                   'DATA'  : self._receive_data}

    def _send_hello(self):
        pass

    def _send_tc(self):
        pass

    def _send_data(self):
        pass

    def check_timeout(self):
        pass

    def _follow_to_file(self):
        tofilename = 'to' + self.nid + '.txt'
        print(tofilename)
        try:
            open(tofilename, 'r').close()
        except IOError:
            open(tofilename, 'w').close()
        with open(tofilename, 'r') as to_me:
            while True:
                line = to_me.readline()
                if not line:
                    continue
                yield line
                sleep(0.1)

    def _receive_hello(self, msg):
        print("received hello")
        pass

    def _receive_tc(self, msg):
        print("received tc")
        pass

    def _receive_data(self, msg):
        print("received data")
        pass

    def _msg_processor(self):
        for msg in self.msg_queue:
            self._message_processor[msg.split(' ')[2]](msg)

    def _timer(self):
        for i in range(120):
            # self._executor.submit(check_timeout)
            self.check_timeout()

            # Send message at specific timestamp.
            if i == self.timestamp:
                self._executor.submit(self.send_message)
                # self.send_message()

            # Each node sends a hello message every 5 seconds.
            if i % 5 == 0:
                self._executor.submit(self.send_hello)
                # self.send_hello()

            # Every node with a non-empty MS sets creates and floods a TC
            # message every 10 seconds.
            if i % 10 == 0:
                self._executor.submit(self.send_tc)
                # self.send_tc()

            sleep(1)

def main():
    c = Node(argv[1], argv[2], int(argv[3]))
    print(c.nid, c.message, c.timestamp)
    c._msg_processor()

if __name__ == '__main__':
    main()
