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

    class RecursiveSolution {
        public boolean isInterleave(String s1, String s2, String s3) {
            return isInterleave(s1, s2, s3, 0, 0, 0);
        }

        private boolean isInterleave(String s1, String s2, String s3, int i1, int i2, int i3) {

            if (i1 >= s1.length())
                return isSame(s2, s3, i2, i3);

            if (i2 >= s2.length())
                return isSame(s1, s3, i1, i3);

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

        private boolean isSame(String s1, String s2, int i1, int i2) {
            for (; i1 < s1.length() && i2 < s2.length(); i1++, i2++)
                if (s1.charAt(i1) != s2.charAt(i2))
                    return false;
            return i1 == s1.length() && i2 == s2.length();
        }
    }
}
