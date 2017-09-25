import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * There is a garden with N slots. In each slot, there is a flower. The N flowers will bloom one by
 * one in N days. In each day, there will be exactly one flower blooming and it will be in the
 * status of blooming since then.
 * <p>
 * Given an array flowers consists of number from 1 to N. Each number in the array represents the
 * place where the flower will open in that day.
 * <p>
 * For example, flowers[i] = x means that the unique flower that blooms at day i will be at position
 * x, where i and x will be in the range from 1 to N.
 * <p>
 * Also given an integer k, you need to output in which day there exists two flowers in the status
 * of blooming, and also the number of flowers between them is k and these flowers are not
 * blooming.
 * <p>
 * If there isn't such day, output -1.
 * <p>
 * Example 1:
 * <pre> Input:
 * flowers: [1,3,2]
 * k: 1
 * Output: 2
 * Explanation: In the second day, the first and the third flower have become blooming.</pre>
 * <p>
 * Example 2:
 * <pre>Input:
 * flowers: [1,2,3]
 * k: 1
 * Output: -1</pre>
 * <p>
 * Note: The given array will be in the range [1, 20000].
 */

public class L683 {
    @Test
    public void calPoints() throws Exception {

        int[] flowers = new int[]{1, 3, 2};

        int k = 1;
        assertEquals(2, new Solution().kEmptySlots(flowers, k));
    }

    class Solution {

        public int kEmptySlots(int[] flowers, int k) {
            if (k > flowers.length)
                return -1;
            int b = 0;
            boolean[] boom = new boolean[flowers.length + 1];
            for (int f : flowers) {
                b++;
                boom[f] = true;
                int i;
                for (i = 1; f + i <= flowers.length && !boom[f + i]; i++) ;
                if (f + i <= flowers.length && i - 1 == k)
                    return b;
                for (i = -1; f + i > 0 && !boom[f + i]; i--) ;
                if (f + i > 0 && 0 - i - 1 == k)
                    return b;
            }
            return -1;
        }
    }
}
