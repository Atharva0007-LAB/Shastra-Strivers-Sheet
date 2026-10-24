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
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) {
            baseDir = "../TestCases";
        }
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs();
        outDir.mkdirs();

        Random rand = new Random(130);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int[] vals;

            if (tc == 0) {
                vals = new int[]{1, -1, 2, 3, -1};
            } else if (tc == 1) {
                vals = new int[]{10};
            } else if (tc == 2) {
                vals = new int[]{1, 2, -1, 3, -1};
            } else if (tc == 3) {
                vals = new int[]{1, 2, 3, 4, 5, 6, 7};
            } else if (tc == 4) {
                vals = new int[]{4, 2, 6, 1, 3, 5, 7, -1, -1};
            } else {
                int count = 10 + (tc % 40) * 5;
                vals = new int[count];
                vals[0] = 1 + rand.nextInt(100);
                for (int i = 1; i < count; i++) {
                    if (rand.nextDouble() < 0.25) vals[i] = -1;
                    else vals[i] = 1 + rand.nextInt(500);
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(vals.length);
            for (int i = 0; i < vals.length; i++) {
                inPw.print(vals[i] + (i == vals.length - 1 ? "" : " "));
            }
            inPw.println();
            inPw.close();

            // Reconstruct Tree
            TreeNode root = new TreeNode(vals[0]);
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            int idx = 1;
            while (!queue.isEmpty() && idx < vals.length) {
                TreeNode node = queue.poll();
                if (idx < vals.length && vals[idx] != -1) {
                    node.left = new TreeNode(vals[idx]);
                    queue.offer(node.left);
                }
                idx++;
                if (idx < vals.length && vals[idx] != -1) {
                    node.right = new TreeNode(vals[idx]);
                    queue.offer(node.right);
                }
                idx++;
            }

            // Morris Preorder
            List<Integer> preorder = new ArrayList<>();
            TreeNode curr = root;
            while (curr != null) {
                if (curr.left == null) {
                    preorder.add(curr.val);
                    curr = curr.right;
                } else {
                    TreeNode prev = curr.left;
                    while (prev.right != null && prev.right != curr) prev = prev.right;
                    if (prev.right == null) {
                        prev.right = curr;
                        preorder.add(curr.val);
                        curr = curr.left;
                    } else {
                        prev.right = null;
                        curr = curr.right;
                    }
                }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (int i = 0; i < preorder.size(); i++) {
                outPw.print(preorder.get(i) + (i == preorder.size() - 1 ? "" : " "));
            }
            outPw.println();
            outPw.close();
        }
        System.out.println("Morris Preorder: 100 test cases generated.");
    }
}
