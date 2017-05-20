/* 004.Median of Two Sorted Arrays */

#include<stdio.h>
#include<stdlib.h>
#include<time.h>

#define HalfCeil(x) (x % 2 ? x/2 + 1 : x/2)

double findK(int* n1, int s1, int* n2, int s2, int k) {

    if (s1 == 0)
        return n2[k-1];

    if (s2 == 0)
        return n1[k-1];

    if (n1[s1-1] <= n2[0])
        return (double)(k > s1 ? n2[k-s1-1] : n1[k-1]);

    if (n2[s2-1] <= n1[0])
        return (double)(k > s2 ? n1[k-s2-1] : n2[k-1]);

    if (s1 == 1) {
        if (n1[0] < n2[s2/2]) {
            if (k < s2/2 + 1)
                return findK(n1,s1,n2,s2/2,k);
            else if (k > s2/2 + 1)
                return findK(n1,s1,&(n2[s2/2]),HalfCeil(s2),k-s2/2);
            else
                return findK(n1,s1,n2,s2/2,k);
        } else {
            if (k < s2/2)
                return findK(n1,0,n2,s2/2,k);
            else if (k > s2/2)
                return findK(n1,s1,&(n2[s2/2]),HalfCeil(s2),k-s2/2);
            else
                return findK(n1,0,n2,s2/2,k);
        }
    }
    if (s2 == 1) {
        if (n2[0] < n1[s1/2]) {
            if (k < s1/2 + 1)
                return findK(n1,s1/2,n2,s2,k);
            else if (k > s1/2 + 1)
                return findK(&(n1[s1/2]),HalfCeil(s1),n2,s2,k-s1/2);
            else
                return findK(n1,s1/2,n2,s2,k);
        } else {
            if (k < s1/2)
                return findK(n2,0,n1,s1/2,k);
            else if (k > s1/2)
                return findK(n2,s2,&(n1[s1/2]),HalfCeil(s1),k-s1/2);
            else
                return findK(n2,0,n1,s1/2,k);
        }
    }
    /* s1/2 denotes the number of integers that are less than n1[s1/2]. */
    if (n1[s1/2] > n2[s2/2]) {
        if (k <= s1/2 + s2/2)
            return findK(n1,s1/2,n2,s2,k);
        else
            return findK(n1,s1, &(n2[s2/2]), HalfCeil(s2), k-s2/2);
    } else {
        if (k <= s1/2 + s2/2)
            return findK(n2,s2/2,n1,s1,k);
        else
            return findK(n2,s2, &(n1[s1/2]), HalfCeil(s1), k-s1/2);
    }
}

double findMedianSortedArrays(int* nums1, int nums1Size, int* nums2, int nums2Size) {
    int k = (nums1Size + nums2Size) / 2;
    if ((nums1Size + nums2Size) % 2)
        return findK(nums1, nums1Size, nums2, nums2Size, k+1);
    else
        return (findK(nums1, nums1Size, nums2, nums2Size, k) +
                findK(nums1, nums1Size, nums2, nums2Size, k+1)) / 2;
}

int main() {
    srand(time(NULL));
    /* Generate array 1, with size no more than 10.  */
    int s1 = rand() % 10 + 1;
    int* nums1 = (int*)malloc(s1 * sizeof(int));
    printf("\nnums1[%d]: {", s1);
    for (int i = 0; i < s1; i++) {
        nums1[i] = i ? nums1[i-1] + rand() % 100 : rand() % 100;
        printf(" %d ", nums1[i]);
    }
    /* Generate array 2, with size no more than 15. */
    int s2 = rand() % 15 + 1;
    int* nums2 = (int*)malloc(s2 * sizeof(int));
    printf("}\nnums2[%d]: {", s2);
    for (int i = 0; i < s2; i++) {
        nums2[i] = i ? nums2[i-1] + rand() % 100 : rand() % 100;
        printf(" %d ", nums2[i]);
    }
    printf("}\n");

    /* Compute the median. */
    printf("\nMedian: %f", findMedianSortedArrays(nums1, s1,nums2,s2));
    return 0;
}
