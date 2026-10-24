import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(141);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            long n;
            if (tc == 0) n = 28;
            else if (tc == 1) n = 0;
            else if (tc == 2) n = 1;
            else if (tc == 3) n = 25;
            else if (tc == 4) n = 1000000000L;
            else {
                if (tc < 25) n = rand.nextInt(100);
                else if (tc < 65) n = 100 + rand.nextInt(100000);
                else n = 1000000L + (long)(rand.nextDouble() * 999000000L);
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            inPw.close();

            long ans = (long) Math.sqrt(n);

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Problem 141 generated 100 test cases.");
    }
}
