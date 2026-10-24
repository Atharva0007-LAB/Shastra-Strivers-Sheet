import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(153);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int m, n, k;
            int[] arr1, arr2;

            if (tc == 0) {
                m = 5; n = 4; k = 5;
                arr1 = new int[]{2, 3, 6, 7, 9}; arr2 = new int[]{1, 4, 8, 10};
            } else if (tc == 1) {
                m = 1; n = 1; k = 1;
                arr1 = new int[]{10}; arr2 = new int[]{20};
            } else if (tc == 2) {
                m = 1; n = 1; k = 2;
                arr1 = new int[]{10}; arr2 = new int[]{20};
            } else if (tc == 3) {
                m = 4; n = 3; k = 7;
                arr1 = new int[]{1, 3, 5, 7}; arr2 = new int[]{2, 4, 6};
            } else if (tc == 4) {
                m = 3; n = 5; k = 1;
                arr1 = new int[]{5, 8, 10}; arr2 = new int[]{1, 2, 3, 4, 6};
            } else {
                if (tc < 25) { m = 5 + rand.nextInt(20); n = 5 + rand.nextInt(20); }
                else if (tc < 65) { m = 500 + rand.nextInt(1000); n = 500 + rand.nextInt(1000); }
                else { m = 10000 + rand.nextInt(15000); n = 10000 + rand.nextInt(15000); }

                arr1 = new int[m];
                int cur = 0;
                for (int i = 0; i < m; i++) { cur += rand.nextInt(4); arr1[i] = cur; }
                arr2 = new int[n];
                cur = 0;
                for (int i = 0; i < n; i++) { cur += rand.nextInt(4); arr2[i] = cur; }
                k = 1 + rand.nextInt(m + n);
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(m + " " + n + " " + k);
            for (int i = 0; i < m; i++) inPw.print(arr1[i] + (i == m - 1 ? "" : " "));
            inPw.println();
            for (int i = 0; i < n; i++) inPw.print(arr2[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int[] merged = new int[m + n];
            int i = 0, j = 0, p = 0;
            while (i < m && j < n) {
                if (arr1[i] <= arr2[j]) merged[p++] = arr1[i++];
                else merged[p++] = arr2[j++];
            }
            while (i < m) merged[p++] = arr1[i++];
            while (j < n) merged[p++] = arr2[j++];

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(merged[k - 1]);
            outPw.close();
        }
        System.out.println("Problem 153 generated 100 test cases.");
    }
}
