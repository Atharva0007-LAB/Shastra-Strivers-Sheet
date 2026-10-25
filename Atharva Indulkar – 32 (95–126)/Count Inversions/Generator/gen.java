// Count Inversions - Generator/gen.java
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
        long[] arr = new long[nums.length];
        for (int i = 0; i < nums.length; i++) arr[i] = nums[i];
        long[] temp = new long[nums.length];
        return mergeSortAndCount(arr, temp, 0, nums.length - 1);
    }

    static long mergeSortAndCount(long[] arr, long[] temp, int left, int right) {
        long count = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;
            count += mergeSortAndCount(arr, temp, left, mid);
            count += mergeSortAndCount(arr, temp, mid + 1, right);
            count += mergeAndCount(arr, temp, left, mid, right);
        }
        return count;
    }

    static long mergeAndCount(long[] arr, long[] temp, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;
        long count = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                count += (mid - i + 1);
            }
        }
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];
        for (i = left; i <= right; i++) arr[i] = temp[i];
        return count;
    }

    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Samples
        list.add(new int[]{2, 4, 1, 3, 5});
        list.add(new int[]{2, 3, 4, 5, 6});
        list.add(new int[]{10, 10, 10});

        // Edge cases
        list.add(new int[]{1});
        list.add(new int[]{1, 2});
        list.add(new int[]{2, 1});
        list.add(new int[]{1000000000, 1});
        list.add(new int[]{1, 1, 1, 1});
        list.add(new int[]{5, 4, 3, 2, 1});
        list.add(new int[]{1, 2, 3, 4, 5});

        while (list.size() < 60) {
            int n = 1 + rand.nextInt(25);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(50) + 1;
            list.add(arr);
        }

        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(10000) + 1;
            list.add(arr);
        }

        while (list.size() < 93) {
            int n = 5000 + rand.nextInt(20001);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(1000000000) + 1;
            list.add(arr);
        }

        // Max cases (n = 50,000)
        int n = 50000;
        int[] sorted = new int[n];
        for (int i = 0; i < n; i++) sorted[i] = i + 1;
        list.add(sorted);

        int[] rev = new int[n];
        for (int i = 0; i < n; i++) rev[i] = n - i;
        list.add(rev);

        int[] allSame = new int[n];
        Arrays.fill(allSame, 55555);
        list.add(allSame);

        for (int k = 0; k < 4; k++) {
            int[] randMax = new int[n];
            for (int i = 0; i < n; i++) randMax[i] = rand.nextInt(1000000000) + 1;
            list.add(randMax);
        }

        return list;
    }
}
