# 053. Maximum Subarray
# The simple O(n) solution.

import unittest

class Solution(object):
    def maxSubArray(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        ret = nums[0]
        pre = nums[0]
        for i in nums[1:]:
            if ret < i and ret < 0:
                ret = pre = i
                continue
            cur = pre + i
            if ret < cur:
                ret = pre = cur
                continue
            if cur >= 0:
                pre = cur
                continue
            # if cur < 0: # Better start over.
            pre = 0
        return ret

class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testMaxSubArray(self):
        s = Solution()
        self.assertEqual(s.maxSubArray([-2,1,-3,4,-1,2,1,-5,4]), 6)
        self.assertEqual(s.maxSubArray([-2,1]), 1)
        self.assertEqual(s.maxSubArray([-1]), -1)

if __name__ == '__main__':
    unittest.main()
