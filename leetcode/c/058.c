/* 058. Length of Last Word. */

#include <stdio.h>
#include <stdbool.h>

int lengthOfLastWord(char* s) {
    int start = -1;
    int end = -1;
    int i = -1;
    bool word = false;
    while (*s != '\0') {
        i++;
        if (*s == ' ') {
            // If currently within a word, it means the word just ended.
            if (word) {
                end = i;
                word = false;
            }
            // Otherwise, just continuous blank. Do nothing.
        }
        else {
            // If currently not within a word, it means the word just started.
            if (!word) {
                start = i;
                word = true;
            }
            // Otherwise, just keep going within a word. Do nothing.
        }
        s++;
    }
    // If in the end, no blank exist, set the end index of last word.
    if (word)
        end = i + 1;
    // Otherwise, the end index should already be set at the first blank.

    return end - start;
}

int main() {
    char* s1 = "hello world";
    char* s2 = " hello world    ";

    printf("%d\t%d\n", lengthOfLastWord(s1), lengthOfLastWord(s2));

    return 0;
}
