import java.io.*;
import java.util.*;

public class gen {

    static class TestCase {
        int[] nums;

        TestCase(int[] nums) {
            this.nums = nums;
        }
    }

    // Efficient reference solver: O(n log n)
    static int solve(int[] nums) {
        int[] tails = new int[nums.length];
        int length = 0;

        for (int x : nums) {
            int left = 0;
            int right = length;

            // Find the first position with tails[pos] >= x.
            while (left < right) {
                int mid = left + (right - left) / 2;

                if (tails[mid] >= x) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }

            tails[left] = x;

            if (left == length) {
                length++;
            }
        }

        return length;
    }

    static int[] randomArray(
            Random random, int n, int min, int max) {

        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = random.nextInt(max - min + 1) + min;
        }

        return nums;
    }

    public static void main(String[] args) throws IOException {

        File base = new File("Testcases");

        File inputDir = new File(base, "input");
        File outputDir = new File(base, "output");

        if (!inputDir.exists()) {
            inputDir.mkdirs();
        }

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        List<TestCase> cases = new ArrayList<>();

        // 1. Standard example
        cases.add(new TestCase(
            new int[]{10, 9, 2, 5, 3, 7, 101, 18}
        ));

        // 2. Duplicate values mixed with increasing values
        cases.add(new TestCase(
            new int[]{0, 1, 0, 3, 2, 3}
        ));

        // 3. All elements equal
        cases.add(new TestCase(
            new int[]{7, 7, 7, 7, 7, 7, 7}
        ));

        // 4. Single element
        cases.add(new TestCase(
            new int[]{1}
        ));

        // 5. Strictly increasing
        cases.add(new TestCase(
            new int[]{1, 2, 3, 4, 5, 6}
        ));

        // 6. Strictly decreasing
        cases.add(new TestCase(
            new int[]{6, 5, 4, 3, 2, 1}
        ));

        // 7. Negative and positive values
        cases.add(new TestCase(
            new int[]{-5, -1, -3, 0, 2, -2, 4}
        ));

        Random random = new Random(20261024);

        // 8. Medium random test case
        cases.add(new TestCase(
            randomArray(random, 1000, -1000, 1000)
        ));

        // 9. Large random test case
        cases.add(new TestCase(
            randomArray(random, 100000, -1000000, 1000000)
        ));

        // 10. Large strictly increasing test case
        int[] increasing = new int[100000];

        for (int i = 0; i < increasing.length; i++) {
            increasing[i] = i - 50000;
        }

        cases.add(new TestCase(increasing));

        // Generate input and expected output files.
        for (int i = 0; i < cases.size(); i++) {

            String id = String.format("%02d", i);
            TestCase testCase = cases.get(i);

            File inputFile = new File(
                inputDir, "input" + id + ".txt"
            );

            File outputFile = new File(
                outputDir, "output" + id + ".txt"
            );

            try (PrintWriter writer = new PrintWriter(
                    new BufferedWriter(
                        new FileWriter(inputFile)))) {

                writer.println(testCase.nums.length);

                for (int j = 0; j < testCase.nums.length; j++) {
                    if (j > 0) {
                        writer.print(" ");
                    }

                    writer.print(testCase.nums[j]);
                }

                writer.println();
            }

            int answer = solve(testCase.nums);

            try (PrintWriter writer = new PrintWriter(
                    new BufferedWriter(
                        new FileWriter(outputFile)))) {

                writer.println(answer);
            }
        }

        System.out.println(
            "Generated " + cases.size() + " test cases successfully."
        );
    }
}