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

        Random rand = new Random(135);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] price;

            if (tc == 0) {
                price = new int[]{1, 5, 8, 9, 10, 17, 17, 20};
            } else if (tc == 1) {
                price = new int[]{10};
            } else if (tc == 2) {
                price = new int[]{1, 3, 5, 10};
            } else if (tc == 3) {
                price = new int[]{3, 1, 1, 1};
            } else if (tc == 4) {
                price = new int[]{2, 5, 7, 8, 10, 13};
            } else {
                // Scaled sizes: large n ensures brute force O(2^N) fails
                if (tc < 30) n = 15 + rand.nextInt(20);
                else if (tc < 70) n = 100 + rand.nextInt(200);
                else n = 500 + rand.nextInt(400);

                price = new int[n];
                int cur = 2;
                for (int i = 0; i < n; i++) {
                    price[i] = cur;
                    cur += 1 + rand.nextInt(5);
                }
            }

            n = price.length;

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) inPw.print(price[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            // Optimal 1D DP
            int[] dp = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                int maxVal = Integer.MIN_VALUE;
                for (int j = 1; j <= i; j++) {
                    maxVal = Math.max(maxVal, price[j - 1] + dp[i - j]);
                }
                dp[i] = maxVal;
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(dp[n]);
            outPw.close();
        }
        System.out.println("Rod Cutting Problem: 100 test cases generated.");
    }
}
