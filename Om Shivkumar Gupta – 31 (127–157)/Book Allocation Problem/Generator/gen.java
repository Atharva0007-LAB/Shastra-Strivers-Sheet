import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(150);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, m;
            int[] arr;

            if (tc == 0) {
                n = 4; m = 2;
                arr = new int[]{12, 34, 67, 90};
            } else if (tc == 1) {
                n = 4; m = 5;
                arr = new int[]{10, 20, 30, 40};
            } else if (tc == 2) {
                n = 1; m = 1;
                arr = new int[]{100};
            } else if (tc == 3) {
                n = 5; m = 1;
                arr = new int[]{25, 46, 28, 49, 24};
            } else if (tc == 4) {
                n = 5; m = 5;
                arr = new int[]{10, 20, 30, 40, 50};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                arr = new int[n];
                for (int i = 0; i < n; i++) arr[i] = 1 + rand.nextInt(100);
                if (tc % 7 == 0) m = n + 5;
                else m = 1 + rand.nextInt(Math.min(n, 40));
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + m);
            for (int i = 0; i < n; i++) inPw.print(arr[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            long ans = -1;
            if (m <= n) {
                long max = 0, sum = 0;
                for (int p : arr) { max = Math.max(max, p); sum += p; }
                long low = max, high = sum;
                ans = sum;
                while (low <= high) {
                    long mid = low + (high - low) / 2;
                    int students = 1;
                    long pages = 0;
                    for (int p : arr) {
                        if (pages + p > mid) { students++; pages = p; }
                        else pages += p;
                    }
                    if (students <= m) { ans = mid; high = mid - 1; }
                    else low = mid + 1;
                }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Problem 150 generated 100 test cases.");
    }
}
