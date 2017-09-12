import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Implement a magic directory with buildDict, and search methods.
 * <p>
 * For the method buildDict, you'll be given a list of non-repetitive words to build a dictionary
 * <p>
 * For the method search, you'll be given a word, and judge whether if you modify exactly one
 * character into another character in this word, the modified word is in the dictionary you just
 * built.
 */
public class L676 {

    class MagicDictionary {

        ArrayList<String> dict;

        /**
         * Initialize your data structure here.
         */
        public MagicDictionary() {
            dict = new ArrayList<>();
        }

        /**
         * Build a dictionary through a list of words, a brute force way.
         */
        public void buildDict(String[] dict) {
            char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                    'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
            for (String word : dict) {
                for (int i = 0; i < word.length(); i++) {
                    for (char x : alphabet) {
                        if (x == word.charAt(i))
                            continue;
                        StringBuilder newWord = new StringBuilder(word);
                        newWord.setCharAt(i, x);
                        this.dict.add(newWord.toString());
                    }
                }
            }

        }

        /**
         * Returns if there is any word in the trie that equals to the given word after modifying
         * exactly one character
         */
        public boolean search(String word) {
            return this.dict.contains(word);
        }
    }

    @Test
    public void search() throws Exception {
        MagicDictionary dict = new MagicDictionary();
        // Input: buildDict(["hello", "leetcode"]), Output: Null
        // Input: search("hello"), Output: False
        // Input: search("hhllo"), Output: True
        // Input: search("hell"), Output: False
        // Input: search("leetcoded"), Output: False
        dict.buildDict(new String[]{"hello", "leetcode"});
        assertFalse(dict.search("hello"));
        assertTrue(dict.search("hhllo"));
        assertFalse(dict.search("hell"));
        assertFalse(dict.search("leetcoded"));
    }

}
