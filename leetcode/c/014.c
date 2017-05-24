/* 014.Longest Common Prefix */

#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>
#include<string.h>

bool e(char** strs, int strsSize, int i) {
    for (int j = 0; j < strsSize; j++)
        if ((strs[j][i] == '\0') || (j < strsSize - 1 && strs[j][i] != strs[j+1][i]))
            return false;
    return true;
}

char* longestCommonPrefix(char** strs, int strsSize) {
    if (strsSize == 0)
        return "";
    int i;
    for (i = 0; e(strs, strsSize, i); i++);
    if (i == 0)
        return "";

    char* p = (char*)malloc((i) * sizeof(char));
    memmove(p, strs[0], i);
    p[i] = '\0';
    return p;
}

int main() {
    char* strs[3] = {"aaa", "aaaa", "aaab"};
    printf("%s\n", longestCommonPrefix(strs, 3));
    return 0;
}
