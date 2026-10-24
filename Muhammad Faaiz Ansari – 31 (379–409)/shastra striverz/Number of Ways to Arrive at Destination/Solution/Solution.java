import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Solution {

    static final int MOD = 1000000007;

    static class Edge {
        int to;
        long time;

        Edge(int to, long time) {
            this.to = to;
            this.time = time;
        }
    }

    static class Node implements Comparable<Node> {
        int u;
        long d;

        Node(int u, long d) {
            this.u = u;
            this.d = d;
        }

        @Override
        public int compareTo(Node o) {
            return Long.compare(this.d, o.d);
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
            boolean neg = false;
            if (c == '-') {
                neg = true;
                c = read();
            }
            int res = 0;
            while (c >= '0' && c <= '9') {
                res = res * 10 + (c - '0');
                c = read();
            }
            return neg ? -res : res;
        }

        public long nextLong() throws IOException {
            int c = read();
            while (c <= 32) {
                if (c == -1) return -1;
                c = read();
            }
            boolean neg = false;
            if (c == '-') {
                neg = true;
                c = read();
            }
            long res = 0;
            while (c >= '0' && c <= '9') {
                res = res * 10 + (c - '0');
                c = read();
            }
            return neg ? -res : res;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int n = fs.nextInt();
        if (n == -1) {
            out.flush();
            return;
        }
        int m = fs.nextInt();

        List<Edge>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();
            long time = fs.nextLong();
            if (u >= 0 && u < n && v >= 0 && v < n) {
                adj[u].add(new Edge(v, time));
                adj[v].add(new Edge(u, time));
            }
        }

        if (n <= 1) {
            out.println(1);
            out.flush();
            return;
        }

        long INF = Long.MAX_VALUE / 2;
        long[] dist = new long[n];
        int[] ways = new int[n];
        Arrays.fill(dist, INF);

        dist[0] = 0;
        ways[0] = 1;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int u = curr.u;
            long d = curr.d;

            if (d > dist[u]) continue;

            for (Edge e : adj[u]) {
                int v = e.to;
                long nextDist = d + e.time;

                if (nextDist < dist[v]) {
                    dist[v] = nextDist;
                    ways[v] = ways[u];
                    pq.add(new Node(v, nextDist));
                } else if (nextDist == dist[v]) {
                    ways[v] = (ways[v] + ways[u]) % MOD;
                }
            }
        }

        out.println(ways[n - 1]);
        out.flush();
    }
}
