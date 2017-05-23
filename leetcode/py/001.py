# 001. Two Sum
import unittest

class Solution:
    def twoSum(self, nums, target):
        """
        :type nums: List[int]
        :type target: int
        :rtype: List[int]
        """
        H = {}
        for i,val in enumerate(nums):
            if str(target - val) in H:
                return [i, H[str(target - val)]]
            else:
                H[str(val)] = i
        return []

class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testsingleNumber(self):
        # data = [0,1,3,2]
        s = Solution()
        self.assertEqual(s.twoSum([3,2,4], 6), [2,1])

if __name__ == '__main__':
    unittest.main()
