/* 071. Simplify Path. */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char* simplifyPath(char* path) {
    int length = strlen(path);
    char* ret = (char*)malloc(length * sizeof(char));
    char* iret = ret;
    *iret = *path;
    iret++;
    path++;
    while (*path != '\0') {
        if (*path == '/') {
            if (*(iret-1) == '/') {
                path++;
                continue;
            }
            if (*(iret-1) == '.' && *(iret-2) == '.' && *(iret-3) == '/') {
                if (iret - 3 == ret)
                    iret -= 2;
                else
                    for (iret -= 3; iret - 1 != ret && *(iret - 1) != '/' ; iret--);
                path++;
                continue;
            }
            if (*(iret-1) == '.' && *(iret-2) == '/') {
                iret--;
                path++;
                continue;
            }
        }
        *iret = *path;
        iret++;
        path++;
    }

    // The following is redundant code, though not sure how to reuse.
    if (*(iret-1) == '.' && *(iret-2) == '.' && *(iret-3) == '/') {
        if (iret - 3 == ret)
            iret -= 2;
        else
            for (iret -= 3; iret - 1 != ret && *(iret - 1) != '/' ; iret--);
    } else if (*(iret-1) == '.' && *(iret-2) == '/') {
        iret--;
    }
    if (*(iret-1) == '/' && iret - 1 != ret)
        iret--;
    *iret = '\0';

    return ret;
}

int main() {
    printf("%s\n", simplifyPath("/a/b/c/d/"));
    printf("%s\n", simplifyPath("/a/../c/.."));
    return 0;
}
