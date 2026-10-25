// 3Sum - Generator/gen.java
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
            outWriter.print(solve(arr));
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
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        boolean found = false;

        for (int i = 0; i < arr.length - 2; i++) {
            if (i > 0 && arr[i] == arr[i - 1]) continue;
            if (arr[i] > 0) break;

            int left = i + 1, right = arr.length - 1;
            while (left < right) {
                int sum = arr[i] + arr[left] + arr[right];
                if (sum == 0) {
                    found = true;
                    sb.append(arr[i]).append(" ").append(arr[left]).append(" ").append(arr[right]).append("\n");
                    while (left < right && arr[left] == arr[left + 1]) left++;
                    while (left < right && arr[right] == arr[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        if (!found) return "-1\n";
        return sb.toString();
    }

    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Samples
        list.add(new int[]{-1, 0, 1, 2, -1, -4});
        list.add(new int[]{0, 1, 1});
        list.add(new int[]{0, 0, 0});

        // Edge cases
        list.add(new int[]{0, 0, 0, 0});
        list.add(new int[]{1, 2, 3});
        list.add(new int[]{-3, -2, -1});
        list.add(new int[]{-2, 0, 1, 1, 2});
        list.add(new int[]{-100000, 50000, 50000});
        list.add(new int[]{-100000, 0, 100000});
        list.add(new int[]{-5, -5, 10, -5, 10});
        list.add(new int[]{1, -1, 2, -2, 3, -3});

        while (list.size() < 60) {
            int n = 3 + rand.nextInt(20);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(31) - 15;
            list.add(arr);
        }

        while (list.size() < 85) {
            int n = 50 + rand.nextInt(151);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(201) - 100;
            list.add(arr);
        }

        while (list.size() < 93) {
            int n = 300 + rand.nextInt(701);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(2001) - 1000;
            list.add(arr);
        }

        // Max size (n = 3000)
        int n = 3000;
        int[] all0 = new int[n];
        list.add(all0);

        int[] allPos = new int[n];
        for (int i = 0; i < n; i++) allPos[i] = i + 1;
        list.add(allPos);

        int[] allNeg = new int[n];
        for (int i = 0; i < n; i++) allNeg[i] = -(i + 1);
        list.add(allNeg);

        int[] rand1 = new int[n];
        for (int i = 0; i < n; i++) rand1[i] = rand.nextInt(200001) - 100000;
        list.add(rand1);

        int[] rand2 = new int[n];
        for (int i = 0; i < n; i++) rand2[i] = rand.nextInt(1001) - 500;
        list.add(rand2);

        int[] duplicates = new int[n];
        for (int i = 0; i < n; i++) duplicates[i] = rand.nextInt(11) - 5;
        list.add(duplicates);

        int[] sym = new int[n];
        for (int i = 0; i < n / 2; i++) {
            sym[i] = -(i + 1);
            sym[n - 1 - i] = i + 1;
        }
        list.add(sym);

        return list;
    }
}
