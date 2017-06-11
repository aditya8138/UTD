/* 033. Search in Rotated Sorted Array. */

#include<stdio.h>
#include<stdlib.h>

int search(int* nums, int numsSize, int target) {

    int i = 0, j;
    while (i < numsSize && nums[i] != target)
        i++;

    /* If target not found */
    if (i == numsSize)
        return -1;

    if (i == 0)
        return 0;

    /* Otherwise, rotate. */
    int* buff = (int*)malloc((i+1) * sizeof(int));
    for (j = 0; j < i; j++)
        buff[j] = nums[j];
    for (j = i; j < numsSize; j++)
        nums[j - i] = nums[j];
    for (j = 0; j < i; j++)
        nums[numsSize - i + j] = buff[j];
    free(buff);

    return i;
}

int main() {
    int n[10] = {1,2,3,4,5,6,7,8,9,10};

     for (int j = 0; j < 10; j++)
         printf("%d ", n[j]);
     printf("\n");

     printf("%d\n", search(n, 10, 3));

     for (int j = 0; j < 10; j++)
         printf("%d ", n[j]);
     printf("\n");

     return 0;
}

