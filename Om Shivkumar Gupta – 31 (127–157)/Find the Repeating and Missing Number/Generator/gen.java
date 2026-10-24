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

        Random rand = new Random(138);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] nums;
            int rep, mis;

            if (tc == 0) {
                nums = new int[]{3, 1, 2, 5, 3};
                rep = 3; mis = 4;
            } else if (tc == 1) {
                nums = new int[]{2, 2};
                rep = 2; mis = 1;
            } else if (tc == 2) {
                nums = new int[]{1, 1};
                rep = 1; mis = 2;
            } else if (tc == 3) {
                nums = new int[]{4, 3, 6, 2, 1, 1};
                rep = 1; mis = 5;
            } else if (tc == 4) {
                nums = new int[]{1, 2, 3, 5, 6, 7, 8, 8};
                rep = 8; mis = 4;
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 60) n = 1000 + rand.nextInt(2000);
                else n = 10000 + rand.nextInt(25000);

                nums = new int[n];
                for (int i = 0; i < n; i++) nums[i] = i + 1;

                mis = 1 + rand.nextInt(n);
                rep = 1 + rand.nextInt(n);
                while (rep == mis) rep = 1 + rand.nextInt(n);

                // Replace missing number with repeating number
                nums[mis - 1] = rep;

                // Shuffle array
                for (int i = n - 1; i > 0; i--) {
                    int j = rand.nextInt(i + 1);
                    int tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tmp;
                }
            }

            n = nums.length;

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) inPw.print(nums[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            // Optimal Math solution to calculate rep and mis
            long N = n;
            long sumN = (N * (N + 1)) / 2;
            long sumSqN = (N * (N + 1) * (2 * N + 1)) / 6;
            long sumArr = 0, sumSqArr = 0;
            for (int x : nums) {
                sumArr += (long)x;
                sumSqArr += (long)x * (long)x;
            }
            long diff = sumArr - sumN; // R - M
            long sqDiff = sumSqArr - sumSqN; // R^2 - M^2
            long sumVal = sqDiff / diff; // R + M
            long calculatedRep = (diff + sumVal) / 2;
            long calculatedMis = calculatedRep - diff;

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(calculatedRep + " " + calculatedMis);
            outPw.close();
        }
        System.out.println("Find the Repeating and Missing Number: 100 test cases generated.");
    }
}
