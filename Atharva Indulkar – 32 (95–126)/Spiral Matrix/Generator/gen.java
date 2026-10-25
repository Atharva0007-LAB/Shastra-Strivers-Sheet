// Spiral Matrix - Generator/gen.java
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
            outWriter.println(solve(mat));
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
        int top = 0, bottom = m - 1, left = 0, right = n - 1;
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        while (top <= bottom && left <= right) {
            for (int j = left; j <= right; j++) {
                if (!first) sb.append(" ");
                sb.append(matrix[top][j]);
                first = false;
            }
            top++;

            for (int i = top; i <= bottom; i++) {
                if (!first) sb.append(" ");
                sb.append(matrix[i][right]);
                first = false;
            }
            right--;

            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    if (!first) sb.append(" ");
                    sb.append(matrix[bottom][j]);
                    first = false;
                }
                bottom--;
            }

            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    if (!first) sb.append(" ");
                    sb.append(matrix[i][left]);
                    first = false;
                }
                left++;
            }
        }
        return sb.toString();
    }

    static List<int[][]> buildCases() {
        Random rand = new Random(2026);
        List<int[][]> list = new ArrayList<>();

        // Samples
        list.add(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        list.add(new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}});

        // Edge cases
        list.add(new int[][]{{1}});
        list.add(new int[][]{{1, 2, 3, 4, 5}}); // 1 row
        list.add(new int[][]{{1}, {2}, {3}, {4}, {5}}); // 1 col
        list.add(new int[][]{{1, 2}, {3, 4}});
        list.add(new int[][]{{1, 2, 3}, {4, 5, 6}});
        list.add(new int[][]{{1, 2}, {3, 4}, {5, 6}});

        while (list.size() < 60) {
            int m = 1 + rand.nextInt(10);
            int n = 1 + rand.nextInt(10);
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) mat[i][j] = rand.nextInt(201) - 100;
            }
            list.add(mat);
        }

        while (list.size() < 85) {
            int m = 10 + rand.nextInt(21);
            int n = 10 + rand.nextInt(21);
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) mat[i][j] = rand.nextInt(201) - 100;
            }
            list.add(mat);
        }

        while (list.size() < 93) {
            int m = 30 + rand.nextInt(41);
            int n = 30 + rand.nextInt(41);
            int[][] mat = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) mat[i][j] = rand.nextInt(201) - 100;
            }
            list.add(mat);
        }

        // Max cases (m, n up to 100)
        int m = 100, n = 100;
        int[][] seq = new int[m][n];
        int val = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) seq[i][j] = val++;
        }
        list.add(seq);

        int[][] all0 = new int[m][n];
        list.add(all0);

        int[][] oneRowMax = new int[1][100];
        for (int j = 0; j < 100; j++) oneRowMax[0][j] = j;
        list.add(oneRowMax);

        int[][] oneColMax = new int[100][1];
        for (int i = 0; i < 100; i++) oneColMax[i][0] = i;
        list.add(oneColMax);

        for (int k = 0; k < 3; k++) {
            int[][] randMax = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) randMax[i][j] = rand.nextInt(201) - 100;
            }
            list.add(randMax);
        }

        return list;
    }
}
