import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(151);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] prices;

            if (tc == 0) {
                prices = new int[]{7, 1, 5, 3, 6, 4};
            } else if (tc == 1) {
                prices = new int[]{1, 2, 3, 4, 5};
            } else if (tc == 2) {
                prices = new int[]{7, 6, 4, 3, 1};
            } else if (tc == 3) {
                prices = new int[]{10};
            } else if (tc == 4) {
                prices = new int[]{3, 3, 3, 3, 3};
            } else {
                n = (tc < 40) ? 20 + rand.nextInt(30) : 1000 + rand.nextInt(5000);
                prices = new int[n];
                int cur = 50;
                for (int i = 0; i < n; i++) {
                    cur = Math.max(1, cur + rand.nextInt(21) - 10);
                    prices[i] = cur;
                }
            }

            n = prices.length;

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) inPw.print(prices[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int profit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(profit);
            outPw.close();
        }
        System.out.println("Best Time to Buy and Sell Stock II: 100 test cases generated.");
    }
}
