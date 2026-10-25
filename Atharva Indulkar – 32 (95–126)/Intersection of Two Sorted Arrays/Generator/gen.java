// Intersection of Two Sorted Arrays - Generator/gen.java
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

    // Reference answer: set intersection with TreeSets (different from the two-pointer solution)
    static String solve(int[] a, int[] b) {
        TreeSet<Integer> setA = new TreeSet<>();
        for (int x : a) setA.add(x);
        TreeSet<Integer> common = new TreeSet<>();
        for (int x : b) {
            if (setA.contains(x)) common.add(x);
        }
        if (common.isEmpty()) return "-1";
        StringBuilder sb = new StringBuilder();
        for (int x : common) {
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

    static int[] sequence(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i + 1;
        return arr;
    }

    static void add(List<int[][]> list, int[] a, int[] b) {
        list.add(new int[][]{a, b});
    }

    // 100 testcases: 0-1 samples, then edge cases, patterns, random small / medium / large, last 7 at max size
    static List<int[][]> buildCases() {
        Random rand = new Random(2026);
        List<int[][]> list = new ArrayList<>();

        // Examples from the problem statement
        add(list, new int[]{1, 1, 2, 2, 2, 4}, new int[]{2, 2, 4, 4});
        add(list, new int[]{4, 4, 4, 4}, new int[]{3, 3, 3, 3});

        // Tiny arrays and value boundaries (1 and 10^9)
        add(list, new int[]{1}, new int[]{1});
        add(list, new int[]{1}, new int[]{2});
        add(list, new int[]{1000000000}, new int[]{1000000000});
        add(list, new int[]{1, 1}, new int[]{1});
        add(list, new int[]{5}, new int[]{1, 2, 3, 4, 5});
        add(list, new int[]{1, 2, 3}, new int[]{4, 5, 6});
        add(list, new int[]{1, 1000000000}, new int[]{1, 1000000000});
        add(list, new int[]{1, 1000000000}, new int[]{2, 1000000000});

        // Structured patterns
        int[] seq = sequence(50);
        int[] odds = new int[50];
        int[] evens = new int[50];
        for (int i = 0; i < 50; i++) {
            odds[i] = 2 * i + 1;
            evens[i] = 2 * i + 2;
        }
        add(list, seq, seq.clone());                                   // identical arrays
        add(list, new int[]{3, 3, 10, 10, 10, 20, 49}, seq);           // a is a subset of b
        add(list, seq, new int[]{3, 3, 10, 10, 10, 20, 49});           // b is a subset of a
        add(list, odds, evens);                                        // interleaved, nothing common
        add(list, filled(40, 9), filled(60, 9));                       // all same value in both
        add(list, new int[]{1, 2, 2, 2, 3, 3, 8}, new int[]{3, 3, 3, 4, 5, 6, 7, 8, 8}); // common at the end
        add(list, new int[]{1, 1, 5, 6, 7}, new int[]{1, 2, 3, 4});    // common only at the start

        // Random small (values 1..10, many duplicates and overlaps)
        while (list.size() < 60) {
            int n = 1 + rand.nextInt(15);
            int m = 1 + rand.nextInt(15);
            add(list, sortedRandom(n, 1, 10, rand), sortedRandom(m, 1, 10, rand));
        }

        // Random medium
        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int m = 100 + rand.nextInt(901);
            int hi = 200 + rand.nextInt(800);
            add(list, sortedRandom(n, 1, hi, rand), sortedRandom(m, 1, hi, rand));
        }

        // Random large
        while (list.size() < 93) {
            int n = 5000 + rand.nextInt(95001);
            int m = 5000 + rand.nextInt(95001);
            int hi = 10000 + rand.nextInt(300000);
            add(list, sortedRandom(n, 1, hi, rand), sortedRandom(m, 1, hi, rand));
        }

        // Max size (n = m = 10^5)
        int n = 100000;
        int[] bigOdds = new int[n];
        int[] bigEvens = new int[n];
        for (int i = 0; i < n; i++) {
            bigOdds[i] = 2 * i + 1;
            bigEvens[i] = 2 * i + 2;
        }
        add(list, sortedRandom(n, 1, 200000, rand), sortedRandom(n, 1, 200000, rand));   // many common values
        add(list, filled(n, 5), filled(n, 5));                                            // all equal
        add(list, bigOdds, bigEvens);                                                     // nothing common
        add(list, sequence(n), sequence(n));                                              // identical
        add(list, filled(n, 5), filled(n, 6));                                            // nothing common, all equal
        add(list, sortedRandom(n, 1, 1000000000, rand), sortedRandom(n, 1, 1000000000, rand)); // sparse, large values
        add(list, sequence(n), bigEvens);                                                 // b has the even numbers up to 2n

        return list;
    }
}