import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(148);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, k;
            int[] boards;

            if (tc == 0) {
                n = 4; k = 2;
                boards = new int[]{10, 20, 30, 40};
            } else if (tc == 1) {
                n = 4; k = 4;
                boards = new int[]{10, 20, 30, 40};
            } else if (tc == 2) {
                n = 1; k = 1;
                boards = new int[]{50};
            } else if (tc == 3) {
                n = 5; k = 1;
                boards = new int[]{5, 5, 5, 5, 5};
            } else if (tc == 4) {
                n = 5; k = 3;
                boards = new int[]{2, 1, 5, 6, 2};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                boards = new int[n];
                for (int i = 0; i < n; i++) boards[i] = 1 + rand.nextInt(200);
                k = 1 + rand.nextInt(Math.min(n, 50));
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + k);
            for (int i = 0; i < n; i++) inPw.print(boards[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            long max = 0, sum = 0;
            for (int b : boards) { max = Math.max(max, b); sum += b; }
            long low = max, high = sum, ans = sum;
            while (low <= high) {
                long mid = low + (high - low) / 2;
                int painters = 1;
                long load = 0;
                for (int b : boards) {
                    if (load + b > mid) { painters++; load = b; }
                    else load += b;
                }
                if (painters <= k) { ans = mid; high = mid - 1; }
                else low = mid + 1;
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Problem 148 generated 100 test cases.");
    }
}
