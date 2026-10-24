import java.io.*;
import java.util.*;

public class gen {

    static class TestCase {
        int[] nums;

        TestCase(int[] nums) {
            this.nums = nums;
        }
    }

    public static void main(String[] args) throws IOException {
        String baseDir = "Testcases";

        if (!new File(baseDir).exists()
                && new File("../Testcases").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");

        inputDir.mkdirs();
        outputDir.mkdirs();

        List<TestCase> testCases = new ArrayList<>();

        testCases.add(generateTC0());
        testCases.add(generateTC1());
        testCases.add(generateTC2());
        testCases.add(generateTC3());
        testCases.add(generateTC4());
        testCases.add(generateTC5());
        testCases.add(generateTC6());
        testCases.add(generateTC7());

        for (int i = 0; i < testCases.size(); i++) {
            String fileName = String.format("%02d", i);

            File inFile = new File(
                inputDir, "input" + fileName + ".txt"
            );

            File outFile = new File(
                outputDir, "output" + fileName + ".txt"
            );

            int[] nums = testCases.get(i).nums;

            try (PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter(inFile)))) {

                writer.println(nums.length);

                for (int j = 0; j < nums.length; j++) {
                    if (j > 0) {
                        writer.print(" ");
                    }
                    writer.print(nums[j]);
                }

                writer.println();
            }

            int[] sorted = nums.clone();
            solve(sorted);

            try (PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter(outFile)))) {

                for (int j = 0; j < sorted.length; j++) {
                    if (j > 0) {
                        writer.print(" ");
                    }
                    writer.print(sorted[j]);
                }

                writer.println();
            }
        }

        System.out.println(
            "8 test cases generated successfully!"
        );
    }

    static void solve(int[] nums) {
        int low = 0;
        int mid = 0;
        int high = nums.length - 1;

        while (mid <= high) {
            if (nums[mid] == 0) {
                int temp = nums[low];
                nums[low] = nums[mid];
                nums[mid] = temp;

                low++;
                mid++;
            }
            else if (nums[mid] == 1) {
                mid++;
            }
            else {
                int temp = nums[mid];
                nums[mid] = nums[high];
                nums[high] = temp;

                high--;
            }
        }
    }

    static TestCase generateTC0() {
        return new TestCase(
            new int[]{2, 0, 2, 1, 1, 0}
        );
    }

    static TestCase generateTC1() {
        return new TestCase(
            new int[]{0, 0, 1, 1, 1}
        );
    }

    static TestCase generateTC2() {
        return new TestCase(
            new int[]{2, 2, 2, 2, 2}
        );
    }

    static TestCase generateTC3() {
        return new TestCase(
            new int[]{0, 0, 0, 0, 0}
        );
    }

    static TestCase generateTC4() {
        return new TestCase(
            new int[]{1, 1, 1, 1, 1, 1}
        );
    }

    static TestCase generateTC5() {
        return new TestCase(
            new int[]{2, 1, 0}
        );
    }

    static TestCase generateTC6() {
        int n = 1000;
        int[] nums = new int[n];

        Random random = new Random(42);

        for (int i = 0; i < n; i++) {
            nums[i] = random.nextInt(3);
        }

        return new TestCase(nums);
    }

    static TestCase generateTC7() {
        int n = 100000;
        int[] nums = new int[n];

        Random random = new Random(12345);

        for (int i = 0; i < n; i++) {
            nums[i] = random.nextInt(3);
        }

        return new TestCase(nums);
    }
}