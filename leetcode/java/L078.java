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

import java.util.ArrayList;
import java.util.List;

public class L078 {
    @Test
    public void subsetsBitwise() throws Exception {
        Solution solution = new Solution();
        int[] s = {11, 22, 33, 44, 55};
        System.out.println(solution.subsetsBitwise(s));
    }

    @Test
    public void subsetsIntuitive() throws Exception {
        Solution solution = new Solution();
        int[] s = {11, 22, 33, 44, 55};
        System.out.println(solution.subsetsIntuitive(s));
    }

    @Test
    public void subsets() throws Exception {
    }

    class Solution {
        /**
         * Intuitive solution following the previous problem 077.Combination. The union of all
         * combinations are the subsets. The previous solution compute all the index (actually
         * index+1), then after the union, change each value the actual value in the corresponding
         * index. Actual running time is slow.
         *
         * @param nums : List to be compute.
         * @return : List of all subset.
         */
        public List<List<Integer>> subsetsIntuitive(int[] nums) {
            L077.Solution c = new L077.Solution();

            List<List<Integer>> ret = new ArrayList<>();
            for (int i = 0; i <= nums.length; i++)
                ret.addAll(c.combine(nums.length, i));

            for (List<Integer> l : ret) {
                l.replaceAll(i -> nums[i - 1]);
            }

            return ret;
        }

        /**
         * Bitwise solution. Since a subset can be represented as a bit string of length n, like
         * '00010000101', and total number of possible subset is 2^n. We can just iterate from 1 to
         * 2^n, for each number, generate a subset based on the corresponding bit string. A trick to
         * extract specific bit from a bit string is simply bitwise AND with another specific bit
         * string, for example '00000001'.
         *
         * @param nums : List to be compute.
         * @return : List of all subset.
         */
        public List<List<Integer>> subsetsBitwise(int[] nums) {
            int size = ((int) Math.pow(2, nums.length));
            List<List<Integer>> ret = new ArrayList<>(size);
            List<Integer> subset;
            for (int i = 0; i < size; i++) {
                subset = new ArrayList<>();
                for (int j = i, k = 0; j > 0; j >>= 1, k++) {
                    if ((j & 1) == 1)
                        subset.add(nums[k]);
                }
                ret.add(subset);
            }
            return ret;
        }
    }
}
