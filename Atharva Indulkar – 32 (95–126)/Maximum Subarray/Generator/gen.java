// Maximum Subarray - Generator/gen.java
import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "Testcases";
        if (new File("../Testcases").exists() || new File("../Problem.md").exists()) {
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

    static long solve(int[] nums) {
        long maxSum = Long.MIN_VALUE;
        long curSum = 0;
        for (int x : nums) {
            curSum += x;
            if (curSum > maxSum) maxSum = curSum;
            if (curSum < 0) curSum = 0;
        }
        return maxSum;
    }

    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Problem samples
        list.add(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        list.add(new int[]{1});
        list.add(new int[]{5, 4, -1, 7, 8});

        // Tiny arrays & boundary tests
        list.add(new int[]{-1});
        list.add(new int[]{0});
        list.add(new int[]{-10000});
        list.add(new int[]{10000});
        list.add(new int[]{-10, -5, -20, -2, -100}); // all negatives
        list.add(new int[]{10, 20, 30, 40, 50});    // all positives
        list.add(new int[]{0, 0, 0, 0, 0});         // all zeros
        list.add(new int[]{-1, 0, -2});             // zeros with negatives
        list.add(new int[]{100, -200, 100});
        list.add(new int[]{-2, -1});
        list.add(new int[]{1, -1, 1, -1, 1});
        list.add(new int[]{10, -5, 20, -10, 30});

        // Small cases (up to 60)
        while (list.size() < 60) {
            int n = 1 + rand.nextInt(25);
            int[] arr = new int[n];
            int mode = rand.nextInt(4);
            if (mode == 0) {
                // All negative
                for (int i = 0; i < n; i++) arr[i] = -(1 + rand.nextInt(500));
            } else if (mode == 1) {
                // All positive
                for (int i = 0; i < n; i++) arr[i] = 1 + rand.nextInt(500);
            } else {
                for (int i = 0; i < n; i++) arr[i] = rand.nextInt(2001) - 1000;
            }
            list.add(arr);
        }

        // Medium cases (up to 85)
        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(20001) - 10000;
            list.add(arr);
        }

        // Large cases (up to 93)
        while (list.size() < 93) {
            int n = 5000 + rand.nextInt(25001);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(20001) - 10000;
            list.add(arr);
        }

        // Max cases (n = 50,000)
        int n = 50000;
        int[] allPos = new int[n];
        Arrays.fill(allPos, 10000);
        list.add(allPos);

        int[] allNeg = new int[n];
        Arrays.fill(allNeg, -10000);
        list.add(allNeg);

        int[] alt = new int[n];
        for (int i = 0; i < n; i++) alt[i] = (i % 2 == 0) ? 10000 : -5000;
        list.add(alt);

        int[] bigCenter = new int[n];
        Arrays.fill(bigCenter, -100);
        bigCenter[n / 2] = 10000;
        list.add(bigCenter);

        int[] randomMax1 = new int[n];
        for (int i = 0; i < n; i++) randomMax1[i] = rand.nextInt(20001) - 10000;
        list.add(randomMax1);

        int[] randomMax2 = new int[n];
        for (int i = 0; i < n; i++) randomMax2[i] = rand.nextInt(20001) - 10000;
        list.add(randomMax2);

        int[] randomMax3 = new int[n];
        for (int i = 0; i < n; i++) randomMax3[i] = rand.nextInt(20001) - 10000;
        list.add(randomMax3);

        return list;
    }
}
