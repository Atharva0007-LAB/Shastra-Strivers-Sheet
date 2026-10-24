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

    // Reference answer: distinct values via streams (different from the two-pointer solution)
    static String solve(int[] arr) {
        int[] unique = Arrays.stream(arr).distinct().toArray();
        return unique.length + "\n" + join(unique);
    }

    // Sorted array of n random values in [lo, hi]
    static int[] sortedRandom(int n, int lo, int hi, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = lo + rand.nextInt(hi - lo + 1);
        Arrays.sort(arr);
        return arr;
    }

    // TC0: samples from the problem statement
    static int[][] generateTC0() {
        return new int[][]{
            {1, 1, 2},
            {0, 0, 1, 1, 1, 2, 2, 3, 3, 4}
        };
    }

    // TC1: tiny arrays and value boundaries (-100 and 100)
    static int[][] generateTC1() {
        return new int[][]{
            {7},
            {-100},
            {100},
            {1, 1},
            {1, 2},
            {-100, 100},
            {0, 0, 0},
            {-100, -100, 100, 100},
            {-1, 0, 1}
        };
    }

    // TC2: structured patterns (all same, all distinct, each value twice, negatives repeated)
    static int[][] generateTC2() {
        int[] allSame = new int[1000];
        Arrays.fill(allSame, -5);

        int[] allDistinct = new int[201];
        for (int i = 0; i < 201; i++) allDistinct[i] = -100 + i;

        int[] eachTwice = new int[402];
        for (int i = 0; i < 201; i++) {
            eachTwice[2 * i] = -100 + i;
            eachTwice[2 * i + 1] = -100 + i;
        }

        int[] negativeRepeated = new int[300];
        for (int i = 0; i < 100; i++) {
            negativeRepeated[3 * i] = -100 + i;
            negativeRepeated[3 * i + 1] = -100 + i;
            negativeRepeated[3 * i + 2] = -100 + i;
        }

        return new int[][]{allSame, allDistinct, eachTwice, negativeRepeated};
    }

    // TC3: random small cases over the full value range
    static int[][] generateTC3() {
        Random rand = new Random(42);
        int[][] cases = new int[50][];
        for (int i = 0; i < 50; i++) {
            int n = 1 + rand.nextInt(30);
            cases[i] = sortedRandom(n, -100, 100, rand);
        }
        return cases;
    }

    // TC4: max size (n = 3 * 10^4), values in [-100, 100]
    static int[][] generateTC4() {
        Random rand = new Random(999);
        return new int[][]{sortedRandom(30000, -100, 100, rand)};
    }

    // TC5: t = 1000 cases, small value range so duplicates are common
    static int[][] generateTC5() {
        Random rand = new Random(1234);
        int[][] cases = new int[1000][];
        for (int i = 0; i < 1000; i++) {
            int n = 1 + rand.nextInt(200);
            cases[i] = sortedRandom(n, -3, 3, rand);
        }
        return cases;
    }

    // TC6: three max-size arrays: all 0, spread evenly over [-100, 100], only -100 and 100
    static int[][] generateTC6() {
        int n = 30000;
        int[] allSame = new int[n];

        int[] spread = new int[n];
        for (int i = 0; i < n; i++) spread[i] = -100 + (int) ((long) i * 201 / n);

        int[] twoValues = new int[n];
        for (int i = 0; i < n; i++) twoValues[i] = i < n / 2 ? -100 : 100;

        return new int[][]{allSame, spread, twoValues};
    }

    // TC7: 40 medium cases over the full range
    static int[][] generateTC7() {
        Random rand = new Random(2024);
        int[][] cases = new int[40][];
        for (int i = 0; i < 40; i++) {
            int n = 1 + rand.nextInt(2000);
            cases[i] = sortedRandom(n, -100, 100, rand);
        }
        return cases;
    }
}
