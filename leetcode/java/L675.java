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

public class L675 {

    @Test
    public void cutOffTree() throws Exception {
        List<List<Integer>> forest1 = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(0, 0, 4),
                Arrays.asList(7, 6, 5)
        );

        Solution solution = new Solution();
        assertEquals(6, solution.cutOffTree(forest1));


        List<List<Integer>> forest2 = Arrays.asList(
                Arrays.asList(54581641, 64080174, 24346381, 69107959),
                Arrays.asList(86374198, 61363882, 68783324, 79706116),
                Arrays.asList(668150, 92178815, 89819108, 94701471),
                Arrays.asList(83920491, 22724204, 46281641, 47531096),
                Arrays.asList(89078499, 18904913, 25462145, 60813308)
        );
        assertEquals(57, solution.cutOffTree(forest2));
    }

    public static class WeightedTuple implements Comparable<WeightedTuple> {
        public int x;
        public int y;
        public int weight;

        public WeightedTuple(int x, int y, int weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }

        @Override
        public int compareTo(WeightedTuple o) {
            if (this.weight > o.weight)
                return 1;
            if (this.weight == o.weight)
                return 0;
            return -1;
        }
    }

    class Solution {
        public int cutOffTree(List<List<Integer>> forest) {
            boolean[][] been = new boolean[forest.size()][forest.get(0).size()];
            Integer count = 0;
            for (List<Integer> l : forest)
                for (Integer i : l)
                    if (i > 0)
                        count++;

            int maxx = forest.size();
            int maxy = forest.get(0).size();
            int steps = 0;

            PriorityQueue<WeightedTuple> tree = new PriorityQueue<>();
            Queue<WeightedTuple> traverse = new ArrayDeque<>();

            int height = 0;
            if (forest.get(0).get(0) > height) {
                tree.offer(new WeightedTuple(0, 0, forest.get(0).get(0)));
                traverse.offer(new WeightedTuple(0, 0, forest.get(0).get(0)));
            }

            WeightedTuple cur, next;

            int prex = 0;
            int prey = 0;

            while (traverse.peek() != null) {
                cur = traverse.poll();
                count--;
                been[cur.x][cur.y] = true;
//            height = cur.weight;
//            steps += Math.abs(cur.x - prex + cur.y - prey);
                prex = cur.x;
                prey = cur.y;

                if (cur.x + 1 < maxx && !been[cur.x + 1][cur.y]) {
                    next = new WeightedTuple(cur.x + 1, cur.y, forest.get(cur.x + 1).get(cur.y));
                    if (forest.get(cur.x + 1).get(cur.y) > 0)
                        tree.offer(next);
                    traverse.offer(next);
                }
                if (cur.x - 1 < maxx && !been[cur.x - 1][cur.y]) {
                    next = new WeightedTuple(cur.x - 1, cur.y, forest.get(cur.x - 1).get(cur.y));
                    if (forest.get(cur.x - 1).get(cur.y) > 0)
                        tree.offer(next);
                    traverse.offer(next);
                }
                if (cur.y + 1 < maxy && !been[cur.x][cur.y + 1]) {
                    next = new WeightedTuple(cur.x, cur.y + 1, forest.get(cur.x).get(cur.y + 1));
                    if (forest.get(cur.x).get(cur.y + 1) > 0)
                        tree.offer(next);
                    traverse.offer(next);
                }
                if (cur.y - 1 < maxy && !been[cur.x][cur.y - 1]) {
                    next = new WeightedTuple(cur.x, cur.y - 1, forest.get(cur.x).get(cur.y - 1));
                    if (forest.get(cur.x).get(cur.y - 1) > 0)
                        tree.offer(next);
                    traverse.offer(next);
                }
//            if (cur.x + 1 < maxx && !been[cur.x+1][cur.y] && forest.get(cur.x+1).get(cur.y) > 0) {
//                next.offer(new WeightedTuple(cur.x + 1, cur.y, forest.get(cur.x + 1).get(cur.y)));
//                traverse.offer(new WeightedTuple(cur.x + 1, cur.y, forest.get(cur.x + 1).get(cur.y)));
//            }
//            if (cur.x - 1 >= 0 && !been[cur.x-1][cur.y] && forest.get(cur.x-1).get(cur.y) > 0) {
//                next.add(new WeightedTuple(cur.x - 1, cur.y, forest.get(cur.x - 1).get(cur.y)));
//            }
//            if (cur.y + 1 < maxy && !been[cur.x][cur.y+1] && forest.get(cur.x).get(cur.y+1) > 0) {
//                next.add(new WeightedTuple(cur.x, cur.y + 1, forest.get(cur.x).get(cur.y + 1)));
//            }
//            if (cur.y - 1 >= 0 && !been[cur.x][cur.y-1] && forest.get(cur.x).get(cur.y-1) > 0) {
//                next.add(new WeightedTuple(cur.x, cur.y - 1, forest.get(cur.x).get(cur.y - 1)));
//            }
            }

            if (count == 0)
                return steps;

            return -1;
        }

    }
}
