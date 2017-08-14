# 039. Combination Sum

import unittest

class Solution(object):

    def __combinationSum__(self, candidates, target):
        """
        :type candidates: sorted List[int]
        :type target: int
        :rtype: List[List[int]]
        """
        empty_filter = lambda x: x != list()
        add_cur = lambda x: [cur] + x

        cur = candidates[0]

        if cur > target:
            return []

        if cur == target:
            return [[cur]]

        if cur < target:
            _include = list() + self.combinationSum(candidates, target - cur)

        include = map(add_cur, filter(empty_filter, _include))

        discard = list()
        if len(candidates) > 1:
            _discard = self.__combinationSum__(candidates[1:], target)
            discard = filter(empty_filter, _discard)
        return include + discard

    def combinationSum(self, candidates, target):
        """
        :type candidates: List[int]
        :type target: int
        :rtype: List[List[int]]
        """
        candidates = sorted(candidates)

        return self.__combinationSum__(candidates, target)


class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testCombinationSum(self):
        # data = [0,1,3,2]
        s = Solution()
        self.assertEqual(s.combinationSum([2,3,6,7], 7), [[2,2,3],[7]])

        result = [[2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],
                  [2,2,2,2,2,2,2,2,2,2,2,2,2,2,4],
                  [2,2,2,2,2,2,2,2,2,2,2,2,4,4],
                  [2,2,2,2,2,2,2,2,2,2,2,2,8],
                  [2,2,2,2,2,2,2,2,2,2,4,4,4],
                  [2,2,2,2,2,2,2,2,2,2,4,8],
                  [2,2,2,2,2,2,2,2,4,4,4,4],
                  [2,2,2,2,2,2,2,2,4,4,8],
                  [2,2,2,2,2,2,2,2,8,8],
                  [2,2,2,2,2,2,2,2,16],
                  [2,2,2,2,2,2,4,4,4,4,4],
                  [2,2,2,2,2,2,4,4,4,8],
                  [2,2,2,2,2,2,4,8,8],
                  [2,2,2,2,2,2,4,16],
                  [2,2,2,2,4,4,4,4,4,4],
                  [2,2,2,2,4,4,4,4,8],
                  [2,2,2,2,4,4,8,8],
                  [2,2,2,2,4,4,16],
                  [2,2,2,2,8,8,8],
                  [2,2,2,2,8,16],
                  [2,2,4,4,4,4,4,4,4],
                  [2,2,4,4,4,4,4,8],
                  [2,2,4,4,4,8,8],
                  [2,2,4,4,4,16],
                  [2,2,4,8,8,8],
                  [2,2,4,8,16],
                  [4,4,4,4,4,4,4,4],
                  [4,4,4,4,4,4,8],
                  [4,4,4,4,8,8],
                  [4,4,4,4,16],
                  [4,4,8,8,8],
                  [4,4,8,16],
                  [8,8,8,8],
                  [8,8,16],
                  [16,16]]

        self.assertEqual(s.combinationSum([2,4,8,16], 32), result)

if __name__ == '__main__':
    unittest.main()
