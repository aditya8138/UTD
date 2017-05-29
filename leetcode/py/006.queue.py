# 006. ZigZag Conversion
# The idea is based on Min Priority Queue.
# However, in python, this turned out to be really slow.
import unittest
import queue

class Solution(object):
    def convert(self, s, numRows):
        """
        :type s: str
        :type numRows: int
        :rtype: str
        """
        if numRows == 1:
            return s
        q = queue.PriorityQueue()
        for i,c in enumerate(s):
            p = i % ((numRows-1) * 2)
            if p >= numRows:
                p = numRows * 2 - 2 - p
            q.put(((p,i), c))
        ret = ""
        while not q.empty():
            _,c = q.get()
            ret = ret+c
        return ret

class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testsingleNumber(self):
        s = Solution()
        self.assertEqual(
            s.convert("perowkjahsdmnzmqweuiryakjdshvzkljaptipoaadkjfh", 4),
            "pjnujkikekamzeikdzltpdjrwhdmwrasvjpoafosqyhaah")

if __name__ == '__main__':
    unittest.main()
