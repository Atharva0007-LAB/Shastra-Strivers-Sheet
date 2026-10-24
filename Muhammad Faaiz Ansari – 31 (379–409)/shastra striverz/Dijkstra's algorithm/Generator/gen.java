import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static class Edge {
        int u, v, wt;
        Edge(int u, int v, int wt) {
            this.u = u;
            this.v = v;
            this.wt = wt;
        }
    }

    static class AdjEdge {
        int to, wt;
        AdjEdge(int to, int wt) {
            this.to = to;
            this.wt = wt;
        }
    }

    static class Node implements Comparable<Node> {
        int u, d;
        Node(int u, int d) {
            this.u = u;
            this.d = d;
        }
        public int compareTo(Node o) {
            return Integer.compare(this.d, o.d);
        }
    }

    @SuppressWarnings("unchecked")
    private static int[] solve(int V, List<Edge> edges, int S) {
        List<AdjEdge>[] adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }

        for (Edge e : edges) {
            adj[e.u].add(new AdjEdge(e.v, e.wt));
            adj[e.v].add(new AdjEdge(e.u, e.wt));
        }

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

            for (AdjEdge e : adj[u]) {
                if (dist[u] + e.wt < dist[e.to]) {
                    dist[e.to] = dist[u] + e.wt;
                    pq.add(new Node(e.to, dist[e.to]));
                }
            }
        }

        return dist;
    }

    private static void writeTestCase(File inDir, File outDir, int index, int V, List<Edge> edges, int S) throws Exception {
        String filename = String.format("%02d.txt", index);
        File inFile = new File(inDir, "input" + filename);
        File outFile = new File(outDir, "output" + filename);

        try (PrintWriter inPw = new PrintWriter(inFile)) {
            inPw.println(V + " " + edges.size() + " " + S);
            for (Edge e : edges) {
                inPw.println(e.u + " " + e.v + " " + e.wt);
            }
        }

        int[] dist = solve(V, edges, S);
        try (PrintWriter outPw = new PrintWriter(outFile)) {
            for (int i = 0; i < V; i++) {
                outPw.print(dist[i]);
                if (i + 1 < V) {
                    outPw.print(" ");
                }
            }
            outPw.println();
        }
    }

    private static List<Edge> generateGraph(int V, int targetE, boolean connectS, int S, Random rng) {
        List<Edge> edges = new ArrayList<>();
        if (V <= 1 || targetE <= 0) return edges;

        Set<Long> set = new HashSet<>();
        long maxPossible = ((long) V * (V - 1)) / 2;
        int actualE = (int) Math.min(targetE, Math.min(maxPossible, 20000));

        List<Integer> nodes = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (connectS || i != S) {
                nodes.add(i);
            }
        }

        int avail = nodes.size();
        if (avail >= 2 && actualE >= avail - 1 && rng.nextBoolean()) {
            for (int i = 0; i < avail - 1 && edges.size() < actualE; i++) {
                int u = nodes.get(i);
                int v = nodes.get(i + 1);
                int wt = 1 + rng.nextInt(100);
                long key = (((long) Math.min(u, v)) << 32) | (Math.max(u, v) & 0xFFFFFFFFL);
                if (set.add(key)) {
                    edges.add(new Edge(u, v, wt));
                }
            }
        }

        int attempts = 0;
        while (edges.size() < actualE && attempts < actualE * 15) {
            attempts++;
            int u = nodes.get(rng.nextInt(avail));
            int v = nodes.get(rng.nextInt(avail));
            if (u == v) continue;
            long key = (((long) Math.min(u, v)) << 32) | (Math.max(u, v) & 0xFFFFFFFFL);
            if (set.add(key)) {
                edges.add(new Edge(u, v, 1 + rng.nextInt(1000)));
            }
        }

        return edges;
    }

    public static void main(String[] args) {
        try {
            File baseDir = new File("d:/MF Shastra/shastra striverz/Dijkstra's algorithm/Testcases");
            File inDir = new File(baseDir, "Input");
            File outDir = new File(baseDir, "Output");

            inDir.mkdirs();
            outDir.mkdirs();

            Random rng = new Random(5202026L);

            // 00: Sample 1
            writeTestCase(inDir, outDir, 0, 2, Collections.singletonList(new Edge(0, 1, 9)), 0);

            // 01: Sample 2
            writeTestCase(inDir, outDir, 1, 3, Arrays.asList(
                new Edge(0, 1, 1), new Edge(0, 2, 6), new Edge(1, 2, 3)
            ), 2);

            // 02: Sample 3
            writeTestCase(inDir, outDir, 2, 4, Arrays.asList(
                new Edge(0, 1, 1), new Edge(0, 3, 2), new Edge(1, 2, 4), new Edge(2, 3, 3)
            ), 0);

            // 03: Minimal boundary V=1, E=0, S=0
            writeTestCase(inDir, outDir, 3, 1, Collections.emptyList(), 0);

            // 04: Minimal boundary V=2, E=0, S=0
            writeTestCase(inDir, outDir, 4, 2, Collections.emptyList(), 0);

            // 05: Minimal boundary V=2, E=1, S=1
            writeTestCase(inDir, outDir, 5, 2, Collections.singletonList(new Edge(0, 1, 7)), 1);

            // 06: Minimal boundary V=3, E=0, S=1
            writeTestCase(inDir, outDir, 6, 3, Collections.emptyList(), 1);

            // 07: Minimal boundary V=3, E=1, S=0
            writeTestCase(inDir, outDir, 7, 3, Collections.singletonList(new Edge(0, 1, 4)), 0);

            // 08: Minimal boundary V=3, E=2, S=0 (0-1, 1-2)
            writeTestCase(inDir, outDir, 8, 3, Arrays.asList(
                new Edge(0, 1, 5), new Edge(1, 2, 6)
            ), 0);

            // 09: Triangle with shortcut (direct wt 10, 2-hop wt 2+3=5)
            writeTestCase(inDir, outDir, 9, 3, Arrays.asList(
                new Edge(0, 1, 2), new Edge(1, 2, 3), new Edge(0, 2, 10)
            ), 0);

            // 10: V=4 Diamond with shortcut
            writeTestCase(inDir, outDir, 10, 4, Arrays.asList(
                new Edge(0, 1, 10), new Edge(1, 3, 15), new Edge(0, 3, 100), new Edge(0, 2, 30)
            ), 0);

            // 11: V=4 Disjoint {0-1} and {2-3}, S=0
            writeTestCase(inDir, outDir, 11, 4, Arrays.asList(
                new Edge(0, 1, 5), new Edge(2, 3, 8)
            ), 0);

            // 12: V=5 Star centered at S=0
            writeTestCase(inDir, outDir, 12, 5, Arrays.asList(
                new Edge(0, 1, 2), new Edge(0, 2, 4), new Edge(0, 3, 6), new Edge(0, 4, 8)
            ), 0);

            // 13: V=5 Star centered at 0, S=4
            writeTestCase(inDir, outDir, 13, 5, Arrays.asList(
                new Edge(0, 1, 2), new Edge(0, 2, 4), new Edge(0, 3, 6), new Edge(0, 4, 8)
            ), 4);

            // 14: V=6 Ring graph
            writeTestCase(inDir, outDir, 14, 6, Arrays.asList(
                new Edge(0, 1, 1), new Edge(1, 2, 1), new Edge(2, 3, 1),
                new Edge(3, 4, 1), new Edge(4, 5, 1), new Edge(5, 0, 10)
            ), 0);

            // 15 - 34: Medium graphs (V = 10..100)
            for (int tc = 15; tc <= 34; tc++) {
                int V = 10 + (tc - 15) * 4 + rng.nextInt(5);
                int E = rng.nextInt(Math.min(V * 3 + 10, 300));
                int S = rng.nextInt(V);
                boolean connectS = (tc % 4 != 0);
                writeTestCase(inDir, outDir, tc, V, generateGraph(V, E, connectS, S, rng), S);
            }

            // 35 - 69: Large graphs (V = 150..1500)
            for (int tc = 35; tc <= 69; tc++) {
                int V = 150 + (tc - 35) * 35 + rng.nextInt(20);
                int maxE = Math.min(4000, V * 3);
                int E = 50 + rng.nextInt(maxE);
                int S = rng.nextInt(V);
                boolean connectS = (tc % 5 != 0);
                writeTestCase(inDir, outDir, tc, V, generateGraph(V, E, connectS, S, rng), S);
            }

            // 70 - 89: Very Large graphs (V = 2000..6000)
            for (int tc = 70; tc <= 89; tc++) {
                int V = 2000 + (tc - 70) * 200 + rng.nextInt(50);
                int E = 1000 + rng.nextInt(11000);
                int S = rng.nextInt(V);
                boolean connectS = (tc % 4 != 0);
                writeTestCase(inDir, outDir, tc, V, generateGraph(V, E, connectS, S, rng), S);
            }

            // 90 - 99: Extreme Maximum Constraints (V = 10000, E up to 20000)
            // 90: V=10000, E=0, S=5000
            writeTestCase(inDir, outDir, 90, 10000, Collections.emptyList(), 5000);

            // 91: V=10000, E=9999, S=0 (single long chain)
            List<Edge> e91 = new ArrayList<>();
            for (int i = 0; i < 9999; i++) {
                e91.add(new Edge(i, i + 1, 1));
            }
            writeTestCase(inDir, outDir, 91, 10000, e91, 0);

            // 92: V=10000, E=9999, S=9999 (reverse chain query)
            writeTestCase(inDir, outDir, 92, 10000, e91, 9999);

            // 93: V=10000, E=9998, S=0 (0 disconnected from chain 1..9999)
            List<Edge> e93 = new ArrayList<>();
            for (int i = 1; i < 9999; i++) {
                e93.add(new Edge(i, i + 1, 1));
            }
            writeTestCase(inDir, outDir, 93, 10000, e93, 0);

            // 94: V=10000, E=9999, S=0 (star: 0 to all)
            List<Edge> e94 = new ArrayList<>();
            for (int i = 1; i < 10000; i++) {
                e94.add(new Edge(0, i, (i % 100) + 1));
            }
            writeTestCase(inDir, outDir, 94, 10000, e94, 0);

            // 95: V=10000, E=15000, S=0 (chain + shortcuts)
            List<Edge> e95 = new ArrayList<>(e91);
            Set<Long> set95 = new HashSet<>();
            for (Edge e : e95) {
                set95.add((((long) Math.min(e.u, e.v)) << 32) | (Math.max(e.u, e.v) & 0xFFFFFFFFL));
            }
            while (e95.size() < 15000) {
                int u = rng.nextInt(9995);
                int v = u + 2 + rng.nextInt(Math.min(50, 10000 - u - 2));
                long key = (((long) Math.min(u, v)) << 32) | (Math.max(u, v) & 0xFFFFFFFFL);
                if (set95.add(key)) {
                    e95.add(new Edge(u, v, 1 + rng.nextInt(50)));
                }
            }
            writeTestCase(inDir, outDir, 95, 10000, e95, 0);

            // 96: V=10000, E=9999, S=0 (binary tree)
            List<Edge> e96 = new ArrayList<>();
            for (int i = 0; i < 5000; i++) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                if (left < 10000) e96.add(new Edge(i, left, (i % 10) + 1));
                if (right < 10000) e96.add(new Edge(i, right, (i % 10) + 2));
            }
            writeTestCase(inDir, outDir, 96, 10000, e96, 0);

            // 97: V=10000, E=15000, S=1234
            writeTestCase(inDir, outDir, 97, 10000, generateGraph(10000, 15000, true, 1234, rng), 1234);

            // 98: V=10000, E=20000, S=0
            writeTestCase(inDir, outDir, 98, 10000, generateGraph(10000, 20000, true, 0, rng), 0);

            // 99: V=10000, E=20000, S=7777 (S disconnected)
            writeTestCase(inDir, outDir, 99, 10000, generateGraph(10000, 20000, false, 7777, rng), 7777);

            System.out.println("SUCCESS: Generated 100 testcases for Dijkstra's Algorithm.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
