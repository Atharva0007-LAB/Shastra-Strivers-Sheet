import java.io.*;
import java.util.*;

public class gen {

    public static void main(String[] args) throws IOException {
        File baseDir = new File("d:/MF Shastra/shastra striverz/Number of Provinces/Testcases");
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

        List<int[][]> testCases = new ArrayList<>();
        Random rng = new Random(535);

        // ==========================================
        // 00 - 02: Platform Samples
        // ==========================================
        // Sample 1: V = 4 -> 2 provinces
        testCases.add(new int[][]{
            {1, 0, 0, 1},
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {1, 0, 0, 1}
        });

        // Sample 2: V = 3 -> 2 provinces
        testCases.add(new int[][]{
            {1, 0, 1},
            {0, 1, 0},
            {1, 0, 1}
        });

        // Sample 3: V = 2 -> 1 province
        testCases.add(new int[][]{
            {1, 1},
            {1, 1}
        });

        // ==========================================
        // 03 - 07: Minimal Boundary Cases
        // ==========================================
        // TC 03: V = 1 -> 1 province
        testCases.add(new int[][]{{1}});

        // TC 04: V = 2, Identity matrix -> 2 provinces
        testCases.add(new int[][]{{1, 0}, {0, 1}});

        // TC 05: V = 3, Identity matrix -> 3 provinces
        testCases.add(makeIdentityMatrix(3));

        // TC 06: V = 3, Complete graph -> 1 province
        testCases.add(makeCompleteMatrix(3));

        // TC 07: V = 4, Edge between 1 and 2, others isolated -> 3 provinces
        testCases.add(new int[][]{
            {1, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 1}
        });

        // ==========================================
        // 08 - 25: Small Canonical Topologies (V = 4 to 25)
        // ==========================================
        testCases.add(makePathMatrix(5));
        testCases.add(makeCycleMatrix(5));
        testCases.add(makeStarMatrix(6, 0));
        testCases.add(makeStarMatrix(6, 3));
        testCases.add(makeIdentityMatrix(8));
        testCases.add(makeCompleteMatrix(8));
        testCases.add(makeDisjointCliquesMatrix(9, 3)); // 3 cliques of 3 -> 3
        testCases.add(makeDisjointPairsMatrix(10));      // 5 pairs -> 5
        testCases.add(makePathMatrix(12));
        testCases.add(makeDisjointCyclesMatrix(new int[]{4, 4, 4})); // 3
        testCases.add(makeIdentityMatrix(15));
        testCases.add(makeCompleteMatrix(15));
        testCases.add(makeGiantPlusIsolatedMatrix(16, 6, rng));
        testCases.add(makeDisjointCliquesMatrix(16, 4));
        testCases.add(makePathMatrix(20));
        testCases.add(makeDisjointPairsMatrix(20));
        testCases.add(makeIdentityMatrix(25));
        testCases.add(makeCompleteMatrix(25));

        // ==========================================
        // 26 - 55: Medium Structured & Random (V = 30 to 120)
        // ==========================================
        for (int i = 26; i <= 55; i++) {
            int v = 30 + (i - 26) * 3;
            int numComp = 1 + (i % 8);
            testCases.add(makeRandomComponentsMatrix(v, numComp, rng));
        }

        // ==========================================
        // 56 - 80: Larger Topologies (V = 130 to 275)
        // ==========================================
        for (int i = 56; i <= 80; i++) {
            int v = 130 + (i - 56) * 6;
            int numComp;
            if (i % 5 == 0) {
                numComp = 1;
            } else if (i % 5 == 1) {
                numComp = 2;
            } else if (i % 5 == 2) {
                numComp = v / 5;
            } else {
                numComp = 5 + rng.nextInt(15);
            }
            testCases.add(makeRandomComponentsMatrix(v, numComp, rng));
        }

        // ==========================================
        // 81 - 99: Max Constraints & Stress Tests (V = 280 to 300)
        // ==========================================
        // TC 81: V = 300, Identity matrix -> 300 provinces
        testCases.add(makeIdentityMatrix(300));

        // TC 82: V = 300, Complete graph (all 1s) -> 1 province
        testCases.add(makeCompleteMatrix(300));

        // TC 83: V = 300, Single Path -> 1 province
        testCases.add(makePathMatrix(300));

        // TC 84: V = 300, Exactly 2 components (150 each)
        testCases.add(makeRandomComponentsMatrix(300, 2, rng));

        // TC 85: V = 300, Exactly 3 components (100 each)
        testCases.add(makeRandomComponentsMatrix(300, 3, rng));

        // TC 86: V = 300, Exactly 5 components (60 each)
        testCases.add(makeRandomComponentsMatrix(300, 5, rng));

        // TC 87: V = 300, Exactly 10 components (30 each)
        testCases.add(makeRandomComponentsMatrix(300, 10, rng));

        // TC 88: V = 300, Exactly 25 components (12 each)
        testCases.add(makeRandomComponentsMatrix(300, 25, rng));

        // TC 89: V = 300, Exactly 50 components (6 each)
        testCases.add(makeRandomComponentsMatrix(300, 50, rng));

        // TC 90: V = 300, Exactly 75 components (4 each)
        testCases.add(makeRandomComponentsMatrix(300, 75, rng));

        // TC 91: V = 300, Exactly 100 components (3 each)
        testCases.add(makeRandomComponentsMatrix(300, 100, rng));

        // TC 92: V = 300, Exactly 150 components (pairs of 2)
        testCases.add(makeDisjointPairsMatrix(300));

        // TC 93: V = 300, Giant component of 290 nodes + 10 isolated nodes -> 11 provinces
        testCases.add(makeGiantPlusIsolatedMatrix(300, 10, rng));

        // TC 94: V = 300, Giant component of 299 nodes + 1 isolated node -> 2 provinces
        testCases.add(makeGiantPlusIsolatedMatrix(300, 1, rng));

        // TC 95: V = 290, Dense components
        testCases.add(makeRandomComponentsMatrix(290, 4, rng));

        // TC 96: V = 295, Dense components
        testCases.add(makeRandomComponentsMatrix(295, 8, rng));

        // TC 97: V = 300, 20 components
        testCases.add(makeRandomComponentsMatrix(300, 20, rng));

        // TC 98: V = 300, 40 components
        testCases.add(makeRandomComponentsMatrix(300, 40, rng));

        // TC 99: V = 300, 15 components
        testCases.add(makeRandomComponentsMatrix(300, 15, rng));

        System.out.println("Total test cases prepared: " + testCases.size());
        if (testCases.size() != 100) {
            throw new RuntimeException("Expected 100 testcases, but found: " + testCases.size());
        }

        // Write all 100 testcases to Input and Output
        for (int i = 0; i < testCases.size(); i++) {
            String fileName = String.format("%02d", i);
            File inFile = new File(inputDir, "input" + fileName + ".txt");
            File outFile = new File(outputDir, "output" + fileName + ".txt");

            int[][] adj = testCases.get(i);
            int V = adj.length;

            // Write input
            try (PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)))) {
                inWriter.println(V);
                for (int r = 0; r < V; r++) {
                    StringBuilder sb = new StringBuilder();
                    for (int c = 0; c < V; c++) {
                        if (c > 0) sb.append(' ');
                        sb.append(adj[r][c]);
                    }
                    inWriter.println(sb.toString());
                }
            }

            int ans = numProvinces(adj);

            try (PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)))) {
                outWriter.println(ans);
            }
        }

        System.out.println("All 100 test cases generated successfully in Testcases/Input and Testcases/Output!");
    }

    // ==========================================
    // Solver Method (Self-Contained)
    // ==========================================
    public static int numProvinces(int[][] adj) {
        int V = adj.length;
        if (V <= 1) return V;

        boolean[] visited = new boolean[V];
        int[] stack = new int[V];
        int provinces = 0;

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                provinces++;
                int top = 0;
                stack[top] = i;
                visited[i] = true;

                while (top >= 0) {
                    int u = stack[top--];
                    for (int v = 0; v < V; v++) {
                        if (adj[u][v] == 1 && !visited[v]) {
                            visited[v] = true;
                            stack[++top] = v;
                        }
                    }
                }
            }
        }
        return provinces;
    }

    // ==========================================
    // Helper Matrix Generators
    // ==========================================
    static int[][] makeIdentityMatrix(int V) {
        int[][] adj = new int[V][V];
        for (int i = 0; i < V; i++) adj[i][i] = 1;
        return adj;
    }

    static int[][] makeCompleteMatrix(int V) {
        int[][] adj = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                adj[i][j] = 1;
            }
        }
        return adj;
    }

    static int[][] makePathMatrix(int V) {
        int[][] adj = makeIdentityMatrix(V);
        for (int i = 0; i < V - 1; i++) {
            adj[i][i + 1] = 1;
            adj[i + 1][i] = 1;
        }
        return adj;
    }

    static int[][] makeCycleMatrix(int V) {
        int[][] adj = makePathMatrix(V);
        adj[0][V - 1] = 1;
        adj[V - 1][0] = 1;
        return adj;
    }

    static int[][] makeStarMatrix(int V, int center) {
        int[][] adj = makeIdentityMatrix(V);
        for (int i = 0; i < V; i++) {
            adj[center][i] = 1;
            adj[i][center] = 1;
        }
        return adj;
    }

    static int[][] makeDisjointPairsMatrix(int V) {
        int[][] adj = makeIdentityMatrix(V);
        for (int i = 0; i < V / 2; i++) {
            int u = 2 * i;
            int v = 2 * i + 1;
            adj[u][v] = 1;
            adj[v][u] = 1;
        }
        return adj;
    }

    static int[][] makeDisjointCliquesMatrix(int V, int numCliques) {
        int[][] adj = makeIdentityMatrix(V);
        int sz = V / numCliques;
        for (int c = 0; c < numCliques; c++) {
            int start = c * sz;
            int end = (c == numCliques - 1) ? V : start + sz;
            for (int i = start; i < end; i++) {
                for (int j = start; j < end; j++) {
                    adj[i][j] = 1;
                }
            }
        }
        return adj;
    }

    static int[][] makeDisjointCyclesMatrix(int[] cycleSizes) {
        int V = 0;
        for (int s : cycleSizes) V += s;
        int[][] adj = makeIdentityMatrix(V);
        int curr = 0;
        for (int s : cycleSizes) {
            for (int i = 0; i < s; i++) {
                int u = curr + i;
                int v = curr + (i + 1) % s;
                adj[u][v] = 1;
                adj[v][u] = 1;
            }
            curr += s;
        }
        return adj;
    }

    static int[][] makeGiantPlusIsolatedMatrix(int V, int numIsolated, Random rng) {
        int[][] adj = makeIdentityMatrix(V);
        int giantSize = V - numIsolated;
        for (int i = 1; i < giantSize; i++) {
            int p = rng.nextInt(i);
            adj[p][i] = 1;
            adj[i][p] = 1;
        }
        // Add random cross edges inside giant component
        int extra = giantSize * 2;
        for (int k = 0; k < extra; k++) {
            int u = rng.nextInt(giantSize);
            int v = rng.nextInt(giantSize);
            adj[u][v] = 1;
            adj[v][u] = 1;
        }
        return adj;
    }

    static int[][] makeRandomComponentsMatrix(int V, int numComponents, Random rng) {
        if (numComponents >= V) {
            return makeIdentityMatrix(V);
        }

        int[][] adj = makeIdentityMatrix(V);

        // Assign each vertex to a component
        int[] comp = new int[V];
        for (int i = 0; i < numComponents; i++) comp[i] = i;
        for (int i = numComponents; i < V; i++) comp[i] = rng.nextInt(numComponents);

        for (int i = V - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            int tmp = comp[i];
            comp[i] = comp[j];
            comp[j] = tmp;
        }

        List<List<Integer>> groups = new ArrayList<>(numComponents);
        for (int i = 0; i < numComponents; i++) groups.add(new ArrayList<>());
        for (int i = 0; i < V; i++) groups.get(comp[i]).add(i);

        for (int c = 0; c < numComponents; c++) {
            List<Integer> nodes = groups.get(c);
            int sz = nodes.size();
            for (int i = 1; i < sz; i++) {
                int p = rng.nextInt(i);
                int u = nodes.get(p);
                int v = nodes.get(i);
                adj[u][v] = 1;
                adj[v][u] = 1;
            }
            // Add extra edges within this component
            int extras = Math.min(sz * 2, 50);
            for (int k = 0; k < extras; k++) {
                int u = nodes.get(rng.nextInt(sz));
                int v = nodes.get(rng.nextInt(sz));
                adj[u][v] = 1;
                adj[v][u] = 1;
            }
        }

        return adj;
    }
}
