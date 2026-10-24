### Problem 134: Clone a LL with Random and Next Pointer
**Difficulty:** Hard | **Tags:** Linked List, Pointers

**Problem Statement:**
A linked list of length `N` is given such that each node contains an additional random pointer, which could point to any node in the list, or `null`.

Construct a deep copy of the list. The deep copy should consist of exactly `N` brand new nodes, where each new node has its value set to the value of its corresponding original node. Both the `next` and `random` pointer of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. None of the pointers in the new list should point to nodes in the original list.

### Input Format
* The first line contains an integer `N`, representing the number of nodes in the linked list.
* The next `N` lines each contain two integers: `val` and `random_index` (0-indexed position of the node pointed to by `random`, or `-1` if `random` is null).

### Output Format
* Print `N` lines, each containing the cloned node's `val` and its `random_index`.

### Constraints
* 0 <= N <= 1000
* -10^4 <= Node.val <= 10^4
* random_index is -1 or in the range [0, N - 1]

### Example
#### Input:
```text
5
7 -1
13 0
11 4
10 2
1 0
```

#### Output:
```text
7 -1
13 0
11 4
10 2
1 0
```

#### Explanation
* The cloned list has 5 distinct nodes whose values and random relationships perfectly mirror the original list.
