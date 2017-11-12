import basic.TreeNode;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * <h1>094. Binary Tree Inorder Traversal</h1>
 * <p>
 * Given a binary tree, return the inorder traversal of its nodes' values.
 * <p>
 * For example: Given binary tree [1,null,2,3], return [1,3,2].
 * <p>
 * Note: Recursive solution is trivial, could you do it iteratively?
 */
public class L094 {

    class Solution {
        public List<Integer> inorderTraversal(TreeNode root) {

            List<Integer> ret = new LinkedList<>();
            if (root == null)
                return ret;

            Deque<TreeNode> d = new LinkedList<>();
            d.push(root);
            while (!d.isEmpty()) {
                TreeNode t = d.peek();

                if (t.left != null) {
                    d.push(t.left);
                    t.left = null;
                    continue;
                }

                t = d.pop();
                ret.add(t.val);

                if (t.right != null) {
                    d.push(t.right);
                }
            }
            return ret;
        }
    }
}
