// Rearrange Array Elements by Sign - Generator/gen.java
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

    // Reference answer: split into positives and negatives, then interleave them
    static String solve(int[] arr) {
        int n = arr.length;
        int[] positives = new int[n / 2];
        int[] negatives = new int[n / 2];
        int p = 0, q = 0;
        for (int x : arr) {
            if (x > 0) positives[p++] = x;
            else negatives[q++] = x;
        }
        int[] res = new int[n];
        for (int k = 0; k < n / 2; k++) {
            res[2 * k] = positives[k];
            res[2 * k + 1] = negatives[k];
        }
        return join(res);
    }

    // Builds an array of even size n with n/2 positives and n/2 negatives.
    // mode: 0 = random positions, 1 = positives first, 2 = negatives first,
    //       3 = alternating starting positive, 4 = alternating starting negative
    // extremeMags: if true, magnitudes are only 1 or 100000 (many duplicates); otherwise 1..100000
    static int[] build(int n, int mode, boolean extremeMags, Random rand) {
        boolean[] isPos = new boolean[n];
        if (mode == 0) {
            for (int i = 0; i < n / 2; i++) isPos[i] = true;
            for (int i = n - 1; i > 0; i--) {
                int j = rand.nextInt(i + 1);
                boolean tmp = isPos[i];
                isPos[i] = isPos[j];
                isPos[j] = tmp;
            }
        } else if (mode == 1) {
            for (int i = 0; i < n / 2; i++) isPos[i] = true;
        } else if (mode == 2) {
            for (int i = n / 2; i < n; i++) isPos[i] = true;
        } else if (mode == 3) {
            for (int i = 0; i < n; i++) isPos[i] = (i % 2 == 0);
        } else {
            for (int i = 0; i < n; i++) isPos[i] = (i % 2 == 1);
        }
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            int mag = extremeMags ? (rand.nextBoolean() ? 1 : 100000) : 1 + rand.nextInt(100000);
            arr[i] = isPos[i] ? mag : -mag;
        }
        return arr;
    }

    // 100 testcases: 0-1 samples, then edge cases, patterns, random small / medium / large, last 7 at max size
    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Examples from the problem statement
        list.add(new int[]{3, 1, -2, -5, 2, -4});
        list.add(new int[]{-1, 1});

        // Smallest sizes and value boundaries
        list.add(new int[]{1, -1});
        list.add(new int[]{100000, -100000});
        list.add(new int[]{-100000, 100000});
        list.add(new int[]{1, 1, -1, -1});
        list.add(new int[]{-1, -1, 1, 1});
        list.add(new int[]{1, -1, 1, -1});
        list.add(new int[]{-1, 1, -1, 1});
        list.add(new int[]{5, -5, 5, -5});
        list.add(new int[]{-7, -3, 2, 9});

        // n = 20 in every arrangement
        for (int mode = 0; mode < 5; mode++) {
            list.add(build(20, mode, false, rand));
        }

        // Random small
        while (list.size() < 60) {
            int n = 2 * (1 + rand.nextInt(10));
            list.add(build(n, rand.nextInt(5), false, rand));
        }

        // Random medium
        while (list.size() < 85) {
            int n = 2 * (50 + rand.nextInt(451));
            list.add(build(n, rand.nextInt(5), rand.nextBoolean(), rand));
        }

        // Random large
        while (list.size() < 93) {
            int n = 2 * (1000 + rand.nextInt(49001));
            list.add(build(n, rand.nextInt(5), rand.nextBoolean(), rand));
        }

        // Max size (n = 2 * 10^5)
        int n = 200000;
        for (int mode = 0; mode < 5; mode++) {
            list.add(build(n, mode, false, rand));
        }
        list.add(build(n, 0, true, rand));
        list.add(build(n, 1, true, rand));

        return list;
    }
}