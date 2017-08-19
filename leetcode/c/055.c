/* 055. Jump Game. */

#include <stdio.h>
#include <stdbool.h>

int findFurthest(int* nums, int numsSize, int start, int end) {
    int furthest = 0;
    for (int i = end; i >= start; i--)
        if (i + nums[i] > furthest)
            furthest = i + nums[i];
    return furthest;
}

bool canJump(int* nums, int numsSize) {
    int start = 0;
    int end = nums[0];
    int furthest = 0;
    while (end < numsSize - 1) {
        furthest = findFurthest(nums, numsSize, start, end);
        if (furthest <= end)
            return false;
        start = end + 1;
        end = furthest;
    }
    return true;
}

int main() {
    int n1[] = {2,3,1,1,4};
    int n2[] = {3,2,1,0,4};

    printf("%d\n", canJump(n1,5));
    printf("%d\n", canJump(n2,5));

    return 0;
}
