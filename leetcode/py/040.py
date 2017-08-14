# 039. Combination Sum

import unittest

class Solution(object):


    def __comSum__(self, candidates, target, dup):
        """
        :type candidates: sorted List[int]
        :type target: int
        :rtype: List[List[int]]
        """
        empty_filter = lambda x: x != list()
        add_cur = lambda x: [cur] + x

        cur = candidates[0]

        if cur == target:
            return[[cur]]

        if cur > target or len(candidates) == 1:
            return []

        if cur in dup:
            return filter(empty_filter,
                          self.__comSum__(candidates[1:], target, dup))

        _include = list()
        if cur < target:
            _include = self.__comSum__(candidates[1:], target - cur, dup)
        include = map(add_cur, filter(empty_filter, _include))

        _discard = self.__comSum__(candidates[1:],
                                   target,
                                   set.union(dup,{cur}))
        discard = filter(empty_filter, _discard)

        return include + discard

    def combinationSum2(self, candidates, target):
        """
        :type candidates: List[int]
        :type target: int
        :rtype: List[List[int]]
        """
        candidates = sorted(candidates)

        return self.__comSum__(candidates, target, set())


class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testCombinationSum2(self):
        s = Solution()

        result = [
                    [1, 7],
                    [1, 2, 5],
                    [2, 6],
                    [1, 1, 6]
                 ]
        self.assertEqual(s.combinationSum2([10, 1, 2, 7, 6, 1, 5], 8), result)

if __name__ == '__main__':
    unittest.main()
