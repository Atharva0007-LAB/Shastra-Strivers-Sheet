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

        Random rand = new Random(137);

        for (int tc = 0; tc < 100; tc++) {
            String fName = String.format("%02d", tc);
            File inFile = new File(inDir, "input" + fName + ".txt");
            File outFile = new File(outDir, "output" + fName + ".txt");

            int n;
            String[] strs;

            if (tc == 0) {
                strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
            } else if (tc == 1) {
                strs = new String[]{"code"};
            } else if (tc == 2) {
                strs = new String[]{"ab", "ba", "ab", "ba"};
            } else if (tc == 3) {
                strs = new String[]{"cat", "dog", "bird"};
            } else if (tc == 4) {
                strs = new String[]{"listen", "silent", "elbow", "below", "car", "arc", "state"};
            } else {
                n = 10 + (tc % 40) * 8;
                strs = new String[n];
                String[] seeds = {"code", "listen", "play", "study", "cat", "algorithm", "star", "night"};
                for (int i = 0; i < n; i++) {
                    String base = seeds[rand.nextInt(seeds.length)];
                    List<Character> chars = new ArrayList<>();
                    for (char c : base.toCharArray()) chars.add(c);
                    Collections.shuffle(chars, rand);
                    StringBuilder sb = new StringBuilder();
                    for (char c : chars) sb.append(c);
                    strs[i] = sb.toString();
                }
            }

            n = strs.length;

            PrintWriter inPw = new PrintWriter(new BufferedWriter(new FileWriter(inFile)));
            inPw.println(n);
            for (int i = 0; i < n; i++) inPw.print(strs[i] + (i == n - 1 ? "" : " "));
            inPw.println();
            inPw.close();

            // Optimal Anagram Grouping
            Map<String, List<String>> map = new HashMap<>();
            for (String s : strs) {
                char[] ca = s.toCharArray();
                Arrays.sort(ca);
                String k = new String(ca);
                map.putIfAbsent(k, new ArrayList<>());
                map.get(k).add(s);
            }

            List<List<String>> result = new ArrayList<>();
            for (List<String> group : map.values()) {
                Collections.sort(group);
                result.add(group);
            }
            Collections.sort(result, (a, b) -> a.get(0).compareTo(b.get(0)));

            PrintWriter outPw = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            for (List<String> group : result) {
                outPw.print("[");
                for (int i = 0; i < group.size(); i++) {
                    outPw.print(group.get(i) + (i == group.size() - 1 ? "" : ", "));
                }
                outPw.println("]");
            }
            outPw.close();
        }
        System.out.println("Group Words by Anagrams: 100 test cases generated.");
    }
}
