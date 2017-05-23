/* 005.Longest Palindromic Substring */
/* A naive solution, brutle force. */

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

int getCurLongestPalindrom(const char* s, const int length, const int lmax) {
    int i = length;
    while (i > 1) {
        if (i < lmax)
            return 1;
        if (isPalindrome(s, i))
            return i;
        i--;
    }
    return i;
}

char* longestPalindrome(char* s) {
    int i = 0;
    int lmax = 1;
    int imax = 0;
    int tmax = 0;
    int length = strlen(s);
    while (s[i] != '\0') {
        if (imax + lmax == length)
            break;
        tmax = getCurLongestPalindrom(&(s[i]), length - i, lmax);
        if (tmax > lmax) {
            lmax = tmax;
            imax = i;
        }
        i++;
    }
    char* p = (char*)malloc((lmax + 1) * sizeof(char));
    memmove(p, s+imax, lmax);
    p[lmax] = '\0';

    return p;
}

int main() {
    printf("%s", longestPalindrome("abbadd"));
    return 0;
}
