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

import static org.junit.Assert.assertArrayEquals;

/**
 * We are given a "tree" in the form of a 2D-array, with <b>distinct</b> values for each node.
 * <p>
 * In the given 2D-array, each element pair <b>[u, v]</b> represents that <b>v</b> is a child of
 * <b>u</b> in the tree.
 * <p>
 * We can remove <b>exactly</b> one redundant pair in this "tree" to make the result a tree.
 * <p>
 * You need to find and output such a pair. If there are multiple answers for this question, output
 * the one appearing last in the 2D-array. There is always at least one answer.
 * <p>
 * Example 1: Input: [[1,2], [1,3], [2,3]] Output: [2,3]
 * <p>
 * Example 2: Input: [[1,2], [1,3], [3,1]] Output: [3,1]
 */
public class L684 {

    @Test
    public void findRedundantConnection() throws Exception {
        int[][] edges = new int[][]{
                {5, 2},
                {3, 4},
                {4, 3},
                {5, 3},
                {1, 3}
        };

        int[] ret = new Solution().findRedundantConnection(edges);

        assertArrayEquals(new int[]{4, 3}, ret);
    }

    class Solution {
        /**
         * The problem can be easily solved with union-find data structure.
         *
         * @param edges Edges in the graph.
         * @return The redundant edge.
         */
        public int[] findRedundantConnection(int[][] edges) {
            int[] u = new int[2000];
            for (int i = 0; i < 2000; i++)
                u[i] = i;

            int last = -1;

            for (int i = 0; i < edges.length; i++) {
                final int a = get(u, edges[i][0]);
                final int b = get(u, edges[i][1]);

                // Two vertex are connected, mark index.
                if (a == b)
                    last = i;
                else {
                    if (a > b)
                        u[a] = b;
                    else
                        u[b] = a;
                }
            }

            return edges[last];
        }

        int get(int[] a, int i) {
            while (a[i] != i)
                i = a[i];
            return i;
        }
    }

}
