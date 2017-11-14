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
 * Given a non-negative integer, you could swap two digits at most once to get the maximum valued
 * number. Return the maximum valued number you could get. <ul> <li>Example 1: Input: 2736, Output:
 * 7236. Explanation: Swap the number 2 and the number 7.</li> <li>Example 2: Input: 9973 Output:
 * 9973. Explanation: No swap.</li> </ul> Note: The given number is in the range [0, 108]
 */
public class L670 {

    @Test
    public void maximumSwap() throws Exception {
        Solution solution = new Solution();
        assertEquals(7236, solution.maximumSwap(2736));
        assertEquals(9973, solution.maximumSwap(9937));
        assertEquals(9913, solution.maximumSwap(1993));
        assertEquals(99837, solution.maximumSwap(99738));
        assertEquals(98863, solution.maximumSwap(98368));
        assertEquals(10000, solution.maximumSwap(10000));
        assertEquals(110000, solution.maximumSwap(100010));
    }

    class Solution {
        /**
         * Return the maximum valued number with one swap. First transform the number to char array.
         * For each char, find the largest char afterward. If the largest is the same as current
         * char, continue to next iteration. Otherwise, swap and break.
         *
         * @param num Number to be processed.
         * @return The maximum valued number with one swap.
         */
        public int maximumSwap(int num) {

            String s = String.valueOf(num);
            char[] chars = Integer.toString(num).toCharArray();

            for (int i = 0; i < chars.length; i++) {
                int imax = i;
                for (int j = i; j < chars.length; j++) {
                    if (chars[imax] <= chars[j]) {
                        imax = j;
                    }
                }
                if (chars[imax] != chars[i]) {
                    // Swap and break
                    chars[i] ^= chars[imax];
                    chars[imax] ^= chars[i];
                    chars[i] ^= chars[imax];
                    break;
                }

            }
            return Integer.parseInt(String.valueOf(chars));
        }
    }
}
