// Missing Number - Generator/gen.java
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

        List<int[]> cases = buildCases();
        if (cases.size() != 100) {
            throw new IllegalStateException("Expected 100 testcases but built " + cases.size());
        }

        for (int i = 0; i < cases.size(); i++) {
            String fileName = String.format("%02d", i);
            int[] arr = cases.get(i);

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(inputDir, "input" + fileName + ".txt"))));
            inWriter.println(arr.length);
            inWriter.println(join(arr));
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, "output" + fileName + ".txt"))));
            outWriter.println(solve(arr));
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

    // 100 testcases: 0-2 samples, then edge cases, patterns, random small / medium / large, last 7 at max size
    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Examples from the problem statement
        list.add(new int[]{3, 0, 1});
        list.add(new int[]{0, 1});
        list.add(new int[]{9, 6, 4, 2, 3, 5, 7, 0, 1});

        // n = 1 and n = 2, every possible missing value
        list.add(new int[]{0});
        list.add(new int[]{1});
        list.add(new int[]{1, 0});
        list.add(new int[]{0, 2});
        list.add(new int[]{1, 2});
        list.add(new int[]{2, 0});
        list.add(new int[]{2, 1});

        // n = 10: missing 0, missing n, missing a middle value, sorted and reverse sorted
        for (int missing : new int[]{0, 10, 5}) {
            list.add(sortedWithout(10, missing));
            list.add(reversed(sortedWithout(10, missing)));
        }

        // Random small
        while (list.size() < 60) {
            int n = 1 + rand.nextInt(30);
            list.add(shuffled(n, rand.nextInt(n + 1), rand));
        }

        // Random medium
        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            list.add(shuffled(n, rand.nextInt(n + 1), rand));
        }

        // Random large
        while (list.size() < 93) {
            int n = 2000 + rand.nextInt(8000);
            list.add(shuffled(n, rand.nextInt(n + 1), rand));
        }

        // Max size (n = 10^4)
        int n = 10000;
        list.add(sortedWithout(n, 0));
        list.add(sortedWithout(n, n));
        list.add(sortedWithout(n, n / 2));
        list.add(reversed(sortedWithout(n, n / 2)));
        list.add(shuffled(n, rand.nextInt(n + 1), rand));
        list.add(shuffled(n, rand.nextInt(n + 1), rand));
        list.add(shuffled(n, rand.nextInt(n + 1), rand));

        return list;
    }
}