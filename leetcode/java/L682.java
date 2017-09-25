import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * You're now a baseball game point recorder.
 * <p>
 * Given a list of strings, each string can be one of the 4 following types: <ol> <li>Integer (one
 * round's score): Directly represents the number of points you get in this round.</li> <li>"+" (one
 * round's score): Represents that the points you get in this round are the sum of the last two
 * valid round's points.</li> <li>"D" (one round's score): Represents that the points you get in
 * this round are the doubled data of the last valid round's points.</li> <li>"C" (an operation,
 * which isn't a round's score): Represents the last valid round's points you get were invalid and
 * should be removed.</li> </ol> Each round's operation is permanent and could have an impact on the
 * round before and the round after.
 * <p>
 * You need to return the sum of the points you could get in all the rounds.
 * <p>
 * Example 1:
 * <pre>Input: ["5","2","C","D","+"]
 * Output: 30
 * Explanation:
 * Round 1: You could get 5 points. The sum is: 5.
 * Round 2: You could get 2 points. The sum is: 7.
 * Operation 1: The round 2's data was invalid. The sum is: 5.
 * Round 3: You could get 10 points (the round 2's data has been removed). The sum is: 15.
 * Round 4: You could get 5 + 10 = 15 points. The sum is: 30.</pre>
 * <p>
 * Example 2:
 * <pre>Input: ["5","-2","4","C","D","9","+","+"]
 * Output: 27
 * Explanation:
 * Round 1: You could get 5 points. The sum is: 5.
 * Round 2: You could get -2 points. The sum is: 3.
 * Round 3: You could get 4 points. The sum is: 7.
 * Operation 1: The round 3's data is invalid. The sum is: 3.
 * Round 4: You could get -4 points (the round 3's data has been removed). The sum is: -1.
 * Round 5: You could get 9 points. The sum is: 8.
 * Round 6: You could get -4 + 9 = 5 points. The sum is 13.
 * Round 7: You could get 9 + 5 = 14 points. The sum is 27.</pre>
 * <p>
 * Note: The size of the input list will be between 1 and 1000. Every integer represented in the
 * list will be between -30000 and 30000.
 */

public class L682 {
    @Test
    public void calPoints() throws Exception {
        String[] points = new String[]{"-60", "D", "-36", "30", "13", "C", "C", "-33", "53", "79"};

        assertEquals(-117, new Solution().calPoints(points));
    }

    class Solution {

        /**
         * The basic idea is to maintain a stack. If "c" occur, pop, otherwise, push based on
         * previous points. In the end, calculate the sum of the stack. Since the problem does not
         * require more function from stack. A simple stack implemented with an array is used.
         *
         * @param ops Points operations.
         * @return Total point in the match.
         */
        public int calPoints(String[] ops) {
            int[] point = new int[ops.length];
            int index = 0;

            for (String op : ops) {

                if (op.equals("C")) {
                    assert index > 0;
                    index--;
                    continue;
                }

                if (op.equals("D")) {
                    assert index > 0;
                    point[index] = point[index - 1] * 2;
                    index++;
                    continue;
                }

                if (op.equals("+")) {
                    assert index > 1;
                    point[index] = point[index - 1] + point[index - 2];
                    index++;
                    continue;
                }

                point[index] = Integer.valueOf(op);
                index++;
            }

            int points = 0;
            for (int i = 0; i < index; i++)
                points += point[i];

            return points;
        }
    }
}
