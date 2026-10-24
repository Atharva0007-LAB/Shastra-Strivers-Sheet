# 142. Largest BST in Binary Tree - Editorial

## Problem Statement
Given a binary tree, find the size of the largest subtree which is also a Binary Search Tree (BST). If the entire tree is a BST, the size is the total number of nodes in the binary tree.

A subtree must include all of its descendants.

---

## Sample Example

### Sample Input
```text
7
5 2 4 1 3 -1 -1
```

### Sample Output
```text
3
```

### Explanation
* The subtree rooted at node 2 (with left child 1 and right child 3) is a valid BST of size 3:
      2
     / \
    1   3
* Node 5 is not a valid BST root because 5 > 4 (in right subtree).
* The largest BST subtree size is 3.

---

## Intuition
A naive approach validates whether each node's subtree is a BST from top to bottom, which takes O(N^2) time.

We can achieve O(N) time with a bottom-up Postorder Traversal:
For each node, we collect:
1. `minNode`: Minimum value in this subtree
2. `maxNode`: Maximum value in this subtree
3. `maxSize`: Size of the largest BST in this subtree

If `left.maxNode < root.val < right.minNode`, then the current subtree is a valid BST of size `1 + left.size + right.size`.
Otherwise, we propagate `max(left.size, right.size)` and set bounds to `[-inf, +inf]` so ancestors know the tree is not a BST.

## Approach
1. Use a postorder traversal helper returning `NodeValue(minNode, maxNode, maxSize)`.
2. Base case: `root == null` returns `[+inf, -inf, 0]`.
3. Recursively compute left and right subtree states.
4. If `left.maxNode < root.val < right.minNode`:
   - Valid BST formed.
   - Return `[min(root.val, left.minNode), max(root.val, right.maxNode), 1 + left.size + right.size]`.
5. Else:
   - Return `[-inf, +inf, max(left.size, right.size)]`.
6. Return `root`'s `maxSize`.

---

## Complexity Analysis
* **Time Complexity:** O(N) because each node in the tree is visited exactly once.
* **Space Complexity:** O(H) where H is the height of the tree, for the recursion stack.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { this.val = v; }
    }

    static class NodeValue {
        int maxNode, minNode, maxSize;
        NodeValue(int minNode, int maxNode, int maxSize) {
            this.minNode = minNode;
            this.maxNode = maxNode;
            this.maxSize = maxSize;
        }
    }

    /**
     * Traverses the tree bottom-up (postorder) to find the largest BST subtree.
     */
    private NodeValue largestBSTHelper(TreeNode root) {
        // An empty tree is a BST of size 0
        if (root == null) {
            return new NodeValue(Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
        }

        NodeValue left = largestBSTHelper(root.left);
        NodeValue right = largestBSTHelper(root.right);

        // Check BST property: left.max < root.val < right.min
        if (left.maxNode < root.val && root.val < right.minNode) {
            // Valid BST
            return new NodeValue(
                Math.min(root.val, left.minNode),
                Math.max(root.val, right.maxNode),
                1 + left.maxSize + right.maxSize
            );
        }

        // Invalid BST: return max size found so far with invalid bounds
        return new NodeValue(Integer.MIN_VALUE, Integer.MAX_VALUE, Math.max(left.maxSize, right.maxSize));
    }

    public int largestBST(TreeNode root) {
        return largestBSTHelper(root).maxSize;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        if (n == 0) {
            System.out.println(0);
            return;
        }

        int[] vals = new int[n];
        for (int i = 0; i < n; i++) vals[i] = sc.nextInt();

        if (vals[0] == -1) {
            System.out.println(0);
            return;
        }

        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int idx = 1;
        while (!queue.isEmpty() && idx < n) {
            TreeNode curr = queue.poll();
            if (idx < n && vals[idx] != -1) {
                curr.left = new TreeNode(vals[idx]);
                queue.offer(curr.left);
            }
            idx++;
            if (idx < n && vals[idx] != -1) {
                curr.right = new TreeNode(vals[idx]);
                queue.offer(curr.right);
            }
            idx++;
        }

        Solution sol = new Solution();
        int ans = sol.largestBST(root);
        System.out.println(ans);
        sc.close();
    }
}
```
