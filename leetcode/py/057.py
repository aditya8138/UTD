# 057. Insert Interval
# Customized solution, no sorting since the intervals are already sorted.

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
    def insert(self, intervals, newInterval):
        """
        :type intervals: List[Interval]
        :type newInterval: Interval
        :rtype: List[Interval]
        """
        if intervals == []:
            return [newInterval]

        if intervals[-1].end < newInterval.start:
            return intervals + [newInterval]

        if intervals[0].start > newInterval.end:
            return [newInterval] + intervals

        left = 0
        for i,k in enumerate(intervals):
            if k.end < newInterval.start:
                continue
            left = i
            break

        right = len(intervals)
        for i,k in enumerate(intervals[left:]):
            if k.start <= newInterval.end:
                continue
            right = i + left
            break

        if left == right:
            intervals.insert(left, newInterval)
            return intervals

        merged = Interval(min(newInterval.start, intervals[left].start),
                         max(newInterval.end, intervals[right-1].end))

        return intervals[:left] + [merged] + intervals[right:]

class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testMerge(self):
        # data = [0,1,3,2]
        s = Solution()

        intervals = [Interval(1,2),Interval(3,4),Interval(6,7),Interval(8,10)]
        self.assertEqual(s.insert(intervals, Interval(3,8)),
                         [Interval(1,2), Interval(3,10)])

if __name__ == '__main__':
    unittest.main()
