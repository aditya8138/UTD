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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class L734 {
    class Solution {
        public boolean areSentencesSimilar(String[] words1, String[] words2, String[][] pairs) {
            if (words1.length != words2.length)
                return false;

            Map<String, Set<String>> dict = new HashMap<>();

            for (String[] pair : pairs) {
                String word1 = pair[0].toLowerCase();
                String word2 = pair[1].toLowerCase();
                addDict(dict, word1, word2);
                addDict(dict, word2, word1);
            }

            for (int i = 0; i < words1.length; i++) {
                if (!areWordsSimilar(words1[i], words2[i], dict))
                    return false;
            }

            return true;
        }

        private void addDict(Map<String, Set<String>> dict, String word1, String word2) {
            if (dict.containsKey(word1)) {
                dict.get(word1).add(word2);
            } else {
                Set<String> ns = new HashSet<>();
                ns.add(word2);
                dict.put(word1, ns);
            }
        }

        private boolean areWordsSimilar(String word1, String word2, Map<String, Set<String>> dict) {
            if (word1.equals(word2))
                return true;

            return dict.getOrDefault(word1, new HashSet<>()).contains(word2) || dict.getOrDefault(word2, new HashSet<>()).contains(word1);

        }
    }
}
