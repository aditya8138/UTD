/* 061. Rotate List. */

#include <stdio.h>
#include <stdlib.h>

/*Definition for singly-linked list.*/
struct ListNode {
    int val;
    struct ListNode *next;
};

int len(const struct ListNode* head) {
    if (head != NULL)
        return 1 + len(head->next);
    return 0;
}

struct ListNode* nth(struct ListNode* head, int n) {
    if (n == 1)
        return head;
    if (head != NULL)
        return nth(head->next, n-1);
    return NULL;
}

struct ListNode* last(struct ListNode* head) {
    if (head->next == NULL)
        return head;
    return last(head->next);
}

struct ListNode* rotateRight(struct ListNode* head, int k) {
    if (head == NULL)
        return head;

    int l = len(head);
    k = k % l;

    if (k == 0)
        return head;

    int n = l - k;

    struct ListNode* pre = nth(head, n);
    struct ListNode* nhead = pre->next;
    pre->next = NULL;
    last(nhead)->next = head;
    return nhead;
}

int main() {
    struct ListNode node[8];
    for (int i = 0; i < 7; i++) {
        node[i].val = i;
        node[i].next = &(node[i+1]);
    }
    node[7].val = 7;
    node[7].next = NULL;

    struct ListNode* it = node;
    while (it != NULL) {
        printf("%d\t", it->val);
        it = it->next;
    }
    printf("\n");

    it = rotateRight(node, 4);
    while (it != NULL) {
        printf("%d\t", it->val);
        it = it->next;
    }
    printf("\n");

    return 0;
}
