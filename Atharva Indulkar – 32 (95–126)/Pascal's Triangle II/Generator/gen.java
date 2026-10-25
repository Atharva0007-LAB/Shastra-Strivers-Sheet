// Pascal's Triangle II - Generator/gen.java
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
            outWriter.println(solve(n));
            outWriter.close();
        }
        System.out.println("Testcases generated successfully!");
    }

    static String solve(int rowIndex) {
        long val = 1;
        StringBuilder sb = new StringBuilder();
        sb.append(1);
        for (int i = 1; i <= rowIndex; i++) {
            val = val * (rowIndex - i + 1) / i;
            sb.append(" ").append(val);
        }
        return sb.toString();
    }

    static List<Integer> buildCases() {
        List<Integer> list = new ArrayList<>();
        // 0 to 33 all covered
        for (int i = 0; i <= 33; i++) list.add(i);
        for (int i = 0; i <= 33; i++) list.add(i);
        for (int i = 0; i <= 31; i++) list.add(i);
        return list;
    }
}
