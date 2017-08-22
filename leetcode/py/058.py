# 058. Length of Last Word

import unittest

class Solution(object):
    def lengthOfLastWord(self, s):
        """
        :type s: str
        :rtype: int
        """
        terminate = False
        ret = 0
        for c in s[::-1]:
            if c == ' ':
                if terminate:
                    break
                continue
            ret += 1
            terminate = True

        return ret

class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testLength(self):
        s = Solution()
        self.assertEqual(s.lengthOfLastWord("Hello World"), 5)

if __name__ == '__main__':
    unittest.main()
