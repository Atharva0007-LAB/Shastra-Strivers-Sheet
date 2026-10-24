import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static class Edge {
        int u, v;
        Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }

    @SuppressWarnings("unchecked")
    private static int[] solve(int N, List<Edge> edges) {
        List<Integer>[] adj = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adj[i] = new ArrayList<>();
        }

        for (Edge e : edges) {
            adj[e.u].add(e.v);
            adj[e.v].add(e.u);
        }

        int[] dist = new int[N];
        Arrays.fill(dist, -1);
        dist[0] = 0;

        int[] queue = new int[N];
        int head = 0;
        int tail = 0;
        queue[tail++] = 0;

        while (head < tail) {
            int u = queue[head++];
            int d = dist[u] + 1;

            for (int v : adj[u]) {
                if (dist[v] == -1) {
                    dist[v] = d;
                    queue[tail++] = v;
                }
            }
        }

        return dist;
    }

    private static void writeTestCase(File inDir, File outDir, int index, int N, List<Edge> edges) throws Exception {
        String filename = String.format("%02d.txt", index);
        File inFile = new File(inDir, "input" + filename);
        File outFile = new File(outDir, "output" + filename);

        try (PrintWriter inPw = new PrintWriter(inFile)) {
            inPw.println(N + " " + edges.size());
            for (Edge e : edges) {
                inPw.println(e.u + " " + e.v);
            }
        }

        int[] dist = solve(N, edges);
        try (PrintWriter outPw = new PrintWriter(outFile)) {
            for (int i = 0; i < N; i++) {
                outPw.print(dist[i]);
                if (i + 1 < N) {
                    outPw.print(" ");
                }
            }
            outPw.println();
        }
    }

    private static List<Edge> generateGraph(int N, int targetM, boolean connectZero, Random rng) {
        List<Edge> edges = new ArrayList<>();
        if (N <= 1 || targetM <= 0) return edges;

        Set<Long> set = new HashSet<>();
        long maxPossible = ((long) N * (N - 1)) / 2;
        int actualM = (int) Math.min(targetM, Math.min(maxPossible, 20000));

        int startNode = connectZero ? 0 : 1;
        int availableNodes = N - startNode;

        if (availableNodes >= 2 && actualM >= availableNodes - 1 && rng.nextBoolean()) {
            for (int i = startNode; i < N - 1 && edges.size() < actualM; i++) {
                int u = i;
                int v = i + 1;
                long key = (((long) Math.min(u, v)) << 32) | (Math.max(u, v) & 0xFFFFFFFFL);
                if (set.add(key)) {
                    edges.add(new Edge(u, v));
                }
            }
        }

        int attempts = 0;
        while (edges.size() < actualM && attempts < actualM * 15) {
            attempts++;
            int u = startNode + rng.nextInt(availableNodes);
            int v = startNode + rng.nextInt(availableNodes);
            if (u == v) continue;
            long key = (((long) Math.min(u, v)) << 32) | (Math.max(u, v) & 0xFFFFFFFFL);
            if (set.add(key)) {
                edges.add(new Edge(u, v));
            }
        }

        return edges;
    }

    public static void main(String[] args) {
        try {
            File baseDir = new File("d:/MF Shastra/shastra striverz/Shortest path in undirected graph with unit weights/Testcases");
            File inDir = new File(baseDir, "Input");
            File outDir = new File(baseDir, "Output");

            inDir.mkdirs();
            outDir.mkdirs();

            Random rng = new Random(5082026L);

            // 00: Sample 1
            writeTestCase(inDir, outDir, 0, 9, Arrays.asList(
                new Edge(0, 1), new Edge(0, 3), new Edge(3, 4), new Edge(4, 5),
                new Edge(5, 6), new Edge(1, 2), new Edge(2, 6), new Edge(6, 7),
                new Edge(7, 8), new Edge(6, 8)
            ));

            // 01: Sample 2
            writeTestCase(inDir, outDir, 1, 8, Arrays.asList(
                new Edge(1, 0), new Edge(2, 1), new Edge(0, 3), new Edge(3, 7),
                new Edge(3, 4), new Edge(7, 4), new Edge(7, 6), new Edge(4, 5),
                new Edge(4, 6), new Edge(6, 5)
            ));

            // 02: Sample 3 (0 is disconnected)
            writeTestCase(inDir, outDir, 2, 3, Collections.singletonList(new Edge(1, 2)));

            // 03: Minimal boundary N=1, M=0
            writeTestCase(inDir, outDir, 3, 1, Collections.emptyList());

            // 04: Minimal boundary N=2, M=0
            writeTestCase(inDir, outDir, 4, 2, Collections.emptyList());

            // 05: Minimal boundary N=2, M=1 (0 - 1)
            writeTestCase(inDir, outDir, 5, 2, Collections.singletonList(new Edge(0, 1)));

            // 06: N=3, M=0
            writeTestCase(inDir, outDir, 6, 3, Collections.emptyList());

            // 07: N=3, M=1 (0 - 1)
            writeTestCase(inDir, outDir, 7, 3, Collections.singletonList(new Edge(0, 1)));

            // 08: N=3, M=2 (0 - 1, 1 - 2)
            writeTestCase(inDir, outDir, 8, 3, Arrays.asList(new Edge(0, 1), new Edge(1, 2)));

            // 09: N=3, M=3 (triangle 0-1-2-0)
            writeTestCase(inDir, outDir, 9, 3, Arrays.asList(
                new Edge(0, 1), new Edge(1, 2), new Edge(2, 0)
            ));

            // 10: N=4 Diamond graph
            writeTestCase(inDir, outDir, 10, 4, Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(1, 3), new Edge(2, 3)
            ));

            // 11: N=4 Disjoint {0 - 1} and {2 - 3}
            writeTestCase(inDir, outDir, 11, 4, Arrays.asList(new Edge(0, 1), new Edge(2, 3)));

            // 12: N=5 Star centered at 0
            writeTestCase(inDir, outDir, 12, 5, Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(0, 3), new Edge(0, 4)
            ));

            // 13: N=5 Star centered at 4 (0-4, 1-4, 2-4, 3-4)
            writeTestCase(inDir, outDir, 13, 5, Arrays.asList(
                new Edge(0, 4), new Edge(1, 4), new Edge(2, 4), new Edge(3, 4)
            ));

            // 14: N=6 Cycle (0-1-2-3-4-5-0)
            writeTestCase(inDir, outDir, 14, 6, Arrays.asList(
                new Edge(0, 1), new Edge(1, 2), new Edge(2, 3),
                new Edge(3, 4), new Edge(4, 5), new Edge(5, 0)
            ));

            // 15 - 34: Medium graphs (N = 10..100)
            for (int tc = 15; tc <= 34; tc++) {
                int N = 10 + (tc - 15) * 4 + rng.nextInt(5);
                int M = rng.nextInt(Math.min(N * 3 + 10, 300));
                boolean connectZero = (tc % 4 != 0);
                writeTestCase(inDir, outDir, tc, N, generateGraph(N, M, connectZero, rng));
            }

            // 35 - 69: Large graphs (N = 150..1500)
            for (int tc = 35; tc <= 69; tc++) {
                int N = 150 + (tc - 35) * 35 + rng.nextInt(20);
                int maxM = Math.min(4000, N * 3);
                int M = 50 + rng.nextInt(maxM);
                boolean connectZero = (tc % 5 != 0);
                writeTestCase(inDir, outDir, tc, N, generateGraph(N, M, connectZero, rng));
            }

            // 70 - 89: Very Large graphs (N = 2000..6000)
            for (int tc = 70; tc <= 89; tc++) {
                int N = 2000 + (tc - 70) * 200 + rng.nextInt(50);
                int M = 1000 + rng.nextInt(11000);
                boolean connectZero = (tc % 4 != 0);
                writeTestCase(inDir, outDir, tc, N, generateGraph(N, M, connectZero, rng));
            }

            // 90 - 99: Extreme Maximum Constraints (N = 10000, M up to 20000)
            // 90: N=10000, M=0
            writeTestCase(inDir, outDir, 90, 10000, Collections.emptyList());

            // 91: N=10000, M=9999 (single long chain 0 - 1 - ... - 9999)
            List<Edge> e91 = new ArrayList<>();
            for (int i = 0; i < 9999; i++) {
                e91.add(new Edge(i, i + 1));
            }
            writeTestCase(inDir, outDir, 91, 10000, e91);

            // 92: N=10000, M=9998 (chain 1 - 2 - ... - 9999, 0 disconnected)
            List<Edge> e92 = new ArrayList<>();
            for (int i = 1; i < 9999; i++) {
                e92.add(new Edge(i, i + 1));
            }
            writeTestCase(inDir, outDir, 92, 10000, e92);

            // 93: N=10000, M=9999 (star: 0 connected to all 9999)
            List<Edge> e93 = new ArrayList<>();
            for (int i = 1; i < 10000; i++) {
                e93.add(new Edge(0, i));
            }
            writeTestCase(inDir, outDir, 93, 10000, e93);

            // 94: N=10000, M=9999 (star: node 9999 connected to all)
            List<Edge> e94 = new ArrayList<>();
            for (int i = 0; i < 9999; i++) {
                e94.add(new Edge(i, 9999));
            }
            writeTestCase(inDir, outDir, 94, 10000, e94);

            // 95: N=10000, M=15000 (long chain + shortcuts)
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
                    e95.add(new Edge(u, v));
                }
            }
            writeTestCase(inDir, outDir, 95, 10000, e95);

            // 96: N=10000, M=9999 (binary tree from 0)
            List<Edge> e96 = new ArrayList<>();
            for (int i = 0; i < 5000; i++) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                if (left < 10000) e96.add(new Edge(i, left));
                if (right < 10000) e96.add(new Edge(i, right));
            }
            writeTestCase(inDir, outDir, 96, 10000, e96);

            // 97: N=10000, M=15000 (connected graph)
            writeTestCase(inDir, outDir, 97, 10000, generateGraph(10000, 15000, true, rng));

            // 98: N=10000, M=20000 (dense connected graph)
            writeTestCase(inDir, outDir, 98, 10000, generateGraph(10000, 20000, true, rng));

            // 99: N=10000, M=20000 (dense multi-component graph with disconnected zero)
            writeTestCase(inDir, outDir, 99, 10000, generateGraph(10000, 20000, false, rng));

            System.out.println("SUCCESS: Generated 100 testcases for Shortest Path in Undirected Graph with Unit Weights.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
