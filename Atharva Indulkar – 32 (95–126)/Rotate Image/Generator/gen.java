// Rotate Image - Generator/gen.java
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
            inWriter.println(mat.length);
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
        int n = matrix.length;
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[j][n - 1 - i] = matrix[i][j];
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(res[i][j]);
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
        list.add(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        list.add(new int[][]{{5, 1, 9, 11}, {2, 4, 8, 10}, {13, 3, 6, 7}, {15, 14, 12, 16}});

        // Tiny & boundaries
        list.add(new int[][]{{1}});
        list.add(new int[][]{{-1000}});
        list.add(new int[][]{{1, 2}, {3, 4}});
        list.add(new int[][]{{0, 0}, {0, 0}});
        list.add(new int[][]{{-1, -2}, {-3, -4}});
        list.add(new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}); // identity matrix

        while (list.size() < 60) {
            int n = 1 + rand.nextInt(10);
            int[][] mat = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) mat[i][j] = rand.nextInt(201) - 100;
            }
            list.add(mat);
        }

        while (list.size() < 85) {
            int n = 10 + rand.nextInt(31);
            int[][] mat = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) mat[i][j] = rand.nextInt(1001) - 500;
            }
            list.add(mat);
        }

        while (list.size() < 93) {
            int n = 50 + rand.nextInt(51);
            int[][] mat = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) mat[i][j] = rand.nextInt(2001) - 1000;
            }
            list.add(mat);
        }

        // Max size (n = 200)
        int n = 200;
        int[][] allSame = new int[n][n];
        for (int i = 0; i < n; i++) Arrays.fill(allSame[i], 42);
        list.add(allSame);

        int[][] identity = new int[n][n];
        for (int i = 0; i < n; i++) identity[i][i] = 1;
        list.add(identity);

        int[][] sym = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) sym[i][j] = i + j;
        }
        list.add(sym);

        for (int k = 0; k < 4; k++) {
            int[][] randMax = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) randMax[i][j] = rand.nextInt(2001) - 1000;
            }
            list.add(randMax);
        }

        return list;
    }
}
