/*
 * Copyright (c) 2017.  Hanlin He
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

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
    class Solution {
        public int largestRectangleAreaBrute(int[] heights) {

            if (heights.length == 1)
                return heights[0];

            int hmax = -1;
            Set<Integer> s = new TreeSet<>();

            for (int h : heights) {
                if (hmax < h)
                    hmax = h;
                s.add(h);
            }

            int max = 0;
            for (int i : s) {
                int tmp = 0; // temp rectangle size.
                for (int h : heights) {
                    if (h >= i)
                        tmp += i;
                    else {
                        if (max < tmp)
                            max = tmp;
                        tmp = 0;
                    }
                }
                if (max < tmp)
                    max = tmp;
            }
            return max;
        }

        /**
         * Array as stack implementation. Fast but not easy to see. Refer to deque for some insight.
         */
        public int largestRectangleAreaArray(int[] heights) {
            int[] stack = new int[heights.length];

            final int bottom = -1;
            int top = -1;
            int pop;
            int min = 0;
            int tmp;

            int index = -1;
            for (int height : heights) {
                index++;

                if (top == bottom) {
                    top++;
                    stack[top] = index;
                    continue;
                }

                if (heights[stack[top]] <= height) {
                    top++;
                    stack[top] = index;
                    continue;
                }

                while (top > bottom && heights[stack[top]] > height) {
                    pop = stack[top];
                    top--;
                    if (top == bottom)
                        tmp = heights[pop] * index;
                    else
                        tmp = heights[pop] * (index - 1 - stack[top]);
                    if (tmp > min)
                        min = tmp;
                }
                top++;
                stack[top] = index;
            }

            while (top > 0) {
                pop = stack[top];
                top--;
                if (top == bottom)
                    tmp = heights[pop] * (index + 1);
                else
                    tmp = heights[pop] * (index - stack[top]);
                if (tmp > min)
                    min = tmp;
            }

            return min;
        }

        public int largestRectangleAreaDeque(int[] heights) {
            Deque<Integer> stack = new ArrayDeque<>(heights.length);

            int pop;
            int min = 0;
            int tmp;

            int index = -1;
            for (int height : heights) {
                index++;

                // Stack empty
                if (stack.isEmpty()) {
                    stack.push(index);
                    continue;
                }

                // Otherwise, stack is not empty.

                // If all elements on stack are equal or less than current.
                // Push current into stack.
                if (heights[stack.peek()] <= height) {
                    stack.push(index);
                    continue;
                }

                // Otherwise, elements on top of stack is bigger.
                // Pop until a smaller is found or stack empty.
                // With each pop, calculate a largest rec area with popped height as the minimum height.
                // The left side index is current stack top, the right index is current index - 1.
                while (!stack.isEmpty() && heights[stack.peek()] > height) {
                    pop = stack.pop();
                    if (stack.isEmpty())
                        tmp = heights[pop] * index;
                    else
                        tmp = heights[pop] * (index - 1 - stack.peek());
                    if (tmp > min)
                        min = tmp;
                }
                stack.push(index);
            }

            // After iteration, pop and calculate until stack empty.
            while (!stack.isEmpty()) {
                pop = stack.pop();
                if (stack.isEmpty())
                    tmp = heights[pop] * (index + 1); // This plus one is essential.
                else
                    tmp = heights[pop] * (index - stack.peek());
                if (tmp > min)
                    min = tmp;
            }

            return min;
        }
    }


    @Test
    public void largestRectangleArea() throws Exception {

        int[] heights = new int[]{2,1,5,6,2,3};

        assertEquals(10, new Solution().largestRectangleAreaArray(heights));
        assertEquals(10, new Solution().largestRectangleAreaBrute(heights));
        assertEquals(10, new Solution().largestRectangleAreaDeque(heights));

        int[] largeH = new int[20000];
        for (int i = 0; i < 20000; i++)
            largeH[i] = i;

        long start;
        start = System.currentTimeMillis();
        assertEquals(100000000, new Solution().largestRectangleAreaArray(largeH));
        long array = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        assertEquals(100000000, new Solution().largestRectangleAreaBrute(largeH));
        long brute = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        assertEquals(100000000, new Solution().largestRectangleAreaDeque(largeH));
        long deque = System.currentTimeMillis() - start;

        System.out.println(String.format("Brute Force: %d\nArray: %d\nDeque: %d", brute, array, deque));

        assertEquals(2, new Solution().largestRectangleAreaDeque(new int[]{1,1}));
    }
}
