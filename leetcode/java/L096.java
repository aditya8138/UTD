import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class L096 {
    static class Solution {
        public int numTrees(int n) {
            Integer[] in = new Integer[n];
            for (int i = 0; i < n; i++)
                in[i] = i + 1;

            List<Integer[]> p = permute(in);

            int count = 0;

            for (Integer[] x : p) {
                if (valid(x))
                    count++;
            }

            return count;
        }

        boolean valid(Integer[] in) {
            int premin = 0;
            int min = in[0];
            for (int i : in) {
                if (i <= min && i >= premin) {
                    premin = i;
                    continue;
                }
                if (i > min) {
                    premin = min;
                    min = i;
                    continue;
                }
                return false;
            }
            return true;
        }

        public static <T> List<T[]> permute(T[] x) {
            final int n = x.length;

            int[] c = new int[x.length];

            List<T[]> ret = new ArrayList<>();
            ret.add(Arrays.copyOf(x, x.length));

            int i = 0;
            while (i < n) {
                if (c[i] < i) {
                    if ((i & 1) == 0) // if i is even
                        swap(x, 0, i);
                    else
                        swap(x, c[i], i);
                    ret.add(Arrays.copyOf(x, x.length));
                    c[i] += 1;
                    i = 0;
                } else {
                    c[i] = 0;
                    i += 1;
                }
            }

            return ret;
        }

        private static <T> void swap(T[] ar, int a, int b) {
            T tmp = ar[a];
            ar[a] = ar[b];
            ar[b] = tmp;
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();

        System.out.println(s.numTrees(5));
    }
}
