import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(130);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, x;
            int[] arr;

            if (tc == 0) {
                n = 5; x = 7;
                arr = new int[]{3, 5, 8, 15, 19};
            } else if (tc == 1) {
                n = 4; x = 2;
                arr = new int[]{1, 2, 2, 3};
            } else if (tc == 2) {
                n = 1; x = 10;
                arr = new int[]{5};
            } else if (tc == 3) {
                n = 1; x = 5;
                arr = new int[]{5};
            } else if (tc == 4) {
                n = 5; x = 1;
                arr = new int[]{2, 3, 7, 10, 11};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                arr = new int[n];
                int cur = 0;
                for (int i = 0; i < n; i++) {
                    cur += rand.nextInt(3);
                    arr[i] = cur;
                }
                x = rand.nextInt(cur + 5);
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + x);
            for (int i = 0; i < n; i++) inPw.print(arr[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int low = 0, high = n - 1, ans = n;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (arr[mid] >= x) {
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
        System.out.println("Problem 130 generated 100 test cases.");
    }
}
