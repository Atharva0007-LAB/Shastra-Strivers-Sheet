import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    private static int solve(String startWord, String targetWord, List<String> wordList) {
        Set<String> set = new HashSet<>(wordList);
        if (!set.contains(targetWord)) {
            return 0;
        }

        Queue<String> wordQ = new ArrayDeque<>();
        Queue<Integer> levelQ = new ArrayDeque<>();

        wordQ.add(startWord);
        levelQ.add(1);
        set.remove(startWord);

        while (!wordQ.isEmpty()) {
            String word = wordQ.poll();
            int level = levelQ.poll();

            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char orig = chars[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == orig) continue;
                    chars[i] = c;
                    String nextWord = new String(chars);

                    if (set.contains(nextWord)) {
                        if (nextWord.equals(targetWord)) {
                            return level + 1;
                        }
                        set.remove(nextWord);
                        wordQ.add(nextWord);
                        levelQ.add(level + 1);
                    }
                }
                chars[i] = orig;
            }
        }

        return 0;
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

        int ans = solve(startWord, targetWord, wordList);
        try (PrintWriter outPw = new PrintWriter(outFile)) {
            outPw.println(ans);
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
                                           int chainLen, boolean reachable, Random rng) throws Exception {
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
        } else {
            targetWord = randomWord(M, rng);
            while (targetWord.equals(startWord)) {
                targetWord = randomWord(M, rng);
            }
        }

        // Fill remaining words
        while (set.size() < N) {
            String w = randomWord(M, rng);
            set.add(w);
        }

        List<String> wordList = new ArrayList<>(set);
        wordList.remove(startWord); // startWord doesn't have to be in wordList

        if (reachable && !wordList.contains(targetWord)) {
            wordList.add(targetWord);
        }

        // If not reachable, ensure targetWord is either not in wordList or disconnected
        if (!reachable && rng.nextBoolean()) {
            wordList.remove(targetWord);
        }

        Collections.shuffle(wordList, rng);
        writeTestCase(inDir, outDir, index, startWord, targetWord, wordList);
    }

    public static void main(String[] args) {
        try {
            File baseDir = new File("d:/MF Shastra/shastra striverz/Word ladder I/Testcases");
            File inDir = new File(baseDir, "Input");
            File outDir = new File(baseDir, "Output");

            inDir.mkdirs();
            outDir.mkdirs();

            Random rng = new Random(5092026L);

            // 00: Sample 1
            writeTestCase(inDir, outDir, 0, "der", "dfs",
                Arrays.asList("des", "der", "dfr", "dgt", "dfs"));

            // 01: Sample 2
            writeTestCase(inDir, outDir, 1, "gedk", "geek",
                Arrays.asList("geek", "gefk"));

            // 02: Sample 3
            writeTestCase(inDir, outDir, 2, "hit", "cog",
                Arrays.asList("hot", "dot", "dog", "lot", "log"));

            // 03: M=1 (reachable: a -> b)
            writeTestCase(inDir, outDir, 3, "a", "b", Collections.singletonList("b"));

            // 04: M=1 (unreachable target c missing)
            writeTestCase(inDir, outDir, 4, "a", "c", Collections.singletonList("b"));

            // 05: M=2 (ab -> cb -> cd)
            writeTestCase(inDir, outDir, 5, "ab", "cd", Arrays.asList("cb", "cd"));

            // 06: M=2 (ab -> ac)
            writeTestCase(inDir, outDir, 6, "ab", "ac", Collections.singletonList("ac"));

            // 07: M=2 (unreachable)
            writeTestCase(inDir, outDir, 7, "ab", "cd", Arrays.asList("ac", "bc"));

            // 08: M=3 chain of 4 (cat -> bat -> bad -> bed)
            writeTestCase(inDir, outDir, 8, "cat", "bed",
                Arrays.asList("bat", "bad", "bed", "bot", "bog"));

            // 09: M=3 dead end trap
            writeTestCase(inDir, outDir, 9, "cat", "dog",
                Arrays.asList("car", "bar", "bat", "dat"));

            // 10: M=4 ladder length 5 (cold -> cord -> card -> ward -> warm)
            writeTestCase(inDir, outDir, 10, "cold", "warm",
                Arrays.asList("cord", "card", "ward", "warm", "corn", "born"));

            // 11: M=4 ladder with shortcut
            writeTestCase(inDir, outDir, 11, "game", "fame",
                Arrays.asList("gate", "late", "fate", "fame"));

            // 12: M=5 ladder
            writeTestCase(inDir, outDir, 12, "stone", "phone",
                Arrays.asList("shone", "phone", "prune", "plane"));

            // 13: M=5 unreachable
            writeTestCase(inDir, outDir, 13, "apple", "zebra",
                Arrays.asList("apply", "ample", "zebro", "zebra"));

            // 14: M=6 ladder
            writeTestCase(inDir, outDir, 14, "butter", "bitter",
                Arrays.asList("batter", "bitter", "better"));

            // 15 - 34: Medium testcases (N = 10..50, M = 3..6)
            for (int tc = 15; tc <= 34; tc++) {
                int N = 10 + (tc - 15) * 2;
                int M = 3 + (tc - 15) % 4;
                boolean reachable = (tc % 4 != 0);
                int chainLen = 3 + (tc - 15) % 6;
                generateRandomCase(inDir, outDir, tc, N, M, chainLen, reachable, rng);
            }

            // 35 - 69: Large testcases (N = 60..200, M = 4..8)
            for (int tc = 35; tc <= 69; tc++) {
                int N = 60 + (tc - 35) * 4 + rng.nextInt(5);
                int M = 4 + (tc - 35) % 5;
                boolean reachable = (tc % 4 != 1);
                int chainLen = 4 + (tc - 35) % 10;
                generateRandomCase(inDir, outDir, tc, N, M, chainLen, reachable, rng);
            }

            // 70 - 89: Very Large testcases (N = 250..600, M = 5..10)
            for (int tc = 70; tc <= 89; tc++) {
                int N = 250 + (tc - 70) * 18 + rng.nextInt(10);
                int M = 5 + (tc - 70) % 6;
                boolean reachable = (tc % 5 != 0);
                int chainLen = 6 + (tc - 70) % 15;
                generateRandomCase(inDir, outDir, tc, N, M, chainLen, reachable, rng);
            }

            // 90 - 99: Extreme Maximum Constraints (N = 700..1000, M up to 10)
            generateRandomCase(inDir, outDir, 90, 1000, 6, 25, true, rng);
            generateRandomCase(inDir, outDir, 91, 1000, 7, 10, false, rng);
            generateRandomCase(inDir, outDir, 92, 1000, 8, 1, true, rng);
            generateRandomCase(inDir, outDir, 93, 1000, 8, 15, true, rng);
            generateRandomCase(inDir, outDir, 94, 1000, 10, 30, true, rng);
            generateRandomCase(inDir, outDir, 95, 1000, 10, 15, false, rng);
            generateRandomCase(inDir, outDir, 96, 1000, 10, 20, true, rng);
            generateRandomCase(inDir, outDir, 97, 1000, 10, 25, true, rng);
            generateRandomCase(inDir, outDir, 98, 1000, 10, 35, true, rng);
            generateRandomCase(inDir, outDir, 99, 1000, 10, 40, true, rng);

            System.out.println("SUCCESS: Generated 100 testcases for Word Ladder I.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
