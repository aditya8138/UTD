/* 028.Implement strStr() */

#include<stdio.h>

int same(char* haystack, char* needle) {
    if (*needle == '\0')
        return 1;
    if (*haystack == '\0')
        return -1;
    if (*needle == *haystack)
        return same(haystack+1, needle+1);
    return 0;
}

int strStr(char* haystack, char* needle) {
    if (*needle == '\0' && *haystack == '\0')
        return 0;
    for (int i = 0; haystack[i] != '\0'; i++) {
        int r = same(&haystack[i], needle);
        if (r == 1)
            return i;
        else if (r == -1)
            return -1;
        else
            ;
    }

    return -1;
}

int main() {
    char* h = "ABCDEFGHIJAB";
    char* n = "EFG";

    printf("%d\n", strStr(h,n));

    return 0;
}
