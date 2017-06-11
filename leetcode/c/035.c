/* 035. Search Insert Position. */

#include<stdio.h>
#include<stdlib.h>

int _searchInsert(int* n, int s, int t, int l, int r) {
    if (l == r)
        return n[l] >= t ? l : l + 1;
    int m = (l + r) / 2;
    if (t == n[m])
        return m;
    if (t < n[m])
        return _searchInsert(n, s, t, l, m);
    return _searchInsert(n, s, t, m + 1, r);
}

int searchInsert(int* nums, int numsSize, int target) {
    return _searchInsert(nums, numsSize, target, 0, numsSize - 1);
}

int main() {
    int n[] = {1,2,3,5,6,7,9};
    printf("%d\n", searchInsert(n, 7, 0));
    printf("%d\n", searchInsert(n, 7, 1));
    printf("%d\n", searchInsert(n, 7, 2));
    printf("%d\n", searchInsert(n, 7, 3));
    printf("%d\n", searchInsert(n, 7, 4));
    printf("%d\n", searchInsert(n, 7, 5));
    printf("%d\n", searchInsert(n, 7, 6));
    printf("%d\n", searchInsert(n, 7, 7));
    printf("%d\n", searchInsert(n, 7, 8));
    printf("%d\n", searchInsert(n, 7, 9));
    printf("%d\n", searchInsert(n, 7, 10));
    return 0;
}
