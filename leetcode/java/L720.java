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

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class L720 {
    @Test
    public void longestWord() throws Exception {
        Solution solution = new Solution();

        String[] words = {"a", "banana", "app", "appl", "ap", "apply", "apple"};

        assertTrue("apple".equals(solution.longestWord(words)));
    }

    class Solution {

        /**
         * Naive Solution. Put string into priority queue based on length of word, and validate from
         * the longest string. Use {@code HashSet} to determine whether a substring exists in words
         * list.
         *
         * @param words Words list to check.
         *
         * @return The longest word in {@code words} that can be built one character at a time by
         * other words in words
         */
        public String longestWord(String[] words) {
            HashSet<String> set = new HashSet<>();
            set.addAll(Arrays.asList(words));

            PriorityQueue<String> x = new PriorityQueue<>(
                    (o1, o2) -> {
                        if (o1.length() < o2.length())
                            return 1;
                        if (o1.length() > o2.length())
                            return -1;
                        return String.CASE_INSENSITIVE_ORDER.compare(o1, o2);
                    }
            );

            x.addAll(Arrays.asList(words));

            String ret = null;
            String w = x.poll();
            while (!x.isEmpty()) {
                if (ret == null)
                    ret = w;
                if (w.length() == 1)
                    return ret;

                String subw = w.substring(0, w.length() - 1);

                if (set.contains(subw)) {
                    w = subw;
                } else {
                    ret = null;
                    w = x.poll();
                }
            }

            return "";
        }
    }
}
