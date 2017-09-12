import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Given an unsorted array of integers, find the length of longest continuous increasing
 * subsequence.
 */
public class L674 {

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

    @Test
    public void findLengthOfLCIS() throws Exception {
        Solution solution = new Solution();
        assertEquals(3, solution.findLengthOfLCIS(new int[]{1, 3, 5, 4, 7}));
        assertEquals(1, solution.findLengthOfLCIS(new int[]{2, 2, 2, 2, 2}));
    }
}
