/* 008.String to Integer (atoi) */

#include<stdio.h>

#define INT_MAX	+2147483647
#define INT_MIN	-2147483648

int _atoi(const char* str, int re, int sig, int flag) {
    if (flag) {
        /* flag = 1, started. */
        if (str[0] > '9' || str[0] < '0')
            return re;
        if ((re * 10 / 10) != re)
            return sig + 1 ? INT_MAX : INT_MIN;
        int r = re * 10 + (str[0] - '0') * sig;
        if (r < re && sig == 1)
            return INT_MAX;
        if (r > re  && sig == -1)
            return INT_MIN;
        return _atoi(&str[1], r, sig, 1);
    } else {
        /* flag = 0, not started yet. */
        if (str[0] == ' ')
            return _atoi(&str[1], 0, 1, 0);
        if (str[0] == '+')
            return _atoi(&str[1], 0, 1, 1);
        if (str[0] == '-')
            return _atoi(&str[1], 0, -1, 1);
        if (str[0] > '9' || str[0] < '0')
            return 0;
        return _atoi(str, 0, 1, 1);
    }
}

int myAtoi(char* str) {
    return _atoi(str, 0, 1, 0);
}

int main(){
    printf("%d", myAtoi("    2147483648"));
    return 0;
}
