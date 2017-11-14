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

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * <h1> 443. String Compression</h1>
 * <p>
 * Given an array of characters, compress it in-place. The length after compression must always be
 * smaller than or equal to the original array. Every element of the array should be a character
 * (not int) of length 1. After you are done modifying the input array in-place, return the new
 * length of the array.
 * <p>
 * <pre>
 * Example 1:
 * Input:
 * ["a","a","b","b","c","c","c"]
 * Output:
 * Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]
 * Explanation:
 * "aa" is replaced by "a2". "bb" is replaced by "b2". "ccc" is replaced by "c3".
 * </pre>
 * <pre>
 * Example 2:
 * Input:
 * ["a"]
 * Output:
 * Return 1, and the first 1 characters of the input array should be: ["a"]
 * Explanation:
 * Nothing is replaced.
 * </pre>
 * <pre>
 * Example 3:
 * Input:
 * ["a","b","b","b","b","b","b","b","b","b","b","b","b"]
 * Output:
 * Return 4, and the first 4 characters of the input array should be: ["a","b","1","2"].
 * Explanation:
 * Since the character "a" does not repeat, it is not compressed. "bbbbbbbbbbbb" is replaced by
 * "b12".
 * Notice each digit has it's own entry in the array.
 * </pre>
 * <p>
 * Note: All characters have an ASCII value in [35, 126]. 1 <= len(chars) <= 1000.
 */
public class L443 {
    @Test
    public void compress() throws Exception {
        char[] chars1 = {'a', 'a', 'b', 'b', 'c', 'c', 'c'};
        char[] chars2 = {'a'};
        char[] chars3 = {'a', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b'};

        Solution solution = new Solution();

        int ret1 = solution.compress(chars1);
        assertEquals(6, ret1);
        char[] chars1after = Arrays.copyOf(chars1, ret1);
        assertArrayEquals(new char[]{'a', '2', 'b', '2', 'c', '3'}, chars1after);

        int ret2 = solution.compress(chars2);
        assertEquals(1, ret2);
        char[] chars2after = Arrays.copyOf(chars2, ret2);
        assertArrayEquals(new char[]{'a'}, chars2after);

        int ret3 = solution.compress(chars3);
        assertEquals(4, ret3);
        char[] chars3after = Arrays.copyOf(chars3, ret3);
        assertArrayEquals(new char[]{'a', 'b', '1', '5'}, chars3after);
    }

    class Solution {
        public int compress(char[] chars) {
            int i = 0, j = 0;
            char c;
            while (i < chars.length) {

                c = chars[i];

                int k = 0;
                while (i + k < chars.length && chars[i + k] == c) {
                    k++;
                }

                i += k;

                chars[j] = c;
                j++;

                if (k <= 1)
                    continue;

                if (k >= 100) {
                    chars[j] = (char) ('0' + (k / 100));
                    k = k % 100;
                    j++;
                }

                if (k >= 10) {
                    chars[j] = (char) ('0' + (k / 10));
                    k = k % 10;
                    j++;
                }

                if (k >= 0) {
                    chars[j] = (char) ('0' + k);
                    j++;
                }
            }

            return j;
        }
    }
}
