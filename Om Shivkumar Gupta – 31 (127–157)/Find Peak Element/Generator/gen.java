import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) {
            baseDir = "../TestCases";
        }
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs();
        outDir.mkdirs();

        Random rand = new Random(129);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] nums;

            if (tc == 0) {
                nums = new int[]{1, 2, 3, 1};
            } else if (tc == 1) {
                nums = new int[]{42};
            } else if (tc == 2) {
                nums = new int[]{1, 2, 3, 4, 5};
            } else if (tc == 3) {
                nums = new int[]{5, 4, 3, 2, 1};
            } else if (tc == 4) {
                nums = new int[]{1, 2, 1, 3, 5, 6, 4};
            } else {
                if (tc < 20) n = 20 + rand.nextInt(80);
                else if (tc < 60) n = 1000 + rand.nextInt(2000);
                else n = 10000 + rand.nextInt(30000);

                nums = new int[n];
                // Ensure no two adjacent elements are equal
                int cur = rand.nextInt(100);
                for (int i = 0; i < n; i++) {
                    nums[i] = cur;
                    int step = 1 + rand.nextInt(10);
                    if (rand.nextBoolean()) cur += step;
                    else cur -= step;
                }
            }

            n = nums.length;

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) {
                inPw.print(nums[i] + (i == n - 1 ? "" : " "));
            }
            inPw.println();
            inPw.close();

            // Optimal Binary Search for peak
            int low = 0, high = n - 1;
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (nums[mid] < nums[mid + 1]) low = mid + 1;
                else high = mid;
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(low);
            outPw.close();
        }
        System.out.println("Find Peak Element: 100 test cases generated.");
    }
}
