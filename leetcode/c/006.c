/* 006. ZigZag Conversion */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>

char* convert(char* s, int numRows) {
    if(numRows == 1)
        return s;

    int l = strlen(s);
    char* r = (char*)malloc((l + 1) * sizeof(char));
    r[l] = '\0';



    int i = 0;
    int row = 1;
    int step1, step2;
    while (row <= numRows) {
        //printf("row = %d, i = %d\n", row, i);
        memmove(&r[i], &s[row - 1], 1);
        i++;
        //printf("i = %d\n",  i);

        //printf("%s\n", r);
        step1 = 2 * (numRows - row);
        step2 = 2 * (row - 1);

        //printf("step1: %d  step2: %d\n", step1, step2);
        int j = row - 1;
        while (i < l && j < l) {
            if (step1 != 0) {
                if (j + step1 >= l)
                    break;
                j += step1;
                memmove(&r[i], &s[j], 1);
                //printf("%s\n", r);
                i++;
        //printf("i = %d\n",  i);
            }
            if (step2 != 0) {
                if (j + step2 >= l)
                    break;
                j += step2;
                memmove(&r[i], &s[j], 1);
                //printf("%s\n", r);
                i++;
        //printf("i = %d\n",  i);
            }
        }
        row++;
        //printf("%s\n", r);
    }

    return r;
}

int main() {
    char* s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    printf("Original string:\t%s\nZigzag string:\t\t%s\n", s, convert(s, 5));

    return 0;
}
