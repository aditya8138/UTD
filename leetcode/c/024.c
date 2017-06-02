/* 024. Swap Nodes in Pairs. */

#include<stdio.h>
#include<stdlib.h>

/* Definition for singly-linked list. */
struct ListNode {
    int val;
    struct ListNode *next;
};


struct ListNode* swapPairs(struct ListNode* head) {
    if ((head == NULL) || (head->next == NULL))
        return head;
    struct ListNode* tmp = head->next;
    head->next = swapPairs(tmp->next);
    tmp->next = head;
    return tmp;
}

int main() {
    struct ListNode ls[9];

    for (int i = 0; i < 8; i++) {
        ls[i].val = i;
        ls[i].next = &ls[i+1];
    }
    ls[8].val = 8;
    ls[8].next = NULL;

    for (struct ListNode* i = ls; i != NULL; i = i->next)
        printf("%d ", i->val);
    printf("\n");

    for (struct ListNode* i = swapPairs(ls); i != NULL; i = i->next)
        printf("%d ", i->val);
    printf("\n");

    return 0;
}
