import basic.TreeNode;
import org.junit.Test;

import static org.junit.Assert.*;

public class L671_SecondMinimumNodeInBinaryTreeTest {
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

        L671_SecondMinimumNodeInBinaryTree solution = new L671_SecondMinimumNodeInBinaryTree();

        assertEquals(3, solution.findSecondMinimumValue(root));
        assertEquals(3, solution.findSecondMinimumValueRecursive(root));
    }

}