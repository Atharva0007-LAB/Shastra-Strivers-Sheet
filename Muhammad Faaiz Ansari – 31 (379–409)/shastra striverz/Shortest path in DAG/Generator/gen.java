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

    @SuppressWarnings("unchecked")
    private static long[] solve(int N, List<Edge> edges) {
        List<AdjEdge>[] adj = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adj[i] = new ArrayList<>();
        }
        int[] inDegree = new int[N];

        for (Edge e : edges) {
            adj[e.u].add(new AdjEdge(e.v, e.wt));
            inDegree[e.v]++;
        }

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
            for (AdjEdge e : adj[u]) {
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
                for (AdjEdge e : adj[u]) {
                    if (dist[u] + e.wt < dist[e.to]) {
                        dist[e.to] = dist[u] + e.wt;
                    }
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
                inPw.println(e.u + " " + e.v + " " + e.wt);
            }
        }

        long[] dist = solve(N, edges);
        long INF = Long.MAX_VALUE / 2;
        try (PrintWriter outPw = new PrintWriter(outFile)) {
            for (int i = 0; i < N; i++) {
                outPw.print(dist[i] >= INF ? -1 : dist[i]);
                if (i + 1 < N) {
                    outPw.print(" ");
                }
            }
            outPw.println();
        }
    }

    private static List<Edge> generateDAG(int N, int targetM, boolean sourceAtZero, Random rng) {
        List<Edge> edges = new ArrayList<>();
        if (N <= 1 || targetM <= 0) return edges;

        int[] perm = new int[N];
        for (int i = 0; i < N; i++) perm[i] = i;
        for (int i = N - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int tmp = perm[i];
            perm[i] = perm[j];
            perm[j] = tmp;
        }

        if (sourceAtZero) {
            // Find where 0 is, swap with perm[0] so node 0 is at the top of topological order
            int zeroIdx = 0;
            for (int i = 0; i < N; i++) {
                if (perm[i] == 0) {
                    zeroIdx = i;
                    break;
                }
            }
            int tmp = perm[0];
            perm[0] = perm[zeroIdx];
            perm[zeroIdx] = tmp;
        }

        Set<Long> set = new HashSet<>();
        long maxPossible = ((long) N * (N - 1)) / 2;
        int actualM = (int) Math.min(targetM, Math.min(maxPossible, 20000));

        // Connect chain along permutation
        if (actualM >= N - 1 && rng.nextBoolean()) {
            for (int i = 0; i < N - 1 && edges.size() < actualM; i++) {
                int u = perm[i];
                int v = perm[i + 1];
                int wt = 1 + rng.nextInt(100);
                long key = (((long) u) << 32) | (v & 0xFFFFFFFFL);
                if (set.add(key)) {
                    edges.add(new Edge(u, v, wt));
                }
            }
        }

        int attempts = 0;
        while (edges.size() < actualM && attempts < actualM * 15) {
            attempts++;
            int i = rng.nextInt(N);
            int j = rng.nextInt(N);
            if (i == j) continue;
            if (i > j) {
                int t = i;
                i = j;
                j = t;
            }
            int u = perm[i];
            int v = perm[j];
            int wt = 1 + rng.nextInt(1000);
            long key = (((long) u) << 32) | (v & 0xFFFFFFFFL);
            if (set.add(key)) {
                edges.add(new Edge(u, v, wt));
            }
        }

        return edges;
    }

    public static void main(String[] args) {
        try {
            File baseDir = new File("d:/MF Shastra/shastra striverz/Shortest path in DAG/Testcases");
            File inDir = new File(baseDir, "Input");
            File outDir = new File(baseDir, "Output");

            inDir.mkdirs();
            outDir.mkdirs();

            Random rng = new Random(5072026L);

            // 00: Sample 1
            writeTestCase(inDir, outDir, 0, 4, Arrays.asList(
                new Edge(0, 1, 2), new Edge(0, 2, 1)
            ));

            // 01: Sample 2
            writeTestCase(inDir, outDir, 1, 6, Arrays.asList(
                new Edge(0, 1, 2), new Edge(0, 4, 1), new Edge(4, 5, 4),
                new Edge(4, 2, 2), new Edge(1, 2, 3), new Edge(2, 3, 6), new Edge(5, 3, 1)
            ));

            // 02: Sample 3
            writeTestCase(inDir, outDir, 2, 3, Arrays.asList(
                new Edge(0, 1, 4), new Edge(0, 2, 2), new Edge(1, 2, 5)
            ));

            // 03: Minimal boundary N=1, M=0
            writeTestCase(inDir, outDir, 3, 1, Collections.emptyList());

            // 04: Minimal boundary N=2, M=0
            writeTestCase(inDir, outDir, 4, 2, Collections.emptyList());

            // 05: Minimal boundary N=2, M=1 (0 -> 1, wt=5)
            writeTestCase(inDir, outDir, 5, 2, Collections.singletonList(new Edge(0, 1, 5)));

            // 06: Minimal boundary N=2, M=1 (1 -> 0, wt=5)
            writeTestCase(inDir, outDir, 6, 2, Collections.singletonList(new Edge(1, 0, 5)));

            // 07: N=3, M=0
            writeTestCase(inDir, outDir, 7, 3, Collections.emptyList());

            // 08: N=3, M=2 (0 -> 1, 1 -> 2, weights 3, 4)
            writeTestCase(inDir, outDir, 8, 3, Arrays.asList(
                new Edge(0, 1, 3), new Edge(1, 2, 4)
            ));

            // 09: N=3, M=2 (2 -> 1, 1 -> 0, weights 3, 4)
            writeTestCase(inDir, outDir, 9, 3, Arrays.asList(
                new Edge(2, 1, 3), new Edge(1, 0, 4)
            ));

            // 10: N=4 Diamond with shortcut (direct edge wt 10, 2-hop path wt 2+3=5)
            writeTestCase(inDir, outDir, 10, 4, Arrays.asList(
                new Edge(0, 1, 2), new Edge(1, 3, 3), new Edge(0, 3, 10), new Edge(0, 2, 7)
            ));

            // 11: N=4 Diamond with direct edge shorter (direct wt 4, 2-hop wt 3+3=6)
            writeTestCase(inDir, outDir, 11, 4, Arrays.asList(
                new Edge(0, 1, 3), new Edge(1, 3, 3), new Edge(0, 3, 4), new Edge(0, 2, 1)
            ));

            // 12: N=5 Star Out-Tree from 0
            writeTestCase(inDir, outDir, 12, 5, Arrays.asList(
                new Edge(0, 1, 10), new Edge(0, 2, 20), new Edge(0, 3, 30), new Edge(0, 4, 40)
            ));

            // 13: N=5 Star In-Tree to 0
            writeTestCase(inDir, outDir, 13, 5, Arrays.asList(
                new Edge(1, 0, 10), new Edge(2, 0, 20), new Edge(3, 0, 30), new Edge(4, 0, 40)
            ));

            // 14: N=6 Disjoint: {0 -> 1 -> 2} and {3 -> 4 -> 5}
            writeTestCase(inDir, outDir, 14, 6, Arrays.asList(
                new Edge(0, 1, 5), new Edge(1, 2, 10),
                new Edge(3, 4, 2), new Edge(4, 5, 3)
            ));

            // 15 - 34: Medium DAGs (N = 10..100)
            for (int tc = 15; tc <= 34; tc++) {
                int N = 10 + (tc - 15) * 4 + rng.nextInt(5);
                int M = rng.nextInt(Math.min(N * 3 + 10, 300));
                boolean sourceAtZero = (tc % 3 != 0);
                writeTestCase(inDir, outDir, tc, N, generateDAG(N, M, sourceAtZero, rng));
            }

            // 35 - 69: Large DAGs (N = 150..1500)
            for (int tc = 35; tc <= 69; tc++) {
                int N = 150 + (tc - 35) * 35 + rng.nextInt(20);
                int maxM = Math.min(4000, N * 3);
                int M = 50 + rng.nextInt(maxM);
                boolean sourceAtZero = (tc % 4 != 0);
                writeTestCase(inDir, outDir, tc, N, generateDAG(N, M, sourceAtZero, rng));
            }

            // 70 - 89: Very Large DAGs (N = 2000..6000)
            for (int tc = 70; tc <= 89; tc++) {
                int N = 2000 + (tc - 70) * 200 + rng.nextInt(50);
                int M = 1000 + rng.nextInt(11000);
                boolean sourceAtZero = (tc % 3 != 0);
                writeTestCase(inDir, outDir, tc, N, generateDAG(N, M, sourceAtZero, rng));
            }

            // 90 - 99: Extreme Maximum Constraints (N = 10000, M up to 20000)
            // 90: N=10000, M=0
            writeTestCase(inDir, outDir, 90, 10000, Collections.emptyList());

            // 91: N=10000, M=9999 (long chain 0 -> 1 -> ... -> 9999, weight 1)
            List<Edge> e91 = new ArrayList<>();
            for (int i = 0; i < 9999; i++) {
                e91.add(new Edge(i, i + 1, 1));
            }
            writeTestCase(inDir, outDir, 91, 10000, e91);

            // 92: N=10000, M=9999 (reverse long chain 9999 -> ... -> 0)
            List<Edge> e92 = new ArrayList<>();
            for (int i = 9999; i > 0; i--) {
                e92.add(new Edge(i, i - 1, 1));
            }
            writeTestCase(inDir, outDir, 92, 10000, e92);

            // 93: N=10000, M=9999 (star: 0 connects to all 9999 nodes)
            List<Edge> e93 = new ArrayList<>();
            for (int i = 1; i < 10000; i++) {
                e93.add(new Edge(0, i, (i % 100) + 1));
            }
            writeTestCase(inDir, outDir, 93, 10000, e93);

            // 94: N=10000, M=9999 (star: all 9999 nodes connect to 0)
            List<Edge> e94 = new ArrayList<>();
            for (int i = 1; i < 10000; i++) {
                e94.add(new Edge(i, 0, (i % 100) + 1));
            }
            writeTestCase(inDir, outDir, 94, 10000, e94);

            // 95: N=10000, M=15000 (long chain + forward shortcuts)
            List<Edge> e95 = new ArrayList<>(e91);
            Set<Long> set95 = new HashSet<>();
            for (Edge e : e95) {
                set95.add((((long) e.u) << 32) | (e.v & 0xFFFFFFFFL));
            }
            while (e95.size() < 15000) {
                int u = rng.nextInt(9995);
                int v = u + 2 + rng.nextInt(Math.min(50, 10000 - u - 2));
                long key = (((long) u) << 32) | (v & 0xFFFFFFFFL);
                if (set95.add(key)) {
                    e95.add(new Edge(u, v, 1 + rng.nextInt(50)));
                }
            }
            writeTestCase(inDir, outDir, 95, 10000, e95);

            // 96: N=10000, M=9999 (binary heap tree DAG from 0)
            List<Edge> e96 = new ArrayList<>();
            for (int i = 0; i < 5000; i++) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                if (left < 10000) e96.add(new Edge(i, left, (i % 10) + 1));
                if (right < 10000) e96.add(new Edge(i, right, (i % 10) + 2));
            }
            writeTestCase(inDir, outDir, 96, 10000, e96);

            // 97: N=10000, M=15000 (layered DAG)
            writeTestCase(inDir, outDir, 97, 10000, generateDAG(10000, 15000, true, rng));

            // 98: N=10000, M=20000 (dense DAG, 0 at root)
            writeTestCase(inDir, outDir, 98, 10000, generateDAG(10000, 20000, true, rng));

            // 99: N=10000, M=20000 (dense DAG, 0 in middle)
            writeTestCase(inDir, outDir, 99, 10000, generateDAG(10000, 20000, false, rng));

            System.out.println("SUCCESS: Generated 100 testcases for Shortest Path in DAG.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
