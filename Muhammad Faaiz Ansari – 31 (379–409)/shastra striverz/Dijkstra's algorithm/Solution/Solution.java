import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Solution {

    static class Edge {
        int to;
        int wt;

        Edge(int to, int wt) {
            this.to = to;
            this.wt = wt;
        }
    }

    static class Node implements Comparable<Node> {
        int u;
        int d;

        Node(int u, int d) {
            this.u = u;
            this.d = d;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.d, o.d);
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
    public static int[] dijkstra(int V, List<Edge>[] adj, int S) {
        int INF = 1000000000;
        int[] dist = new int[V];
        Arrays.fill(dist, INF);
        dist[S] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(S, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int u = curr.u;
            int d = curr.d;

            if (d > dist[u]) continue;

            for (Edge e : adj[u]) {
                if (dist[u] + e.wt < dist[e.to]) {
                    dist[e.to] = dist[u] + e.wt;
                    pq.add(new Node(e.to, dist[e.to]));
                }
            }
        }

        return dist;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            FastScanner sc = new FastScanner(System.in);
            int V = sc.nextInt();
            if (V == -1) return;
            int E = sc.nextInt();
            int S = sc.nextInt();

            List<Edge>[] adj = new ArrayList[V];
            for (int i = 0; i < V; i++) {
                adj[i] = new ArrayList<>();
            }

            for (int i = 0; i < E; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                int wt = sc.nextInt();
                adj[u].add(new Edge(v, wt));
                adj[v].add(new Edge(u, wt));
            }

            int[] dist = dijkstra(V, adj, S);

            PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
            for (int i = 0; i < V; i++) {
                out.print(dist[i]);
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
