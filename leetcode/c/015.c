/* 015.3Sum */
/* Solution based on twoSum function. 
 * The running time is unbearably long. */

#include<stdio.h>
#include<stdlib.h>

#define IMAX +100000
#define DMAX +300000

#define print_triple(t) printf("[%d, %d, %d]\n", t[0], t[1], t[2])

int comp (const void * a, const void * b) {
    return *(int *)a - *(int *)b;
}

int** threeSum(int* nums, int numsSize, int* returnSize) {
    /* Sort the array before other operation. */
    qsort(nums, numsSize, sizeof (int), comp);

    int** ts = (int**)malloc(18000 * sizeof(int*));

    /* Traverse the nums array.
     * For each number encountered, if not seen before,
     * get a 3Sum from elements after the number.*/
    int* tr;
    int r, x = 0, target;
    int nmax = nums[numsSize - 1];
    *returnSize = 0;
    for (int i = 0; nums[i] <= 0; i++) {
        if (i > 0 && nums[i] == nums[i-1])
            continue;

        /* First hashtable, mark existence of number in array. */
        unsigned int h[DMAX] = {0};

        /* Second hashtable, mark triple with same number,
            * such as: -10 + 5 + 5 = 0. */
        unsigned int d[DMAX] = {0};
        target = -nums[i];
        for (int j = numsSize - 1; j > i; j--) {
            x = target - nums[j];
            if (x > nmax)
                break;
            if (h[x + IMAX + 1] != 0) {
                if (h[nums[j] + IMAX + 1] != 0 &&
                        (x != nums[j] || (x == nums[j] && d[nums[j] + IMAX + 1] != 0)))
                    continue;
                else {
                    ts[*returnSize] = (int*)malloc(3 * sizeof(int));
                    ts[*returnSize][0] = nums[j];
                    ts[*returnSize][1] = x;
                    ts[*returnSize][2] = nums[i];
                    *returnSize += 1;
                    if (x == nums[j])
                        d[nums[j] + IMAX + 1] = 1;
                }
            }
            h[nums[j] + IMAX + 1] = j + 1;
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
