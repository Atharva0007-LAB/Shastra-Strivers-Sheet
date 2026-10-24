# Kth Smallest and Largest Element in a BST

**Difficulty:** Medium

**Prerequisites:** Binary Search Tree, Inorder Traversal, Recursion

## Problem Statement

Given the root of a Binary Search Tree (BST) and an integer `k`, find the kth smallest and kth largest elements in the BST.

## Approach

### Intuition

A Binary Search Tree follows the property that all nodes in the left subtree contain smaller values than the root, while all nodes in the right subtree contain larger values.

Therefore, performing an **inorder traversal (Left → Root → Right)** visits the nodes in ascending order. The kth element encountered is the kth smallest element.

Similarly, performing a **reverse inorder traversal (Right → Root → Left)** visits the nodes in descending order. The kth element encountered is the kth largest element.

We maintain a counter during each traversal. Whenever a node is visited, we increment the counter. When the counter becomes equal to `k`, we store the node's value and stop further traversal.

### Algorithm

1. Initialize the answer for the kth smallest and kth largest elements.
2. Perform inorder traversal to find the kth smallest element:
   - Recursively traverse the left subtree.
   - Increment the counter when visiting the current node.
   - If the counter equals `k`, store the current node's value.
   - Traverse the right subtree only if the answer has not been found.
3. Perform reverse inorder traversal to find the kth largest element:
   - Recursively traverse the right subtree.
   - Increment the counter when visiting the current node.
   - If the counter equals `k`, store the current node's value.
   - Traverse the left subtree only if the answer has not been found.
4. Return both answers.

## C++ Code

```cpp
class Solution {
private:
    void findSmallest(TreeNode* root, int k, int& count, int& ans) {
        if (root == nullptr || count >= k)
            return;

        findSmallest(root->left, k, count, ans);

        if (count >= k)
            return;

        count++;

        if (count == k) {
            ans = root->data;
            return;
        }

        findSmallest(root->right, k, count, ans);
    }

    void findLargest(TreeNode* root, int k, int& count, int& ans) {
        if (root == nullptr || count >= k)
            return;

        findLargest(root->right, k, count, ans);

        if (count >= k)
            return;

        count++;

        if (count == k) {
            ans = root->data;
            return;
        }

        findLargest(root->left, k, count, ans);
    }

public:
    vector<int> findKthElements(TreeNode* root, int k) {
        int smallest = -1;
        int largest = -1;
        int count = 0;

        findSmallest(root, k, count, smallest);

        count = 0;

        findLargest(root, k, count, largest);

        return {smallest, largest};
    }
};
```

## Complexity Analysis

**Time Complexity:** O(N) in the worst case, where N is the number of nodes in the BST. Each traversal may visit every node.

**Space Complexity:** O(H), where H is the height of the BST, due to the recursive call stack. In the worst case, H can be N for a skewed BST.

## Key Takeaway

Inorder traversal gives the elements of a BST in ascending order, while reverse inorder traversal gives them in descending order. Using a counter allows us to identify the kth smallest and kth largest elements without storing the entire traversal.

**Note:** Adjust `root->data` and the function signature according to the exact structure expected by the platform. If the node stores its value in `val`, replace `root->data` with `root->val`.