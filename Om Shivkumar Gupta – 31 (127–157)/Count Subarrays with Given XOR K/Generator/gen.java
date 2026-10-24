import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(128);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, b;
            int[] a;

            if (tc == 0) {
                n = 5; b = 6;
                a = new int[]{4, 2, 2, 6, 4};
            } else if (tc == 1) {
                n = 4; b = 1;
                a = new int[]{1, 2, 3, 4};
            } else if (tc == 2) {
                n = 1; b = 0;
                a = new int[]{0};
            } else if (tc == 3) {
                n = 3; b = 5;
                a = new int[]{5, 6, 7};
            } else if (tc == 4) {
                n = 6; b = 2;
                a = new int[]{1, 3, 2, 2, 1, 3};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                b = rand.nextInt(32);
                a = new int[n];
                for (int i = 0; i < n; i++) a[i] = rand.nextInt(32);
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + b);
            for (int i = 0; i < n; i++) inPw.print(a[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int count = 0, cur = 0;
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0, 1);
            for (int x : a) {
                cur ^= x;
                count += map.getOrDefault(cur ^ b, 0);
                map.put(cur, map.getOrDefault(cur, 0) + 1);
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(count);
            outPw.close();
        }
        System.out.println("Problem 128 generated 100 test cases.");
    }
}
