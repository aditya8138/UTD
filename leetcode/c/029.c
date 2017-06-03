/* 029. Divide Two Integers. */
/* Using bitwise operation. */

#include<stdio.h>

#define MAX_INT +2147483647
#define MIN_INT -2147483648

int divide(int dividend, int divisor) {
    if (divisor == 1)
        return dividend;
    if (divisor == MIN_INT)
        return dividend == MIN_INT ? 1 : 0;

    if (dividend >= 0) {
        if (divisor > 0) {
            int ans = 0;
            int n = dividend;
            int d = divisor;
            while(n >= d) {
                int a = d;
                int m = 1;
                while((a << 1 >> 1 == a) && (a<<1) < n) {
                    a <<= 1;
                    m <<= 1;
                }
                ans += m;
                n -= a;
            }
            return ans;
        } else
            return 0 - divide(dividend, 0 - divisor);
    } else {
        if (dividend == -2147483648) {
            if (divisor == -1)
                return MAX_INT;
            if (divisor < 0)
                return 1 + divide(divisor - dividend, 0 - divisor);
            else
                return 0 - 1 - divide(0 - divisor - dividend, divisor);
        }
        if (divisor < 0) {
            return divide(0 - dividend, 0 - divisor);
        } else
            return 0 - divide(0 - dividend, divisor);
    }
}

int main() {
    printf("%d\n", divide(MIN_INT, 5));
    return 0;
}
