/* 036. Valid Sudoku */

#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>

bool isValidSudoku(char** board, int boardRowSize, int boardColSize) {
    int map[27][10] = {0};
    for (int i = 0; i < boardRowSize; i++) {
        for (int j = 0; j < boardColSize; j++) {
            if (board[i][j] == '.')
                continue;
            if (map[i][board[i][j] - '0'] == 1
                || map[9+j][board[i][j] - '0'] == 1
                || map[18 + i / 3 * 3  + j/3][board[i][j] - '0'] == 1)
                return false;
            map[i][board[i][j] - '0'] =
                map[9+j][board[i][j] - '0'] =
                map[18 + i / 3 * 3  + j/3][board[i][j] - '0'] = 1;

            /* // map each char to three line.
             *map[i][j] = board[i][j];
             *map[9+j][i] = board[i][j];
             *map[18 + i / 3 * 3  + j/3][i % 3 * 3 + j % 3] = board[i][j];
             */
        }
    }
    // for (int i = 0; i < 27; i++) {
    //     for (int j = 0; j < 9; j++) {
    //         printf("%d", map[i][j]);
    //     }
    //     printf("\n");
    // }
    return true;
}

int main() {
    char* b[9];
    b[0] = ".87654321";
    b[1] = "2........";
    b[2] = "3........";
    b[3] = "4........";
    b[4] = "5........";
    b[5] = "6........";
    b[6] = "7........";
    b[7] = "8........";
    b[8] = "9........";

    printf("%d\n", isValidSudoku(b, 9, 9));

    return 0;
}
