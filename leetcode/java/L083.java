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
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 * <p>
 * For example, Given 1->1->2, return 1->2. Given 1->1->2->3->3, return 1->2->3.
 */
public class L083 {
    @Test
    public void deleteDuplicates() throws Exception {

        Integer[] d1 = new Integer[]{1, 1, 1, 1, 2, 2, 2, 3, 3, 4, 4, 5};
        Integer[] a1 = new Integer[]{1, 2, 3, 4, 5};

        assertArrayEquals(new Integer[]{1, 2, 3},
                ListNode.toArray(new Solution().deleteDuplicates(ListNode.asList(new Integer[]{1, 1, 1, 2, 2, 3}))));

        assertArrayEquals(a1, ListNode.toArray(new Solution().deleteDuplicates(ListNode.asList(d1))));
    }

    class Solution {
        /**
         * Remove Duplicates from Sorted List.
         *
         * @param head Header of the sorted linked list.
         * @return New header of the sorted linked list after deleting all duplicates.
         */
        public ListNode deleteDuplicates(ListNode head) {
            final ListNode nhead = head;
            ListNode cursor = head;
            ListNode it = head;
            while (it != null) {
                for (it = it.next; it != null && it.val == cursor.val; it = it.next) ;
                cursor.next = it;
                cursor = cursor.next;
            }
            return nhead;
        }
    }
}
