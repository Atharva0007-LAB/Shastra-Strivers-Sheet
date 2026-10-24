import java.io.*;
import java.util.*;

public class gen {

    public static void main(String[] args) throws IOException {
        File baseDir = new File("d:/MF Shastra/shastra striverz/Connected Components/Testcases");
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
        Random rng = new Random(1032);

        // ==========================================
        // 00 - 02: Platform Samples
        // ==========================================
        // Sample 1: V = 4, edges = [[0, 1], [1, 2]] -> 2 components
        testCases.add(new TestCase(4, new int[][]{{0, 1}, {1, 2}}));

        // Sample 2: V = 7, edges = [[0, 1], [1, 2], [2, 3], [4, 5]] -> 3 components
        testCases.add(new TestCase(7, new int[][]{{0, 1}, {1, 2}, {2, 3}, {4, 5}}));

        // Sample 3: V = 5, edges = [[0, 1], [1, 2], [3, 4]] -> 2 components
        testCases.add(new TestCase(5, new int[][]{{0, 1}, {1, 2}, {3, 4}}));

        // ==========================================
        // 03 - 06: Minimal Boundary Cases
        // ==========================================
        // TC 03: V = 1, E = 0 -> 1 component
        testCases.add(new TestCase(1, new int[][]{}));

        // TC 04: V = 2, E = 0 -> 2 components
        testCases.add(new TestCase(2, new int[][]{}));

        // TC 05: V = 2, E = 1 -> 1 component
        testCases.add(new TestCase(2, new int[][]{{0, 1}}));

        // TC 06: V = 5, E = 0 (all isolated) -> 5 components
        testCases.add(new TestCase(5, new int[][]{}));

        // ==========================================
        // 07 - 15: Small Canonical Topologies
        // ==========================================
        // TC 07: Completely connected triangle V = 3, E = 3 -> 1 component
        testCases.add(new TestCase(3, new int[][]{{0, 1}, {1, 2}, {2, 0}}));

        // TC 08: Two disjoint triangles V = 6, E = 6 -> 2 components
        testCases.add(new TestCase(6, new int[][]{{0, 1}, {1, 2}, {2, 0}, {3, 4}, {4, 5}, {5, 3}}));

        // TC 09: Three disjoint pairs V = 6, E = 3 -> 3 components
        testCases.add(new TestCase(6, new int[][]{{0, 1}, {2, 3}, {4, 5}}));

        // TC 10: One path of 5, plus 5 isolated vertices (V = 10) -> 6 components
        testCases.add(makePathPlusIsolated(10, 5));

        // TC 11: Two disjoint star graphs V = 12 (centers at 0 and 6) -> 2 components
        testCases.add(makeTwoStars(12, 6));

        // TC 12: Five independent paths of length 3 (V = 15) -> 5 components
        testCases.add(makeDisjointPaths(15, 5));

        // TC 13: Four disjoint cliques of size 4 (V = 16) -> 4 components
        testCases.add(makeDisjointCliques(16, 4));

        // TC 14: Linear path V = 20 (single component)
        testCases.add(makePath(20));

        // TC 15: Disjoint cycles C_4 and C_6 and C_8 (V = 18) -> 3 components
        testCases.add(makeDisjointCycles(new int[]{4, 6, 8}));

        // ==========================================
        // 16 - 35: Structured multi-component graphs (V = 20 to 100)
        // ==========================================
        for (int i = 16; i <= 35; i++) {
            int v = 20 + (i - 16) * 4;
            int numComp = 2 + (i % 6);
            testCases.add(makeRandomComponents(v, numComp, rng));
        }

        // ==========================================
        // 36 - 65: Medium scale graphs (V = 150 to 3000)
        // ==========================================
        int[] medSizes = {
            150, 200, 250, 300, 350, 400, 450, 500, 600, 700,
            800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700,
            1800, 1900, 2000, 2200, 2400, 2500, 2600, 2800, 2900, 3000
        };

        for (int i = 0; i < medSizes.length; i++) {
            int v = medSizes[i];
            int numComp;
            if (i % 4 == 0) {
                numComp = 1; // Single large connected component
            } else if (i % 4 == 1) {
                numComp = v / 10 + 1; // Many components
            } else if (i % 4 == 2) {
                numComp = 2; // Split into two halves
            } else {
                numComp = 5 + rng.nextInt(15);
            }
            testCases.add(makeRandomComponents(v, numComp, rng));
        }

        // ==========================================
        // 66 - 80: Tricky / Anti-heuristic & Skewed Graphs
        // ==========================================
        // TC 66: V = 3500, 1 giant component of 3450 nodes + 50 isolated nodes
        testCases.add(makeGiantPlusIsolated(3500, 50, rng));

        // TC 67: V = 4000, 1 giant component of 3900 nodes + 100 isolated nodes
        testCases.add(makeGiantPlusIsolated(4000, 100, rng));

        // TC 68: V = 4500, 10 equal components of 450 nodes each
        testCases.add(makeRandomComponents(4500, 10, rng));

        // TC 69: V = 5000, 1 single massive tree (1 component)
        testCases.add(makeRandomComponents(5000, 1, rng));

        // TC 70: V = 5000, 500 components of 10 nodes each
        testCases.add(makeRandomComponents(5000, 500, rng));

        // TC 71-80: Varied medium-large distributions (V = 5200 to 7000)
        for (int i = 71; i <= 80; i++) {
            int v = 5000 + (i - 70) * 200;
            int numComp = (i % 2 == 0) ? (rng.nextInt(20) + 1) : (rng.nextInt(100) + 50);
            testCases.add(makeRandomComponents(v, numComp, rng));
        }

        // ==========================================
        // 81 - 99: Max Constraints & Stress Tests (V up to 10000, E up to 10000)
        // ==========================================
        // TC 81: V = 10000, E = 0 -> 10000 components (all isolated)
        testCases.add(new TestCase(10000, new int[][]{}));

        // TC 82: V = 10000, 1 single connected tree (E = 9999, 1 component)
        testCases.add(makeRandomComponents(10000, 1, rng));

        // TC 83: V = 10000, single component with cycles (E = 10000, 1 component)
        testCases.add(makeMaxSingleComponent(10000, 10000, rng));

        // TC 84: V = 10000, exactly 2 equal components (5000 each)
        testCases.add(makeRandomComponents(10000, 2, rng));

        // TC 85: V = 10000, exactly 3 components
        testCases.add(makeRandomComponents(10000, 3, rng));

        // TC 86: V = 10000, exactly 5 components
        testCases.add(makeRandomComponents(10000, 5, rng));

        // TC 87: V = 10000, exactly 10 components
        testCases.add(makeRandomComponents(10000, 10, rng));

        // TC 88: V = 10000, exactly 25 components
        testCases.add(makeRandomComponents(10000, 25, rng));

        // TC 89: V = 10000, exactly 50 components
        testCases.add(makeRandomComponents(10000, 50, rng));

        // TC 90: V = 10000, exactly 100 components
        testCases.add(makeRandomComponents(10000, 100, rng));

        // TC 91: V = 10000, exactly 250 components
        testCases.add(makeRandomComponents(10000, 250, rng));

        // TC 92: V = 10000, exactly 500 components
        testCases.add(makeRandomComponents(10000, 500, rng));

        // TC 93: V = 10000, exactly 1000 components
        testCases.add(makeRandomComponents(10000, 1000, rng));

        // TC 94: V = 10000, exactly 2000 components
        testCases.add(makeRandomComponents(10000, 2000, rng));

        // TC 95: V = 10000, exactly 3333 components
        testCases.add(makeRandomComponents(10000, 3333, rng));

        // TC 96: V = 10000, exactly 5000 components (pairs of nodes)
        testCases.add(makeDisjointPairs(10000));

        // TC 97: V = 10000, 1 giant component of 9500 + 500 isolated nodes -> 501 components
        testCases.add(makeGiantPlusIsolated(10000, 500, rng));

        // TC 98: V = 10000, 1 giant component of 9900 + 100 isolated nodes -> 101 components
        testCases.add(makeGiantPlusIsolated(10000, 100, rng));

        // TC 99: V = 10000, 1 giant component of 9990 + 10 isolated nodes -> 11 components
        testCases.add(makeGiantPlusIsolated(10000, 10, rng));

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

            int ans = countComponents(tc.V, adj);

            try (PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)))) {
                outWriter.println(ans);
            }
        }

        System.out.println("All 100 test cases generated successfully in Testcases/Input and Testcases/Output!");
    }

    public static int countComponents(int V, List<List<Integer>> adj) {
        if (V <= 1) return V;

        boolean[] visited = new boolean[V];
        int[] stack = new int[V];
        int components = 0;

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                components++;
                int top = 0;
                stack[top] = i;
                visited[i] = true;

                while (top >= 0) {
                    int u = stack[top--];
                    for (int v : adj.get(u)) {
                        if (!visited[v]) {
                            visited[v] = true;
                            stack[++top] = v;
                        }
                    }
                }
            }
        }
        return components;
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

    static TestCase makePath(int V) {
        int[][] edges = new int[V - 1][2];
        for (int i = 0; i < V - 1; i++) {
            edges[i][0] = i;
            edges[i][1] = i + 1;
        }
        return new TestCase(V, edges);
    }

    static TestCase makePathPlusIsolated(int V, int pathLen) {
        int[][] edges = new int[pathLen - 1][2];
        for (int i = 0; i < pathLen - 1; i++) {
            edges[i][0] = i;
            edges[i][1] = i + 1;
        }
        return new TestCase(V, edges);
    }

    static TestCase makeTwoStars(int V, int half) {
        List<int[]> edges = new ArrayList<>();
        int center1 = 0;
        for (int i = 1; i < half; i++) {
            edges.add(new int[]{center1, i});
        }
        int center2 = half;
        for (int i = half + 1; i < V; i++) {
            edges.add(new int[]{center2, i});
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeDisjointPaths(int V, int numPaths) {
        List<int[]> edges = new ArrayList<>();
        int len = V / numPaths;
        for (int p = 0; p < numPaths; p++) {
            int start = p * len;
            int end = (p == numPaths - 1) ? V : start + len;
            for (int i = start; i < end - 1; i++) {
                edges.add(new int[]{i, i + 1});
            }
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeDisjointCliques(int V, int numCliques) {
        List<int[]> edges = new ArrayList<>();
        int size = V / numCliques;
        for (int c = 0; c < numCliques; c++) {
            int start = c * size;
            int end = (c == numCliques - 1) ? V : start + size;
            for (int i = start; i < end; i++) {
                for (int j = i + 1; j < end; j++) {
                    edges.add(new int[]{i, j});
                }
            }
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeDisjointCycles(int[] cycleSizes) {
        int V = 0;
        for (int s : cycleSizes) V += s;
        List<int[]> edges = new ArrayList<>();
        int curr = 0;
        for (int s : cycleSizes) {
            for (int i = 0; i < s; i++) {
                int u = curr + i;
                int v = curr + (i + 1) % s;
                edges.add(new int[]{u, v});
            }
            curr += s;
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeGiantPlusIsolated(int V, int numIsolated, Random rng) {
        int giantSize = V - numIsolated;
        List<int[]> edges = new ArrayList<>();
        // Spanning tree for giant component
        for (int i = 1; i < giantSize; i++) {
            int parent = rng.nextInt(i);
            edges.add(new int[]{parent, i});
        }
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeDisjointPairs(int V) {
        int[][] edges = new int[V / 2][2];
        for (int i = 0; i < V / 2; i++) {
            edges[i][0] = 2 * i;
            edges[i][1] = 2 * i + 1;
        }
        return new TestCase(V, edges);
    }

    static TestCase makeMaxSingleComponent(int V, int E, Random rng) {
        Set<Long> set = new HashSet<>();
        List<int[]> edges = new ArrayList<>(E);

        // Spanning tree
        for (int i = 1; i < V; i++) {
            int p = rng.nextInt(i);
            edges.add(new int[]{p, i});
            set.add(packEdge(p, i));
        }

        while (edges.size() < E) {
            int u = rng.nextInt(V);
            int v = rng.nextInt(V);
            if (u != v) {
                long key = packEdge(u, v);
                if (set.add(key)) {
                    edges.add(new int[]{u, v});
                }
            }
        }

        Collections.shuffle(edges, rng);
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static TestCase makeRandomComponents(int V, int numComponents, Random rng) {
        if (numComponents >= V) {
            return new TestCase(V, new int[][]{});
        }
        if (numComponents <= 1) {
            return makeMaxSingleComponent(V, V - 1, rng);
        }

        // Randomly assign each vertex to a component in [0, numComponents - 1]
        // Ensure every component gets at least 1 vertex
        int[] comp = new int[V];
        for (int i = 0; i < numComponents; i++) {
            comp[i] = i;
        }
        for (int i = numComponents; i < V; i++) {
            comp[i] = rng.nextInt(numComponents);
        }

        // Shuffle assignment across vertices
        for (int i = V - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int tmp = comp[i];
            comp[i] = comp[j];
            comp[j] = tmp;
        }

        // Group vertices by component
        List<List<Integer>> groups = new ArrayList<>(numComponents);
        for (int i = 0; i < numComponents; i++) {
            groups.add(new ArrayList<>());
        }
        for (int i = 0; i < V; i++) {
            groups.get(comp[i]).add(i);
        }

        List<int[]> edges = new ArrayList<>();
        Set<Long> set = new HashSet<>();

        // Build a spanning tree for each component with size >= 2
        for (int c = 0; c < numComponents; c++) {
            List<Integer> nodes = groups.get(c);
            int sz = nodes.size();
            for (int i = 1; i < sz; i++) {
                int p = rng.nextInt(i);
                int u = nodes.get(p);
                int v = nodes.get(i);
                edges.add(new int[]{u, v});
                set.add(packEdge(u, v));
            }
        }

        // Add some random internal edges within components to add cycles (up to limit 10000)
        int attempts = 0;
        while (edges.size() < Math.min(10000, V + 500) && attempts < 20000) {
            attempts++;
            int c = rng.nextInt(numComponents);
            List<Integer> nodes = groups.get(c);
            if (nodes.size() < 2) continue;
            int u = nodes.get(rng.nextInt(nodes.size()));
            int v = nodes.get(rng.nextInt(nodes.size()));
            if (u != v) {
                long key = packEdge(u, v);
                if (set.add(key)) {
                    edges.add(new int[]{u, v});
                }
            }
        }

        Collections.shuffle(edges, rng);
        return new TestCase(V, edges.toArray(new int[0][]));
    }

    static long packEdge(int u, int v) {
        int min = Math.min(u, v);
        int max = Math.max(u, v);
        return (((long) min) << 32) | (max & 0xFFFFFFFFL);
    }
}
