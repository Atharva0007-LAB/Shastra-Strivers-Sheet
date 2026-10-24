import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static final int INF = 1000000000;

    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    private static String solve(int V, int E, int S, List<Edge> edges) {
        int[] dist = new int[V];
        Arrays.fill(dist, INF);
        dist[S] = 0;

        for (int iter = 0; iter < V - 1; iter++) {
            boolean updated = false;
            for (Edge e : edges) {
                if (dist[e.u] != INF && dist[e.u] + e.w < dist[e.v]) {
                    dist[e.v] = dist[e.u] + e.w;
                    updated = true;
                }
            }
            if (!updated) break;
        }

        for (Edge e : edges) {
            if (dist[e.u] != INF && dist[e.u] + e.w < dist[e.v]) {
                return "-1";
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < V; i++) {
            sb.append(dist[i]);
            if (i < V - 1) sb.append(" ");
        }
        return sb.toString();
    }

    private static void writeTestCase(int index, int V, int S, List<Edge> edges) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        String inFileName = String.format("input%02d.txt", index);
        String outFileName = String.format("output%02d.txt", index);

        File inFile = new File(inputDir, inFileName);
        File outFile = new File(outputDir, outFileName);

        int E = edges.size();
        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(V + " " + E + " " + S);
            for (Edge e : edges) {
                pw.println(e.u + " " + e.v + " " + e.w);
            }
        }

        String ans = solve(V, E, S, edges);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5182026L);
        int tc = 0;

        // Sample 1: V = 6, S = 0
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(3, 2, 6));
            edges.add(new Edge(5, 3, 1));
            edges.add(new Edge(0, 1, 5));
            edges.add(new Edge(1, 5, -3));
            edges.add(new Edge(1, 2, -2));
            edges.add(new Edge(3, 4, -2));
            edges.add(new Edge(2, 4, 3));
            writeTestCase(tc++, 6, 0, edges);
        }

        // Sample 2: V = 2, S = 0
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 9));
            writeTestCase(tc++, 2, 0, edges);
        }

        // Sample 3: V = 3, S = 2
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 5));
            edges.add(new Edge(1, 0, 3));
            edges.add(new Edge(1, 2, -1));
            edges.add(new Edge(2, 0, 1));
            writeTestCase(tc++, 3, 2, edges);
        }

        // TC 03: Minimal V = 1, E = 0, S = 0
        writeTestCase(tc++, 1, 0, new ArrayList<>());

        // TC 04: Minimal V = 2, E = 0, S = 0
        writeTestCase(tc++, 2, 0, new ArrayList<>());

        // TC 05: V = 2, negative edge
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, -500));
            writeTestCase(tc++, 2, 0, edges);
        }

        // TC 06: Negative 2-cycle reachable from S -> -1
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, -2));
            edges.add(new Edge(1, 0, -3));
            writeTestCase(tc++, 2, 0, edges);
        }

        // TC 07: Positive 2-cycle reachable
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 2));
            edges.add(new Edge(1, 0, 3));
            writeTestCase(tc++, 2, 0, edges);
        }

        // TC 08: Negative cycle disconnected from S
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 10));
            // negative cycle on nodes 2 and 3
            edges.add(new Edge(2, 3, -10));
            edges.add(new Edge(3, 2, -10));
            writeTestCase(tc++, 4, 0, edges);
        }

        // TC 09: Reachable negative 3-cycle
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 1));
            edges.add(new Edge(1, 2, -5));
            edges.add(new Edge(2, 3, -5));
            edges.add(new Edge(3, 1, -5));
            writeTestCase(tc++, 4, 0, edges);
        }

        // TC 10: Zero weight cycle
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 0));
            edges.add(new Edge(1, 2, 0));
            edges.add(new Edge(2, 0, 0));
            writeTestCase(tc++, 3, 0, edges);
        }

        // TC 11: All disconnected V=10
        writeTestCase(tc++, 10, 3, new ArrayList<>());

        // TC 12: Source in middle of chain 0 <- 1 <- 2(S) -> 3 -> 4
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(2, 1, -5));
            edges.add(new Edge(1, 0, -5));
            edges.add(new Edge(2, 3, 10));
            edges.add(new Edge(3, 4, 10));
            writeTestCase(tc++, 5, 2, edges);
        }

        // TC 13: Star with negative weights
        {
            int V = 10;
            List<Edge> edges = new ArrayList<>();
            for (int i = 1; i < V; i++) edges.add(new Edge(0, i, -i * 100));
            writeTestCase(tc++, V, 0, edges);
        }

        // TC 14: Shortcut with negative edge beating long path
        {
            List<Edge> edges = new ArrayList<>();
            edges.add(new Edge(0, 1, 10));
            edges.add(new Edge(1, 2, 10));
            edges.add(new Edge(2, 3, 10));
            edges.add(new Edge(0, 3, -50)); // direct negative shortcut
            writeTestCase(tc++, 4, 0, edges);
        }

        // TC 15 - 24: Directed line graphs (length 15 to 25) with negative edges
        for (int k = 0; k < 10; k++) {
            int V = 15 + k;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < V - 1; i++) {
                int wt = rng.nextInt(100) - 40; // mix of pos and neg
                edges.add(new Edge(i, i + 1, wt));
            }
            writeTestCase(tc++, V, 0, edges);
        }

        // TC 25 - 34: Graphs with reachable negative cycles of lengths 2, 3, 4, 5
        for (int k = 0; k < 10; k++) {
            int V = 15 + k;
            List<Edge> edges = new ArrayList<>();
            // lead in from S=0 to cycle start
            int cycleLen = 2 + (k % 4);
            int cycleStart = 3;
            edges.add(new Edge(0, 1, 10));
            edges.add(new Edge(1, 2, 10));
            edges.add(new Edge(2, cycleStart, 10));
            for (int i = 0; i < cycleLen; i++) {
                int u = cycleStart + i;
                int v = cycleStart + (i + 1) % cycleLen;
                edges.add(new Edge(u, v, -50));
            }
            // other random edges
            for (int e = 0; e < 20; e++) {
                int u = rng.nextInt(V);
                int v = rng.nextInt(V);
                if (u != v) edges.add(new Edge(u, v, rng.nextInt(200) + 1));
            }
            writeTestCase(tc++, V, 0, edges);
        }

        // TC 35 - 44: Pure DAGs with negative weights (no cycles possible)
        for (int k = 0; k < 10; k++) {
            int V = 20 + k;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                for (int j = i + 1; j < V; j++) {
                    if (rng.nextDouble() < 0.35) {
                        edges.add(new Edge(i, j, rng.nextInt(2000) - 1000));
                    }
                }
            }
            writeTestCase(tc++, V, 0, edges);
        }

        // TC 45 - 60: Medium randomized graphs without negative cycles
        for (int k = 0; k < 16; k++) {
            int V = 30 + k * 3;
            List<Edge> edges = new ArrayList<>();
            // Build DAG first so no cycles, then add positive backward edges
            for (int i = 0; i < V - 1; i++) {
                edges.add(new Edge(i, i + 1, rng.nextInt(100) - 20));
            }
            for (int extra = 0; extra < V * 2; extra++) {
                int u = rng.nextInt(V);
                int v = rng.nextInt(V);
                if (u < v) {
                    edges.add(new Edge(u, v, rng.nextInt(500) - 100));
                } else if (u > v) {
                    // large positive backward edge to prevent negative cycle
                    edges.add(new Edge(u, v, rng.nextInt(500) + 500));
                }
            }
            writeTestCase(tc++, V, 0, edges);
        }

        // TC 61 - 74: Medium randomized graphs with 50% chance of negative cycle
        for (int k = 0; k < 14; k++) {
            int V = 40 + k * 2;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < V - 1; i++) edges.add(new Edge(i, i + 1, rng.nextInt(50) + 1));
            for (int e = 0; e < V * 3; e++) {
                int u = rng.nextInt(V);
                int v = rng.nextInt(V);
                if (u != v) edges.add(new Edge(u, v, rng.nextInt(400) - 50));
            }
            if (k % 2 == 1) {
                // inject guaranteed negative cycle
                edges.add(new Edge(5, 6, -500));
                edges.add(new Edge(6, 5, -500));
            }
            writeTestCase(tc++, V, 0, edges);
        }

        // TC 75 - 84: High V (V: 100 to 300) graphs
        for (int k = 0; k < 10; k++) {
            int V = 100 + k * 20;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < V - 1; i++) edges.add(new Edge(i, i + 1, rng.nextInt(100) - 20));
            for (int e = 0; e < V * 2; e++) {
                int u = rng.nextInt(V);
                int v = rng.nextInt(V);
                if (u < v) edges.add(new Edge(u, v, rng.nextInt(200) - 50));
            }
            writeTestCase(tc++, V, 0, edges);
        }

        // TC 85 - 94: High V graphs with negative cycles
        for (int k = 0; k < 10; k++) {
            int V = 150 + k * 15;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < V - 1; i++) edges.add(new Edge(i, i + 1, 10));
            // add negative cycle near the end
            int u = V - 3;
            int v = V - 2;
            edges.add(new Edge(u, v, -500));
            edges.add(new Edge(v, u, -500));
            writeTestCase(tc++, V, 0, edges);
        }

        // TC 95 - 99: Max scale stress tests (V = 500)
        // TC 95: V = 500 worst-case simple line graph (needs exactly 499 passes)
        {
            int V = 500;
            List<Edge> edges = new ArrayList<>();
            // reverse order in edge list forces 1 update per iteration!
            for (int i = V - 2; i >= 0; i--) {
                edges.add(new Edge(i, i + 1, 1));
            }
            writeTestCase(tc++, V, 0, edges);
        }

        // TC 96: V = 500 line graph with alternating negative and positive weights
        {
            int V = 500;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < V - 1; i++) {
                edges.add(new Edge(i, i + 1, (i % 2 == 0) ? -5 : 10));
            }
            writeTestCase(tc++, V, 0, edges);
        }

        // TC 97: V = 500 with negative cycle at vertex 499
        {
            int V = 500;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < V - 1; i++) edges.add(new Edge(i, i + 1, 1));
            edges.add(new Edge(499, 498, -100)); // cycle 498-499 weight 1 - 100 = -99 < 0
            writeTestCase(tc++, V, 0, edges);
        }

        // TC 98: V = 500 pure DAG with 2500 edges, all negative weights
        {
            int V = 500;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < V - 1; i++) edges.add(new Edge(i, i + 1, -1));
            for (int e = 0; e < 2000; e++) {
                int u = rng.nextInt(V);
                int v = rng.nextInt(V);
                if (u < v) edges.add(new Edge(u, v, -rng.nextInt(500) - 1));
            }
            writeTestCase(tc++, V, 0, edges);
        }

        // TC 99: V = 500 dense graph with mix of positive/negative weights
        {
            int V = 500;
            List<Edge> edges = new ArrayList<>();
            for (int i = 0; i < V - 1; i++) edges.add(new Edge(i, i + 1, rng.nextInt(20) + 1));
            for (int e = 0; e < 2500; e++) {
                int u = rng.nextInt(V);
                int v = rng.nextInt(V);
                if (u != v) edges.add(new Edge(u, v, rng.nextInt(1000) - 10));
            }
            writeTestCase(tc++, V, 0, edges);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Bellman Ford Algorithm.");
    }
}
