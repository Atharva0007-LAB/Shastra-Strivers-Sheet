# 130. Morris Preorder Traversal - Editorial

## Problem Statement
Given the root of a binary tree, return the preorder traversal of its nodes' values without using recursion or an explicit call stack.

Morris Traversal allows traversing the tree in `O(N)` time and `O(1)` auxiliary space by temporarily modifying tree pointers (creating threads to inorder predecessors) and restoring them.

---

## Sample Example

### Sample Input
```text
5
1 -1 2 3 -1
```

### Sample Output
```text
1 2 3
```

### Explanation
* The binary tree structure is:
      1
       \
        2
       /
      3
* Preorder traversal (Root -> Left -> Right): 1 2 3

---

## Intuition
Standard tree traversals require either recursion or an explicit stack, using O(H) auxiliary space (where H is tree height).

Morris Traversal eliminates the stack using Threaded Binary Trees:
Before navigating into a node's left subtree, we locate its inorder predecessor (the rightmost node in the left subtree) and link the predecessor's `right` pointer back to the current node. This thread allows us to return to the root node once the left subtree is fully explored.

## Approach
1. Initialize `curr = root`.
2. While `curr != null`:
   - If `curr.left == null`:
     - Record `curr.val` in preorder list.
     - Move `curr = curr.right`.
   - Else:
     - Find inorder predecessor: `prev = curr.left`.
     - Advance `prev = prev.right` while `prev.right != null && prev.right != curr`.
     - If `prev.right == null`:
       - Record `curr.val` (preorder visits node BEFORE left child).
       - Create thread: `prev.right = curr`.
       - Move `curr = curr.left`.
     - Else (`prev.right == curr`):
       - Remove thread: `prev.right = null`.
       - Move `curr = curr.right`.
3. Return the preorder traversal.

---

## Complexity Analysis
* **Time Complexity:** O(N) amortized because each edge in the tree is traversed at most 3 times.
* **Space Complexity:** O(1) auxiliary space since no recursion stack or extra data structures are used.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * Performs Morris Preorder Traversal in O(1) auxiliary space.
     * 
     * @param root Root of the binary tree.
     * @return List of integers in preorder order.
     */
    public List<Integer> morrisPreorder(TreeNode root) {
        List<Integer> preorder = new ArrayList<>();
        TreeNode curr = root;

        while (curr != null) {
            // Case 1: If current node has no left child, visit it and move to right child
            if (curr.left == null) {
                preorder.add(curr.val);
                curr = curr.right;
            } else {
                // Find the inorder predecessor of current node
                TreeNode prev = curr.left;
                while (prev.right != null && prev.right != curr) {
                    prev = prev.right;
                }

                // Case 2a: Thread does not exist, create it and visit current (preorder)
                if (prev.right == null) {
                    prev.right = curr;
                    preorder.add(curr.val);
                    curr = curr.left;
                } 
                // Case 2b: Thread already exists, break it and move to right child
                else {
                    prev.right = null;
                    curr = curr.right;
                }
            }
        }

        return preorder;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        if (n == 0) return;

        int[] vals = new int[n];
        for (int i = 0; i < n; i++) {
            vals[i] = sc.nextInt();
        }

        if (vals[0] == -1) return;

        // Reconstruct binary tree from level order array
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int idx = 1;
        while (!queue.isEmpty() && idx < n) {
            TreeNode node = queue.poll();

            // Left child
            if (idx < n && vals[idx] != -1) {
                node.left = new TreeNode(vals[idx]);
                queue.offer(node.left);
            }
            idx++;

            // Right child
            if (idx < n && vals[idx] != -1) {
                node.right = new TreeNode(vals[idx]);
                queue.offer(node.right);
            }
            idx++;
        }

        Solution sol = new Solution();
        List<Integer> ans = sol.morrisPreorder(root);

        for (int i = 0; i < ans.size(); i++) {
            System.out.print(ans.get(i) + (i == ans.size() - 1 ? "" : " "));
        }
        System.out.println();
        sc.close();
    }
}
```
