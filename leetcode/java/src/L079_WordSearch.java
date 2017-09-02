/**
 * Given a 2D board and a word, find if the word exists in the grid. The word can be constructed
 * from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or
 * vertically neighboring. The same letter cell may not be used more than once.
 */

public class L079_WordSearch {
    /**
     * Check if a word exist in the board. The function iterate through the board line by line. For
     * each character, treat it as the starting point and call the helper {@code backtracking}
     * function.
     *
     * @param board Board of characters available.
     * @param word  Word to be checked.
     * @return {@code True} if there exist a valid construction of letter of word, and {@code False}
     * otherwise.
     */
    public boolean exist(char[][] board, String word) {
        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (backtracking(board, word, visited, i, j))
                    return true;
            }
        }
        return false;
    }

    /**
     * Backtracking helper function. <ul> <li>Base case: If currently the word to be checked has
     * only one character, return if the character is matched with the character of board with
     * specific coordinate.</li> <li>Inductive case: If equal, then recursively check whether the
     * other four direction, i.e., north, south, east, west, whichever the board allows. Before
     * recursive call, mark current character as visited.</li> </ul>
     *
     * @param board   Board of characters available.
     * @param word    Word to be checked.
     * @param visited Boolean flag recording whether the character has been visited.
     * @param x       X-coordinate of current check character on board.
     * @param y       Y-coordinate of current check character on board.
     * @return {@code True} if there exist a valid construction of letter of word, and {@code False}
     */
    private boolean backtracking(char[][] board, String word, boolean[][] visited, int x, int y) {

        // Base case: If there is only one char left.
        if (word.length() == 1) {
            return board[x][y] == word.charAt(0);
        }

        // Otherwise, if current first letter of the string is not equal to board[x][y], return false.
        if (board[x][y] != word.charAt(0))
            return false;
        // Inductive case: Recursively check four direction if the boundary allows.
        visited[x][y] = true;
        if (x > 0 && !visited[x - 1][y] && backtracking(board, word.substring(1), visited, x - 1, y))
            return true;

        if (x < board.length - 1 && !visited[x + 1][y] && backtracking(board, word.substring(1), visited, x + 1, y))
            return true;

        if (y > 0 && !visited[x][y - 1] && backtracking(board, word.substring(1), visited, x, y - 1))
            return true;

        if (y < board[0].length - 1 && !visited[x][y + 1] && backtracking(board, word.substring(1), visited, x, y + 1))
            return true;

        visited[x][y] = false;
        return false;
    }
}
