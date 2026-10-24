import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        // Run from inside the Generator folder: writes into ../Testcases
        String baseDir = "Testcases";
        if (new File("../Testcases").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<int[][]> testCases = new ArrayList<>();
        testCases.add(generateTC0());
        testCases.add(generateTC1());
        testCases.add(generateTC2());
        testCases.add(generateTC3());
        testCases.add(generateTC4());
        testCases.add(generateTC5());
        testCases.add(generateTC6());
        testCases.add(generateTC7());

        for (int i = 0; i < testCases.size(); i++) {
            String fileName = String.format("%02d", i);
            File inFile = new File(inputDir, "input" + fileName + ".txt");
            File outFile = new File(outputDir, "output" + fileName + ".txt");

            int[][] cases = testCases.get(i);

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inWriter.println(cases.length);
            for (int[] arr : cases) {
                inWriter.println(arr.length);
                inWriter.println(join(arr));
            }
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (int[] arr : cases) {
                outWriter.println(solve(arr));
            }
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

    // Reference answer: mark which values are present (different from the sum formula in Solution)
    static int solve(int[] arr) {
        int n = arr.length;
        boolean[] seen = new boolean[n + 1];
        for (int x : arr) seen[x] = true;
        for (int v = 0; v <= n; v++) {
            if (!seen[v]) return v;
        }
        return -1; // never happens for valid input
    }

    // Values 0..n without 'missing', in increasing order
    static int[] sortedWithout(int n, int missing) {
        int[] arr = new int[n];
        int idx = 0;
        for (int v = 0; v <= n; v++) {
            if (v != missing) arr[idx++] = v;
        }
        return arr;
    }

    static int[] shuffled(int n, int missing, Random rand) {
        int[] arr = sortedWithout(n, missing);
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    static int[] reversed(int[] arr) {
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) res[i] = arr[arr.length - 1 - i];
        return res;
    }

    // TC0: the three examples from the problem statement
    static int[][] generateTC0() {
        return new int[][]{
            {3, 0, 1},
            {0, 1},
            {9, 6, 4, 2, 3, 5, 7, 0, 1}
        };
    }

    // TC1: n = 1 and n = 2, every possible missing value
    static int[][] generateTC1() {
        return new int[][]{
            {0},
            {1},
            {1, 0},
            {0, 2},
            {1, 2},
            {2, 0},
            {2, 1}
        };
    }

    // TC2: n = 10, missing 0, missing n, missing a middle value, sorted and reverse sorted
    static int[][] generateTC2() {
        return new int[][]{
            sortedWithout(10, 0),
            sortedWithout(10, 10),
            sortedWithout(10, 5),
            reversed(sortedWithout(10, 0)),
            reversed(sortedWithout(10, 10)),
            reversed(sortedWithout(10, 5))
        };
    }

    // TC3: random small cases
    static int[][] generateTC3() {
        Random rand = new Random(42);
        int[][] cases = new int[50][];
        for (int i = 0; i < 50; i++) {
            int n = 1 + rand.nextInt(30);
            cases[i] = shuffled(n, rand.nextInt(n + 1), rand);
        }
        return cases;
    }

    // TC4: max size (n = 10^4), shuffled
    static int[][] generateTC4() {
        Random rand = new Random(999);
        return new int[][]{shuffled(10000, rand.nextInt(10001), rand)};
    }

    // TC5: t = 1000 cases, medium n
    static int[][] generateTC5() {
        Random rand = new Random(1234);
        int[][] cases = new int[1000][];
        for (int i = 0; i < 1000; i++) {
            int n = 1 + rand.nextInt(200);
            cases[i] = shuffled(n, rand.nextInt(n + 1), rand);
        }
        return cases;
    }

    // TC6: max-size sorted arrays: missing 0, missing n, missing the middle, plus reverse sorted
    static int[][] generateTC6() {
        int n = 10000;
        return new int[][]{
            sortedWithout(n, 0),
            sortedWithout(n, n),
            sortedWithout(n, n / 2),
            reversed(sortedWithout(n, n / 2))
        };
    }

    // TC7: 20 larger shuffled cases, sum of n <= 2 * 10^5
    static int[][] generateTC7() {
        Random rand = new Random(2024);
        int[][] cases = new int[20][];
        for (int i = 0; i < 20; i++) {
            int n = 100 + rand.nextInt(9901);
            cases[i] = shuffled(n, rand.nextInt(n + 1), rand);
        }
        return cases;
    }
}
