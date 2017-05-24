/* 013.Roman to Integer */

#include<stdio.h>

int romanToInt(char* s) {
    int i = 0, r = 0;
    while (s[i] != '\0') {
        if (s[i] == 'M') {
            r += 1000;
            i++;
        } else if (s[i] == 'D') {
            r += 500;
            i++;
        } else if (s[i] == 'C') {
            if (s[i+1] == 'D') {
                r += 400;
                i += 2;
            } else if (s[i+1] == 'M') {
                r += 900;
                i += 2;
            } else {
                r += 100;
                i ++;
            }
        } else if (s[i] == 'L') {
            r += 50;
            i++;
        } else if (s[i] == 'X') {
            if (s[i+1] == 'L') {
                r += 40;
                i += 2;
            } else if (s[i+1] == 'C') {
                r += 90;
                i += 2;
            } else {
                r += 10;
                i ++;
            }
        } else if (s[i] == 'V') {
            r += 5;
            i++;
        } else /*if (s[i] == 'I')*/ {
            if (s[i+1] == 'V') {
                r += 4;
                i += 2;
            } else if (s[i+1] == 'X') {
                r += 9;
                i += 2;
            } else {
                r += 1;
                i ++;
            }
        }
    }
    return r;
}

int main() {
    printf("%d\n", romanToInt("DCXXI"));
    return 0;
}
