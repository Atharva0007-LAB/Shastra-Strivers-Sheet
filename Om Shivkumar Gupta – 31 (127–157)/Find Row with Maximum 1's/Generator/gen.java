import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(156);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, m;
            int[][] mat;

            if (tc == 0) {
                n = 3; m = 3;
                mat = new int[][]{{0, 1, 1}, {0, 0, 1}, {0, 0, 0}};
            } else if (tc == 1) {
                n = 2; m = 2;
                mat = new int[][]{{0, 0}, {0, 0}};
            } else if (tc == 2) {
                n = 1; m = 1;
                mat = new int[][]{{1}};
            } else if (tc == 3) {
                n = 3; m = 4;
                mat = new int[][]{{0, 0, 1, 1}, {0, 1, 1, 1}, {0, 0, 0, 1}};
            } else if (tc == 4) {
                n = 4; m = 2;
                mat = new int[][]{{0, 1}, {1, 1}, {0, 0}, {1, 1}};
            } else {
                if (tc < 25) { n = 5 + rand.nextInt(10); m = 5 + rand.nextInt(10); }
                else if (tc < 65) { n = 30 + rand.nextInt(30); m = 30 + rand.nextInt(30); }
                else { n = 100 + rand.nextInt(100); m = 100 + rand.nextInt(100); }

                mat = new int[n][m];
                for (int i = 0; i < n; i++) {
                    int ones = rand.nextInt(m + 1);
                    int zeros = m - ones;
                    for (int j = 0; j < zeros; j++) mat[i][j] = 0;
                    for (int j = zeros; j < m; j++) mat[i][j] = 1;
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + m);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    inPw.print(mat[i][j] + (j == m - 1 ? "" : " "));
                }
                inPw.println();
            }
            inPw.close();

            int maxOnes = 0, rowIdx = -1;
            for (int i = 0; i < n; i++) {
                int ones = 0;
                for (int j = 0; j < m; j++) if (mat[i][j] == 1) ones++;
                if (ones > maxOnes) { maxOnes = ones; rowIdx = i; }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(rowIdx);
            outPw.close();
        }
        System.out.println("Problem 156 generated 100 test cases.");
    }
}
