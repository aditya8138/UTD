/* 072. Edit Distance. */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int minDistance(char* word1, char* word2) {
    int l1 = strlen(word1);
    int l2 = strlen(word2);
    int** edit = (int**)malloc((l1 + 1) * sizeof(int*));
    for (int i = 0; i <= l1; i++)
        edit[i] = (int*)malloc((l2 + 1) * sizeof(int));

    for (int i = 0; i <= l2; i++)
        edit[0][i] = i;

    for (int i = 0; i <= l1; i++)
        edit[i][0] = i;

    int a,b,c;
    for (int i = 1; i <= l1; i++)
        for (int j = 1; j <= l2; j++) {
            edit[i][j] = edit[i-1][j] < edit[i][j-1] ? edit[i-1][j] + 1 : edit[i][j-1] + 1;
            if (word1[i-1] == word2[j-1]) {
                edit[i][j] = edit[i-1][j-1] < edit[i][j] ? edit[i-1][j-1] : edit[i][j];
            } else {
                edit[i][j] = edit[i-1][j-1] + 1 < edit[i][j] ? edit[i-1][j-1] + 1 : edit[i][j];
            }
        }

    return edit[l1][l2];
}

int main() {
    printf("%d\n", minDistance("ALGORITHM", "ALTRUISTIC"));
    return 0;
}
