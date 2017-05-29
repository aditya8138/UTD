/* 019. Remove Nth Node From End of List */
/* A intuitive recursive solution. */

#include<stdio.h>
#include<stdlib.h>

struct ListNode {
    int val;
    struct ListNode *next;
};


void _removeNthFromEnd(struct ListNode* head, struct ListNode* tail) {
    if (tail != NULL)
        return _removeNthFromEnd(head->next, tail->next);
    head->next = head->next->next;
    return;
}

struct ListNode* removeNthFromEnd(struct ListNode* head, int n) {
    struct ListNode* _head = (struct ListNode*)malloc(sizeof(struct ListNode));
    _head->next = head;
    struct ListNode* it = _head;
    for (int i = 0; i <= n; i++)
        it = it->next;
    _removeNthFromEnd(_head, it);
    return _head->next;
}

int main() {
    struct ListNode* head = (struct ListNode*)malloc(sizeof(struct ListNode));
    head->val = 0;
    struct ListNode* it = head;
    for (int i = 1; i < 10; i++) {
        it->next = (struct ListNode*)malloc(sizeof(struct ListNode));
        it->next->val = i;
        it = it->next;
    }
    it->next = NULL;

    for (struct ListNode* i = head; i != NULL; i = i->next)
        printf("%d ", i->val);
    printf("\n");

    for (struct ListNode* i = removeNthFromEnd(head, 3); i != NULL; i = i->next)
        printf("%d ", i->val);

    return 0;
}
