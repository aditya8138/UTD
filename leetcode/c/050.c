/* 050. Pow(x, n). */

#include <stdio.h>

double _myPow(double x, int n) {
    if (n == 0)
        return 1;
    return (n % 2 == 0) ? _myPow(x * x, n/2) : x * _myPow(x * x, n/2);
}

double myPow(double x, int n) {
    if (n > 0)
        return _myPow(x, n);
    else
        return 1 / _myPow(x, -n);
}

int main() {
    printf("%f\n%f\n", myPow(8.88023, 3), 700.28148);
    return 0;
}
