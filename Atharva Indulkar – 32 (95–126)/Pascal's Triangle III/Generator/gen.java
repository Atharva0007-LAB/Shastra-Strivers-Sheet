// Pascal's Triangle III - Generator/gen.java
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

        List<Integer> cases = buildCases();
        if (cases.size() != 100) {
            throw new IllegalStateException("Expected 100 testcases but built " + cases.size());
        }

        for (int i = 0; i < cases.size(); i++) {
            String fileName = String.format("%02d", i);
            int n = cases.get(i);

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(inputDir, "input" + fileName + ".txt"))));
            inWriter.println(n);
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, "output" + fileName + ".txt"))));
            outWriter.print(solve(n));
            outWriter.close();
        }
        System.out.println("Testcases generated successfully!");
    }

    static String solve(int n) {
        int[][] triangle = new int[n][];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            triangle[i] = new int[i + 1];
            triangle[i][0] = 1;
            triangle[i][i] = 1;
            for (int j = 1; j < i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
            for (int j = 0; j <= i; j++) {
                sb.append(triangle[i][j]);
                if (j != i) sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    static List<Integer> buildCases() {
        List<Integer> list = new ArrayList<>();
        // 1 to 30 all covered
        for (int i = 1; i <= 30; i++) list.add(i);
        // Repeat interesting values
        for (int i = 1; i <= 30; i++) list.add(i);
        for (int i = 1; i <= 30; i++) list.add(i);
        // Last 10 values
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(5);
        list.add(10);
        list.add(15);
        list.add(20);
        list.add(25);
        list.add(29);
        list.add(30);
        return list;
    }
}
