import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

public class L077 {
    static class Solution {
        /**
         * Helper function to generate combinations. Basic idea is generate combination including
         * current integer, by recursively call combine with s+1 and k-1. And recursively generate
         * combination excluding current integer. Finally, return the union of two list.
         * <p>
         * Note that, this solution is designed to be transformed to DP. Each recursive call is
         * strictly rely on a larger s and a less or equal k. By memoizing the result of each
         * recursive call, might reduce time complexity with cost of some increase in space
         * complexity. However, during implementation, I realize that Java do not support generic
         * type array. Statement <code>List&lt;List&lt;Integer&gt;&gt;[][] m</code> can never be
         * initialized.
         *
         * @param s : Current start integer in n.
         * @param n : Total number to consider, which is also the biggest number.
         * @param k : Current length of combination needed.
         * @return : List of all combination of length k with numbers in [s, n].
         */
        private List<List<Integer>> combine(int s, int n, int k) {
            List<List<Integer>> ret = new ArrayList<>();

            // If currently no combination is needed, or numbers left is not sufficient for the required
            // combination length, return list of empty list.
            if (k == 0 || s + k - 1 > n) {
                ret.add(new ArrayList<>());
                return ret;
            }

            // Otherwise, first generate combination of length k-1 in [s+1, n]
            ret.addAll(combine(s + 1, n, k - 1));

            // Add current integer (which is s) to each list in the combinations just generated.
            ret.forEach(l -> l.add(0, s));

            // Generate combination of length k in [s+1,n], i.e., combinations excluding current int.
            ret.addAll(combine(s + 1, n, k));

            // Remove empty item before return. (This step is resulted by well designed.)
            ret.removeIf(p -> p.size() == 0);

            return ret;
        }

        public List<List<Integer>> combine(int n, int k) {
            return combine(1, n, k);
        }

    }

