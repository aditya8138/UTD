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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * <h1>097. Interleaving String</h1>
 * <p>
 * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
 * <p>
 * For example, Given: s1 = "aabcc", s2 = "dbbca",
 * <p>
 * When s3 = "aadbbcbcac", return true. When s3 = "aadbbbaccc", return false.
 */
public class L097 {
    private String s1 = "bbbbbabbbbabaababaaaabbababbaaabbabbaaabaaaaababbbababbbbbabbbbababbabaabababbbaabababababbbaaababaa";
    private String s2 = "babaaaabbababbbabbbbaabaabbaabbbbaabaaabaababaaaabaaabbaaabaaaabaabaabbbbbbbbbbbabaaabbababbabbabaab";
    private String s3 = "babbbabbbaaabbababbbbababaabbabaabaaabbbbabbbaaabbbaaaaabbbbaabbaaabababbaaaaaabababbababaababbababbbababbbbaaaabaabbabbaaaaabbabbaaaabbbaabaaabaababaababbaaabbbbbabbbbaabbabaabbbbabaaabbababbabbabbab";
    private String s3p = "bbbbbabbbbababaaaabbababbbabbbbaabaabbaabaababaaaabbababbaaabbabbaaabaabbbbaabaaabaababaaaabaaabbaaabaaaaababbbababbbbbabbbbababbabaabababaabaabaabbbbbbbbbbbabaaabbababbabbabaabbbaabababababbbaaababaa";

    @Test
    public void OPisInterleave() throws Exception {
        OptimizeDPSolution optimizeDPSolution = new OptimizeDPSolution();
        assertFalse(optimizeDPSolution.isInterleave(s1, s2, s3));
        assertTrue(optimizeDPSolution.isInterleave(s1, s2, s3p));
    }

    @Test
    public void DPisInterleave() throws Exception {
        DirectDPSolution directDPSolution = new DirectDPSolution();
        assertFalse(directDPSolution.isInterleave(s1, s2, s3));
        assertTrue(directDPSolution.isInterleave(s1, s2, s3p));
    }

    @Test
    public void REisInterleave() throws Exception {
        RecursiveSolution recursiveSolution = new RecursiveSolution();
        assertFalse(recursiveSolution.isInterleave(s1, s2, s3));
        assertTrue(recursiveSolution.isInterleave(s1, s2, s3p));
    }

    /*
     * Since every recursive call, i3 would increase, it's pointless to store past value.
     * Storing only most current one would be sufficient.
     */
    class OptimizeDPSolution {
        public boolean isInterleave(String s1, String s2, String s3) {

            int l1 = s1.length();
            int l2 = s2.length();
            int l3 = s3.length();

            if (l3 != l1 + l2)
                return false;

            boolean[][] m = new boolean[l2 + 1][l1 + 1];

            m[l2][l1] = true;

            for (int i2 = l2 - 1; i2 >= 0; i2--)
                m[i2][l1] = (s3.charAt(l3 - l2 + i2) == s2.charAt(i2) && m[i2 + 1][l1]);

            for (int i1 = l1 - 1; i1 >= 0; i1--)
                m[l2][i1] = (s3.charAt(l3 - l1 + i1) == s1.charAt(i1) && m[l2][i1 + 1]);

            for (int i2 = l2 - 1; i2 >= 0; i2--)
                for (int i1 = l1 - 1; i1 >= 0; i1--)
                    m[i2][i1] = (s3.charAt(l3 - l1 + i1 - l2 + i2) == s1.charAt(i1) && m[i2][i1 + 1])
                            || (s3.charAt(l3 - l1 + i1 - l2 + i2) == s2.charAt(i2) && m[i2 + 1][i1]);

            return m[0][0];
        }
    }

