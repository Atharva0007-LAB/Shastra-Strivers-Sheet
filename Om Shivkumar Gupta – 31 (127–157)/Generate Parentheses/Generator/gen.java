import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n = (tc % 8) + 1; // 1 to 8

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            inPw.close();

            List<String> res = new ArrayList<>();
            bt(0, 0, n, new StringBuilder(), res);

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (String s : res) outPw.println(s);
            outPw.close();
        }
        System.out.println("Generate Parentheses: 100 test cases generated.");
    }

    static void bt(int o, int c, int n, StringBuilder sb, List<String> res) {
        if (sb.length() == 2 * n) { res.add(sb.toString()); return; }
        if (o < n) { sb.append('('); bt(o + 1, c, n, sb, res); sb.deleteCharAt(sb.length() - 1); }
        if (c < o) { sb.append(')'); bt(o, c + 1, n, sb, res); sb.deleteCharAt(sb.length() - 1); }
    }
}
