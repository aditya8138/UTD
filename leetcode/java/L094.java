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
