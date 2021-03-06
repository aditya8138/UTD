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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Given a 2D board and a word, find if the word exists in the grid. The word can be constructed
 * from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or
 * vertically neighboring. The same letter cell may not be used more than once.
 */

public class L079 {
    @Test
    public void exist() throws Exception {
        Solution solution = new Solution();
        char[][] board1 = {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};

        assertTrue(solution.exist(board1, "ABCCED"));
        assertTrue(solution.exist(board1, "BCESEEDFSA"));
        assertFalse(solution.exist(board1, "ABCB"));
        assertFalse(solution.exist(board1, "ABCBNFKS"));
    }

    class Solution {
        /**
         * Check if a word exist in the board. The function iterate through the board line by line.
         * For each character, treat it as the starting point and call the helper {@code
         * backtracking} function.
         *
         * @param board Board of characters available.
         * @param word  Word to be checked.
         * @return {@code True} if there exist a valid construction of letter of word, and {@code
         * False} otherwise.
         */
        public boolean exist(char[][] board, String word) {
            boolean[][] visited = new boolean[board.length][board[0].length];

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (backtracking(board, word, visited, i, j, 0))
                        return true;
                }
            }
            return false;
        }

        /**
         * Backtracking helper function. <ul> <li>Base case: If currently the word to be checked has
         * only one character, return if the character is matched with the character of board with
         * specific coordinate.</li> <li>Inductive case: If equal, then recursively check whether
         * the other four direction, i.e., north, south, east, west, whichever the board allows.
         * Before recursive call, mark current character as visited.</li> </ul>
         *
         * @param board   Board of characters available.
         * @param word    Word to be checked.
         * @param visited Boolean flag recording whether the character has been visited.
         * @param x       X-coordinate of current check character on board.
         * @param y       Y-coordinate of current check character on board.
         * @param i       Current checking index in the word.
         * @return {@code True} if there exist a valid construction of letter of word, and {@code
         * False}
         */
        private boolean backtracking(char[][] board, String word, boolean[][] visited, int x, int y, int i) {

            // Base case: If there is only one char left.
            if (i == word.length() - 1) {
                return board[x][y] == word.charAt(i);
            }

            // Otherwise, if current first letter of the string is not equal to board[x][y], return false.
            if (board[x][y] != word.charAt(i))
                return false;
            // Inductive case: Recursively check four direction if the boundary allows.
            visited[x][y] = true;
            if (x > 0 && !visited[x - 1][y] && backtracking(board, word, visited, x - 1, y, i + 1))
                return true;

            if (x < board.length - 1 && !visited[x + 1][y] && backtracking(board, word, visited, x + 1, y, i + 1))
                return true;

            if (y > 0 && !visited[x][y - 1] && backtracking(board, word, visited, x, y - 1, i + 1))
                return true;

            if (y < board[0].length - 1 && !visited[x][y + 1] && backtracking(board, word, visited, x, y + 1, i + 1))
                return true;

            visited[x][y] = false;
            return false;
        }

    }
}
