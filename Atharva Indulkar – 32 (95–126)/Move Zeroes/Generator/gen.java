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

    // Reference answer: copy non-zeros in order into a new array (zeros are already at the end)
    static String solve(int[] arr) {
        int[] res = new int[arr.length];
        int idx = 0;
        for (int x : arr) {
            if (x != 0) res[idx++] = x;
        }
        return join(res);
    }

    static int nonZero(Random rand) {
        int v = rand.nextInt();
        while (v == 0) v = rand.nextInt();
        return v;
    }

    static int[] randomWithZeros(int n, int zeroPercent, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(100) < zeroPercent ? 0 : nonZero(rand);
        }
        return arr;
    }

    // TC0: samples from the problem statement
    static int[][] generateTC0() {
        return new int[][]{
            {0, 1, 0, 3, 12},
            {0}
        };
    }

    // TC1: tiny arrays and int boundaries
    static int[][] generateTC1() {
        return new int[][]{
            {5},
            {0},
            {0, 0},
            {1, 2},
            {0, 1},
            {1, 0},
            {Integer.MIN_VALUE, 0, Integer.MAX_VALUE},
            {0, 0, 1},
            {1, 0, 0}
        };
    }

    // TC2: structured patterns (all zeros, no zeros, zeros at start/end, alternating, single zero/non-zero)
    static int[][] generateTC2() {
        int[] allZeros = new int[100];

        int[] noZeros = new int[100];
        for (int i = 0; i < 100; i++) noZeros[i] = i + 1;

        int[] zerosFirst = new int[100];
        for (int i = 50; i < 100; i++) zerosFirst[i] = i - 49;

        int[] zerosLast = new int[100];
        for (int i = 0; i < 50; i++) zerosLast[i] = i + 1;

        int[] alternating = new int[100];
        for (int i = 0; i < 100; i++) alternating[i] = (i % 2 == 0) ? 0 : i;

        int[] singleNonZero = new int[99];
        singleNonZero[49] = 7;

        int[] singleZero = new int[99];
        for (int i = 0; i < 99; i++) singleZero[i] = i + 1;
        singleZero[49] = 0;

        return new int[][]{allZeros, noZeros, zerosFirst, zerosLast, alternating, singleNonZero, singleZero};
    }

    // TC3: random small cases with varying zero density
    static int[][] generateTC3() {
        Random rand = new Random(42);
        int[][] cases = new int[50][];
        for (int i = 0; i < 50; i++) {
            int n = 1 + rand.nextInt(20);
            cases[i] = randomWithZeros(n, rand.nextInt(101), rand);
        }
        return cases;
    }

    // TC4: max size (n = 10^4), about half zeros
    static int[][] generateTC4() {
        Random rand = new Random(999);
        return new int[][]{randomWithZeros(10000, 50, rand)};
    }

    // TC5: t = 1000 cases, medium n (sum of n stays well under 2 * 10^5)
    static int[][] generateTC5() {
        Random rand = new Random(1234);
        int[][] cases = new int[1000][];
        for (int i = 0; i < 1000; i++) {
            int n = 1 + rand.nextInt(200);
            cases[i] = randomWithZeros(n, rand.nextInt(101), rand);
        }
        return cases;
    }

    // TC6: four max-size arrays: all zeros, no zeros, zeros first half, zeros second half
    static int[][] generateTC6() {
        int n = 10000;
        int[] allZeros = new int[n];

        int[] noZeros = new int[n];
        for (int i = 0; i < n; i++) noZeros[i] = i + 1;

        int[] zerosFirst = new int[n];
        for (int i = n / 2; i < n; i++) zerosFirst[i] = i - n / 2 + 1;

        int[] zerosLast = new int[n];
        for (int i = 0; i < n / 2; i++) zerosLast[i] = i + 1;

        return new int[][]{allZeros, noZeros, zerosFirst, zerosLast};
    }

    // TC7: heavy duplicates (values 0, 1, 2), 20 cases, sum of n <= 2 * 10^5
    static int[][] generateTC7() {
        Random rand = new Random(2024);
        int[][] cases = new int[20][];
        for (int i = 0; i < 20; i++) {
            int n = 1 + rand.nextInt(10000);
            int[] arr = new int[n];
            for (int j = 0; j < n; j++) arr[j] = rand.nextInt(3);
            cases[i] = arr;
        }
        return cases;
    }
}
