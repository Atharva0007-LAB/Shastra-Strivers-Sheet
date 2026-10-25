// Next Permutation - Generator/gen.java
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

    static String solve(int[] nums) {
        int[] arr = nums.clone();
        int n = arr.length;
        int i = n - 2;
        while (i >= 0 && arr[i] >= arr[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = n - 1;
            while (arr[j] <= arr[i]) {
                j--;
            }
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        int l = i + 1, r = n - 1;
        while (l < r) {
            int tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
            l++;
            r--;
        }
        return join(arr);
    }

    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Problem samples
        list.add(new int[]{1, 2, 3});
        list.add(new int[]{3, 2, 1});
        list.add(new int[]{1, 1, 5});

        // Tiny arrays & boundaries
        list.add(new int[]{1});
        list.add(new int[]{100});
        list.add(new int[]{0});
        list.add(new int[]{1, 2});
        list.add(new int[]{2, 1});
        list.add(new int[]{5, 5});
        list.add(new int[]{1, 3, 2});
        list.add(new int[]{2, 1, 3});
        list.add(new int[]{2, 3, 1});
        list.add(new int[]{3, 1, 2});
        list.add(new int[]{5, 5, 5, 5});
        list.add(new int[]{0, 0, 0});
        list.add(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1});
        list.add(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        list.add(new int[]{1, 5, 4, 3, 2}); // pivot at index 0
        list.add(new int[]{1, 2, 3, 4, 6, 5}); // pivot at n - 2
        list.add(new int[]{2, 3, 1, 3, 3}); // duplicates
        list.add(new int[]{4, 2, 0, 2, 3, 2, 0});

        // Small cases (up to 60)
        while (list.size() < 60) {
            int n = 2 + rand.nextInt(19);
            int[] arr = new int[n];
            int mode = rand.nextInt(4);
            if (mode == 0) {
                // Strictly increasing
                for (int i = 0; i < n; i++) arr[i] = i * 2;
            } else if (mode == 1) {
                // Strictly decreasing
                for (int i = 0; i < n; i++) arr[i] = (n - i) * 2;
            } else if (mode == 2) {
                // Many duplicate values
                for (int i = 0; i < n; i++) arr[i] = rand.nextInt(4);
            } else {
                for (int i = 0; i < n; i++) arr[i] = rand.nextInt(101);
            }
            list.add(arr);
        }

        // Medium cases (up to 85)
        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(101);
            list.add(arr);
        }

        // Large cases (up to 93)
        while (list.size() < 93) {
            int n = 5000 + rand.nextInt(25001);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(101);
            list.add(arr);
        }

        // Max cases (n = 50000)
        int n = 50000;
        int[] asc = new int[n];
        for (int i = 0; i < n; i++) asc[i] = i % 101;
        list.add(asc);

        int[] desc = new int[n];
        for (int i = 0; i < n; i++) desc[i] = (n - i) % 101;
        list.add(desc);

        int[] allSame = new int[n];
        Arrays.fill(allSame, 50);
        list.add(allSame);

        int[] pivotNearEnd = new int[n];
        for (int i = 0; i < n - 2; i++) pivotNearEnd[i] = 10;
        pivotNearEnd[n - 2] = 20;
        pivotNearEnd[n - 1] = 15;
        list.add(pivotNearEnd);

        int[] pivotAtStart = new int[n];
        pivotAtStart[0] = 5;
        for (int i = 1; i < n; i++) pivotAtStart[i] = 50 - (i % 40);
        list.add(pivotAtStart);

        int[] randomLarge1 = new int[n];
        for (int i = 0; i < n; i++) randomLarge1[i] = rand.nextInt(101);
        list.add(randomLarge1);

        int[] randomLarge2 = new int[n];
        for (int i = 0; i < n; i++) randomLarge2[i] = rand.nextInt(101);
        list.add(randomLarge2);

        return list;
    }
}
