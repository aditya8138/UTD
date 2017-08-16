/* 044. Wildcard Matching. */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

bool isMatch(char* s, char* p) {

    int slen = strlen(s);
    int plen = strlen(p);

    int pl = plen + 1;
    int sl = slen + 1;

    bool* ret = (bool*)malloc(sl * pl * sizeof(bool));

    ret[plen * sl + slen] = true;

    for (int i = 0; i < slen; i++)
        ret[plen * sl + i] = false;

    for (int i = plen - 1; i >= 0; i--)
        ret[i * sl + slen] = ret[(i+1) * sl + slen] && p[i] == '*';

    ret[plen * sl + slen] = true;

    for (int i = plen - 1; i >= 0; i--) {
        for (int j = slen - 1; j >= 0; j--) {
            ret[i * sl + j] = ((s[j] == p[i] || p[i] == '?') && ret[(i+1) * sl + (j+1)])
                || (p[i] == '*' && (ret[i * sl + (j+1)] || ret[(i+1) * sl + j] || ret[(i+1) * sl + (j+1)]));
        }
    }

    return ret[0];
}

int main() {
    char* s = "abbaabbbbababaababababbabbbaaaabbbbaaabbbabaabbbbbabbbbabbabbaaabaaaabbbbbbaaabbabbbbababbbaaabbabbabb";
    char* p = "***b**a*a*b***b*a*b*bbb**baa*bba**b**bb***b*a*aab*a**";

    printf("%d", isMatch(s,p));

    return 0;
}
