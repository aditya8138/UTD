# 046. Permutations
# The iterative solution.

import unittest
import math

class Solution(object):
    def permute(self, nums):
        """
        :type nums: List[int]
        :rtype: List[List[int]]
        """
        ret = [[]]
        for i in range(len(nums)):
            ret_prime = map(lambda y: (y, set(nums) - set(y)), ret)
            ret_prime_map = map(lambda (x,y): [x+[i] for i in y], ret_prime)
            ret = reduce(lambda x,y: x+y, ret_prime_map)

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
        self.assertEqual(len(s.permute([1,2,3,4,5,6,7])), math.factorial(7))
        self.assertEqual(len(s.permute([1,2,3,4,5,6,7,8])), math.factorial(8))
        # self.assertEqual(len(s.permute([1,2,3,4,5,6,7,8,9])),
                         # math.factorial(9))

if __name__ == '__main__':
    unittest.main()
