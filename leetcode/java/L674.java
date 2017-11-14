/*
 * Copyright (c) 2017.  Hanlin He
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Given an unsorted array of integers, find the length of longest continuous increasing
 * subsequence.
 */
public class L674 {

    @Test
    public void findLengthOfLCIS() throws Exception {
        Solution solution = new Solution();
        assertEquals(3, solution.findLengthOfLCIS(new int[]{1, 3, 5, 4, 7}));
        assertEquals(1, solution.findLengthOfLCIS(new int[]{2, 2, 2, 2, 2}));
    }

    class Solution {
        /**
         * Find the length of longest continuous increasing subsequence.
         *
         * @param nums Array to be checked.
         * @return
         */
        public int findLengthOfLCIS(int[] nums) {
            int i = 0, j = 1;
            int max = 0;
            int tmp;
            while (i < nums.length) {
                while (j < nums.length && nums[j] > nums[j - 1])
                    j++;
                tmp = j - i;
                if (tmp > max)
                    max = tmp;
                i = j;
                j += 1;
            }
            return max;
        }

    }
}
