import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

public class L078 {
    class Solution {
        /**
         * Intuitive solution following the previous problem 077.Combination. The union of all
         * combinations are the subsets. The previous solution compute all the index (actually
         * index+1), then after the union, change each value the actual value in the corresponding
         * index. Actual running time is slow.
         *
         * @param nums : List to be compute.
         * @return : List of all subset.
         */
        public List<List<Integer>> subsetsIntuitive(int[] nums) {
            L077.Solution c = new L077.Solution();

            List<List<Integer>> ret = new ArrayList<>();
            for (int i = 0; i <= nums.length; i++)
                ret.addAll(c.combine(nums.length, i));

            for (List<Integer> l : ret) {
                l.replaceAll(i -> nums[i - 1]);
            }

            return ret;
        }

        /**
         * Bitwise solution. Since a subset can be represented as a bit string of length n, like
         * '00010000101', and total number of possible subset is 2^n. We can just iterate from 1 to
         * 2^n, for each number, generate a subset based on the corresponding bit string. A trick to
         * extract specific bit from a bit string is simply bitwise AND with another specific bit
         * string, for example '00000001'.
         *
         * @param nums : List to be compute.
         * @return : List of all subset.
         */
        public List<List<Integer>> subsetsBitwise(int[] nums) {
            int size = ((int) Math.pow(2, nums.length));
            List<List<Integer>> ret = new ArrayList<>(size);
            List<Integer> subset;
            for (int i = 0; i < size; i++) {
                subset = new ArrayList<>();
                for (int j = i, k = 0; j > 0; j >>= 1, k++) {
                    if ((j & 1) == 1)
                        subset.add(nums[k]);
                }
                ret.add(subset);
            }
            return ret;
        }
    }

    @Test
    public void subsetsBitwise() throws Exception {
        Solution solution = new Solution();
        int[] s = {11, 22, 33, 44, 55};
        System.out.println(solution.subsetsBitwise(s));
    }

    @Test
    public void subsetsIntuitive() throws Exception {
        Solution solution = new Solution();
        int[] s = {11, 22, 33, 44, 55};
        System.out.println(solution.subsetsIntuitive(s));
    }

    @Test
    public void subsets() throws Exception {
    }
}
