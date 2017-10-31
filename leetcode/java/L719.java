import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.*;

/**
 * <h1>719. Find K-th Smallest Pair Distance</h1>
 * <p>
 * Given an integer array, return the k-th smallest distance among all the pairs. The distance of a
 * pair (A, B) is defined as the absolute difference between A and B.
 * <p>
 * <pre>
 * Example 1:
 * Input:
 *      nums = [1,3,1]
 *      k = 1
 * Output: 0
 * Explanation:
 * Here are all the pairs:
 *      (1,3) -> 2
 *      (1,1) -> 0
 *      (3,1) -> 2
 * Then the 1st smallest distance pair is (1,1), and its distance is 0.
 * </pre>
 * Note:
 * <p>
 * <ul> <li>2 <= len(nums) <= 10000. </li> <li>0 <= nums[i] < 1000000. </li> <li>1 <= k <= len(nums)
 * * (len(nums) - 1) / 2.</li> </ul>
 */
public class L719 {
    @Test
    public void findRedundantConnectionNaive() throws Exception {
        int[] nums = new int[10000];
        for (int i = 0; i < 10000; i++)
            nums[i] = i;

        int ret = new Solution().smallestDistancePairNaive(nums, 2500000);

        assertEquals(254, ret);
    }

    @Test
    public void findRedundantConnection() throws Exception {
        int[] nums = new int[10000];
        for (int i = 0; i < 10000; i++)
            nums[i] = i;

        int ret = new Solution().smallestDistancePair(nums, 2500000);

        assertEquals(254, ret);
    }

    class Solution {

        /**
         * Optimized O(n) solution to count number of pairs with absolute difference less than
         * {@code mid}. The key trick is {@code j} never reset to {@code i} since {@code nums[i]} is
         * strictly increasing. {@code nums[j] - nums[i] < nums[j] - nums[i+1]}.
         *
         * @param nums Original array.
         * @param mid  middle point to compute.
         *
         * @return Number of pairs with absolute difference less than {@code mid}.
         */
        private int countPairOn(final int[] nums, final int mid) {
            final int length = nums.length;
            int ret = 0;
            int i, j = 0;
            for (i = 0; i < length; i++) {
                while (j < length && nums[j] - nums[i] <= mid)
                    j++;
                ret += j - i - 1;
            }
            return ret;
        }

        /**
         * Helper function used by {@code smallestDistancePair()} to count the number of pairs with
         * absolute difference less than {@code mid}. Linear search version.
         *
         * @param nums Original array.
         * @param mid  middle point to compute.
         *
         * @return Number of pairs with absolute difference less than {@code mid}.
         */
        private int countPair(final int[] nums, final int mid) {
            final int length = nums.length;
            int ret = 0;
            int i, j;
            for (i = 0; i < length; i++) {
                for (j = i; j < length && nums[j] - nums[i] <= mid; j++) ;
                ret += j - i - 1;
            }
            return ret;
        }

        /**
         * Binary search solution. Conduct binary search on absolute difference.
         *
         * @param nums Original array to be processed.
         * @param k    k-th smallest difference to compute.
         *
         * @return K-th Smallest Pair Distance
         */
        public int smallestDistancePair(int[] nums, int k) {
            final int length = nums.length;

            Arrays.sort(nums);

            // lower bound of absolute difference.
            int low = nums[1] - nums[0];
            int tmp;
            for (int i = 2; i < length; i++) {
                tmp = nums[i] - nums[i - 1];
                if (tmp < low)
                    low = tmp;
            }

            // upper bound of absolute difference.
            int high = nums[length - 1] - nums[0];

            // Binary search for k
            while (low < high) {
                tmp = ((low + high) >> 1);
                if (countPairOn(nums, tmp) < k)
                    low = tmp + 1;
                else
                    high = tmp;
            }

            return low;
        }

        /**
         * Naive solution, runs in O(n^2) time.
         */
        public int smallestDistancePairNaive(int[] nums, int k) {

            Map<Integer, Integer> cmap = new HashMap<>();
            TreeSet<Integer> d = new TreeSet<>();

            for (int i : nums) {
                d.add(i);
                if (cmap.containsKey(i)) {
                    cmap.replace(i, cmap.get(i) + 1);
                    continue;
                }
                cmap.put(i, 1);
            }

            Integer[] da = d.toArray(new Integer[0]);

            TreeMap<Integer, Integer> map = new TreeMap<>();

            int ic, jc;
            for (int i = 0; i < da.length; i++) {
                ic = cmap.get(da[i]);
                if (ic > 1) {
                    if (map.containsKey(0))
                        map.replace(0, map.get(0) + (ic * (ic - 1) / 2));
                    else
                        map.put(0, (ic * (ic - 1) / 2));
                }
                for (int j = i + 1; j < da.length; j++) {
                    jc = cmap.get(da[j]);
                    Integer x = Math.abs(da[j] - da[i]);
                    if (map.containsKey(x))
                        map.replace(x, map.get(x) + ic * jc);
                    else
                        map.put(x, ic * jc);
                }
            }

            int count = 0;
            while (!map.isEmpty()) {
                Map.Entry<Integer, Integer> x = map.pollFirstEntry();
                count += x.getValue();
                if (count >= k)
                    return x.getKey();
            }

            return 0;
        }
    }
}
