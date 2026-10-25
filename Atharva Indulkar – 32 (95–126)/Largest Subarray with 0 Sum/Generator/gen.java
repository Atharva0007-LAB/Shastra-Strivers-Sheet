// Largest Subarray with 0 Sum - Generator/gen.java
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

    static int solve(int[] nums) {
        Map<Long, Integer> map = new HashMap<>();
        map.put(0L, -1);
        long prefixSum = 0;
        int maxLen = 0;
        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i];
            if (map.containsKey(prefixSum)) {
                maxLen = Math.max(maxLen, i - map.get(prefixSum));
            } else {
                map.put(prefixSum, i);
            }
        }
        return maxLen;
    }

    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Samples
        list.add(new int[]{15, -2, 2, -8, 1, 7, 10, 23});
        list.add(new int[]{2, 10, 4, 8, 6});
        list.add(new int[]{1, 0, -1});

        // Edge cases
        list.add(new int[]{0});
        list.add(new int[]{5});
        list.add(new int[]{-5});
        list.add(new int[]{0, 0, 0});
        list.add(new int[]{1, -1});
        list.add(new int[]{-1, 1});
        list.add(new int[]{1000000000, -1000000000});
        list.add(new int[]{1, 2, 3, 4, 5});

        while (list.size() < 60) {
            int n = 1 + rand.nextInt(25);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(41) - 20;
            list.add(arr);
        }

        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(2001) - 1000;
            list.add(arr);
        }

        while (list.size() < 93) {
            int n = 5000 + rand.nextInt(20001);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(2000000001) - 1000000000;
            list.add(arr);
        }

        // Max cases (n = 50,000)
        int n = 50000;
        int[] all0 = new int[n];
        list.add(all0);

        int[] allPos = new int[n];
        for (int i = 0; i < n; i++) allPos[i] = i + 1;
        list.add(allPos);

        int[] alt = new int[n];
        for (int i = 0; i < n; i++) alt[i] = (i % 2 == 0) ? 1 : -1;
        list.add(alt);

        for (int k = 0; k < 4; k++) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(21) - 10;
            list.add(arr);
        }

        return list;
    }
}
