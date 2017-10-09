package basic;

import java.util.ArrayList;

public class ListNode {

    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public static ListNode asList(Integer[] a) {
        ListNode dummy = new ListNode(0);
        ListNode cursor = dummy;
        for (int i : a) {
            cursor.next = new ListNode(i);
            cursor = cursor.next;
        }
        return dummy.next;
    }

    public static Integer[] toArray(ListNode head) {
        ArrayList<Integer> a = new ArrayList<>();
        while (head != null) {
            a.add(head.val);
            head = head.next;
        }
        return a.toArray(new Integer[a.size()]);
    }
}
