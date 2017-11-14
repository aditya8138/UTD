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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class L081 {
    @Test
    public void search() throws Exception {
        int[] a = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 1, 2, 3, 4, 5, 6, 7};
        assertTrue(new SolutionRec().search(a, 5));
        assertTrue(new SolutionRec().search(a, 13));
        assertFalse(new SolutionRec().search(a, 133));

        int[] b = {1, 3};
        assertTrue(new SolutionRec().search(b, 3));
    }

    /**
     * Recursive solution with binary search.
     */
    class SolutionRec {
        /**
         * Recursive binary search over sorted array.
         *
         * @param nums   Array to do search on.
         * @param target Search target.
         * @param l      Starting index.
         * @param r      Ending index.
         * @return True if exist, false otherwise.
         */
        public boolean bsearch(int[] nums, int target, int l, int r) {
            if (l > r)
                return false;

            final int mid = (r + l) / 2;
            if (nums[mid] == target)
                return true;
            if (nums[mid] > target)
                return bsearch(nums, target, l, mid - 1);
            return bsearch(nums, target, mid + 1, r);
        }

        /**
         * Recursive binary search over rotated sorted array.
         *
         * @param nums   Array to do search on.
         * @param target Search target.
         * @param l      Starting index.
         * @param r      Ending index.
         * @return True if exist, false otherwise.
         */
        public boolean search(int[] nums, int target, int l, int r) {
            if (l > r)
                return false;

            final int mid = (r + l) / 2;
            if (nums[mid] == target || nums[l] == target || nums[r] == target)
                return true;

            if (nums[l] < nums[mid]) {
                if (nums[mid] > target && nums[l] < target && nums[mid] > nums[l])
                    return bsearch(nums, target, l + 1, mid - 1);
                return search(nums, target, mid + 1, r - 1) || search(nums, target, l + 1, mid - 1);
            } else {
                if (nums[mid] < target && nums[r] > target && nums[mid] < nums[r])
                    return bsearch(nums, target, mid + 1, r - 1);
                return search(nums, target, mid + 1, r - 1) || search(nums, target, l + 1, mid - 1);
            }
        }

        public boolean search(int[] nums, int target) {
            return search(nums, target, 0, nums.length - 1);
        }
    }
}
