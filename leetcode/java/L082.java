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
