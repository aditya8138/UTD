import org.junit.Test;

import static org.junit.Assert.*;

import basic.TreeNode;

import java.util.TreeSet;

/**
 * Given a non-empty special binary tree consisting of nodes with the non-negative value, where each
 * node in this tree has exactly two or zero sub-node. If the node has two sub-nodes, then this
 * node's value is the smaller value among its two sub-nodes.
 * <p>
 * Given such a binary tree, you need to output the second minimum value in the set made of all the
 * nodes' value in the whole tree.
 * <p>
 * If no such second minimum value exists, output -1 instead.
 */
public class L671 {

    class Solution {
        /**
         * Recursively find second minimum value in the tree.
         *
         * @param root Current tree root to be checked.
         * @return Second minimum value in current tree, if such number not exist, return {@code
         * -1}.
         */
        public int findSecondMinimumValueRecursive(TreeNode root) {

            // If current root is a leaf node, no second minimum value exists, return -1.
            if (root.left == null && root.right == null)
                return -1;

            assert (root.left != null && root.right != null);

            // Otherwise, recursively compute second minimum value in two children.
            int left = findSecondMinimumValueRecursive(root.left);
            int right = findSecondMinimumValueRecursive(root.right);

            // If two children have same value, then root.val == left.val == right.val, and they
            // should be the minimum value of this tree.
            if (root.left.val == root.right.val) {
                // If both child's subtree do not have second minimum value, this whole tree won't have.
                if (left == -1 && right == -1)
                    return -1;

                // If both child's have found a minimum value, then whichever is smaller would be the
                // second minimum value of the tree.
                if (left != -1 && right != -1)
                    return Integer.min(left, right);

                // Otherwise, one of the children return -1, then the other value would be the second
                // minimum value of the tree.
                return Integer.max(left, right);
            }

            // If left val is smaller than right val, based on definition, the second minimum value
            // can only appear in left subtree.
            if (root.left.val < root.right.val) {
                // If left subtree do not have a minimum, i.e., all values in left subtree is the
                // same (as root), then right value is the second minimum.
                if (left == -1)
                    return root.right.val;

                // Otherwise, whichever is smaller among left subtree's second minimum value and
                // right value is the second minimum value of the tree.
                return Integer.min(left, root.right.val);
            }

            // Corresponding operation with right.
            if (root.left.val > root.right.val) {
                if (right == -1)
                    return root.left.val;
                return Integer.min(root.left.val, right);
            }

            // This return can never be executed.
            assert false;
            return -1;
        }

        /**
         * Find second minimum value in the tree using TreeSet. The elements in {@code TreeSet} are
         * ordered using their natural ordering. It's quite easy to extract the second minimum
         * value.
         *
         * @param root Current tree root to be checked.
         * @return Second minimum value in current tree, if such number not exist, return {@code
         * -1}.
         */
        public int findSecondMinimumValue(TreeNode root) {
            TreeSet<Integer> treeSet = new TreeSet<>();
            traverse(root, treeSet);
            Integer[] a = {};
            a = treeSet.toArray(a);
            return a.length > 1 ? a[1] : -1;
        }

        /**
         * Helper function for traverse the tree and add value to {@code TreeSet}.
         *
         * @param root    Current subtree root to be traverse.
         * @param treeSet TreeSet to add value in.
         */
        private void traverse(TreeNode root, TreeSet<Integer> treeSet) {
            if (root == null)
                return;
            treeSet.add(root.val);
            traverse(root.left, treeSet);
            traverse(root.right, treeSet);
        }

    }

    @Test
    public void findSecondMinimumValue() throws Exception {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.left.right.left = new TreeNode(2);
        root.left.right.right = new TreeNode(3);
        root.right.left = new TreeNode(10);
        root.right.right = new TreeNode(5);

        Solution solution = new Solution();

        assertEquals(3, solution.findSecondMinimumValue(root));
        assertEquals(3, solution.findSecondMinimumValueRecursive(root));
    }
}
