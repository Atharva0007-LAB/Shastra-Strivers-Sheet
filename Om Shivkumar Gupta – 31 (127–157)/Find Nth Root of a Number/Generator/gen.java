import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(142);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, m;
            if (tc == 0) { n = 3; m = 27; }
            else if (tc == 1) { n = 4; m = 69; }
            else if (tc == 2) { n = 1; m = 14; }
            else if (tc == 3) { n = 2; m = 1000000; }
            else if (tc == 4) { n = 5; m = 32; }
            else {
                n = 1 + rand.nextInt(8);
                if (rand.nextBoolean()) {
                    int r = 1 + rand.nextInt((int) Math.pow(1000000, 1.0 / n));
                    m = 1;
                    for (int i = 0; i < n; i++) m *= r;
                } else {
                    m = 1 + rand.nextInt(1000000);
                }
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + m);
            inPw.close();

            int ans = -1;
            int low = 1, high = m;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                long p = 1;
                boolean overflow = false;
                for (int i = 0; i < n; i++) {
                    p *= mid;
                    if (p > m) { overflow = true; break; }
                }
                if (!overflow && p == m) { ans = mid; break; }
                if (!overflow && p < m) low = mid + 1;
                else high = mid - 1;
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Problem 142 generated 100 test cases.");
    }
}
