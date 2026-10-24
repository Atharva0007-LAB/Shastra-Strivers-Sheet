import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(140);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, x;
            int[] arr;

            if (tc == 0) {
                n = 7; x = 2;
                arr = new int[]{1, 1, 2, 2, 2, 2, 3};
            } else if (tc == 1) {
                n = 5; x = 4;
                arr = new int[]{1, 1, 2, 2, 3};
            } else if (tc == 2) {
                n = 1; x = 1;
                arr = new int[]{1};
            } else if (tc == 3) {
                n = 1; x = 2;
                arr = new int[]{1};
            } else if (tc == 4) {
                n = 6; x = 5;
                arr = new int[]{5, 5, 5, 5, 5, 5};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                arr = new int[n];
                int cur = 1;
                for (int i = 0; i < n; i++) {
                    if (rand.nextDouble() > 0.5) cur++;
                    arr[i] = cur;
                }
                if (rand.nextBoolean()) x = arr[rand.nextInt(n)];
                else x = cur + 5;
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + x);
            for (int i = 0; i < n; i++) inPw.print(arr[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int cnt = 0;
            for (int val : arr) if (val == x) cnt++;

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(cnt);
            outPw.close();
        }
        System.out.println("Problem 140 generated 100 test cases.");
    }
}
