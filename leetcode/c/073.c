/* 073. Set Matrix Zeroes. */

#include <stdio.h>

void setZeroes(int** matrix, int matrixRowSize, int matrixColSize) {
    int col = 1;
    for (int i = 0; i < matrixRowSize; i++) {
        for (int j = 0; j < matrixColSize; j++) {
            if (matrix[i][j] == 0) {
                if (j == 0)
                    col = 0;
                else
                    matrix[i][0] = matrix[0][j] = 0;
            }
        }
    }

    for (int i = 1; i < matrixRowSize; i++)
        if (matrix[i][0] == 0)
            for (int j = 0; j < matrixColSize; j++)
                matrix[i][j] = 0;
    for (int j = 1; j < matrixColSize; j++)
        if (matrix[0][j] == 0)
            for (int i = 0; i < matrixRowSize; i++)
                matrix[i][j] = 0;
    if (matrix[0][0] == 0)
        for (int j = 1; j < matrixColSize; j++)
            matrix[0][j] = 0;
    if (col == 0)
        for (int i = 0; i < matrixRowSize; i++)
            matrix[i][0] = 0;
}

int main() {
    // TO-DO: test case is not finished.
    return 0;
}
