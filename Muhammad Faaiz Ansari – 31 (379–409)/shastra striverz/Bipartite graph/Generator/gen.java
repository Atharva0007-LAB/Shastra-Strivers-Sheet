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
        File baseDir = new File("d:/MF Shastra/shastra striverz/Bipartite graph/Testcases");
        if (!baseDir.isAbsolute()) {
            baseDir = new File("../Testcases");
        }
        baseDir.mkdirs();

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<GraphData> testCases = new ArrayList<>();
        Random rng = new Random(499);

        // ==========================================
        // 00 - 02: Platform Samples
        // ==========================================
        // Sample 1: 4-cycle -> true
        testCases.add(new GraphData(4, Arrays.asList(
            new Edge(0, 1), new Edge(0, 3), new Edge(1, 2), new Edge(2, 3)
        )));

        // Sample 2: V=4 with triangle (0,1,2) -> false
        testCases.add(new GraphData(4, Arrays.asList(
            new Edge(0, 1), new Edge(0, 2), new Edge(0, 3), new Edge(2, 1), new Edge(3, 2)
        )));

        // Sample 3: V=5 pentagon cycle -> false
        testCases.add(new GraphData(5, Arrays.asList(
            new Edge(0, 1), new Edge(0, 3), new Edge(1, 2), new Edge(2, 4), new Edge(4, 3)
        )));

        // ==========================================
        // 03 - 14: Minimal Boundaries & Small Cases
        // ==========================================
        // TC 03: V=1, E=0 -> true
        testCases.add(new GraphData(1, Collections.emptyList()));

        // TC 04: V=2, E=0 -> true
        testCases.add(new GraphData(2, Collections.emptyList()));

        // TC 05: V=2, E=1 -> true
        testCases.add(new GraphData(2, Collections.singletonList(new Edge(0, 1))));

        // TC 06: V=3, line (tree) -> true
        testCases.add(new GraphData(3, Arrays.asList(new Edge(0, 1), new Edge(1, 2))));

        // TC 07: V=3, triangle -> false
        testCases.add(new GraphData(3, Arrays.asList(new Edge(0, 1), new Edge(1, 2), new Edge(2, 0))));

        // TC 08: V=4, star graph -> true
        testCases.add(new GraphData(4, Arrays.asList(new Edge(0, 1), new Edge(0, 2), new Edge(0, 3))));

        // TC 09: V=4, square cycle -> true
        testCases.add(new GraphData(4, Arrays.asList(new Edge(0, 1), new Edge(1, 2), new Edge(2, 3), new Edge(3, 0))));

        // TC 10: V=5, pentagon cycle -> false
        testCases.add(makeCycleGraph(5));

        // TC 11: V=6, hexagon cycle -> true
        testCases.add(makeCycleGraph(6));

        // TC 12: V=5, tree -> true
        testCases.add(new GraphData(5, Arrays.asList(new Edge(0, 1), new Edge(0, 2), new Edge(1, 3), new Edge(1, 4))));

        // TC 13: V=6, two components: path + triangle -> false
        testCases.add(new GraphData(6, Arrays.asList(
            new Edge(0, 1), new Edge(1, 2),
            new Edge(3, 4), new Edge(4, 5), new Edge(5, 3)
        )));

        // TC 14: V=8, two components: two 4-cycles -> true
        testCases.add(new GraphData(8, Arrays.asList(
            new Edge(0, 1), new Edge(1, 2), new Edge(2, 3), new Edge(3, 0),
            new Edge(4, 5), new Edge(5, 6), new Edge(6, 7), new Edge(7, 4)
        )));

        // ==========================================
        // 15 - 34: Medium Graphs (V = 10 to 100)
        // ==========================================
        // TC 15: V=10, complete bipartite K_5,5 -> true
        testCases.add(makeCompleteBipartite(10, 5, 5));

        // TC 16: V=7, odd cycle of length 7 -> false
        testCases.add(makeCycleGraph(7));

        // TC 17: V=8, even cycle of length 8 -> true
        testCases.add(makeCycleGraph(8));

        // TC 18: V=9, odd cycle of length 9 -> false
        testCases.add(makeCycleGraph(9));

        // TC 19: V=10, even cycle of length 10 -> true
        testCases.add(makeCycleGraph(10));

        // TC 20: V=15, random tree -> true
        testCases.add(makeRandomTree(15, rng));

        // TC 21: V=15, tree with odd chord -> false
        testCases.add(makeTreeWithOddChord(15, rng));

        // TC 22: V=15, tree with even chord -> true
        testCases.add(makeTreeWithEvenChord(15, rng));

        // TC 23: V=20, 4x5 grid graph -> true
        testCases.add(makeGridGraph(4, 5));

        // TC 24: V=20, 4x5 grid graph with diagonal edge across cell -> false
        testCases.add(makeGridWithDiagonal(4, 5));

        // TC 25: V=25, odd cycle of 25 -> false
        testCases.add(makeCycleGraph(25));

        // TC 26: V=26, even cycle of 26 -> true
        testCases.add(makeCycleGraph(26));

        // TC 27: V=30, bipartite graph with random cross-edges -> true
        testCases.add(makeRandomBipartite(30, 0.3, rng));

        // TC 28: V=30, bipartite graph with 1 internal cross-edge -> false
        testCases.add(makeNonBipartite(30, 0.3, rng));

        // TC 29: V=40, forest of 4 trees -> true
        testCases.add(makeForest(40, 4, rng));

        // TC 30: V=40, forest where 1 component has a triangle -> false
        testCases.add(makeForestWithOddCycle(40, 4, rng));

        // TC 31: V=50, random tree -> true
        testCases.add(makeRandomTree(50, rng));

        // TC 32: V=50, tree with triangle attached -> false
        testCases.add(makeTreeWithOddChord(50, rng));

        // TC 33: V=70, bipartite graph -> true
        testCases.add(makeRandomBipartite(70, 0.15, rng));

        // TC 34: V=100, random tree with even chord -> true
        testCases.add(makeTreeWithEvenChord(100, rng));

        // ==========================================
        // 35 - 69: Large Graphs (V = 150 to 2,000)
        // ==========================================
        for (int i = 35; i <= 69; i++) {
            int vCount = 150 + (i - 35) * 55;
            boolean makeBip = (i % 2 == 0);

            if (makeBip) {
                if (i % 4 == 0) {
                    testCases.add(makeRandomTree(vCount, rng));
                } else {
                    testCases.add(makeRandomBipartite(vCount, 0.05, rng));
                }
            } else {
                if (i % 4 == 1) {
                    testCases.add(makeTreeWithOddChord(vCount, rng));
                } else {
                    testCases.add(makeNonBipartite(vCount, 0.05, rng));
                }
            }
        }

        // ==========================================
        // 70 - 89: Very Large Graphs (V = 2,500 to 8,000)
        // ==========================================
        for (int i = 70; i <= 89; i++) {
            int vCount = 2500 + (i - 70) * 280;
            boolean makeBip = (i % 2 == 0);

            if (makeBip) {
                testCases.add(makeRandomBipartite(vCount, 0.003, rng));
            } else {
                testCases.add(makeNonBipartite(vCount, 0.003, rng));
            }
        }

        // ==========================================
        // 90 - 99: Extreme Constraints (Up to V = 10^4, E = 10^4)
        // ==========================================
        // TC 90: V = 10000, linear path (E = 9999) -> true
        testCases.add(makePathGraph(10000));

        // TC 91: V = 10000, even cycle of size 10000 (E = 10000) -> true
        testCases.add(makeCycleGraph(10000));

        // TC 92: V = 9999, odd cycle of size 9999 (E = 9999) -> false
        testCases.add(makeCycleGraph(9999));

        // TC 93: V = 10000, star graph (E = 9999) -> true
        testCases.add(makeStarGraph(10000));

        // TC 94: V = 10000, star graph with 1 leaf edge (creates triangle) -> false
        testCases.add(makeStarWithTriangle(10000));

        // TC 95: V = 10000, random tree (E = 9999) -> true
        testCases.add(makeRandomTree(10000, rng));

        // TC 96: V = 10000, random tree with even chord -> true
        testCases.add(makeTreeWithEvenChord(10000, rng));

        // TC 97: V = 10000, random tree with odd chord -> false
        testCases.add(makeTreeWithOddChord(10000, rng));

        // TC 98: V = 10000, forest of 5000 pairs -> true
        testCases.add(makeForestOfPairs(10000));

        // TC 99: V = 10000, empty graph (E = 0) -> true
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

            boolean ans = isBipartite(V, adj);

            try (PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)))) {
                outWriter.println(ans);
            }
        }

        System.out.println("All 100 test cases generated successfully in Testcases/Input and Testcases/Output!");
    }

    // ==========================================
    // Solver Method (Self-Contained)
    // ==========================================
    public static boolean isBipartite(int V, List<Integer>[] adj) {
        if (V <= 2 && adj == null) return true;

        int[] color = new int[V];
        Arrays.fill(color, -1);
        int[] queue = new int[V];

        for (int i = 0; i < V; i++) {
            if (color[i] == -1) {
                int head = 0, tail = 0;
                color[i] = 0;
                queue[tail++] = i;

                while (head < tail) {
                    int u = queue[head++];

                    if (adj[u] != null) {
                        for (int v : adj[u]) {
                            if (color[v] == -1) {
                                color[v] = 1 - color[u];
                                queue[tail++] = v;
                            } else if (color[v] == color[u]) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
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

    static GraphData makeStarWithTriangle(int V) {
        GraphData gd = makeStarGraph(V);
        if (V >= 3) {
            gd.edges.add(new Edge(1, 2));
        }
        return gd;
    }

    static GraphData makeCompleteBipartite(int V, int n1, int n2) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                edges.add(new Edge(i, n1 + j));
            }
        }
        return new GraphData(V, edges);
    }

    static GraphData makeRandomTree(int V, Random rng) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i < V; i++) {
            int p = rng.nextInt(i);
            edges.add(new Edge(p, i));
        }
        return new GraphData(V, edges);
    }

    static GraphData makeTreeWithOddChord(int V, Random rng) {
        // Build tree and determine depth of each node
        int[] parent = new int[V];
        int[] depth = new int[V];
        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i < V; i++) {
            parent[i] = rng.nextInt(i);
            depth[i] = depth[parent[i]] + 1;
            edges.add(new Edge(parent[i], i));
        }
        // An odd cycle is formed by connecting two nodes with the SAME depth parity (e.g. depth diff is even, adding 1 edge makes cycle odd)
        for (int i = 1; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (depth[i] % 2 == depth[j] % 2 && parent[i] != j && parent[j] != i) {
                    edges.add(new Edge(i, j));
                    return new GraphData(V, edges);
                }
            }
        }
        // Fallback: connect 1 and 2 if both attached to 0
        if (V >= 3) edges.add(new Edge(1, 2));
        return new GraphData(V, edges);
    }

    static GraphData makeTreeWithEvenChord(int V, Random rng) {
        int[] parent = new int[V];
        int[] depth = new int[V];
        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i < V; i++) {
            parent[i] = rng.nextInt(i);
            depth[i] = depth[parent[i]] + 1;
            edges.add(new Edge(parent[i], i));
        }
        // An even cycle is formed by connecting two nodes with OPPOSITE depth parity
        for (int i = 1; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (depth[i] % 2 != depth[j] % 2 && parent[i] != j && parent[j] != i) {
                    edges.add(new Edge(i, j));
                    return new GraphData(V, edges);
                }
            }
        }
        return new GraphData(V, edges);
    }

    static GraphData makeGridGraph(int R, int C) {
        int V = R * C;
        List<Edge> edges = new ArrayList<>();
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                int u = r * C + c;
                if (r + 1 < R) edges.add(new Edge(u, (r + 1) * C + c));
                if (c + 1 < C) edges.add(new Edge(u, r * C + (c + 1)));
            }
        }
        return new GraphData(V, edges);
    }

    static GraphData makeGridWithDiagonal(int R, int C) {
        GraphData gd = makeGridGraph(R, C);
        // Add diagonal edge across top-left cell: (0, 0) to (1, 1) -> forms triangle with (0, 1)
        if (R >= 2 && C >= 2) {
            gd.edges.add(new Edge(0, C + 1));
        }
        return gd;
    }

    static GraphData makeRandomBipartite(int V, double prob, Random rng) {
        int n1 = V / 2;
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < n1; i++) {
            for (int j = n1; j < V; j++) {
                if (rng.nextDouble() < prob) {
                    edges.add(new Edge(i, j));
                    if (edges.size() >= 10000) break;
                }
            }
            if (edges.size() >= 10000) break;
        }
        // Ensure at least 1 edge if possible
        if (edges.isEmpty() && V >= 2) {
            edges.add(new Edge(0, n1));
        }
        return new GraphData(V, edges);
    }

    static GraphData makeNonBipartite(int V, double prob, Random rng) {
        GraphData gd = makeRandomBipartite(V, prob, rng);
        int n1 = V / 2;
        // Add an internal edge within set A (0 to 1) or set B
        if (n1 >= 2) {
            gd.edges.add(new Edge(0, 1));
        } else if (V >= 3) {
            gd.edges.add(new Edge(1, 2));
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

    static GraphData makeForestWithOddCycle(int V, int components, Random rng) {
        GraphData gd = makeForest(V, components, rng);
        int compSize = V / components;
        if (compSize >= 3) {
            gd.edges.add(new Edge(0, 2)); // creates triangle in comp 0
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
}
