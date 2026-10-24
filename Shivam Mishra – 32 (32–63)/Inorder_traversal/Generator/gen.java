import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class gen {

    static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    static class TestCase {
        List<String> nodes;
        List<String> expected;

        TestCase(List<String> nodes, List<String> expected) {
            this.nodes = nodes;
            this.expected = expected;
        }
    }

    public static void main(String[] args) throws IOException {

        Path baseDir = Paths.get("Testcases");

        if (!Files.exists(baseDir)) {
            baseDir = Paths.get("../Testcases");
        }

        Path inputDir = baseDir.resolve("input");
        Path outputDir = baseDir.resolve("output");

        Files.createDirectories(inputDir);
        Files.createDirectories(outputDir);

        List<TestCase> testCases = new ArrayList<>();

        // Test case 1: 5 actual nodes
        testCases.add(makeCase("1 4 null 4 2"));

        // Test case 2: 3 actual nodes
        testCases.add(makeCase("1 null 2 3"));

        // Test case 3: Single node
        testCases.add(makeCase("7"));

        // Test case 4: Complete binary tree with 7 nodes
        testCases.add(makeCase("1 2 3 4 5 6 7"));

        // Test case 5: Random tree with 99 actual nodes
        testCases.add(randomTree(99, 1, 100000));

        for (int i = 0; i < testCases.size(); i++) {

            TestCase tc = testCases.get(i);

            Path inputPath = inputDir.resolve(
                    String.format("input%02d.txt", i)
            );

            Path outputPath = outputDir.resolve(
                    String.format("output%02d.txt", i)
            );

            // Input: 1 4 null 4 2
            String input = String.join(" ", tc.nodes);

            // Output: 4 4 2 1
            String output = String.join(" ", tc.expected);

            Files.writeString(
                    inputPath,
                    input + System.lineSeparator()
            );

            Files.writeString(
                    outputPath,
                    output + System.lineSeparator()
            );
        }

        System.out.println(
                "Generated " + testCases.size()
                        + " test cases successfully."
        );

        System.out.println(
                "Input directory: " + inputDir.toAbsolutePath()
        );

        System.out.println(
                "Output directory: " + outputDir.toAbsolutePath()
        );
    }

    static TestCase makeCase(String input) {

        List<String> nodes = new ArrayList<>();

        for (String value : input.trim().split("\\s+")) {
            nodes.add(value.trim());
        }

        TreeNode root = buildTree(nodes);

        List<String> inorderResult = new ArrayList<>();
        inorder(root, inorderResult);

        return new TestCase(nodes, inorderResult);
    }

    static TreeNode buildTree(List<String> nodes) {

        if (nodes.isEmpty()
                || nodes.get(0).equalsIgnoreCase("null")) {
            return null;
        }

        TreeNode root = new TreeNode(
                Integer.parseInt(nodes.get(0))
        );

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;

        while (!queue.isEmpty() && i < nodes.size()) {

            TreeNode current = queue.poll();

            // Left child
            if (i < nodes.size()
                    && !nodes.get(i).equalsIgnoreCase("null")) {

                current.left = new TreeNode(
                        Integer.parseInt(nodes.get(i))
                );

                queue.offer(current.left);
            }
            i++;

            // Right child
            if (i < nodes.size()
                    && !nodes.get(i).equalsIgnoreCase("null")) {

                current.right = new TreeNode(
                        Integer.parseInt(nodes.get(i))
                );

                queue.offer(current.right);
            }
            i++;
        }

        return root;
    }

    static void inorder(TreeNode root, List<String> result) {

        if (root == null) {
            return;
        }

        inorder(root.left, result);
        result.add(String.valueOf(root.val));
        inorder(root.right, result);
    }

    static TestCase randomTree(
            int n, int minValue, int maxValue) {

        if (n <= 0 || n % 2 == 0) {
            throw new IllegalArgumentException(
                    "The number of actual nodes must be a positive odd number."
            );
        }

        Random random = new Random();

        TreeNode root = new TreeNode(
                random.nextInt(maxValue - minValue + 1) + minValue
        );

        List<TreeNode> available = new ArrayList<>();
        available.add(root);

        int count = 1;

        while (count < n) {

            int index = random.nextInt(available.size());
            TreeNode parent = available.get(index);

            boolean useLeft;

            if (parent.left == null && parent.right == null) {
                useLeft = random.nextBoolean();
            } else {
                useLeft = parent.left == null;
            }

            TreeNode child = new TreeNode(
                    random.nextInt(maxValue - minValue + 1) + minValue
            );

            if (useLeft) {
                parent.left = child;
            } else {
                parent.right = child;
            }

            available.add(child);
            count++;

            if (parent.left != null && parent.right != null) {
                available.remove(parent);
            }
        }

        List<String> serialized = new ArrayList<>();
        serialize(root, serialized);

        List<String> inorderResult = new ArrayList<>();
        inorder(root, inorderResult);

        return new TestCase(serialized, inorderResult);
    }

    static void serialize(
            TreeNode root, List<String> result) {

        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {

            TreeNode current = queue.poll();

            if (current == null) {
                result.add("null");
                continue;
            }

            result.add(String.valueOf(current.val));

            queue.offer(current.left);
            queue.offer(current.right);
        }

        // Remove unnecessary trailing null values.
        while (!result.isEmpty()
                && result.get(result.size() - 1).equals("null")) {

            result.remove(result.size() - 1);
        }
    }
}