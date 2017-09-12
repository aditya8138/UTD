import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

import basic.TreeNode;

/**
 * Given a binary search tree and the lowest and highest boundaries as {@code L} and {@code R}, trim
 * the tree so that all its elements lies in {@code [L, R]} (R>= L). You might need to change the
 * root of the tree, so the result should return the new root of the trimmed binary search tree.
 */
public class L669 {

    class Solution {
        /**
         * Trim binary search tree with left and right boundary.
         *
         * @param root Current root tree node to process.
         * @param L    Lower bound.
         * @param R    Higher bound
         * @return A subtree which fits in the boundary and whose original height is at most root.
         */
        public TreeNode trimBST(TreeNode root, int L, int R) {
            // If current root is leaf node, keep leaf the same.
            if (root == null)
                return null;

            // Otherwise, if current root is smaller than lower bound, then the whole left subtree
            // should be trimed, return whatever right subtree after recursive trim.
            if (root.val < L)
                return trimBST(root.right, L, R);

            // Correspongin operation with left.
            if (root.val > R)
                return trimBST(root.left, L, R);

            // Otherwise, current root does not need to trim. Recursively trim two children.
            root.left = trimBST(root.left, L, R);
            root.right = trimBST(root.right, L, R);

            return root;
        }

    }

    @Test
    public void trimBST() throws Exception {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.left.right.right = new TreeNode(4);

        root.right = new TreeNode(10);
        root.right.left = new TreeNode(8);
        root.right.left.left = new TreeNode(6);
        root.right.left.right = new TreeNode(9);
        root.right.right = new TreeNode(15);

        Integer[] rootArr = {1, 2, 3, 4, 5, 6, 8, 9, 10, 15};
        assertArrayEquals(rootArr, root.toArray().toArray());

        Solution solution = new Solution();

        TreeNode nroot = solution.trimBST(root, 2, 8);

        Integer[] nrootArr = {2, 3, 4, 5, 6, 8};
        assertArrayEquals(nrootArr, nroot.toArray().toArray());
    }

}
