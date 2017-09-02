import org.junit.Test;

import static org.junit.Assert.*;

public class L078_SubsetsTest {
    @Test
    public void subsetsBitwise() throws Exception {
        L078_Subsets solution = new L078_Subsets();
        int[] s = {11,22,33,44,55};
        System.out.println(solution.subsetsBitwise(s));
    }

    @Test
    public void subsetsIntuitive() throws Exception {
        L078_Subsets solution = new L078_Subsets();
        int[] s = {11, 22, 33, 44, 55};
        System.out.println(solution.subsetsIntuitive(s));
    }

    @Test
    public void subsets() throws Exception {
    }

}