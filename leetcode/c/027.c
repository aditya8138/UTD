/* 027. Remove Element. */

#include<stdio.h>

int _removeElement(int* nums, int numsSize, int val, int i, int j) {
    if (numsSize == 0)
        return 0;
    if (j >= numsSize)
        return i;
    if (nums[j] == val)
        return _removeElement(nums, numsSize, val, i, j+1);
    nums[i] = nums[j];
    return _removeElement(nums, numsSize, val, i+1, j+1);
}

int removeElement(int* nums, int numsSize, int val) {
    return _removeElement(nums, numsSize, val, 0, 0);
}

int main() {
    int n[] = {4,4,4,5,5,5,5,5,5,5,5,5,5,5,10,10,10,10};
    int l = removeElement(n, 18, 5);
    for (int i = 0; i < l; i++)
        printf("%d ", n[i]);

    return 0;
}
