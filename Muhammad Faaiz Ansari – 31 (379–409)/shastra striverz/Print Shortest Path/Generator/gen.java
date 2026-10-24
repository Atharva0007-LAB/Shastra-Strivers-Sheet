import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static class Edge {
        int u, v;
        long wt;
        Edge(int u, int v, long wt) {
            this.u = u;
            this.v = v;
            this.wt = wt;
        }
    }

    static class AdjEdge {
        int to;
        long wt;
        AdjEdge(int to, long wt) {
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
        public int compareTo(Node o) {
            return Long.compare(this.d, o.d);
        }
    }

    @SuppressWarnings("unchecked")
    private static String solve(int n, int m, List<Edge> edges) {
        List<AdjEdge>[] adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (Edge e : edges) {
            if (e.u >= 1 && e.u <= n && e.v >= 1 && e.v <= n) {
                adj[e.u].add(new AdjEdge(e.v, e.wt));
                adj[e.v].add(new AdjEdge(e.u, e.wt));
            }
        }

        long INF = Long.MAX_VALUE / 2;
        long[] dist = new long[n + 1];
        int[] parent = new int[n + 1];
        Arrays.fill(dist, INF);
        for (int i = 1; i <= n; i++) parent[i] = i;

        dist[1] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(1, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int u = curr.u;
            long d = curr.d;

            if (d > dist[u]) continue;
            if (u == n) break;

            for (AdjEdge e : adj[u]) {
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
            return "-1";
        }

        List<Integer> path = new ArrayList<>();
        int curr = n;
        while (parent[curr] != curr) {
            path.add(curr);
            curr = parent[curr];
        }
        path.add(1);
        Collections.reverse(path);

        StringBuilder sb = new StringBuilder();
        sb.append(dist[n]);
        for (int v : path) {
            sb.append(" ").append(v);
        }
        return sb.toString();
    }

    private static void writeTestCase(int index, int n, List<Edge> edges) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        String inFileName = String.format("input%02d.txt", index);
        String outFileName = String.format("output%02d.txt", index);

        File inFile = new File(inputDir, inFileName);
        File outFile = new File(outputDir, outFileName);

        int m = edges.size();
        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(n + " " + m);
            for (Edge e : edges) {
                pw.println(e.u + " " + e.v + " " + e.wt);
            }
        }

        String ans = solve(n, m, edges);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5262026L);
        int tc = 0;

        // Sample 1: 5 6
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(1, 2, 2));
            edges.add(new Edge(2, 5, 5));
            edges.add(new Edge(2, 3, 4));
            edges.add(new Edge(1, 4, 1));
            edges.add(new Edge(4, 3, 3));
            edges.add(new Edge(3, 5, 1));
            writeTestCase(tc++, 5, edges);
        }

        // Sample 2: 4 4
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(1, 2, 2));
            edges.add(new Edge(2, 3, 4));
            edges.add(new Edge(1, 4, 1));
            edges.add(new Edge(4, 3, 3));
            writeTestCase(tc++, 4, edges);
        }

        // Sample 3: 3 1
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(1, 2, 2));
            writeTestCase(tc++, 3, edges);
        }

        // TC 03: Minimal n=2, m=0
        {
            writeTestCase(tc++, 2, new ArrayList<>());
        }

        // TC 04: Minimal n=2, m=1 wt=1
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(1, 2, 1));
            writeTestCase(tc++, 2, edges);
        }

        // TC 05: Minimal n=2, m=1 wt=100000
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(1, 2, 100000));
            writeTestCase(tc++, 2, edges);
        }

        // TC 06: n=2 with multiple edges between 1 and 2
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(1, 2, 50));
            edges.add(new Edge(1, 2, 10));
            edges.add(new Edge(2, 1, 30));
            edges.add(new Edge(1, 2, 5));
            writeTestCase(tc++, 2, edges);
        }

        // TC 07: n=3 line graph 1-2-3
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(1, 2, 10));
            edges.add(new Edge(2, 3, 20));
            writeTestCase(tc++, 3, edges);
        }

        // TC 08: n=3 triangle 1-3 direct vs 1-2-3
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(1, 2, 4));
            edges.add(new Edge(2, 3, 3));
            edges.add(new Edge(1, 3, 15));
            writeTestCase(tc++, 3, edges);
        }

        // TC 09: n=5 with self-loops
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(1, 1, 5));
            edges.add(new Edge(1, 2, 10));
            edges.add(new Edge(2, 2, 2));
            edges.add(new Edge(2, 5, 20));
            edges.add(new Edge(5, 5, 100));
            writeTestCase(tc++, 5, edges);
        }

        // TC 10: n=10, m=0
        {
            writeTestCase(tc++, 10, new ArrayList<>());
        }

        // TC 11: n=10, 1 and n in disconnected components
        {
            List<Edge> edges = new ArrayList<>();
            // component 1: 1..5
            for (int i = 1; i < 5; i++) edges.add(new Edge(i, i + 1, rng.nextInt(100) + 1));
            // component 2: 6..10
            for (int i = 6; i < 10; i++) edges.add(new Edge(i, i + 1, rng.nextInt(100) + 1));
            writeTestCase(tc++, 10, edges);
        }

        // TC 12: n=10, only n is isolated
        {
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < 9; i++) edges.add(new Edge(i, i + 1, 5));
            writeTestCase(tc++, 10, edges);
        }

        // TC 13: n=10, only 1 is isolated
        {
            List<Edge> edges = new ArrayList<>();
            for (int i = 2; i < 10; i++) edges.add(new Edge(i, i + 1, 5));
            writeTestCase(tc++, 10, edges);
        }

        // TC 14: Star graph center at 1
        {
            int n = 15;
            List<Edge> edges = new ArrayList<>();
            for (int i = 2; i <= n; i++) edges.add(new Edge(1, i, rng.nextInt(500) + 1));
            writeTestCase(tc++, n, edges);
        }

        // TC 15: Star graph center at n
        {
            int n = 15;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) edges.add(new Edge(i, n, rng.nextInt(500) + 1));
            writeTestCase(tc++, n, edges);
        }

        // TC 16: Star graph center at middle vertex (e.g. 5)
        {
            int n = 15;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                if (i != 5) edges.add(new Edge(5, i, rng.nextInt(100) + 1));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 17: Long line graph n=50
        {
            int n = 50;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) edges.add(new Edge(i, i + 1, rng.nextInt(20) + 1));
            writeTestCase(tc++, n, edges);
        }

        // TC 18: Line graph with shortcuts
        {
            int n = 50;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) edges.add(new Edge(i, i + 1, 10));
            // add shortcuts
            edges.add(new Edge(1, 15, 25));
            edges.add(new Edge(15, 35, 30));
            edges.add(new Edge(35, n, 20));
            writeTestCase(tc++, n, edges);
        }

        // TC 19: Simple cycle 1-2-...-n-1
        {
            int n = 30;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) edges.add(new Edge(i, i + 1, rng.nextInt(50) + 1));
            edges.add(new Edge(n, 1, rng.nextInt(50) + 1));
            writeTestCase(tc++, n, edges);
        }

        // TC 20: Cycle with cross chords
        {
            int n = 40;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) edges.add(new Edge(i, i + 1, rng.nextInt(50) + 10));
            edges.add(new Edge(n, 1, 100));
            for (int k = 0; k < 15; k++) {
                int u = rng.nextInt(n) + 1;
                int v = rng.nextInt(n) + 1;
                edges.add(new Edge(u, v, rng.nextInt(20) + 1));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 21 - 25: Complete / dense small graphs
        for (int k = 0; k < 5; k++) {
            int n = 15 + k * 5;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                for (int j = i + 1; j <= n; j++) {
                    if (rng.nextDouble() < 0.6) {
                        edges.add(new Edge(i, j, rng.nextInt(1000) + 1));
                    }
                }
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 26 - 30: Binary tree topologies
        for (int k = 0; k < 5; k++) {
            int n = 63;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i <= n / 2; i++) {
                int left = 2 * i;
                int right = 2 * i + 1;
                if (left <= n) edges.add(new Edge(i, left, rng.nextInt(100) + 1));
                if (right <= n) edges.add(new Edge(i, right, rng.nextInt(100) + 1));
            }
            // maybe connect leaves to test alternate paths
            if (k > 2) {
                edges.add(new Edge(n, 1, rng.nextInt(500) + 50));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 31 - 35: Grid / Mesh topologies
        for (int k = 0; k < 5; k++) {
            int R = 8 + k * 2;
            int C = 8 + k * 2;
            int n = R * C;
            List<Edge> edges = new ArrayList<>();
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    int u = r * C + c + 1;
                    if (c + 1 < C) edges.add(new Edge(u, u + 1, rng.nextInt(50) + 1));
                    if (r + 1 < R) edges.add(new Edge(u, u + C, rng.nextInt(50) + 1));
                }
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 36 - 40: Multi-layer DAG-like graphs
        for (int k = 0; k < 5; k++) {
            int layers = 10;
            int perLayer = 8;
            int n = layers * perLayer + 2; // 1 is src, n is dest
            List<Edge> edges = new ArrayList<>();
            // connect 1 to layer 1
            for (int j = 2; j <= perLayer + 1; j++) edges.add(new Edge(1, j, rng.nextInt(50) + 1));
            // between layers
            for (int l = 0; l < layers - 1; l++) {
                int startA = 2 + l * perLayer;
                int startB = startA + perLayer;
                for (int a = 0; a < perLayer; a++) {
                    for (int b = 0; b < perLayer; b++) {
                        if (rng.nextDouble() < 0.5) {
                            edges.add(new Edge(startA + a, startB + b, rng.nextInt(100) + 1));
                        }
                    }
                }
            }
            // last layer to n
            int lastStart = 2 + (layers - 1) * perLayer;
            for (int j = 0; j < perLayer; j++) edges.add(new Edge(lastStart + j, n, rng.nextInt(50) + 1));
            writeTestCase(tc++, n, edges);
        }

        // TC 41 - 44: Graph with 1-hop large weight vs multi-hop tiny weights
        for (int k = 0; k < 4; k++) {
            int n = 20 + k * 10;
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(1, n, 90000)); // direct expensive edge
            // chain 1 -> 2 -> ... -> n with weight 1 each
            for (int i = 1; i < n; i++) edges.add(new Edge(i, i + 1, 1));
            // some random noise edges
            for (int e = 0; e < 30; e++) {
                int u = rng.nextInt(n) + 1;
                int v = rng.nextInt(n) + 1;
                edges.add(new Edge(u, v, rng.nextInt(1000) + 100));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 45 - 64: Medium scale random graphs (n: 100 to 1000)
        for (int k = 0; k < 20; k++) {
            int n = 100 + rng.nextInt(900);
            int m = Math.min(2000, n + rng.nextInt(1500));
            List<Edge> edges = new ArrayList<>();

            // 80% of them connected, 20% disconnected
            boolean connect = (k % 5 != 0);
            if (connect) {
                for (int i = 1; i < n; i++) {
                    int p = rng.nextInt(i) + 1;
                    edges.add(new Edge(p, i + 1, rng.nextInt(10000) + 1));
                }
            }
            while (edges.size() < m) {
                int u = rng.nextInt(n) + 1;
                int v = rng.nextInt(n) + 1;
                edges.add(new Edge(u, v, rng.nextInt(10000) + 1));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 65 - 74: Specific weight distribution graphs
        for (int k = 0; k < 10; k++) {
            int n = 500 + rng.nextInt(500);
            int m = 1500 + rng.nextInt(1000);
            List<Edge> edges = new ArrayList<>();
            // tree for connectivity
            for (int i = 1; i < n; i++) {
                int p = rng.nextInt(i) + 1;
                long wt;
                if (k < 3) wt = 1; // unit weights
                else if (k < 6) wt = 100000; // max weights
                else wt = 1L << (rng.nextInt(16)); // powers of 2
                edges.add(new Edge(p, i + 1, wt));
            }
            while (edges.size() < m) {
                int u = rng.nextInt(n) + 1;
                int v = rng.nextInt(n) + 1;
                long wt = (k < 3) ? 1 : ((k < 6) ? 100000 : (1L << rng.nextInt(16)));
                edges.add(new Edge(u, v, wt));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 75 - 84: High vertex count chains, ladders, trees (n: 3000 to 10000)
        for (int k = 0; k < 10; k++) {
            int n = 3000 + k * 700;
            if (n > 10000) n = 10000;
            List<Edge> edges = new ArrayList<>();

            if (k % 3 == 0) {
                // Long chain
                for (int i = 1; i < n; i++) edges.add(new Edge(i, i + 1, rng.nextInt(100) + 1));
                // Add sparse shortcuts
                for (int s = 0; s < 500; s++) {
                    int u = rng.nextInt(n) + 1;
                    int v = rng.nextInt(n) + 1;
                    edges.add(new Edge(u, v, rng.nextInt(10000) + 1));
                }
            } else if (k % 3 == 1) {
                // Ladder (2 x (n/2))
                int half = n / 2;
                for (int i = 1; i < half; i++) {
                    edges.add(new Edge(i, i + 1, rng.nextInt(50) + 1));
                    edges.add(new Edge(half + i, half + i + 1, rng.nextInt(50) + 1));
                }
                for (int i = 1; i <= half; i++) {
                    edges.add(new Edge(i, half + i, rng.nextInt(100) + 1));
                }
            } else {
                // Random tree with extra edges
                for (int i = 1; i < n; i++) {
                    int p = rng.nextInt(i) + 1;
                    edges.add(new Edge(p, i + 1, rng.nextInt(500) + 1));
                }
                for (int extra = 0; extra < 2000; extra++) {
                    int u = rng.nextInt(n) + 1;
                    int v = rng.nextInt(n) + 1;
                    edges.add(new Edge(u, v, rng.nextInt(500) + 1));
                }
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 85 - 94: Disconnected large graphs / separated components
        for (int k = 0; k < 10; k++) {
            int n = 5000 + k * 500;
            if (n > 10000) n = 10000;
            List<Edge> edges = new ArrayList<>();
            // Partition nodes into 2 halves: 1..(n/2) and (n/2+1)..n
            int mid = n / 2;
            int mTotal = Math.min(18000, n * 2);
            for (int i = 1; i < mid; i++) edges.add(new Edge(i, i + 1, rng.nextInt(100) + 1));
            for (int i = mid + 1; i < n; i++) edges.add(new Edge(i, i + 1, rng.nextInt(100) + 1));

            // Add random edges strictly within each half
            while (edges.size() < mTotal) {
                if (rng.nextBoolean()) {
                    int u = rng.nextInt(mid) + 1;
                    int v = rng.nextInt(mid) + 1;
                    edges.add(new Edge(u, v, rng.nextInt(1000) + 1));
                } else {
                    int u = mid + 1 + rng.nextInt(n - mid);
                    int v = mid + 1 + rng.nextInt(n - mid);
                    edges.add(new Edge(u, v, rng.nextInt(1000) + 1));
                }
            }
            // In half of them, bridge them with an edge; in other half, leave disconnected
            if (k % 2 == 1) {
                edges.add(new Edge(rng.nextInt(mid) + 1, mid + 1 + rng.nextInt(n - mid), rng.nextInt(10000) + 1));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 95 - 99: Max constraint stress tests (n=10000, m=20000)
        // TC 95: n=10000, m=20000 random connected graph
        {
            int n = 10000;
            int m = 20000;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) edges.add(new Edge(i, i + 1, rng.nextInt(100000) + 1));
            while (edges.size() < m) {
                int u = rng.nextInt(n) + 1;
                int v = rng.nextInt(n) + 1;
                edges.add(new Edge(u, v, rng.nextInt(100000) + 1));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 96: n=10000, m=20000 with huge weights (all 100000)
        {
            int n = 10000;
            int m = 20000;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) edges.add(new Edge(i, i + 1, 100000));
            while (edges.size() < m) {
                int u = rng.nextInt(n) + 1;
                int v = rng.nextInt(n) + 1;
                edges.add(new Edge(u, v, 100000));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 97: n=10000, m=20000 with unit weights (all 1)
        {
            int n = 10000;
            int m = 20000;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) edges.add(new Edge(i, i + 1, 1));
            while (edges.size() < m) {
                int u = rng.nextInt(n) + 1;
                int v = rng.nextInt(n) + 1;
                edges.add(new Edge(u, v, 1));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 98: n=10000, m=20000 disconnected (1 and n disconnected)
        {
            int n = 10000;
            int m = 20000;
            List<Edge> edges = new ArrayList<>();
            int mid = 5000;
            for (int i = 1; i < mid; i++) edges.add(new Edge(i, i + 1, rng.nextInt(1000) + 1));
            for (int i = mid + 1; i < n; i++) edges.add(new Edge(i, i + 1, rng.nextInt(100) + 1));
            while (edges.size() < m) {
                if (rng.nextBoolean()) {
                    int u = rng.nextInt(mid) + 1;
                    int v = rng.nextInt(mid) + 1;
                    edges.add(new Edge(u, v, rng.nextInt(500) + 1));
                } else {
                    int u = mid + 1 + rng.nextInt(n - mid);
                    int v = mid + 1 + rng.nextInt(n - mid);
                    edges.add(new Edge(u, v, rng.nextInt(500) + 1));
                }
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 99: n=10000, m=20000 with multi-edges and self-loops on massive scale
        {
            int n = 10000;
            int m = 20000;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) {
                edges.add(new Edge(i, i + 1, rng.nextInt(1000) + 1));
            }
            // self loops
            for (int s = 1; s <= 2000; s++) {
                int u = rng.nextInt(n) + 1;
                edges.add(new Edge(u, u, rng.nextInt(100) + 1));
            }
            // multi-edges
            while (edges.size() < m) {
                int u = rng.nextInt(n) + 1;
                int v = rng.nextInt(n) + 1;
                edges.add(new Edge(u, v, rng.nextInt(50000) + 1));
            }
            writeTestCase(tc++, n, edges);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Print Shortest Path.");
    }
}
