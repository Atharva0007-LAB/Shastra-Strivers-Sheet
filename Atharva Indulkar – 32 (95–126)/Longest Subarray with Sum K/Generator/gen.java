// Longest Subarray with Sum K - Generator/gen.java
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

        List<Case> cases = buildCases();
        if (cases.size() != 100) {
            throw new IllegalStateException("Expected 100 testcases but built " + cases.size());
        }

        for (int i = 0; i < cases.size(); i++) {
            String fileName = String.format("%02d", i);
            Case c = cases.get(i);

            PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(inputDir, "input" + fileName + ".txt"))));
            inWriter.println(c.arr.length + " " + c.k);
            inWriter.println(join(c.arr));
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, "output" + fileName + ".txt"))));
            outWriter.println(solve(c.arr, c.k));
            outWriter.close();
        }
        System.out.println("Testcases generated successfully!");
    }

    static class Case {
        int[] arr;
        long k;
        Case(int[] arr, long k) { this.arr = arr; this.k = k; }
    }

    static String join(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < arr.length; j++) {
            sb.append(arr[j]);
            if (j != arr.length - 1) sb.append(" ");
        }
        return sb.toString();
    }

    static int solve(int[] nums, long k) {
        Map<Long, Integer> map = new HashMap<>();
        map.put(0L, -1);
        long prefixSum = 0;
        int maxLen = 0;
        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            if (map.containsKey(prefixSum - k)) {
                maxLen = Math.max(maxLen, i - map.get(prefixSum - k));
            }
            if (!map.containsKey(prefixSum)) {
                map.put(prefixSum, i);
            }
        }
        return maxLen;
    }

    static List<Case> buildCases() {
        Random rand = new Random(2026);
        List<Case> list = new ArrayList<>();

        // Samples
        list.add(new Case(new int[]{10, 5, 2, 7, 1, 9}, 15));
        list.add(new Case(new int[]{-1, 2, 3}, 5));
        list.add(new Case(new int[]{-1, 1, 1}, 1));

        // Edge cases
        list.add(new Case(new int[]{1}, 1));
        list.add(new Case(new int[]{1}, 2));
        list.add(new Case(new int[]{0}, 0));
        list.add(new Case(new int[]{0, 0, 0}, 0));
        list.add(new Case(new int[]{-5, 5, -5, 5}, 0));
        list.add(new Case(new int[]{1000000000, -1000000000}, 0));
        list.add(new Case(new int[]{1, 2, 3, 4, 5}, 100));

        while (list.size() < 60) {
            int n = 1 + rand.nextInt(25);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(41) - 20;
            long k = rand.nextInt(31) - 15;
            list.add(new Case(arr, k));
        }

        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(2001) - 1000;
            long k = rand.nextInt(501) - 250;
            list.add(new Case(arr, k));
        }

        while (list.size() < 93) {
            int n = 5000 + rand.nextInt(20001);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(2000000001) - 1000000000;
            long k = rand.nextInt(1001) - 500;
            list.add(new Case(arr, k));
        }

        // Max cases (n = 50,000)
        int n = 50000;
        int[] all0 = new int[n];
        list.add(new Case(all0, 0));

        int[] all1 = new int[n];
        Arrays.fill(all1, 1);
        list.add(new Case(all1, 1000));

        int[] alt = new int[n];
        for (int i = 0; i < n; i++) alt[i] = (i % 2 == 0) ? 1 : -1;
        list.add(new Case(alt, 0));

        for (int k = 0; k < 4; k++) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(21) - 10;
            list.add(new Case(arr, 5));
        }

        return list;
    }
}
