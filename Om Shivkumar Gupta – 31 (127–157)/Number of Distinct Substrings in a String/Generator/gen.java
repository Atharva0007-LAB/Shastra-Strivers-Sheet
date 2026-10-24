import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(145);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            String s;

            if (tc == 0) s = "ababa";
            else if (tc == 1) s = "a";
            else if (tc == 2) s = "aaaa";
            else if (tc == 3) s = "abcdef";
            else if (tc == 4) s = "banana";
            else {
                int len = (tc < 40) ? 10 + rand.nextInt(30) : 100 + rand.nextInt(200);
                int alphabet = (tc % 3 == 0) ? 2 : (tc % 3 == 1 ? 5 : 26);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < len; i++) sb.append((char)('a' + rand.nextInt(alphabet)));
                s = sb.toString();
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(s);
            inPw.close();

            // HashSet to verify distinct substrings
            Set<String> set = new HashSet<>();
            for (int i = 0; i < s.length(); i++) {
                for (int j = i + 1; j <= s.length(); j++) {
                    set.add(s.substring(i, j));
                }
            }

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(set.size());
            outPw.close();
        }
        System.out.println("Number of Distinct Substrings: 100 test cases generated.");
    }
}
