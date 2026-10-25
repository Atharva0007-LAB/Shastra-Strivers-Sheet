// Rotate Array - Generator/gen.java
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

        List<Object[]> cases = buildCases();
        if (cases.size() != 100) {
            throw new IllegalStateException("Expected 100 testcases but built " + cases.size());
        }

        for (int i = 0; i < cases.size(); i++) {
            String fileName = String.format("%02d", i);
            int k = (int) cases.get(i)[0];
            int[] arr = (int[]) cases.get(i)[1];

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(inputDir, "input" + fileName + ".txt"))));
            inWriter.println(arr.length + " " + k);
            inWriter.println(join(arr));
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, "output" + fileName + ".txt"))));
            outWriter.println(solve(k, arr));
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

    static void add(List<Object[]> list, int k, int[] arr) {
        list.add(new Object[]{k, arr});
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

    static int[] reversed(int[] arr) {
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) res[i] = arr[arr.length - 1 - i];
        return res;
    }

    // 100 testcases: 0-1 samples, then edge cases, patterns, random small / medium / large, last 7 at max size
    static List<Object[]> buildCases() {
        Random rand = new Random(2026);
        List<Object[]> list = new ArrayList<>();

        // Samples from the problem statement
        add(list, 3, new int[]{1, 2, 3, 4, 5, 6, 7});
        add(list, 2, new int[]{-1, -100, 3, 99});

        // Tiny arrays, k = 0, k = max, k larger than n
        add(list, 0, new int[]{5});
        add(list, 1, new int[]{5});
        add(list, 100000, new int[]{5});
        add(list, 0, new int[]{1, 2});
        add(list, 1, new int[]{1, 2});
        add(list, 2, new int[]{1, 2});
        add(list, 3, new int[]{1, 2});
        add(list, 100000, new int[]{1, 2});
        add(list, 99999, new int[]{1, 2});
        add(list, 0, new int[]{1, 2, 3});
        add(list, 10, new int[]{1, 2});

        // k around n: k < n, k = n - 1, k = n, k = n + 1, k = 2n, k = 2n + 1, k = 0
        int[] five = {1, 2, 3, 4, 5};
        for (int k : new int[]{0, 1, 4, 5, 6, 10, 11}) add(list, k, five);

        // int limits and negatives
        add(list, 2, new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 0});
        add(list, 1, new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 0});
        add(list, 3, new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE});
        add(list, 7, new int[]{-1, -2, -3, -4, -5, -6});

        // Patterns: all same, sorted, reverse sorted
        int[] same = new int[500];
        Arrays.fill(same, 7);
        add(list, 123, same);
        int[] asc = sequence(1000);
        add(list, 1, asc);
        add(list, 999, asc);
        add(list, 500, reversed(asc));

        // Random small
        while (list.size() < 60) {
            int n = 1 + rand.nextInt(20);
            add(list, rand.nextInt(100001), randomArray(n, rand));
        }

        // Random medium
        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            add(list, rand.nextInt(100001), randomArray(n, rand));
        }

        // Random large
        while (list.size() < 93) {
            int n = 2000 + rand.nextInt(98001);
            add(list, rand.nextInt(100001), randomArray(n, rand));
        }

        // Max size (n = 10^5)
        int n = 100000;
        add(list, 0, randomArray(n, rand));
        add(list, 1, randomArray(n, rand));
        add(list, 99999, randomArray(n, rand));
        add(list, 100000, randomArray(n, rand));
        add(list, 50000, randomArray(n, rand));
        add(list, rand.nextInt(100001), randomArray(n, rand));
        add(list, 50001, sequence(n));

        return list;
    }
}