// Reverse Pairs - Generator/gen.java
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
        int[] arr = nums.clone();
        int[] temp = new int[arr.length];
        return mergeSort(arr, temp, 0, arr.length - 1);
    }

    static long mergeSort(int[] nums, int[] temp, int left, int right) {
        if (left >= right) return 0;
        int mid = left + (right - left) / 2;
        long count = mergeSort(nums, temp, left, mid) + mergeSort(nums, temp, mid + 1, right);
        int j = mid + 1;
        for (int i = left; i <= mid; i++) {
            while (j <= right && (long) nums[i] > 2L * nums[j]) j++;
            count += (j - (mid + 1));
        }
        int i = left, k = left;
        j = mid + 1;
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) temp[k++] = nums[i++];
            else temp[k++] = nums[j++];
        }
        while (i <= mid) temp[k++] = nums[i++];
        while (j <= right) temp[k++] = nums[j++];
        for (i = left; i <= right; i++) nums[i] = temp[i];
        return count;
    }

    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Samples
        list.add(new int[]{1, 3, 2, 3, 1});
        list.add(new int[]{2, 4, 3, 5, 1});

        // Edge cases
        list.add(new int[]{1});
        list.add(new int[]{2, 1});
        list.add(new int[]{1, 2});
        list.add(new int[]{2147483647, 1073741823});
        list.add(new int[]{-2147483648, 2147483647});
        list.add(new int[]{0, 0, 0});
        list.add(new int[]{5, 4, 3, 2, 1});
        list.add(new int[]{1, 2, 3, 4, 5});

        while (list.size() < 60) {
            int n = 1 + rand.nextInt(25);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(51) - 25;
            list.add(arr);
        }

        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(2000000001) - 1000000000;
            list.add(arr);
        }

        while (list.size() < 93) {
            int n = 5000 + rand.nextInt(20001);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(2000000001) - 1000000000;
            list.add(arr);
        }

        int n = 50000;
        int[] asc = new int[n];
        for (int i = 0; i < n; i++) asc[i] = i;
        list.add(asc);

        int[] desc = new int[n];
        for (int i = 0; i < n; i++) desc[i] = n - i;
        list.add(desc);

        int[] allSame = new int[n];
        Arrays.fill(allSame, 1000);
        list.add(allSame);

        for (int k = 0; k < 4; k++) {
            int[] randMax = new int[n];
            for (int i = 0; i < n; i++) randMax[i] = rand.nextInt(2000000001) - 1000000000;
            list.add(randMax);
        }

        return list;
    }
}
