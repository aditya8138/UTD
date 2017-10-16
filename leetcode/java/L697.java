import java.util.*;

public class L697 {
    class Solution {
        public int findShortestSubArray(int[] nums) {
            Map<Integer, Integer> freq = new HashMap<>();
            Map<Integer, List<Integer>> rfreq = new HashMap<>();


            for (int n : nums) {
                freq.put(n, freq.getOrDefault(n, 1) + 1);
                int m = freq.get(n);
                List<Integer> nl = rfreq.getOrDefault(m, new LinkedList<>(Collections.singletonList(n)));
                nl.add(m);
                rfreq.put(m, nl);
            }

            int maxv = Collections.max(freq.values());
            List<Integer> maxn = rfreq.get(maxv);

            int left = 0, right = 0;
            List<Integer> x = new ArrayList<Integer>(Arrays.asList(nums));
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == maxn) {
                    right = left = i;
                    break;
                }
            }

            for (int i = nums.length - 1; i > left; i--) {
                if (nums[i] == maxn) {
                    right = i;
                    break;
                }
            }

            return right - left + 1;

        }
    }
}
