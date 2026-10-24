import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(134);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, target;
            int[] nums;

            if (tc == 0) {
                n = 6; target = 8;
                nums = new int[]{5, 7, 7, 8, 8, 10};
            } else if (tc == 1) {
                n = 6; target = 6;
                nums = new int[]{5, 7, 7, 8, 8, 10};
            } else if (tc == 2) {
                n = 1; target = 5;
                nums = new int[]{5};
            } else if (tc == 3) {
                n = 1; target = 3;
                nums = new int[]{5};
            } else if (tc == 4) {
                n = 5; target = 2;
                nums = new int[]{2, 2, 2, 2, 2};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                nums = new int[n];
                int cur = 0;
                for (int i = 0; i < n; i++) {
                    if (rand.nextDouble() > 0.4) cur++;
                    nums[i] = cur;
                }
                if (rand.nextBoolean()) {
                    target = nums[rand.nextInt(n)];
                } else {
                    target = cur + 5;
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + target);
            for (int i = 0; i < n; i++) inPw.print(nums[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int first = -1, last = -1;
            for (int i = 0; i < n; i++) {
                if (nums[i] == target) {
                    if (first == -1) first = i;
                    last = i;
                }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(first + " " + last);
            outPw.close();
        }
        System.out.println("Problem 134 generated 100 test cases.");
    }
}
