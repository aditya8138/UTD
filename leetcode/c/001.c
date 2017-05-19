/* 001. Two Sum */

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* twoSum(int* nums, int numsSize, int target) {
    int i, j, k, *result, outofloop;
    result = (int*)malloc(sizeof(int)*2);
    int hashtable1[65535] = {0};
    int hashtable2[65535] = {0};
    for (i = 0; i <= numsSize - 1; i++) {
        if( nums[i] >=0 )
            hashtable1[nums[i]] = 1;
        else
            hashtable2[-nums[i]] = 1;
    }
    for (i = 0, outofloop=1; i <= numsSize - 1 && outofloop==1; i++){
        j = target - nums[i];
        if (j >= 0) {
            if (hashtable1[j]) {
                for(k = i+1; k <= numsSize - 1; k++) {
                    if(nums[k] == j) {
                        result[0] = i;
                        result[1] = k;
                        outofloop=0;; 
                    }
                }
            }
        } else {
            if (hashtable2[-j]) {
                for(k = i+1; k <= numsSize - 1; k++) {
                    if(nums[k] == j) {
                        result[0] = i;
                        result[1] = k;
                        outofloop=0;
                    }
                }
            }
        }
    }
    return result;
}
