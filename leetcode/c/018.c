/* 018.4Sum */
/* Solution based on threeSum function. */

#include<stdio.h>
#include<stdlib.h>

#define print_quadruplets(t) printf("[%d, %d, %d, %d]\n", t[0], t[1], t[2], t[3])

int comp (const void * a, const void * b) {
    return *(int *)a - *(int *)b;
}

/**
 * Return an array of arrays of size *returnSize.
 * Note: The returned array must be malloced, assume caller calls free().
 */
int** fourSum(int* nums, int numsSize, int target, int* returnSize) {
    /* Define triplet size. */
    int size = 4 * sizeof(int);
    /* Sort the array before other operation. */
    qsort(nums, numsSize, sizeof (int), comp);
    int** ts = (int**)malloc(100 * sizeof(int*));
    *returnSize = 0;

    for (int f = 0; f < numsSize - 3; f++) {
        if ((f > 0 && nums[f] == nums[f-1]))
            continue;
        int starget, l, r;
        for (int i = f + 1; i < numsSize - 2; i++) {
            /* Skip duplicating target. */
            if ((i > f + 1 && nums[i] == nums[i-1]))
                continue;

            starget = target - nums[f] - nums[i];

            l = i + 1;
            r = numsSize - 1;
            while (l < r) {
                /* If sum is smaller, increase left index. */
                if (nums[l] + nums[r] < starget) {
                    l++;
                    continue;
                }
                /* If sum is larger, decrease right index. */
                if (nums[l] + nums[r] > starget) {
                    r--;
                    continue;
                }
                /* Otherwise, find one answer. */
                ts[*returnSize] = (int*)malloc(size);
                ts[*returnSize][0] = nums[f];
                ts[*returnSize][1] = nums[i];
                ts[*returnSize][2] = nums[l];
                ts[*returnSize][3] = nums[r];
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

    }
    return ts;
}

int main() {
    int n[] = {-1, 0, 1, 2, -1, -4, -5, -9, 3,5,6,7,8,4};
    int size;

    int** ts = fourSum(n, 14, 6, &size);

    for (int i = 0; i < size; i++) {
        print_quadruplets(ts[i]);
    }

    return 0;
}
