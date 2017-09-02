import org.junit.Test;

import static org.junit.Assert.*;

public class L079_WordSearchTest {
    @Test
    public void exist() throws Exception {
        L079_WordSearch solution = new L079_WordSearch();
        char[][] board1 = {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};

        assertTrue(solution.exist(board1, "ABCCED"));
        assertTrue(solution.exist(board1, "BCESEEDFSA"));
        assertFalse(solution.exist(board1, "ABCB"));
        assertFalse(solution.exist(board1, "ABCBNFKS"));
    }

}