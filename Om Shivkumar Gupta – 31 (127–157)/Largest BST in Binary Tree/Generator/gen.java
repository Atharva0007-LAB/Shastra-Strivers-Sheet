import java.io.*;
import java.util.*;

public class gen {
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { this.val = v; }
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(142);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int[] vals;

            if (tc == 0) {
                vals = new int[]{5, 2, 4, 1, 3, -1, -1};
            } else if (tc == 1) {
                vals = new int[]{10};
            } else if (tc == 2) {
                vals = new int[]{1, 2, 3, 4, 5, 6, 7}; // not a BST root
            } else if (tc == 3) {
                vals = new int[]{4, 2, 6, 1, 3, 5, 7}; // complete BST of size 7
            } else if (tc == 4) {
                vals = new int[]{10, 5, 15, 1, 8, -1, 7};
            } else {
                int count = 15 + (tc % 30) * 10;
                vals = new int[count];
                vals[0] = 50 + rand.nextInt(100);
                for (int i = 1; i < count; i++) {
                    if (rand.nextDouble() < 0.2) vals[i] = -1;
                    else vals[i] = rand.nextInt(300);
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(vals.length);
            for (int i = 0; i < vals.length; i++) inPw.print(vals[i] + (i == vals.length - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            // Reconstruct and solve
            TreeNode root = new TreeNode(vals[0]);
            Queue<TreeNode> q = new LinkedList<>();
            q.offer(root);
            int idx = 1;
            while (!q.isEmpty() && idx < vals.length) {
                TreeNode curr = q.poll();
                if (idx < vals.length && vals[idx] != -1) {
                    curr.left = new TreeNode(vals[idx]);
                    q.offer(curr.left);
                }
                idx++;
                if (idx < vals.length && vals[idx] != -1) {
                    curr.right = new TreeNode(vals[idx]);
                    q.offer(curr.right);
                }
                idx++;
            }

            int ans = solveLargestBST(root);

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Largest BST in Binary Tree: 100 test cases generated.");
    }

    static class Res {
        int min, max, size;
        Res(int min, int max, int size) { this.min = min; this.max = max; this.size = size; }
    }

    static int solveLargestBST(TreeNode root) {
        return helper(root).size;
    }

    static Res helper(TreeNode root) {
        if (root == null) return new Res(Integer.MAX_VALUE, Integer.MIN_VALUE, 0);
        Res l = helper(root.left);
        Res r = helper(root.right);
        if (l.max < root.val && root.val < r.min) {
            return new Res(Math.min(root.val, l.min), Math.max(root.val, r.max), 1 + l.size + r.size);
        }
        return new Res(Integer.MIN_VALUE, Integer.MAX_VALUE, Math.max(l.size, r.size));
    }
}
