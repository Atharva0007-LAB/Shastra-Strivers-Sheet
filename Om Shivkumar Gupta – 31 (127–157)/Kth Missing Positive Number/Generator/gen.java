import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(147);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, k;
            int[] arr;

            if (tc == 0) {
                n = 5; k = 5;
                arr = new int[]{2, 3, 4, 7, 11};
            } else if (tc == 1) {
                n = 4; k = 2;
                arr = new int[]{1, 2, 3, 4};
            } else if (tc == 2) {
                n = 1; k = 1;
                arr = new int[]{2};
            } else if (tc == 3) {
                n = 1; k = 5;
                arr = new int[]{1};
            } else if (tc == 4) {
                n = 3; k = 3;
                arr = new int[]{4, 7, 9};
            } else {
                if (tc < 25) n = 10 + rand.nextInt(50);
                else if (tc < 65) n = 1000 + rand.nextInt(2000);
                else n = 15000 + rand.nextInt(25000);

                arr = new int[n];
                int cur = 1;
                for (int i = 0; i < n; i++) {
                    cur += 1 + rand.nextInt(3);
                    arr[i] = cur;
                }
                k = 1 + rand.nextInt(cur + 10);
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + k);
            for (int i = 0; i < n; i++) inPw.print(arr[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int low = 0, high = n - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (arr[mid] - (mid + 1) < k) low = mid + 1;
                else high = mid - 1;
            }
            int ans = low + k;

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Problem 147 generated 100 test cases.");
    }
}
