// 4Sum - Generator/gen.java
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
            inWriter.println(c.arr.length + " " + c.target);
            inWriter.println(join(c.arr));
            inWriter.close();

            PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, "output" + fileName + ".txt"))));
            outWriter.print(solve(c.arr, c.target));
            outWriter.close();
        }
        System.out.println("Testcases generated successfully!");
    }

    static class Case {
        int[] arr;
        long target;
        Case(int[] arr, long target) { this.arr = arr; this.target = target; }
    }

    static String join(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < arr.length; j++) {
            sb.append(arr[j]);
            if (j != arr.length - 1) sb.append(" ");
        }
        return sb.toString();
    }

    static String solve(int[] nums, long target) {
        int[] arr = nums.clone();
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        boolean found = false;

        for (int i = 0; i < arr.length - 3; i++) {
            if (i > 0 && arr[i] == arr[i - 1]) continue;

            for (int j = i + 1; j < arr.length - 2; j++) {
                if (j > i + 1 && arr[j] == arr[j - 1]) continue;

                int left = j + 1, right = arr.length - 1;
                while (left < right) {
                    long sum = (long) arr[i] + arr[j] + arr[left] + arr[right];
                    if (sum == target) {
                        found = true;
                        sb.append(arr[i]).append(" ").append(arr[j]).append(" ")
                          .append(arr[left]).append(" ").append(arr[right]).append("\n");
                        while (left < right && arr[left] == arr[left + 1]) left++;
                        while (left < right && arr[right] == arr[right - 1]) right--;
                        left++;
                        right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        if (!found) return "-1\n";
        return sb.toString();
    }

    static List<Case> buildCases() {
        Random rand = new Random(2026);
        List<Case> list = new ArrayList<>();

        // Samples
        list.add(new Case(new int[]{1, 0, -1, 0, -2, 2}, 0));
        list.add(new Case(new int[]{2, 2, 2, 2, 2}, 9));
        list.add(new Case(new int[]{2, 2, 2, 2}, 8));

        // Edge cases & overflow test
        list.add(new Case(new int[]{1000000000, 1000000000, 1000000000, 1000000000}, -294967296L)); // LeetCode overflow test
        list.add(new Case(new int[]{-1000000000, -1000000000, 1000000000, 1000000000}, 0));
        list.add(new Case(new int[]{0, 0, 0, 0, 0}, 0));
        list.add(new Case(new int[]{-3, -1, 0, 2, 4, 5}, 1));
        list.add(new Case(new int[]{1, 2, 3, 4}, 10));
        list.add(new Case(new int[]{1, 2, 3, 4}, 11)); // no solution

        while (list.size() < 60) {
            int n = 4 + rand.nextInt(15);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(31) - 15;
            long target = rand.nextInt(21) - 10;
            list.add(new Case(arr, target));
        }

        while (list.size() < 85) {
            int n = 30 + rand.nextInt(51);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(201) - 100;
            long target = rand.nextInt(101) - 50;
            list.add(new Case(arr, target));
        }

        while (list.size() < 93) {
            int n = 100 + rand.nextInt(101);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(2001) - 1000;
            long target = rand.nextInt(1001) - 500;
            list.add(new Case(arr, target));
        }

        // Max size (n = 400 - 500)
        int n = 400;
        int[] all0 = new int[n];
        list.add(new Case(all0, 0));

        int[] allPos = new int[n];
        for (int i = 0; i < n; i++) allPos[i] = i;
        list.add(new Case(allPos, 1000));

        int[] allNeg = new int[n];
        for (int i = 0; i < n; i++) allNeg[i] = -i;
        list.add(new Case(allNeg, -500));

        int[] rand1 = new int[n];
        for (int i = 0; i < n; i++) rand1[i] = rand.nextInt(2000000001) - 1000000000;
        list.add(new Case(rand1, 0));

        int[] rand2 = new int[n];
        for (int i = 0; i < n; i++) rand2[i] = rand.nextInt(101) - 50;
        list.add(new Case(rand2, 10));

        int[] heavyDup = new int[n];
        for (int i = 0; i < n; i++) heavyDup[i] = rand.nextInt(5) - 2;
        list.add(new Case(heavyDup, 0));

        int[] maxBoundary = new int[n];
        for (int i = 0; i < n / 2; i++) maxBoundary[i] = -1000000000;
        for (int i = n / 2; i < n; i++) maxBoundary[i] = 1000000000;
        list.add(new Case(maxBoundary, 0));

        return list;
    }
}
