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
 * Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct
 * numbers from the original list.
 * <p>
 * For example, Given 1->2->3->3->4->4->5, return 1->2->5. Given 1->1->1->2->3, return 2->3.
 */
public class L082 {
    @Test
    public void deleteDuplicates() throws Exception {

        Integer[] d1 = new Integer[]{1, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5};
        Integer[] a1 = new Integer[]{5};

        assertArrayEquals(new Integer[]{},
                ListNode.toArray(new Solution().deleteDuplicates(ListNode.asList(new Integer[]{1, 1, 1, 2, 2, 3, 3}))));

        assertArrayEquals(a1, ListNode.toArray(new Solution().deleteDuplicates(ListNode.asList(d1))));
    }

    class Solution {
        /**
         * Remove element with duplicate, leaving only distinct numbers from the original list.
         *
         * @param head Header of the sorted linked list.
         * @return New header of the sorted linked list after deleting all duplicates.
         */
        public ListNode deleteDuplicates(ListNode head) {
            if (head == null)
                return null;

            if (head.next == null)
                return head;

            ListNode cursor = head.next;
            while (cursor != null && cursor.val == head.val)
                cursor = cursor.next;

            if (cursor != head.next)
                return deleteDuplicates(cursor);

            head.next = deleteDuplicates(cursor);
            return head;
        }
    }
}
