// Leaders in an Array - Generator/gen.java
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
        List<Integer> list = new ArrayList<>();
        int max = Integer.MIN_VALUE;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] >= max) {
                list.add(nums[i]);
                max = nums[i];
            }
        }
        Collections.reverse(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i != list.size() - 1) sb.append(" ");
        }
        return sb.toString();
    }

    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Samples
        list.add(new int[]{16, 17, 4, 3, 5, 2});
        list.add(new int[]{1, 2, 3, 4, 0});
        list.add(new int[]{5, 4, 3, 2});

        // Edge cases
        list.add(new int[]{1});
        list.add(new int[]{1000000000});
        list.add(new int[]{-1000000000});
        list.add(new int[]{5, 5, 5, 5}); // all equal -> all leaders
        list.add(new int[]{1, 2, 3, 4, 5}); // strictly ascending -> only last
        list.add(new int[]{5, 4, 3, 2, 1}); // strictly descending -> all
        list.add(new int[]{-5, -10, -20});
        list.add(new int[]{0, 0, 0});

        while (list.size() < 60) {
            int n = 1 + rand.nextInt(25);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(201) - 100;
            list.add(arr);
        }

        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(20001) - 10000;
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
        int[] asc = new int[n];
        for (int i = 0; i < n; i++) asc[i] = i;
        list.add(asc);

        int[] desc = new int[n];
        for (int i = 0; i < n; i++) desc[i] = n - i;
        list.add(desc);

        int[] allEqual = new int[n];
        Arrays.fill(allEqual, 777);
        list.add(allEqual);

        int[] alt = new int[n];
        for (int i = 0; i < n; i++) alt[i] = (i % 2 == 0) ? 100 : 50;
        list.add(alt);

        for (int k = 0; k < 3; k++) {
            int[] randMax = new int[n];
            for (int i = 0; i < n; i++) randMax[i] = rand.nextInt(2000000001) - 1000000000;
            list.add(randMax);
        }

        return list;
    }
}
