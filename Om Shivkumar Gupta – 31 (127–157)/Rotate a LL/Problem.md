### Problem 149: Rotate a LL
**Difficulty:** Medium | **Tags:** Linked List, Two Pointers

**Problem Statement:**
Given the head of a singly linked list, rotate the list to the right by `K` places.

### Input Format
* The first line contains two integers `N` and `K`, representing the number of nodes in the linked list and the number of places to rotate.
* The second line contains `N` space-separated integers representing the node values (if N > 0).

### Output Format
* Print space-separated integers representing the rotated linked list on a single line (or an empty line if N = 0).

### Constraints
* 0 <= N <= 10^5
* 0 <= K <= 2 * 10^9
* -100 <= Node.val <= 100

### Example
#### Input:
```text
5 2
1 2 3 4 5
```

#### Output:
```text
4 5 1 2 3
```

#### Explanation
* Rotate 1 step right: 5 -> 1 -> 2 -> 3 -> 4
* Rotate 2 steps right: 4 -> 5 -> 1 -> 2 -> 3
