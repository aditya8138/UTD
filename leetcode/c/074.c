/* Search 2D Matrix. */

#include <stdio.h>
#include <stdbool.h>

/* Binary Search. */
bool _exist(int* n, int l, int r, int target) {
    if (l > r)
        return false;
    int mid = (r + l) / 2;
    if (n[mid] == target)
        return true;
    if (n[mid] > target)
        return _exist(n, l, mid-1, target);
    else
        return _exist(n, mid+1, r, target);
}

bool exist(int* n, int size, int target) {
    if(target >= n[0] && target <= n[size - 1])
        return _exist(n, 0, size-1, target);
    return false;
}

bool searchMatrix(int** matrix, int matrixRowSize, int matrixColSize, int target) {
    if (matrixRowSize == 0 || matrixColSize == 0)
        return false;
    if (target < matrix[0][0] || target > matrix[matrixRowSize - 1][matrixColSize - 1])
        return false;
    if (matrixRowSize == 1)
        return exist(matrix[0], matrixColSize, target);

    int mid = matrixRowSize / 2;

    if (matrix[mid][0] <= target) {
        if (exist(matrix[mid], matrixColSize, target))
            return true;
        return searchMatrix(matrix + mid + 1, matrixRowSize - mid - 1, matrixColSize, target);
    }
    return searchMatrix(matrix, mid, matrixColSize, target);
}

int main() {
    // TO-DO: test case.
    return 0;
}
