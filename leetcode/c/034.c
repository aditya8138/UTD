/* 034.Search for a Range. */

#include<stdio.h>
#include<stdlib.h>

int lowerBinarySearch(int* n, int s, int t, int l, int r) {
    int m = (l + r) / 2;
    if (l == r)
        return t == n[l] ? l : -1;
    if (t <= n[m])
        return lowerBinarySearch(n, s, t, l, m);
    /*if (t > n[m])*/
    return lowerBinarySearch(n, s, t, m + 1, r);
}

int upperBinarySearch(int* n, int s, int t, int l, int r) {
    int m = (l + r) / 2;
    m = m * 2 == l + r ? m : m + 1;
    if (l == r)
        return t == n[r] ? r : -1;
    if (t >= n[m])
        return upperBinarySearch(n, s, t, m, r);
    /*if (t < n[m])*/
    return upperBinarySearch(n, s, t, l, m - 1);
}

/**
 * Return an array of size *returnSize.
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* searchRange(int* nums, int numsSize, int target, int* returnSize) {
    int* r = (int*)malloc(2 * sizeof(int));
    r[0] = lowerBinarySearch(nums, numsSize, target, 0, numsSize-1);
    r[1] = upperBinarySearch(nums, numsSize, target, 0, numsSize-1);
    *returnSize = 2;
    return r;

}
int main() {
    int n[] = {1,1,1,3,3,3,5,5,5};

    printf("%d\n", lowerBinarySearch(n, 9, 5, 0, 8));
    printf("%d\n", upperBinarySearch(n, 9, 5, 0, 8));

    return 0;
}
