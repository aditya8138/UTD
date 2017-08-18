# 047. Permutations II
# The recursive solution based on 046.

import unittest
import math

class Solution(object):
    def permuteUnique(self, nums):
        """
        :type nums: List[int]
        :rtype: List[List[int]]
        """
        if nums == []:
            return [[]]

        ret = list()
        s = set()
        for i,n in enumerate(nums):
            if n in s:
                continue
            r = self.permuteUnique(map(lambda (_,x): x,
                list(set(enumerate(nums)) - set([(i,n)]))))
            ret = ret + map(lambda x: [n] + x, r)
            s.add(n)

        return ret

class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testPermute(self):
        s = Solution()
        self.assertEqual(len(s.permuteUnique([1,1,2])), 3)

if __name__ == '__main__':
    unittest.main()
