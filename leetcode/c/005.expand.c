/* 004.Median of Two Sorted Arrays */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

char* longestPalindrome(char* s) {
    int length = strlen(s);
    int imax, lmax = 0;
    int i = 0;
    int j, k, tmax;
    while (s[i] != '\0') {
        /* If current i cannot exceed known max length. */
        if (length - i < lmax / 2)
            break;
        /* Mark the left side. */
        j = i;
        /* Skip duplacating character to find the right side. */
        k = i;
        while (s[k] == s[i])
            k++;
        /* Step to next different character than s[i]. */
        i = k;
        /* Expand at both side until find different characters. */
        while (j >= 0 && s[j] == s[k-1]) {
            j--;
            k++;
        }
        tmax = k - j - 2;
        if (tmax > lmax) {
            lmax = tmax;
            imax = j + 1;
        }
    }
    char* p = (char*)malloc((lmax + 1) * sizeof(char));
    memmove(p, s+imax, lmax);
    p[lmax] = '\0';
    return p;

    /*This two commands can run on leetcode but result in bus error in local machine (macOS).*/
    /*
     *s[imax + lmax] = '\0';
     *return s + imax;
     */
}

int main() {
    printf("%s", longestPalindrome("dddddddddddd"));
    return 0;
}
