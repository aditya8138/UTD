import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 695. Max Area of Island
 * <p>
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land)
 * connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are
 * surrounded by water.
 * <p>
 * Find the maximum area of an island in the given 2D array. (If there is no island, the maximum
 * area is 0.)
 * <p>
 * Example 1:
 * <pre>
 * [[0,0,1,0,0,0,0,1,0,0,0,0,0],
 * [0,0,0,0,0,0,0,1,1,1,0,0,0],
 * [0,1,1,0,1,0,0,0,0,0,0,0,0],
 * [0,1,0,0,1,1,0,0,1,0,1,0,0],
 * [0,1,0,0,1,1,0,0,1,1,1,0,0],
 * [0,0,0,0,0,0,0,0,0,0,1,0,0],
 * [0,0,0,0,0,0,0,1,1,1,0,0,0],
 * [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 * </pre>
 * Given the above grid, return 6. Note the answer is not 11, because the island must be connected
 * 4-directionally.
 * <p>
 * Example 2: [[0,0,0,0,0,0,0,0]] Given the above grid, return 0.
 * <p>
 * Note: The length of each dimension in the given grid does not exceed 50.
 */
public class L695 {
    @Test
    public void combine() throws Exception {
        Solution solution = new Solution();

        int[][] area = {
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
        };

        assertEquals(6, solution.maxAreaOfIsland(area));
    }

    class Solution {
        public int maxAreaOfIsland(int[][] grid) {
            int max = 0;
            int tmp;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {

                    if (grid[i][j] == 0)
                        continue;

                    tmp = dfs(grid, i, j);
                    if (tmp > max)
                        max = tmp;

                }
            }
            return max;
        }

        private int dfs(int[][] grid, int x, int y) {
            if (x >= grid.length || x < 0 || y >= grid[0].length || y < 0)
                return 0;

            if (grid[x][y] == 0)
                return 0;

            grid[x][y] = 0;
            return 1 + dfs(grid, x + 1, y) + dfs(grid, x, y + 1)
                    + dfs(grid, x - 1, y) + dfs(grid, x, y - 1);
        }
    }

}

