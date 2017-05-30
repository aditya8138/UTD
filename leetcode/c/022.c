/* 022.Generate Parentheses */
/* The idea is somewhat called backtracking, which I have no idea what it is. */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

/**
 * Return an array of size *returnSize.
 * Note: The returned array must be malloced, assume caller calls free().
 */
char** generateParenthesis(int n, int* returnSize) {
    int plength = n * 2 + 1;
    char** r = (char**)malloc(65535 * sizeof(char*));

    char* p = (char*)malloc(plength * sizeof(char));
    p[plength - 1] = '\0';
    for (int i = 0; i < n; i++) {
        p[i]   = '(';
        p[i+n] = ')';
    }

    int index = 0;
    do {
        r[index] = p;
        p = (char*)malloc(plength * sizeof(char));
        memmove(p, r[index], plength);
        p[plength] = '\0';
        int i = 0, left = n, right = n;
        for (i = plength - 2; i >= 0; i--) {
            if (p[i] == ')') {
                right--;
                continue;
            }
            if (left - 1 <= right) {
                p[i] = '\0';
                left--;
            } else {
                p[i] = '\0';
                left--;
                break;
            }
        }
        if (p[0] != '\0') {
            p[i] = ')';
            right++;
            i++;
            for (; left < n; left++, i++)
                p[i] = '(';
            for (; right < n; right++, i++)
                p[i] = ')';
        }
        index++;
    } while (p[0] != '\0');

    *returnSize = index;

    return r;
}

int main() {
    int s = 0;
    char** p = generateParenthesis(5, &s);

    for (int i = 0; i < s; i++)
        printf("%s\n", p[i]);

    return 0;
}
