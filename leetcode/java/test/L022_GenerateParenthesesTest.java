import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

public class L022_GenerateParenthesesTest {
    @Test
    public void generateParenthesis() throws Exception {
        L022_GenerateParentheses solution = new L022_GenerateParentheses();
        String[] ret3 = {"((()))", "(()())", "(())()", "()(())", "()()()"};
        assertArrayEquals(solution.generateParenthesis(3).toArray(), ret3);

        String[] ret4 = {"(((())))", "((()()))", "((())())", "((()))()", "(()(()))", "(()()())", "(()())()",
                "(())(())", "(())()()", "()((()))", "()(()())", "()(())()", "()()(())", "()()()()"};
        assertArrayEquals(solution.generateParenthesis(4).toArray(), ret4);
    }

}