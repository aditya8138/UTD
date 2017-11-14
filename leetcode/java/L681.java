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

/**
 * Given a time represented in the format "HH:MM", form the next closest time by reusing the current
 * digits. There is no limit on how many times a digit can be reused.
 * <p>
 * You may assume the given input string is always valid. For example, "01:34", "12:09" are all
 * valid. "1:34", "12:9" are all invalid.
 * <p>
 * Example 1:
 * <pre> Input: "19:34"
 * Output: "19:39"
 * Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39, which occurs 5
 * minutes later.  It is not 19:33, because this occurs 23 hours and 59 minutes later.</pre>
 * Example 2:
 * <pre>Input: "23:59"
 * Output: "22:22"
 * Explanation: The next closest time choosing from digits 2, 3, 5, 9, is 22:22. It may be assumed
 * that the returned time is next day's time since it is smaller than the input time
 * numerically.</pre>
 */

public class L681 {
    @Test
    public void calPoints() throws Exception {
        String time = "11:11";

        assertTrue(new Solution().nextClosestTime(time).equals("11:11"));
    }

    class Solution {

        /**
         * The idea is to increment time one minute each time, check whether the new timestamp meets
         * the requirement that each character comes from original time.
         *
         * @param time Original time.
         * @return Next closest time.
         */
        public String nextClosestTime(String time) {
            final int t1 = time.charAt(0) - '0';
            final int t2 = time.charAt(1) - '0';
            final int t3 = time.charAt(3) - '0';
            final int t4 = time.charAt(4) - '0';
            boolean[] a = new boolean[10];
            a[t1] = a[t2] = a[t3] = a[t4] = true;

            final int hour = t1 * 10 + t2;
            final int minute = t3 * 10 + t4;

            for (int m = (minute + 1) % 60, h = (hour + (m == 0 ? 1 : 0)) % 24;
                 h != hour || m != minute;
                 m = (m + 1) % 60, h = (h + (m == 0 ? 1 : 0)) % 24) {
                if (a[h / 10] && a[h % 10] && a[m / 10] && a[m % 10])
                    return "" + (h < 10 ? ("0" + h) : h) + ":" + (m < 10 ? ("0" + m) : m);
            }
            return time;
        }
    }
}
