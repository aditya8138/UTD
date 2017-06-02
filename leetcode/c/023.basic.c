/* 023. Merge K Sorted Lists. */
/* A basic solution where lists is accumulated iteratively. */

#include<stdio.h>
#include<stdlib.h>

/*Definition for singly-linked list.*/
struct ListNode {
    int val;
    struct ListNode *next;
};

/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     struct ListNode *next;
 * };
 */

int length(struct ListNode* l) {
    return l == NULL ? 0 : 1 + length(l->next);
}

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

struct ListNode* mergeKLists(struct ListNode** lists, int listsSize) {
    if (listsSize <= 1)
        return *lists;
    lists[1] = mergeTwoLists(lists[0], lists[1]);
    return mergeKLists(lists + 1, listsSize - 1);
}

int main() {
    int n = 10;
    struct ListNode** lists = (struct ListNode**)malloc(n * sizeof(struct ListNode*));
    struct ListNode* head = (struct ListNode*)malloc(sizeof(struct ListNode));
    for (int j = 0; j < n; j++) {
        struct ListNode* it = head;
        for (int i = j; i < j * 5; i += (10 + j)) {
            it->next = (struct ListNode*)malloc(sizeof(struct ListNode));
            it->next->val = i;
            it = it->next;
        }
        it->next = NULL;
        lists[j] = head->next;
    }

    struct ListNode* result = mergeKLists(lists, n);

    for (struct ListNode* it = result; it != NULL; it = it->next)
        printf("%d ", it->val);

    return 0;
}
