/* 060. Permutation Sequence. */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

int get(char* charset, bool* taken, int nth, int length) {
    for (int i = 0, c = 0; i < length; i++) {
        if (taken[i])
            continue;
        if (c == nth)
            return i;
        c++;
    }
    // Should always return before this line.
    return -1;
}

int getNext(bool* taken, int length) {
    int i = 0;
    while (i < length) {
        if (!taken[i])
            break;
        i++;
    }
    return i;
}

char* getPermutation(int n, int k) {
    char* ret = (char*)malloc((n + 1) * sizeof(char));
    char charset[] = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    bool taken[] = { false, false, false, false, false, false, false, false, false };
    int x[] = { 0, 1, 2, 6, 24, 120, 720, 5040, 40320 };
    int div, mod = k-1, next;
    for (int i = n - 1; i > 0; i--) {
        div = mod / x[i];
        next = get(charset, taken, div, n);
        taken[next] = true;
        ret[n - 1 - i] = charset[next];
        mod = mod % x[i];
    }
    ret[n - 1] = charset[getNext(taken, n)];
    ret[n] = '\0'; // Hand-written EOF.
    return ret;
}

int main() {

    for (int i = 1; i <= 120; i++)
        printf("%d:\t%s\n", i, getPermutation(5, i));

    return 0;
}
