import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(154);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, k;
            int[] stations;

            if (tc == 0) {
                n = 10; k = 1;
                stations = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            } else if (tc == 1) {
                n = 5; k = 4;
                stations = new int[]{1, 13, 17, 23, 35};
            } else if (tc == 2) {
                n = 2; k = 1;
                stations = new int[]{1, 10};
            } else if (tc == 3) {
                n = 3; k = 2;
                stations = new int[]{10, 20, 30};
            } else if (tc == 4) {
                n = 4; k = 3;
                stations = new int[]{1, 2, 8, 9};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(30);
                else if (tc < 65) n = 500 + rand.nextInt(1000);
                else n = 10000 + rand.nextInt(15000);

                stations = new int[n];
                int cur = 0;
                for (int i = 0; i < n; i++) {
                    cur += 1 + rand.nextInt(100);
                    stations[i] = cur;
                }
                k = 1 + rand.nextInt(Math.min(500, n * 2));
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + k);
            for (int i = 0; i < n; i++) inPw.print(stations[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            double low = 0, high = 0;
            for (int i = 0; i < n - 1; i++) high = Math.max(high, (double)(stations[i+1] - stations[i]));
            while (high - low > 1e-6) {
                double mid = (low + high) / 2.0;
                int req = 0;
                for (int i = 1; i < n; i++) {
                    int inBetween = (int)((stations[i] - stations[i-1]) / mid);
                    if ((stations[i] - stations[i-1]) == inBetween * mid) inBetween--;
                    req += inBetween;
                }
                if (req > k) low = mid;
                else high = mid;
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(String.format(Locale.US, "%.6f", high));
            outPw.close();
        }
        System.out.println("Problem 154 generated 100 test cases.");
    }
}