    /*
     * Translate intuitive recursive solution to DP directly,
     * since all i based on strictly larger values.
     */
    class DirectDPSolution {
        public boolean isInterleave(String s1, String s2, String s3) {
            int l1 = s1.length();
            int l2 = s2.length();
            int l3 = s3.length();
            boolean[][][] m = new boolean[l3 + 1][l2 + 1][l1 + 1];

            m[l3][l2][l1] = true;

            // Translate base case.
            for (int i3 = l3 - 1; i3 >= 0; i3--) {
                for (int i2 = l2 - 1; i2 >= 0; i2--)
                    m[i3][i2][l1] = (s3.charAt(i3) == s2.charAt(i2) && m[i3 + 1][i2 + 1][l1]);
                for (int i1 = l1 - 1; i1 >= 0; i1--)
                    m[i3][l2][i1] = (s3.charAt(i3) == s1.charAt(i1) && m[i3 + 1][l2][i1 + 1]);
            }

            // Translate recursion phase.
            for (int i3 = l3 - 1; i3 >= 0; i3--) {
                for (int i2 = l2 - 1; i2 >= 0; i2--) {
                    for (int i1 = l1 - 1; i1 >= 0; i1--) {
                        if (s3.charAt(i3) == s1.charAt(i1) && s3.charAt(i3) == s2.charAt(i2))
                            m[i3][i2][i1] = m[i3 + 1][i2 + 1][i1] || m[i3 + 1][i2][i1 + 1];

                        else if (s3.charAt(i3) == s1.charAt(i1))
                            m[i3][i2][i1] = m[i3 + 1][i2][i1 + 1];

                        else if (s3.charAt(i3) == s2.charAt(i2))
                            m[i3][i2][i1] = m[i3 + 1][i2 + 1][i1];

                        else
                            ;
                    }
                }
            }

            return m[0][0][0];
        }
    }

    /*
     * Original intuitive recursive solution, based on following rules:
     *
     * Base case:
     * 1. If s1 or s2 exhausted, s3 should be exhausted.
     * 2. If s3 is exhausted, while s1 or s2 not, return false.
     *
     * Recursion phase:
     * 1. If s1 exhausted, all remaining s2 and s3 should be the same.
     * 2. If s2 exhausted, all remaining s1 and s3 should be the same.
     * 3. Otherwise,
     *    a. if char is the same for both s3/s1 and s3/s2, increment index of
     *       either s1 or s2 to recurse.
     *    b. if char is the same for only s3/s1, increment index of s1 and recurse.
     *    c. if char is the same for only s3/s2, increment index of s2 and recurse.
     *    d. otherwise, false.
     */
    class RecursiveSolution {
        public boolean isInterleave(String s1, String s2, String s3) {
            return isInterleave(s1, s2, s3, 0, 0, 0);
        }

        private boolean isInterleave(String s1, String s2, String s3, int i1, int i2, int i3) {

            // Base case 1.
            if (i1 >= s1.length() && i2 >= s2.length())
                return i3 >= s3.length();

            // Base case 2.
            if (i3 >= s3.length())
                return false;

            // Recursive phase 1.
            if (i1 >= s1.length())
                return s2.charAt(i2) == s3.charAt(i3) && isInterleave(s1, s2, s3, i1, i2 + 1, i3 + 1);

            // Recursive phase 2.
            if (i2 >= s2.length())
                return s1.charAt(i1) == s3.charAt(i3) && isInterleave(s1, s2, s3, i1 + 1, i2, i3 + 1);

            final char c1 = s1.charAt(i1);
            final char c2 = s2.charAt(i2);
            final char c3 = s3.charAt(i3);

            // Recursive phase 3.a
            if (c3 == c1 && c3 == c2)
                return isInterleave(s1, s2, s3, i1 + 1, i2, i3 + 1)
                        || isInterleave(s1, s2, s3, i1, i2 + 1, i3 + 1);

            // Recursive phase 3.b
            if (c3 == c1)
                return isInterleave(s1, s2, s3, i1 + 1, i2, i3 + 1);

            // Recursive phase 3.c
            if (c3 == c2)
                return isInterleave(s1, s2, s3, i1, i2 + 1, i3 + 1);

            // Recursive phase 3.d
            return false;
        }
    }
}
