### Problem 130: Morris Preorder Traversal
**Difficulty:** Medium | **Tags:** Binary Tree, Morris Traversal

**Problem Statement:**
Given the root of a binary tree, return the preorder traversal of its nodes' values without using recursion or an explicit call stack.

Morris Traversal allows traversing the tree in `O(N)` time and `O(1)` auxiliary space by temporarily modifying tree pointers (creating threads to inorder predecessors) and restoring them.

### Input Format
* The first line contains an integer `N`, representing the number of tokens in the level-order serialization of the tree.
* The second line contains `N` space-separated integers representing the level-order traversal, where `-1` indicates a null node.

### Output Format
* Print space-separated integers representing the preorder traversal of the tree on a single line.

### Constraints
* 1 <= N <= 10^5
* -10^5 <= node.val <= 10^5

### Example
#### Input:
```text
5
1 -1 2 3 -1
```

#### Output:
```text
1 2 3
```

#### Explanation
* The binary tree structure is:
      1
       \
        2
       /
      3
* Preorder traversal (Root -> Left -> Right): 1 2 3
