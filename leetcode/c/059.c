/* 059. Spiral Matrix II. */

#include <stdio.h>
#include <stdlib.h>

/**
 * Return an array of arrays.
 * Note: The returned array must be malloced, assume caller calls free().
 */
int** generateMatrix(int n) {
    if (n == 0)
        return NULL;
    int size = n * n;
    int** ret = (int**)malloc(n * sizeof(int*));
    for (int i = 0; i < n; i++)
        ret[i] = (int*)malloc(n * sizeof(int));

    int round = 0;
    int i = 1;

    while (i < size) {
        for (int j = round; j < n - 1 - round; j++, i++)
            ret[round][j] = i;
        for (int j = round; j < n - 1 - round; j++, i++)
            ret[j][n - 1 - round] = i;
        for (int j = round; j < n- 1 - round; j++, i++)
            ret[n - 1 - round][n - 1 - j] = i;
        for (int j = round; j < n - 1 - round; j++, i++)
             ret[n - 1 - j][round] = i;
        round++;
    }
    if (n / 2 * 2 != n)
        ret[n/2][n/2] = size;
    return ret;
}

int main() {
    int n[] = {3,5,8,10,11};
    for (int k = 0; k < 5; k++) {
        int** m = generateMatrix(n[k]);
        for (int i = 0; i < n[k]; i++) {
            for (int j = 0; j < n[k]; j++)
                printf("%d\t", m[i][j]);
            printf("\n");
        }
        printf("\n");
    }

    return 0;
}
