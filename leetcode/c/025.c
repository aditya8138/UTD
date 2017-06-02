/* 025. Reverse Nodes in k-Group. */

#include<stdio.h>
#include<stdlib.h>

/* Definition for singly-linked list. */
struct ListNode {
    int val;
    struct ListNode *next;
};

struct ListNode* reverseKGroup(struct ListNode* head, int k) {

    struct ListNode* it = head;
    int i = 1;
    while (i <= k) {
        if (it == NULL)
            return head;
        i++;
        it = it->next;
    }
    struct ListNode* tmp;
    struct ListNode* tail = reverseKGroup(it, k);
    i = 1;
    while (i < k) {
        tmp = head->next;
        head->next = tail;
        tail = head;
        head = tmp;
        i++;
    }
    head->next = tail;
    return head;
}

int main() {
    struct ListNode ls[20];

    for (int i = 0; i < 19; i++) {
        ls[i].val = i;
        ls[i].next = &ls[i+1];
    }
    ls[19].val = 19;
    ls[19].next = NULL;

    for (struct ListNode* i = ls; i != NULL; i = i->next)
        printf("%d ", i->val);
    printf("\n");

    for (struct ListNode* i = reverseKGroup(ls, 5); i != NULL; i = i->next)
        printf("%d ", i->val);
    printf("\n");

    return 0;
}
