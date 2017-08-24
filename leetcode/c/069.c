/* 069. Sqrt(x) */
/* Spaghetti Code. Sum of odd number is the power 2 of some number. */

#include <stdio.h>

int mySqrt(int x) {
    int i, sum;
    for (i = 1, sum = 0; sum < sum + i * 2 - 1 && (sum += i * 2 - 1) < x; i++);
    return sum == x ? i : i-1;
}

int main() {
    printf("%d == %d\n", 46340, mySqrt(2147483647));
    return 0;
}
