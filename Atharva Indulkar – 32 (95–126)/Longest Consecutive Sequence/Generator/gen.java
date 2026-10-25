// Longest Consecutive Sequence - Generator/gen.java
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
            if (arr.length > 0) inWriter.println(join(arr));
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
        if (nums.length == 0) return 0;
        Set<Integer> set = new HashSet<>(nums.length);
        for (int x : nums) set.add(x);
        int maxLen = 0;
        for (int x : set) {
            if (!set.contains(x - 1)) {
                int cur = x;
                int len = 1;
                while (set.contains(cur + 1)) {
                    cur++;
                    len++;
                }
                if (len > maxLen) maxLen = len;
            }
        }
        return maxLen;
    }

    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Samples
        list.add(new int[]{100, 4, 200, 1, 3, 2});
        list.add(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1});

        // Edge cases
        list.add(new int[]{}); // empty
        list.add(new int[]{1});
        list.add(new int[]{1, 2});
        list.add(new int[]{2, 1});
        list.add(new int[]{10, 20, 30}); // no consecutive
        list.add(new int[]{5, 5, 5, 5}); // all equal -> len 1
        list.add(new int[]{-5, -4, -3, -2, -1, 0, 1});
        list.add(new int[]{-1000000000, 1000000000});
        list.add(new int[]{-1000000000, -999999999, 1000000000});
        list.add(new int[]{1, 3, 5, 7, 9, 2, 4, 6, 8, 10});

        while (list.size() < 60) {
            int n = 1 + rand.nextInt(25);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(51) - 25;
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
        int[] fullSeq = new int[n];
        for (int i = 0; i < n; i++) fullSeq[i] = i - 25000;
        for (int i = n - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int tmp = fullSeq[i];
            fullSeq[i] = fullSeq[j];
            fullSeq[j] = tmp;
        }
        list.add(fullSeq);

        int[] allEqual = new int[n];
        Arrays.fill(allEqual, 42);
        list.add(allEqual);

        int[] evens = new int[n];
        for (int i = 0; i < n; i++) evens[i] = 2 * i;
        list.add(evens);

        for (int k = 0; k < 4; k++) {
            int[] randMax = new int[n];
            for (int i = 0; i < n; i++) randMax[i] = rand.nextInt(2000000001) - 1000000000;
            list.add(randMax);
        }

        return list;
    }
}
