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
    private static int[] solve(int V, List<Edge> edges) {
        List<Integer>[] adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
        int[] inDegree = new int[V];
        for (Edge e : edges) {
            adj[e.u].add(e.v);
            inDegree[e.v]++;
        }

        int[] order = new int[V];
        int head = 0;
        int tail = 0;

        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0) {
                order[tail++] = i;
            }
        }

        while (head < tail) {
            int u = order[head++];
            for (int v : adj[u]) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    order[tail++] = v;
                }
            }
        }

        return order;
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

        int[] order = solve(V, edges);
        try (PrintWriter outPw = new PrintWriter(outFile)) {
            for (int i = 0; i < V; i++) {
                outPw.print(order[i]);
                if (i + 1 < V) {
                    outPw.print(" ");
                }
            }
            outPw.println();
        }
    }

    private static List<Edge> generateDAG(int V, int targetE, Random rng) {
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
        long maxPossibleEdges = ((long) V * (V - 1)) / 2;
        int actualE = (int) Math.min(targetE, Math.min(maxPossibleEdges, 10000));

        // Connect chain to make sure it's nicely structured if actualE >= V - 1
        if (actualE >= V - 1 && rng.nextBoolean()) {
            for (int i = 0; i < V - 1 && edges.size() < actualE; i++) {
                int u = perm[i];
                int v = perm[i + 1];
                long key = (((long) u) << 32) | (v & 0xFFFFFFFFL);
                if (set.add(key)) {
                    edges.add(new Edge(u, v));
                }
            }
        }

        int attempts = 0;
        while (edges.size() < actualE && attempts < actualE * 15) {
            attempts++;
            int i = rng.nextInt(V);
            int j = rng.nextInt(V);
            if (i == j) continue;
            if (i > j) {
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

        return edges;
    }

    public static void main(String[] args) {
        try {
            File baseDir = new File("d:/MF Shastra/shastra striverz/Topological sort or Kahns algorithm/Testcases");
            File inDir = new File(baseDir, "Input");
            File outDir = new File(baseDir, "Output");

            inDir.mkdirs();
            outDir.mkdirs();

            Random rng = new Random(5022026L);

            // 00: Sample 1
            List<Edge> e0 = Arrays.asList(
                new Edge(2, 3), new Edge(3, 1), new Edge(4, 0),
                new Edge(4, 1), new Edge(5, 0), new Edge(5, 2)
            );
            writeTestCase(inDir, outDir, 0, 6, e0);

            // 01: Sample 2
            List<Edge> e1 = Arrays.asList(
                new Edge(1, 0), new Edge(2, 0), new Edge(3, 0)
            );
            writeTestCase(inDir, outDir, 1, 4, e1);

            // 02: Sample 3
            List<Edge> e2 = Arrays.asList(
                new Edge(0, 1), new Edge(1, 2)
            );
            writeTestCase(inDir, outDir, 2, 3, e2);

            // 03: V=1, E=0
            writeTestCase(inDir, outDir, 3, 1, Collections.emptyList());

            // 04: V=2, E=0
            writeTestCase(inDir, outDir, 4, 2, Collections.emptyList());

            // 05: V=2, E=1 (0 -> 1)
            writeTestCase(inDir, outDir, 5, 2, Collections.singletonList(new Edge(0, 1)));

            // 06: V=2, E=1 (1 -> 0)
            writeTestCase(inDir, outDir, 6, 2, Collections.singletonList(new Edge(1, 0)));

            // 07: V=3, E=0
            writeTestCase(inDir, outDir, 7, 3, Collections.emptyList());

            // 08: V=3, E=1 (2 -> 1)
            writeTestCase(inDir, outDir, 8, 3, Collections.singletonList(new Edge(2, 1)));

            // 09: V=4 Diamond (0 -> 1, 0 -> 2, 1 -> 3, 2 -> 3)
            writeTestCase(inDir, outDir, 9, 4, Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(1, 3), new Edge(2, 3)
            ));

            // 10: V=5 Star Out-Tree (0 -> 1, 2, 3, 4)
            writeTestCase(inDir, outDir, 10, 5, Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(0, 3), new Edge(0, 4)
            ));

            // 11: V=5 Star In-Tree (0, 1, 2, 3 -> 4)
            writeTestCase(inDir, outDir, 11, 5, Arrays.asList(
                new Edge(0, 4), new Edge(1, 4), new Edge(2, 4), new Edge(3, 4)
            ));

            // 12: V=5 Linear Chain (4 -> 3 -> 2 -> 1 -> 0)
            writeTestCase(inDir, outDir, 12, 5, Arrays.asList(
                new Edge(4, 3), new Edge(3, 2), new Edge(2, 1), new Edge(1, 0)
            ));

            // 13: V=6 Disjoint Chains (0 -> 1 -> 2, 3 -> 4 -> 5)
            writeTestCase(inDir, outDir, 13, 6, Arrays.asList(
                new Edge(0, 1), new Edge(1, 2), new Edge(3, 4), new Edge(4, 5)
            ));

            // 14: V=7 Binary Tree DAG (0 -> 1, 0 -> 2, 1 -> 3, 1 -> 4, 2 -> 5, 2 -> 6)
            writeTestCase(inDir, outDir, 14, 7, Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(1, 3),
                new Edge(1, 4), new Edge(2, 5), new Edge(2, 6)
            ));

            // 15 - 34: Medium DAGs (V = 10..100)
            for (int tc = 15; tc <= 34; tc++) {
                int V = 10 + (tc - 15) * 4 + rng.nextInt(5);
                int E = rng.nextInt(Math.min(V * 2 + 10, 250));
                writeTestCase(inDir, outDir, tc, V, generateDAG(V, E, rng));
            }

            // 35 - 69: Large DAGs (V = 150..2000)
            for (int tc = 35; tc <= 69; tc++) {
                int V = 150 + (tc - 35) * 50 + rng.nextInt(25);
                int maxE = Math.min(10000, V * 3);
                int E = 50 + rng.nextInt(maxE);
                writeTestCase(inDir, outDir, tc, V, generateDAG(V, E, rng));
            }

            // 70 - 89: Very Large DAGs (V = 2500..8000)
            for (int tc = 70; tc <= 89; tc++) {
                int V = 2500 + (tc - 70) * 270 + rng.nextInt(50);
                int E = 1000 + rng.nextInt(8500);
                writeTestCase(inDir, outDir, tc, V, generateDAG(V, E, rng));
            }

            // 90 - 99: Extreme Maximum Constraints (V = 10000, E up to 10000)
            // 90: V=10000, E=0
            writeTestCase(inDir, outDir, 90, 10000, Collections.emptyList());

            // 91: V=10000, E=9999 (single long chain 0 -> 1 -> ... -> 9999)
            List<Edge> e91 = new ArrayList<>();
            for (int i = 0; i < 9999; i++) {
                e91.add(new Edge(i, i + 1));
            }
            writeTestCase(inDir, outDir, 91, 10000, e91);

            // 92: V=10000, E=9999 (reverse long chain 9999 -> 9998 -> ... -> 0)
            List<Edge> e92 = new ArrayList<>();
            for (int i = 9999; i > 0; i--) {
                e92.add(new Edge(i, i - 1));
            }
            writeTestCase(inDir, outDir, 92, 10000, e92);

            // 93: V=10000, E=9999 (star: 0 -> all other 9999 vertices)
            List<Edge> e93 = new ArrayList<>();
            for (int i = 1; i < 10000; i++) {
                e93.add(new Edge(0, i));
            }
            writeTestCase(inDir, outDir, 93, 10000, e93);

            // 94: V=10000, E=9999 (star: all 9999 vertices -> 9999)
            List<Edge> e94 = new ArrayList<>();
            for (int i = 0; i < 9999; i++) {
                e94.add(new Edge(i, 9999));
            }
            writeTestCase(inDir, outDir, 94, 10000, e94);

            // 95: V=10000, E=10000 (long chain + skip forward edges)
            List<Edge> e95 = new ArrayList<>(e91);
            Set<Long> set95 = new HashSet<>();
            for (Edge e : e95) {
                set95.add((((long) e.u) << 32) | (e.v & 0xFFFFFFFFL));
            }
            while (e95.size() < 10000) {
                int u = rng.nextInt(9998);
                int v = u + 2 + rng.nextInt(Math.min(50, 10000 - u - 2));
                long key = (((long) u) << 32) | (v & 0xFFFFFFFFL);
                if (set95.add(key)) {
                    e95.add(new Edge(u, v));
                }
            }
            writeTestCase(inDir, outDir, 95, 10000, e95);

            // 96: V=10000, E=9999 (binary heap tree DAG: parent -> left, parent -> right)
            List<Edge> e96 = new ArrayList<>();
            for (int i = 0; i < 5000; i++) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                if (left < 10000) e96.add(new Edge(i, left));
                if (right < 10000) e96.add(new Edge(i, right));
            }
            writeTestCase(inDir, outDir, 96, 10000, e96);

            // 97: V=10000, E=10000 (layered DAG: 100 layers of 100 vertices each)
            List<Edge> e97 = new ArrayList<>();
            Set<Long> set97 = new HashSet<>();
            // Each node in layer L can connect to layer L + 1
            for (int layer = 0; layer < 99 && e97.size() < 9000; layer++) {
                int u = layer * 100 + rng.nextInt(100);
                int v = (layer + 1) * 100 + rng.nextInt(100);
                long key = (((long) u) << 32) | (v & 0xFFFFFFFFL);
                if (set97.add(key)) {
                    e97.add(new Edge(u, v));
                }
            }
            while (e97.size() < 10000) {
                int l1 = rng.nextInt(99);
                int l2 = l1 + 1 + rng.nextInt(100 - l1 - 1);
                int u = l1 * 100 + rng.nextInt(100);
                int v = l2 * 100 + rng.nextInt(100);
                long key = (((long) u) << 32) | (v & 0xFFFFFFFFL);
                if (set97.add(key)) {
                    e97.add(new Edge(u, v));
                }
            }
            writeTestCase(inDir, outDir, 97, 10000, e97);

            // 98: V=10000, E=10000 (Random DAG over random permutation)
            writeTestCase(inDir, outDir, 98, 10000, generateDAG(10000, 10000, rng));

            // 99: V=10000, E=10000 (Another extreme random DAG)
            writeTestCase(inDir, outDir, 99, 10000, generateDAG(10000, 10000, rng));

            System.out.println("SUCCESS: Generated 100 testcases for Topological Sort or Kahn's Algorithm.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
