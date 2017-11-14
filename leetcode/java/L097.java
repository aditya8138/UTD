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

    class OptimizeDPSolution {
        public boolean isInterleave(String s1, String s2, String s3) {

            int l1 = s1.length();
            int l2 = s2.length();
            int l3 = s3.length();

            if (l3 != l1 + l2)
                return false;

            boolean[][] m = new boolean[l2 + 1][l1 + 1];

            m[l2][l1] = true;

            for (int i2 = l2 - 1; i2 >= 0; i2--) {
                m[i2][l1] = (s3.charAt(l3 - l2 + i2) == s2.charAt(i2) && m[i2 + 1][l1]);
            }

            for (int i1 = l1 - 1; i1 >= 0; i1--) {
                m[l2][i1] = (s3.charAt(l3 - l1 + i1) == s1.charAt(i1) && m[l2][i1 + 1]);
            }

            for (int i2 = l2 - 1; i2 >= 0; i2--) {
                for (int i1 = l1 - 1; i1 >= 0; i1--) {
                    m[i2][i1] = (s3.charAt(l3 - l1 + i1 - l2 + i2) == s1.charAt(i1) && m[i2][i1 + 1])
                            || (s3.charAt(l3 - l1 + i1 - l2 + i2) == s2.charAt(i2) && m[i2 + 1][i1]);
                }
            }

            return m[0][0];
        }
    }

    class DirectDPSolution {
        public boolean isInterleave(String s1, String s2, String s3) {
            int l1 = s1.length();
            int l2 = s2.length();
            int l3 = s3.length();
            boolean[][][] m = new boolean[l3 + 1][l2 + 1][l1 + 1];

            m[l3][l2][l1] = true;

            for (int i3 = l3 - 1; i3 >= 0; i3--) {
                for (int i2 = l2 - 1; i2 >= 0; i2--) {
                    m[i3][i2][l1] = (s3.charAt(i3) == s2.charAt(i2) && m[i3 + 1][i2 + 1][l1]);
                }
                for (int i1 = l1 - 1; i1 >= 0; i1--) {
                    m[i3][l2][i1] = (s3.charAt(i3) == s1.charAt(i1) && m[i3 + 1][l2][i1 + 1]);
                }
            }

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

    class RecursiveSolution {
        public boolean isInterleave(String s1, String s2, String s3) {
            return isInterleave(s1, s2, s3, 0, 0, 0);
        }

        private boolean isInterleave(String s1, String s2, String s3, int i1, int i2, int i3) {

            if (i1 >= s1.length() && i2 >= s2.length())
                return i3 >= s3.length();

            if (i3 >= s3.length())
                return false;

            if (i1 >= s1.length())
                return s2.charAt(i2) == s3.charAt(i3) && isInterleave(s1, s2, s3, i1, i2 + 1, i3 + 1);

            if (i2 >= s2.length())
                return s1.charAt(i1) == s3.charAt(i3) && isInterleave(s1, s2, s3, i1 + 1, i2, i3 + 1);

            final char c1 = s1.charAt(i1);
            final char c2 = s2.charAt(i2);
            final char c3 = s3.charAt(i3);

            if (c3 == c1 && c3 == c2)
                return isInterleave(s1, s2, s3, i1 + 1, i2, i3 + 1)
                        || isInterleave(s1, s2, s3, i1, i2 + 1, i3 + 1);

            if (c3 == c1)
                return isInterleave(s1, s2, s3, i1 + 1, i2, i3 + 1);

            if (c3 == c2)
                return isInterleave(s1, s2, s3, i1, i2 + 1, i3 + 1);

            return false;
        }
    }
}
