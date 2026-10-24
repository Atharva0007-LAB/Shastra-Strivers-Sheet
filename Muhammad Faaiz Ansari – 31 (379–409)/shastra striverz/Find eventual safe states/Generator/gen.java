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
    private static List<Integer> solve(int V, List<Edge> edges) {
        List<Integer>[] revAdj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            revAdj[i] = new ArrayList<>();
        }
        int[] inDegree = new int[V];

        for (Edge e : edges) {
            revAdj[e.v].add(e.u);
            inDegree[e.u]++;
        }

        int[] queue = new int[V];
        int head = 0;
        int tail = 0;

        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0) {
                queue[tail++] = i;
            }
        }

        boolean[] isSafe = new boolean[V];

        while (head < tail) {
            int v = queue[head++];
            isSafe[v] = true;
            for (int u : revAdj[v]) {
                inDegree[u]--;
                if (inDegree[u] == 0) {
                    queue[tail++] = u;
                }
            }
        }

        List<Integer> safeNodes = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (isSafe[i]) {
                safeNodes.add(i);
            }
        }

        return safeNodes;
    }

    private static void writeTestCase(File inDir, File outDir, int index, int V, List<Edge> edges) throws Exception {
        String filename = String.format("%02d.txt", index);
        File inFile = new File(inDir, "input" + filename);
        File outFile = new File(outDir, "output" + filename);

        try (PrintWriter inPw = new PrintWriter(inFile)) {
            inPw.println(V + " " + edges.size());
            for (Edge e : edges) {
                inPw.println(e.u + " " + e.v);
            }
        }

        List<Integer> safeNodes = solve(V, edges);
        try (PrintWriter outPw = new PrintWriter(outFile)) {
            for (int i = 0; i < safeNodes.size(); i++) {
                outPw.print(safeNodes.get(i));
                if (i + 1 < safeNodes.size()) {
                    outPw.print(" ");
                }
            }
            outPw.println();
        }
    }

    private static List<Edge> generateHybridGraph(int V, int targetE, double cycleFraction, Random rng) {
        List<Edge> edges = new ArrayList<>();
        if (V <= 1 || targetE <= 0) return edges;

        int[] perm = new int[V];
        for (int i = 0; i < V; i++) perm[i] = i;
        for (int i = V - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int tmp = perm[i];
            perm[i] = perm[j];
            perm[j] = tmp;
        }

        Set<Long> set = new HashSet<>();
        int cycleCutoff = (int) (V * cycleFraction);

        // Forward edges across all vertices
        int attempts = 0;
        int maxE = Math.min(targetE, 40000);
        while (edges.size() < maxE && attempts < maxE * 15) {
            attempts++;
            int i = rng.nextInt(V);
            int j = rng.nextInt(V);
            if (i > j) {
                int t = i;
                i = j;
                j = t;
            }
            // If i == j, allowed with 2% probability for self loops
            if (i == j && rng.nextDouble() > 0.02) continue;

            int u = perm[i];
            int v = perm[j];
            long key = (((long) u) << 32) | (v & 0xFFFFFFFFL);
            if (set.add(key)) {
                edges.add(new Edge(u, v));
            }
        }

        // Add back-edges within the first cycleCutoff vertices
        if (cycleCutoff >= 2) {
            int cycleEdges = Math.min(cycleCutoff, 100);
            for (int k = 0; k < cycleEdges && edges.size() < maxE; k++) {
                int i = rng.nextInt(cycleCutoff);
                int j = rng.nextInt(cycleCutoff);
                if (i <= j) {
                    int t = i;
                    i = j;
                    j = t;
                }
                int u = perm[i];
                int v = perm[j];
                long key = (((long) u) << 32) | (v & 0xFFFFFFFFL);
                if (set.add(key)) {
                    edges.add(new Edge(u, v));
                }
            }
        }

        return edges;
    }

    public static void main(String[] args) {
        try {
            File baseDir = new File("d:/MF Shastra/shastra striverz/Find eventual safe states/Testcases");
            File inDir = new File(baseDir, "Input");
            File outDir = new File(baseDir, "Output");

            inDir.mkdirs();
            outDir.mkdirs();

            Random rng = new Random(5062026L);

            // 00: Sample 1
            List<Edge> e0 = Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(1, 2),
                new Edge(1, 3), new Edge(2, 5), new Edge(3, 0), new Edge(4, 5)
            );
            writeTestCase(inDir, outDir, 0, 7, e0);

            // 01: Sample 2
            List<Edge> e1 = Arrays.asList(
                new Edge(0, 1), new Edge(1, 2), new Edge(2, 0), new Edge(2, 3)
            );
            writeTestCase(inDir, outDir, 1, 4, e1);

            // 02: Sample 3
            List<Edge> e2 = Arrays.asList(
                new Edge(0, 1), new Edge(1, 2), new Edge(2, 0)
            );
            writeTestCase(inDir, outDir, 2, 4, e2);

            // 03: V=1, E=0 (safe: 0)
            writeTestCase(inDir, outDir, 3, 1, Collections.emptyList());

            // 04: V=1, E=1 (self-loop 0 -> 0, safe: none)
            writeTestCase(inDir, outDir, 4, 1, Collections.singletonList(new Edge(0, 0)));

            // 05: V=2, E=0 (safe: 0 1)
            writeTestCase(inDir, outDir, 5, 2, Collections.emptyList());

            // 06: V=2, E=1 (0 -> 1, safe: 0 1)
            writeTestCase(inDir, outDir, 6, 2, Collections.singletonList(new Edge(0, 1)));

            // 07: V=2, E=2 (0 -> 1, 1 -> 0, safe: none)
            writeTestCase(inDir, outDir, 7, 2, Arrays.asList(new Edge(0, 1), new Edge(1, 0)));

            // 08: V=3, E=0 (safe: 0 1 2)
            writeTestCase(inDir, outDir, 8, 3, Collections.emptyList());

            // 09: V=3, E=3 (0 -> 1 -> 2 -> 0, safe: none)
            writeTestCase(inDir, outDir, 9, 3, Arrays.asList(
                new Edge(0, 1), new Edge(1, 2), new Edge(2, 0)
            ));

            // 10: V=4 Diamond DAG (safe: 0 1 2 3)
            writeTestCase(inDir, outDir, 10, 4, Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(1, 3), new Edge(2, 3)
            ));

            // 11: V=4 Diamond with self-loop on 2 (2 -> 2; safe: 3)
            writeTestCase(inDir, outDir, 11, 4, Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(1, 3), new Edge(2, 3), new Edge(2, 2)
            ));

            // 12: V=5 Star Out-Tree (0 -> 1, 2, 3, 4; all safe: 0 1 2 3 4)
            writeTestCase(inDir, outDir, 12, 5, Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(0, 3), new Edge(0, 4)
            ));

            // 13: V=5 Star In-Tree (1, 2, 3, 4 -> 0; all safe: 0 1 2 3 4)
            writeTestCase(inDir, outDir, 13, 5, Arrays.asList(
                new Edge(1, 0), new Edge(2, 0), new Edge(3, 0), new Edge(4, 0)
            ));

            // 14: V=6 Disjoint: {0 -> 1, 1 -> 2} and {3 -> 4, 4 -> 5, 5 -> 3} (safe: 0 1 2)
            writeTestCase(inDir, outDir, 14, 6, Arrays.asList(
                new Edge(0, 1), new Edge(1, 2),
                new Edge(3, 4), new Edge(4, 5), new Edge(5, 3)
            ));

            // 15 - 34: Medium graphs (V = 10..100)
            for (int tc = 15; tc <= 34; tc++) {
                int V = 10 + (tc - 15) * 4 + rng.nextInt(5);
                int E = rng.nextInt(Math.min(V * 3 + 10, 300));
                double cycleFrac = (tc % 3 == 0) ? 0.0 : (tc % 3 == 1 ? 0.3 : 0.8);
                writeTestCase(inDir, outDir, tc, V, generateHybridGraph(V, E, cycleFrac, rng));
            }

            // 35 - 69: Large graphs (V = 150..2000)
            for (int tc = 35; tc <= 69; tc++) {
                int V = 150 + (tc - 35) * 50 + rng.nextInt(25);
                int maxE = Math.min(8000, V * 3);
                int E = 50 + rng.nextInt(maxE);
                double cycleFrac = (tc % 4 == 0) ? 0.0 : (tc % 4 == 1 ? 0.2 : (tc % 4 == 2 ? 0.5 : 0.9));
                writeTestCase(inDir, outDir, tc, V, generateHybridGraph(V, E, cycleFrac, rng));
            }

            // 70 - 89: Very Large graphs (V = 2500..8000)
            for (int tc = 70; tc <= 89; tc++) {
                int V = 2500 + (tc - 70) * 270 + rng.nextInt(50);
                int E = 1000 + rng.nextInt(20000);
                double cycleFrac = (tc % 3 == 0) ? 0.0 : (tc % 3 == 1 ? 0.35 : 0.7);
                writeTestCase(inDir, outDir, tc, V, generateHybridGraph(V, E, cycleFrac, rng));
            }

            // 90 - 99: Extreme Maximum Constraints (V = 10000, E up to 40000)
            // 90: V=10000, E=0 (all 10000 safe)
            writeTestCase(inDir, outDir, 90, 10000, Collections.emptyList());

            // 91: V=10000, E=9999 (long chain 0 -> 1 -> ... -> 9999; all safe)
            List<Edge> e91 = new ArrayList<>();
            for (int i = 0; i < 9999; i++) {
                e91.add(new Edge(i, i + 1));
            }
            writeTestCase(inDir, outDir, 91, 10000, e91);

            // 92: V=10000, E=10000 (long chain + cycle at start: 0 <-> 1; safe: 2..9999)
            List<Edge> e92 = new ArrayList<>(e91);
            e92.add(new Edge(1, 0));
            writeTestCase(inDir, outDir, 92, 10000, e92);

            // 93: V=10000, E=10000 (long chain + cycle at end: 9999 -> 0; safe: none)
            List<Edge> e93 = new ArrayList<>(e91);
            e93.add(new Edge(9999, 0));
            writeTestCase(inDir, outDir, 93, 10000, e93);

            // 94: V=10000, E=9999 (star: 0 -> all 9999 nodes; all safe)
            List<Edge> e94 = new ArrayList<>();
            for (int i = 1; i < 10000; i++) {
                e94.add(new Edge(0, i));
            }
            writeTestCase(inDir, outDir, 94, 10000, e94);

            // 95: V=10000, E=10000 (star + self-loop 0 -> 0; only leaves 1..9999 safe)
            List<Edge> e95 = new ArrayList<>(e94);
            e95.add(new Edge(0, 0));
            writeTestCase(inDir, outDir, 95, 10000, e95);

            // 96: V=10000, E=9999 (star: all 9999 nodes -> 9999; all safe)
            List<Edge> e96 = new ArrayList<>();
            for (int i = 0; i < 9999; i++) {
                e96.add(new Edge(i, 9999));
            }
            writeTestCase(inDir, outDir, 96, 10000, e96);

            // 97: V=10000, E=10000 (all 9999 nodes -> 9999 + self loop 9999 -> 9999; safe: none)
            List<Edge> e97 = new ArrayList<>(e96);
            e97.add(new Edge(9999, 9999));
            writeTestCase(inDir, outDir, 97, 10000, e97);

            // 98: V=10000, E=25000 (Random Hybrid DAG + cycles)
            writeTestCase(inDir, outDir, 98, 10000, generateHybridGraph(10000, 25000, 0.4, rng));

            // 99: V=10000, E=40000 (Absolute maximum constraints: V=10000, E=40000)
            writeTestCase(inDir, outDir, 99, 10000, generateHybridGraph(10000, 40000, 0.5, rng));

            System.out.println("SUCCESS: Generated 100 testcases for Find eventual safe states.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
