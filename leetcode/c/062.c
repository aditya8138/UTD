/* 062. Unique Paths */

#include <stdio.h>
#include <stdlib.h>

int uniquePaths(int m, int n) {
    int *p[m + 1];
    for (int i = 0; i < m + 1; i++)
        p[i] = (int*)malloc((n + 1) * sizeof(int));

    for (int i = 0; i < m + 1; i++)
        p[i][n] = 0;

    for (int i = i; i < n + 1; i++)
        p[m][i] = 0;

    p[m][n-1] = 1;

    for (int i = m-1; i >= 0; i--)
        for (int j = n-1; j >= 0; j--)
            p[i][j] = p[i][j+1] + p[i+1][j];

    return p[0][0];
}

int main() {
    printf("%d", uniquePaths(5,8));
    return 0;
}
