// Sort Colors - Generator/gen.java
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
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        return join(sorted);
    }

    static List<int[]> buildCases() {
        Random rand = new Random(2026);
        List<int[]> list = new ArrayList<>();

        // Samples
        list.add(new int[]{2, 0, 2, 1, 1, 0});
        list.add(new int[]{2, 0, 1});

        // Edge cases
        list.add(new int[]{0});
        list.add(new int[]{1});
        list.add(new int[]{2});
        list.add(new int[]{0, 0});
        list.add(new int[]{1, 1});
        list.add(new int[]{2, 2});
        list.add(new int[]{1, 0});
        list.add(new int[]{2, 1});
        list.add(new int[]{2, 0});
        list.add(new int[]{0, 1, 2});
        list.add(new int[]{2, 1, 0});
        list.add(new int[]{1, 2, 0});
        list.add(new int[]{1, 0, 2});
        list.add(new int[]{0, 2, 1});
        list.add(new int[]{0, 0, 0, 0});
        list.add(new int[]{1, 1, 1, 1});
        list.add(new int[]{2, 2, 2, 2});
        list.add(new int[]{1, 0, 1, 0, 0, 1});
        list.add(new int[]{2, 1, 2, 1, 1, 2});
        list.add(new int[]{2, 0, 2, 0, 0, 2});
        list.add(new int[]{0, 0, 1, 1, 2, 2});
        list.add(new int[]{2, 2, 1, 1, 0, 0});

        while (list.size() < 60) {
            int n = 1 + rand.nextInt(25);
            int[] arr = new int[n];
            int mode = rand.nextInt(4);
            if (mode == 0) for (int i = 0; i < n; i++) arr[i] = rand.nextInt(2);
            else if (mode == 1) for (int i = 0; i < n; i++) arr[i] = 1 + rand.nextInt(2);
            else if (mode == 2) for (int i = 0; i < n; i++) arr[i] = rand.nextBoolean() ? 0 : 2;
            else for (int i = 0; i < n; i++) arr[i] = rand.nextInt(3);
            list.add(arr);
        }

        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(3);
            list.add(arr);
        }

        while (list.size() < 93) {
            int n = 5000 + rand.nextInt(25001);
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(3);
            list.add(arr);
        }

        int n = 50000;
        int[] all0 = new int[n];
        list.add(all0);

        int[] all1 = new int[n];
        Arrays.fill(all1, 1);
        list.add(all1);

        int[] all2 = new int[n];
        Arrays.fill(all2, 2);
        list.add(all2);

        int[] rev = new int[n];
        for (int i = 0; i < n; i++) {
            if (i < n / 3) rev[i] = 2;
            else if (i < 2 * (n / 3)) rev[i] = 1;
            else rev[i] = 0;
        }
        list.add(rev);

        int[] alt = new int[n];
        for (int i = 0; i < n; i++) alt[i] = i % 3;
        list.add(alt);

        int[] skewed = new int[n];
        for (int i = 0; i < n; i++) {
            int r = rand.nextInt(100);
            skewed[i] = (r < 80) ? 0 : (r < 90 ? 1 : 2);
        }
        list.add(skewed);

        int[] balanced = new int[n];
        for (int i = 0; i < n; i++) balanced[i] = rand.nextInt(3);
        list.add(balanced);

        return list;
    }
}
