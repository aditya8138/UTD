import unittest

class Solution(object):
    def groupAnagrams(self, strs):
        """
        :type strs: List[str]
        :rtype: List[List[str]]
        """
        prime = {'a':2,  'b':3,  'c':5,  'd':7,  'e':11, 'f':13, 'g':17, 
                 'h':19, 'i':23, 'j':29, 'k':31, 'l':41, 'm':43, 'n':47, 
                 'o':53, 'p':59, 'q':61, 'r':67, 's':71, 't':73, 
                 'u':79, 'v':83, 'w':89, 'x':97, 'y':101, 'z':103}

        nstrs = map(lambda x: (reduce(lambda x, y: x * y, map(lambda x: prime[x], x), -1), x), strs)

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
