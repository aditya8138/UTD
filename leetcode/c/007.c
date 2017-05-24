/* 007.Reverse Integer */

#include<stdio.h>

/* A recursion to calculate reverse number. */
int _reverse(int x, int r) {
    if (x == 0)
        return r;
    if ((r * 10 / 10) != r)
        return 0;
    int re = (r * 10) + (x % 10);
    if ((r > re && r > 0) || (r < re && r < 0))
        return 0;
    return _reverse(x / 10, re);
}

int reverse(int x) {
    return _reverse(x, 0);
}

int main() {
    printf("%d\n", reverse(1234578));
    return 0;
}
