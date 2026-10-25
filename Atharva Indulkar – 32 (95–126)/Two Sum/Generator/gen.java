// Two Sum - Generator/gen.java
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
            outWriter.println(solve(c.arr, c.target));
            outWriter.close();
        }
        System.out.println("Testcases generated successfully!");
    }

    static class Case {
        int[] arr;
        int target;
        Case(int[] arr, int target) { this.arr = arr; this.target = target; }
    }

    static String join(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < arr.length; j++) {
            sb.append(arr[j]);
            if (j != arr.length - 1) sb.append(" ");
        }
        return sb.toString();
    }

    static String solve(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int comp = target - nums[i];
            if (map.containsKey(comp)) {
                return map.get(comp) + " " + i;
            }
            map.put(nums[i], i);
        }
        return "-1";
    }

    static List<Case> buildCases() {
        Random rand = new Random(2026);
        List<Case> list = new ArrayList<>();

        // Samples
        list.add(new Case(new int[]{2, 7, 11, 15}, 9));
        list.add(new Case(new int[]{3, 2, 4}, 6));
        list.add(new Case(new int[]{3, 3}, 6));

        // Edge cases
        list.add(new Case(new int[]{1, 2}, 3));
        list.add(new Case(new int[]{-3, 4, 3, 90}, 0));
        list.add(new Case(new int[]{-1000000000, 1000000000}, 0));
        list.add(new Case(new int[]{1, 5, 3, 7}, 100)); // no solution
        list.add(new Case(new int[]{0, 4, 3, 0}, 0));
        list.add(new Case(new int[]{-10, -20, -30, -40}, -50));
        list.add(new Case(new int[]{5, 5, 5, 5}, 10));
        list.add(new Case(new int[]{1, 2, 3, 4, 5, 6}, 11)); // at the end
        list.add(new Case(new int[]{10, 20, 30, 40}, 30));   // at the start

        while (list.size() < 60) {
            int n = 2 + rand.nextInt(20);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(100) - 50;
            int idx1 = rand.nextInt(n);
            int idx2 = rand.nextInt(n);
            while (idx2 == idx1) idx2 = rand.nextInt(n);
            int target = arr[idx1] + arr[idx2];
            list.add(new Case(arr, target));
        }

        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(20000) - 10000;
            int idx1 = rand.nextInt(n);
            int idx2 = rand.nextInt(n);
            while (idx2 == idx1) idx2 = rand.nextInt(n);
            int target = arr[idx1] + arr[idx2];
            list.add(new Case(arr, target));
        }

        while (list.size() < 93) {
            int n = 5000 + rand.nextInt(20001);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(2000000001) - 1000000000;
            int idx1 = rand.nextInt(n);
            int idx2 = rand.nextInt(n);
            while (idx2 == idx1) idx2 = rand.nextInt(n);
            int target = arr[idx1] + arr[idx2];
            list.add(new Case(arr, target));
        }

        int n = 50000;
        int[] maxArr1 = new int[n];
        for (int i = 0; i < n; i++) maxArr1[i] = i * 2;
        list.add(new Case(maxArr1, maxArr1[100] + maxArr1[49000]));

        int[] maxArr2 = new int[n];
        for (int i = 0; i < n; i++) maxArr2[i] = rand.nextInt(2000000001) - 1000000000;
        list.add(new Case(maxArr2, maxArr2[0] + maxArr2[n - 1]));

        int[] maxArr3 = new int[n];
        Arrays.fill(maxArr3, 1);
        list.add(new Case(maxArr3, 2));

        int[] maxArr4 = new int[n];
        for (int i = 0; i < n; i++) maxArr4[i] = i;
        list.add(new Case(maxArr4, -999)); // no solution

        int[] maxArr5 = new int[n];
        for (int i = 0; i < n; i++) maxArr5[i] = rand.nextInt(100);
        list.add(new Case(maxArr5, maxArr5[n / 2] + maxArr5[n / 2 + 1]));

        int[] maxArr6 = new int[n];
        for (int i = 0; i < n; i++) maxArr6[i] = -i;
        list.add(new Case(maxArr6, maxArr6[5] + maxArr6[n - 5]));

        int[] maxArr7 = new int[n];
        maxArr7[0] = -1000000000;
        maxArr7[n - 1] = 1000000000;
        list.add(new Case(maxArr7, 0));

        return list;
    }
}
