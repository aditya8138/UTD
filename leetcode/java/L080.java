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

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

/**
 * Follow up for "Remove Duplicates": What if duplicates are allowed at most twice?
 * <p>
 * For example, Given sorted array nums = [1,1,1,2,2,3],
 * <p>
 * Your function should return length = 5, with the first five elements of nums being 1, 1, 2, 2 and
 * 3. It doesn't matter what you leave beyond the new length.
 */
public class L080 {
    // For testing.
    int[] a = {1, 1, 1, 1, 2, 3, 3, 3, 4, 4, 5, 6, 6, 6, 6, 7, 7, 8};
    int[] d2 = {1, 1, 2, 3, 3, 4, 4, 5, 6, 6, 7, 7, 8};
    int[] d3 = {1, 1, 1, 2, 3, 3, 3, 4, 4, 5, 6, 6, 6, 7, 7, 8};

    @Test
    public void removeDuplicatesK() throws Exception {
        Solution solution = new Solution();
        int[] ret = Arrays.copyOf(a, solution.removeDuplicatesK(a, 3));
        assertArrayEquals(d3, ret);
    }

    @Test
    public void removeDuplicatesK2() throws Exception {
        Solution solution = new Solution();
        int[] ret = Arrays.copyOf(a, solution.removeDuplicatesK2(a, 3));
        assertArrayEquals(d3, ret);
    }

    @Test
    public void removeDuplicates() throws Exception {
        Solution solution = new Solution();
        int[] ret = Arrays.copyOf(a, solution.removeDuplicates(a));
        assertArrayEquals(d2, ret);
    }

    class Solution {
        /**
         * Remove duplicate in a sorted array, allowing at most k duplicate for an element. The key
         * idea is keep the track of current duplicate number, if less then k, add the element, and
         * update k.
         *
         * @param nums Array to be processed.
         * @param k    Number of duplicate elements allowed.
         * @return The length of the array after removing duplicates.
         */
        public int removeDuplicatesK(int[] nums, final int k) {
            final int length = nums.length;
            int cursor = 0;
            int it = 1;
            int state = k - 1;

            while (it < length) {
                if (state > 0) {
                    if (nums[cursor] == nums[it])
                        state--;
                    else
                        state = k - 1;
                    cursor++;
                    nums[cursor] = nums[it];
                    it++;
                }

                // If total duplicate for current element reach limits, jump over all duplicates hereafter.
                if (state == 0) {
                    while (it < length && nums[it] == nums[cursor])
                        it++;
                    state = k;
                }
            }
            return cursor + 1;
        }

        /**
         * An attemp to simplify the code. Remove the jumping part, leave the operation to the outer
         * for-loop.
         */
        public int removeDuplicatesK2(int[] nums, final int k) {
            final int length = nums.length;
            int cursor = 0;
            int state = k - 1;

            for (int it = 1; it < length; it++) {
                // If met new elememnt, reset state.
                if (nums[it] != nums[cursor])
                    state = k;

                if (state > 0) {
                    if (nums[cursor] == nums[it])
                        state--;
                    else
                        state = k - 1;
                    cursor++;
                    nums[cursor] = nums[it];
                }
            }
            return cursor + 1;
        }

        /**
         * Remove duplicate in a sorted array, allowing at most 2 duplicate for an element. A
         * special case of removeDuplicatesK, just call the function with parameter 2.
         *
         * @param nums Array to be processed.
         * @return The length of the array after removing duplicates.
         */
        public int removeDuplicates(int[] nums) {
            return removeDuplicatesK(nums, 2);
        }
    }
}
