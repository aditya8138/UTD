/* 043.Multiply Strings. */

/* Use function in 415. Add String. */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char* _addStrings(char* num1, char* num2, int len1, int len2) {
    char* sum = (char*)malloc((len1 + 2) * sizeof(char));
    sum[len1 + 1] = '\0';
    int carry = 0;

    int i1 = len1 - 1;
    int i2 = len2 - 1;
    while (i1 >= 0) {
        sum[i1 + 1] = num1[i1] + carry + (i2 >= 0 ? num2[i2] - '0' : 0);
        if (sum[i1 + 1] > '9') {
            carry = 1;
            sum[i1 + 1] -= 10;
        } else
            carry = 0;
        i1--;
        i2--;
    }

    if (carry == 1) {
        sum[0] = '1';
        return sum;
    }
    /* memmove has wierd effect on leetcode.
     *memmove(sum, sum + 1, strlen(sum));
     *return sum;
     */
    return sum + 1;
}

char* addStrings(char* num1, char* num2) {
    int len1 = strlen(num1);
    int len2 = strlen(num2);
    if (len1 >= len2)
        return _addStrings(num1, num2, len1, len2);
    return _addStrings(num2, num1, len2, len1);
}

char* _multiply(char* num1, char n, int digit) {
    int len1 = (strlen(num1));
    char* sum = (char*)malloc((len1 + 2 + digit) * sizeof(char));
    sum[len1 + 1 + digit] = '\0';
    for (int i = len1 + 1; i < len1 + 1 + digit; i++)
        sum[i] = '0';

    int carry = 0;
    for (int i = len1 - 1; i >= 0; i--) {
        int r = (num1[i] - '0') * (n - '0') + carry;
        sum[i + 1] = r % 10 + '0';
        carry = r / 10;
    }
    if (carry != 0) {
        sum[0] = '0' + carry;
        return sum;
    }
    /*memmove(sum, sum + 1, strlen(sum));*/
    /*return sum;*/
    return sum + 1;
}

char* multiply(char* num1, char* num2) {

    if(*num1=='0' || *num2=='0') return "0";

    int len1 = strlen(num1) - 1;
    char* base = (char*)malloc(2 * sizeof(char));
    base[0] = '0';
    base[1] = '\0';
    char* ret;
    for (int i = 0; i <= len1; i++) {
        char* m = _multiply(num2, num1[len1 - i], i);

        ret = addStrings(base, m);
        /* free has wierd effect on leetcode, currently no free used.
         *free(base);
         *free(m);
         */
        base = ret;
    }
    return ret;
}

int main() {
    char* n0 = "0";
    char* n1 = "12345";
    char* n2 = "54321";
    char* n3 = "12345678901";
    char* n4 = "98765432123456789";

    printf("%s\n", multiply(n1, n2));
    printf("%s\n", multiply(n3, n4));

    return 0;
}
