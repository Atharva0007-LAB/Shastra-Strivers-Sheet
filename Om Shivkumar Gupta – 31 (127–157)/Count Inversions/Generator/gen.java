import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) {
            baseDir = "../TestCases";
        }
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs();
        outDir.mkdirs();

        Random rand = new Random(131);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            long[] arr;

            if (tc == 0) {
                arr = new long[]{5, 3, 2, 4, 1};
            } else if (tc == 1) {
                arr = new long[]{5};
            } else if (tc == 2) {
                arr = new long[]{1, 2, 3, 4, 5};
            } else if (tc == 3) {
                arr = new long[]{5, 4, 3, 2, 1};
            } else if (tc == 4) {
                arr = new long[]{2, 4, 1, 3, 5, 0, 9, 6};
            } else {
                // Scaled sizes: large inputs to ensure O(N^2) brute force fails
                if (tc < 25) n = 50 + rand.nextInt(100);
                else if (tc < 60) n = 1000 + rand.nextInt(2000);
                else n = 10000 + rand.nextInt(15000);

                arr = new long[n];
                if (tc % 4 == 0) {
                    // strictly reverse sorted -> maximum inversions
                    for (int i = 0; i < n; i++) arr[i] = n - i;
                } else if (tc % 4 == 1) {
                    // sorted -> 0 inversions
                    for (int i = 0; i < n; i++) arr[i] = i * 2;
                } else {
                    for (int i = 0; i < n; i++) arr[i] = rand.nextLong() % 1000000;
                }
            }

            n = arr.length;

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) {
                inPw.print(arr[i] + (i == n - 1 ? "" : " "));
            }
            inPw.println();
            inPw.close();

            long[] copy = arr.clone();
            long[] temp = new long[n];
            long ans = mergeSort(copy, temp, 0, n - 1);

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Count Inversions: 100 test cases generated.");
    }

    private static long mergeSort(long[] arr, long[] temp, int left, int right) {
        long count = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;
            count += mergeSort(arr, temp, left, mid);
            count += mergeSort(arr, temp, mid + 1, right);
            count += merge(arr, temp, left, mid, right);
        }
        return count;
    }

    private static long merge(long[] arr, long[] temp, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;
        long invCount = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                invCount += (mid - i + 1);
            }
        }
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];
        for (i = left; i <= right; i++) arr[i] = temp[i];
        return invCount;
    }
}
