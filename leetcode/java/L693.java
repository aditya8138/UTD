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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * 693. Binary Number with Alternating Bits
 * <p>
 * Given a positive integer, check whether it has alternating bits: namely, if two adjacent bits
 * will always have different values.
 * <p>
 * Example 1: Input: 5 Output: True Explanation: The binary representation of 5 is: 101
 * <p>
 * Example 2: Input: 7 Output: False Explanation: The binary representation of 7 is: 111.
 * <p>
 * Example 3: Input: 11 Output: False Explanation: The binary representation of 11 is: 1011.
 * <p>
 * Example 4: Input: 10 Output: True Explanation: The binary representation of 10 is: 1010.
 */
public class L693 {
    @Test
    public void combine() throws Exception {
        Solution solution = new Solution();

        assertTrue(solution.hasAlternatingBits(5));
        assertFalse(solution.hasAlternatingBits(7));
        assertFalse(solution.hasAlternatingBits(11));
        assertTrue(solution.hasAlternatingBits(10));
    }

    class Solution {
        public boolean hasAlternatingBits(int n) {
            int ret = ((n >> 1) ^ n);
            return (ret + 1 & ret) == 0;
        }
    }
}
