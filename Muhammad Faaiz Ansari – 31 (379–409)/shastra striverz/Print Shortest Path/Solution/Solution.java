import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Solution {

    static class Edge {
        int to;
        long wt;

        Edge(int to, long wt) {
            this.to = to;
            this.wt = wt;
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

        List<Edge>[] adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();
            long w = fs.nextLong();
            if (u >= 1 && u <= n && v >= 1 && v <= n) {
                adj[u].add(new Edge(v, w));
                adj[v].add(new Edge(u, w));
            }
        }

        long INF = Long.MAX_VALUE / 2;
        long[] dist = new long[n + 1];
        int[] parent = new int[n + 1];
        Arrays.fill(dist, INF);
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        dist[1] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(1, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int u = curr.u;
            long d = curr.d;

            if (d > dist[u]) continue;
            if (u == n) break; // early exit when destination is finalized

            for (Edge e : adj[u]) {
                int v = e.to;
                long wt = e.wt;
                if (dist[u] + wt < dist[v]) {
                    dist[v] = dist[u] + wt;
                    parent[v] = u;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        if (dist[n] == INF) {
            out.println("-1");
        } else {
            List<Integer> path = new ArrayList<>();
            int curr = n;
            while (parent[curr] != curr) {
                path.add(curr);
                curr = parent[curr];
            }
            path.add(1);
            Collections.reverse(path);

            out.print(dist[n]);
            for (int v : path) {
                out.print(" ");
                out.print(v);
            }
            out.println();
        }

        out.flush();
    }
}
