import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <h1>718. Maximum Length of Repeated Subarray</h1>
 * <p>
 * Given two integer arrays A and B, return the maximum length of an subarray that appears in both
 * arrays.
 * <p>
 * <pre>
 * Example 1:
 * Input:
 * A: [1,2,3,2,1]
 * B: [3,2,1,4,7]
 * Output: 3
 * Explanation:
 * The repeated subarray with maximum length is [3, 2, 1].
 * </pre>
 * Note: 1 <= len(A), len(B) <= 1000 0 <= A[i], B[i] < 100
 */
public class L718 {

    @Test
    public void findLength() throws Exception {
        Solution solution = new Solution();

        int[] a = {1, 2, 3, 2, 1};
        int[] b = {3, 2, 1, 4, 7};

        assertEquals(3, solution.findLength(a, b));
    }

    class Solution {

        /**
         * Basic DP solution. Similar to Longest Common Subsequence, but with some difference.
         */
        public int findLength(int[] A, int[] B) {
            int[][] l = new int[A.length + 1][B.length + 1];

            for (int i = 0; i <= A.length; i++)
                l[i][B.length] = 0;

            for (int i = 0; i <= B.length; i++)
                l[A.length][i] = 0;

            // Different from LCS, a max length must recorded.
            int max = 0;

            for (int i = A.length - 1; i >= 0; i--) {
                for (int j = B.length - 1; j >= 0; j--) {
                    if (A[i] == B[j])
                        l[i][j] = l[i + 1][j + 1] + 1;
                    else
                        l[i][j] = 0;

                    if (l[i][j] > max)
                        max = l[i][j];
                }
            }

            return max;
        }
    }

}
