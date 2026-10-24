import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(157);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            int[] vals;

            if (tc == 0) {
                n = 5; vals = new int[]{1, 2, 3, 4, 5};
            } else if (tc == 1) {
                n = 1; vals = new int[]{100};
            } else if (tc == 2) {
                n = 2; vals = new int[]{1, 2};
            } else if (tc == 3) {
                n = 6; vals = new int[]{1, 2, 3, 4, 5, 6};
            } else if (tc == 4) {
                n = 7; vals = new int[]{10, 20, 30, 40, 50, 60, 70};
            } else {
                n = (tc < 40) ? 10 + rand.nextInt(30) : 1000 + rand.nextInt(3000);
                vals = new int[n];
                for (int i = 0; i < n; i++) vals[i] = rand.nextInt(100000);
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) inPw.print(vals[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            int midIdx = n / 2;
            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (int i = midIdx; i < n; i++) {
                outPw.print(vals[i] + (i == n - 1 ? "" : " "));
            }
            outPw.println();
            outPw.close();
        }
        System.out.println("Find Middle of Linked List: 100 test cases generated.");
    }
}
