# 042. Trapping Rain Water

# Recursive solution with no tail recursive optimization may cause
# RuntimeError: maximum recursion depth exceeded

import unittest

class Solution(object):

    def __trap__(self, height, hstack, index):
        # Base case, if no height left, no trap water.
        if height == []:
            return 0

        # In the beginning, no height in stack, add one and continue.
        if hstack == []:
            hstack.append((height[index], index))
            return self.__trap__(height[1:], hstack, index+1)

        # In the processing, first pop last element.
        last_h, last_i = hstack[len(hstack) - 1]

        if last_h > height[0]:
            hstack.append((height[0], index))
            return self.__trap__(height[1:], hstack, index+1)

        if last_h < height[0]:
            base_h,_ = hstack.pop()

            if hstack == []:
                hstack.append((height[0], index))
                return self.__trap__(height[1:], hstack, index+1)

            increment = 0

            while hstack != []:
                last_h, last_i = hstack[len(hstack) - 1]
                if last_h > height[0]:
                    increment = increment + (height[0] - base_h) * (index - last_i - 1)
                    break

                if last_h == height[0]:
                    hstack.pop()
                    increment = increment + (height[0] - base_h) * (index - last_i - 1)
                    break

                if last_h < height[0]:
                    increment = increment + (last_h - base_h) * (index - last_i - 1)
                    base_h,_ = hstack.pop()

            hstack.append((height[0], index))
            return increment + self.__trap__(height[1:], hstack, index+1)

        hstack.pop()
        hstack.append((height[0],index))

        return self.__trap__(height[1:], hstack, index+1)

    def trap(self, height):
        """
        :type height: List[int]
        :rtype: int
        """
        return self.__trap__(height, [], 0)


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
