/* 009.Palindrome Number */

#include<stdio.h>
#include <stdbool.h>

/* A little different from 007, no need to consider overflow,
 * since overflow number can not be palindrome. */
int reverse(int x, int r) {
    if (x == 0)
        return r;
    return reverse(x / 10, (r * 10) + (x % 10));
}

bool isPalindrome(int x) {
    if (x < 0)
        return false;
    return reverse(x, 0) == x;
}

int main() {
    printf("%d\n", isPalindrome(123454321));
    printf("%d\n", isPalindrome(-123454321));
    return 0;
}