    @Test
    public void combine() throws Exception {
        Solution solution = new Solution();

        int[][] expect1 = {{1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4}};
        List<List<Integer>> ret1 = solution.combine(4, 2);
        Integer[][] actual1 = new Integer[ret1.size()][];
        int i = 0;
        for (List<Integer> nestedList : ret1) {
            actual1[i++] = nestedList.toArray(new Integer[nestedList.size()]);
        }
        assertArrayEquals(expect1, actual1);

        int[][] expect2 = {{1, 2, 3, 4, 5, 6, 7}, {1, 2, 3, 4, 5, 6, 8}, {1, 2, 3, 4, 5, 6, 9},
                {1, 2, 3, 4, 5, 6, 10}, {1, 2, 3, 4, 5, 7, 8}, {1, 2, 3, 4, 5, 7, 9},
                {1, 2, 3, 4, 5, 7, 10}, {1, 2, 3, 4, 5, 8, 9}, {1, 2, 3, 4, 5, 8, 10},
                {1, 2, 3, 4, 5, 9, 10}, {1, 2, 3, 4, 6, 7, 8}, {1, 2, 3, 4, 6, 7, 9},
                {1, 2, 3, 4, 6, 7, 10}, {1, 2, 3, 4, 6, 8, 9}, {1, 2, 3, 4, 6, 8, 10},
                {1, 2, 3, 4, 6, 9, 10}, {1, 2, 3, 4, 7, 8, 9}, {1, 2, 3, 4, 7, 8, 10},
                {1, 2, 3, 4, 7, 9, 10}, {1, 2, 3, 4, 8, 9, 10}, {1, 2, 3, 5, 6, 7, 8},
                {1, 2, 3, 5, 6, 7, 9}, {1, 2, 3, 5, 6, 7, 10}, {1, 2, 3, 5, 6, 8, 9},
                {1, 2, 3, 5, 6, 8, 10}, {1, 2, 3, 5, 6, 9, 10}, {1, 2, 3, 5, 7, 8, 9},
                {1, 2, 3, 5, 7, 8, 10}, {1, 2, 3, 5, 7, 9, 10}, {1, 2, 3, 5, 8, 9, 10},
                {1, 2, 3, 6, 7, 8, 9}, {1, 2, 3, 6, 7, 8, 10}, {1, 2, 3, 6, 7, 9, 10},
                {1, 2, 3, 6, 8, 9, 10}, {1, 2, 3, 7, 8, 9, 10}, {1, 2, 4, 5, 6, 7, 8},
                {1, 2, 4, 5, 6, 7, 9}, {1, 2, 4, 5, 6, 7, 10}, {1, 2, 4, 5, 6, 8, 9},
                {1, 2, 4, 5, 6, 8, 10}, {1, 2, 4, 5, 6, 9, 10}, {1, 2, 4, 5, 7, 8, 9},
                {1, 2, 4, 5, 7, 8, 10}, {1, 2, 4, 5, 7, 9, 10}, {1, 2, 4, 5, 8, 9, 10},
                {1, 2, 4, 6, 7, 8, 9}, {1, 2, 4, 6, 7, 8, 10}, {1, 2, 4, 6, 7, 9, 10},
                {1, 2, 4, 6, 8, 9, 10}, {1, 2, 4, 7, 8, 9, 10}, {1, 2, 5, 6, 7, 8, 9},
                {1, 2, 5, 6, 7, 8, 10}, {1, 2, 5, 6, 7, 9, 10}, {1, 2, 5, 6, 8, 9, 10},
                {1, 2, 5, 7, 8, 9, 10}, {1, 2, 6, 7, 8, 9, 10}, {1, 3, 4, 5, 6, 7, 8},
                {1, 3, 4, 5, 6, 7, 9}, {1, 3, 4, 5, 6, 7, 10}, {1, 3, 4, 5, 6, 8, 9},
                {1, 3, 4, 5, 6, 8, 10}, {1, 3, 4, 5, 6, 9, 10}, {1, 3, 4, 5, 7, 8, 9},
                {1, 3, 4, 5, 7, 8, 10}, {1, 3, 4, 5, 7, 9, 10}, {1, 3, 4, 5, 8, 9, 10},
                {1, 3, 4, 6, 7, 8, 9}, {1, 3, 4, 6, 7, 8, 10}, {1, 3, 4, 6, 7, 9, 10},
                {1, 3, 4, 6, 8, 9, 10}, {1, 3, 4, 7, 8, 9, 10}, {1, 3, 5, 6, 7, 8, 9},
                {1, 3, 5, 6, 7, 8, 10}, {1, 3, 5, 6, 7, 9, 10}, {1, 3, 5, 6, 8, 9, 10},
                {1, 3, 5, 7, 8, 9, 10}, {1, 3, 6, 7, 8, 9, 10}, {1, 4, 5, 6, 7, 8, 9},
                {1, 4, 5, 6, 7, 8, 10}, {1, 4, 5, 6, 7, 9, 10}, {1, 4, 5, 6, 8, 9, 10},
                {1, 4, 5, 7, 8, 9, 10}, {1, 4, 6, 7, 8, 9, 10}, {1, 5, 6, 7, 8, 9, 10},
                {2, 3, 4, 5, 6, 7, 8}, {2, 3, 4, 5, 6, 7, 9}, {2, 3, 4, 5, 6, 7, 10},
                {2, 3, 4, 5, 6, 8, 9}, {2, 3, 4, 5, 6, 8, 10}, {2, 3, 4, 5, 6, 9, 10},
                {2, 3, 4, 5, 7, 8, 9}, {2, 3, 4, 5, 7, 8, 10}, {2, 3, 4, 5, 7, 9, 10},
                {2, 3, 4, 5, 8, 9, 10}, {2, 3, 4, 6, 7, 8, 9}, {2, 3, 4, 6, 7, 8, 10},
                {2, 3, 4, 6, 7, 9, 10}, {2, 3, 4, 6, 8, 9, 10}, {2, 3, 4, 7, 8, 9, 10},
                {2, 3, 5, 6, 7, 8, 9}, {2, 3, 5, 6, 7, 8, 10}, {2, 3, 5, 6, 7, 9, 10},
                {2, 3, 5, 6, 8, 9, 10}, {2, 3, 5, 7, 8, 9, 10}, {2, 3, 6, 7, 8, 9, 10},
                {2, 4, 5, 6, 7, 8, 9}, {2, 4, 5, 6, 7, 8, 10}, {2, 4, 5, 6, 7, 9, 10},
                {2, 4, 5, 6, 8, 9, 10}, {2, 4, 5, 7, 8, 9, 10}, {2, 4, 6, 7, 8, 9, 10},
                {2, 5, 6, 7, 8, 9, 10}, {3, 4, 5, 6, 7, 8, 9}, {3, 4, 5, 6, 7, 8, 10},
                {3, 4, 5, 6, 7, 9, 10}, {3, 4, 5, 6, 8, 9, 10}, {3, 4, 5, 7, 8, 9, 10},
                {3, 4, 6, 7, 8, 9, 10}, {3, 5, 6, 7, 8, 9, 10}, {4, 5, 6, 7, 8, 9, 10}};

        List<List<Integer>> ret2 = solution.combine(10, 7);
        Integer[][] actual2 = new Integer[ret2.size()][];
        i = 0;
        for (List<Integer> nestedList : ret2) {
            actual2[i++] = nestedList.toArray(new Integer[nestedList.size()]);
        }
        assertArrayEquals(expect2, actual2);
    }
}
