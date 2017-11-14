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
 * <h1>089. Gray Code</h1>
 * <p>
 * The gray code is a binary numeral system where two successive values differ in only one bit.
 * Given a non-negative integer n representing the total number of bits in the code, print the
 * sequence of gray code. A gray code sequence must begin with 0.
 * <p>
 * For example, given n = 2, return [0,1,3,2]. Its gray code sequence is:
 * <pre>00 - 0
 * 01 - 1
 * 11 - 3
 * 10 - 2</pre>
 * <p>
 * Note: 1) For a given n, a gray code sequence is not uniquely defined. 2) For example, [0,2,3,1]
 * is also a valid gray code sequence according to the above definition. 3) For now, the judge is
 * able to judge based on one instance of gray code sequence. Sorry about that.
 * <p>
 * For more infomation about gray code, refer to https://en.wikipedia.org/wiki/Gray_code.
 */
public class L089 {

    @Test
    public void grayCode() throws Exception {
        assertArrayEquals(new Integer[]{0, 1, 3, 2, 6, 7, 5, 4, 12, 13, 15, 14, 10, 11, 9, 8},
                new Solution().grayCode(4).toArray(new Integer[0]));
    }

    class Solution {
        public List<Integer> grayCode(int n) {
            List<Integer> result = new LinkedList<>();
            for (int i = 0; i < 1 << n; i++)
                result.add(i ^ i >> 1);
            return result;
        }
    }
}

