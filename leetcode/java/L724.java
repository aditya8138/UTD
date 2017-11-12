import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 724. Find Pivot Index
 * <p>
 * Given an array of integers nums, write a method that returns the "pivot" index of this array.
 * <p>
 * We define the pivot index as the index where the sum of the numbers to the left of the index is
 * equal to the sum of the numbers to the right of the index.
 * <p>
 * If no such index exists, we should return -1. If there are multiple pivot indexes, you should
 * return the left-most pivot index.
 * <p>
 * Example 1:
 * <pre>
 * Input:       nums = [1, 7, 3, 6, 5, 6]
 * Output:      3
 * Explanation: The sum of the numbers to the left of index 3 (nums[3] = 6) is equal to the sum of
 *              numbers to the right of index 3. Also, 3 is the first index where this occurs.
 * </pre>
 * <p>
 * Example 2:
 * <pre>
 * Input:       nums = [1, 2, 3]
 * Output:      -1
 * Explanation: There is no index that satisfies the conditions in the problem statement.
 * </pre>
 * Note:
 * <p>
 * The length of nums will be in the range [0, 10000]. Each element nums[i] will be an integer in
 * the range [-1000, 1000].
 */
public class L724 {

    @Test
    public void pivotIndex() throws Exception {
        Solution solution = new Solution();

        int[] nums1 = {1, 7, 3, 6, 5, 6};
        assertEquals(3, solution.pivotIndex(nums1));

        int[] nums2 = {-1, -1, -1, 0, 1, 1, 1, 0};
        assertEquals(7, solution.pivotIndex(nums2));

        int[] nums3 = {-1, -1, -1, -1, -1, -1, 0};
        assertEquals(-1, solution.pivotIndex(nums3));
    }

    class Solution {
        public int pivotIndex(int[] nums) {

            if (nums.length == 0)
                return -1;
            int sum = 0;
            for (int i : nums)
                sum += i;

            int left = 0;
            int i = 0;
            int right = sum - nums[i];

            while (i < nums.length - 1) {
                if (left == right)
                    return i;
                left += nums[i];
                i++;
                right -= nums[i];
            }
            return left == right ? i : -1;
        }
    }
}
