/* 001. Two Sum */
/* A version design to return all pair of number..*/

#include<stdio.h>
#include<stdlib.h>

#define INT_MAX +65535
#define UNSIGNED_MAX 4294967295
#define print_triple(t) printf("[%d, %d, %d]\n", t[0], t[1], t[2])

int* twoSum(int* nums, int numsSize, int target) {
    int k = 0, outofloop;
    int** results = (int**)malloc(65535 * sizeof(int*));
    int* tr;
    int rs = 0;
    unsigned int h[131070] = {0};
    for (int i = 0; i < numsSize; i++) {
        if (h[target - nums[i] + INT_MAX + 1] != 0) {
            if (h[nums[i] + INT_MAX + 1] != 0 && target - nums[i] != nums[i])
                continue;
            else {
                tr = (int*)malloc(3 * sizeof(int));
                tr[0] = nums[i];
                tr[1] = target - nums[i];
                tr[2] = target;
                results[k++] = tr;
                rs++;

            }
        }
        h[nums[i] + INT_MAX + 1] = i + 1;
    }
    for (int j = 0; j < rs; j++)
        print_triple(results[j]);

    return results[0];
}

int main() {
    int n[18] = {1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9};
    twoSum(n, 18, 10);
    return 0;
}
