#include<stdio.h>
#include<string.h>

int lengthOfLongestSubstring(char* s) {
    int max = 0;
    int hashtable[127] = {0};
    int i = 0;
    int j = 0;
    while (s[j] != '\0') {
        while (hashtable[s[j]] < i + 1 && s[j] != '\0') {
            hashtable[s[j]] = j + 1;
            j++;
        }
        printf("%d %d\n", i, j);
        max = max < j - i ? j - i: max;
        if (s[j] != '\0') {
            i = hashtable[s[j]];
            hashtable[s[j]] = j + 1;
            j++;
        }
    }
    return max;
}

int main() {

    char* s = "c";

    printf("%s\t%d\n", s, lengthOfLongestSubstring(s));

    return 0;
}
