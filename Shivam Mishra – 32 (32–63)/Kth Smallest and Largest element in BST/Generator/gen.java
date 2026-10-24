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
        int k;
        List<String> expected;

        TestCase(List<String> nodes, int k, List<String> expected) {
            this.nodes = nodes;
            this.k = k;
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

        // Test case 1: Balanced BST
        testCases.add(makeCase("5 3 7 2 4 6 8", 2));

        // Test case 2: BST with missing children
        testCases.add(makeCase("10 5 15 2 7 null 20", 3));

        // Test case 3: Single-node BST
        testCases.add(makeCase("7", 1));

        // Test case 4: BST with two nodes
        testCases.add(makeCase("10 5", 1));

        // Test case 5: Skewed BST
        testCases.add(makeCase("1 null 2 null 3 null 4 null 5", 2));

        // Test case 6: Larger balanced BST
        testCases.add(makeCase(
                "50 30 70 20 40 60 80 10 25 35 45 55 65 75 90", 4));

        // Test case 7: k is equal to the number of nodes
        testCases.add(makeCase("8 4 12 2 6 10 14", 7));

        // Test case 8: Random BST with 99 nodes
        testCases.add(randomBST(99, 1, 100000, 25));

        for (int i = 0; i < testCases.size(); i++) {

            TestCase tc = testCases.get(i);

            Path inputPath = inputDir.resolve(
                    String.format("input%02d.txt", i));

            Path outputPath = outputDir.resolve(
                    String.format("output%02d.txt", i));

            // Input format: level-order BST followed by k
            List<String> inputTokens = new ArrayList<>(tc.nodes);
            inputTokens.add(String.valueOf(tc.k));

            String input = String.join(" ", inputTokens);

            // Output format: kth smallest followed by kth largest
            String output = String.join(" ", tc.expected);

            Files.writeString(
                    inputPath,
                    input + System.lineSeparator());

            Files.writeString(
                    outputPath,
                    output + System.lineSeparator());
        }

        System.out.println(
                "Generated " + testCases.size()
                        + " test cases successfully.");

        System.out.println(
                "Input directory: " + inputDir.toAbsolutePath());

        System.out.println(
                "Output directory: " + outputDir.toAbsolutePath());
    }

    static TestCase makeCase(String input, int k) {

        List<String> nodes = new ArrayList<>(
                Arrays.asList(input.trim().split("\\s+")));

        TreeNode root = buildTree(nodes);

        List<Integer> sorted = new ArrayList<>();
        inorder(root, sorted);

        if (k < 1 || k > sorted.size()) {
            throw new IllegalArgumentException(
                    "k must be between 1 and the number of nodes.");
        }

        int smallest = sorted.get(k - 1);
        int largest = sorted.get(sorted.size() - k);

        List<String> expected = Arrays.asList(
                String.valueOf(smallest),
                String.valueOf(largest));

        return new TestCase(nodes, k, expected);
    }

    static TreeNode buildTree(List<String> nodes) {

        if (nodes.isEmpty()
                || nodes.get(0).equalsIgnoreCase("null")) {
            return null;
        }

        TreeNode root = new TreeNode(
                Integer.parseInt(nodes.get(0)));

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;

        while (!queue.isEmpty() && i < nodes.size()) {

            TreeNode current = queue.poll();

            // Left child
            if (i < nodes.size()
                    && !nodes.get(i).equalsIgnoreCase("null")) {

                current.left = new TreeNode(
                        Integer.parseInt(nodes.get(i)));

                queue.offer(current.left);
            }
            i++;

            // Right child
            if (i < nodes.size()
                    && !nodes.get(i).equalsIgnoreCase("null")) {

                current.right = new TreeNode(
                        Integer.parseInt(nodes.get(i)));

                queue.offer(current.right);
            }
            i++;
        }

        return root;
    }

    static void inorder(TreeNode root, List<Integer> result) {

        if (root == null) {
            return;
        }

        inorder(root.left, result);
        result.add(root.val);
        inorder(root.right, result);
    }

    static TestCase randomBST(
            int n, int minValue, int maxValue, int k) {

        if (n <= 0 || minValue > maxValue
                || (long) maxValue - minValue + 1 < n) {
            throw new IllegalArgumentException(
                    "Invalid node count or value range.");
        }

        if (k < 1 || k > n) {
            throw new IllegalArgumentException(
                    "k must be between 1 and n.");
        }

        Random random = new Random();

        // Generate distinct values so the BST has no duplicate keys.
        List<Integer> values = new ArrayList<>();

        for (int value = minValue; value <= maxValue; value++) {
            values.add(value);

            if (values.size() == n) {
                break;
            }
        }

        Collections.shuffle(values, random);

        TreeNode root = null;

        for (int value : values) {
            root = insert(root, value);
        }

        List<String> serialized = new ArrayList<>();
        serialize(root, serialized);

        List<Integer> sorted = new ArrayList<>();
        inorder(root, sorted);

        List<String> expected = Arrays.asList(
                String.valueOf(sorted.get(k - 1)),
                String.valueOf(sorted.get(n - k)));

        return new TestCase(serialized, k, expected);
    }

    static TreeNode insert(TreeNode root, int value) {

        if (root == null) {
            return new TreeNode(value);
        }

        if (value < root.val) {
            root.left = insert(root.left, value);
        } else if (value > root.val) {
            root.right = insert(root.right, value);
        }

        return root;
    }

    static void serialize(TreeNode root, List<String> result) {

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