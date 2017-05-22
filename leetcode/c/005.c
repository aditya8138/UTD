#include<stdio.h>
#include<stdlib.h>
#include<string.h>

#define MAX_LENGTH 1000

// int* lp(const char* s, int prev, int start) {
//     int r[2] = {0,1};
//     if (s[start] == '\0')
//         return r;
    
//     int ignore = lp(s, start + 1, start + 1);
//     if 
// }

int getLength(const char* s) {
    int l = 0;
    while (s[l] != '\0')
        l++;
    return l;
}

int isPalindrome(const char* s, int length) {
    /*int i = 0;*/
    /*while (i < length) {*/
        /*if (s[i] != s[length - i - 1])*/
            /*return 0;*/
        /*i++;*/
    /*}*/
    /*return 1;*/
    if (length <= 1)
        return 1;
    if (s[0] == s[length-1])
        return isPalindrome(&(s[1]), length - 2);
    else
        return 0;
}

int getCurLongestPalindrom(const char* s, const int length) {
    int max = 1;
    int i = 1;
    while (s[i] != '\0') {
        if (isPalindrome(s, i+1)) {
            max = i+1;
            /*printf("%d\n", max);*/
        }
        i++;
    }
    return max;
}

char* longestPalindrome(char* s) {
    int i = 0;
    int lmax = 1;
    int imax = 0;
    int tmax = 0;
    int length = getLength(s);
    while (s[i] != '\0') {
        tmax = getCurLongestPalindrom(&(s[i]), length - i);
        if (tmax > lmax) {
            lmax = tmax;
            imax = i;
        }
        i++;
    }
    char* p = (char*)malloc(lmax * sizeof(char));
    memmove(p, s+imax, lmax);

    return p;
}

int main() {
    printf("%d", isPalindrome("abbb", 4));

    printf("%s", longestPalindrome("abbadd"));

    return 0;
}
