/* 006. ZigZag Conversion */
/* The main idea is traverse the new string, copy the corresponding character 
 * from the original string. */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

char* convert(char* s, int numRows) {
    if(numRows == 1)
        return s;

    int l = strlen(s);
    char* r = (char*)malloc((l + 1) * sizeof(char));
    r[l] = '\0';

    int i = 0, j, row = 1, step1, step2;
    while (row <= numRows) {
        j = row - 1;
        memmove(&r[i], &s[j], 1);
        i++;

        step1 = 2 * (numRows - row);
        step2 = 2 * (row - 1);

        while (i < l && j < l) {
            if (step1) {
                j += step1;
                if (j >= l)
                    break;
                memmove(&r[i], &s[j], 1);
                i++;
            }
            if (step2) {
                j += step2;
                if (j >= l)
                    break;
                memmove(&r[i], &s[j], 1);
                i++;
            }
        }
        row++;
    }

    return r;
}

int main() {
    char* s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    printf("Original string:\t%s\nZigzag string:\t\t%s\n", s, convert(s, 5));

    return 0;
}
