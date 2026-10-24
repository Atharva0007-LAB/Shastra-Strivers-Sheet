import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static class Edge {
        int u, v, price;
        Edge(int u, int v, int price) {
            this.u = u;
            this.v = v;
            this.price = price;
        }
    }

    static class Tuple {
        int stops, node, cost;
        Tuple(int stops, int node, int cost) {
            this.stops = stops;
            this.node = node;
            this.cost = cost;
        }
    }

    @SuppressWarnings("unchecked")
    private static int solve(int n, int m, int src, int dst, int K, List<Edge> edges) {
        if (src == dst) return 0;

        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();

        for (Edge e : edges) {
            if (e.u >= 0 && e.u < n && e.v >= 0 && e.v < n) {
                adj[e.u].add(new int[]{e.v, e.price});
            }
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

            for (int[] edge : adj[u]) {
                int v = edge[0];
                int price = edge[1];

                if (cost + price < dist[v]) {
                    dist[v] = cost + price;
                    queue.add(new Tuple(stops + 1, v, cost + price));
                }
            }
        }
        return dist[dst] == INF ? -1 : dist[dst];
    }

    private static void writeTestCase(int index, int n, int src, int dst, int K, List<Edge> edges) throws Exception {
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
            pw.println(src + " " + dst + " " + K);
            for (Edge e : edges) {
                pw.println(e.u + " " + e.v + " " + e.price);
            }
        }

        int ans = solve(n, m, src, dst, K, edges);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5192026L);
        int tc = 0;

        // Sample 1: n=4, src=0, dst=3, K=1
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 100));
            edges.add(new Edge(1, 2, 100));
            edges.add(new Edge(2, 0, 100));
            edges.add(new Edge(1, 3, 600));
            edges.add(new Edge(2, 3, 200));
            writeTestCase(tc++, 4, 0, 3, 1, edges);
        }

        // Sample 2: n=3, src=0, dst=2, K=1
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 100));
            edges.add(new Edge(1, 2, 100));
            edges.add(new Edge(0, 2, 500));
            writeTestCase(tc++, 3, 0, 2, 1, edges);
        }

        // Sample 3: n=3, src=0, dst=2, K=0
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 100));
            edges.add(new Edge(1, 2, 100));
            edges.add(new Edge(0, 2, 500));
            writeTestCase(tc++, 3, 0, 2, 0, edges);
        }

        // TC 03: Minimal n=1, m=0, src=0, dst=0, K=0
        {
            writeTestCase(tc++, 1, 0, 0, 0, new ArrayList<>());
        }

        // TC 04: Minimal n=2, m=0, src=0, dst=1, K=0
        {
            writeTestCase(tc++, 2, 0, 1, 0, new ArrayList<>());
        }

        // TC 05: n=2, m=1, direct flight, K=0
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 450));
            writeTestCase(tc++, 2, 0, 1, 0, edges);
        }

        // TC 06: n=2, m=1 reverse flight, K=1
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(1, 0, 450));
            writeTestCase(tc++, 2, 0, 1, 1, edges);
        }

        // TC 07: n=3 line 0->1->2, K=0 (unreachable)
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 100));
            edges.add(new Edge(1, 2, 200));
            writeTestCase(tc++, 3, 0, 2, 0, edges);
        }

        // TC 08: n=3 line 0->1->2, K=1 (reachable)
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 100));
            edges.add(new Edge(1, 2, 200));
            writeTestCase(tc++, 3, 0, 2, 1, edges);
        }

        // TC 09: n=3 line 0->1->2, K=2 (reachable)
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 100));
            edges.add(new Edge(1, 2, 200));
            writeTestCase(tc++, 3, 0, 2, 2, edges);
        }

        // TC 10: n=5, src == dst
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 50));
            edges.add(new Edge(1, 2, 50));
            writeTestCase(tc++, 5, 3, 3, 0, edges);
        }

        // TC 11: Cheaper path uses 2 stops, expensive path uses 1 stop, K=1
        {
            List<Edge> edges = new ArrayList<>();
            // expensive: 0 -> 1 -> 3 (cost 1000 + 1000 = 2000, 1 stop)
            edges.add(new Edge(0, 1, 1000));
            edges.add(new Edge(1, 3, 1000));
            // cheap: 0 -> 4 -> 2 -> 3 (cost 10 + 10 + 10 = 30, 2 stops)
            edges.add(new Edge(0, 4, 10));
            edges.add(new Edge(4, 2, 10));
            edges.add(new Edge(2, 3, 10));
            writeTestCase(tc++, 5, 0, 3, 1, edges);
        }

        // TC 12: Cycle with no effect
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 50));
            edges.add(new Edge(1, 2, 50));
            edges.add(new Edge(2, 1, 50)); // cycle between 1 and 2
            edges.add(new Edge(2, 3, 50));
            writeTestCase(tc++, 4, 0, 3, 2, edges);
        }

        // TC 13: Disconnected components
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 100));
            edges.add(new Edge(1, 2, 100));
            edges.add(new Edge(3, 4, 100));
            writeTestCase(tc++, 5, 0, 4, 3, edges);
        }

        // TC 14: Star graph out of src
        {
            int n = 10;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < n; i++) edges.add(new Edge(0, i, i * 100));
            writeTestCase(tc++, n, 0, n - 1, 0, edges);
        }

        // TC 15 - 24: Line graphs of length 15 to 25 with varying K
        for (int k = 0; k < 10; k++) {
            int n = 15 + k;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) edges.add(new Edge(i, i + 1, rng.nextInt(50) + 10));
            int K = (k % 2 == 0) ? n - 2 : n / 2; // half reachable, half unreachable
            writeTestCase(tc++, n, 0, n - 1, K, edges);
        }

        // TC 25 - 34: Competing multi-hop paths (1-stop, 2-stop, 3-stop, ...)
        for (int k = 0; k < 10; k++) {
            int n = 12;
            List<Edge> edges = new ArrayList<>();
            // direct edge: cost 5000
            edges.add(new Edge(0, n - 1, 5000));
            // 2-hop (1 stop): cost 2000
            edges.add(new Edge(0, 1, 1000));
            edges.add(new Edge(1, n - 1, 1000));
            // 3-hop (2 stops): cost 600
            edges.add(new Edge(0, 2, 200));
            edges.add(new Edge(2, 3, 200));
            edges.add(new Edge(3, n - 1, 200));
            // 4-hop (3 stops): cost 200
            edges.add(new Edge(0, 4, 50));
            edges.add(new Edge(4, 5, 50));
            edges.add(new Edge(5, 6, 50));
            edges.add(new Edge(6, n - 1, 50));

            int K = k % 4; // K from 0 to 3
            writeTestCase(tc++, n, 0, n - 1, K, edges);
        }

        // TC 35 - 44: Complete DAGs where edge u -> v exists for all u < v
        for (int k = 0; k < 10; k++) {
            int n = 10 + k;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    edges.add(new Edge(i, j, rng.nextInt(1000) + 1));
                }
            }
            int K = rng.nextInt(3) + 1; // 1 to 3 stops
            writeTestCase(tc++, n, 0, n - 1, K, edges);
        }

        // TC 45 - 60: Medium randomized graphs (n: 30 to 60)
        for (int k = 0; k < 16; k++) {
            int n = 30 + k * 2;
            int m = Math.min(n * (n - 1) / 2, 50 + k * 25);
            Set<Long> set = new HashSet<>();
            List<Edge> edges = new ArrayList<>();

            while (edges.size() < m) {
                int u = rng.nextInt(n);
                int v = rng.nextInt(n);
                if (u != v) {
                    long key = ((long) u << 32) | v;
                    if (!set.contains(key)) {
                        set.add(key);
                        edges.add(new Edge(u, v, rng.nextInt(5000) + 1));
                    }
                }
            }
            int src = rng.nextInt(n);
            int dst = (src + rng.nextInt(n - 1) + 1) % n;
            int K = rng.nextInt(n / 2) + 1;
            writeTestCase(tc++, n, src, dst, K, edges);
        }

        // TC 61 - 74: Layered graphs (src -> layer 1 -> layer 2 ... -> dst)
        for (int k = 0; k < 14; k++) {
            int layers = 5 + k / 2;
            int perLayer = 4;
            int n = layers * perLayer + 2;
            int src = 0;
            int dst = n - 1;
            List<Edge> edges = new ArrayList<>();

            // connect src to layer 0
            for (int j = 1; j <= perLayer; j++) edges.add(new Edge(src, j, rng.nextInt(200) + 1));

            // layer to layer
            for (int l = 0; l < layers - 1; l++) {
                int startA = 1 + l * perLayer;
                int startB = startA + perLayer;
                for (int a = 0; a < perLayer; a++) {
                    for (int b = 0; b < perLayer; b++) {
                        edges.add(new Edge(startA + a, startB + b, rng.nextInt(300) + 1));
                    }
                }
            }

            // last layer to dst
            int lastStart = 1 + (layers - 1) * perLayer;
            for (int j = 0; j < perLayer; j++) edges.add(new Edge(lastStart + j, dst, rng.nextInt(200) + 1));

            // Add some skip-layer edges
            for (int s = 0; s < 10; s++) {
                int u = rng.nextInt(n - 2) + 1;
                int v = u + perLayer + rng.nextInt(perLayer);
                if (v < dst) edges.add(new Edge(u, v, rng.nextInt(1000) + 100));
            }

            int K = layers / 2 + (k % 4);
            writeTestCase(tc++, n, src, dst, K, edges);
        }

        // TC 75 - 84: High n graphs (n: 80 to 100) with small K
        for (int k = 0; k < 10; k++) {
            int n = 80 + k * 2;
            int m = 500 + k * 50;
            Set<Long> set = new HashSet<>();
            List<Edge> edges = new ArrayList<>();

            while (edges.size() < m) {
                int u = rng.nextInt(n);
                int v = rng.nextInt(n);
                if (u != v) {
                    long key = ((long) u << 32) | v;
                    if (!set.contains(key)) {
                        set.add(key);
                        edges.add(new Edge(u, v, rng.nextInt(10000) + 1));
                    }
                }
            }
            int src = 0;
            int dst = n - 1;
            int K = rng.nextInt(4) + 1; // tight K (1 to 4)
            writeTestCase(tc++, n, src, dst, K, edges);
        }

        // TC 85 - 94: High n graphs with large K (K: 30 to 90)
        for (int k = 0; k < 10; k++) {
            int n = 90 + k;
            int m = 800 + k * 50;
            Set<Long> set = new HashSet<>();
            List<Edge> edges = new ArrayList<>();

            while (edges.size() < m) {
                int u = rng.nextInt(n);
                int v = rng.nextInt(n);
                if (u != v) {
                    long key = ((long) u << 32) | v;
                    if (!set.contains(key)) {
                        set.add(key);
                        edges.add(new Edge(u, v, rng.nextInt(500) + 1));
                    }
                }
            }
            int src = 0;
            int dst = n - 1;
            int K = 20 + k * 7;
            writeTestCase(tc++, n, src, dst, K, edges);
        }

        // TC 95 - 99: Max constraint stress tests (n = 100, max edges up to 4950)
        // TC 95: n=100, complete directed graph (4950 edges), K=1
        {
            int n = 100;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j) edges.add(new Edge(i, j, rng.nextInt(10000) + 1));
                }
            }
            writeTestCase(tc++, n, 0, 99, 1, edges);
        }

        // TC 96: n=100, complete directed graph, K=0 (direct flights only)
        {
            int n = 100;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j) edges.add(new Edge(i, j, rng.nextInt(10000) + 1));
                }
            }
            writeTestCase(tc++, n, 0, 99, 0, edges);
        }

        // TC 97: n=100, complete directed graph, max K=99
        {
            int n = 100;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j) edges.add(new Edge(i, j, rng.nextInt(10000) + 1));
                }
            }
            writeTestCase(tc++, n, 0, 99, 99, edges);
        }

        // TC 98: n=100, long chain 0->1->...->99, K=98 vs direct expensive edge
        {
            int n = 100;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) edges.add(new Edge(i, i + 1, 10));
            edges.add(new Edge(0, 99, 10000));
            writeTestCase(tc++, n, 0, 99, 50, edges); // K=50 cannot finish chain of 98 stops, so must take direct edge!
        }

        // TC 99: n=100, disconnected halves (0..49 and 50..99), K=99 -> -1
        {
            int n = 100;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    if (i != j && rng.nextDouble() < 0.4) edges.add(new Edge(i, j, rng.nextInt(500) + 1));
                }
            }
            for (int i = 50; i < 100; i++) {
                for (int j = 50; j < 100; j++) {
                    if (i != j && rng.nextDouble() < 0.4) edges.add(new Edge(i, j, rng.nextInt(500) + 1));
                }
            }
            writeTestCase(tc++, n, 0, 99, 99, edges);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Cheapest Flight Within K Stops.");
    }
}
