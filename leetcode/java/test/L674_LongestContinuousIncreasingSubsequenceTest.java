import org.junit.Test;

import static org.junit.Assert.*;

public class L674_LongestContinuousIncreasingSubsequenceTest {
    @Test
    public void findLengthOfLCIS() throws Exception {
        L674_LongestContinuousIncreasingSubsequence solution = new L674_LongestContinuousIncreasingSubsequence();
        assertEquals(3, solution.findLengthOfLCIS(new int[]{1, 3, 5, 4, 7}));
        assertEquals(1, solution.findLengthOfLCIS(new int[]{2, 2, 2, 2, 2}));
    }

}