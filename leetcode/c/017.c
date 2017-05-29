/* 017. Letter Combinations of a Phone Number. */
/* A revise version, reduce code and improve reuse.. */

#include<stdio.h>
#include<stdlib.h>

/**
 * Return an array of size *returnSize.
 * Note: The returned array must be malloced, assume caller calls free().
 */

char** letterCombinations(char* digits, int* returnSize) {
    int nmap[] = {0, 0, 3, 3, 3, 3, 3, 4, 3, 4};
    char* cmap[] = {
        "",
        "",
        "abc",
        "def",
        "ghi",
        "jkl",
        "mno",
        "pqrs",
        "tuv",
        "wxyz"};
    int s, i, size, l, c, step, n;
    *returnSize = 0;
    if (digits[0] == '\0')
        return NULL;
    for (size = 0, s = 1; digits[size] != '\0'; size++) {
        if (digits[size] < '1' && digits[size] > '9')
            return NULL;
        s = s * nmap[digits[size] - '0'];
    }
    *returnSize = s;

    /* At this point, only exist 23456789. */
    printf("At this point, only exist 23456789. \nLength is %d\n", *returnSize);
    char** r = (char**)malloc(*returnSize * sizeof(char*));

    /* Allocate all memory for all possible answers. */
    for (i = 0; i < *returnSize; i++) {
        r[i] = (char*)malloc((size + 1) * sizeof(char));
        r[i][size] = '\0';
    }

    /* Fill out the tables. */
    for (i = 0, step = 1, n = 1; digits[i] != '\0'; i++) {
        c = digits[i] - '0';    //Get current number.
        l = nmap[c];            //Get possible characters' number.
        s = s / l;              //Calc repeat count.
        for (int m = 0; m < n; m++)
            for (int k = m * s * l; k < m * s * l + s; k++)
                for (int j = 0; j < l; j++)
                    r[k + j * s][i] = cmap[c][j];
        n = l * n;
    }
    return r;
}

int main() {
    int r;
    char* a = "7492";
    char** lc = letterCombinations(a, &r);

    for (int i = 0; i < r; i++)
        printf("%s\n", lc[i]);

    return 0;
}
