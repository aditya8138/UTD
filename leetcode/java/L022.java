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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

/**
 * Given n pairs of parentheses, write a function to generate all combinations of well-formed
 * parentheses.
 * <p>
 * For example, given n = 3, a solution set is:
 */
public class L022 {
    @Test
    public void generateParenthesis() throws Exception {
        Solution solution = new Solution();
        String[] ret3 = {"((()))", "(()())", "(())()", "()(())", "()()()"};
        assertArrayEquals(solution.generateParenthesis(3).toArray(), ret3);

        String[] ret4 = {"(((())))", "((()()))", "((())())", "((()))()", "(()(()))", "(()()())", "(()())()",
                "(())(())", "(())()()", "()((()))", "()(()())", "()(())()", "()()(())", "()()()()"};
        assertArrayEquals(solution.generateParenthesis(4).toArray(), ret4);
    }

    class Solution {
        /**
         * DFS solution.
         *
         * @param n : number of parentheses to be generated.
         * @return : List of possible parentheses.
         */
        public List<String> generateParenthesis(int n) {
            List<String> list = new ArrayList<>();
            generateOneByOne("", list, n, n);
            return list;
        }

        /**
         * Helper function, recursively generate parentheses.
         *
         * @param sublist : Current status (like a stack).
         * @param list    : List of generated parentheses.
         * @param left    : number of left parentheses to be added.
         * @param right   : number of right parentheses to be added.
         */
        private void generateOneByOne(String sublist, List<String> list, int left, int right) {
            if (left > right) {
                return;
            }
            if (left > 0) {
                generateOneByOne(sublist + "(", list, left - 1, right);
            }
            if (right > 0) {
                generateOneByOne(sublist + ")", list, left, right - 1);
            }
            if (left == 0 && right == 0) {
                list.add(sublist);
                // return;
            }
        }
    }
}
