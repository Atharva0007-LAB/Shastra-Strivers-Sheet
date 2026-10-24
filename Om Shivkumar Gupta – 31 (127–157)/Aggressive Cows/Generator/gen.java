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

            int n, k;
            int[] stalls;

            if (tc == 0) {
                n = 6; k = 4;
                stalls = new int[]{0, 3, 4, 7, 10, 9};
            } else if (tc == 1) {
                n = 2; k = 2;
                stalls = new int[]{1, 5};
            } else if (tc == 2) {
                n = 5; k = 2;
                stalls = new int[]{1, 2, 4, 8, 9};
            } else if (tc == 3) {
                n = 5; k = 5;
                stalls = new int[]{2, 4, 6, 8, 10};
            } else if (tc == 4) {
                n = 7; k = 3;
                stalls = new int[]{10, 100, 1000, 10000, 100000, 1000000, 10000000};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(30);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(20000);

                k = 2 + rand.nextInt(Math.max(2, n / 3));
                stalls = new int[n];
                int cur = 0;
                for (int i = 0; i < n; i++) {
                    cur += 1 + rand.nextInt(200);
                    stalls[i] = cur;
                }
                // shuffle stalls
                for (int i = n - 1; i > 0; i--) {
                    int j = rand.nextInt(i + 1);
                    int tmp = stalls[i]; stalls[i] = stalls[j]; stalls[j] = tmp;
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + k);
            for (int i = 0; i < n; i++) inPw.print(stalls[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            // Optimal solve
            int[] sorted = stalls.clone();
            Arrays.sort(sorted);
            int low = 1, high = sorted[n - 1] - sorted[0], ans = 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                int cows = 1, last = sorted[0];
                for (int i = 1; i < n; i++) {
                    if (sorted[i] - last >= mid) {
                        cows++;
                        last = sorted[i];
                    }
                }
                if (cows >= k) {
                    ans = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Aggressive Cows: 100 test cases generated.");
    }
}
