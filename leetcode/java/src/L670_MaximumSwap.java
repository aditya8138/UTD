/**
 * Given a non-negative integer, you could swap two digits at most once to get the maximum valued
 * number. Return the maximum valued number you could get. <ul> <li>Example 1: Input: 2736, Output:
 * 7236. Explanation: Swap the number 2 and the number 7.</li> <li>Example 2: Input: 9973 Output:
 * 9973. Explanation: No swap.</li> </ul> Note: The given number is in the range [0, 108]
 */
public class L670_MaximumSwap {

    /**
     * Return the maximum valued number with one swap. First transform the number to char array. For
     * each char, find the largest char afterward. If the largest is the same as current char,
     * continue to next iteration. Otherwise, swap and break.
     *
     * @param num Number to be processed.
     * @return The maximum valued number with one swap.
     */
    public int maximumSwap(int num) {

        String s = String.valueOf(num);
        char[] chars = Integer.toString(num).toCharArray();

        for (int i = 0; i < chars.length; i++) {
            int imax = i;
            for (int j = i; j < chars.length; j++) {
                if (chars[imax] <= chars[j]) {
                    imax = j;
                }
            }
            if (chars[imax] != chars[i]) {
                // Swap and break
                chars[i] ^= chars[imax];
                chars[imax] ^= chars[i];
                chars[i] ^= chars[imax];
                break;
            }

        }
        return Integer.parseInt(String.valueOf(chars));
    }

}
