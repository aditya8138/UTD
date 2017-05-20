/* 004.Median of Two Sorted Arrays */

#include<stdio.h>
#include<stdlib.h>

#define HalfCeil(x) (x % 2 ? x/2 + 1 : x/2)

double getMedian(int* n, int s) {
    if (s % 2)
        return (double)n[s/2];
    else
        return ((double)(n[s/2-1] + n[s/2]))/2;
}

double findK(int* n1, int s1, int* n2, int s2, int k) {
    printf("\nnums1: ");
    for (int i = 0; i < s1; i++) {
        printf("%d ",n1[i]);
    }
    printf("\nnums2: ");
    for (int i = 0; i < s2; i++) {
        printf("%d ",n2[i]);
    }
    printf("\nk: %d ",k);

    if (s1 == 1)
        return n2[k];

    if (s2 == 1)
        return n1[k];
    // double m1 = getMedian(n1, s1);
    // double m2 = getMedian(n2, s2);
    if (k < (s1/2 + s2/2)) {
        if (n1[s1/2] < n2[s2/2])
            return findK(n1, s1, n2, HalfCeil(s2), k);
        else if (n1[s1/2] > n2[s2/2])
            return findK(n1, HalfCeil(s1), n2, s2, k);
        else // if (n1[s1/2] == n2[s2/2])
            return findK(n1, s1/2, n2, s2/2, k);
    } else if (k > (s1/2 + s2/2)) {
        if (n1[s1/2] < n2[s2/2])
            return findK(&(n1[s1/2]), HalfCeil(s1), n2, s2, k - s1/2);
        else if (n1[s1/2] > n2[s2/2])
            return findK(n1, s1, &(n2[s2/2]), HalfCeil(s2), k - s2/2);
        else // if (n1[s1/2] == n2[s2/2])
            return findK(&(n1[s1/2]), HalfCeil(s1), &(n2[s2/2]), HalfCeil(s2), k - s1/2 - s2/2);
    } else {
        if (n1[s1/2] < n2[s2/2])
            return findK(&(n1[s1/2]), s1/2, n2, s2/2, k - s1/2);
        else if (n1[s1/2] > n2[s2/2])
            return findK(n1, s1/2, &(n2[s2/2]), s2/2, k - s2/2);
        else // if (n1[s1/2] == n2[s2/2])
            return n1[s1/2];
    }
}

// double getMedian(int* nums1, int nums1Size, int* nums2, int nums2Size) {
//     printf("getMedian  ");
//     int i;
//     if (nums1Size < nums2Size) {
//         i = nums2Size - nums1Size;
//         if (i % 2)
//             return (double)nums2[i/2];
//         else
//             return ((double)(nums2[i/2-1] + nums2[i/2]))/2;
//     }
//     if (nums2Size < nums1Size) {
//         i = nums1Size + nums2Size;
//         if (i % 2)
//             return (double)nums1[i/2];
//         else
//             return ((double)(nums1[i/2-1] + nums1[i/2]))/2;
//     }
//     return ((double)(nums1[nums1Size-1] + nums2[0]))/2;
// }

// double getMedianFromThree(int* nums1, int nums1Size, int* nums2, int nums2Size) {
//     int total = nums1Size + nums2Size;
//     int* m = (int*)malloc(total * sizeof(int));
//     int i,j;
//     i = j = 0;
//     for (int k = 0; k < total; k++) {
//         if (i == nums1Size) {
//             m[k] = nums2[j];
//             j++;
//         } else if (j == nums2Size) {
//             m[k] = nums1[i];
//             i++;
//         }else if (nums1[i] < nums2[j]) {
//             m[k] = nums1[i];
//             i++;
//         } else {
//             m[k] = nums2[j];
//             j++;
//         }
//     }
//     printf("\nm: ");
//     for (int i = 0; i < total; i++) {
//         printf("%d ",m[i]);
//     }
//     if (total % 2)
//         return (double)m[total/2];
//     else
//         return ((double)(m[total/2-1] + m[total/2]))/2;
// }

// double findMedianSortedArrays(int* nums1, int nums1Size, int* nums2, int nums2Size) {
//     printf("\nnums1: ");
//     for (int i = 0; i < nums1Size; i++) {
//         printf("%d ",nums1[i]);
//     }
//     printf("\nnums2: ");
//     for (int i = 0; i < nums2Size; i++) {
//         printf("%d ",nums2[i]);
//     }
//     /* Early exit point. */
//     /* One array is empty. */
//     if (nums1Size == 0)
//         return nums2Size % 2 ? (double)nums2[nums2Size/2] :
//             ((double)(nums2[nums2Size/2-1] + nums2[nums2Size/2]))/2;
//     if (nums2Size == 0)
//         return nums1Size % 2 ? (double)nums1[nums1Size/2] : 
//             ((double)(nums1[nums1Size/2-1] + nums1[nums1Size/2]))/2;
//     /* No overlapping between two arrays, easy case. */
//     if (nums1[nums1Size-1] <= nums2[0]) {
//         return getMedian(nums1, nums1Size, nums2, nums2Size);
//     }
//     if (nums2[nums2Size-1] <= nums1[0]) {
//         return getMedian(nums2, nums2Size, nums1, nums1Size);
//     }
//     printf("Otherwise");

//     /* Otherwise, two arrays are overlapping. */
//     /* Case 1: Two arrays size are two and overlapping. */
//     // if (nums1Size == 2 && nums2Size == 2) {
//     //     if (nums1[0] <= nums2[0] && nums1[1] >= nums2[1])
//     //         return ((double)(nums2[1] + nums2[0]))/2;
//     //     if (nums2[0] <= nums1[0] && nums2[1] >= nums1[1])
//     //         return ((double)(nums1[1] + nums1[0]))/2;

//     //     if (nums1[1] >= nums2[1])
//     //         return ((double)(nums1[0] + nums2[1]))/2;
//     //     if (nums2[1] >= nums1[1])
//     //         return ((double)(nums2[0] + nums1[1]))/2;
//     // }
//     // /* Case 3: One array size is three.*/
//     // if (nums1Size == 3 && nums2Size == 3)
//     //     return findMedianSortedArrays(&(nums1[1]), 1, &(nums2[1]), 1);
//     // if (nums1Size == 3)
//     //     return findMedianSortedArrays(&(nums1[1]), 1, nums2, nums2Size);
//     // if (nums2Size == 3)
//     //     return findMedianSortedArrays(nums1, nums1Size, &(nums2[1]), 1);
//     // /* Case 2: One array size is one and the other size is two. */
//     // if (nums1Size == 1 && nums2Size == 2)
//     //     return nums1[0];
//     // if (nums2Size == 1 && nums1Size == 2)
//     //     return nums2[0];
//     /* There is no case 3 when two arrays size are both one,
//      * since that would fit in early exit point. */

//     if (nums1Size <= 3 && nums2Size <= 3)
//         return getMedianFromThree(nums1, nums1Size, nums2, nums2Size);

//     return findMedianSortedArrays(&(nums1[nums1Size/4]), nums1Size - (nums1Size/4) * 2,
//                                 &(nums2[nums2Size/4]), nums2Size - (nums2Size/4) * 2);
// }

int main() {
    int nums1[1] = {3};
    int nums2[3] = {1,4,6};
    printf("%f %f\n", getMedian(nums1,1), getMedian(nums2,3));
    double median = findK(nums1, 1, nums2, 3, 2);
    printf("%f", median);
    return 0;
}
