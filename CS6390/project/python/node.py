# from threading import Thread
import unittest
from concurrent.futures import ThreadPoolExecutor
from sys import argv, exit
from time import sleep
from re import sub, search
from clock import Clock
from route import Route

class Node:

    __executor__  = ThreadPoolExecutor(max_workers=10)
    __clock__     = Clock()
    __msg_queue__ = None
    __terminate__ = False
    __msg_cache__ = dict()

    def __init__(self, node_id, dst=None, msg=None, timestamp=None):
        self.nid = node_id
        self.dst = dst
        self.msg = msg
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

    __hello__ = '* {} HELLO UNIDIR {} BIDIR {} MPR {}\n'

    def __send_hello__(self):
        # print("send hello")
        sender  = self.nid
        unidir  = ' '.join(map(str, self.__route__.unidir))
        bidir   = ' '.join(map(str, self.__route__.bidir))
        mpr     = ' '.join(map(str, self.__route__.mpr))
        hello   = self.__hello__.format(sender, unidir, bidir, mpr)
        # print(hello)
        with open(self.fromfilename, 'a') as from_me:
            from_me.write(hello)

    # __tc__ = '* {} TC {} {} MS {} TTL {}\n'
    __tc__ = '* {} TC {} {} MS {}\n'

    def __send_tc__(self):
        # print("send tc")
        sender  = self.nid
        source  = self.nid
        seqno   = self.__route__.ms_seqno
        ms      = ' '.join(map(str, self.__route__.ms))
        # Previous Design with TTL in TC message.
        # ttl     = '10'
        # tc = self.__tc__.format(sender, source, seqno, ms, ttl)
        tc = self.__tc__.format(sender, source, seqno, ms)
        # print(tc)
        with open(self.fromfilename, 'a') as from_me:
            from_me.write(tc)

    __data__ = '{} {} DATA {} {} {}\n'

    def __send_data__(self):
        print("send data")
        next_hop = self.__route__.get_route(self.dst)
        sender = self.nid
        originator = self.nid
        dst = self.dst
        msg = self.msg
        data = self.__data__.format(next_hop, sender, originator, msg)
        print(data)
        with open(self.fromfilename, 'a') as from_me:
            from_me.write(data)

    def __check_timeout__(self):
        self.__route__.check_timeout()

    def __follow_to_file__(self):
        # print('start following ' + self.tofilename)
        try:
            open(self.tofilename, 'r').close()
        except IOError:
            open(self.tofilename, 'w').close()
        with open(self.tofilename, 'r') as to_me:
            to_me.seek(0,2)
            while not self.__terminate__:
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
        print(self.nid + " received " + msg +'%')
        neighbor = msg[2]
        unidir, rest = msg.split('UNIDIR ')[1].split(' BIDIR ')
        # print(unidir)
        # print(rest+'%')
        bidir, mpr = rest.split(' MPR ')
        to_set = lambda x: set() if x == '' else set(x.split(' '))
        # print(neighbor, to_set(unidir), to_set(bidir), to_set(mpr))
        self.__route__.hello_update(neighbor,
                                    to_set(unidir),
                                    to_set(bidir),
                                    to_set(mpr))

    def __receive_tc__(self, msg):
        with open(self.receivefilename, 'a') as received:
            received.write(msg)
        msg = msg[:-1]
        print(self.nid + " received " + msg + '%')
        neighbor = msg[2]
        s, ms = msg.split(' TC ')[1].split(' MS ')
        source, seqno = s.split(' ')
        # ms, ttl = rest.split(' TTL ')
        # ttl = int(ttl)

        # If the TC message was originated by the node itself, discard it.
        if source == self.nid:
            return

        # If the TC message in cache is newer or the same (cache[source] >=
        # seqno), discard it.
        try:
            if self.__msg_cache__[source] >= seqno:
                return
        except KeyError:
            pass
        finally:
            self.__msg_cache__[source] = seqno

        to_set = lambda x: set() if x == '' else set(x.split(' '))
        # print(neighbor, source, to_set(ms), ms_seqno)
        self.__route__.tc_update(source, to_set(ms), int(seqno))

        # Forward message if node itself is sender's mpr and TTL is greater
        # than 1.
        if self.__route__.mpr_of(neighbor):
            tc = self.__tc__.format(self.nid, source, seqno, ms)
            with open(self.fromfilename, 'a') as from_me:
                from_me.write(tc)

    def __receive_data__(self, msg):
        print("received data")
        pass

    def __msg_processor__(self):
        for msg in self.__msg_queue__:
            self.__message_processor__[msg.split(' ')[2]](msg)
            if self.__terminate__:
                break

    def start(self):
        self.__msg_queue__ =\
                (self.__executor__.submit(self.__follow_to_file__)).result()
        self.__executor__.submit(self.__msg_processor__)
        while self.time <= 122:
            print(self.__clock__.time)
            self.__executor__.submit(self.__check_timeout__)

            # Send message at specific timestamp.
            if self.time == self.timestamp:
                self.__executor__.submit(self.__send_data__)

            # Each node sends a hello message every 5 seconds.
            if self.time % 5 == 0:
                self.__executor__.submit(self.__send_hello__)

            # Every node with a non-empty MS sets creates and floods a TC
            # message every 10 seconds.
            if self.time % 10 == 0 and self.__route__.ms:
                self.__executor__.submit(self.__send_tc__)

            sleep(1)
            self.tick()
        # self.__executor__.shutdown(False)
        # print("executor shutdown");
        self.__terminate__ = True
        sleep(1)

