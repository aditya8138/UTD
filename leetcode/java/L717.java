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
 * <h1>717. 1-bit and 2-bit Characters</h1>
 * <p>
 * We have two special characters. The first character can be represented by one bit 0. The second
 * character can be represented by two bits (10 or 11).
 * <p>
 * Now given a string represented by several bits. Return whether the last character must be a
 * one-bit character or not. The given string will always end with a zero.
 * <p>
 * Example 1:
 * <pre>
 * Input:
 * bits = [1, 0, 0]
 * Output: True
 * Explanation:
 * The only way to decode it is two-bit character and one-bit character. So the last character is
 * one-bit character.
 * </pre>
 * <p>
 * Example 2:
 * <pre>
 * Input:
 * bits = [1, 1, 1, 0]
 * Output: False
 * Explanation:
 * The only way to decode it is two-bit character and two-bit character. So the last character is
 * NOT one-bit character.
 * </pre>
 * Note: 1 <= len(bits) <= 1000. bits[i] is always 0 or 1.
 */
public class L717 {
    @Test
    public void isOneBitCharacter() throws Exception {
        Solution solution = new Solution();

        int[] bits1 = {1, 0, 0};
        int[] bits2 = {1, 1, 1, 0};

        assertTrue(solution.isOneBitCharacter(bits1));
        assertFalse(solution.isOneBitCharacter(bits2));
    }

    class Solution {
        public boolean isOneBitCharacter(int[] bits) {
            boolean ret = true;
            for (int i = 0; i < bits.length - 1; i++) {
                if (ret && bits[i] == 1)
                    ret = false;
                else if (ret == false)
                    ret = true;
            }
            return ret;
        }
    }
}
