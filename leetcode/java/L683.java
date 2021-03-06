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

        int[] flowers = new int[]{2,1,4,3};

        int k = 1;
        assertEquals(1, new Solution().kEmptySlots(flowers, k));
    }

    class Solution {

        public int kEmptySlots(int[] flowers, int k) {
            if (k > flowers.length)
                return -1;
            int b = 0;
            boolean[] boom = new boolean[flowers.length + 1];

            // Current implementation is not accepted on Leetcode.
            boom[0] = true;
            for (int f : flowers) {
                b++;
                boom[f] = true;
                int i;

                //
                for (i = 1; f + i <= flowers.length && !boom[f + i]; i++) ;
                if (f + i <= flowers.length && i - 1 == k)
                    return b;

                //
                for (i = -1; f + i >= 0 && !boom[f + i]; i--) ;
                if (f + i >= 0 && 0 - i - 1 == k)
                    return b;
            }
            return -1;
        }
    }
}
