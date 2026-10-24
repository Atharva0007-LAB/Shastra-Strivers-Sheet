import java.io.*;
import java.util.*;

public class gen {

    private static final String BASE_DIR = "Testcases";

    public static void main(String[] args) throws IOException {
        String baseDir = BASE_DIR;

        if (!new File(baseDir).exists()
                && new File("../Testcases").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "input");
        File outputDir = new File(baseDir, "output");

        inputDir.mkdirs();
        outputDir.mkdirs();

        List<int[][]> testCases = new ArrayList<>();

        // Test case 1: One zero in the middle.
        testCases.add(new int[][] {
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
        });

        // Test case 2: Zeroes in the first row.
        testCases.add(new int[][] {
            {0, 1, 2, 0},
            {3, 4, 5, 2},
            {1, 3, 1, 5}
        });

        // Test case 3: Matrix without zeroes.
        testCases.add(new int[][] {
            {1, 2},
            {3, 4}
        });

        // Test case 4: Single element.
        testCases.add(new int[][] {
            {0}
        });

        // Test case 5: Zero in an inner column.
        testCases.add(new int[][] {
            {1, 2, 3, 4},
            {5, 6, 0, 8},
            {9, 10, 11, 12}
        });

        // Test case 6: Multiple zeroes.
        testCases.add(new int[][] {
            {1, 2, 0},
            {4, 5, 6},
            {7, 0, 9}
        });

        // Test case 7: Negative values.
        testCases.add(new int[][] {
            {1, -2, 3},
            {-4, 5, -6},
            {7, 8, 9}
        });

        // Test case 8: All zeroes.
        testCases.add(new int[][] {
            {0, 0, 0},
            {0, 0, 0}
        });

        // Test case 9: Rectangular matrix.
        testCases.add(new int[][] {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10}
        });

        // Test case 10: Maximum-size matrix.
        testCases.add(generateLargeTestCase());

        for (int i = 0; i < testCases.size(); i++) {
            String suffix = String.format("%02d", i);

            File inputFile = new File(
                inputDir, "input" + suffix + ".txt"
            );

            File outputFile = new File(
                outputDir, "output" + suffix + ".txt"
            );

            int[][] matrix = testCases.get(i);

            writeInput(inputFile, matrix);

            int[][] result = copyMatrix(matrix);
            setZeroes(result);

            writeOutput(outputFile, result);
        }

        System.out.println(
            "Generated " + testCases.size()
            + " test cases successfully."
        );
    }

    private static int[][] generateLargeTestCase() {
        int rows = 200;
        int cols = 200;

        int[][] matrix = new int[rows][cols];
        Random random = new Random(20261024L);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(2001) - 1000;
            }
        }

        matrix[0][cols - 1] = 0;
        matrix[rows - 1][0] = 0;
        matrix[rows / 2][cols / 2] = 0;
        matrix[rows - 2][cols - 3] = 0;

        return matrix;
    }

    private static int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][];

        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }

        return copy;
    }

    private static void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        boolean firstRowZero = false;
        boolean firstColZero = false;

        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                firstRowZero = true;
                break;
            }
        }

        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstColZero = true;
                break;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        if (firstRowZero) {
            Arrays.fill(matrix[0], 0);
        }

        if (firstColZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    private static void writeInput(
            File file, int[][] matrix) throws IOException {

        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(file)))) {

            writer.println(
                matrix.length + " " + matrix[0].length
            );

            for (int[] row : matrix) {
                for (int j = 0; j < row.length; j++) {
                    if (j > 0) writer.print(' ');
                    writer.print(row[j]);
                }
                writer.println();
            }
        }
    }

    private static void writeOutput(
            File file, int[][] matrix) throws IOException {

        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(new FileWriter(file)))) {

            for (int[] row : matrix) {
                for (int j = 0; j < row.length; j++) {
                    if (j > 0) writer.print(' ');
                    writer.print(row[j]);
                }
                writer.println();
            }
        }
    }
}