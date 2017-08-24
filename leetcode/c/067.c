/* 067. Add Binary. */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char* _addBinary(char* a, char* b, int al, int bl) {

    int l = al > bl ? al + 1 : bl + 1;
    char* ret = (char*)malloc((l + 1) * sizeof(char));

    ret[l] = '\0';

    int carry = 0;
    int sum = 0;

    for (l--, al--, bl--; bl >= 0; al--, bl--, l--) {
        sum = a[al] + b[bl] - '0' * 2 + carry;
        carry = sum / 2;
        ret[l] = sum % 2 + '0';
    }

    for (;al >= 0; al--, l--) {
        sum = a[al] - '0' + carry;
        carry = sum / 2;
        ret[l] = sum % 2 + '0';
    }

    if (carry == 1) {
        ret[0] = '1';
        return ret;
    }

    return ret + 1;
}

char* addBinary(char* a, char* b) {
    int al = strlen(a);
    int bl = strlen(b);

    return al > bl ? _addBinary(a,b,al,bl) : _addBinary(b,a,bl,al);
}

int main() {
    char* a = "1111111111";
    char* b = "1";

    printf("%s\n", addBinary(a,b));

    return 0;
}
