import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.*;

public class Solution {

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[32768];
        private int head = 0;
        private int tail = 0;

        public FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (head >= tail) {
                head = 0;
                tail = in.read(buffer, 0, buffer.length);
                if (tail <= 0) return -1;
            }
            return buffer[head++];
        }

        public String nextToken() throws IOException {
            int c = read();
            while (c <= 32) {
                if (c == -1) return null;
                c = read();
            }
            StringBuilder sb = new StringBuilder();
            while (c > 32) {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }

        public int nextInt() throws IOException {
            String s = nextToken();
            if (s == null) return -1;
            return Integer.parseInt(s);
        }
    }

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

    public static List<List<String>> findSequences(String startWord, String targetWord, String[] wordList, int N) {
        Set<String> wordSet = new HashSet<>();
        for (int i = 0; i < N; i++) {
            wordSet.add(wordList[i]);
        }

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

    public static void main(String[] args) {
        try {
            FastScanner sc = new FastScanner(System.in);
            String startWord = sc.nextToken();
            if (startWord == null) return;
            String targetWord = sc.nextToken();
            int N = sc.nextInt();

            String[] wordList = new String[N];
            for (int i = 0; i < N; i++) {
                wordList[i] = sc.nextToken();
            }

            List<List<String>> sequences = findSequences(startWord, targetWord, wordList, N);

            PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
            for (List<String> seq : sequences) {
                for (int i = 0; i < seq.size(); i++) {
                    out.print(seq.get(i));
                    if (i + 1 < seq.size()) {
                        out.print(" ");
                    }
                }
                out.println();
            }
            if (sequences.isEmpty()) {
                out.println();
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
