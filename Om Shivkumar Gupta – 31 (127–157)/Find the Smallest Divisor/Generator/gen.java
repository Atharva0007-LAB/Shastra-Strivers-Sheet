import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(143);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, threshold;
            int[] nums;

            if (tc == 0) {
                n = 4; threshold = 6;
                nums = new int[]{1, 2, 5, 9};
            } else if (tc == 1) {
                n = 5; threshold = 5;
                nums = new int[]{2, 3, 5, 7, 11};
            } else if (tc == 2) {
                n = 1; threshold = 1;
                nums = new int[]{100};
            } else if (tc == 3) {
                n = 5; threshold = 100;
                nums = new int[]{19, 12, 10, 9, 7};
            } else if (tc == 4) {
                n = 4; threshold = 8;
                nums = new int[]{10, 20, 30, 40};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                nums = new int[n];
                int max = 0;
                for (int i = 0; i < n; i++) {
                    nums[i] = 1 + rand.nextInt(10000);
                    max = Math.max(max, nums[i]);
                }
                threshold = n + rand.nextInt(n * 2);
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + threshold);
            for (int i = 0; i < n; i++) inPw.print(nums[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int max = 0;
            for (int x : nums) max = Math.max(max, x);
            int low = 1, high = max, ans = max;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                long sum = 0;
                for (int x : nums) sum += (x + mid - 1) / mid;
                if (sum <= threshold) {
                    ans = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Problem 143 generated 100 test cases.");
    }
}
