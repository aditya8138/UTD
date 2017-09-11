import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class L675_CutOffTreesforGolfEventTest {
    @Test
    public void cutOffTree() throws Exception {
        Arrays.asList(1, 2, 3);
        List<List<Integer>> forest1 = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(0, 0, 4),
                Arrays.asList(7, 6, 5)
        );

        L675_CutOffTreesforGolfEvent solution = new L675_CutOffTreesforGolfEvent();
        assertEquals(6, solution.cutOffTree(forest1));


        List<List<Integer>> forest2 = Arrays.asList(
                Arrays.asList(54581641, 64080174, 24346381, 69107959),
                Arrays.asList(86374198, 61363882, 68783324, 79706116),
                Arrays.asList(668150, 92178815, 89819108, 94701471),
                Arrays.asList(83920491, 22724204, 46281641, 47531096),
                Arrays.asList(89078499, 18904913, 25462145, 60813308)
        );
        assertEquals(57, solution.cutOffTree(forest2));
    }

}