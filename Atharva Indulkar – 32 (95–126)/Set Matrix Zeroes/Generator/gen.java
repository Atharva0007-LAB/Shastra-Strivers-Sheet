// Set Matrix Zeroes - Generator/gen.java
import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "Testcases";
        if (new File("../Testcases").exists() || new File("../Problem.md").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<int[][]> cases = buildCases();
        if (cases.size() != 100) {
            throw new IllegalStateException("Expected 100 testcases but built " + cases.size());
        }

        for (int i = 0; i < cases.size(); i++) {
            String fileName = String.format("%02d", i);
            int[][] mat = cases.get(i);

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(inputDir, "input" + fileName + ".txt"))));
            inWriter.println(mat.length + " " + mat[0].length);
            for (int r = 0; r < mat.length; r++) {
                inWriter.println(join(mat[r]));
            }
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, "output" + fileName + ".txt"))));
            outWriter.print(solve(mat));
            outWriter.close();
        }
        System.out.println("Testcases generated successfully!");
    }

    static String join(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < arr.length; j++) {
            sb.append(arr[j]);
            if (j != arr.length - 1) sb.append(" ");
        }
        return sb.toString();
    }

    static String solve(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] copy = new int[m][n];
        boolean[] zeroRow = new boolean[m];
        boolean[] zeroCol = new boolean[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = matrix[i][j];
                if (matrix[i][j] == 0) {
                    zeroRow[i] = true;
                    zeroCol[j] = true;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (zeroRow[i] || zeroCol[j]) {
                    copy[i][j] = 0;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(copy[i][j]);
                if (j != n - 1) sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    static List<int[][]> buildCases() {
        Random rand = new Random(2026);
        List<int[][]> list = new ArrayList<>();

        // Samples
        list.add(new int[][]{{1, 1, 1}, {1, 0, 1}, {1, 1, 1}});
        list.add(new int[][]{{0, 1, 2, 0}, {3, 4, 5, 2}, {1, 3, 1, 5}});

        // Edge cases
        list.add(new int[][]{{0}});
        list.add(new int[][]{{5}});
        list.add(new int[][]{{0, 1}});
        list.add(new int[][]{{1, 0}});
        list.add(new int[][]{{1, 1}});
        list.add(new int[][]{{0}, {1}});
        list.add(new int[][]{{1}, {0}});
        list.add(new int[][]{{0, 0}, {0, 0}});
        list.add(new int[][]{{1, 2}, {3, 4}});
        list.add(new int[][]{{1, 0, 3}, {4, 5, 6}, {7, 8, 9}}); // 0 in first row
        list.add(new int[][]{{1, 2, 3}, {0, 5, 6}, {7, 8, 9}}); // 0 in first col
        list.add(new int[][]{{0, 2, 3}, {4, 5, 6}, {7, 8, 9}}); // 0 at (0, 0)

        while (list.size() < 60) {
            int m = 1 + rand.nextInt(8);
            int n = 1 + rand.nextInt(8);
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    mat[i][j] = (rand.nextInt(4) == 0) ? 0 : rand.nextInt(20) - 10;
                }
            }
            list.add(mat);
        }

        while (list.size() < 85) {
            int m = 10 + rand.nextInt(20);
            int n = 10 + rand.nextInt(20);
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    mat[i][j] = (rand.nextInt(10) == 0) ? 0 : rand.nextInt(100);
                }
            }
            list.add(mat);
        }

        while (list.size() < 93) {
            int m = 40 + rand.nextInt(40);
            int n = 40 + rand.nextInt(40);
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    mat[i][j] = (rand.nextInt(20) == 0) ? 0 : rand.nextInt(1000);
                }
            }
            list.add(mat);
        }

        // Max size (m = 200, n = 200)
        int m = 200, n = 200;
        int[][] all0 = new int[m][n];
        list.add(all0);

        int[][] allPos = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(allPos[i], 1);
        list.add(allPos);

        int[][] one0 = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(one0[i], 5);
        one0[m / 2][n / 2] = 0;
        list.add(one0);

        int[][] diag0 = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(diag0[i], 7);
            diag0[i][i] = 0;
        }
        list.add(diag0);

        int[][] firstRow0 = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(firstRow0[i], 9);
        firstRow0[0][5] = 0;
        list.add(firstRow0);

        int[][] firstCol0 = new int[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(firstCol0[i], 9);
        firstCol0[5][0] = 0;
        list.add(firstCol0);

        int[][] randMax = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                randMax[i][j] = (rand.nextInt(50) == 0) ? 0 : rand.nextInt(10000);
            }
        }
        list.add(randMax);

        return list;
    }
}
