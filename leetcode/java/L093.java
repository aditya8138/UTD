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

import java.util.LinkedList;
import java.util.List;

/**
 * <h1>093. Restore IP Addresses</h1>
 * <p>
 * Given a string containing only digits, restore it by returning all possible valid IP address
 * combinations.
 * <p>
 * For example: Given "25525511135", return ["255.255.11.135", "255.255.111.35"]. (Order does not
 * matter)
 */
public class L093 {
    @Test
    public void restoreIpAddresses() throws Exception {

        Solution solution = new Solution();
        System.out.println(solution.restoreIpAddresses("10101010"));

    }

    class Solution {
        public List<String> restoreIpAddresses(String s) {

            List<String> ret = new LinkedList<>();

            restoreIpAddresses(s.toCharArray(), 0, 4, ret, "");

            return ret;
        }

        /**
         * The key insight is the so-called functional way to write code. No variable except {@code
         * ips} is mutable. All changed was done during passing parameters.
         *
         * @param sc    Original string as char array.
         * @param index Current index to consider.
         * @param k     Remain digit.
         * @param ips   IP set.
         * @param ip    Current IP segment.
         */
        private void restoreIpAddresses(final char[] sc,
                                        final int index,
                                        final int k,
                                        List<String> ips,
                                        final String ip) {
            final int left = sc.length - index;

            if (left < 0 || left > k * 3 || left < k || k > 4)
                return;

            if (k == 0) {
                ips.add(ip.substring(0, ip.length() - 1));
                return;
            }

            restoreIpAddresses(sc, index + 1, k - 1, ips, ip + String.valueOf(sc[index]) + ".");

            if (left >= 2) {
                final int d = charToInt(sc[index], sc[index + 1]);
                if (d >= 10)
                    restoreIpAddresses(sc, index + 2, k - 1, ips, ip + String.valueOf(charToInt(sc[index], sc[index + 1])) + ".");
            }

            if (left >= 3) {
                final int d = charToInt(sc[index], sc[index + 1], sc[index + 2]);
                if (d <= 255 && d >= 100)
                    restoreIpAddresses(sc, index + 3, k - 1, ips, ip + String.valueOf(d) + ".");
            }
        }

        /**
         * Helper functions to transform char to integer.
         */

        private int charToInt(char z) {
            return charToInt('0', '0', z);
        }

        private int charToInt(char y, char z) {
            return charToInt('0', y, z);
        }

        private int charToInt(char x, char y, char z) {
            return (x - '0') * 100 + (y - '0') * 10 + (z - '0');
        }
    }
}
