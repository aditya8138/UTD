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

public class L735 {
    @Test
    public void asteroidCollision() throws Exception {
        Solution solution = new Solution();

        int[] as = {-2, -2, -2, 2};

        for (int i : solution.asteroidCollision(as))
            System.out.println(i);

    }

    class Solution {
        public int[] asteroidCollision(int[] asteroids) {
            ArrayList<Integer> al = new ArrayList<>(asteroids.length);
            for (int i : asteroids)
                al.add(i);

            for (int i = 0; i < asteroids.length; i += 2) {
                asteroidCollisionL(al);
                asteroidCollisionR(al);
            }

            int[] ret = new int[al.size()];
            for (int j = 0; j < al.size(); j++)
                ret[j] = al.get(j);

            return ret;
        }

        private void asteroidCollisionL(ArrayList<Integer> asteroids) {
            int i = 0;
            while (i < asteroids.size() - 1) {
                if (asteroids.get(i) > 0 && asteroids.get(i + 1) < 0 && asteroids.get(i) + asteroids.get(i + 1) == 0) {
                    asteroids.remove(i + 1);
                    asteroids.remove(i);
                    continue;
                }

                if (asteroids.get(i) > 0 && asteroids.get(i + 1) < 0 && asteroids.get(i) + asteroids.get(i + 1) > 0) {
                    asteroids.remove(i + 1);
                    continue;
                }

                if (asteroids.get(i) > 0 && asteroids.get(i + 1) < 0 && asteroids.get(i) + asteroids.get(i + 1) < 0) {
                    asteroids.remove(i);
                    continue;
                }

                i++;
            }
        }

        private void asteroidCollisionR(ArrayList<Integer> asteroids) {
            int i = asteroids.size() - 1;
            while (i >= 1) {
                if (asteroids.get(i) < 0 && asteroids.get(i - 1) > 0 && asteroids.get(i) + asteroids.get(i - 1) == 0) {
                    asteroids.remove(i);
                    asteroids.remove(i - 1);
                    i -= 2;
                    continue;
                }

                if (asteroids.get(i) < 0 && asteroids.get(i - 1) > 0 && asteroids.get(i) + asteroids.get(i - 1) < 0) {
                    asteroids.remove(i - 1);
                    i--;
                    continue;
                }

                if (asteroids.get(i) < 0 && asteroids.get(i - 1) > 0 && asteroids.get(i) + asteroids.get(i - 1) > 0) {
                    asteroids.remove(i);
                    i--;
                    continue;
                }

                i--;
            }
        }
    }
}
