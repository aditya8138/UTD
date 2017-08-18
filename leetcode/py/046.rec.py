# 046. Permutations
# The recursive solution.

import unittest
import math

class Solution(object):
    def permute(self, nums):
        """
        :type nums: List[int]
        :rtype: List[List[int]]
        """

        if nums == []:
            return [[]]

        ret = list()
        for n in nums:
            r = self.permute(list(set(nums) - set([n])))
            ret = ret + list(map(lambda x: [n] + x, r))

        return ret

class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testPermute(self):
        s = Solution()
        self.assertEqual(s.permute([1,2]), [[1,2], [2,1]])
        self.assertEqual(len(s.permute([1,2,3])),
                len([[1,2,3],[2,1,3],[2,3,1],[1,3,2],[3,1,2],[3,2,1]]))

        self.assertEqual(len(s.permute([1,2,3,4,5,6])), math.factorial(6))
        self.assertEqual(len(s.permute([1,2,3,4,5,6,7,8,9])),
                         math.factorial(9))

if __name__ == '__main__':
    unittest.main()
