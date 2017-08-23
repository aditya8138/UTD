/* 065. Valid Number. */
/* This solution is a total mess! */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

bool _isNumber(int state, char* s) {
    switch(state) {
        // init state.
        case 0:
            if (*s == ' ')
                return _isNumber(0, s+1);
            if (*s == '-' || *s == '+')
                return _isNumber(1, s+1);
            if (*s >= '0' && *s <= '9')
                return _isNumber(2, s+1);
            if (*s == '.')
                return _isNumber(6, s+1);
            return false;

        // +/- detected at init state, next char must be number or dot.
        case 1:
            if (*s >= '0' && *s <= '9')
                return _isNumber(2, s+1);
            if (*s == '.')
                return _isNumber(6, s+1);
            return false;

        // First number char occur, next char can only be number, dot or e.
        case 2:
            if (*s >= '0' && *s <= '9')
                return _isNumber(2, s+1);
            if (*s == '.')
                return _isNumber(3, s+1);
            if (*s == 'e')
                return _isNumber(4, s+1);
            if (*s == ' ')
                return _isNumber(9, s+1);
            if (*s == '\0')
                return true;
            return false;

        // Dot occur, only number can occur.
        case 3:
            if (*s >= '0' && *s <= '9')
                return _isNumber(3, s+1);
            if (*s == '\0')
                return true;
            if (*s == 'e')
                return _isNumber(4, s+1);
            if (*s == ' ')
                return _isNumber(9, s+1);
            return false;

        // After e occur, only number or +/- can occur and at least one number exists.
        case 4:
            if (*s >= '0' && *s <= '9')
                return _isNumber(5, s+1);
            if (*s == '+' || *s == '-')
                return _isNumber(10, s+1);
            return false;

        // After e, only number can occur.
        case 5:
            if (*s >= '0' && *s <= '9')
                return _isNumber(5, s+1);
            if (*s == ' ')
                return _isNumber(9, s+1);
            if (*s == '\0')
                return true;
            return false;

        // If dot appear before any number, follow char must be number.
        case 6:
            if (*s >= '0' && *s <= '9')
                return _isNumber(7, s+1);
            return false;
        // After number appear in case 6, can appear e, number or end.
        case 7:
            if (*s >= '0' && *s <= '9')
                return _isNumber(7, s+1);
            if (*s == 'e')
                return _isNumber(4, s+1);
            if (*s == ' ')
                return _isNumber(9, s+1);
            if (*s == '\0')
                return true;
            return false;

        // blank occur, only blank can occur.
        case 9:
            if (*s == ' ')
                return _isNumber(9, s+1);
            if (*s == '\0')
                return true;
            return false;

        // If +/- occur after e, only number can appear, and at least one number exist.
        case 10:
            if (*s >= '0' && *s <= '9')
                return _isNumber(5, s+1);
            return false;


        default: return false;
    }
}

bool isNumber(char* s) {
    return _isNumber(0, s);
}

int main() {
    char* s[10];
    s[0] = "123";
    s[1] = " 0.1 ";
    s[2] = "bc";
    s[3] = "1 a";
    s[4] = "2e10";
    s[5] = "2e10.";
    s[6] = ".4";
    s[7] = "43.e4";
    s[8] = ".2e81";
    s[9] = "3.  ";
    for (int i = 0; i < 10; i++)
        printf("%d\n", isNumber(s[i]));

    return 0;
}
