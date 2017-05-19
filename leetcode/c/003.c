#include<stdio.h>
#include<string.h>

int lengthOfLongestSubstring(char* s) {
    int max = 0;
    int hashtable[127] = {0};
    int i = 0;
    int j = 0;
    while (s[j] != '\0') {
        if (hashtable[s[j]] >= i + 1)
            i = hashtable[s[j]];
        hashtable[s[j]] = j + 1;
        max = max < j - i + 1 ? j - i + 1: max;
        j++;
    }
    return max;
}

int main() {

    char* s = "abcabcbb";

    printf("%s\t%d\n", s, lengthOfLongestSubstring(s));

    return 0;
}
