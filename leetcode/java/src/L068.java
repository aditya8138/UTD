import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of words and a length L, format the text such that each line has exactly L characters and is fully
 * (left and right) justified.
 * <p>
 * You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra
 * spaces ' ' when necessary so that each line has exactly L characters.
 * <p>
 * Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line do not
 * divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.
 * <p>
 * For the last line of text, it should be left justified and no extra space is inserted between words.
 * <p>
 * For example, words: ["This", "is", "an", "example", "of", "text", "justification."] L: 16.
 */
public class L068 {
    class Solution {
        /**
         * Intuitive solution. Spaghetti Code. Slow but not sure why.
         *
         * @param words    Array words to be justify.
         * @param maxWidth Maximum width of one line.
         * @return List of line (String) after justification.
         */
        public List<String> fullJustify(String[] words, int maxWidth) {
            List<String> ret = new ArrayList<>();
            if (words.length == 1 && words[0].equals("")) {
                if (maxWidth != 0)
                    ret.add(String.format("%" + maxWidth + "s", " "));
                else
                    ret.add("");
                return ret;
            }

            List<String> line = new ArrayList<>();

            int wordsLengthWithoutBlank = 0;

            for (String word : words) {
            /* length of current words + new word length + number of blank <= maxWidth => add word */
                if (wordsLengthWithoutBlank + word.length() + line.size() <= maxWidth) {
                    wordsLengthWithoutBlank += word.length();
                    line.add(word);
                    continue;
                }
                if (line.size() == 1) {
                    ret.add(String.format("%-" + maxWidth + "s", line.get(0)));
                } else {
            /* Otherwise, add current words list to the new line, and start over. */
                    StringBuilder stringBuilder = new StringBuilder(maxWidth);
                    int blankCount = (maxWidth - wordsLengthWithoutBlank) / (line.size() - 1);
                    int restBlankCount = (maxWidth - wordsLengthWithoutBlank) % (line.size() - 1);
                    if (restBlankCount == 0) {
                        ret.add(String.join(String.format("%" + blankCount + "s", " "), line));
                    } else {
                        stringBuilder.append(String.join(String.format("%" + (blankCount + 1) + "s", ' '), line.subList(0, restBlankCount + 1)))
                                .append(String.format("%" + blankCount + "s", ' '))
                                .append(String.join(String.format("%" + blankCount + "s", ' '), line.subList(restBlankCount + 1, line.size())));
                        ret.add(stringBuilder.toString());
                    }
                }
                line = new ArrayList<>();
                line.add(word);
                wordsLengthWithoutBlank = word.length();
            }
            ret.add(String.format("%-" + maxWidth + "s", String.join(" ", line)));
            return ret;
        }

        /**
         * A modified version, though having little difference. Change [String.format("%" +
         * blankCount + "s", " ")] to for loop.
         */
        public List<String> fullJustify2(String[] words, int maxWidth) {
            List<String> ret = new ArrayList<>();
            if (words.length == 1 && words[0].equals("")) {
                if (maxWidth != 0)
                    ret.add(String.format("%" + maxWidth + "s", " "));
                else
                    ret.add("");
                return ret;
            }

            List<String> line = new ArrayList<>();

            int wordsLengthWithoutBlank = 0;

            for (String word : words) {
            /* length of current words + new word length + number of blank <= maxWidth => add word */
                if (wordsLengthWithoutBlank + word.length() + line.size() <= maxWidth) {
                    wordsLengthWithoutBlank += word.length();
                    line.add(word);
                    continue;
                }
                if (line.size() == 1) {
                    ret.add(String.format("%-" + maxWidth + "s", line.get(0)));
                } else {
            /* Otherwise, add current words list to the new line, and start over. */
                    StringBuilder stringBuilder = new StringBuilder(maxWidth);
                    int blankCount = (maxWidth - wordsLengthWithoutBlank) / (line.size() - 1);
                    int restBlankCount = (maxWidth - wordsLengthWithoutBlank) % (line.size() - 1);
                    if (restBlankCount == 0) {
                        ret.add(String.join(String.format("%" + blankCount + "s", " "), line));
                    } else {
                        int i;
                        for (i = 0; i < restBlankCount; i++) {
                            stringBuilder.append(line.get(i));
                            addBlank(stringBuilder, blankCount + 1);
                        }
                        for (; i < line.size(); i++) {
                            stringBuilder.append(line.get(i));
                            addBlank(stringBuilder, blankCount);
                        }
                        ret.add(stringBuilder.toString());
                    }
                }
                line = new ArrayList<>();
                line.add(word);
                wordsLengthWithoutBlank = word.length();
            }
            ret.add(String.format("%-" + maxWidth + "s", String.join(" ", line)));
            return ret;
        }

        private void addBlank(StringBuilder stringBuilder, int numberOfBlank) {
            for (int i = 0; i < numberOfBlank; i++)
                stringBuilder.append(" ");
        }
    }

    @Test
    public void fullJustify() throws Exception {
        Solution solution = new Solution();

        String[] words = {"This", "is", "an", "example", "of", "text", "justification."};
        int length = 16;
        String[] ret = {"This    is    an", "example  of text", "justification.  "};
        assertArrayEquals(ret, solution.fullJustify(words, 16).toArray());
        String[] ret1 = {""};
        assertArrayEquals(ret1, solution.fullJustify(ret1, 0).toArray());

        String[] words2 = {"a", "b", "c", "d", "e"};
        assertArrayEquals(words2, solution.fullJustify(words2, 1).toArray());

        String[] words3 = {"Don't", "go", "around", "saying", "the", "world", "owes", "you", "a", "living;", "the", "world", "owes", "you", "nothing;", "it", "was", "here", "first."};
        String[] ret3 = {"Don't  go  around  saying  the", "world  owes  you a living; the", "world owes you nothing; it was", "here first.                   "};
        assertArrayEquals(ret3, solution.fullJustify(words3, 30).toArray());
    }

    @Test
    public void fullJustify2() throws Exception {
        Solution solution = new Solution();

        String[] words = {"This", "is", "an", "example", "of", "text", "justification."};
        int length = 16;
        String[] ret = {"This    is    an", "example  of text", "justification.  "};
        assertArrayEquals(ret, solution.fullJustify2(words, 16).toArray());
        String[] ret1 = {""};
        assertArrayEquals(ret1, solution.fullJustify2(ret1, 0).toArray());

        String[] words2 = {"a", "b", "c", "d", "e"};
        assertArrayEquals(words2, solution.fullJustify2(words2, 1).toArray());

        String[] words3 = {"Don't", "go", "around", "saying", "the", "world", "owes", "you", "a", "living;", "the", "world", "owes", "you", "nothing;", "it", "was", "here", "first."};
        String[] ret3 = {"Don't  go  around  saying  the", "world  owes  you a living; the", "world owes you nothing; it was", "here first.                   "};
        assertArrayEquals(ret3, solution.fullJustify2(words3, 30).toArray());
    }
}
