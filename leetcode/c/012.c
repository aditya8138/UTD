/* 012. Integer to Roman */

#include<stdio.h>
#include<stdlib.h>

#define l(x) x < 4 ? x : (x == 4 || x == 9 ? 2 : x - 4);

char* intToRoman(int num) {

    int m = num / 1000;
    int c = num % 1000 / 100;
    int x = num % 100 / 10;
    int i = num % 10;

    /* Calculate storage size. */
    int cl = l(c);
    int xl = l(x);
    int il = l(i);
    int l = m + cl + xl + il;

    char* r = (char*)malloc((l + 1) * sizeof(char));
    r[l] = '\0';

    /* 1000 */
    int k = 0;
    for (int j = 0; j < m; j++) {
        r[k] = 'M';
        k++;
    }

    /* 100 */
    if (c < 4) {
        for (int j = 0; j < c; j++)
            r[k++] = 'C';
    } else if (c == 4) {
        r[k++] = 'C';
        r[k++] = 'D';
    } else if (c == 9) {
        r[k++] = 'C';
        r[k++] = 'M';
    } else {
        r[k++] = 'D';
        for (int j = 0; j < c - 5; j++)
            r[k++] = 'C';
    }

    /* 10 */
    if (x < 4) {
        for (int j = 0; j < x; j++)
            r[k++] = 'X';
    } else if (x == 4) {
        r[k++] = 'X';
        r[k++] = 'L';
    } else if (x == 9) {
        r[k++] = 'X';
        r[k++] = 'C';
    } else {
        r[k++] = 'L';
        for (int j = 0; j < x - 5; j++)
            r[k++] = 'X';
    }

    /* 1 */
    if (i < 4) {
        for (int j = 0; j < i; j++)
            r[k++] = 'I';
    } else if (i == 4) {
        r[k++] = 'I';
        r[k++] = 'V';
    } else if (i == 9) {
        r[k++] = 'I';
        r[k++] = 'X';
    } else {
        r[k++] = 'V';
        for (int j = 0; j < i - 5; j++)
            r[k++] = 'I';
    }

    return r;
}

int main() {
    printf("%s\n", intToRoman(3499));
    printf("%s\n", intToRoman(1234));
    printf("%s\n", intToRoman(531));
    return 0;
}
