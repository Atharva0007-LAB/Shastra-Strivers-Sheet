import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(139);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] nums;

            if (tc == 0) {
                n = 9; nums = new int[]{1, 1, 2, 3, 3, 4, 4, 8, 8};
            } else if (tc == 1) {
                n = 7; nums = new int[]{3, 3, 7, 7, 10, 11, 11};
            } else if (tc == 2) {
                n = 1; nums = new int[]{5};
            } else if (tc == 3) {
                n = 3; nums = new int[]{1, 2, 2};
            } else if (tc == 4) {
                n = 3; nums = new int[]{1, 1, 2};
            } else {
                int pairs;
                if (tc < 25) pairs = 5 + rand.nextInt(20);
                else if (tc < 65) pairs = 500 + rand.nextInt(1000);
                else pairs = 8000 + rand.nextInt(12000);

                n = 2 * pairs + 1;
                nums = new int[n];
                int singleIdx = rand.nextInt(pairs + 1);
                int val = 1;
                int idx = 0;
                for (int p = 0; p <= pairs; p++) {
                    if (p == singleIdx) {
                        nums[idx++] = val++;
                    }
                    if (p < pairs) {
                        nums[idx++] = val;
                        nums[idx++] = val++;
                    }
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) inPw.print(nums[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int xor = 0;
            for (int x : nums) xor ^= x;

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(xor);
            outPw.close();
        }
        System.out.println("Problem 139 generated 100 test cases.");
    }
}
