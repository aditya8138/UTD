# 056. Merge Intervals
# Intuitive solution. Not pythonic at all...

import unittest

# Definition for an interval.
class Interval(object):
    def __init__(self, s=0, e=0):
        self.start = s
        self.end = e

    # Helper function for testing.
    def __repr__(self):
        return "(" + str(self.start) + " " + str(self.end) + ")"

    def __eq__(self, other):
        return self.start == other.start and self.end == other.end

class Solution(object):
    def merge(self, intervals):
        """
        :type intervals: List[Interval]
        :rtype: List[Interval]
        """
        left = []
        right = []
        for interval in intervals:
            left.append(interval.start)
            right.append(interval.end)

        left.sort()
        right.sort()

        liter = iter(left)
        riter = iter(right)

        ret = []

        try:
            nextl = liter.next()
        except StopIteration:
            return []

        while True:
            try:
                l = nextl
                nextl = liter.next()
                nextr = riter.next()
                while nextr >= nextl:
                    nextl = liter.next()
                    nextr = riter.next()
                ret.append(Interval(l, nextr))
            except StopIteration:
                ret.append(Interval(l, right[-1]))
                break
        return ret

class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testMerge(self):
        # data = [0,1,3,2]
        s = Solution()

        intervals = [Interval(1,3),Interval(2,6),Interval(2,7),Interval(6,10)]
        self.assertEqual(s.merge(intervals), [Interval(1, 10)])

if __name__ == '__main__':
    unittest.main()
