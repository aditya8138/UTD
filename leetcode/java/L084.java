import basic.ListNode;
import org.junit.Test;

import java.util.Deque;

import static org.junit.Assert.assertArrayEquals;

/**
 * Given n non-negative integers representing the histogram's bar height where the width of each bar
 * is 1, find the area of largest rectangle in the histogram.
 * <p>
 * Above is a histogram where width of each bar is 1, given height = [2,1,5,6,2,3].
 * <p>
 * The largest rectangle is shown in the shaded area, which has area = 10 unit.
 * <p>
 * For example, Given heights = [2,1,5,6,2,3], return 10.
 */

public class L084 {
//    class Solution {
//        public int largestRectangleArea(int[] heights) {
//            int[] stack = new int[heights.length];
//
//            final int bottom = -1;
//            int top = -1;
//            int min = -1;
//
//            for (int height : heights) {
//
//                // Stack empty
//                if (top == bottom) {
//                    top++;
//                    stack[top] = height;
//                    min = height;
//                }
//
//                // Otherwise, stack is not empty.
//
//                // If all elements on stack are equal or less than current.
//                // Push current into stack.
//                if (stack[top] <= height) {
//                    top++;
//                    stack[top] = height;
//                    continue;
//                }
//
//                // Otherwise, elements on top of stack is bigger.
//                // Pop until a smaller is found or stack empty.
//                while (top > bottom && )
//            }
//
//            return 0;
//        }
//    }
}
