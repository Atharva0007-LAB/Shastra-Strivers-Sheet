import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(149);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n, k;
            int[] vals;

            if (tc == 0) {
                n = 5; k = 2; vals = new int[]{1, 2, 3, 4, 5};
            } else if (tc == 1) {
                n = 0; k = 5; vals = new int[]{};
            } else if (tc == 2) {
                n = 1; k = 1000; vals = new int[]{42};
            } else if (tc == 3) {
                n = 4; k = 4; vals = new int[]{10, 20, 30, 40};
            } else if (tc == 4) {
                n = 6; k = 14; vals = new int[]{0, 1, 2, 3, 4, 5};
            } else {
                n = (tc < 40) ? 10 + rand.nextInt(30) : 1000 + rand.nextInt(2000);
                k = rand.nextInt(1000000000);
                vals = new int[n];
                for (int i = 0; i < n; i++) vals[i] = rand.nextInt(200) - 100;
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n + " " + k);
            if (n > 0) {
                for (int i = 0; i < n; i++) inPw.print(vals[i] + (i == n - 1 ? "" : " "));
                inPw.println();
            }
            inPw.close();

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            if (n > 0) {
                int shift = k % n;
                int[] res = new int[n];
                for (int i = 0; i < n; i++) {
                    res[(i + shift) % n] = vals[i];
                }
                for (int i = 0; i < n; i++) {
                    outPw.print(res[i] + (i == n - 1 ? "" : " "));
                }
                outPw.println();
            }
            outPw.close();
        }
        System.out.println("Rotate a LL: 100 test cases generated.");
    }
}
