import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(157);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int m, n, target;
            int[][] matrix;

            if (tc == 0) {
                m = 3; n = 4; target = 3;
                matrix = new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
            } else if (tc == 1) {
                m = 3; n = 4; target = 13;
                matrix = new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
            } else if (tc == 2) {
                m = 1; n = 1; target = 1;
                matrix = new int[][]{{1}};
            } else if (tc == 3) {
                m = 1; n = 2; target = 3;
                matrix = new int[][]{{1, 3}};
            } else if (tc == 4) {
                m = 2; n = 2; target = 0;
                matrix = new int[][]{{1, 2}, {3, 4}};
            } else {
                if (tc < 25) { m = 5 + rand.nextInt(10); n = 5 + rand.nextInt(10); }
                else if (tc < 65) { m = 30 + rand.nextInt(30); n = 30 + rand.nextInt(30); }
                else { m = 100 + rand.nextInt(50); n = 100 + rand.nextInt(50); }

                matrix = new int[m][n];
                int cur = 1;
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        cur += 1 + rand.nextInt(4);
                        matrix[i][j] = cur;
                    }
                }
                if (rand.nextBoolean()) {
                    target = matrix[rand.nextInt(m)][rand.nextInt(n)];
                } else {
                    target = cur + 5;
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(m + " " + n + " " + target);
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    inPw.print(matrix[i][j] + (j == n - 1 ? "" : " "));
                }
                inPw.println();
            }
            inPw.close();

            boolean found = false;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == target) { found = true; break; }
                }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(found);
            outPw.close();
        }
        System.out.println("Problem 157 generated 100 test cases.");
    }
}
