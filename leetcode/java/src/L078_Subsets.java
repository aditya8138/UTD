import java.util.List;
import java.util.ArrayList;

public class L078_Subsets {
    /**
     * Intuitive solution following the previous problem 077.Combination. The union of all
     * combinations are the subsets. The previous solution compute all the index (actually index+1),
     * then after the union, change each value the actual value in the corresponding index. Actual
     * running time is slow.
     * @param nums  : List to be compute.
     * @return      : List of all subset.
     */
    public List<List<Integer>> subsetsIntuitive(int[] nums) {
        L077_Combinations c = new L077_Combinations();

        List<List<Integer>> ret = new ArrayList<>();
        for (int i = 0; i <= nums.length; i++)
            ret.addAll(c.combine(nums.length, i));

        for (List<Integer> l: ret) {
            l.replaceAll(i -> nums[i-1]);
        }

        return ret;
    }
}
