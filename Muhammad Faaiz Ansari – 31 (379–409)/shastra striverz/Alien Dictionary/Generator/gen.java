import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    @SuppressWarnings("unchecked")
    private static String solve(String[] words, int N, int K) {
        List<Integer>[] adj = new ArrayList[K];
        for (int i = 0; i < K; i++) {
            adj[i] = new ArrayList<>();
        }
        int[] inDegree = new int[K];

        for (int i = 0; i < N - 1; i++) {
            String w1 = words[i];
            String w2 = words[i + 1];

            int minLen = Math.min(w1.length(), w2.length());
            boolean diffFound = false;

            for (int j = 0; j < minLen; j++) {
                if (w1.charAt(j) != w2.charAt(j)) {
                    int u = w1.charAt(j) - 'a';
                    int v = w2.charAt(j) - 'a';
                    if (u < K && v < K) {
                        adj[u].add(v);
                        inDegree[v]++;
                    }
                    diffFound = true;
                    break;
                }
            }

            if (!diffFound && w1.length() > w2.length()) {
                return "";
            }
        }

        int[] queue = new int[K];
        int head = 0;
        int tail = 0;

        for (int i = 0; i < K; i++) {
            if (inDegree[i] == 0) {
                queue[tail++] = i;
            }
        }

        int[] order = new int[K];
        int count = 0;

        while (head < tail) {
            int u = queue[head++];
            order[count++] = u;

            for (int v : adj[u]) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue[tail++] = v;
                }
            }
        }

        if (count < K) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < K; i++) {
            sb.append((char) ('a' + order[i]));
            if (i + 1 < K) {
                sb.append(' ');
            }
        }

        return sb.toString();
    }

    private static void writeTestCase(File inDir, File outDir, int index, int N, int K, String[] words) throws Exception {
        String filename = String.format("%02d.txt", index);
        File inFile = new File(inDir, "input" + filename);
        File outFile = new File(outDir, "output" + filename);

        try (PrintWriter inPw = new PrintWriter(inFile)) {
            inPw.println(N + " " + K);
            for (int i = 0; i < N; i++) {
                inPw.println(words[i]);
            }
        }

        String result = solve(words, N, K);
        try (PrintWriter outPw = new PrintWriter(outFile)) {
            outPw.println(result);
        }
    }

    private static String[] generateWords(int N, int K, boolean makeInvalid, Random rng) {
        // Create an alien alphabet permutation
        int[] perm = new int[K];
        for (int i = 0; i < K; i++) perm[i] = i;
        for (int i = K - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int tmp = perm[i];
            perm[i] = perm[j];
            perm[j] = tmp;
        }

        int[] rank = new int[K];
        for (int i = 0; i < K; i++) {
            rank[perm[i]] = i;
        }

        // Generate N random words
        String[] words = new String[N];
        for (int i = 0; i < N; i++) {
            int len = 1 + rng.nextInt(Math.min(50, Math.max(3, K * 2)));
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < len; j++) {
                sb.append((char) ('a' + perm[rng.nextInt(K)]));
            }
            words[i] = sb.toString();
        }

        // Ensure every character from 0..K-1 appears in at least one word
        for (int c = 0; c < K && c < N; c++) {
            words[c] = ((char) ('a' + perm[c])) + words[c];
        }

        // Sort words using alien comparator
        Arrays.sort(words, (w1, w2) -> {
            int minLen = Math.min(w1.length(), w2.length());
            for (int i = 0; i < minLen; i++) {
                int r1 = rank[w1.charAt(i) - 'a'];
                int r2 = rank[w2.charAt(i) - 'a'];
                if (r1 != r2) {
                    return Integer.compare(r1, r2);
                }
            }
            return Integer.compare(w1.length(), w2.length());
        });

        if (makeInvalid && N >= 2) {
            if (rng.nextBoolean()) {
                // Inject prefix violation: replace words[idx+1] with prefix of words[idx]
                int idx = rng.nextInt(N - 1);
                if (words[idx].length() >= 2) {
                    words[idx + 1] = words[idx].substring(0, words[idx].length() - 1);
                } else {
                    words[idx + 1] = words[idx];
                    words[idx] = words[idx] + "a";
                }
            } else {
                // Swap two adjacent distinct words to create a contradiction
                int idx = rng.nextInt(N - 1);
                String tmp = words[idx];
                words[idx] = words[idx + 1];
                words[idx + 1] = tmp;
            }
        }

        return words;
    }

    public static void main(String[] args) {
        try {
            File baseDir = new File("d:/MF Shastra/shastra striverz/Alien Dictionary/Testcases");
            File inDir = new File(baseDir, "Input");
            File outDir = new File(baseDir, "Output");

            inDir.mkdirs();
            outDir.mkdirs();

            Random rng = new Random(5032026L);

            // 00: Sample 1
            writeTestCase(inDir, outDir, 0, 5, 4, new String[]{"baa", "abcd", "abca", "cab", "cad"});

            // 01: Sample 2
            writeTestCase(inDir, outDir, 1, 3, 3, new String[]{"caa", "aaa", "aab"});

            // 02: Sample 3
            writeTestCase(inDir, outDir, 2, 3, 3, new String[]{"abc", "bca", "cab"});

            // 03: Minimal boundary N=1, K=1
            writeTestCase(inDir, outDir, 3, 1, 1, new String[]{"a"});

            // 04: Minimal boundary N=1, K=2
            writeTestCase(inDir, outDir, 4, 1, 2, new String[]{"a"});

            // 05: Minimal boundary N=2, K=2 ("a", "b")
            writeTestCase(inDir, outDir, 5, 2, 2, new String[]{"a", "b"});

            // 06: Minimal boundary N=2, K=2 ("b", "a")
            writeTestCase(inDir, outDir, 6, 2, 2, new String[]{"b", "a"});

            // 07: Prefix violation ("ba", "b") -> invalid
            writeTestCase(inDir, outDir, 7, 2, 2, new String[]{"ba", "b"});

            // 08: 2-cycle contradiction ("a", "b", "a") -> invalid
            writeTestCase(inDir, outDir, 8, 3, 2, new String[]{"a", "b", "a"});

            // 09: 3-cycle contradiction ("a", "b", "c", "a") -> invalid
            writeTestCase(inDir, outDir, 9, 4, 3, new String[]{"a", "b", "c", "a"});

            // 10: N=4, K=3 valid
            writeTestCase(inDir, outDir, 10, 4, 3, new String[]{"a", "b", "c", "ab"});

            // 11: Prefix violation N=4, K=3 ("abc", "ab", "bc", "c") -> invalid
            writeTestCase(inDir, outDir, 11, 4, 3, new String[]{"abc", "ab", "bc", "c"});

            // 12: Reverse standard alphabet
            writeTestCase(inDir, outDir, 12, 5, 5, new String[]{"e", "d", "c", "b", "a"});

            // 13: Normal standard alphabet
            writeTestCase(inDir, outDir, 13, 5, 5, new String[]{"a", "b", "c", "d", "e"});

            // 14: Diamond DAG
            writeTestCase(inDir, outDir, 14, 5, 4, new String[]{"ab", "ac", "ba", "ca", "d"});

            // 15 - 34: Medium dictionaries (N = 10..50, K = 4..10)
            for (int tc = 15; tc <= 34; tc++) {
                int N = 10 + (tc - 15) * 2;
                int K = 4 + (tc - 15) % 7;
                boolean makeInvalid = (tc % 4 == 0);
                writeTestCase(inDir, outDir, tc, N, K, generateWords(N, K, makeInvalid, rng));
            }

            // 35 - 69: Large dictionaries (N = 60..150, K = 10..20)
            for (int tc = 35; tc <= 69; tc++) {
                int N = 60 + (tc - 35) * 2 + rng.nextInt(5);
                int K = 10 + (tc - 35) % 11;
                boolean makeInvalid = (tc % 4 == 1);
                writeTestCase(inDir, outDir, tc, N, K, generateWords(N, K, makeInvalid, rng));
            }

            // 70 - 89: Very Large dictionaries (N = 160..250, K = 20..26)
            for (int tc = 70; tc <= 89; tc++) {
                int N = 160 + (tc - 70) * 4 + rng.nextInt(5);
                int K = 20 + (tc - 70) % 7;
                boolean makeInvalid = (tc % 5 == 0);
                writeTestCase(inDir, outDir, tc, N, K, generateWords(N, K, makeInvalid, rng));
            }

            // 90 - 99: Extreme Maximum Constraints (N = 300, K = 26)
            // 90: N=300, K=26, reverse standard alphabet
            String[] w90 = new String[300];
            for (int i = 0; i < 300; i++) {
                char first = (char) ('z' - (i % 26));
                char second = (char) ('z' - ((i / 26) % 26));
                w90[i] = "" + first + second;
            }
            Arrays.sort(w90, (a, b) -> {
                int minLen = Math.min(a.length(), b.length());
                for (int i = 0; i < minLen; i++) {
                    int r1 = 'z' - a.charAt(i);
                    int r2 = 'z' - b.charAt(i);
                    if (r1 != r2) return Integer.compare(r1, r2);
                }
                return Integer.compare(a.length(), b.length());
            });
            writeTestCase(inDir, outDir, 90, 300, 26, w90);

            // 91: N=300, K=26, pure random valid
            writeTestCase(inDir, outDir, 91, 300, 26, generateWords(300, 26, false, rng));

            // 92: N=300, K=26, prefix violation injected
            String[] w92 = generateWords(300, 26, false, rng);
            w92[151] = w92[150].substring(0, Math.max(1, w92[150].length() - 1));
            w92[150] = w92[150] + "z";
            writeTestCase(inDir, outDir, 92, 300, 26, w92);

            // 93: N=300, K=26, cycle injected
            String[] w93 = generateWords(300, 26, false, rng);
            String tmp = w93[100];
            w93[100] = w93[101];
            w93[101] = tmp;
            writeTestCase(inDir, outDir, 93, 300, 26, w93);

            // 94 - 99: Valid extreme cases and edge cases
            writeTestCase(inDir, outDir, 94, 300, 26, generateWords(300, 26, false, rng));
            writeTestCase(inDir, outDir, 95, 300, 26, generateWords(300, 26, false, rng));
            writeTestCase(inDir, outDir, 96, 300, 26, generateWords(300, 26, true, rng));
            writeTestCase(inDir, outDir, 97, 300, 26, generateWords(300, 26, false, rng));
            writeTestCase(inDir, outDir, 98, 300, 26, generateWords(300, 26, false, rng));
            writeTestCase(inDir, outDir, 99, 300, 26, generateWords(300, 26, false, rng));

            System.out.println("SUCCESS: Generated 100 testcases for Alien Dictionary.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
