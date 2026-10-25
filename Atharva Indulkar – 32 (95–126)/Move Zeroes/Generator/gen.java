// Move Zeroes - Generator/gen.java
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

    // 100 testcases: 0-1 samples, then edge cases, patterns, random small / medium / large, last 7 at max size
    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Samples from the problem statement
        list.add(new int[]{0, 1, 0, 3, 12});
        list.add(new int[]{0});

        // Tiny arrays and int limits
        list.add(new int[]{5});
        list.add(new int[]{0, 0});
        list.add(new int[]{1, 2});
        list.add(new int[]{0, 1});
        list.add(new int[]{1, 0});
        list.add(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE});
        list.add(new int[]{0, 0, 1});
        list.add(new int[]{1, 0, 0});
        list.add(new int[]{0, 0, 0, 0, 5});

        // Patterns: all zeros, no zeros, zeros first, zeros last, alternating, single non-zero, single zero
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

        list.add(new int[100]);
        list.add(noZeros);
        list.add(zerosFirst);
        list.add(zerosLast);
        list.add(alternating);
        list.add(singleNonZero);
        list.add(singleZero);

        // Random small
        while (list.size() < 60) {
            int n = 1 + rand.nextInt(20);
            list.add(randomWithZeros(n, rand.nextInt(101), rand));
        }

        // Random medium
        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            list.add(randomWithZeros(n, rand.nextInt(101), rand));
        }

        // Random large
        while (list.size() < 93) {
            int n = 2000 + rand.nextInt(8000);
            list.add(randomWithZeros(n, rand.nextInt(101), rand));
        }

        // Max size (n = 10^4)
        int n = 10000;
        int[] maxNoZeros = new int[n];
        for (int i = 0; i < n; i++) maxNoZeros[i] = i + 1;
        int[] maxZerosFirst = new int[n];
        for (int i = n / 2; i < n; i++) maxZerosFirst[i] = i - n / 2 + 1;
        int[] maxZerosLast = new int[n];
        for (int i = 0; i < n / 2; i++) maxZerosLast[i] = i + 1;

        list.add(new int[n]);
        list.add(maxNoZeros);
        list.add(maxZerosFirst);
        list.add(maxZerosLast);
        list.add(randomWithZeros(n, 50, rand));
        list.add(randomWithZeros(n, 10, rand));
        list.add(randomWithZeros(n, 90, rand));

        return list;
    }
}