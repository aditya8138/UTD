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

import static org.junit.Assert.assertArrayEquals;

import java.util.LinkedList;
import java.util.List;

/**
 * <h1>728. Self Dividing Numbers</h1>
 * <p>
 * A self-dividing number is a number that is divisible by every digit it contains. For example, 128
 * is a self-dividing number because 128 % 1 == 0, 128 % 2 == 0, and 128 % 8 == 0. Also, a
 * self-dividing number is not allowed to contain the digit zero. Given a lower and upper number
 * bound, output a list of every possible self dividing number, including the bounds if possible.
 * <p>
 * Example 1: Input: left = 1, right = 22 Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]
 * <p>
 * Note: The boundaries of each input argument are 1 <= left <= right <= 10000.
 */
public class L728 {
    @Test
    public void selfDividingNumbers() throws Exception {
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22},
                new Solution().selfDividingNumbers(1, 22).toArray(new Integer[0]));
    }

    class Solution {
        public List<Integer> selfDividingNumbers(int left, int right) {
            List<Integer> ret = new LinkedList<>();

            for (int i = left; i <= right; i++) {
                if (selfDividingNumbers(i))
                    ret.add(i);
            }

            return ret;
        }

        private boolean selfDividingNumbers(final int x) {
            int mod, div = x;

            while (div > 0) {
                mod = div % 10;
                div /= 10;

                if (mod == 0 || x / mod * mod != x)
                    return false;
            }

            return true;
        }
    }
}
