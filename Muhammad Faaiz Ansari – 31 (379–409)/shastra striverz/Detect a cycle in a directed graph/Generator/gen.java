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
    private static boolean solve(int V, List<Edge> edges) {
        if (V <= 1) return false;

        List<Integer>[] adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
        int[] inDegree = new int[V];
        for (Edge e : edges) {
            adj[e.u].add(e.v);
            inDegree[e.v]++;
        }

        int[] queue = new int[V];
        int head = 0;
        int tail = 0;

        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0) {
                queue[tail++] = i;
            }
        }

        while (head < tail) {
            int u = queue[head++];
            for (int v : adj[u]) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue[tail++] = v;
                }
            }
        }

        return head < V;
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

        boolean hasCycle = solve(V, edges);
        try (PrintWriter outPw = new PrintWriter(outFile)) {
            outPw.println(hasCycle);
        }
    }

    private static List<Edge> generateGraph(int V, int targetE, boolean makeCyclic, Random rng) {
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

        // Connect chain along permutation
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

        // Add forward edges
        int attempts = 0;
        int forwardTarget = makeCyclic ? Math.max(1, actualE - 1 - rng.nextInt(3)) : actualE;
        while (edges.size() < forwardTarget && attempts < forwardTarget * 15) {
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

        // If makeCyclic is true, add at least one back-edge (j -> i where j > i)
        if (makeCyclic && V >= 2) {
            int backAttempts = 0;
            boolean cycleAdded = false;
            while (backAttempts < 500 && !cycleAdded) {
                backAttempts++;
                int i = rng.nextInt(V);
                int j = rng.nextInt(V);
                if (i == j) continue;
                if (i < j) {
                    int t = i;
                    i = j;
                    j = t;
                }
                // i > j in permutation order -> perm[i] points to perm[j] (backwards)
                int u = perm[i];
                int v = perm[j];
                long key = (((long) u) << 32) | (v & 0xFFFFFFFFL);
                if (set.add(key)) {
                    edges.add(new Edge(u, v));
                    cycleAdded = true;
                }
            }

            // Fallback if random didn't add
            if (!cycleAdded) {
                int u = perm[1];
                int v = perm[0];
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
            File baseDir = new File("d:/MF Shastra/shastra striverz/Detect a cycle in a directed graph/Testcases");
            File inDir = new File(baseDir, "Input");
            File outDir = new File(baseDir, "Output");

            inDir.mkdirs();
            outDir.mkdirs();

            Random rng = new Random(5002026L);

            // 00: Sample 1 (cyclic)
            List<Edge> e0 = Arrays.asList(
                new Edge(0, 1), new Edge(1, 2), new Edge(1, 5),
                new Edge(2, 3), new Edge(3, 4), new Edge(4, 1)
            );
            writeTestCase(inDir, outDir, 0, 6, e0);

            // 01: Sample 2 (acyclic)
            List<Edge> e1 = Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(1, 2),
                new Edge(3, 0), new Edge(3, 2)
            );
            writeTestCase(inDir, outDir, 1, 4, e1);

            // 02: Sample 3 (cyclic)
            List<Edge> e2 = Arrays.asList(
                new Edge(0, 1), new Edge(1, 2), new Edge(2, 0)
            );
            writeTestCase(inDir, outDir, 2, 3, e2);

            // 03: V=1, E=0 (false)
            writeTestCase(inDir, outDir, 3, 1, Collections.emptyList());

            // 04: V=2, E=0 (false)
            writeTestCase(inDir, outDir, 4, 2, Collections.emptyList());

            // 05: V=2, E=1 (0 -> 1, false)
            writeTestCase(inDir, outDir, 5, 2, Collections.singletonList(new Edge(0, 1)));

            // 06: V=2, E=2 (0 -> 1, 1 -> 0, true)
            writeTestCase(inDir, outDir, 6, 2, Arrays.asList(new Edge(0, 1), new Edge(1, 0)));

            // 07: V=3, E=0 (false)
            writeTestCase(inDir, outDir, 7, 3, Collections.emptyList());

            // 08: V=3, E=2 (0 -> 1, 1 -> 2, false)
            writeTestCase(inDir, outDir, 8, 3, Arrays.asList(new Edge(0, 1), new Edge(1, 2)));

            // 09: V=3, 3-cycle (2 -> 1, 1 -> 0, 0 -> 2, true)
            writeTestCase(inDir, outDir, 9, 3, Arrays.asList(
                new Edge(2, 1), new Edge(1, 0), new Edge(0, 2)
            ));

            // 10: V=4 Diamond DAG (false)
            writeTestCase(inDir, outDir, 10, 4, Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(1, 3), new Edge(2, 3)
            ));

            // 11: V=4 Diamond with back-edge (3 -> 0, true)
            writeTestCase(inDir, outDir, 11, 4, Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(1, 3), new Edge(2, 3), new Edge(3, 0)
            ));

            // 12: V=5 Star Out-Tree (false)
            writeTestCase(inDir, outDir, 12, 5, Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(0, 3), new Edge(0, 4)
            ));

            // 13: V=5 Star In-Tree (false)
            writeTestCase(inDir, outDir, 13, 5, Arrays.asList(
                new Edge(1, 0), new Edge(2, 0), new Edge(3, 0), new Edge(4, 0)
            ));

            // 14: V=5 Star with back-edge (0 -> 1, 1 -> 0, true)
            writeTestCase(inDir, outDir, 14, 5, Arrays.asList(
                new Edge(0, 1), new Edge(0, 2), new Edge(0, 3), new Edge(0, 4), new Edge(1, 0)
            ));

            // 15 - 34: Medium graphs (V = 10..100)
            for (int tc = 15; tc <= 34; tc++) {
                int V = 10 + (tc - 15) * 4 + rng.nextInt(5);
                int E = rng.nextInt(Math.min(V * 3 + 10, 250));
                boolean makeCyclic = (tc % 2 == 0);
                writeTestCase(inDir, outDir, tc, V, generateGraph(V, E, makeCyclic, rng));
            }

            // 35 - 69: Large graphs (V = 150..2000)
            for (int tc = 35; tc <= 69; tc++) {
                int V = 150 + (tc - 35) * 50 + rng.nextInt(25);
                int maxE = Math.min(10000, V * 3);
                int E = 50 + rng.nextInt(maxE);
                boolean makeCyclic = (tc % 2 == 1);
                writeTestCase(inDir, outDir, tc, V, generateGraph(V, E, makeCyclic, rng));
            }

            // 70 - 89: Very Large graphs (V = 2500..8000)
            for (int tc = 70; tc <= 89; tc++) {
                int V = 2500 + (tc - 70) * 270 + rng.nextInt(50);
                int E = 1000 + rng.nextInt(8500);
                boolean makeCyclic = (tc % 2 == 0);
                writeTestCase(inDir, outDir, tc, V, generateGraph(V, E, makeCyclic, rng));
            }

            // 90 - 99: Extreme Maximum Constraints (V = 10000, E up to 10000)
            // 90: V=10000, E=0 (false)
            writeTestCase(inDir, outDir, 90, 10000, Collections.emptyList());

            // 91: V=10000, E=9999 (long chain, false)
            List<Edge> e91 = new ArrayList<>();
            for (int i = 0; i < 9999; i++) {
                e91.add(new Edge(i, i + 1));
            }
            writeTestCase(inDir, outDir, 91, 10000, e91);

            // 92: V=10000, E=10000 (long chain + back-edge 9999 -> 0, true)
            List<Edge> e92 = new ArrayList<>(e91);
            e92.add(new Edge(9999, 0));
            writeTestCase(inDir, outDir, 92, 10000, e92);

            // 93: V=10000, E=10000 (long chain + small 2-cycle 9999 -> 9998, true)
            List<Edge> e93 = new ArrayList<>(e91);
            e93.add(new Edge(9999, 9998));
            writeTestCase(inDir, outDir, 93, 10000, e93);

            // 94: V=10000, E=9999 (star: 0 -> all, false)
            List<Edge> e94 = new ArrayList<>();
            for (int i = 1; i < 10000; i++) {
                e94.add(new Edge(0, i));
            }
            writeTestCase(inDir, outDir, 94, 10000, e94);

            // 95: V=10000, E=10000 (star + back-edge 9999 -> 0, true)
            List<Edge> e95 = new ArrayList<>(e94);
            e95.add(new Edge(9999, 0));
            writeTestCase(inDir, outDir, 95, 10000, e95);

            // 96: V=10000, E=9999 (binary heap tree DAG, false)
            List<Edge> e96 = new ArrayList<>();
            for (int i = 0; i < 5000; i++) {
                int left = 2 * i + 1;
                int right = 2 * i + 2;
                if (left < 10000) e96.add(new Edge(i, left));
                if (right < 10000) e96.add(new Edge(i, right));
            }
            writeTestCase(inDir, outDir, 96, 10000, e96);

            // 97: V=10000, E=10000 (binary heap tree DAG + back-edge 9999 -> 0, true)
            List<Edge> e97 = new ArrayList<>(e96);
            e97.add(new Edge(9999, 0));
            writeTestCase(inDir, outDir, 97, 10000, e97);

            // 98: V=10000, E=10000 (Random DAG, false)
            writeTestCase(inDir, outDir, 98, 10000, generateGraph(10000, 10000, false, rng));

            // 99: V=10000, E=10000 (Random Graph with Cycle, true)
            writeTestCase(inDir, outDir, 99, 10000, generateGraph(10000, 10000, true, rng));

            System.out.println("SUCCESS: Generated 100 testcases for Detect a cycle in a directed graph.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
