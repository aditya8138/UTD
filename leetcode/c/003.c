#include<stdio.h>
#include<string.h>

int lengthOfLongestSubstring(char* s) {
    int i = 0;
    int max = 0;
    while (s[i] != '\0') {
        int hashtable[127] = {0};
        int j = i;
        int count = 0;
        while (hashtable[s[j]] != 1 && s[j] != '\0') {
            hashtable[s[j]] = 1;
            count++;
            j++;
        }
        max = max < count ? count : max;
        i++;
    }
    return max;
}

int main() {

    char* s = "asdfasdfgasdfgh";

    printf("%s\t%d\n", s, lengthOfLongestSubstring(s));

    return 0;
}
