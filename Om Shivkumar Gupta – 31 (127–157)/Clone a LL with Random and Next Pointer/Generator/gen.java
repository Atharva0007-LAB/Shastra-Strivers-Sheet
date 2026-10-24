import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) {
            baseDir = "../TestCases";
        }
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs();
        outDir.mkdirs();

        Random rand = new Random(134);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] vals;
            int[] randIdx;

            if (tc == 0) {
                vals = new int[]{7, 13, 11, 10, 1};
                randIdx = new int[]{-1, 0, 4, 2, 0};
            } else if (tc == 1) {
                vals = new int[]{};
                randIdx = new int[]{};
            } else if (tc == 2) {
                vals = new int[]{42};
                randIdx = new int[]{0};
            } else if (tc == 3) {
                vals = new int[]{1, 2};
                randIdx = new int[]{1, 0};
            } else if (tc == 4) {
                vals = new int[]{3, 3, 3};
                randIdx = new int[]{-1, 0, -1};
            } else {
                n = 5 + (tc % 50) * 3;
                vals = new int[n];
                randIdx = new int[n];
                for (int i = 0; i < n; i++) {
                    vals[i] = rand.nextInt(2000) - 1000;
                    if (rand.nextDouble() < 0.2) randIdx[i] = -1;
                    else randIdx[i] = rand.nextInt(n);
                }
            }

            n = vals.length;

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) {
                inPw.println(vals[i] + " " + randIdx[i]);
            }
            inPw.close();

            // A deep copy retains exact values and random indices
            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (int i = 0; i < n; i++) {
                outPw.println(vals[i] + " " + randIdx[i]);
            }
            outPw.close();
        }
        System.out.println("Clone a LL: 100 test cases generated.");
    }
}
