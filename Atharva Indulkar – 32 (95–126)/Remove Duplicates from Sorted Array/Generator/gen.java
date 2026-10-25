// Remove Duplicates from Sorted Array - Generator/gen.java
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

    // Random sorted array whose value range has a random width (1 to 201 distinct values possible)
    static int[] sortedRandomAnyRange(int n, Random rand) {
        int width = 1 + rand.nextInt(201);
        int lo = -100 + rand.nextInt(202 - width);
        return sortedRandom(n, lo, lo + width - 1, rand);
    }

    // 100 testcases: 0-1 samples, then edge cases, patterns, random small / medium / large, last 7 at max size
    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Samples from the problem statement
        list.add(new int[]{1, 1, 2});
        list.add(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4});

        // Tiny arrays and value boundaries (-100 and 100)
        list.add(new int[]{7});
        list.add(new int[]{-100});
        list.add(new int[]{100});
        list.add(new int[]{1, 1});
        list.add(new int[]{1, 2});
        list.add(new int[]{-100, 100});
        list.add(new int[]{0, 0, 0});
        list.add(new int[]{-100, -100, 100, 100});
        list.add(new int[]{-1, 0, 1});

        // Patterns: all same, all distinct, each value twice, negatives repeated
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

        list.add(allSame);
        list.add(allDistinct);
        list.add(eachTwice);
        list.add(negativeRepeated);

        // Random small
        while (list.size() < 60) {
            int n = 1 + rand.nextInt(30);
            list.add(sortedRandomAnyRange(n, rand));
        }

        // Random medium
        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            list.add(sortedRandomAnyRange(n, rand));
        }

        // Random large
        while (list.size() < 93) {
            int n = 5000 + rand.nextInt(25000);
            list.add(sortedRandomAnyRange(n, rand));
        }

        // Max size (n = 3 * 10^4)
        int n = 30000;
        int[] spread = new int[n];
        for (int i = 0; i < n; i++) spread[i] = -100 + (int) ((long) i * 201 / n);

        int[] twoValues = new int[n];
        for (int i = 0; i < n; i++) twoValues[i] = i < n / 2 ? -100 : 100;

        int[] mostlyOne = new int[n];
        for (int i = 0; i < 10; i++) mostlyOne[i] = -100 + rand.nextInt(201);
        Arrays.sort(mostlyOne);

        list.add(new int[n]);
        list.add(spread);
        list.add(twoValues);
        list.add(sortedRandom(n, -100, 100, rand));
        list.add(sortedRandom(n, -3, 3, rand));
        list.add(sortedRandom(n, -100, -90, rand));
        list.add(mostlyOne);

        return list;
    }
}