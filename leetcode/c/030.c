/* 030. Substring with Concatenation of All Words. */
/* Solution exceed time limits. */

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<stdbool.h>

int comp (const void * a, const void * b) {
    return *(int *)a - *(int *)b;
}

int same(char* haystack, char* needle) {
    if (*needle == '\0')
        return 1;
    if (*haystack == '\0')
        return -1;
    if (*needle == *haystack)
        return same(haystack+1, needle+1);
    return 0;
}

int strStr(char* haystack, char* needle) {
    if (*needle == '\0' && *haystack == '\0')
        return 0;
    for (int i = 0; haystack[i] != '\0'; i++) {
        int r = same(&haystack[i], needle);
        if (r == 1)
            return i;
        else if (r == -1)
            return -1;
        else
            ;
    }
    return -1;
}

int validation(int* n, int size, int interval) {
    int* x = (int*)malloc(size * sizeof(int));
    for (int i = 0; i < size; i++)
        x[i] = n[i];
    qsort(x, size, sizeof (int), comp);
    for (int i = 1; i < size; i++) {
        if (x[i-1] + interval != x[i])
            return -1;
    }
    int _x = x[0];
    free(x);
    return _x;
}

int* strStrs(char* t, char* s, int tl, int sl, int* size) {
    *size = 0;
    int n = strStr(t, s);
    if (n == -1)
        return NULL;
    int* o = (int*)malloc((tl - sl + 1) * sizeof(int));
    o[*size] = n;
    printf("%d ", n);
    (*size) += 1;
    while (n + sl + 1 <= tl) {
        n = strStr(t + o[(*size)-1] + 1, s);
        if (n == -1)
            break;
        o[*size] = o[(*size)-1] + 1 + n;
        (*size) += 1;
    }
    /*int* no= realloc(o, (*size) * sizeof(int));*/
    return o;
}

int** getALLIndex(int** iw, int* lw, int size, int* returnSize) {
    int _size = 1;
    for (int i = 0; i < size; i++)
        _size *= lw[i];

    *returnSize = _size;

    /*printf("%d\n", _size);*/
    int** r = (int**)malloc(_size * sizeof(int*));

    int i, n, s = _size, l;
    for (i = 0; i < _size; i++) {
        r[i] = (int*)malloc((size) * sizeof(int));
    }

    for (i = 0, n = 1; i < size; i++) {
        l = lw[i];
        s = s / l;
        for (int m = 0; m < n; m++) {
            for (int k = m * s * l; k < m * s * l + s; k++) {
                for (int j = 0; j < l; j++) {
                    r[k + j * s][i] = iw[i][j];
                }
            }
        }
        n = l * n;
    }

    /*for (int q = 0; q < _size; q++) {*/
        /*for (int p = 0; p < size; p++)*/
            /*printf("%d ", r[q][p]);*/
        /*printf("\n");*/
    /*}*/

    return r;
}

/**
 * Return an array of size *returnSize.
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* findSubstring(char* s, char** words, int wordsSize, int* returnSize) {

    int* result = (int*)malloc(10 * sizeof(int));
    int slength = strlen(s);
    int length = strlen(words[0]);
    *returnSize = 0;
    if (length * wordsSize > slength)
        return NULL;

    int** iwords = (int**)malloc(wordsSize * sizeof(int*));
    int* lwords = (int*)malloc(wordsSize * sizeof(int));
    for (int i = 0; i < wordsSize; i++) {
        iwords[i] = strStrs(s, words[i], slength, length, &lwords[i]);
        /*printf("%d \n", lwords[i]);*/
    }

    /*
     *for (int i = 0; i < wordsSize; i++) {
     *    printf("[");
     *    for (int j = 0; j < lwords[i]; j++)
     *        printf("%d ", iwords[i][j]);
     *    printf("]\n");
     *}
     */

    int p;
    int** w = getALLIndex(iwords, lwords, wordsSize, &p);

    for (int i = 0; i < p; i++) {
        for (int j = 0; j < wordsSize; j++)
            printf("%d ", w[i][j]);
        printf("\n");
    }

    int* h = (int*)malloc(slength * sizeof(int));
    for (int i = 0; i < length; i++)
        h[i] = 0;
    for (int i = 0; i < p; i++) {
        int start = validation(w[i], wordsSize, length);
        if (start != -1 && h[start] == 0) {
        /*printf("%d\n", start);*/
            h[start] = 1;
            result[*returnSize] = start;
            (*returnSize) += 1;
        }
    }

    /*
     *int* nresult= realloc(result, (*returnSize) * sizeof(int));
     */

    return result;
}

int main() {
    char** cl = (char**)malloc(3 * sizeof(char*));
    cl[0] = "ab";
    cl[1] = "bb";
    cl[2] = "foo";
    char* s = "abaaabbb";
    int r;
    int* rs = findSubstring(s, cl, 2, &r);
    for (int i = 0; i < r; i++)
        printf("%d ", rs[i]);

    return 0;
}
