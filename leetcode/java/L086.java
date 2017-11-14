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
 * 086. Partition List
 * <p>
 * Given a linked list and a value x, partition it such that all nodes less than x come before nodes
 * greater than or equal to x.
 * <p>
 * You should preserve the original relative order of the nodes in each of the two partitions.
 * <p>
 * For example, Given 1->4->3->2->5->2 and x = 3, return 1->2->2->4->3->5.
 */
public class L086 {
    @Test
    public void partition() throws Exception {

        Solution solution = new Solution();

        assertArrayEquals(new Integer[]{1, 2, 2, 4, 3, 5},
                ListNode.toArray(solution.partition(ListNode.asList(new Integer[]{1, 4, 3, 2, 5, 2}), 3)));
    }

    class Solution {
        public ListNode partition(ListNode head, int x) {
            ListNode dummyH = new ListNode(0);
            ListNode dummyx = new ListNode(x);

            ListNode hi = dummyH, xi = dummyx;

            while (head != null) {
                if (head.val < x) {
                    hi.next = head;
                    hi = hi.next;
                } else {
                    xi.next = head;
                    xi = xi.next;
                }
                head = head.next;
            }
            hi.next = dummyx.next;
            xi.next = null;

            return dummyH.next;
        }
    }
}
