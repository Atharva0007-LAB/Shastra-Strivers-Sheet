// Maximum Product Subarray - Generator/gen.java
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
        long maxProd = nums[0];
        long minProd = nums[0];
        long ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int x = nums[i];
            if (x < 0) {
                long tmp = maxProd;
                maxProd = minProd;
                minProd = tmp;
            }
            maxProd = Math.max((long) x, maxProd * x);
            minProd = Math.min((long) x, minProd * x);
            ans = Math.max(ans, maxProd);
        }
        return ans;
    }

    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Samples
        list.add(new int[]{2, 3, -2, 4});
        list.add(new int[]{-2, 0, -1});

        // Edge cases
        list.add(new int[]{0});
        list.add(new int[]{-2});
        list.add(new int[]{5});
        list.add(new int[]{-2, -3});
        list.add(new int[]{-2, -3, -4});
        list.add(new int[]{0, 2});
        list.add(new int[]{-2, 0});
        list.add(new int[]{-2, 0, 0, -3});
        list.add(new int[]{1, -1, 1, -1, 1});
        list.add(new int[]{-1, -2, -3, -4});

        while (list.size() < 60) {
            int n = 1 + rand.nextInt(20);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(7) - 3;
            list.add(arr);
        }

        while (list.size() < 85) {
            int n = 50 + rand.nextInt(101);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = (rand.nextInt(10) == 0) ? 0 : (rand.nextInt(5) - 2);
            list.add(arr);
        }

        while (list.size() < 93) {
            int n = 500 + rand.nextInt(501);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = (rand.nextInt(15) == 0) ? 0 : (rand.nextBoolean() ? 1 : -1);
            list.add(arr);
        }

        int n = 20000;
        int[] all1 = new int[n];
        Arrays.fill(all1, 1);
        list.add(all1);

        int[] allNeg1 = new int[n];
        Arrays.fill(allNeg1, -1);
        list.add(allNeg1);

        int[] zerosAnd1 = new int[n];
        for (int i = 0; i < n; i++) zerosAnd1[i] = (i % 2 == 0) ? 1 : 0;
        list.add(zerosAnd1);

        for (int k = 0; k < 4; k++) {
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                int r = rand.nextInt(20);
                arr[i] = (r == 0) ? 0 : (r < 10 ? 1 : -1);
            }
            list.add(arr);
        }

        return list;
    }
}
