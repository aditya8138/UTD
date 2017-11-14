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

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's (representing land)
 * connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are
 * surrounded by water.
 * <p>
 * Count the number of distinct islands. An island is considered to be the same as another if and
 * only if one island can be translated (and not rotated or reflected) to equal the other.
 * <p>
 * Example 1: [[11000], [11000], [00011], [00011]]. Given the above grid map, return 1.
 * <p>
 * Example 2: [[11011], [10000], [00001], [11011]]. Given the above grid map, return 3.
 * <p>
 * Notice that: 11 1 and 1 11 are considered different island shapes, because we do not consider
 * reflection / rotation.
 * <p>
 * Note: The length of each dimension in the given grid does not exceed 50.
 */
public class L694 {
    class Solution {
        public int numDistinctIslands(int[][] grid) {

            Set<String> ss = new HashSet<>();
            StringBuilder gs = new StringBuilder();

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 0) {
                        continue;
                    }
                    gs.setLength(0);
                    dfs(grid, i, j, 0, 0, gs);
                    ss.add(gs.toString());
                }
            }
            return ss.size();
        }

        void dfs(int[][] grid, final int sx, final int sy, int x, int y, StringBuilder gs) {
            if (sx + x >= grid.length || sx + x < 0 || sy + y >= grid[0].length || sy + y < 0)
                return;

            if (grid[sx + x][sy + y] == 0)
                return;

            grid[sx + x][sy + y] = 0;

            /*
             * A key point is how to hash the 'Point', here a string of point's x,y sequence is used.
             * Note that, in order for this to work, the sequence of points in same pattern island
             * must be the same, which lead to the next comment block.
             */
            gs.append(x).append(y);

            /*
             * Originally, I sorted the 'Point's so that the sequence in the list is the same for
             * same pattern island. However, the operation is redundant in the first place.
             * A key insight is: since the following recursion steps are fixed, the output sequence
             * would automatically be the same for same pattern island.
             */
            dfs(grid, sx, sy, x + 1, y, gs);
            dfs(grid, sx, sy, x, y + 1, gs);
            dfs(grid, sx, sy, x - 1, y, gs);
            dfs(grid, sx, sy, x, y - 1, gs);
        }
    }

    @Test
    public void combine() throws Exception {
        Solution solution = new Solution();

        int[][] area = {
                {1, 1, 0, 1, 1},
                {1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1},
                {1, 1, 0, 1, 1}
        };

        assertEquals(3, solution.numDistinctIslands(area));
    }
}
