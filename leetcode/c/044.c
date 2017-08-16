#include <stdio.h>
#include <stdbool.h>

int findNext(const char* p) {
    int i = 1;
    while (p[i] == '*')
        i++;
    return i;
}

bool isMatch(char* s, char* p) {

    if (*s == '\0') {
//         if (*p == '\0')
//             return true;

//         if (*p == '*' && strlen(p) == 1)
//             return true;

//         if (*p != '*')
//             return false;

        return *p == '\0' || (*p == '*' && isMatch(s, p + 1));
    }

    if (*p != '*')
        return (*s == *p || *p == '?') && isMatch(s + 1, p + 1);
    else { // *p == '*'
        int i = findNext(p);
        /*return (isMatch(s + 1, p)*/
               /*|| isMatch(s, p + i)*/
               /*|| isMatch(s + 1, p + i));*/
        if (isMatch(s, p + i))
            return true;
        if (isMatch(s + 1, p + i))
            return true;
        if (isMatch(s + 1, p))
            return true;
        return false;
    }
}

int main() {
    char* s = "abbaabbbbababaababababbabbbaaaabbbbaaabbbabaabbbbbabbbbabbabbaaabaaaabbbbbbaaabbabbbbababbbaaabbabbabb";
    char* p = "***b**a*a*b***b*a*b*bbb**baa*bba**b**bb***b*a*aab*a**";

    printf("%d", isMatch(s,p));

    return 0;
}
