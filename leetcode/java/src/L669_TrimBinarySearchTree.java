import basic.TreeNode;

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
