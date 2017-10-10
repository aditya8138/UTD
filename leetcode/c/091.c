/* 091. Decode Ways. */
/* A naive recursive solution. Memoize version is available. */

#include<stdio.h>

// Function declaration.
int helper(char* s);
int _numDecodings(char* s);

// Helper function dealing with two recursive call, judge -1.
int helper(char* s) {
    int a = _numDecodings(s + 1);
    int b = _numDecodings(s + 2);

    if (a == -1 && b == -1)
        return -1;

    if (a == -1)
        return b == 0 ? 1 : b;

    if (b == -1)
        return a;

    if (b == 0)
        return a + 1;
    else
        return a + b;
}

int _numDecodings(char* s) {

    // Non paring zero occur.
    if (*s == '0')
        return -1;

    // At the end of string, return 0.
    if (*s == '\0')
        return 0;

    // At the last of string, return 1.
    if (*(s + 1) == '\0')
        return 1;

    // Otherwise, at least two char exist.

    // If current char is '1', two possible way to decode.
    if (*s == '1') {
        if (*(s + 1) == '0') {
            int x = _numDecodings(s + 2);
            return x == 0 ? 1 : x;
        }
        return helper(s);
    }

    // If current char is '2', only if next char is from '0' to '6'.
    if (*s == '2') {
        if (*(s + 1) > '0' && *(s + 1) <= '6')
            return helper(s);

        int a = _numDecodings(s + 2);
        return a == 0 ? 1 : a;
    }

    // Other number or ('2' + [7-9]), only one way to decode.
    int a = _numDecodings(s + 1);
    return a == 0 ? 1 : a;
}

int numDecodings(char* s) {
    int ret = _numDecodings(s);
    return ret == -1 ? 0 : ret;
}

int main() {
    char* a = "121123123124124128";
    char* b = "100";

    printf("%s:\n\tExpected value: 702.\n\tActual value: %d\n", a, numDecodings(a));
    printf("%s:\n\tExpected value: 0.\n\tActual value: %d\n", b, numDecodings(b));
}

