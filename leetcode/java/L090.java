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
import java.util.LinkedList;
import java.util.List;

/**
 * <h1>090. Subsets II</h1>
 * <p>
 * Given a collection of integers that might contain duplicates, nums, return all possible subsets
 * (the power set).
 * <p>
 * Note: The solution set must not contain duplicate subsets.
 * <p>
 * For example, If nums = [1,2,2], a solution is: [ [2], [1], [1,2,2], [2,2], [1,2], [] ]
 */
public class L090 {

    @Test
    public void subsetsWithDup() throws Exception {
        Solution solution = new Solution();

        solution.subsetsWithDup(new int[]{1, 2, 2, 9, 8, 8, 7, 7, 7, 6, 5, 5, 5}).forEach(System.out::println);
    }

    class Solution {
        public List<List<Integer>> subsetsWithDup(int[] nums) {
            Arrays.sort(nums);

            List<Integer> subset = new LinkedList<>();

            List<List<Integer>> ret = new LinkedList<>();

            ret.add(subset);

            subsetsWithDup(nums, 0, ret, subset);

            return ret;
        }

        /**
         * Helper function to compute all subset except empty set (which might be a flaw).
         *
         * @param nums   Integer array with duplicate elements to compute all subset.
         * @param k      Current element to concern in the call.
         * @param ret    Return list of subset, pre-allocated by caller.
         * @param subset Current list of integer indicating a subset.
         */
        private void subsetsWithDup(final int[] nums,
                                    final int k,
                                    final List<List<Integer>> ret,
                                    final List<Integer> subset) {

            // If no more elements left in array, return.
            if (k >= nums.length)
                return;

            // Otherwise, first put current index integer into subset,
            // add to ret, and recursive compute next subset include current integer.
            subset.add(nums[k]);
            ret.add(new LinkedList<>(subset));
            subsetsWithDup(nums, k + 1, ret, subset);

            // After computing next subset based on current subset,
            // remove current element just added.
            subset.remove(subset.size() - 1);

            // Jump over duplicate elements to eliminate duplicate subset. (KEY step)
            int i;
            for (i = 1; k + i < nums.length && nums[k + i - 1] == nums[k + i]; i++) ;

            // Recursively compute subset without current integer.
            subsetsWithDup(nums, k + i, ret, subset);
        }
    }
}
