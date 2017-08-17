/* 045. Jump Game II. */

/* Intuitively, each index represent a node. The number in the array indicate
 * the neighbor of that node and the distance between each node is 1. Than the
 * problem basically becomes a Single Source Shortest Path problem. 
 * Using Dijkstra or Bellman-Ford, the shortest distance (i.e., the minimum
 * number of jump) to each node (index) could easily be calculated in O(m)
 * time (where m is the number of edges). 
 *
 * This approach compute distance to all index, if circular array is used as
 * queue, the capacity is a subtle point. However, the solution becomes more
 * efficient than linked list. */

#include <stdio.h>
#include <stdlib.h>

int jump(int* nums, int numsSize) {
    int* dist = (int*)malloc(numsSize * sizeof(int));

    const int capacity = 5600;
    int* queue = (int*)malloc(capacity * sizeof(int));

    int qh = 0;
    int qt = 0;

    dist[0] = 0;

    for (int i = 1; i < numsSize; i++)
        dist[i] = 2147483647;

    queue[qt] = 0;
    qt++;

    int pop;
    while (qh != qt) {
        pop = queue[qh];
        qh = (qh + 1) % capacity;
        for (int i = 1; i <= nums[pop] && pop + i < numsSize; i++) {
            if (dist[pop] + 1 < dist[pop + i]) {
                dist[pop + i] = dist[pop] + 1;
                queue[qt] = pop + i;
                qt = (qt + 1) % capacity;
            }
        }
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
