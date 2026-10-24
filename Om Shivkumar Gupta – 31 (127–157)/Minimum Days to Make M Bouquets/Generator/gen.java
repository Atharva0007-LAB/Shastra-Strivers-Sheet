import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(145);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, m, k;
            int[] bloomDay;

            if (tc == 0) {
                n = 5; m = 3; k = 1;
                bloomDay = new int[]{1, 10, 3, 10, 2};
            } else if (tc == 1) {
                n = 5; m = 3; k = 2;
                bloomDay = new int[]{1, 10, 3, 10, 2};
            } else if (tc == 2) {
                n = 6; m = 2; k = 3;
                bloomDay = new int[]{7, 7, 7, 7, 12, 7};
            } else if (tc == 3) {
                n = 1; m = 1; k = 1;
                bloomDay = new int[]{5};
            } else if (tc == 4) {
                n = 5; m = 2; k = 2;
                bloomDay = new int[]{1, 2, 4, 9, 3};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                bloomDay = new int[n];
                for (int i = 0; i < n; i++) bloomDay[i] = 1 + rand.nextInt(10000);

                if (tc % 5 == 0) {
                    m = n + 5; k = 1;
                } else {
                    k = 1 + rand.nextInt(Math.min(5, n));
                    m = 1 + rand.nextInt(Math.max(1, n / k));
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + m + " " + k);
            for (int i = 0; i < n; i++) inPw.print(bloomDay[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int ans = -1;
            if ((long) m * k <= n) {
                int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
                for (int b : bloomDay) { min = Math.min(min, b); max = Math.max(max, b); }
                int low = min, high = max;
                while (low <= high) {
                    int mid = low + (high - low) / 2;
                    int cnt = 0, bouquets = 0;
                    for (int b : bloomDay) {
                        if (b <= mid) cnt++;
                        else { bouquets += cnt / k; cnt = 0; }
                    }
                    bouquets += cnt / k;
                    if (bouquets >= m) { ans = mid; high = mid - 1; }
                    else low = mid + 1;
                }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Problem 145 generated 100 test cases.");
    }
}
