/* 033. Search in Rotated Sorted Array. */

#include<stdio.h>
#include<stdlib.h>

void nextPermutation(int* nums, int numsSize) {
    if (numsSize == 1)
        return;
    for (int i = numsSize - 2; i >= 0; i--) {
        if (nums[i] > nums[i+1])
            continue;
        for (int j = numsSize - 1; j > i; j--) {
            if (nums[i] >= nums[j])
                continue;
            nums[i] ^= nums[j];
            nums[j] ^= nums[i];
            nums[i] ^= nums[j];

            /* The next 5 line is just sorting. Since at this point,
             * all elements on the right must be in descending order.
             * Thus reverse each pair would give a sorted array.*/
            for(int k = i+1; k <= (i + numsSize - 1)/2; k++) {
                nums[k] ^= nums[numsSize + i - k];
                nums[numsSize + i - k] ^= nums[k];
                nums[k] ^= nums[numsSize + i - k];
            }
            return;
        }
    }

    /* The same with line 19. */
    for(int k = 0; k < numsSize - 1 - k; k++) {
        nums[k] ^= nums[numsSize - 1 - k];
        nums[numsSize - 1 - k] ^= nums[k];
        nums[k] ^= nums[numsSize - 1 - k];
    }
}

int main() {
    int n[10] = {4,5,6,6,3,2,1,9,8,7};

     for (int j = 0; j < 10; j++)
         printf("%d ", n[j]);
     printf("\n");

     nextPermutation(n, 10);

     for (int j = 0; j < 10; j++)
         printf("%d ", n[j]);
     printf("\n");

     return 0;
}

