import java.io.*;
import java.util.*;

public class gen {

    public static void main(String[] args) throws IOException {
        File baseDir = new File("d:/MF Shastra/shastra striverz/Traversal Techniques/Testcases");
        if (!baseDir.exists()) {
            baseDir = new File("../Testcases");
        }
        if (!baseDir.exists()) {
            baseDir = new File("Testcases");
        }

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<TestCase> testCases = new ArrayList<>();
        Random rng = new Random(42);

        // ==========================================
        // 00 - 02: Platform Samples
        // ==========================================
        // Sample 1
        testCases.add(new TestCase(5, new int[][]{
            {0, 2}, {0, 3}, {0, 1}, {2, 4}
        }));

        // Sample 2
        testCases.add(new TestCase(4, new int[][]{
            {0, 1}, {0, 3}, {1, 2}
        }));

        // Sample 3
        testCases.add(new TestCase(3, new int[][]{
            {0, 1}, {0, 2}
        }));

        // ==========================================
        // 03 - 05: Minimal Boundary Cases
        // ==========================================
        // TC 03: V = 1, E = 0
        testCases.add(new TestCase(1, new int[][]{}));

        // TC 04: V = 2, E = 1
        testCases.add(new TestCase(2, new int[][]{{0, 1}}));

        // TC 05: V = 3, Triangle Cycle
        testCases.add(new TestCase(3, new int[][]{{0, 1}, {1, 2}, {2, 0}}));

        // ==========================================
        // 06 - 15: Canonical Graph Types
        // ==========================================
        // TC 06: Path Graph V = 10 (0-1-2-...-9)
        testCases.add(makePathGraph(10, true));

        // TC 07: Path Graph V = 15 with reverse edge ordering
        testCases.add(makePathGraph(15, false));

        // TC 08: Star Graph V = 20 centered at 0
        testCases.add(makeStarGraph(20, 0));

        // TC 09: Star Graph V = 25 centered at 5
        testCases.add(makeStarGraph(25, 5));

        // TC 10: Simple Cycle Graph C_20
        testCases.add(makeCycleGraph(20));

        // TC 11: Complete Binary Tree rooted at 0 (V = 31)
        testCases.add(makeBinaryTree(31));

        // TC 12: Ternary Tree rooted at 0 (V = 40)
        testCases.add(makeKaryTree(40, 3));

        // TC 13: Complete Graph K_6 (V = 6, E = 15)
        testCases.add(makeCompleteGraph(6));

        // TC 14: Complete Graph K_12 (V = 12, E = 66)
        testCases.add(makeCompleteGraph(12));

        // TC 15: Wheel Graph (V = 15: center 0 + cycle)
        testCases.add(makeWheelGraph(15));

        // ==========================================
        // 16 - 30: Small Structured & Special Topologies
        // ==========================================
        // TC 16: Grid 3x3 (V = 9)
        testCases.add(makeGridGraph(3, 3));

        // TC 17: Grid 5x5 (V = 25)
        testCases.add(makeGridGraph(5, 5));

        // TC 18: Ladder Graph 2x10 (V = 20)
        testCases.add(makeGridGraph(2, 10));

        // TC 19: Barbell Graph (two K_5 connected by path of 4)
        testCases.add(makeBarbellGraph(5, 4));

        // TC 20: Lollipop Graph (K_6 with path of 6)
        testCases.add(makeLollipopGraph(6, 6));

        // TC 21: Caterpillar tree (V = 30)
        testCases.add(makeCaterpillar(30, 10, rng));

        // TC 22: Disjoint degree tree (DFS goes deep, BFS stays shallow)
        testCases.add(makeBfsDfsDivergenceGraph(40));

        // TC 23: Complete Bipartite Graph K_{3, 5} (V = 8)
        testCases.add(makeBipartiteGraph(3, 5));

        // TC 24: Complete Bipartite Graph K_{5, 7} (V = 12)
        testCases.add(makeBipartiteGraph(5, 7));

        // TC 25: Prism Graph (V = 16)
        testCases.add(makeGridGraph(2, 8));

        // TC 26: Random Connected Small V = 15, E = 14 (Tree)
        testCases.add(makeRandomConnected(15, 14, rng));

        // TC 27: Random Connected Small V = 15, E = 25 (Multiple cycles)
        testCases.add(makeRandomConnected(15, 25, rng));

        // TC 28: Random Connected Small V = 20, E = 30
        testCases.add(makeRandomConnected(20, 30, rng));

        // TC 29: Tree with high degree root (0 has 20 children, each has 1 child)
        testCases.add(makeStarWithLeaves(41, 20));

        // TC 30: Flower / Rose Graph (Petals attached to 0)
        testCases.add(makeFlowerGraph(7, 3)); // 7 petals of length 3 each

        // ==========================================
        // 31 - 60: Medium Random & Structured Graphs
        // ==========================================
        int[] mediumSizes = {
            50, 75, 100, 120, 150, 180, 200, 250, 300, 350,
            400, 450, 500, 600, 700, 800, 900, 1000, 1200, 1500,
            1600, 1700, 1800, 1900, 2000, 2200, 2400, 2500, 2800, 3000
        };

        for (int i = 0; i < mediumSizes.length; i++) {
            int v = mediumSizes[i];
            int e;
            if (i % 3 == 0) {
                // Sparse Tree
                e = v - 1;
            } else if (i % 3 == 1) {
                // Medium Density
                e = Math.min(v * 2, v * (v - 1) / 2);
            } else {
                // Heavier Density
                e = Math.min(v * 3 + rng.nextInt(v), Math.min(10000, v * (v - 1) / 2));
            }
            testCases.add(makeRandomConnected(v, e, rng));
        }

        // ==========================================
        // 61 - 80: Tricky / Anti-heuristic & Specific Topologies
        // ==========================================
        // TC 61: Deep path V = 3500 (Tests deep traversal)
        testCases.add(makePathGraph(3500, true));

        // TC 62: Deep path V = 5000 (Tests linear recursion handling)
        testCases.add(makePathGraph(5000, false));

        // TC 63: Large Star Graph V = 4000 centered at 0
        testCases.add(makeStarGraph(4000, 0));

        // TC 64: Large Star Graph V = 4000 centered at vertex 2000
        testCases.add(makeStarGraph(4000, 2000));

        // TC 65: Large binary tree V = 4095
        testCases.add(makeBinaryTree(4095));

        // TC 66: Large Grid Graph 50x50 (V = 2500, E = 4900)
        testCases.add(makeGridGraph(50, 50));

        // TC 67: Large Grid Graph 60x60 (V = 3600, E = 7080)
        testCases.add(makeGridGraph(60, 60));

        // TC 68: Multi-chordal cycle V = 3000
        testCases.add(makeChordalCycle(3000, 4500, rng));

        // TC 69: Long Path leading to Dense Subgraph
        testCases.add(makePathToDense(2000, 200, rng));

        // TC 70: Dense Subgraph at 0 leading to Long Path
        testCases.add(makeDenseToPath(200, 2000, rng));

        // TC 71-80: Varied medium-large topologies (V = 3500 to 5000)
        for (int i = 71; i <= 80; i++) {
            int v = 3500 + (i - 71) * 150;
            int e = Math.min(10000, v + rng.nextInt(2000));
            testCases.add(makeRandomConnected(v, e, rng));
        }

        // ==========================================
        // 81 - 99: Max Constraints & Stress Tests (V, E up to 10000)
        // ==========================================
        // TC 81: Max Line Graph V = 10000, E = 9999
        testCases.add(makePathGraph(10000, true));

        // TC 82: Max Line Graph V = 10000, E = 9999 (interleaved edge directions)
        testCases.add(makePathGraph(10000, false));

        // TC 83: Max Star Graph V = 10000, E = 9999 centered at 0
        testCases.add(makeStarGraph(10000, 0));

        // TC 84: Max Star Graph V = 10000, E = 9999 centered at 9999
        testCases.add(makeStarGraph(10000, 9999));

        // TC 85: Max Tree V = 10000 (Random Spanning Tree)
        testCases.add(makeRandomConnected(10000, 9999, rng));

        // TC 86: Max Constraint V = 10000, E = 10000 (Tree + 1 cycle)
        testCases.add(makeRandomConnected(10000, 10000, rng));

        // TC 87: Max Constraint V = 9000, E = 10000
        testCases.add(makeRandomConnected(9000, 10000, rng));

        // TC 88: Max Constraint V = 8000, E = 10000
        testCases.add(makeRandomConnected(8000, 10000, rng));

        // TC 89: Max Constraint V = 7000, E = 10000
        testCases.add(makeRandomConnected(7000, 10000, rng));

        // TC 90: Max Constraint V = 6000, E = 10000
        testCases.add(makeRandomConnected(6000, 10000, rng));

        // TC 91: Max Constraint V = 5000, E = 10000
        testCases.add(makeRandomConnected(5000, 10000, rng));

        // TC 92: Max Constraint V = 4000, E = 10000
        testCases.add(makeRandomConnected(4000, 10000, rng));

        // TC 93: Max Constraint V = 3000, E = 10000
        testCases.add(makeRandomConnected(3000, 10000, rng));

        // TC 94: Max Constraint V = 2000, E = 10000
        testCases.add(makeRandomConnected(2000, 10000, rng));

        // TC 95: Max Constraint V = 1500, E = 10000
        testCases.add(makeRandomConnected(1500, 10000, rng));

        // TC 96: Max Constraint V = 1000, E = 10000
        testCases.add(makeRandomConnected(1000, 10000, rng));

        // TC 97: Max Constraint V = 800, E = 10000
        testCases.add(makeRandomConnected(800, 10000, rng));

        // TC 98: Max Constraint V = 500, E = 10000
        testCases.add(makeRandomConnected(500, 10000, rng));

        // TC 99: Max Constraint V = 10000, E = 10000 with mixed high-degree hubs
        testCases.add(makeMultiHubGraph(10000, 10000, 10, rng));

        System.out.println("Total test cases prepared: " + testCases.size());
        if (testCases.size() != 100) {
            throw new RuntimeException("Expected 100 testcases, but found: " + testCases.size());
        }

        // Write all 100 testcases to Input and Output
        for (int i = 0; i < testCases.size(); i++) {
            String fileName = String.format("%02d", i);
            File inFile = new File(inputDir, "input" + fileName + ".txt");
            File outFile = new File(outputDir, "output" + fileName + ".txt");

            TestCase tc = testCases.get(i);

            // Write input
            try (PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)))) {
                inWriter.println(tc.V + " " + tc.edges.length);
                for (int[] edge : tc.edges) {
                    inWriter.println(edge[0] + " " + edge[1]);
                }
            }

            // Solve
            List<List<Integer>> adj = new ArrayList<>(tc.V);
            for (int v = 0; v < tc.V; v++) {
                adj.add(new ArrayList<>());
            }
            for (int[] edge : tc.edges) {
                adj.get(edge[0]).add(edge[1]);
                adj.get(edge[1]).add(edge[0]);
            }

            List<Integer> dfs = dfsOfGraph(tc.V, adj);
            List<Integer> bfs = bfsOfGraph(tc.V, adj);

            try (PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)))) {
                StringBuilder sbDfs = new StringBuilder();
                for (int j = 0; j < dfs.size(); j++) {
                    if (j > 0) sbDfs.append(' ');
                    sbDfs.append(dfs.get(j));
                }
                outWriter.println(sbDfs.toString());

                StringBuilder sbBfs = new StringBuilder();
                for (int j = 0; j < bfs.size(); j++) {
                    if (j > 0) sbBfs.append(' ');
                    sbBfs.append(bfs.get(j));
                }
                outWriter.println(sbBfs.toString());
            }
        }

        System.out.println("All 100 test cases generated successfully in Testcases/Input and Testcases/Output!");
    }

    public static List<Integer> dfsOfGraph(int V, List<List<Integer>> adj) {
        List<Integer> dfsResult = new ArrayList<>(V);
        if (V == 0) return dfsResult;

        boolean[] visited = new boolean[V];
        int[] stack = new int[V];
        int[] edgePtr = new int[V];
        int top = 0;

        stack[top] = 0;
        edgePtr[top] = 0;
        visited[0] = true;
        dfsResult.add(0);

        while (top >= 0) {
            int u = stack[top];
            List<Integer> neighbors = adj.get(u);
            if (edgePtr[top] < neighbors.size()) {
                int v = neighbors.get(edgePtr[top]++);
                if (!visited[v]) {
                    visited[v] = true;
                    dfsResult.add(v);
                    top++;
                    stack[top] = v;
                    edgePtr[top] = 0;
                }
            } else {
                top--;
            }
        }
        return dfsResult;
    }

    public static List<Integer> bfsOfGraph(int V, List<List<Integer>> adj) {
        List<Integer> bfsResult = new ArrayList<>(V);
        if (V == 0) return bfsResult;

        boolean[] visited = new boolean[V];
        int[] queue = new int[V];
        int head = 0, tail = 0;

        visited[0] = true;
        queue[tail++] = 0;

        while (head < tail) {
            int u = queue[head++];
            bfsResult.add(u);
            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    queue[tail++] = v;
                }
            }
        }
        return bfsResult;
    }

    // ==========================================
    // Helper Data Structures & Generators
    // ==========================================
    static class TestCase {
        int V;
        int[][] edges;

        TestCase(int V, int[][] edges) {
            this.V = V;
            this.edges = edges;
        }
    }

    static TestCase makePathGraph(int V, boolean forward) {
        int[][] edges = new int[V - 1][2];
        for (int i = 0; i < V - 1; i++) {
            if (forward) {
                edges[i][0] = i;
                edges[i][1] = i + 1;
            } else {
                edges[i][0] = (i % 2 == 0) ? (i + 1) : i;
                edges[i][1] = (i % 2 == 0) ? i : (i + 1);
            }
        }
        return new TestCase(V, edges);
    }

    static TestCase makeStarGraph(int V, int center) {
        int[][] edges = new int[V - 1][2];
        int idx = 0;
        for (int i = 0; i < V; i++) {
            if (i == center) continue;
            edges[idx][0] = center;
            edges[idx][1] = i;
            idx++;
        }
        return new TestCase(V, edges);
    }

    static TestCase makeCycleGraph(int V) {
        int[][] edges = new int[V][2];
        for (int i = 0; i < V; i++) {
            edges[i][0] = i;
            edges[i][1] = (i + 1) % V;
        }
        return new TestCase(V, edges);
    }

    static TestCase makeBinaryTree(int V) {
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < V) edges.add(new int[]{i, left});
            if (right < V) edges.add(new int[]{i, right});
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeKaryTree(int V, int K) {
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            for (int c = 1; c <= K; c++) {
                int child = K * i + c;
                if (child < V) {
                    edges.add(new int[]{i, child});
                }
            }
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeCompleteGraph(int V) {
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                edges.add(new int[]{i, j});
            }
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeWheelGraph(int V) {
        List<int[]> edges = new ArrayList<>();
        int center = 0;
        int cycleLen = V - 1;
        for (int i = 1; i < V; i++) {
            edges.add(new int[]{center, i});
            int next = (i == V - 1) ? 1 : (i + 1);
            edges.add(new int[]{i, next});
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeGridGraph(int R, int C) {
        int V = R * C;
        List<int[]> edges = new ArrayList<>();
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                int u = r * C + c;
                if (r + 1 < R) edges.add(new int[]{u, (r + 1) * C + c});
                if (c + 1 < C) edges.add(new int[]{u, r * C + (c + 1)});
            }
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeBarbellGraph(int K, int P) {
        int V = 2 * K + P;
        List<int[]> edges = new ArrayList<>();
        // Clique 1: 0 to K-1
        for (int i = 0; i < K; i++) {
            for (int j = i + 1; j < K; j++) {
                edges.add(new int[]{i, j});
            }
        }
        // Path: K-1 to K-1+P
        for (int i = 0; i < P; i++) {
            edges.add(new int[]{K - 1 + i, K + i});
        }
        // Clique 2: K+P-1 to V-1
        int c2Start = K + P - 1;
        for (int i = c2Start; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                edges.add(new int[]{i, j});
            }
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeLollipopGraph(int K, int P) {
        int V = K + P;
        List<int[]> edges = new ArrayList<>();
        // Clique: 0 to K-1
        for (int i = 0; i < K; i++) {
            for (int j = i + 1; j < K; j++) {
                edges.add(new int[]{i, j});
            }
        }
        // Path: K-1 to V-1
        for (int i = K - 1; i < V - 1; i++) {
            edges.add(new int[]{i, i + 1});
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeCaterpillar(int V, int spineLen, Random rng) {
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < spineLen - 1; i++) {
            edges.add(new int[]{i, i + 1});
        }
        for (int i = spineLen; i < V; i++) {
            int attach = rng.nextInt(spineLen);
            edges.add(new int[]{attach, i});
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeBfsDfsDivergenceGraph(int V) {
        List<int[]> edges = new ArrayList<>();
        edges.add(new int[]{0, 1});
        edges.add(new int[]{0, 2});
        int half = V / 2;
        for (int i = 1; i < half; i++) {
            edges.add(new int[]{i, i + 1});
        }
        for (int i = half + 1; i < V - 1; i++) {
            edges.add(new int[]{i, i + 1});
        }
        if (half + 1 < V) {
            edges.add(new int[]{2, half + 1});
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeBipartiteGraph(int N, int M) {
        int V = N + M;
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                edges.add(new int[]{i, N + j});
            }
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeStarWithLeaves(int V, int numBranches) {
        List<int[]> edges = new ArrayList<>();
        int node = 1;
        for (int b = 0; b < numBranches && node < V; b++) {
            int branchRoot = node++;
            edges.add(new int[]{0, branchRoot});
            if (node < V) {
                int leaf = node++;
                edges.add(new int[]{branchRoot, leaf});
            }
        }
        while (node < V) {
            edges.add(new int[]{0, node++});
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeFlowerGraph(int petals, int petalLength) {
        int V = 1 + petals * (petalLength - 1);
        List<int[]> edges = new ArrayList<>();
        int curr = 1;
        for (int p = 0; p < petals; p++) {
            int first = curr++;
            edges.add(new int[]{0, first});
            int prev = first;
            for (int i = 1; i < petalLength - 1; i++) {
                int next = curr++;
                edges.add(new int[]{prev, next});
                prev = next;
            }
            edges.add(new int[]{prev, 0});
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeChordalCycle(int V, int E, Random rng) {
        Set<Long> edgeSet = new HashSet<>();
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            int u = i, v = (i + 1) % V;
            edges.add(new int[]{u, v});
            edgeSet.add(packEdge(u, v));
        }
        while (edges.size() < E && edgeSet.size() < (long) V * (V - 1) / 2) {
            int u = rng.nextInt(V);
            int v = rng.nextInt(V);
            if (u != v) {
                long key = packEdge(u, v);
                if (edgeSet.add(key)) {
                    edges.add(new int[]{u, v});
                }
            }
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makePathToDense(int pathLen, int denseLen, Random rng) {
        int V = pathLen + denseLen;
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < pathLen - 1; i++) {
            edges.add(new int[]{i, i + 1});
        }
        edges.add(new int[]{pathLen - 1, pathLen});
        for (int i = pathLen; i < V; i++) {
            for (int j = i + 1; j < V && edges.size() < 9500; j++) {
                edges.add(new int[]{i, j});
            }
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeDenseToPath(int denseLen, int pathLen, Random rng) {
        int V = denseLen + pathLen;
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < denseLen; i++) {
            for (int j = i + 1; j < denseLen && edges.size() < 9500; j++) {
                edges.add(new int[]{i, j});
            }
        }
        edges.add(new int[]{0, denseLen});
        for (int i = denseLen; i < V - 1; i++) {
            edges.add(new int[]{i, i + 1});
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeMultiHubGraph(int V, int E, int hubCount, Random rng) {
        Set<Long> edgeSet = new HashSet<>();
        List<int[]> edges = new ArrayList<>();

        // Spanning tree
        for (int i = 1; i < V; i++) {
            int parent = (i < hubCount) ? (i - 1) : rng.nextInt(Math.min(i, hubCount));
            edges.add(new int[]{parent, i});
            edgeSet.add(packEdge(parent, i));
        }

        while (edges.size() < E) {
            int u = rng.nextInt(hubCount);
            int v = rng.nextInt(V);
            if (u != v) {
                long key = packEdge(u, v);
                if (edgeSet.add(key)) {
                    edges.add(new int[]{u, v});
                }
            }
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeRandomConnected(int V, int E, Random rng) {
        if (E < V - 1) E = V - 1;
        long maxEdges = (long) V * (V - 1) / 2;
        if (E > maxEdges) E = (int) maxEdges;
        if (E > 10000) E = 10000;

        Set<Long> edgeSet = new HashSet<>();
        List<int[]> edges = new ArrayList<>(E);

        // Build a random spanning tree
        for (int i = 1; i < V; i++) {
            int parent = rng.nextInt(i);
            edges.add(new int[]{parent, i});
            edgeSet.add(packEdge(parent, i));
        }

        // Add additional random edges until count is reached
        int attempts = 0;
        while (edges.size() < E && attempts < 100000) {
            attempts++;
            int u = rng.nextInt(V);
            int v = rng.nextInt(V);
            if (u == v) continue;
            long key = packEdge(u, v);
            if (edgeSet.add(key)) {
                edges.add(new int[]{u, v});
            }
        }

        // Shuffle edges
        Collections.shuffle(edges, rng);

        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static long packEdge(int u, int v) {
        int min = Math.min(u, v);
        int max = Math.max(u, v);
        return (((long) min) << 32) | (max & 0xFFFFFFFFL);
    }
}
