/* 075. Sort Colors. */

#include <stdio.h>

void swap(int* a, int* b) {
    *a ^= *b;
    *b ^= *a;
    *a ^= *b;
}

void sortColors(int* nums, int numsSize) {
    int i = 0, j = numsSize - 1;

    int k = i;
    while (k <= j) {
        if (nums[k] == 0) {
            if (i != k)
                swap(&nums[i], &nums[k]);
            i++;
            if (k < i)
                k = i;
        } else if (nums[k] == 2) {
            if (j != k)
                swap(&nums[j], &nums[k]);
            j--;
        } else
            k++;
    }
}

int main() {
    int n[] = {0,2,1,0,2,0,2,0,2,1,1,2,0,2,1,2};
    sortColors(n, 16);
    for (int i = 0; i < 16; i++)
        printf("%d ", n[i]);

    printf("\n");

    int m[] = {2,0,0};
    sortColors(m, 3);
    for (int i = 0; i < 3; i++)
        printf("%d ", m[i]);

    printf("\n");

    return 0;
}
