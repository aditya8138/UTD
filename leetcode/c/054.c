/* 054. Spiral Matrix. */

#include <stdio.h>
#include <stdlib.h>

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* spiralOrder(int** matrix, int matrixRowSize, int matrixColSize) {

    int size = matrixRowSize * matrixColSize;

    int* ret = (int*)malloc(size * sizeof(int));

    /* Two special case when row or col == 1,
     * just copy the first col or row. */
    if (matrixRowSize == 1) {
        for (int i = 0; i < matrixColSize; i++)
            ret[i] = matrix[0][i];
        return ret;
    }

    if (matrixColSize == 1) {
        for (int i = 0; i < matrixRowSize; i++)
            ret[i] = matrix[i][0];
        return ret;
    }

    /* Otherwise, spiral order. */

    int round = 0;
    int i = 0;

    while (i < size - 1) {
        for (int j = round; j < matrixColSize - 1 - round; j++, i++)
            ret[i] = matrix[round][j];
        for (int j = round; j < matrixRowSize - 1 - round; j++, i++)
            ret[i] = matrix[j][matrixColSize - 1 - round];
        for (int j = round; j < matrixColSize- 1 - round; j++, i++)
            ret[i] = matrix[matrixRowSize - 1 - round][matrixColSize - 1 - j];
        for (int j = round; j < matrixRowSize - 1 - round; j++, i++)
             ret[i] = matrix[matrixRowSize - 1 - j][round];
        round++;
    }

    if ( matrixRowSize == matrixColSize )
        ret[i] = matrix[matrixRowSize/2][matrixColSize/2];

    return ret;
}

int main() {
    int l1[] = {1,2,3,4,5};
    int l2[] = {6,7,8,9,10};
    int l3[] = {11,12,13,14,15};
    int l4[] = {16,17,18,19,20};
    int l5[] = {21,22,23,24,25};

    int** m1 = (int**)malloc(5 * sizeof(int*));
    m1[0] = l1;
    m1[1] = l2;
    m1[2] = l3;
    m1[3] = l4;
    m1[4] = l5;

    int* ret1 = spiralOrder(m1, 5, 5);
    for (int i = 0; i < 25; i++)
        printf("%d ", ret1[i]);
    printf("\n");

    int* ret2 = spiralOrder(m1, 4, 5);
    for (int i = 0; i < 20; i++)
        printf("%d ", ret2[i]);
    printf("\n");

    int* ret3 = spiralOrder(m1, 5, 3);
    for (int i = 0; i < 15; i++)
        printf("%d ", ret3[i]);
    printf("\n");

    int* ret4 = spiralOrder(m1, 1, 5);
    for (int i = 0; i < 5; i++)
        printf("%d ", ret4[i]);
    printf("\n");

    int* ret5 = spiralOrder(m1, 5, 1);
    for (int i = 0; i < 5; i++)
        printf("%d ", ret5[i]);
    printf("\n");

    free(m1);
    free(ret1);
    free(ret2);
    free(ret3);
    free(ret4);
    free(ret5);
    return 0;
}
