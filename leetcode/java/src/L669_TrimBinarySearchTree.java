import basic.TreeNode;

/**
 * Given a binary search tree and the lowest and highest boundaries as {@code L} and {@code R},
 * trim the tree so that all its elements lies in {@code [L, R]} (R>= L). You might need to change
 * the root of the tree, so the result should return the new root of the trimmed binary search tree.
 */
public class L669_TrimBinarySearchTree {

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
