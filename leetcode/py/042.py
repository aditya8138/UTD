# 042. Trapping Rain Water

# Recursive solution with no tail recursive optimization may cause
# RuntimeError: maximum recursion depth exceeded

import unittest

class Solution(object):

    def trap(self, height):
        """
        :type height: List[int]
        :rtype: int
        """
        ret = 0
        index = 0
        hstack = list()
        while index < len(height):
            cur = height[index]
            try:
                if hstack == []:
                    continue

                last_h, _ = hstack[len(hstack) - 1]

                if last_h > cur:
                    continue

                if last_h == cur:
                    hstack.pop()
                    continue

                base_h, _ = hstack.pop()
                if hstack == []:
                    continue

                while hstack != []:
                    last_h, last_i = hstack[len(hstack) - 1]
                    if last_h > cur:
                        ret += (cur - base_h) * (index - last_i - 1)
                        break
                    if last_h == cur:
                        hstack.pop()
                        ret += (cur - base_h) * (index - last_i - 1)
                        break
                    if last_h < cur:
                        ret += (last_h - base_h) * (index - last_i - 1)
                        base_h,_ = hstack.pop()
            finally:
                hstack.append((cur, index))
                index += 1

        return ret


class SolutionUnitTest(unittest.TestCase):
    def setup(self):
        pass
    def tearDown(self):
        pass
    def testTrap(self):
        s = Solution()
        # print "result:", s.__trap__([100,1,5,6,0,6], [], 0)
        # print "result:", s.__trap__([0,1,0,2,1,0,1,3,2,1,2,1], [], 0)

        self.assertEqual(s.trap([0,1,0,2,1,0,1,3,2,1,2,1]), 6)
        self.assertEqual(s.trap([]), 0)

        l1 = [1, 2, 3, 4, 5, 6, 7, 8, 9, 7, 6, 5, 4, 3, 2, 1, 1, 2, 2, 3, 3, 4,
                4, 4, 6, 7, 8, 8, 8, 7, 6, 5, 5, 4, 6, 6, 3, 2, 9, 8, 10, 1, 2]

        l2 = [1, 2, 3, 4, 5, 6, 7, 8, 9, 7, 6, 5, 4, 3, 2, 1, 1, 2, 2, 3, 3, 4]

        # self.assertEqual(s.trap(l1), 131)
        self.assertEqual(s.trap(l2), 15)

    def testTrap2(self):
        s = Solution()

        l = [5,5,1,7,1,1,5,2,7,6]
        self.assertEqual(s.trap(l), 23)


if __name__ == '__main__':
    unittest.main()

