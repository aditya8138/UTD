/* 026. Remove Duplicates from Sorted Array. */

#include<stdio.h>

int _removeDuplicates(int* nums, int numsSize, int i, int j) {
    if (numsSize == 0)
        return 0;
    if (j >= numsSize)
        return i + 1;
    if (nums[i] == nums[j])
        return _removeDuplicates(nums, numsSize, i, j+1);
    i++;
    nums[i] = nums[j];
    j++;
    /* The previous 3 lines can be written as follow:
     * nums[++i] = nums[j++];
     */
    return _removeDuplicates(nums, numsSize, i, j);
}

int removeDuplicates(int* nums, int numsSize) {
    return _removeDuplicates(nums, numsSize, 0, 1);
}

int main() {
    int n[] = {1,1,1,2,2,2,3,3,3,4,4,4,5,5,5};
    int l = removeDuplicates(n, 15);
    for (int i = 0; i < l; i++)
        printf("%d ", n[i]);

    return 0;
}
