import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(146);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, days;
            int[] weights;

            if (tc == 0) {
                n = 10; days = 5;
                weights = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            } else if (tc == 1) {
                n = 6; days = 3;
                weights = new int[]{3, 2, 2, 4, 1, 4};
            } else if (tc == 2) {
                n = 4; days = 1;
                weights = new int[]{1, 2, 3, 4};
            } else if (tc == 3) {
                n = 4; days = 4;
                weights = new int[]{1, 2, 3, 4};
            } else if (tc == 4) {
                n = 1; days = 1;
                weights = new int[]{50};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                weights = new int[n];
                for (int i = 0; i < n; i++) weights[i] = 1 + rand.nextInt(100);
                days = 1 + rand.nextInt(n);
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + days);
            for (int i = 0; i < n; i++) inPw.print(weights[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int max = 0, sum = 0;
            for (int w : weights) { max = Math.max(max, w); sum += w; }
            int low = max, high = sum, ans = sum;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                int d = 1, load = 0;
                for (int w : weights) {
                    if (load + w > mid) { d++; load = w; }
                    else load += w;
                }
                if (d <= days) { ans = mid; high = mid - 1; }
                else low = mid + 1;
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Problem 146 generated 100 test cases.");
    }
}
