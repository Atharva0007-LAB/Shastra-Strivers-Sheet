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

        public int nextInt() throws IOException {
            int c = read();
            while (c <= 32) {
                if (c == -1) return -1;
                c = read();
            }
            int res = 0;
            while (c > 32) {
                if (c < '0' || c > '9') {
                    throw new NumberFormatException();
                }
                res = res * 10 + (c - '0');
                c = read();
            }
            return res;
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean isCyclic(int V, List<Integer>[] adj) {
        if (V <= 1) return false;

        int[] inDegree = new int[V];
        for (int u = 0; u < V; u++) {
            for (int v : adj[u]) {
                inDegree[v]++;
            }
        }

        int[] queue = new int[V];
        int head = 0;
        int tail = 0;

        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0) {
                queue[tail++] = i;
            }
        }

        while (head < tail) {
            int u = queue[head++];
            for (int v : adj[u]) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue[tail++] = v;
                }
            }
        }

        // If the number of processed nodes is less than V, a directed cycle exists.
        return head < V;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            FastScanner sc = new FastScanner(System.in);
            int V = sc.nextInt();
            if (V == -1) return;
            int E = sc.nextInt();

            List<Integer>[] adj = new ArrayList[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new ArrayList<>();
            }

            for (int i = 0; i < E; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                adj[u].add(v);
            }

            boolean hasCycle = isCyclic(V, adj);

            PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
            out.println(hasCycle);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
