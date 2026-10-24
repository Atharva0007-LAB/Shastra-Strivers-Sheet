import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(144);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, h;
            int[] piles;

            if (tc == 0) {
                n = 4; h = 8;
                piles = new int[]{3, 6, 7, 11};
            } else if (tc == 1) {
                n = 5; h = 5;
                piles = new int[]{30, 11, 23, 4, 20};
            } else if (tc == 2) {
                n = 5; h = 6;
                piles = new int[]{30, 11, 23, 4, 20};
            } else if (tc == 3) {
                n = 1; h = 10;
                piles = new int[]{1000};
            } else if (tc == 4) {
                n = 4; h = 4;
                piles = new int[]{5, 5, 5, 5};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                piles = new int[n];
                int max = 0;
                for (int i = 0; i < n; i++) {
                    piles[i] = 1 + rand.nextInt(20000);
                    max = Math.max(max, piles[i]);
                }
                h = n + rand.nextInt(n * 2);
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + h);
            for (int i = 0; i < n; i++) inPw.print(piles[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int max = 0;
            for (int p : piles) max = Math.max(max, p);
            int low = 1, high = max, ans = max;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                long total = 0;
                for (int p : piles) total += (p + mid - 1) / mid;
                if (total <= h) {
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
        System.out.println("Problem 144 generated 100 test cases.");
    }
}
