/* 010.Regular Expression Matching */

#include<stdio.h>
#include <stdbool.h>

/* Function Declaration. */
bool _isMatch(char* s, char* p, char c);
bool isMatch(char* s, char* p);

/* A helping function to deal with *. */
bool _isMatch(char* s, char* p, char c) {
    /* If both string is empty, definitely matching. */
    if (p[0] == '\0' && s[0] == '\0')
        return true;
    /* If s is not empty (p does not matter). */
    if (s[0] != '\0') {
        /* If the previous repeating char is '.',
         * or the previous repeating char match with s[0].*/
        if ((c == '.') || (c == s[0]))
            return (isMatch(s, p) || _isMatch(&s[1], p, c));
        return isMatch(s, p);
    }
    /* Otherwise, s is empty, no matching with repeating char c exist.
     * No need to consider c any more, back to main scenario. */
    return isMatch(s, p);
}

bool isMatch(char* s, char* p) {
    /* If both string is empty, definitely matching. */
    if (p[0] == '\0' && s[0] == '\0')
        return true;
    /* If both string is not empty, based on p[1] and p[0],
     * recursively check the following string. */
    if (p[0] != '\0' && s[0] != '\0') {
        if (p[1] != '*') {
            if (p[0] != '.' && p[0] != s[0])
                return false;
            return isMatch(&s[1], &p[1]);
        }
        return _isMatch(s, &p[2], p[0]);
    }
    /* If s is empty, p is not, the only chance to match is
     * p only consist of "_*". */
    if (p[0] != '\0' && s[0] == '\0') {
        if (p[1] != '*')
            return false;
        return isMatch(s, &p[2]);
    }
    /* Otherwise, p is empty, s is not, no matching found. */
    return false;
}

int main() {
    printf("%d = 1\n", isMatch("aaav", "a*."));
    printf("%d = 1\n", isMatch("aaavbc", "a*.*"));
    printf("%d = 1\n", isMatch("aab", "c*a*b"));
    printf("%d = 0\n", isMatch("aaa", "aa"));
    printf("%d = 0\n", isMatch("ab", ".*c"));
    printf("%d = 1\n", isMatch("a", "ac*"));
    printf("%d = 1\n", isMatch("abbabaaaaaaacaa", "a*.*b.a.*c*b*a*c*"));
    printf("%d = 1\n", isMatch("baaaaaaacaa", "b.a.*c*b*a*c*"));
    printf("%d = 1\n", isMatch("aa", "a*c*"));
    printf("%d = 0\n", isMatch("aa", "c*"));

    return 0;
}
