/* 006. ZigZag Conversion */
/* The main idea is, in stead of traverse the new string,
 * traverse the original string and put each character in
 * the corresponding position. */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

char* convert(char* s, int numRows) {
    if(numRows == 1)
        return s;

    int l = strlen(s);
    char* r = (char*)malloc((l + 1) * sizeof(char));
    r[l] = '\0';

    for (int i = 0; i < l; i++) {
        //To-do
        //The situation is a bit complicated.
    }

    return r;
}

int main() {
    char* s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    printf("Original string:\t%s\nZigzag string:\t\t%s\n", s, convert(s, 5));

    return 0;
}
