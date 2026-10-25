// Union of Two Sorted Arrays - Generator/gen.java
import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        // Run from inside the Generator folder or from problem root
        String baseDir = "Testcases";
        if (new File("../Testcases").exists() || new File("../Problem.md").exists()) {
            baseDir = "../Testcases";
        }

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<int[][]> cases = buildCases(); // each case is {a, b}
        if (cases.size() != 100) {
            throw new IllegalStateException("Expected 100 testcases but built " + cases.size());
        }

        for (int i = 0; i < cases.size(); i++) {
            String fileName = String.format("%02d", i);
            int[] a = cases.get(i)[0];
            int[] b = cases.get(i)[1];

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(inputDir, "input" + fileName + ".txt"))));
            inWriter.println(a.length + " " + b.length);
            inWriter.println(join(a));
            inWriter.println(join(b));
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

    // Reference answer: TreeSet automatically sorts and removes duplicates (mathematical union)
    static String solve(int[] a, int[] b) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int x : a) set.add(x);
        for (int x : b) set.add(x);
        StringBuilder sb = new StringBuilder();
        for (int x : set) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(x);
        }
        return sb.toString();
    }

    // Sorted array of n random values in [lo, hi]
    static int[] sortedRandom(int n, int lo, int hi, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = lo + rand.nextInt(hi - lo + 1);
        Arrays.sort(arr);
        return arr;
    }

    static int[] filled(int n, int value) {
        int[] arr = new int[n];
        Arrays.fill(arr, value);
        return arr;
    }

    static int[] sequence(int n, int start, int step) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = start + i * step;
        return arr;
    }

    static void add(List<int[][]> list, int[] a, int[] b) {
        list.add(new int[][]{a, b});
    }

    // 100 testcases: 0-2 samples, then edge cases, patterns, random small / medium / large, last 7 at max size
    static List<int[][]> buildCases() {
        Random rand = new Random(2026);
        List<int[][]> list = new ArrayList<>();

        // Examples from the problem statement / GFG
        add(list, new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 6, 7});
        add(list, new int[]{2, 2}, new int[]{1, 2, 3, 4});
        add(list, new int[]{1, 1, 1}, new int[]{2, 2});

        // Tiny arrays and value boundaries
        add(list, new int[]{1}, new int[]{1});
        add(list, new int[]{1}, new int[]{2});
        add(list, new int[]{-1000000000}, new int[]{1000000000});
        add(list, new int[]{-1000000000, 0, 1000000000}, new int[]{-500000000, 0, 500000000});
        add(list, new int[]{1, 1, 1, 1}, new int[]{1, 1});
        add(list, new int[]{5}, new int[]{1, 2, 3, 4, 5});
        add(list, new int[]{1, 2, 3}, new int[]{4, 5, 6});
        add(list, new int[]{4, 5, 6}, new int[]{1, 2, 3});
        add(list, new int[]{0, 0, 0}, new int[]{0, 0});
        add(list, new int[]{-20, -10, -5}, new int[]{-15, -10, 0, 5});

        // Structured patterns
        int[] seq = sequence(50, 1, 1);
        int[] odds = sequence(50, 1, 2);
        int[] evens = sequence(50, 2, 2);

        add(list, seq, seq.clone());                                           // identical arrays
        add(list, new int[]{3, 3, 10, 10, 20, 49}, seq);                       // a is subset of b
        add(list, seq, new int[]{3, 3, 10, 10, 20, 49});                       // b is subset of a
        add(list, odds, evens);                                                // interleaved, disjoint
        add(list, filled(40, 9), filled(60, 9));                               // all same value in both
        add(list, filled(30, -5), filled(40, 5));                              // constant different values
        add(list, new int[]{1, 1, 5, 6, 7}, new int[]{1, 2, 3, 4});            // common only at start
        add(list, new int[]{1, 2, 8}, new int[]{3, 4, 5, 8, 8});              // common only at end
        add(list, new int[]{1, 10, 10, 20}, new int[]{5, 10, 15});            // common only in middle
        add(list, new int[]{-100, -50, 0, 50, 100}, new int[]{-75, -50, 0, 25, 100}); // mixed positives and negatives

        // Random small (values -15..20, many duplicates and overlaps)
        while (list.size() < 60) {
            int n = 1 + rand.nextInt(15);
            int m = 1 + rand.nextInt(15);
            add(list, sortedRandom(n, -15, 20, rand), sortedRandom(m, -15, 20, rand));
        }

        // Random medium (moderate range with overlaps)
        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int m = 100 + rand.nextInt(901);
            int lo = -500 + rand.nextInt(500);
            int hi = lo + 200 + rand.nextInt(800);
            add(list, sortedRandom(n, lo, hi, rand), sortedRandom(m, lo, hi, rand));
        }

        // Random large
        while (list.size() < 93) {
            int n = 5000 + rand.nextInt(25001);
            int m = 5000 + rand.nextInt(25001);
            int hi = 10000 + rand.nextInt(300000);
            add(list, sortedRandom(n, 1, hi, rand), sortedRandom(m, 1, hi, rand));
        }

        // Max size (n = m = 50,000 / 100,000)
        int n = 50000;
        int[] bigOdds = sequence(n, 1, 2);
        int[] bigEvens = sequence(n, 2, 2);

        add(list, sortedRandom(n, 1, 100000, rand), sortedRandom(n, 1, 100000, rand));         // many overlaps
        add(list, filled(n, 42), filled(n, 42));                                               // all equal
        add(list, bigOdds, bigEvens);                                                          // interleaved disjoint
        add(list, sequence(n, 1, 1), sequence(n, 1, 1));                                       // identical full range
        add(list, filled(n, -100), filled(n, 100));                                            // two giant blocks
        add(list, sortedRandom(n, -1000000000, 1000000000, rand), sortedRandom(n, -1000000000, 1000000000, rand)); // extreme values
        add(list, sequence(n, 1, 1), bigEvens);                                                // b is evens subset of a

        return list;
    }
}