def main():
    if len(argv) == 5:
        c = Node(argv[1], argv[2], argv[3], int(argv[4]))
    elif len(argv) == 3:
        c = Node(argv[1], argv[2])
    # print(c.nid, c.msg, c.timestamp, c.__route__)
    c.start()
    print("Node {} end.".format(argv[1]))

class TestNode(unittest.TestCase):
    """ This class is for testing the function of Node class. """

    def test_init(self):
        n = Node('0', '4', 'A message from 0 to 4', 40)
        self.assertEqual(n.nid, '0')
        self.assertEqual(n.dst, '4')
        self.assertEqual(n.msg, 'A message from 0 to 4')
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
        hello2 = '* 4 HELLO UNIDIR 0 BIDIR 9 8 MPR 8\n'
        hello3 = '* 3 HELLO UNIDIR  BIDIR 4 5 0 MPR 4 0\n'
        n.__receive_hello__(hello1)
        n.__receive_hello__(hello2)
        n.__receive_hello__(hello3)
        self.assertEqual(n.__route__.mpr, {'4', '3'})
        self.assertEqual(n.__route__.route, {'4':('4',1), '3':('3',1)})
        self.assertEqual(n.__route__.ms, {'3'})

        tc1 = '* 4 TC 4 1 MS 9 8 0\n'
        n.__receive_tc__(tc1)
        self.assertEqual(n.__route__.topo_tuple,
                         {('9','4'), ('8','4'), ('0','4')})
        self.assertEqual(n.__route__.route, 
                        {'4':('4',1), '3':('3',1), '9':('4',2), '8':('4',2)})

        tc2 = '* 3 TC 3 1 MS 5\n'
        tc3 = '* 4 TC 3 1 MS 5\n'
        tc4 = '* 3 TC 3 3 MS 0 5\n'
        tc5 = '* 4 TC 3 3 MS 0 5\n'
        n.__receive_tc__(tc2)
        n.__receive_tc__(tc3)
        n.__receive_tc__(tc2)
        n.__receive_tc__(tc3)

    def test_send_data(self):
        n = Node('0', '4', 'A message from 0 to 4', 40)
        hello1 = '* 1 HELLO UNIDIR  BIDIR 2 0 MPR 2 0\n'
        hello2 = '* 5 HELLO UNIDIR  BIDIR 0 4 MPR 0 4\n'
        tc1     = '* 1 TC 1 2 MS 0 2\n'
        tc2     = '* 1 TC 2 2 MS 1 3\n'
        tc3     = '* 5 TC 3 2 MS 2 4\n'
        tc4     = '* 1 TC 4 2 MS 3 5\n'
        tc5     = '* 5 TC 5 2 MS 0 4\n'
        n.__receive_hello__(hello1)
        n.__receive_hello__(hello2)
        n.__receive_tc__(tc1)
        n.__receive_tc__(tc2)
        n.__receive_tc__(tc3)
        n.__receive_tc__(tc4)
        n.__receive_tc__(tc5)

        self.assertEqual(n.__route__.bidir, {'1', '5'})
        self.assertEqual(n.__route__.unidir, set())
        self.assertEqual(n.__route__.two_hop, {'2', '4'})
        self.assertEqual(n.__route__.mpr, {'1', '5'})
        self.assertEqual(n.__route__.ms, {'1', '5'})
        try:
            self.assertEqual(n.__route__.route,
                            {'1':('1',1), '5':('5',1), '2':('1',2),
                            '4':('5',2), '3':('1',3)})
        except AssertionError:
            self.assertEqual(n.__route__.route,
                            {'1':('1',1), '5':('5',1), '2':('1',2),
                            '4':('5',2), '3':('5',3)})

        n.__send_data__()

if __name__ == '__main__':
    main()
