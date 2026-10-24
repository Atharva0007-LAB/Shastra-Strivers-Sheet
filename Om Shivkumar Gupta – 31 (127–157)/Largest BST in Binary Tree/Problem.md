### Problem 142: Largest BST in Binary Tree
**Difficulty:** Medium | **Tags:** Binary Tree, Binary Search Tree, Postorder Traversal

**Problem Statement:**
Given a binary tree, find the size of the largest subtree which is also a Binary Search Tree (BST). If the entire tree is a BST, the size is the total number of nodes in the binary tree.

A subtree must include all of its descendants.

### Input Format
* The first line contains an integer `N`, representing the number of tokens in the level-order serialization of the tree.
* The second line contains `N` space-separated integers, where `-1` represents a null node.

### Output Format
* Print a single integer representing the number of nodes in the largest BST subtree.

### Constraints
* 1 <= N <= 10^5
* -10^5 <= node.val <= 10^5

### Example
#### Input:
```text
7
5 2 4 1 3 -1 -1
```

#### Output:
```text
3
```

#### Explanation
* The subtree rooted at node 2 (with left child 1 and right child 3) is a valid BST of size 3:
      2
     / \
    1   3
* Node 5 is not a valid BST root because 5 > 4 (in right subtree).
* The largest BST subtree size is 3.
