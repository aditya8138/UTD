# 007. Reverse Integer
import unittest

class Solution(object):
    def reverse(self, x):
        """
        :type x: int
        :rtype: int
        """
        if x < 0:
            x = -x
            print(x)
            # my_string[::-1] is used to reverse string.
            x = -int(str(x)[::-1])
        else:
            x = int(str(x)[::-1])

        if (x > 2147483647) or (x < -2147483648):
            return 0
        else:
            return x

class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testsingleNumber(self):
        # data = [0,1,3,2]
        s = Solution()
        self.assertEqual(s.reverse(123456789), 987654321)

if __name__ == '__main__':
    unittest.main()
