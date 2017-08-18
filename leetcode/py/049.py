import unittest

class Solution(object):
    def groupAnagrams(self, strs):
        """
        :type strs: List[str]
        :rtype: List[List[str]]
        """
        prime = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 41, 43, 47,
                 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103]

        nstrs = map(lambda x: (reduce(lambda x, y: x * y, map(lambda x: prime[x - ord('a')], map(ord, x)), -1), x), strs)

        d = dict()

        for k,v in nstrs:
            if k not in d:
                d[k] = [v]
            else:
                d[k] = d[k] + [v]

        ret = []

        for k, v in d.items():
            ret += [v]

        return ret


class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testGroupAnagrams(self):
        # data = [0,1,3,2]
        s = Solution()
        self.assertEqual(s.groupAnagrams(["eat", "tea", "tan", "ate", "nat", "bat"]),
                [["nat","tan"],["bat"],["ate","eat","tea"]])

if __name__ == '__main__':
    unittest.main()
