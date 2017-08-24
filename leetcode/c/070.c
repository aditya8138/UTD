/* 070. Climb Stairs. */

#include <stdio.h>
#include <stdlib.h>

int climbStairs(int n) {
    int* m = (int*)malloc(n * sizeof(int));

    m[n-1] = 1;
    m[n-2] = 1;

    for (int i = n-3; i >=0; i--)
        m[i] = m[i+1] + m[i+2];

    int ret = m[0];
    free(m);
    return ret;
}

int main() {
    printf("%d == %d\n", 1134903170, climbStairs(45));
    return 0;
}
