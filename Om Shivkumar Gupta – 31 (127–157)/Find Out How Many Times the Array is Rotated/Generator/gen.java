import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(138);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] arr;

            if (tc == 0) {
                n = 5; arr = new int[]{5, 1, 2, 3, 4};
            } else if (tc == 1) {
                n = 5; arr = new int[]{1, 2, 3, 4, 5};
            } else if (tc == 2) {
                n = 4; arr = new int[]{2, 3, 4, 1};
            } else if (tc == 3) {
                n = 1; arr = new int[]{99};
            } else if (tc == 4) {
                n = 2; arr = new int[]{10, 5};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                int[] sorted = new int[n];
                int cur = 0;
                for (int i = 0; i < n; i++) {
                    cur += 1 + rand.nextInt(5);
                    sorted[i] = cur;
                }
                int pivot = rand.nextInt(n);
                arr = new int[n];
                for (int i = 0; i < n; i++) arr[i] = sorted[(i + pivot) % n];
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) inPw.print(arr[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int minIdx = 0;
            for (int i = 1; i < n; i++) {
                if (arr[i] < arr[minIdx]) minIdx = i;
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(minIdx);
            outPw.close();
        }
        System.out.println("Problem 138 generated 100 test cases.");
    }
}
