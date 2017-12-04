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

import java.util.LinkedList;
import java.util.List;

/**
 * <h1>103. Binary Tree Zigzag Level Order Traversal</h1>
 * <p>
 * Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left
 * to right, then right to left for the next level and alternate between).
 */
public class L103 {
    class Solution {
        /**
         * First attempt. Logic is not clear.
         */
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {

            List<List<Integer>> ret = new LinkedList<>();

            if (root == null)
                return ret;

            LinkedList<TreeNode> queue1 = new LinkedList<TreeNode>();
            LinkedList<TreeNode> queue2 = new LinkedList<TreeNode>();

            queue1.offer(root);

            while (true) {
                LinkedList<Integer> next = new LinkedList<>();

                // First round, traverse level left -> right.
                // In previous iteration, previous level in queue is right -> left.
                while (!queue1.isEmpty()) {
                    TreeNode t = queue1.poll();

                    // Each time poll an element, put to the front of the list.
                    next.addFirst(t.val);

                    // Add not-null child from right -> left so that next level have direction right -> left.
                    if (t.right != null)
                        queue2.offer(t.right);
                    if (t.left != null)
                        queue2.offer(t.left);

                }
                ret.add(next);

                if (queue2.isEmpty())
                    break;

                next = new LinkedList<>();
                while (!queue2.isEmpty()) {
                    TreeNode t = queue2.poll();
                    next.addLast(t.val);
                    if (t.right != null)
                        queue1.offer(t.right);
                    if (t.left != null)
                        queue1.offer(t.left);
                }
                ret.add(next);
                if (queue1.isEmpty())
                    break;
            }

            return ret;
        }

        public List<List<Integer>> zigzagLevelOrderRe(TreeNode root) {

            List<List<Integer>> ret = new LinkedList<>();

            if (root == null)
                return ret;

            LinkedList<TreeNode> queue1 = new LinkedList<TreeNode>();
            LinkedList<TreeNode> queue2 = new LinkedList<TreeNode>();

            queue1.offer(root);

            // Each time, the level in queue is left --> right.
            while (true) {
                LinkedList<Integer> next = new LinkedList<>();

                while (!queue1.isEmpty()) {
                    TreeNode t = queue1.poll();

                    // Put at the end.
                    next.addLast(t.val);

                    // Put in queue from left -> right
                    if (t.left != null)
                        queue2.offer(t.left);
                    if (t.right != null)
                        queue2.offer(t.right);

                }
                ret.add(next);

                if (queue2.isEmpty())
                    break;

                next = new LinkedList<>();
                while (!queue2.isEmpty()) {
                    TreeNode t = queue2.poll();

                    // Put at the beginning.
                    next.addFirst(t.val);

                    // Put in queue from left -> right
                    if (t.left != null)
                        queue1.offer(t.left);
                    if (t.right != null)
                        queue1.offer(t.right);
                }
                ret.add(next);
                if (queue1.isEmpty())
                    break;
            }

            return ret;
        }
    }
}
