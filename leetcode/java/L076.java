import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

/**
 * Given a string S and a string T, find the minimum window in S which will contain all the
 * characters in T in complexity O(n).
 * <p>
 * For example, S = "ADOBECODEBANC" T = "ABC" Minimum window is "BANC".
 * <p>
 * Note: If there is no such window in S that covers all characters in T, return the empty string
 * "".
 * <p>
 * If there are multiple such windows, you are guaranteed that there will always be only one unique
 * minimum window in S.
 */
public class L076 {
    class Solution {
        public String minWindow(String s, String t) {

            int[] alpha = new int[52];

            for (char x : t.toCharArray())
                alpha[x - 'a']++;

            char[] sChars = s.toCharArray();

            int start = 0, end = 0;
            int i = 0;
            while (i < sChars.length)
                if (alpha[sChars[i] - 'a'] != 0)
                    break;




            return null;
        }
    }
}
