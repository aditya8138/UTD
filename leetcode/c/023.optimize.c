/* 023. Merge K Sorted Lists. */
/* A basic solution where lists is accumulated iteratively. */

#include<stdio.h>
#include<stdlib.h>

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

struct ListNode* _mergeKLists(struct ListNode** lists, int left, int right) {
    if (left > right)
        return NULL;
    if (left == right)
        return lists[left];
    if (left + 1 == right)
        return mergeTwoLists(lists[left], lists[right]);
    int mid = (left + right) / 2;
    struct ListNode *l1 = _mergeKLists(lists,left,mid);
    struct ListNode *l2 = _mergeKLists(lists,mid+1,right);
    return mergeTwoLists(l1,l2);
}

struct ListNode* mergeKLists(struct ListNode** lists, int listsSize) {
    if (listsSize <= 1)
        return *lists;
    return _mergeKLists(lists, 0, listsSize - 1);
}

int main() {
    int n = 10;
    struct ListNode** lists = (struct ListNode**)malloc(n * sizeof(struct ListNode*));
    struct ListNode* head = (struct ListNode*)malloc(sizeof(struct ListNode));
    for (int j = 0; j < n; j++) {
        struct ListNode* it = head;
        for (int i = j; i < 500 / (1+j); i += (10 + j)) {
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
