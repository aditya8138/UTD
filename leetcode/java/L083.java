import basic.ListNode;
import org.junit.Test;

/**
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 * <p>
 * For example, Given 1->1->2, return 1->2. Given 1->1->2->3->3, return 1->2->3.
 */
public class L083 {
    @Test
    public void search() throws Exception {
        // to-do
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
