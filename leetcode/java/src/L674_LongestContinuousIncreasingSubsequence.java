/**
 * Given an unsorted array of integers, find the length of longest continuous increasing
 * subsequence.
 */
public class L674_LongestContinuousIncreasingSubsequence {

    /**
     * @param nums
     * @return
     */
    public int findLengthOfLCIS(int[] nums) {
        int i = 0, j = 1;
        int max = 0;
        int tmp;
        while (i < nums.length) {
            while (nums[j] > nums[j - 1] && j < nums.length)
                j++;
            tmp = j - i;
            if (tmp > max)
                max = tmp;
            i = j;
        }
        return max;
    }
}
