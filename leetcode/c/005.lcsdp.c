/* 005.Longest Palindromic Substring. */
/* A solution based on longest common substring with DP. */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

int isPalindrome(const char* s, int length) {
    if (length <= 1)
        return 1;
    if (s[0] == s[length-1])
        return isPalindrome(&(s[1]), length - 2);
    else
        return 0;
}

/*The original DP solution with O(M*N) space.*/
char* longestPalindromeLCSDP(char* s) {
    /* A strange error occurs if m is dynamicly allocated. */
    int m[1001][1001] = {0};

    int l= strlen(s);
    int max = 1;
    char* ret = s;

    for (int i = 0; i < l; i++) {
        for (int j = l - 1; j >= 0; j--) {
            if (s[i] == s[j]) {
                m[i][j] = (i >= 1) ? m[i - 1][j + 1] + 1 : 1;
                if (m[i][j] > max && isPalindrome(&s[i - m[i][j] + 1], m[i][j])) {
                    max = m[i][j];
                    ret = &s[i - m[i][j] + 1];
                }
            } else
                m[i][j] = 0;
        }
    }

    char* p = (char*)malloc((max + 1) * sizeof(char));
    memmove(p, ret, max);
    p[max] = '\0';
    return p;
}

/*The optimized DP solution with O(N) space, but more code.*/
char* longestPalindromeLCSDPOP(char* s) {

    int l= strlen(s);
    /* A strange error occurs if m is dynamicly allocated. */
    // int* m1 = (int*)malloc((l*2) * sizeof(int));
    // int* m2 = (int*)malloc((l*2) * sizeof(int));;
    // for (int k = 0; k < l; k++) {
    //     m1[k] = m2[k] = 0;
    // }

    int m1[1001] = {0};
    int m2[1001] = {0};
    int flag = 1;

    int max = 1;
    char* ret = s;

    for (int i = 0; i < l; i++) {
        if (flag) {
            for (int j = l - 1; j >= 0; j--) {
                if (s[i] == s[j]) {
                    m2[j] = m1[j + 1] + 1;
                    if (m2[j] > max && isPalindrome(&s[i - m2[j] + 1], m2[j])) {
                        max = m2[j];
                        ret = &s[i - m2[j] + 1];
                    }
                } else
                    m2[j] = 0;
            }
            flag = 0;
        } else {
            for (int j = l - 1; j >= 0; j--) {
                if (s[i] == s[j]) {
                    m1[j] = m2[j + 1] + 1;
                    if (m1[j] > max && isPalindrome(&s[i - m1[j] + 1], m1[j])) {
                        max = m1[j];
                        ret = &s[i - m1[j] + 1];
                    }
                } else
                    m1[j] = 0;
            }
            flag = 1;
        }
    }

    char* p = (char*)malloc((max + 1) * sizeof(char));
    memmove(p, ret, max);
    p[max] = '\0';
    return p;

    /**(ret+max) = '\0';*/
    /*return ret;*/
}

int main() {
    printf("%s\n", longestPalindromeLCSDP("banana"));
    printf("%s\n", longestPalindromeLCSDPOP("banana"));
    return 0;
}
