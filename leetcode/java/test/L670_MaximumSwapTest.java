import org.junit.Test;

import static org.junit.Assert.*;

public class L670_MaximumSwapTest {
    @Test
    public void maximumSwap() throws Exception {
        L670_MaximumSwap solution = new L670_MaximumSwap();
        assertEquals(7236, solution.maximumSwap(2736));
        assertEquals(9973, solution.maximumSwap(9937));
        assertEquals(9913, solution.maximumSwap(1993));
        assertEquals(99837, solution.maximumSwap(99738));
        assertEquals(98863, solution.maximumSwap(98368));
        assertEquals(10000, solution.maximumSwap(10000));
        assertEquals(110000, solution.maximumSwap(100010));
    }
}