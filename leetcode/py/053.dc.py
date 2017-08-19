# 053. Maximum Subarray
# The D&C solution, however, python is not suitable for recursion.

import unittest

class Solution(object):
    def maxSubArray(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        if len(nums) == 0:
            return 0
        if len(nums) == 1:
            return nums[0]

        mid = len(nums) / 2
        lmax = self.maxSubArray(nums[:mid])
        rmax = self.maxSubArray(nums[mid:])
        mmax = self.maxFromStart(nums[mid:]) + self.maxToEnd(nums[:mid])

        return max(lmax, rmax, mmax)


    def maxFromStart(self, nums):
        if nums == []:
            return 0
        return max(nums[0], nums[0] + self.maxFromStart(nums[1:]))

    def maxToEnd(self, nums):
        if nums == []:
            return 0
        return max(nums[-1], nums[-1] + self.maxToEnd(nums[:-1]))

class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testMaxSubArray(self):
        s = Solution()
        self.assertEqual(s.maxSubArray([-2, 1, -3, 4, -1, 2, 1, -5, 4]),  6)
        self.assertEqual(s.maxSubArray([-2, 1]),  1)
        self.assertEqual(s.maxSubArray([-1]),  -1)
        self.assertEqual(s.maxSubArray([-2, 1, -3, 4, -1, 2, 1, -5, 4, -2, 1,
            -3, 4, -1, 2, 1, -5, 4, -2, 1, -3, 4, -1, 2, 1, -5, 4, -2, 1, -3,
            4, -1, 2, 1, -5, 4]),  9)


if __name__ == '__main__':
    unittest.main()
