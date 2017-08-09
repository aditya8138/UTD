# from threading import Thread
import unittest
from concurrent.futures import ThreadPoolExecutor
from sys import argv
from time import sleep
from clock import Clock
from route import Route

class Node:

    __executor__  = ThreadPoolExecutor(max_workers=10)
    __clock__     = Clock()
    __msg_queue__ = None

    def __init__(self, node_id, destination=None, message=None, timestamp=None):
        self.nid = node_id
        self.dest = destination
        self.message = message
        self.timestamp = timestamp
        self.fromfilename = 'from' + self.nid + '.txt'
        self.tofilename = 'to' + self.nid + '.txt'
        self.receivefilename = self.nid + 'received.txt'
                # args=())
        self.__message_processor__ = {'HELLO' : self.__receive_hello__,
                                      'TC'    : self.__receive_tc__,
                                      'DATA'  : self.__receive_data__}
        self.__route__ = Route(node_id)

    @property
    def time(self):
        return self.__clock__.time

    def tick(self):
        self.__clock__.tick()

    def __send_hello__(self):
        # print("send hello")
        receiver = '*'
        sender = self.nid
        msg_type = 'HELLO'
        unidir = 'UNIDIR'
        unidir_neighbor = ' '.join(map(str, self.__route__.unidir))
        bidir = 'BIDIR'
        bidir_neighbor = ' '.join(map(str, self.__route__.bidir))
        mpr = 'MPR'
        mpr_neighbor = ' '.join(map(str, self.__route__.mpr))
        hello = '{} {} {} {} {} {} {} {} {}\n'.format(
                receiver, sender, msg_type, unidir, unidir_neighbor, bidir,
                bidir_neighbor, mpr, mpr_neighbor)
        # print(hello)
        with open(self.fromfilename, 'a') as from_me:
            from_me.write(hello)

    def __send_tc__(self):
        # print("send tc")
        receiver = '*'
        sender = self.nid
        msg_type = 'TC'
        source = self.nid
        ms_seq = self.__route__.ms_seqno
        ms = 'MS'
        ms_neighbor = ' '.join(map(str, self.__route__.ms))
        tc = '{} {} {} {} {} {} {}\n'.format(
                receiver, sender, msg_type, source, ms_seq, ms, ms_neighbor)
        # print(tc)
        with open(self.fromfilename, 'a') as from_me:
            from_me.write(tc)

    def __send_data__(self):
        print("send data")
        pass

    def __check_timeout_(self):
        pass

    def __follow_to_file__(self):
        # print('start following ' + self.tofilename)
        try:
            open(self.tofilename, 'r').close()
        except IOError:
            open(self.tofilename, 'w').close()
        with open(self.tofilename, 'r') as to_me:
            to_me.seek(0,2)
            while True:
                line = to_me.readline()
                if not line:
                    sleep(0.1)
                    continue
                yield line

    def __receive_hello__(self, msg):
        # print("Received " + msg)
        with open(self.receivefilename, 'a') as received:
            received.write(msg)
        msg = msg[:-1]
        print("Received " + msg +'%')
        neighbor = msg[2]
        unidir, rest = msg.split('UNIDIR ')[1].split(' BIDIR ')
        # print(rest+'%')
        bidir, mpr = rest.split(' MPR ')
        to_set = lambda x: set() if x == '' else set(x.split(' '))
        # print(neighbor, to_set(unidir), to_set(bidir),to_set(mpr))
        self.__route__.hello_update(neighbor,
                                    to_set(unidir),
                                    to_set(bidir),
                                    to_set(mpr))

    def __receive_tc__(self, msg):
        with open(self.receivefilename, 'a') as received:
            received.write(msg)
        msg = msg[:-1]
        print("Received " + msg + '%')
        neighbor = msg[2]
        s, ms = msg.split(' TC ')[1].split(' MS ')
        source, ms_seqno = s.split(' ')
        if source == self.nid:
            return
        to_set = lambda x: set() if x == '' else set(x.split(' '))
        # print(neighbor, source, to_set(ms), ms_seqno)
        self.__route__.tc_update(source, to_set(ms), int(ms_seqno))
        with open(self.fromfilename, 'a') as from_me:
            from_me.write('* {} {}\n'.format(self.nid, msg[4:]))

    def __receive_data__(self, msg):
        print("received data")
        pass

    def __msg_processor__(self):
        for msg in self.__msg_queue__:
            self.__message_processor__[msg.split(' ')[2]](msg)

    def start(self):
        self.__msg_queue__ =\
                (self.__executor__.submit(self.__follow_to_file__)).result()
        self.__executor__.submit(self.__msg_processor__)
        while self.time <= 120:
            print(self.__clock__.time)
            # self._executor.submit(check_timeout)

            # Send message at specific timestamp.
            if self.time == self.timestamp:
                self.__executor__.submit(self.__send_data__)
                # self.send_message()

            # Each node sends a hello message every 5 seconds.
            if self.time % 5 == 0:
                self.__executor__.submit(self.__send_hello__)
                # self.send_hello()

            # Every node with a non-empty MS sets creates and floods a TC
            # message every 10 seconds.
            # if self.time % 10 == 0:
                # self.__executor__.submit(self.__send_tc__)
                # self.send_tc()

            sleep(1)
            self.tick()

