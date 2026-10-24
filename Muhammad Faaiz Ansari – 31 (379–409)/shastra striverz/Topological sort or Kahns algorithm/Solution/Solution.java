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
    public static int[] topoSort(int V, List<Integer>[] adj) {
        int[] inDegree = new int[V];
        for (int u = 0; u < V; u++) {
            for (int v : adj[u]) {
                inDegree[v]++;
            }
        }

        int[] order = new int[V];
        int head = 0;
        int tail = 0;

        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0) {
                order[tail++] = i;
            }
        }

        while (head < tail) {
            int u = order[head++];
            for (int v : adj[u]) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    order[tail++] = v;
                }
            }
        }

        return order;
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

            int[] order = topoSort(V, adj);

            PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
            for (int i = 0; i < V; i++) {
                out.print(order[i]);
                if (i + 1 < V) {
                    out.print(" ");
                }
            }
            out.println();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
