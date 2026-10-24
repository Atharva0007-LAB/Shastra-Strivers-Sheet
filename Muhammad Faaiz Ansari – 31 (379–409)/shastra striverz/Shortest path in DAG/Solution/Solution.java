import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    static class Edge {
        int to;
        int wt;

        Edge(int to, int wt) {
            this.to = to;
            this.wt = wt;
        }
    }

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
    public static long[] shortestPath(int N, List<Edge>[] adj, int[] inDegree) {
        int[] topo = new int[N];
        int head = 0;
        int tail = 0;

        for (int i = 0; i < N; i++) {
            if (inDegree[i] == 0) {
                topo[tail++] = i;
            }
        }

        while (head < tail) {
            int u = topo[head++];
            for (Edge e : adj[u]) {
                inDegree[e.to]--;
                if (inDegree[e.to] == 0) {
                    topo[tail++] = e.to;
                }
            }
        }

        long INF = Long.MAX_VALUE / 2;
        long[] dist = new long[N];
        Arrays.fill(dist, INF);
        dist[0] = 0;

        for (int i = 0; i < tail; i++) {
            int u = topo[i];
            if (dist[u] < INF) {
                for (Edge e : adj[u]) {
                    if (dist[u] + e.wt < dist[e.to]) {
                        dist[e.to] = dist[u] + e.wt;
                    }
                }
            }
        }

        return dist;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            FastScanner sc = new FastScanner(System.in);
            int N = sc.nextInt();
            if (N == -1) return;
            int M = sc.nextInt();

            List<Edge>[] adj = new ArrayList[N];
            for (int i = 0; i < N; i++) {
                adj[i] = new ArrayList<>();
            }
            int[] inDegree = new int[N];

            for (int i = 0; i < M; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                int wt = sc.nextInt();
                adj[u].add(new Edge(v, wt));
                inDegree[v]++;
            }

            long[] dist = shortestPath(N, adj, inDegree);
            long INF = Long.MAX_VALUE / 2;

            PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
            for (int i = 0; i < N; i++) {
                out.print(dist[i] >= INF ? -1 : dist[i]);
                if (i + 1 < N) {
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
