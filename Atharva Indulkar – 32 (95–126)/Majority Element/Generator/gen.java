// Majority Element - Generator/gen.java
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

    // Reference answer: the majority element is always the middle element after sorting
    static int solve(int[] arr) {
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        return sorted[sorted.length / 2];
    }

    static int randValue(Random rand) {
        return rand.nextInt(2000000001) - 1000000000; // [-10^9, 10^9]
    }

    static int otherValue(Random rand, boolean small) {
        return small ? rand.nextInt(5) - 2 : randValue(rand);
    }

    static int minCount(int n) {
        return n / 2 + 1;
    }

    // Builds an array of size n where 'majority' appears c times (c >= n/2 + 1).
    // type: 0 = shuffled, 1 = majority first, 2 = majority last, 3 = alternating
    // small: if true, the other values come from -2..2
    static int[] build(int n, int majority, int c, int type, boolean small, Random rand) {
        int[] arr = new int[n];
        if (type == 0 || type == 1 || type == 2) {
            int[] others = new int[n - c];
            for (int i = 0; i < others.length; i++) others[i] = otherValue(rand, small);
            int idx = 0;
            if (type == 2) {
                for (int x : others) arr[idx++] = x;
                for (int i = 0; i < c; i++) arr[idx++] = majority;
            } else {
                for (int i = 0; i < c; i++) arr[idx++] = majority;
                for (int x : others) arr[idx++] = x;
            }
            if (type == 0) {
                for (int i = n - 1; i > 0; i--) {
                    int j = rand.nextInt(i + 1);
                    int tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        } else {
            int mi = 0, oi = 0;
            for (int i = 0; i < n; i++) {
                boolean takeMaj = (i % 2 == 0 && mi < c) || oi >= n - c;
                if (takeMaj) {
                    arr[i] = majority;
                    mi++;
                } else {
                    arr[i] = otherValue(rand, small);
                    oi++;
                }
            }
        }
        return arr;
    }

    // Random case of size n with a random majority count, arrangement and value style
    static int[] randomCase(int n, Random rand) {
        int c = minCount(n) + rand.nextInt(n - n / 2);
        return build(n, randValue(rand), c, rand.nextInt(4), rand.nextBoolean(), rand);
    }

    // 100 testcases: 0-1 samples, then edge cases, patterns, random small / medium / large, last 7 at max size
    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Examples from the problem statement
        list.add(new int[]{3, 2, 3});
        list.add(new int[]{2, 2, 1, 1, 1, 2, 2});

        // Tiny arrays and value boundaries
        list.add(new int[]{5});
        list.add(new int[]{-1000000000});
        list.add(new int[]{1000000000});
        list.add(new int[]{1, 1});
        list.add(new int[]{7, 7, 7});
        list.add(new int[]{1, 2, 1});
        list.add(new int[]{2, 1, 1});
        list.add(new int[]{1, 1, 2});
        list.add(new int[]{-1000000000, 1000000000, 1000000000});
        list.add(new int[]{-1000000000, -1000000000, 1000000000});

        // n = 11 and n = 10, majority appears the minimum number of times, in every arrangement
        int n11 = 11;
        list.add(build(n11, 4, minCount(n11), 0, true, rand));
        list.add(build(n11, 4, minCount(n11), 1, false, rand));
        list.add(build(n11, -1000000000, minCount(n11), 2, false, rand));
        list.add(build(n11, 1000000000, minCount(n11), 3, false, rand));
        list.add(build(10, 0, minCount(10), 3, true, rand));
        list.add(build(10, -2, minCount(10), 2, true, rand));

        // Random small
        while (list.size() < 60) {
            list.add(randomCase(1 + rand.nextInt(20), rand));
        }

        // Random medium
        while (list.size() < 85) {
            list.add(randomCase(100 + rand.nextInt(901), rand));
        }

        // Random large
        while (list.size() < 93) {
            list.add(randomCase(2000 + rand.nextInt(48001), rand));
        }

        // Max size (n = 5 * 10^4)
        int n = 50000;
        int[] allSame = new int[n];
        Arrays.fill(allSame, -123456789);
        list.add(build(n, randValue(rand), minCount(n), 0, false, rand)); // shuffled, bare majority
        list.add(build(n, 42, minCount(n), 1, false, rand));              // majority first
        list.add(build(n, -42, minCount(n), 2, false, rand));             // majority last
        list.add(build(n, 1000000000, minCount(n), 3, false, rand));      // alternating
        list.add(allSame);                                                // all the same
        list.add(build(n, randValue(rand), n - 1, 0, false, rand));       // only one other value
        list.add(build(n, 0, minCount(n), 0, true, rand));                // tiny value range

        return list;
    }
}