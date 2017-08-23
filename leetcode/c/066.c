/* 066. Plus One */

#include <stdio.h>
#include <stdlib.h>

/**
 * Return an array of size *returnSize.
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* plusOne(int* digits, int digitsSize, int* returnSize) {
    int* ret = (int*)malloc((digitsSize + 1) * sizeof(int));

    int sum = digits[digitsSize - 1] + 1;
    ret[digitsSize] = sum % 10;
    int carry = sum / 10;
    for (int i = digitsSize - 2; i >= 0; i--) {
        if (carry == 0) {
            ret[i+1] = digits[i];
            continue;
        }
        sum = digits[i] + carry;
        ret[i+1] = sum % 10;
        carry = sum / 10;
    }
    ret[0] = carry;
    *returnSize = digitsSize + ret[0];

    return ret[0] ? ret : ret+1;
}

int main() {
    int d1[] = {8,9,9,9,9,9,9,9,9};
    int d2[] = {9,9,9,9,9,9,9,9,9};

    int returnSize;

    int* nd1 = plusOne(d1, 9, &returnSize);
    for (int i = 0; i < returnSize; i++)
        printf("%d ", nd1[i]);
    printf("\n");

    int* nd2 = plusOne(d2, 9, &returnSize);
    for (int i = 0; i < returnSize; i++)
        printf("%d ", nd2[i]);
    printf("\n");

    return 0;
}
