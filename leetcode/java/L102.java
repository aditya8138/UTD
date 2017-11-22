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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <h1>102. Binary Tree Level Order Traversal</h1>
 * <p>
 * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to
 * right, level by level).
 * <p>
 * For example: Given binary tree [3,9,20,null,null,15,7],
 * <pre>
 *     3
 *    / \
 *   9  20
 *     /  \
 *   15    7
 * </pre>
 * <p>
 * return its level order traversal as:
 * <pre>
 * [
 * [3],
 * [9,20],
 * [15,7]
 * ]
 * </pre>
 */
public class L102 {
    class Solution {
        public List<List<Integer>> levelOrder(TreeNode root) {

            List<List<Integer>> ret = new LinkedList<>();

            ArrayList<TreeNode> level = new ArrayList<>();

            if (root == null)
                return ret;
            level.add(root);

            ArrayList<TreeNode> nextLevel = new ArrayList<>();
            ArrayList<TreeNode> tmp;
            while (!level.isEmpty()) {
                List<Integer> l = new ArrayList<>();
                nextLevel.clear();
                for (TreeNode n : level) {
                    l.add(n.val);
                    if (n.left != null)
                        nextLevel.add(n.left);
                    if (n.right != null)
                        nextLevel.add(n.right);
                }
                ret.add(l);
                if (nextLevel.isEmpty())
                    break;
                tmp = level;
                level = nextLevel;
                nextLevel = tmp;
            }

            return ret;
        }
    }
}
