import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static final int MOD = 1000000007;

    static class Edge {
        int u, v;
        long time;
        Edge(int u, int v, long time) {
            this.u = u;
            this.v = v;
            this.time = time;
        }
    }

    static class AdjEdge {
        int to;
        long time;
        AdjEdge(int to, long time) {
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

    @SuppressWarnings("unchecked")
    private static int solve(int n, int m, List<Edge> edges) {
        if (n <= 1) return 1;

        List<AdjEdge>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for (Edge e : edges) {
            if (e.u >= 0 && e.u < n && e.v >= 0 && e.v < n) {
                adj[e.u].add(new AdjEdge(e.v, e.time));
                adj[e.v].add(new AdjEdge(e.u, e.time));
            }
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

            for (AdjEdge e : adj[u]) {
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
        return ways[n - 1];
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
                pw.println(e.u + " " + e.v + " " + e.time);
            }
        }

        int ans = solve(n, m, edges);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5242026L);
        int tc = 0;

        // Sample 1: n=7, m=10
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 6, 7));
            edges.add(new Edge(0, 1, 2));
            edges.add(new Edge(1, 2, 3));
            edges.add(new Edge(1, 3, 3));
            edges.add(new Edge(6, 3, 3));
            edges.add(new Edge(3, 5, 1));
            edges.add(new Edge(6, 5, 1));
            edges.add(new Edge(2, 5, 1));
            edges.add(new Edge(0, 4, 5));
            edges.add(new Edge(4, 6, 2));
            writeTestCase(tc++, 7, edges);
        }

        // Sample 2: n=6, m=8
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 5, 8));
            edges.add(new Edge(0, 2, 2));
            edges.add(new Edge(0, 1, 1));
            edges.add(new Edge(1, 3, 3));
            edges.add(new Edge(1, 2, 3));
            edges.add(new Edge(2, 5, 6));
            edges.add(new Edge(3, 4, 2));
            edges.add(new Edge(4, 5, 2));
            writeTestCase(tc++, 6, edges);
        }

        // Sample 3: n=4, m=4
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 10));
            edges.add(new Edge(1, 2, 7));
            edges.add(new Edge(2, 3, 4));
            edges.add(new Edge(0, 3, 3));
            writeTestCase(tc++, 4, edges);
        }

        // TC 03: Minimal n=1, m=0
        writeTestCase(tc++, 1, new ArrayList<>());

        // TC 04: Minimal n=2, m=1 time=1
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 1));
            writeTestCase(tc++, 2, edges);
        }

        // TC 05: Minimal n=2, m=1 time=10^9
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 1000000000L));
            writeTestCase(tc++, 2, edges);
        }

        // TC 06: n=3 line 0-1-2
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 5));
            edges.add(new Edge(1, 2, 10));
            writeTestCase(tc++, 3, edges);
        }

        // TC 07: n=3 triangle with two equal paths (0-2 time 15 vs 0-1-2 time 5+10=15)
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 5));
            edges.add(new Edge(1, 2, 10));
            edges.add(new Edge(0, 2, 15));
            writeTestCase(tc++, 3, edges);
        }

        // TC 08: n=4 diamond (0-1-3 and 0-2-3 both time 10)
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 5));
            edges.add(new Edge(1, 3, 5));
            edges.add(new Edge(0, 2, 5));
            edges.add(new Edge(2, 3, 5));
            writeTestCase(tc++, 4, edges);
        }

        // TC 09: n=4 square with diagonal
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 2));
            edges.add(new Edge(1, 3, 2));
            edges.add(new Edge(0, 2, 2));
            edges.add(new Edge(2, 3, 2));
            edges.add(new Edge(0, 3, 4)); // diagonal also time 4
            writeTestCase(tc++, 4, edges);
        }

        // TC 10: n=5 simple cycle
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 1));
            edges.add(new Edge(1, 2, 1));
            edges.add(new Edge(2, 3, 1));
            edges.add(new Edge(3, 4, 1));
            edges.add(new Edge(4, 0, 1));
            writeTestCase(tc++, 5, edges);
        }

        // TC 11: Tree topology n=10 (always 1 way)
        {
            int n = 10;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) {
                int p = (i - 1) / 2;
                edges.add(new Edge(p, i, rng.nextInt(100) + 1));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 12: Star graph center at 0
        {
            int n = 15;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) edges.add(new Edge(0, i, i * 10));
            writeTestCase(tc++, n, edges);
        }

        // TC 13: Star graph center at n-1
        {
            int n = 15;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) edges.add(new Edge(i, n - 1, 100));
            edges.add(new Edge(0, 1, 50));
            writeTestCase(tc++, n, edges);
        }

        // TC 14: All edge weights near 10^9
        {
            int n = 5;
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 1000000000L));
            edges.add(new Edge(1, 4, 1000000000L));
            edges.add(new Edge(0, 2, 1000000000L));
            edges.add(new Edge(2, 4, 1000000000L));
            edges.add(new Edge(0, 3, 1000000000L));
            edges.add(new Edge(3, 4, 1000000000L));
            writeTestCase(tc++, n, edges);
        }

        // TC 15 - 24: Diamond chains (k diamonds produce 2^k paths)
        for (int k = 1; k <= 10; k++) {
            // each diamond adds 2 nodes and 4 edges
            // 0 -> (u1, u2) -> node ...
            int diamonds = k;
            List<Edge> edges = new ArrayList<>();
            // Cleaner diamond chain:
            // stage i goes from node S_i to S_{i+1} via two parallel paths A_i and B_i
            // Node 0 -> (1, 2) -> 3 -> (4, 5) -> 6 ...
            edges.clear();
            int cur = 0;
            int nextId = 1;
            for (int d = 0; d < diamonds; d++) {
                int a = nextId++;
                int b = nextId++;
                int target = (d == diamonds - 1) ? nextId++ : nextId++; // next target
                edges.add(new Edge(cur, a, 10));
                edges.add(new Edge(a, target, 10));
                edges.add(new Edge(cur, b, 10));
                edges.add(new Edge(b, target, 10));
                cur = target;
            }
            writeTestCase(tc++, nextId, edges);
        }

        // TC 25 - 34: Triple branch chains (3^k paths)
        for (int k = 1; k <= 10; k++) {
            int stages = k;
            List<Edge> edges = new ArrayList<>();
            int cur = 0;
            int nextId = 1;
            for (int s = 0; s < stages; s++) {
                int a = nextId++;
                int b = nextId++;
                int c = nextId++;
                int target = (s == stages - 1) ? nextId++ : nextId++;
                edges.add(new Edge(cur, a, 5));
                edges.add(new Edge(a, target, 5));
                edges.add(new Edge(cur, b, 5));
                edges.add(new Edge(b, target, 5));
                edges.add(new Edge(cur, c, 5));
                edges.add(new Edge(c, target, 5));
                cur = target;
            }
            writeTestCase(tc++, nextId, edges);
        }

        // TC 35 - 44: Grid / Mesh topologies (combinatorial paths from (0,0) to (R-1, C-1))
        for (int k = 0; k < 10; k++) {
            int R = 3 + k % 3;
            int C = 3 + k / 3;
            int n = R * C;
            List<Edge> edges = new ArrayList<>();
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    int u = r * C + c;
                    if (c + 1 < C) edges.add(new Edge(u, u + 1, 1));
                    if (r + 1 < R) edges.add(new Edge(u, u + C, 1));
                }
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 45 - 60: Medium randomized graphs (n: 30 to 80)
        for (int k = 0; k < 16; k++) {
            int n = 30 + k * 3;
            Set<Long> set = new HashSet<>();
            List<Edge> edges = new ArrayList<>();

            // Spanning tree to ensure connectivity
            for (int i = 1; i < n; i++) {
                int p = rng.nextInt(i);
                edges.add(new Edge(p, i, rng.nextInt(20) + 1));
                long key = ((long) Math.min(p, i) << 32) | Math.max(p, i);
                set.add(key);
            }

            // Extra edges
            int extra = Math.min(n * 2, n * (n - 1) / 2 - edges.size());
            while (extra > 0) {
                int u = rng.nextInt(n);
                int v = rng.nextInt(n);
                if (u != v) {
                    long key = ((long) Math.min(u, v) << 32) | Math.max(u, v);
                    if (!set.contains(key)) {
                        set.add(key);
                        edges.add(new Edge(u, v, rng.nextInt(20) + 1));
                        extra--;
                    }
                }
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 61 - 74: Graphs with multiple direct + multi-hop matching shortcuts
        for (int k = 0; k < 14; k++) {
            int n = 40 + k * 2;
            List<Edge> edges = new ArrayList<>();
            // backbone path 0 to n-1 with total cost 100
            for (int i = 0; i < n - 1; i++) edges.add(new Edge(i, i + 1, 10));
            // add direct edge of exact matching cost or larger
            int directCost = (k % 2 == 0) ? (n - 1) * 10 : (n - 1) * 10 + 50;
            edges.add(new Edge(0, n - 1, directCost));
            // add alternative bypasses
            for (int b = 1; b < 5; b++) {
                int mid = n / (b + 1);
                edges.add(new Edge(0, mid, mid * 10));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 75 - 84: High n graphs (n: 100 to 180)
        for (int k = 0; k < 10; k++) {
            int n = 100 + k * 8;
            Set<Long> set = new HashSet<>();
            List<Edge> edges = new ArrayList<>();

            for (int i = 1; i < n; i++) {
                int p = rng.nextInt(i);
                edges.add(new Edge(p, i, rng.nextInt(50) + 1));
                long key = ((long) Math.min(p, i) << 32) | Math.max(p, i);
                set.add(key);
            }

            int mTotal = Math.min(n * 3, n * (n - 1) / 2);
            while (edges.size() < mTotal) {
                int u = rng.nextInt(n);
                int v = rng.nextInt(n);
                if (u != v) {
                    long key = ((long) Math.min(u, v) << 32) | Math.max(u, v);
                    if (!set.contains(key)) {
                        set.add(key);
                        edges.add(new Edge(u, v, rng.nextInt(50) + 1));
                    }
                }
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 85 - 94: Diamond chains at large scale (producing huge modulo values)
        for (int k = 0; k < 10; k++) {
            int diamonds = 15 + k * 5; // up to 60 diamonds -> 2^60 paths!
            int cur = 0;
            int nextId = 1;
            List<Edge> edges = new ArrayList<>();
            for (int d = 0; d < diamonds; d++) {
                int a = nextId++;
                int b = nextId++;
                int target = nextId++;
                edges.add(new Edge(cur, a, 100));
                edges.add(new Edge(a, target, 100));
                edges.add(new Edge(cur, b, 100));
                edges.add(new Edge(b, target, 100));
                cur = target;
            }
            writeTestCase(tc++, nextId, edges);
        }

        // TC 95 - 99: Max scale stress tests (n = 200)
        // TC 95: n = 200 long chain with 10^9 weights
        {
            int n = 200;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) {
                edges.add(new Edge(i, i + 1, 1000000000L));
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 96: n = 200 dense grid 10 x 20
        {
            int R = 10, C = 20;
            int n = R * C;
            List<Edge> edges = new ArrayList<>();
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    int u = r * C + c;
                    if (c + 1 < C) edges.add(new Edge(u, u + 1, 10));
                    if (r + 1 < R) edges.add(new Edge(u, u + C, 10));
                }
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 97: n = 200 with 66 diamonds (2^66 paths modulo 10^9+7)
        {
            int diamonds = 66;
            int cur = 0;
            int nextId = 1;
            List<Edge> edges = new ArrayList<>();
            for (int d = 0; d < diamonds; d++) {
                int a = nextId++;
                int b = nextId++;
                int target = nextId++;
                edges.add(new Edge(cur, a, 5));
                edges.add(new Edge(a, target, 5));
                edges.add(new Edge(cur, b, 5));
                edges.add(new Edge(b, target, 5));
                cur = target;
            }
            writeTestCase(tc++, nextId, edges);
        }

        // TC 98: n = 200 complete graph (m = 19900 edges) with all weights 1
        {
            int n = 200;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    edges.add(new Edge(i, j, 1));
                }
            }
            writeTestCase(tc++, n, edges);
        }

        // TC 99: n = 200 dense random graph
        {
            int n = 200;
            Set<Long> set = new HashSet<>();
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) {
                int p = rng.nextInt(i);
                edges.add(new Edge(p, i, rng.nextInt(100) + 1));
                set.add(((long) Math.min(p, i) << 32) | Math.max(p, i));
            }
            for (int k = 0; k < 2000; k++) {
                int u = rng.nextInt(n);
                int v = rng.nextInt(n);
                if (u != v) {
                    long key = ((long) Math.min(u, v) << 32) | Math.max(u, v);
                    if (!set.contains(key)) {
                        set.add(key);
                        edges.add(new Edge(u, v, rng.nextInt(100) + 1));
                    }
                }
            }
            writeTestCase(tc++, n, edges);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Number of Ways to Arrive at Destination.");
    }
}
