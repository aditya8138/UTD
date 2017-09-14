package basic;

import java.util.ArrayList;
import java.util.List;

/**
 * Supporting data structure - Binary tree.
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }

    public void print() {
        if (left != null)
            left.print();
        System.out.println(val);
        if (right != null)
            right.print();
    }

    public List<Integer> toArray() {
        List<Integer> arr = new ArrayList<>();
        if (left != null)
            arr.addAll(left.toArray());
        arr.add(val);
        if (right != null)
            arr.addAll(right.toArray());
        return arr;
    }
}
