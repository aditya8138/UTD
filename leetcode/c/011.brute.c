/* 011. Container With Most Water*/
/* Brute force. Time Limit Exceeded More Details. */

#include<stdio.h>

int maxArea(int* height, int heightSize) {
    int max = 0, cur;
    for (int i = 0; i < heightSize; i++) {
        for (int j = i+1; j < heightSize; j++) {
            cur = (j - i) * (height[i] < height[j] ? height[i] : height[j]);
            if (cur > max)
                max = cur;
        }
    }
    return max;
}

int main() {
    int h[10] = {1,2,3,4,5,6,7,8,9,10};
    printf("%d\n", maxArea(h, 10));
    return 0;
}
