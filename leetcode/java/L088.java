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

import static org.junit.Assert.assertArrayEquals;

/**
 * 088. Merge Sorted Array.
 * <p>
 * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
 * <p>
 * Note: You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold
 * additional elements from nums2. The number of elements initialized in nums1 and nums2 are m and n
 * respectively.
 */
public class L088 {
    @Test
    public void merge() throws Exception {
        int[] a = new int[10];
        a[0] = 1;
        a[1] = 3;
        a[2] = 5;
        a[3] = 7;
        int[] b = new int[]{2, 4, 5, 6, 7, 8};

        Solution solution = new Solution();

        solution.merge(a, 4, b, 6);

        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 5, 6, 7, 7, 8}, a);
    }

    class Solution {

        public void merge(int[] nums1, int m, int[] nums2, int n) {

            int i1 = m - 1, i2 = n - 1;

            for (int index = m + n - 1; index >= 0; index--) {
                if (i1 < 0) {
                    nums1[index] = nums2[i2];
                    i2--;
                    continue;
                }
                if (i2 < 0) {
                    nums1[index] = nums1[i1];
                    i1--;
                    continue;
                }
                if (nums1[i1] > nums2[i2]) {
                    nums1[index] = nums1[i1];
                    i1--;
                    continue;
                }
                nums1[index] = nums2[i2];
                i2--;
            }
        }
    }
}
