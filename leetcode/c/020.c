/* 020. Valid Parentheses */

#include<stdio.h>
#include<stdbool.h>

bool isValid(char* s) {
    char stack[8000];
    stack[0] = 'a';
    int i = 0;
    for (char* c = s; *c != '\0'; c++) {
        if (*c == '[' || *c == '{' || *c == '(')
            stack[++i] = *c;
        else if ((*c == ']' && stack[i] == '[') ||
                (*c == '}' && stack[i] == '{') ||
                (*c == ')' && stack[i] == '('))
            i--;
        else
            return false;
    }
    if (i == 0)
        return true;
    else
        return false;

}

int main() {
    printf("%d", isValid("[{[{[(())]}]}]"));
    printf("%d", isValid("[{[{[(())]]}]"));
    return 0;
}
