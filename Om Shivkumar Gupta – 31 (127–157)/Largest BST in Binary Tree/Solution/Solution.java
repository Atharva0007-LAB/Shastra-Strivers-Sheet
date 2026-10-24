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
