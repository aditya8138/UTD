/* 015.3Sum */
/* Solution based on twoSum function. 
 * The running time is unbearably long. */

#include<stdio.h>
#include<stdlib.h>

#define IMAX +100000
#define DMAX +400000

#define print_triple(t) printf("[%d, %d, %d]\n", t[0], t[1], t[2])

/* In memory sort. */
void quick_sort(int *array, int maxlength, int left, int right) {
    if (maxlength <= 0)
        return;
    if (left >= right)
        return;
    int i = left;
    int j = right;
    int key = array[left];
    while (i < j) {
        for (; i < j && key <= array[j]; j--);
        array[i] = array[j];
        for (; i < j && key >= array[i]; i++);
        array[j] = array[i];
    }
    array[i] = key;
    quick_sort(array, i - 1 - left, left, i - 1);
    quick_sort(array, right - i - 1, i + 1, right);
}

int** twoSum(int target, int* nums, int numsSize, int* returnSize) {

    /* First hashtable, mark existence of number in array. */
    unsigned int h[DMAX] = {0};

    /* Second hashtable, mark triple with same number,
     * such as: -10 + 5 + 5 = 0. */
    unsigned int d[DMAX] = {0};
    int* tr;
    int** results = (int**)malloc(65535 * sizeof(int*));
    *returnSize = 0;

    for (int i = 0; i < numsSize; i++) {
        if (h[-target - nums[i] + IMAX + 1] != 0) {
            if (h[nums[i] + IMAX + 1] != 0 && 
                    (-target - nums[i] != nums[i] ||
                    (-target - nums[i] == nums[i] && 
                     d[nums[i] + IMAX + 1] != 0)))
                continue;
            else {
                tr = (int*)malloc(3 * sizeof(int));
                tr[0] = nums[i];
                tr[1] = -target - nums[i];
                tr[2] = target;
                results[(*returnSize)++] = tr;
                if (-target - nums[i] == nums[i])
                    d[nums[i] + IMAX + 1] = 1;
            }
        }
        h[nums[i] + IMAX + 1] = i + 1;
    }
    return results;
}

int** threeSum(int* nums, int numsSize, int* returnSize) {
    /* Sort the array before other operation. */
    quick_sort(nums, numsSize, 0, numsSize - 1);

    int** threeSum = (int**)malloc(65535 * sizeof(int*));

    /* Hashtable used to check duplicate number. */
    int h[DMAX] = {0};
    /* Traverse the nums array.
     * For each number encountered, if not seen before,
     * get a 3Sum from elements after the number.*/
    int r, index = 0;
    *returnSize = 0;
    for (int i = 0; i < numsSize; i++) {
        if (nums[i] > 0)
            break;
        if (h[nums[i] + IMAX + 1])
            continue;
        int** sts = twoSum(nums[i], &nums[i+1] , numsSize - i - 1, &r);
        *returnSize += r;
        for (int j = 0; j < r; j++)
            threeSum[index++] = sts[j];
        h[nums[i] + IMAX + 1] = i + 1;
    }
    return threeSum;
}

int main() {
    int n[] = {-1, 0, 1, 2, -1, -4};
    int size;

    int** ts = threeSum(n, 6, &size);

    for (int i = 0; i < size; i++) {
        print_triple(ts[i]);
    }

    return 0;
}
