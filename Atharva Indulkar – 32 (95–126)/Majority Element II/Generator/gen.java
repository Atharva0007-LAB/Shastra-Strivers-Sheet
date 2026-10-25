// Majority Element II - Generator/gen.java
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

    // Reference answer: HashMap frequency counting
    static String solve(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int x : nums) map.put(x, map.getOrDefault(x, 0) + 1);
        List<Integer> list = new ArrayList<>();
        int threshold = nums.length / 3;
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (e.getValue() > threshold) {
                list.add(e.getKey());
            }
        }
        Collections.sort(list);
        if (list.isEmpty()) return "-1";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) sb.append(" ");
        }
        return sb.toString();
    }

    static int randValue(Random rand) {
        return rand.nextInt(2000000001) - 1000000000;
    }

    static int[] buildWithMajorities(int n, Integer maj1, int c1, Integer maj2, int c2, Random rand) {
        int[] arr = new int[n];
        int idx = 0;
        if (maj1 != null) {
            for (int i = 0; i < c1 && idx < n; i++) arr[idx++] = maj1;
        }
        if (maj2 != null) {
            for (int i = 0; i < c2 && idx < n; i++) arr[idx++] = maj2;
        }
        while (idx < n) {
            arr[idx++] = rand.nextInt(100) - 50;
        }
        // Shuffle
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Problem samples
        list.add(new int[]{3, 2, 3});
        list.add(new int[]{1});
        list.add(new int[]{1, 2});

        // Tiny arrays & boundaries
        list.add(new int[]{-1000000000});
        list.add(new int[]{1000000000});
        list.add(new int[]{-1000000000, 1000000000});
        list.add(new int[]{1, 2, 3}); // no majority (> 1 required)
        list.add(new int[]{2, 2, 2}); // all same
        list.add(new int[]{1, 1, 2, 2}); // both appear 2 times (> 4/3 = 1)
        list.add(new int[]{1, 2, 3, 4, 5, 6}); // no majority (> 2 required)
        list.add(new int[]{1, 1, 1, 2, 3, 4}); // 1 appears 3 times (> 2)
        list.add(new int[]{1, 1, 1, 2, 2, 2}); // both appear 3 times (> 2)
        list.add(new int[]{-5, -5, -5, -5, 10}); // negative majority
        list.add(new int[]{0, 0, 0, 0, 0}); // zeros

        // Border frequency tests (n = 6, n/3 = 2 -> count must be >= 3)
        list.add(new int[]{1, 1, 2, 2, 3, 3}); // none is > 2, output -1
        list.add(new int[]{1, 1, 1, 2, 2, 3}); // 1 is > 2
        list.add(new int[]{1, 1, 1, 2, 2, 2}); // 1 and 2 are > 2

        // Small structured patterns
        list.add(new int[]{4, 4, 4, 4, 5, 5, 5, 5, 6});
        list.add(new int[]{10, 20, 30, 40, 50, 60, 70});
        list.add(new int[]{1, 2, 1, 2, 1, 2, 1, 2, 3});

        // Random small (cases up to 60)
        while (list.size() < 60) {
            int n = 1 + rand.nextInt(20);
            int mode = rand.nextInt(4);
            if (mode == 0) {
                // Two majorities
                int c = n / 3 + 1;
                list.add(buildWithMajorities(n, rand.nextInt(10), c, rand.nextInt(10) + 11, c, rand));
            } else if (mode == 1) {
                // One majority
                int c = n / 3 + 1 + rand.nextInt(Math.max(1, n - n / 3));
                list.add(buildWithMajorities(n, rand.nextInt(10), c, null, 0, rand));
            } else if (mode == 2) {
                // All same
                int[] arr = new int[n];
                Arrays.fill(arr, rand.nextInt(20) - 10);
                list.add(arr);
            } else {
                // Random (often no majority)
                int[] arr = new int[n];
                for (int i = 0; i < n; i++) arr[i] = rand.nextInt(25) - 12;
                list.add(arr);
            }
        }

        // Random medium (cases up to 85)
        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int mode = rand.nextInt(3);
            if (mode == 0) {
                int c = n / 3 + 1 + rand.nextInt(10);
                list.add(buildWithMajorities(n, rand.nextInt(500), c, rand.nextInt(500) + 501, c, rand));
            } else if (mode == 1) {
                int c = n / 3 + 1 + rand.nextInt(n / 3);
                list.add(buildWithMajorities(n, rand.nextInt(1000), c, null, 0, rand));
            } else {
                int[] arr = new int[n];
                for (int i = 0; i < n; i++) arr[i] = rand.nextInt(2000) - 1000;
                list.add(arr);
            }
        }

        // Random large (cases up to 93)
        while (list.size() < 93) {
            int n = 2000 + rand.nextInt(20001);
            int c = n / 3 + 1 + rand.nextInt(100);
            list.add(buildWithMajorities(n, randValue(rand), c, randValue(rand), c, rand));
        }

        // Max size cases (n = 50,000)
        int n = 50000;
        int c = n / 3 + 10;
        list.add(buildWithMajorities(n, 123456789, c, -987654321, c, rand)); // two majorities
        list.add(buildWithMajorities(n, 42, n / 2 + 1, null, 0, rand));       // one clear majority
        int[] allSame = new int[n];
        Arrays.fill(allSame, 7777777);
        list.add(allSame);                                                    // all identical
        int[] noMaj = new int[n];
        for (int i = 0; i < n; i++) noMaj[i] = i;                             // all distinct
        list.add(noMaj);
        list.add(buildWithMajorities(n, -1000000000, c, 1000000000, c, rand)); // boundary values
        int[] border = new int[n];
        // Border: 3 elements each having exactly n/3
        int third = n / 3;
        for (int i = 0; i < third; i++) border[i] = 1;
        for (int i = third; i < 2 * third; i++) border[i] = 2;
        for (int i = 2 * third; i < n; i++) border[i] = 3;
        list.add(border);                                                     // 1, 2, 3 each <= n/3 -> no majority
        list.add(buildWithMajorities(n, 0, n / 3 + 1, null, 0, rand));        // single majority with 0

        return list;
    }
}
