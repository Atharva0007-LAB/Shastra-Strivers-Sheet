import java.io.*;
import java.util.*;

public class gen {

    static class Edge {
        int u, v;
        Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }

    static class GraphData {
        int V;
        List<Edge> edges;
        GraphData(int V, List<Edge> edges) {
            this.V = V;
            this.edges = edges;
        }
    }

    public static void main(String[] args) throws IOException {
        File baseDir = new File("d:/MF Shastra/shastra striverz/Detect a cycle in an undirected graph/Testcases");
        if (!baseDir.isAbsolute()) {
            baseDir = new File("../Testcases");
        }
        baseDir.mkdirs();

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<GraphData> testCases = new ArrayList<>();
        Random rng = new Random(501);

        // ==========================================
        // 00 - 02: Platform Samples
        // ==========================================
        // Sample 1: V=6, 7 edges -> true
        testCases.add(new GraphData(6, Arrays.asList(
            new Edge(0, 1), new Edge(0, 3), new Edge(1, 2),
            new Edge(1, 4), new Edge(2, 5), new Edge(3, 4), new Edge(4, 5)
        )));

        // Sample 2: V=4, 3 edges -> false
        testCases.add(new GraphData(4, Arrays.asList(
            new Edge(0, 1), new Edge(0, 2), new Edge(2, 3)
        )));

        // Sample 3: V=4, 4 edges -> true
        testCases.add(new GraphData(4, Arrays.asList(
            new Edge(0, 1), new Edge(0, 2), new Edge(1, 2), new Edge(2, 3)
        )));

        // ==========================================
        // 03 - 14: Minimal Boundaries & Small Cases
        // ==========================================
        // TC 03: V=1, E=0 -> false
        testCases.add(new GraphData(1, Collections.emptyList()));

        // TC 04: V=2, E=0 -> false
        testCases.add(new GraphData(2, Collections.emptyList()));

        // TC 05: V=2, E=1 -> false
        testCases.add(new GraphData(2, Collections.singletonList(new Edge(0, 1))));

        // TC 06: V=3, line (0-1, 1-2) -> false
        testCases.add(new GraphData(3, Arrays.asList(new Edge(0, 1), new Edge(1, 2))));

        // TC 07: V=3, triangle -> true
        testCases.add(new GraphData(3, Arrays.asList(new Edge(0, 1), new Edge(1, 2), new Edge(2, 0))));

        // TC 08: V=4, star graph (center 0) -> false
        testCases.add(new GraphData(4, Arrays.asList(new Edge(0, 1), new Edge(0, 2), new Edge(0, 3))));

        // TC 09: V=4, square cycle -> true
        testCases.add(new GraphData(4, Arrays.asList(new Edge(0, 1), new Edge(1, 2), new Edge(2, 3), new Edge(3, 0))));

        // TC 10: V=4, two disjoint edges -> false
        testCases.add(new GraphData(4, Arrays.asList(new Edge(0, 1), new Edge(2, 3))));

        // TC 11: V=5, binary tree -> false
        testCases.add(new GraphData(5, Arrays.asList(new Edge(0, 1), new Edge(0, 2), new Edge(1, 3), new Edge(1, 4))));

        // TC 12: V=5, pentagon cycle -> true
        testCases.add(new GraphData(5, Arrays.asList(
            new Edge(0, 1), new Edge(1, 2), new Edge(2, 3), new Edge(3, 4), new Edge(4, 0)
        )));

        // TC 13: V=5, isolated vertices only -> false
        testCases.add(new GraphData(5, Collections.emptyList()));

        // TC 14: V=6, two components: component 1 is path, component 2 has cycle -> true
        testCases.add(new GraphData(6, Arrays.asList(
            new Edge(0, 1), new Edge(1, 2),
            new Edge(3, 4), new Edge(4, 5), new Edge(5, 3)
        )));

        // ==========================================
        // 15 - 34: Medium Graphs (V = 10 to 100)
        // ==========================================
        // TC 15: V=10, linear path -> false
        testCases.add(makePathGraph(10));

        // TC 16: V=10, cycle of 10 -> true
        testCases.add(makeCycleGraph(10));

        // TC 17: V=10, star graph -> false
        testCases.add(makeStarGraph(10));

        // TC 18: V=10, star with 1 cross-edge -> true
        testCases.add(makeStarWithCrossEdge(10));

        // TC 19: V=15, random tree -> false
        testCases.add(makeRandomTree(15, rng));

        // TC 20: V=15, random tree + 1 edge -> true
        testCases.add(makeRandomTreePlusEdges(15, 1, rng));

        // TC 21: V=20, forest of 4 trees -> false
        testCases.add(makeForest(20, 4, rng));

        // TC 22: V=20, forest of 4 trees where 1 has a cycle -> true
        testCases.add(makeForestWithCycle(20, 4, rng));

        // TC 23: V=25, complete graph K_5 + 20 isolated vertices -> true
        testCases.add(makeCompleteSubgraph(25, 5));

        // TC 24: V=30, wheel graph -> true
        testCases.add(makeWheelGraph(30));

        // TC 25: V=35, caterpillar tree -> false
        testCases.add(makeCaterpillarTree(35));

        // TC 26: V=35, caterpillar tree + 1 edge -> true
        testCases.add(makeCaterpillarPlusEdge(35));

        // TC 27: V=40, bipartite complete K_3,3 embedded in 40 vertices -> true
        testCases.add(makeBipartiteGraph(40, 3, 3));

        // TC 28: V=50, random tree -> false
        testCases.add(makeRandomTree(50, rng));

        // TC 29: V=50, random tree + 3 edges -> true
        testCases.add(makeRandomTreePlusEdges(50, 3, rng));

        // TC 30: V=60, forest of 6 trees -> false
        testCases.add(makeForest(60, 6, rng));

        // TC 31: V=70, path graph -> false
        testCases.add(makePathGraph(70));

        // TC 32: V=70, path graph + 1 chord -> true
        testCases.add(makeChordalCycle(70));

        // TC 33: V=80, random tree -> false
        testCases.add(makeRandomTree(80, rng));

        // TC 34: V=100, random tree + 5 edges -> true
        testCases.add(makeRandomTreePlusEdges(100, 5, rng));

        // ==========================================
        // 35 - 69: Large Graphs (V = 150 to 2,000)
        // ==========================================
        for (int i = 35; i <= 69; i++) {
            int vCount = 150 + (i - 35) * 55;
            boolean makeAcyclic = (i % 2 == 0);

            if (makeAcyclic) {
                if (i % 4 == 0) {
                    testCases.add(makeRandomTree(vCount, rng));
                } else {
                    testCases.add(makeForest(vCount, 5 + (i % 10), rng));
                }
            } else {
                if (i % 4 == 1) {
                    testCases.add(makeRandomTreePlusEdges(vCount, 1 + rng.nextInt(3), rng));
                } else {
                    testCases.add(makeForestWithCycle(vCount, 4 + (i % 6), rng));
                }
            }
        }

        // ==========================================
        // 70 - 89: Very Large Graphs (V = 2,500 to 8,000)
        // ==========================================
        for (int i = 70; i <= 89; i++) {
            int vCount = 2500 + (i - 70) * 280;
            boolean makeAcyclic = (i % 2 == 0);

            if (makeAcyclic) {
                testCases.add(makeRandomTree(vCount, rng));
            } else {
                testCases.add(makeRandomTreePlusEdges(vCount, 1 + rng.nextInt(4), rng));
            }
        }

        // ==========================================
        // 90 - 99: Extreme Constraints (Up to V = 10^4, E = 10^4)
        // ==========================================
        // TC 90: V = 10000, linear path (E = 9999) -> false
        testCases.add(makePathGraph(10000));

        // TC 91: V = 10000, giant cycle of size 10000 (E = 10000) -> true
        testCases.add(makeCycleGraph(10000));

        // TC 92: V = 10000, star graph (hub connected to 9999 leaves, E = 9999) -> false
        testCases.add(makeStarGraph(10000));

        // TC 93: V = 10000, star graph + 1 leaf edge (E = 10000) -> true
        testCases.add(makeStarWithCrossEdge(10000));

        // TC 94: V = 10000, large random tree (E = 9999) -> false
        testCases.add(makeRandomTree(10000, rng));

        // TC 95: V = 10000, large random tree + 1 back-edge (E = 10000) -> true
        testCases.add(makeRandomTreePlusEdges(10000, 1, rng));

        // TC 96: V = 10000, disconnected forest of 5000 pairs (E = 5000) -> false
        testCases.add(makeForestOfPairs(10000));

        // TC 97: V = 10000, forest of 4998 pairs + 1 triangle (E = 5001) -> true
        testCases.add(makeForestWithTriangle(10000));

        // TC 98: V = 10000, dense multi-cycle graph (E = 10000) -> true
        testCases.add(makeRandomTreePlusEdges(9000, 1000, rng));

        // TC 99: V = 10000, empty graph (E = 0) -> false
        testCases.add(new GraphData(10000, Collections.emptyList()));

        // ==========================================
        // Generate Files
        // ==========================================
        System.out.println("Total test cases queued: " + testCases.size());

        for (int i = 0; i < testCases.size(); i++) {
            GraphData gd = testCases.get(i);
            int V = gd.V;
            int E = gd.edges.size();

            String indexStr = String.format("%02d", i);
            File inFile = new File(inputDir, "input" + indexStr + ".txt");
            File outFile = new File(outputDir, "output" + indexStr + ".txt");

            try (PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)))) {
                inWriter.println(V + " " + E);
                for (Edge edge : gd.edges) {
                    inWriter.println(edge.u + " " + edge.v);
                }
            }

            @SuppressWarnings("unchecked")
            List<Integer>[] adj = new ArrayList[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new ArrayList<>();
            }
            for (Edge edge : gd.edges) {
                adj[edge.u].add(edge.v);
                adj[edge.v].add(edge.u);
            }

            boolean ans = isCycle(V, adj);

            try (PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)))) {
                outWriter.println(ans);
            }
        }

        System.out.println("All 100 test cases generated successfully in Testcases/Input and Testcases/Output!");
    }

    // ==========================================
    // Solver Method (Self-Contained)
    // ==========================================
    public static boolean isCycle(int V, List<Integer>[] adj) {
        if (V <= 2 && adj == null) return false;

        boolean[] visited = new boolean[V];
        int[] queueNode = new int[V];
        int[] queueParent = new int[V];

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                int head = 0;
                int tail = 0;

                visited[i] = true;
                queueNode[tail] = i;
                queueParent[tail] = -1;
                tail++;

                while (head < tail) {
                    int u = queueNode[head];
                    int p = queueParent[head];
                    head++;

                    if (adj[u] != null) {
                        for (int v : adj[u]) {
                            if (!visited[v]) {
                                visited[v] = true;
                                queueNode[tail] = v;
                                queueParent[tail] = u;
                                tail++;
                            } else if (v != p) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    // ==========================================
    // Graph Construction Helpers
    // ==========================================
    static GraphData makePathGraph(int V) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < V - 1; i++) {
            edges.add(new Edge(i, i + 1));
        }
        return new GraphData(V, edges);
    }

    static GraphData makeCycleGraph(int V) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < V - 1; i++) {
            edges.add(new Edge(i, i + 1));
        }
        if (V > 2) {
            edges.add(new Edge(V - 1, 0));
        }
        return new GraphData(V, edges);
    }

    static GraphData makeStarGraph(int V) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i < V; i++) {
            edges.add(new Edge(0, i));
        }
        return new GraphData(V, edges);
    }

    static GraphData makeStarWithCrossEdge(int V) {
        GraphData gd = makeStarGraph(V);
        if (V >= 3) {
            gd.edges.add(new Edge(1, 2));
        }
        return gd;
    }

    static GraphData makeRandomTree(int V, Random rng) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i < V; i++) {
            int p = rng.nextInt(i);
            edges.add(new Edge(p, i));
        }
        return new GraphData(V, edges);
    }

    static GraphData makeRandomTreePlusEdges(int V, int extra, Random rng) {
        GraphData gd = makeRandomTree(V, rng);
        Set<Long> existing = new HashSet<>();
        for (Edge e : gd.edges) {
            int u = Math.min(e.u, e.v);
            int v = Math.max(e.u, e.v);
            existing.add(((long) u << 32) | v);
        }

        int added = 0;
        int attempts = 0;
        while (added < extra && attempts < 1000) {
            attempts++;
            int u = rng.nextInt(V);
            int v = rng.nextInt(V);
            if (u != v) {
                int min = Math.min(u, v);
                int max = Math.max(u, v);
                long key = ((long) min << 32) | max;
                if (!existing.contains(key)) {
                    existing.add(key);
                    gd.edges.add(new Edge(min, max));
                    added++;
                }
            }
        }
        return gd;
    }

    static GraphData makeForest(int V, int components, Random rng) {
        List<Edge> edges = new ArrayList<>();
        int compSize = V / components;
        for (int c = 0; c < components; c++) {
            int start = c * compSize;
            int end = (c == components - 1) ? V : (c + 1) * compSize;
            for (int i = start + 1; i < end; i++) {
                int p = start + rng.nextInt(i - start);
                edges.add(new Edge(p, i));
            }
        }
        return new GraphData(V, edges);
    }

    static GraphData makeForestWithCycle(int V, int components, Random rng) {
        GraphData gd = makeForest(V, components, rng);
        int compSize = V / components;
        if (compSize >= 3) {
            // Add a cycle in component 0
            gd.edges.add(new Edge(0, 2));
        }
        return gd;
    }

    static GraphData makeCompleteSubgraph(int V, int k) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k; j++) {
                edges.add(new Edge(i, j));
            }
        }
        return new GraphData(V, edges);
    }

    static GraphData makeWheelGraph(int V) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i < V; i++) {
            edges.add(new Edge(0, i)); // hub
            int next = (i == V - 1) ? 1 : (i + 1);
            edges.add(new Edge(i, next)); // rim
        }
        return new GraphData(V, edges);
    }

    static GraphData makeCaterpillarTree(int V) {
        List<Edge> edges = new ArrayList<>();
        int spine = Math.max(2, V / 3);
        for (int i = 0; i < spine - 1; i++) {
            edges.add(new Edge(i, i + 1));
        }
        for (int i = spine; i < V; i++) {
            int parent = (i % spine);
            edges.add(new Edge(parent, i));
        }
        return new GraphData(V, edges);
    }

    static GraphData makeCaterpillarPlusEdge(int V) {
        GraphData gd = makeCaterpillarTree(V);
        if (V >= 4) {
            gd.edges.add(new Edge(V - 1, V - 2));
        }
        return gd;
    }

    static GraphData makeBipartiteGraph(int V, int n1, int n2) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                edges.add(new Edge(i, n1 + j));
            }
        }
        return new GraphData(V, edges);
    }

    static GraphData makeChordalCycle(int V) {
        GraphData gd = makeCycleGraph(V);
        if (V >= 5) {
            gd.edges.add(new Edge(1, 3));
        }
        return gd;
    }

    static GraphData makeForestOfPairs(int V) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i + 1 < V; i += 2) {
            edges.add(new Edge(i, i + 1));
        }
        return new GraphData(V, edges);
    }

    static GraphData makeForestWithTriangle(int V) {
        List<Edge> edges = new ArrayList<>();
        // First component is a triangle
        edges.add(new Edge(0, 1));
        edges.add(new Edge(1, 2));
        edges.add(new Edge(2, 0));
        // Remaining are disjoint pairs
        for (int i = 3; i + 1 < V; i += 2) {
            edges.add(new Edge(i, i + 1));
        }
        return new GraphData(V, edges);
    }
}
