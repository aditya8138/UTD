import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class L068_TextJustificationSolutionTest {
    @Test
    public void fullJustify() throws Exception {
        L068_TextJustificationSolution solution = new L068_TextJustificationSolution();

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

    public void fullJustify2() throws Exception {
        L068_TextJustificationSolution solution = new L068_TextJustificationSolution();

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