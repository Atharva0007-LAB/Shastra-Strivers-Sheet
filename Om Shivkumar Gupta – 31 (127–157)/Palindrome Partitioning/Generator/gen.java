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

        Random rand = new Random(136);
        String[] samples = {
            "aab", "a", "aaa", "abc", "racecar",
            "noon", "deified", "civic", "radar", "rotor", "kayak", "reviver",
            "madam", "refer", "level", "aba", "bb", "abba", "aabaa", "abacaba"
        };

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            String s;
            if (tc < samples.length) {
                s = samples[tc];
            } else {
                int len = 3 + rand.nextInt(7);
                StringBuilder sb = new StringBuilder();
                int alphabetSize = 1 + rand.nextInt(3); // high symmetry to produce multiple palindrome cuts
                for (int i = 0; i < len; i++) {
                    sb.append((char)('a' + rand.nextInt(alphabetSize)));
                }
                s = sb.toString();
            }

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(s);
            inPw.close();

            // Optimal Backtracking
            List<List<String>> result = new ArrayList<>();
            backtrack(0, s, new ArrayList<>(), result);

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (List<String> part : result) {
                outPw.print("[");
                for (int i = 0; i < part.size(); i++) {
                    outPw.print(part.get(i) + (i == part.size() - 1 ? "" : ", "));
                }
                outPw.println("]");
            }
            outPw.close();
        }
        System.out.println("Palindrome Partitioning: 100 test cases generated.");
    }

    private static void backtrack(int start, String s, List<String> current, List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end)) {
                current.add(s.substring(start, end + 1));
                backtrack(end + 1, s, current, result);
                current.remove(current.size() - 1);
            }
        }
    }

    private static boolean isPalindrome(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l++) != s.charAt(r--)) return false;
        }
        return true;
    }
}
