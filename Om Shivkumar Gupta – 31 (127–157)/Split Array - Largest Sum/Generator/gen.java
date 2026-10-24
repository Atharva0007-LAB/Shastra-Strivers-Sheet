import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(155);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, k;
            int[] nums;

            if (tc == 0) {
                n = 5; k = 2;
                nums = new int[]{7, 2, 5, 10, 8};
            } else if (tc == 1) {
                n = 5; k = 1;
                nums = new int[]{1, 2, 3, 4, 5};
            } else if (tc == 2) {
                n = 1; k = 1;
                nums = new int[]{10};
            } else if (tc == 3) {
                n = 4; k = 4;
                nums = new int[]{1, 4, 4, 5};
            } else if (tc == 4) {
                n = 5; k = 3;
                nums = new int[]{2, 3, 1, 2, 4};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                nums = new int[n];
                for (int i = 0; i < n; i++) nums[i] = 1 + rand.nextInt(100);
                k = 1 + rand.nextInt(Math.min(50, n));
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + k);
            for (int i = 0; i < n; i++) inPw.print(nums[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            long max = 0, sum = 0;
            for (int x : nums) { max = Math.max(max, x); sum += x; }
            long low = max, high = sum, ans = sum;
            while (low <= high) {
                long mid = low + (high - low) / 2;
                int count = 1;
                long cur = 0;
                for (int x : nums) {
                    if (cur + x > mid) { count++; cur = x; }
                    else cur += x;
                }
                if (count <= k) { ans = mid; high = mid - 1; }
                else low = mid + 1;
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Problem 155 generated 100 test cases.");
    }
}
