// Merge Sorted Array - Generator/gen.java
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
            int[] a = cases.get(i)[0];
            int[] b = cases.get(i)[1];

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(inputDir, "input" + fileName + ".txt"))));
            inWriter.println(a.length + " " + b.length);
            if (a.length > 0) inWriter.println(join(a));
            if (b.length > 0) inWriter.println(join(b));
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, "output" + fileName + ".txt"))));
            outWriter.println(solve(a, b));
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

    static String solve(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];
        System.arraycopy(a, 0, res, 0, a.length);
        System.arraycopy(b, 0, res, a.length, b.length);
        Arrays.sort(res);
        return join(res);
    }

    static int[] sortedRandom(int n, int lo, int hi, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = lo + rand.nextInt(hi - lo + 1);
        Arrays.sort(arr);
        return arr;
    }

    static List<int[][]> buildCases() {
        Random rand = new Random(2026);
        List<int[][]> list = new ArrayList<>();

        // Samples
        list.add(new int[][]{{1, 2, 3}, {2, 5, 6}});
        list.add(new int[][]{{1}, {}});
        list.add(new int[][]{{}, {1}});

        // Edge cases
        list.add(new int[][]{{1}, {1}});
        list.add(new int[][]{{1}, {2}});
        list.add(new int[][]{{2}, {1}});
        list.add(new int[][]{{-1000000000}, {1000000000}});
        list.add(new int[][]{{0}, {0}});
        list.add(new int[][]{{1, 3, 5}, {2, 4, 6}});
        list.add(new int[][]{{1, 1, 1}, {1, 1, 1}});
        list.add(new int[][]{{1, 2, 3}, {4, 5, 6}});
        list.add(new int[][]{{4, 5, 6}, {1, 2, 3}});

        while (list.size() < 60) {
            int m = rand.nextInt(15);
            int n = rand.nextInt(15);
            if (m == 0 && n == 0) m = 1;
            list.add(new int[][]{sortedRandom(m, -50, 50, rand), sortedRandom(n, -50, 50, rand)});
        }

        while (list.size() < 85) {
            int m = 100 + rand.nextInt(901);
            int n = 100 + rand.nextInt(901);
            list.add(new int[][]{sortedRandom(m, -10000, 10000, rand), sortedRandom(n, -10000, 10000, rand)});
        }

        while (list.size() < 93) {
            int m = 5000 + rand.nextInt(20001);
            int n = 5000 + rand.nextInt(20001);
            list.add(new int[][]{sortedRandom(m, -1000000000, 1000000000, rand), sortedRandom(n, -1000000000, 1000000000, rand)});
        }

        int m = 50000, n = 50000;
        int[] bigOdds = new int[m];
        int[] bigEvens = new int[n];
        for (int i = 0; i < m; i++) bigOdds[i] = 2 * i + 1;
        for (int i = 0; i < n; i++) bigEvens[i] = 2 * i + 2;
        list.add(new int[][]{bigOdds, bigEvens});

        int[] allSame1 = new int[m];
        int[] allSame2 = new int[n];
        Arrays.fill(allSame1, 10);
        Arrays.fill(allSame2, 10);
        list.add(new int[][]{allSame1, allSame2});

        list.add(new int[][]{bigOdds, new int[]{}});
        list.add(new int[][]{new int[]{}, bigEvens});

        for (int k = 0; k < 3; k++) {
            list.add(new int[][]{sortedRandom(m, -1000000000, 1000000000, rand), sortedRandom(n, -1000000000, 1000000000, rand)});
        }

        return list;
    }
}
