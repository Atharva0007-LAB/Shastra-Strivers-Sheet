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

            int n, m;
            long[] arr;

            if (tc == 0) {
                n = 4; m = 2; arr = new long[]{12, 34, 67, 90};
            } else if (tc == 1) {
                n = 3; m = 4; arr = new long[]{10, 20, 30}; // m > n -> -1
            } else if (tc == 2) {
                n = 1; m = 1; arr = new long[]{50};
            } else if (tc == 3) {
                n = 5; m = 1; arr = new long[]{10, 20, 30, 40, 50};
            } else if (tc == 4) {
                n = 5; m = 5; arr = new long[]{5, 5, 5, 5, 5};
            } else {
                n = (tc < 40) ? 10 + rand.nextInt(30) : 1000 + rand.nextInt(3000);
                m = (tc % 10 == 0) ? n + 2 : 1 + rand.nextInt(n);
                arr = new long[n];
                for (int i = 0; i < n; i++) arr[i] = 1 + rand.nextInt(500);
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + m);
            for (int i = 0; i < n; i++) inPw.print(arr[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            long ans;
            if (m > n) {
                ans = -1;
            } else {
                long low = 0, high = 0;
                for (long x : arr) { low = Math.max(low, x); high += x; }
                ans = high;
                while (low <= high) {
                    long mid = low + (high - low) / 2;
                    int students = 1;
                    long cur = 0;
                    for (long x : arr) {
                        if (cur + x <= mid) cur += x;
                        else { students++; cur = x; }
                    }
                    if (students <= m) { ans = mid; high = mid - 1; }
                    else low = mid + 1;
                }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Book Allocation Problem: 100 test cases generated.");
    }
}
