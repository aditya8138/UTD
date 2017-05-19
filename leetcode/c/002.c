/* 002.Add Two Numbers */

/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     struct ListNode *next;
 * };
 */
struct ListNode* addTwoNumbers(struct ListNode* l1, struct ListNode* l2) {
    int t, flag = 0;
    struct ListNode *result, *it, *it1, *it2;
	result = it = NULL;
    it1 = l1;
    it2 = l2;
    
    while (it1 != NULL || it2 != NULL) {
    	if (result == NULL) 
    		it = result = (struct ListNode *)malloc(sizeof(struct ListNode));
    	else
			it = it->next = (struct ListNode *)malloc(sizeof(struct ListNode));
        t = flag + ((it1 != NULL ? it1->val : 0) + (it2 != NULL ? it2->val : 0));
        if (t < 10) {
            flag = 0;
            it->val = t;
        } else {
            flag = 1;
            it->val = t - 10;
        }
        it->next = NULL;
        it1 = it1 != NULL ? (it1->next != NULL ? it1->next : NULL) : NULL; 
        it2 = it2 != NULL ? (it2->next != NULL ? it2->next : NULL) : NULL;
    }
    if (flag == 1) {
        it = it->next = (struct ListNode *)malloc(sizeof(struct ListNode));
        it->val = 1;
        it->next = NULL;
    }
    return result;
}