class TestNode(unittest.TestCase):
    """ This class is for testing the function of Node class. """

    def test_init(self):
        n = Node('0', '4', 'A message from 0 to 4', 40)
        self.assertEqual(n.nid, '0')
        self.assertEqual(n.dest, '4')
        self.assertEqual(n.message, 'A message from 0 to 4')
        self.assertEqual(n.timestamp, 40)
        self.assertEqual(n.__route__.bidir, set())
        self.assertEqual(n.__route__.unidir, set())
        self.assertEqual(n.__route__.mpr, set())
        self.assertEqual(n.__route__.ms, set())
        self.assertEqual(n.__route__.topo_tuple, set())

    def test_receive_hello(self):
        n = Node('0', '4', 'A message from 0 to 4', 40)
        hello1 = '* 4 HELLO UNIDIR  BIDIR  MPR \n'
        n.__receive_hello__(hello1)
        self.assertEqual(n.__route__.bidir, set())
        self.assertEqual(n.__route__.unidir, {'4'})
        self.assertEqual(n.__route__.mpr, set())
        self.assertEqual(n.__route__.ms, set())

        hello2 = '* 4 HELLO UNIDIR 0 BIDIR  MPR \n'
        n.__receive_hello__(hello2)
        self.assertEqual(n.__route__.bidir, {'4'})
        self.assertEqual(n.__route__.unidir, set())
        self.assertEqual(n.__route__.mpr, set())
        self.assertEqual(n.__route__.ms, set())

        hello3 = '* 4 HELLO UNIDIR 0 BIDIR 9 8 MPR 8\n'
        n.__receive_hello__(hello3)
        self.assertEqual(n.__route__.bidir, {'4'})
        self.assertEqual(n.__route__.unidir, set())
        self.assertEqual(n.__route__.mpr, {'4'})
        self.assertEqual(n.__route__.ms, set())

        self.assertEqual(n.__route__.route, {'4':('4',1)})

        hello4 = '* 3 HELLO UNIDIR  BIDIR 0 MPR 0\n'
        n.__receive_hello__(hello4)
        self.assertEqual(n.__route__.bidir, {'3', '4'})
        self.assertEqual(n.__route__.unidir, set())
        self.assertEqual(n.__route__.mpr, {'4'})
        self.assertEqual(n.__route__.ms, {'3'})
        self.assertTrue(n.__route__.mpr_of('3'))

        self.assertEqual(n.__route__.route, {'4':('4',1), '3':('3',1)})


    def test_receive_tc(self):
        n = Node('0', '4', 'A message from 0 to 4', 40)
        hello1 = '* 4 HELLO UNIDIR  BIDIR  MPR \n'
        hello2 = '* 4 HELLO UNIDIR 0 BIDIR  MPR \n'
        hello3 = '* 4 HELLO UNIDIR 0 BIDIR 9 8 MPR 8\n'
        hello4 = '* 3 HELLO UNIDIR  BIDIR 0 MPR 0\n'
        n.__receive_hello__(hello1)
        n.__receive_hello__(hello2)
        n.__receive_hello__(hello3)
        n.__receive_hello__(hello4)

        tc1 = '* 4 TC 4 1 MS 9 8 0\n'
        n.__receive_tc__(tc1)
        self.assertEqual(n.__route__.topo_tuple,
                         {('9','4'), ('8','4'), ('0','4')})
        self.assertEqual(n.__route__.route, 
                        {'4':('4',1), '3':('3',1), '9':('4',2), '8':('4',2)})

def main():
    if len(argv) == 5:
        c = Node(argv[1], argv[2], argv[3], int(argv[4]))
    elif len(argv) == 3:
        c = Node(argv[1], argv[2])
    print(c.nid, c.message, c.timestamp, c.__route__)
    c.start()

if __name__ == '__main__':
    main()
