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

import basic.ListNode;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * 092. Reverse Linked List II
 * <p>
 * Reverse a linked list from position m to n. Do it in-place and in one-pass.
 * <p>
 * For example: Given 1->2->3->4->5->NULL, m = 2 and n = 4,
 * <p>
 * return 1->4->3->2->5->NULL.
 * <p>
 * Note: Given m, n satisfy the following condition: 1 ≤ m ≤ n ≤ length of list.
 */
public class L092 {
    @Test
    public void partition() throws Exception {

        Solution solution = new Solution();

        assertArrayEquals(new Integer[]{7, 6, 5, 4, 3, 2, 1, 8, 9, 10},
                ListNode.toArray(solution.reverseBetween(
                        ListNode.asList(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}), 1, 7)));
    }

    class Solution {
        public ListNode reverseBetween(ListNode head, int m, int n) {
            int i = 1;

            // Create a dummy header pointing to head, in case m == 1.
            ListNode dummy = new ListNode(0);
            dummy.next = head;

            // Iterate from dummy header, stop at the previous node of position m.
            ListNode it = dummy;
            while (i < m) {
                it = it.next;
                i++;
            }

            // Then, left work is reverse the next m - n nodes and repoint its tail to this point.

            // Thus, mark current location.
            ListNode left = it;

            // Initialize reverse env.
            it = it.next;
            ListNode pre = left;
            ListNode tmp;

            /**
             * Reverse operation is intuitive:
             * 1. First store next node to temp variable;
             * 2. Then point current node.next to previous node.
             * 3. Move previous and current point forward.
             * 4. Repeat 1 to 3 until number of nodes reached.
             *
             * i == m at start.
             * i == n + 1 in the end,
             * and @code{it} points to the first right side not reversed, @code{pre} point to last
             * node reversed.
             */
            while (i <= n) {
                tmp = it.next;
                it.next = pre;
                pre = it;
                it = tmp;
                i++;
            }

            // Reconnect the list by point the first node reversed to first right side node, and
            // left.next to last node reversed.
            left.next.next = it;
            left.next = pre;
            return dummy.next;
        }
    }
}
