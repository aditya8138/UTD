import java.util.Set;
import java.util.TreeSet;

/**
 * Implement a magic directory with buildDict, and search methods.
 * <p>
 * For the method buildDict, you'll be given a list of non-repetitive words to build a dictionary
 * <p>
 * For the method search, you'll be given a word, and judge whether if you modify exactly one
 * character into another character in this word, the modified word is in the dictionary you just
 * built.
 */
public class L676_ImplementMagicDictionary {
    class MagicDictionary {

        Set<String> dict;

        /**
         * Initialize your data structure here.
         */
        public MagicDictionary() {
            dict = new TreeSet<>();
        }

        /**
         * Build a dictionary through a list of words
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


}
