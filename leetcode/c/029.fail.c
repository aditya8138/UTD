/* 029.Divide Two Integers. */
/* Intuitive solution but not work. 
 * Too many calls on stack results in segmentation fault. */

#include<stdio.h>

#define MAX_INT +2147483647
#define MIN_INT -2147483648

int divide(int dividend, int divisor) {
    if (dividend >= 0) {
        if (divisor > 0) {
            if (dividend >= divisor)
                return divide(dividend - divisor, divisor) + 1;
            return 0;
        } else
            return 0 - divide(dividend, 0 - divisor);
    } else {
        if (divisor < 0) {
            if (dividend == -2147483648 && divisor == -1)
                return MAX_INT;
            return divide(0 - dividend, 0 - divisor);
        } else
            return 0 - divide(0 - dividend, divisor);
    }
}

int main() {
    printf("%d\n", divide(MAX_INT, 20000));
    return 0;
}
