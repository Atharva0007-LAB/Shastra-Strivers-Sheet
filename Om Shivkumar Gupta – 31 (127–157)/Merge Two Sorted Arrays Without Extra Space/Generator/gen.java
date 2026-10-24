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

            int n, m;
            long[] a1, a2;

            if (tc == 0) {
                n = 4; m = 5;
                a1 = new long[]{1, 3, 5, 7};
                a2 = new long[]{0, 2, 6, 8, 9};
            } else if (tc == 1) {
                n = 1; m = 1;
                a1 = new long[]{5};
                a2 = new long[]{2};
            } else if (tc == 2) {
                n = 3; m = 3;
                a1 = new long[]{1, 2, 3};
                a2 = new long[]{4, 5, 6};
            } else if (tc == 3) {
                n = 3; m = 3;
                a1 = new long[]{4, 5, 6};
                a2 = new long[]{1, 2, 3};
            } else if (tc == 4) {
                n = 4; m = 4;
                a1 = new long[]{-10, -5, 0, 5};
                a2 = new long[]{-8, -2, 3, 10};
            } else {
                n = (tc < 40) ? 20 + rand.nextInt(30) : 500 + rand.nextInt(1000);
                m = (tc < 40) ? 20 + rand.nextInt(30) : 500 + rand.nextInt(1000);
                a1 = new long[n];
                a2 = new long[m];
                for (int i = 0; i < n; i++) a1[i] = rand.nextInt(200000) - 100000;
                for (int i = 0; i < m; i++) a2[i] = rand.nextInt(200000) - 100000;
                Arrays.sort(a1);
                Arrays.sort(a2);
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + m);
            for (int i = 0; i < n; i++) inPw.print(a1[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            for (int i = 0; i < m; i++) inPw.print(a2[i] + (i == m - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            // Combined sort
            long[] combined = new long[n + m];
            System.arraycopy(a1, 0, combined, 0, n);
            System.arraycopy(a2, 0, combined, n, m);
            Arrays.sort(combined);

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (int i = 0; i < n; i++) outPw.print(combined[i] + (i == n - 1 ? "" : " "));
            outPw.println();
            for (int i = 0; i < m; i++) outPw.print(combined[n + i] + (i == m - 1 ? "" : " "));
            outPw.println();
            outPw.close();
        }
        System.out.println("Merge Two Sorted Arrays: 100 test cases generated.");
    }
}
