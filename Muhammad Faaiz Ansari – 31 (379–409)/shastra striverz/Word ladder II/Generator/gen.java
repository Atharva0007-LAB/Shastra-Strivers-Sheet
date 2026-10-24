import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    private static void dfs(String curr, String startWord, Map<String, Integer> dist,
                            List<String> path, List<List<String>> results) {
        if (curr.equals(startWord)) {
            List<String> resPath = new ArrayList<>(path);
            Collections.reverse(resPath);
            results.add(resPath);
            return;
        }

        int d = dist.get(curr);
        char[] chars = curr.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char orig = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == orig) continue;
                chars[i] = c;
                String prev = new String(chars);

                if (dist.containsKey(prev) && dist.get(prev) == d - 1) {
                    path.add(prev);
                    dfs(prev, startWord, dist, path, results);
                    path.remove(path.size() - 1);
                }
            }
            chars[i] = orig;
        }
    }

    private static List<List<String>> solve(String startWord, String targetWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(targetWord)) {
            return Collections.emptyList();
        }

        Map<String, Integer> dist = new HashMap<>();
        Queue<String> queue = new ArrayDeque<>();

        queue.add(startWord);
        dist.put(startWord, 0);

        int targetDist = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            String u = queue.poll();
            int d = dist.get(u);

            if (d >= targetDist) continue;

            char[] chars = u.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char orig = chars[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == orig) continue;
                    chars[i] = c;
                    String v = new String(chars);

                    if (wordSet.contains(v)) {
                        if (!dist.containsKey(v)) {
                            dist.put(v, d + 1);
                            if (v.equals(targetWord)) {
                                targetDist = d + 1;
                            }
                            queue.add(v);
                        }
                    }
                }
                chars[i] = orig;
            }
        }

        if (!dist.containsKey(targetWord)) {
            return Collections.emptyList();
        }

        List<List<String>> results = new ArrayList<>();
        List<String> path = new ArrayList<>();
        path.add(targetWord);

        dfs(targetWord, startWord, dist, path, results);

        results.sort((p1, p2) -> {
            for (int i = 0; i < p1.size(); i++) {
                int cmp = p1.get(i).compareTo(p2.get(i));
                if (cmp != 0) return cmp;
            }
            return 0;
        });

        return results;
    }

    private static void writeTestCase(File inDir, File outDir, int index,
                                      String startWord, String targetWord, List<String> wordList) throws Exception {
        String filename = String.format("%02d.txt", index);
        File inFile = new File(inDir, "input" + filename);
        File outFile = new File(outDir, "output" + filename);

        try (PrintWriter inPw = new PrintWriter(inFile)) {
            inPw.println(startWord + " " + targetWord + " " + wordList.size());
            for (String w : wordList) {
                inPw.println(w);
            }
        }

        List<List<String>> sequences = solve(startWord, targetWord, wordList);
        try (PrintWriter outPw = new PrintWriter(outFile)) {
            for (List<String> seq : sequences) {
                for (int i = 0; i < seq.size(); i++) {
                    outPw.print(seq.get(i));
                    if (i + 1 < seq.size()) {
                        outPw.print(" ");
                    }
                }
                outPw.println();
            }
            if (sequences.isEmpty()) {
                outPw.println();
            }
        }
    }

    private static String randomWord(int M, Random rng) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < M; i++) {
            sb.append((char) ('a' + rng.nextInt(26)));
        }
        return sb.toString();
    }

    private static void generateRandomCase(File inDir, File outDir, int index, int N, int M,
                                           int chainLen, boolean reachable, boolean multiPath, Random rng) throws Exception {
        Set<String> set = new HashSet<>();
        String startWord = randomWord(M, rng);
        set.add(startWord);

        String current = startWord;
        List<String> chain = new ArrayList<>();

        if (reachable) {
            for (int step = 0; step < chainLen; step++) {
                int pos = rng.nextInt(M);
                char[] chars = current.toCharArray();
                char newChar = (char) ('a' + rng.nextInt(26));
                while (newChar == chars[pos]) {
                    newChar = (char) ('a' + rng.nextInt(26));
                }
                chars[pos] = newChar;
                String next = new String(chars);
                if (!set.contains(next)) {
                    set.add(next);
                    chain.add(next);
                    current = next;
                }
            }
        }

        String targetWord;
        if (reachable && !chain.isEmpty()) {
            targetWord = chain.get(chain.size() - 1);
            if (multiPath && chain.size() >= 2) {
                // Create an alternate 2-step detour
                // e.g. start -> alt -> target if chain.size() == 2
                // or chain[k] -> alt -> chain[k+2]
                int k = rng.nextInt(chain.size() - 1);
                String u = (k == 0) ? startWord : chain.get(k - 1);
                String v = chain.get(k + 1);
                // Find diff positions between u and v
                List<Integer> diffs = new ArrayList<>();
                for (int i = 0; i < M; i++) {
                    if (u.charAt(i) != v.charAt(i)) diffs.add(i);
                }
                if (diffs.size() == 2) {
                    char[] altChars = u.toCharArray();
                    altChars[diffs.get(1)] = v.charAt(diffs.get(1));
                    String alt = new String(altChars);
                    set.add(alt);
                }
            }
        } else {
            targetWord = randomWord(M, rng);
            while (targetWord.equals(startWord)) {
                targetWord = randomWord(M, rng);
            }
        }

        while (set.size() < N) {
            set.add(randomWord(M, rng));
        }

        List<String> wordList = new ArrayList<>(set);
        wordList.remove(startWord);

        if (reachable && !wordList.contains(targetWord)) {
            wordList.add(targetWord);
        }
        if (!reachable && rng.nextBoolean()) {
            wordList.remove(targetWord);
        }

        Collections.shuffle(wordList, rng);
        writeTestCase(inDir, outDir, index, startWord, targetWord, wordList);
    }

    public static void main(String[] args) {
        try {
            File baseDir = new File("d:/MF Shastra/shastra striverz/Word ladder II/Testcases");
            File inDir = new File(baseDir, "Input");
            File outDir = new File(baseDir, "Output");

            inDir.mkdirs();
            outDir.mkdirs();

            Random rng = new Random(5102026L);

            // 00: Sample 1
            writeTestCase(inDir, outDir, 0, "der", "dfs",
                Arrays.asList("des", "der", "dfr", "dgt", "dfs"));

            // 01: Sample 2
            writeTestCase(inDir, outDir, 1, "gedk", "geek",
                Arrays.asList("geek", "gefk"));

            // 02: Sample 3
            writeTestCase(inDir, outDir, 2, "abc", "xyz",
                Arrays.asList("abc", "ayc", "ayz", "xyz"));

            // 03: Minimal boundary M=1 (a -> b)
            writeTestCase(inDir, outDir, 3, "a", "b", Collections.singletonList("b"));

            // 04: Minimal boundary M=1 unreachable
            writeTestCase(inDir, outDir, 4, "a", "c", Collections.singletonList("b"));

            // 05: Multiple paths M=2 (ab -> aa -> ba and ab -> bb -> ba)
            writeTestCase(inDir, outDir, 5, "ab", "ba", Arrays.asList("aa", "bb", "ba"));

            // 06: M=2 direct 1-step
            writeTestCase(inDir, outDir, 6, "ab", "ac", Collections.singletonList("ac"));

            // 07: M=2 unreachable
            writeTestCase(inDir, outDir, 7, "ab", "cd", Arrays.asList("ac", "bc"));

            // 08: M=3 single path of length 4 (cat -> bat -> bad -> bed)
            writeTestCase(inDir, outDir, 8, "cat", "bed",
                Arrays.asList("bat", "bad", "bed", "bot", "bog"));

            // 09: M=3 two paths of length 4:
            // cat -> bat -> bot -> bog
            // cat -> cot -> bot -> bog
            writeTestCase(inDir, outDir, 9, "cat", "bog",
                Arrays.asList("bat", "cot", "bot", "bog", "bad"));

            // 10: M=4 multiple paths
            writeTestCase(inDir, outDir, 10, "cold", "warm",
                Arrays.asList("cord", "card", "ward", "warm", "word"));

            // 11: M=4 unreachable target missing
            writeTestCase(inDir, outDir, 11, "game", "fame",
                Arrays.asList("gate", "late", "fate"));

            // 12: M=4 single path length 5
            writeTestCase(inDir, outDir, 12, "head", "tail",
                Arrays.asList("heal", "teal", "tell", "tall", "tail"));

            // 13: M=5 multiple paths
            writeTestCase(inDir, outDir, 13, "stone", "phone",
                Arrays.asList("shone", "phone", "prune", "plane"));

            // 14: M=5 dead end trap
            writeTestCase(inDir, outDir, 14, "apple", "zebra",
                Arrays.asList("apply", "ample", "zebro", "zebra"));

            // 15 - 34: Medium testcases (N = 10..40, M = 3..6)
            for (int tc = 15; tc <= 34; tc++) {
                int N = 10 + (tc - 15);
                int M = 3 + (tc - 15) % 4;
                boolean reachable = (tc % 4 != 0);
                boolean multiPath = (tc % 3 == 0);
                int chainLen = 3 + (tc - 15) % 5;
                generateRandomCase(inDir, outDir, tc, N, M, chainLen, reachable, multiPath, rng);
            }

            // 35 - 69: Large testcases (N = 45..80, M = 4..8)
            for (int tc = 35; tc <= 69; tc++) {
                int N = 45 + (tc - 35);
                int M = 4 + (tc - 35) % 5;
                boolean reachable = (tc % 4 != 1);
                boolean multiPath = (tc % 2 == 0);
                int chainLen = 4 + (tc - 35) % 6;
                generateRandomCase(inDir, outDir, tc, N, M, chainLen, reachable, multiPath, rng);
            }

            // 70 - 89: Very Large testcases (N = 80..100, M = 5..10)
            for (int tc = 70; tc <= 89; tc++) {
                int N = 80 + (tc - 70);
                int M = 5 + (tc - 70) % 6;
                boolean reachable = (tc % 5 != 0);
                boolean multiPath = (tc % 3 != 1);
                int chainLen = 5 + (tc - 70) % 8;
                generateRandomCase(inDir, outDir, tc, N, M, chainLen, reachable, multiPath, rng);
            }

            // 90 - 99: Extreme Maximum Constraints (N = 100, M up to 10)
            generateRandomCase(inDir, outDir, 90, 100, 6, 8, true, true, rng);
            generateRandomCase(inDir, outDir, 91, 100, 7, 5, false, false, rng);
            generateRandomCase(inDir, outDir, 92, 100, 8, 1, true, false, rng);
            generateRandomCase(inDir, outDir, 93, 100, 8, 6, true, true, rng);
            generateRandomCase(inDir, outDir, 94, 100, 10, 10, true, false, rng);
            generateRandomCase(inDir, outDir, 95, 100, 10, 8, false, false, rng);
            generateRandomCase(inDir, outDir, 96, 100, 10, 8, true, true, rng);
            generateRandomCase(inDir, outDir, 97, 100, 10, 12, true, false, rng);
            generateRandomCase(inDir, outDir, 98, 100, 10, 10, true, true, rng);
            generateRandomCase(inDir, outDir, 99, 100, 10, 14, true, true, rng);

            System.out.println("SUCCESS: Generated 100 testcases for Word Ladder II.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
