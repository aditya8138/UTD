import basic.TreeNode;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;


public class L669_TrimBinarySearchTreeTest {
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

        L669_TrimBinarySearchTree solution = new L669_TrimBinarySearchTree();

        TreeNode nroot = solution.trimBST(root, 2, 8);

        Integer[] nrootArr = {2, 3, 4, 5, 6, 8};
        assertArrayEquals(nrootArr, nroot.toArray().toArray());
    }

}