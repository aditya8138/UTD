/* 015.3Sum */
/* Optimized solution based on twoSum function. 
 * The running time is reasonably fast. */

#include<stdio.h>
#include<stdlib.h>

#define print_triple(t) printf("[%d, %d, %d]\n", t[0], t[1], t[2])

int comp (const void * a, const void * b) {
    return *(int *)a - *(int *)b;
}

int** threeSum(int* nums, int numsSize, int* returnSize) {
    /* Define triplet size. */
    int size = 3 * sizeof(int);
    /* Sort the array before other operation. */
    qsort(nums, numsSize, sizeof (int), comp);

    int** ts = (int**)malloc(18000 * sizeof(int*));

    int target, l, r;
    *returnSize = 0;
    for (int i = 0; i < numsSize - 2; i++) {
        /* Skip duplicating target. */
        if ((i > 0 && nums[i] == nums[i-1]))
            continue;

        target = -nums[i];

        l = i + 1;
        r = numsSize - 1;
        while (l < r) {
            /* If sum is smaller, increase left index. */
            if (nums[l] + nums[r] < target) {
                l++;
                continue;
            }
            /* If sum is larger, decrease right index. */
            if (nums[l] + nums[r] > target) {
                r--;
                continue;
            }
            /* Otherwise, find one answer. */
            ts[*returnSize] = (int*)malloc(size);
            ts[*returnSize][0] = nums[i];
            ts[*returnSize][1] = nums[l];
            ts[*returnSize][2] = nums[r];
            *returnSize += 1;
            /* Skip all the duplicating character at both side. */
            while (l < r && nums[l] == nums[l+1])
                l++;
            while (l < r && nums[r] == nums[r-1])
                r--;
            /* Move on. */
            l++;
            r--;
        }
    }

    return ts;
}

int main() {
    int n[] = {-1, 0, 1, 2, -1, -4, -5, -9, 3,5,6,7,8,4};
    int size;

    int** ts = threeSum(n, 14, &size);

    for (int i = 0; i < size; i++) {
        print_triple(ts[i]);
    }

    return 0;
}
