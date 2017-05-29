/* 015.3Sum Closest */

#include<stdio.h>
#include<stdlib.h>
#include<stdbool.h>

int comp (const void * a, const void * b) {
    return *(int *)a - *(int *)b;
}

bool _threeSum(int* nums, int numsSize, int _target) {
    int l, r, target;
    for (int i = 0; i < numsSize - 2; i++) {
        /* Skip duplicating target. */
        if ((i > 0 && nums[i] == nums[i-1]))
            continue;

        target = _target - nums[i];

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
            return true;
        }
    }

    return false;
}

int threeSumClosest(int* nums, int numsSize, int target) {
    /* Sort the array before other operation. */
    qsort(nums, numsSize, sizeof (int), comp);

    int i = 1;
    while (true) {
        if (_threeSum(nums, numsSize, target))
            return target;
        if (_threeSum(nums, numsSize, target + i))
            return target + i;
        if (_threeSum(nums, numsSize, target - i))
            return target - i;
        i++;
    }
    return 0;
}

int main() {
    int n[] = {-1, 0, 1, 2, -1, -4, -5, -9, 3,5,6,7,8,4};
    int size;

    printf("%d\n", threeSumClosest(n, 14, 5));

    return 0;
}
