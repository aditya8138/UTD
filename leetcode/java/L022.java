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
