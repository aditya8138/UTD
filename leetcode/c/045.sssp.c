/* 045. Jump Game II. */

/* Intuitively, each index represent a node. The number in the array indicate
 * the neighbor of that node and the distance between each node is 1. Than the
 * problem basically becomes a Single Source Shortest Path problem. 
 * Using Dijkstra or Bellman-Ford, the shortest distance (i.e., the minimum
 * number of jump) to each node (index) could easily be calculated in O(m)
 * time (where m is the number of edges). 
 *
 * This approach compute distance to all index, however it is not efficient in
 * this particular problem. */

#include <stdio.h>
#include <stdlib.h>

/* Define queue node structure. (Using Bellman-Ford) */
typedef struct _QueueNode {
    int data;
    struct _QueueNode *next;
} QueueNode;


int jump(int* nums, int numsSize) {
    int* dist = (int*)malloc(numsSize * sizeof(int));

    QueueNode* head = NULL;
    QueueNode* tail = NULL;

    dist[0] = 0;

    /* Put source (index 0) into queue. */
    head = tail = malloc(sizeof(QueueNode));
    head->data = 0;
    head->next = NULL;

    /* Initialize distance to infinity. */
    for (int i = 1; i < numsSize; i++)
        dist[i] = 2147483647;

    QueueNode* pop;
    /* While the queue is not empty. */
    while (head != NULL) {
        /* Pop from queue. */
        pop = head;

        /* For each edge. */
        for (int i = 1; i <= nums[pop->data] && pop->data + i < numsSize; i++) {
            /* If edge tense, relax and put next node to queue. */
            if (dist[pop->data + i] > dist[pop->data] + 1) {
                dist[pop->data + i] = dist[pop->data] + 1;

                tail->next = malloc(sizeof(QueueNode));
                tail = tail->next;
                tail->data = pop->data + i;
                tail->next = NULL;
            }
        }

        /* Remove head. */
        head = head->next;
        free(pop);
    }

    return dist[numsSize-1];
}

int main() {
    int n1[] = {2,3,1,1,4};

    int n2[] = {2,0,2,4,6,0,0,3};

    printf("%d\n", jump(n1, 5));
    printf("%d\n", jump(n2, 8));

    return 0;
}
