/* 021. Merge Two Sorted Lists. */

#include<stdio.h>
#include<stdlib.h>

/*Definition for singly-linked list.*/
struct ListNode {
    int val;
    struct ListNode *next;
};

struct ListNode* mergeTwoLists(struct ListNode* l1, struct ListNode* l2) {
    struct ListNode* head = (struct ListNode*)malloc(sizeof(struct ListNode));
    head->next = NULL;

    struct ListNode* ihead = head;

    while (l1 != NULL && l2 != NULL) {
        if (l1->val < l2->val) {
            ihead->next = l1;
            l1 = l1->next;
        } else {
            ihead->next = l2;
            l2 = l2->next;
        }
        ihead = ihead->next;
    }
    if (l1 == NULL)
        ihead->next = l2;
    if (l2 == NULL)
        ihead->next = l1;

    return head->next;
}

int main() {
    struct ListNode* head1 = (struct ListNode*)malloc(sizeof(struct ListNode));
    struct ListNode* it = head1;
    for (int i = 0; i < 100; i += 10) {
        it->next = (struct ListNode*)malloc(sizeof(struct ListNode));
        it->next->val = i;
        it = it->next;
    }
    it->next = NULL;
    struct ListNode* head2 = (struct ListNode*)malloc(sizeof(struct ListNode));
    it = head2;
    for (int i = 5; i < 120; i += 12) {
        it->next = (struct ListNode*)malloc(sizeof(struct ListNode));
        it->next->val = i;
        it = it->next;
    }
    it->next = NULL;

    struct ListNode* result = mergeTwoLists(head1->next, head2->next);

    for (it = result; it != NULL; it = it->next)
        printf("%d ", it->val);

    return 0;
}
