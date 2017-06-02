/* 025. Reverse Nodes in k-Group. */

/* A revised version of the same idea, but using buffer
 * to remember the node and makes it easy to reverse. */

#include<stdio.h>
#include<stdlib.h>

/* Definition for singly-linked list. */
struct ListNode {
    int val;
    struct ListNode *next;
};

struct ListNode* reverseKGroup(struct ListNode* head, int k) {
    struct ListNode* kl[k];
    struct ListNode* it = head;
    int i = 0;
    while (i < k) {
        if (it == NULL)
            return head;
        kl[i] = it;
        it = it->next;
        i++;
    }

    while (--i > 0)
        kl[i]->next = kl[i-1];

    kl[0]->next = reverseKGroup(it, k);

    return kl[k - 1];
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
