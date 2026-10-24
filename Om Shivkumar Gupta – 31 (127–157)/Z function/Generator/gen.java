import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(156);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            String s;

            if (tc == 0) s = "aabxaabxcaabxaabxay";
            else if (tc == 1) s = "a";
            else if (tc == 2) s = "aaaaa";
            else if (tc == 3) s = "abcdef";
            else if (tc == 4) s = "abacaba";
            else {
                int len = (tc < 40) ? 20 + rand.nextInt(30) : 500 + rand.nextInt(2000);
                int alph = (tc % 3 == 0) ? 2 : (tc % 3 == 1 ? 4 : 26);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < len; i++) sb.append((char)('a' + rand.nextInt(alph)));
                s = sb.toString();
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(s);
            inPw.close();

            int n = s.length();
            int[] z = new int[n];
            int l = 0, r = 0;
            for (int i = 1; i < n; i++) {
                if (i <= r) z[i] = Math.min(r - i + 1, z[i - l]);
                while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) z[i]++;
                if (i + z[i] - 1 > r) { l = i; r = i + z[i] - 1; }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (int i = 0; i < n; i++) outPw.print(z[i] + (i == n - 1 ? "" : " "));
            outPw.println();
            outPw.close();
        }
        System.out.println("Z function: 100 test cases generated.");
    }
}
