import java.io.*;
import java.util.*;

public class gen {
    public static void main(String[] args) throws IOException {
        String baseDir = "TestCases";
        if (!new File(baseDir).exists() && new File("../TestCases").exists()) baseDir = "../TestCases";
        File inDir = new File(baseDir, "Input");
        File outDir = new File(baseDir, "Output");
        inDir.mkdirs(); outDir.mkdirs();

        Random rand = new Random(148);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            String s;

            if (tc == 0) s = "bbbab";
            else if (tc == 1) s = "cbbd";
            else if (tc == 2) s = "a";
            else if (tc == 3) s = "abcdef";
            else if (tc == 4) s = "racecar";
            else {
                int len = (tc < 40) ? 15 + rand.nextInt(35) : 100 + rand.nextInt(400);
                StringBuilder sb = new StringBuilder();
                int alph = 2 + rand.nextInt(6);
                for (int i = 0; i < len; i++) sb.append((char)('a' + rand.nextInt(alph)));
                s = sb.toString();
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(s);
            inPw.close();

            int ans = lps(s);

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            outPw.println(ans);
            outPw.close();
        }
        System.out.println("Longest Palindromic Subsequence: 100 test cases generated.");
    }

    static int lps(String s) {
        int n = s.length();
        int[] dp = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1;
            int prev = 0;
            for (int j = i + 1; j < n; j++) {
                int temp = dp[j];
                if (s.charAt(i) == s.charAt(j)) {
                    dp[j] = 2 + prev;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                prev = temp;
            }
        }
        return dp[n - 1];
    }
}
