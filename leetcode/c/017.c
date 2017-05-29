/* 017. Letter Combinations of a Phone Number. */
/* Simply brute force with lots of code. */

#include<stdio.h>
#include<stdlib.h>

/**
 * Return an array of size *returnSize.
 * Note: The returned array must be malloced, assume caller calls free().
 */

int x(char c) {
    switch (c) {
        case '1': return 0;
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '8': return 3; break;
        case '7':
        case '9': return 4; break;
        default: return 0;
    }
}

char** letterCombinations(char* digits, int* returnSize) {
    int s, i, size, l, step, n;
    *returnSize = 0;
    if (digits[0] == '\0')
        return NULL;
    for (size = 0, s = 1; digits[size] != '\0'; size++) {
        s = s * x(digits[size]);
    }
    if (s <= 0)
        return NULL;
    *returnSize = s;

    /* At this point, only exist 23456789. */
    printf("At this point, only exist 23456789. \nLength is %d\n", *returnSize);
    char** r = (char**)malloc(*returnSize * sizeof(char*));

    for (i = 0; i < *returnSize; i++) {
        r[i] = (char*)malloc((size + 1) * sizeof(char));
        r[i][size] = '\0';
    }

    for (i = 0, step = 1, n = 1; digits[i] != '\0'; i++) {
        switch (digits[i]) {
            case '2':
                s = s / 3;
                for (int m = 0; m < n; m++) {
                    for (int k = m * s * 3; k < m * s * 3 + s; k++) {
                        r[k][i] = 'a';
                        r[k+s][i] = 'b';
                        r[k+s*2][i] = 'c';
                    }
                }
                n = 3 * n;
                break;
            case '3':
                s = s / 3;
                for (int m = 0; m < n; m++) {
                    for (int k = m * s * 3; k < m * s * 3 + s; k++) {
                        r[k][i] = 'd';
                        r[k+s][i] = 'e';
                        r[k+s*2][i] = 'f';
                    }
                }
                n = 3 * n;
                break;
            case '4':
                s = s / 3;
                for (int m = 0; m < n; m++) {
                    for (int k = m * s * 3; k < m * s * 3 + s; k++) {
                        r[k][i] = 'g';
                        r[k+s][i] = 'h';
                        r[k+s*2][i] = 'i';
                    }
                }
                n = 3 * n;
                break;
            case '5':
                s = s / 3;
                for (int m = 0; m < n; m++) {
                    for (int k = m * s * 3; k < m * s * 3 + s; k++) {
                        r[k][i] = 'j';
                        r[k+s][i] = 'k';
                        r[k+s*2][i] = 'l';
                    }
                }
                n = 3 * n;
                break;
            case '6':
                s = s / 3;
                for (int m = 0; m < n; m++) {
                    for (int k = m * s * 3; k < m * s * 3 + s; k++) {
                        r[k][i] = 'm';
                        r[k+s][i] = 'n';
                        r[k+s*2][i] = 'o';
                    }
                }
                n = 3 * n;
                break;
            case '7':
                s = s / 4;
                for (int m = 0; m < n; m++) {
                    for (int k = m * s * 4; k < m * s * 4 + s; k++) {
                        r[k][i] = 'p';
                        r[k+s][i] = 'q';
                        r[k+s*2][i] = 'r';
                        r[k+s*3][i] = 's';
                    }
                }
                n = 4 * n;
                break;
            case '8':
                s = s / 3;
                for (int m = 0; m < n; m++) {
                    for (int k = m * s * 3; k < m * s * 3 + s; k++) {
                        r[k][i] = 't';
                        r[k+s][i] = 'u';
                        r[k+s*2][i] = 'v';
                    }
                }
                n = 3 * n;
                break;
            case '9':
                s = s / 4;
                for (int m = 0; m < n; m++) {
                    for (int k = m * s * 4; k < m * s * 4 + s; k++) {
                        r[k][i] = 'w';
                        r[k+s][i] = 'x';
                        r[k+s*2][i] = 'y';
                        r[k+s*3][i] = 'z';
                    }
                }
                n = 4 * n;
                break;
            default: printf("Error!"); return 0;
        }
    }
    return r;
}

int main() {
    int r;
    char* a = "7";
    char** lc = letterCombinations(a, &r);

    for (int i = 0; i < r; i++)
        printf("%s\n", lc[i]);

    return 0;
}
