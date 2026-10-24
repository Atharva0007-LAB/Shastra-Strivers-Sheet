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

        List<Object[][]> testCases = new ArrayList<>();
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

            Object[][] cases = testCases.get(i);

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inWriter.println(cases.length);
            for (Object[] c : cases) {
                int k = (int) c[0];
                int[] arr = (int[]) c[1];
                inWriter.println(arr.length + " " + k);
                inWriter.println(join(arr));
            }
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (Object[] c : cases) {
                outWriter.println(solve((int) c[0], (int[]) c[1]));
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

    // Reference answer uses index mapping (different from the reversal in Solution)
    static String solve(int k, int[] arr) {
        int n = arr.length;
        k %= n;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            res[(i + k) % n] = arr[i];
        }
        return join(res);
    }

    static int[] randomArray(int n, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(); // full int range
        return arr;
    }

    static int[] sequence(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i + 1;
        return arr;
    }

    // TC0: sample from the problem statement
    static Object[][] generateTC0() {
        return new Object[][]{
            {3, new int[]{1, 2, 3, 4, 5, 6, 7}},
            {2, new int[]{-1, -100, 3, 99}}
        };
    }

    // TC1: smallest sizes, k = 0, k = max, n = 1 and n = 2
    static Object[][] generateTC1() {
        return new Object[][]{
            {0, new int[]{5}},
            {1, new int[]{5}},
            {100000, new int[]{5}},
            {0, new int[]{1, 2}},
            {1, new int[]{1, 2}},
            {2, new int[]{1, 2}},
            {3, new int[]{1, 2}},
            {100000, new int[]{1, 2}},
            {99999, new int[]{1, 2}},
            {0, new int[]{1, 2, 3}},
            {10, new int[]{1, 2}}
        };
    }

    // TC2: k vs n boundaries: k < n, k = n - 1, k = n, k = n + 1, k = 2n, k = 2n + 1
    static Object[][] generateTC2() {
        int[] a = {1, 2, 3, 4, 5};
        return new Object[][]{
            {1, a},
            {4, a},
            {5, a},
            {6, a},
            {10, a},
            {11, a},
            {0, a},
            {2, new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 0}},
            {1, new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 0}},
            {3, new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE}},
            {7, new int[]{-1, -2, -3, -4, -5, -6}}
        };
    }

    // TC3: random small cases, random k (including k > n)
    static Object[][] generateTC3() {
        Random rand = new Random(42);
        Object[][] cases = new Object[50][];
        for (int i = 0; i < 50; i++) {
            int n = 1 + rand.nextInt(20);
            int k = rand.nextInt(100001);
            cases[i] = new Object[]{k, randomArray(n, rand)};
        }
        return cases;
    }

    // TC4: max size single case, k = n - 1
    static Object[][] generateTC4() {
        Random rand = new Random(999);
        return new Object[][]{{99999, randomArray(100000, rand)}};
    }

    // TC5: t = 1000 cases, medium n (sum of n stays well under 2*10^5)
    static Object[][] generateTC5() {
        Random rand = new Random(1234);
        Object[][] cases = new Object[1000][];
        for (int i = 0; i < 1000; i++) {
            int n = 1 + rand.nextInt(200);
            int k = rand.nextInt(100001);
            cases[i] = new Object[]{k, randomArray(n, rand)};
        }
        return cases;
    }

    // TC6: two large cases (sum n = 199999): k % n = 0 and k % n = 1
    static Object[][] generateTC6() {
        Random rand = new Random(777);
        return new Object[][]{
            {100000, randomArray(100000, rand)},
            {100000, randomArray(99999, rand)}
        };
    }

    // TC7: duplicates, all same, sorted, reverse sorted, small value range
    static Object[][] generateTC7() {
        Random rand = new Random(2024);
        List<Object[]> list = new ArrayList<>();

        int[] same = new int[500];
        Arrays.fill(same, 7);
        list.add(new Object[]{123, same});

        int[] asc = sequence(1000);
        list.add(new Object[]{1, asc});
        list.add(new Object[]{999, asc});

        int[] desc = new int[1000];
        for (int i = 0; i < 1000; i++) desc[i] = 1000 - i;
        list.add(new Object[]{500, desc});

        for (int i = 0; i < 90; i++) {
            int n = 1 + rand.nextInt(1000);
            int[] arr = new int[n];
            for (int j = 0; j < n; j++) arr[j] = rand.nextInt(3) - 1; // -1, 0, 1
            list.add(new Object[]{rand.nextInt(100001), arr});
        }
        return list.toArray(new Object[0][]);
    }
}
