/* 048. Rotate Image. */

#include <stdio.h>
#include <stdlib.h>

void rotate(int** matrix, int matrixRowSize, int matrixColSize) {
    for (int i = 0; i <= matrixRowSize / 2 - 1; i++) {
        int nw_x = i, nw_y = i;
        int ne_x = i, ne_y = matrixRowSize - 1 - i;
        int sw_x = matrixRowSize - 1 - i, sw_y = i;
        int se_x = matrixRowSize - 1 - i, se_y = matrixRowSize - 1 - i;

        for (int x = 0; x <= matrixRowSize - 2 - i * 2; x++)  {
            int temp = matrix[nw_x][nw_y + x];
            matrix[nw_x][nw_y + x] = matrix[sw_x - x][sw_y];
            matrix[sw_x - x][sw_y] = matrix[se_x][se_y - x];
            matrix[se_x][se_y - x] = matrix[ne_x + x][ne_y];
            matrix[ne_x + x][ne_y] = temp;
        }
    }
}

int main() {
    /*
     *int matrix[5][5] = { 1,2,3,4,5,
     *                     6,7,8,9,10,
     *                     11,12,13,14,15,
     *                     16,17,18,19,20,
     *                     21,22,23,24,25 };
     */

    int m1[] = {1,2,3,4,5};
    int m2[] = {1,2,3,4,5};
    int m3[] = {1,2,3,4,5};
    int m4[] = {1,2,3,4,5};
    int m5[] = {1,2,3,4,5};

    int** matrix = (int**)malloc(5 * sizeof(int*));

    matrix[0] = m1;
    matrix[1] = m2;
    matrix[2] = m3;
    matrix[3] = m4;
    matrix[4] = m5;

    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++)
            printf("%d\t", matrix[i][j]);
        printf("\n");
    }

    rotate(matrix, 5, 5);

    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++)
            printf("%d\t", matrix[i][j]);
        printf("\n");
    }

    return 0;
}
