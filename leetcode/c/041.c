/* 041. First Missing PositiveFirst Missing Positive */

#include<stdio.h>

void swap(int* nums, int a, int b) {
    nums[a] = nums[a] ^ nums[b];
    nums[b] = nums[a] ^ nums[b];
    nums[a] = nums[b] ^ nums[a];
}

int firstMissingPositive(int* nums, int numsSize) {
    int i = 0;
    while (i < numsSize) {
        if (nums[nums[i] - 1] == nums[i] || nums[i] <= 0 || nums[i] > numsSize) {
            i++;
            continue;
        }
        if (nums[i] != i + 1)
            swap(nums, i, nums[i] - 1);
    }

    for (i = 0; i < numsSize; i++)
        if (nums[i] != i + 1)
            break;

    return i + 1;
}

int main() {
    int n1[10] = {4, 3, -5, 2, -6, 7, -1, 3, 2, 2};
    int n2[10] = {4, 3, -5, 2, -6, 7,  1, 3, 2, 2};
    int n3[10] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    printf("%d\t%d\t%d\n", firstMissingPositive(n1, 10),
                           firstMissingPositive(n2, 10),
                           firstMissingPositive(n3, 10));

    return 0;
}
