import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(132);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, target;
            int[] nums;

            if (tc == 0) {
                n = 4; target = 5;
                nums = new int[]{1, 3, 5, 6};
            } else if (tc == 1) {
                n = 4; target = 2;
                nums = new int[]{1, 3, 5, 6};
            } else if (tc == 2) {
                n = 4; target = 7;
                nums = new int[]{1, 3, 5, 6};
            } else if (tc == 3) {
                n = 4; target = 0;
                nums = new int[]{1, 3, 5, 6};
            } else if (tc == 4) {
                n = 1; target = 0;
                nums = new int[]{1};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                nums = new int[n];
                int cur = -1000;
                for (int i = 0; i < n; i++) {
                    cur += 1 + rand.nextInt(4);
                    nums[i] = cur;
                }
                target = cur - rand.nextInt(cur + 2000);
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + target);
            for (int i = 0; i < n; i++) inPw.print(nums[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int low = 0, high = n - 1, ans = n;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (nums[mid] >= target) {
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
        System.out.println("Problem 132 generated 100 test cases.");
    }
}
