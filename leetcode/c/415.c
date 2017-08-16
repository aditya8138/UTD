/* 415. Add Strings. */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char* _addStrings(char* num1, char* num2, int len1, int len2) {
    char* sum = (char*)malloc((len1 + 2) * sizeof(char));
    sum[len1 + 1] = '\0';
    int carry = 0;

    int i1 = len1 - 1;
    int i2 = len2 - 1;
    while (i2 >= 0) {
        sum[i1 + 1] = num1[i1] + num2[i2] - '0' + carry;
        if (sum[i1 + 1] > '9') {
            carry = 1;
            sum[i1 + 1] -= 10;
        } else
            carry = 0;
        i1--;
        i2--;
    }

    while (i1 >= 0) {
        sum[i1 + 1] = num1[i1] + carry;
        if (sum[i1 + 1] > '9') {
            carry = 1;
            sum[i1 + 1] -= 10;
        } else
            carry = 0;
        i1--;
    }

    if (carry == 1) {
        sum[0] = '1';
        return sum;
    }
    return sum + 1;
}

char* addStrings(char* num1, char* num2) {
    int len1 = strlen(num1);
    int len2 = strlen(num2);
    if (len1 >= len2)
        return _addStrings(num1, num2, len1, len2);
    return _addStrings(num2, num1, len2, len1);
}

int main() {
    char* n0 = "0";
    char* n1 = "988888888889";
    char* n2 =  "11111111111";
    char* n3 =  "11111111110";

    printf("%s\n", addStrings(n0,n0));
    printf("%s\n%s\n", addStrings(n1,n2), addStrings(n1,n3));

    return 0;
}
