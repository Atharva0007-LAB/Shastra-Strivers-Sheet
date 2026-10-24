import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;

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

    @SuppressWarnings("unchecked")
    public static String findOrder(String[] words, int N, int K) {
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

            // Prefix violation: longer word before its prefix
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
            return ""; // Cycle detected
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

    public static void main(String[] args) {
        try {
            FastScanner sc = new FastScanner(System.in);
            int N = sc.nextInt();
            if (N == -1) return;
            int K = sc.nextInt();

            String[] words = new String[N];
            for (int i = 0; i < N; i++) {
                words[i] = sc.nextToken();
            }

            String result = findOrder(words, N, K);

            PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
            out.println(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
