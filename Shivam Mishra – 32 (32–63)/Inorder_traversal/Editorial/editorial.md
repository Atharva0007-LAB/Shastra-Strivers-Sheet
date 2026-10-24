# Editorial: Inorder Traversal

## Difficulty

Easy

## Prerequisites
- Binary Trees
- Depth-First Search (DFS)
- Recursion

## Problem Statement

Given the root of a binary tree, return the inorder traversal of its nodes' values.

Inorder traversal follows a specific order: **Left Subtree → Root → Right Subtree**. For every node, first traverse its left subtree, then visit the node itself, and finally traverse its right subtree.

The traversal must preserve this order throughout the entire binary tree.

## Explanation & Approach

### Step 1: Understand the Inorder Traversal Order

In a binary tree, every node can have a left child and a right child. Inorder traversal determines the order in which these nodes are visited.

For every node, we perform three operations:

- **Left:** Traverse the left subtree first.
- **Root:** Add the current node's value to the result.
- **Right:** Traverse the right subtree afterward.

This process is applied recursively to every subtree. If a node does not exist (that is, the current node is `null`), we return immediately because there is nothing to traverse.

### Step 2: Use Recursion to Traverse the Tree

Define a recursive function `inorder(node, result)` that processes the subtree rooted at `node`.

The function follows these steps:

1. If `node == null`, stop the current recursive call.
2. Recursively traverse `node.left`.
3. Add `node.val` to the result list.
4. Recursively traverse `node.right`.

The key idea is that the current node is added to the result **only after its entire left subtree has been traversed** and before its right subtree is processed.

Recursion automatically manages the order of execution through the call stack, so we do not need to maintain a separate stack ourselves.

### Step 3: Return the Final Traversal

Start the recursive function from the root of the binary tree.

Once all recursive calls finish, every node has been visited exactly once in inorder sequence. The result list contains the required traversal.

If the tree is empty, the function returns an empty list.

## Example

### Input

```text
root = [1, 4, null, 4, 2]
```

The binary tree represented by this level-order array is:

```text
        1
       /
      4
     / \
    4   2
```

### Trace

Start at the root node `1`.

- **Node 1:** Its left child is `4`, so traverse the left subtree first.
- **Node 4 (left child of 1):** Its left child is another `4`, so visit that subtree first.
- **Node 4 (leaf node):** It has no left child. Add `4` to the result, then return because it has no right child.
- **Node 4 (parent):** Its left subtree is complete. Add `4` to the result, then traverse its right child `2`.
- **Node 2:** It has no left child, so add `2` to the result. It also has no right child, so return.
- **Node 1:** Both its left subtree and its traversal are complete. Add `1` to the result.

The final traversal is:

```text
[4, 4, 2, 1]
```

### Output

```text
[4, 4, 2, 1]
```

## C++ Solution

```C++
class Solution {
public:
    void inorder(TreeNode* root, vector<int>& result) {
        if (root == nullptr) {
            return;
        }

        inorder(root->left, result);
        result.push_back(root->val);
        inorder(root->right, result);
    }

    vector<int> inorderTraversal(TreeNode* root) {
        vector<int> result;
        inorder(root, result);
        return result;
    }
};
```

**Note:** This solution uses the standard binary-tree node definition with `val`, `left`, and `right` fields. On platforms that provide a predefined node class, use that class and match the required method signature.

## Time Complexity

Let \(N\) be the number of nodes in the binary tree.

Every node is visited exactly once. Each visit performs constant-time work, excluding the recursive calls.

### Total Time Complexity

\[
\boxed{O(N)}
\]

### Space Complexity

The recursive call stack requires \(O(H)\) space, where \(H\) is the height of the tree. The result list requires \(O(N)\) space because it stores all node values.

- **Auxiliary space:** \(O(H)\) for recursion.
- **Total space including the result:** \(O(N)\).

For a balanced binary tree, the recursion stack uses \(O(\log N)\) space. For a completely skewed tree, it can use \(O(N)\) space.

## Conclusion

Inorder traversal is a fundamental depth-first traversal technique for binary trees. By processing the left subtree, the current node, and the right subtree in that order, we can generate the required sequence efficiently using recursion.

For a Binary Search Tree (BST), inorder traversal also produces the node values in sorted order, provided the tree follows the BST ordering property.
