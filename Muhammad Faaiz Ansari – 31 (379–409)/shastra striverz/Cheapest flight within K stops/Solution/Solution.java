import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    static class Edge {
        int to;
        int price;

        Edge(int to, int price) {
            this.to = to;
            this.price = price;
        }
    }

    static class Tuple {
        int stops;
        int node;
        int cost;

        Tuple(int stops, int node, int cost) {
            this.stops = stops;
            this.node = node;
            this.cost = cost;
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

        int src = fs.nextInt();
        int dst = fs.nextInt();
        int K = fs.nextInt();

        List<Edge>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();
            int price = fs.nextInt();
            if (u >= 0 && u < n && v >= 0 && v < n) {
                adj[u].add(new Edge(v, price));
            }
        }

        if (src == dst) {
            out.println(0);
            out.flush();
            return;
        }

        int INF = Integer.MAX_VALUE / 2;
        int[] dist = new int[n];
        Arrays.fill(dist, INF);

        ArrayDeque<Tuple> queue = new ArrayDeque<>();
        dist[src] = 0;
        queue.add(new Tuple(0, src, 0));

        while (!queue.isEmpty()) {
            Tuple curr = queue.poll();
            int stops = curr.stops;
            int u = curr.node;
            int cost = curr.cost;

            if (stops > K) continue;

            for (Edge e : adj[u]) {
                int v = e.to;
                int price = e.price;

                if (cost + price < dist[v]) {
                    dist[v] = cost + price;
                    queue.add(new Tuple(stops + 1, v, cost + price));
                }
            }
        }

        out.println(dist[dst] == INF ? -1 : dist[dst]);
        out.flush();
    }
}
