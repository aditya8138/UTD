import java.util.List;
import java.util.ArrayList;

public class L077_Combinations {

    /**
     * Helper function to generate combinations. Basic idea is generate combination including
     * current integer, by recursively call combine with s+1 and k-1. And recursively generate
     * combination excluding current integer. Finally, return the union of two list.
     * <p>
     * Note that, this solution is designed to be transformed to DP. Each recursive call is strictly
     * rely on a larger s and a less or equal k. By memoizing the result of each recursive call,
     * might reduce time complexity with cost of some increase in space complexity. However, during
     * implementation, I realize that Java do not support generaic type array. Statement
     * <code>List&lt;List&lt;Integer&gt;&gt;[][] m</code> can never be initialized.
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

        // Add current integer (which is s) to the combinations just generated.
        for (List<Integer> l : ret)
            l.add(0, s); // Add at front to ease verification.

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
