import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(137);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] nums;

            if (tc == 0) {
                n = 5; nums = new int[]{3, 4, 5, 1, 2};
            } else if (tc == 1) {
                n = 7; nums = new int[]{4, 5, 6, 7, 0, 1, 2};
            } else if (tc == 2) {
                n = 4; nums = new int[]{11, 13, 15, 17};
            } else if (tc == 3) {
                n = 1; nums = new int[]{42};
            } else if (tc == 4) {
                n = 2; nums = new int[]{2, 1};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                int[] sorted = new int[n];
                int cur = -5000;
                for (int i = 0; i < n; i++) {
                    cur += 1 + rand.nextInt(5);
                    sorted[i] = cur;
                }
                int pivot = rand.nextInt(n);
                nums = new int[n];
                for (int i = 0; i < n; i++) nums[i] = sorted[(i + pivot) % n];
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) inPw.print(nums[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int min = nums[0];
            for (int x : nums) if (x < min) min = x;

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(min);
            outPw.close();
        }
        System.out.println("Problem 137 generated 100 test cases.");
    }
}
