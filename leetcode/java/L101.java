/*
 * Copyright (c) 2017.  Hanlin He
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import basic.TreeNode;

/**
 * <h1>101. Symmetric Tree</h1>
 * <p>
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric.
 * <p>
 * Note: Bonus points if you could solve it both recursively and iteratively.
 */
public class L101 {


    /*
     * Intuitive solution. First mirror a subtree, and then check if left and right are the same.
     */
    class IntuitiveSolution {
        public boolean isSymmetric(TreeNode root) {
            if (root == null)
                return true;

            mirror(root.right);

            return isSame(root.left, root.right);
        }

        private void mirror(TreeNode root) {
            if (root == null)
                return;
            TreeNode tmp = root.left;
            root.left = root.right;
            root.right = tmp;
            mirror(root.left);
            mirror(root.right);
        }

        private boolean isSame(TreeNode a, TreeNode b) {
            if (a == null && b == null)
                return true;

            if (a == null || b == null)
                return false;

            if (a.val != b.val)
                return false;

            return isSame(a.left, b.left) && isSame(a.right, b.right);
        }
    }
}
