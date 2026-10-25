// Missing And Repeating - Generator/gen.java
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
        int n = nums.length;
        long s = 0, s2 = 0;
        for (int x : nums) {
            s += x;
            s2 += (long) x * x;
        }
        long sn = (long) n * (n + 1) / 2;
        long s2n = (long) n * (n + 1) * (2L * n + 1) / 6;
        long diff1 = s - sn;
        long diff2 = s2 - s2n;
        long sum = diff2 / diff1;
        long b = (diff1 + sum) / 2;
        long a = sum - b;
        return b + " " + a;
    }

    static int[] createCase(int n, int repeating, int missing, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i + 1;
        arr[missing - 1] = repeating;
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

        // Samples
        list.add(new int[]{2, 2});
        list.add(new int[]{1, 3, 3});

        // Edge cases
        list.add(new int[]{1, 1}); // 1 repeating, 2 missing
        list.add(new int[]{2, 2, 3});
        list.add(new int[]{1, 2, 1});
        list.add(new int[]{4, 3, 6, 2, 1, 1});

        while (list.size() < 60) {
            int n = 2 + rand.nextInt(25);
            int rep = 1 + rand.nextInt(n);
            int mis = 1 + rand.nextInt(n);
            while (mis == rep) mis = 1 + rand.nextInt(n);
            list.add(createCase(n, rep, mis, rand));
        }

        while (list.size() < 85) {
            int n = 100 + rand.nextInt(901);
            int rep = 1 + rand.nextInt(n);
            int mis = 1 + rand.nextInt(n);
            while (mis == rep) mis = 1 + rand.nextInt(n);
            list.add(createCase(n, rep, mis, rand));
        }

        while (list.size() < 93) {
            int n = 5000 + rand.nextInt(20001);
            int rep = 1 + rand.nextInt(n);
            int mis = 1 + rand.nextInt(n);
            while (mis == rep) mis = 1 + rand.nextInt(n);
            list.add(createCase(n, rep, mis, rand));
        }

        // Max cases (n = 50,000)
        int n = 50000;
        list.add(createCase(n, 1, n, rand));
        list.add(createCase(n, n, 1, rand));
        list.add(createCase(n, n / 2, n / 2 + 1, rand));
        list.add(createCase(n, n / 2 + 1, n / 2, rand));
        list.add(createCase(n, 42, 4242, rand));
        list.add(createCase(n, 4242, 42, rand));
        list.add(createCase(n, 12345, 43210, rand));

        return list;
    }
}
