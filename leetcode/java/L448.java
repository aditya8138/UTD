import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and
 * others appear once.
 * <p>
 * Find all the elements of [1, n] inclusive that do not appear in this array.
 * <p>
 * Could you do it without extra space and in O(n) runtime? You may assume the returned list does
 * not count as extra space.
 * <p>
 * Example:
 * <pre>
 * Input:
 * [4,3,2,7,8,2,3,1]
 * Output:
 * [5,6]
 * </pre>
 */
public class L448 {

    @Test
    public void findDisappearedNumbers() throws Exception {
        Solution solution = new Solution();

        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};

        assertArrayEquals(new Integer[]{5, 6}, solution.findDisappearedNumbers(nums).toArray(new Integer[0]));
    }

    class Solution {
        /**
         * First in place rearrange the array so that each number x is place in index x-1. For
         * duplicate numbers, leave them at original index. After rearrangement, all index with
         * number in it not satisfy index = x - 1, are corresponding to the missing number.
         */
        public List<Integer> findDisappearedNumbers(int[] nums) {
            int i = 0;
            int tmp;
            while (i < nums.length) {
                tmp = nums[i];
                if (nums[tmp - 1] == tmp) {
                    i++;
                } else {
                    nums[i] = nums[tmp - 1];
                    nums[tmp - 1] = tmp;
                }
            }

            List<Integer> ret = new ArrayList<>();

            for (i = 0; i < nums.length; i++) {
                if (nums[i] - 1 != i) {
                    ret.add(i + 1);
                }
            }

            return ret;
        }
    }
}
