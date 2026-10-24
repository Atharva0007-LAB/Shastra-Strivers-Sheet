import java.io.*;
import java.util.*;

public class gen {

    public static void main(String[] args) throws IOException {
        File baseDir = new File("d:/MF Shastra/shastra striverz/Number of Islands/Testcases");
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

        List<char[][]> testCases = new ArrayList<>();
        Random rng = new Random(534);

        // ==========================================
        // 00 - 02: Platform Samples
        // ==========================================
        // Sample 1: 4x5 -> 2
        testCases.add(new char[][]{
            {'1', '1', '1', '0', '1'},
            {'1', '0', '0', '0', '0'},
            {'1', '1', '1', '0', '1'},
            {'0', '0', '0', '1', '1'}
        });

        // Sample 2: 4x5 -> 1
        testCases.add(new char[][]{
            {'1', '0', '0', '0', '1'},
            {'0', '1', '0', '1', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '1', '0', '1', '0'}
        });

        // Sample 3: 4x5 -> 1
        testCases.add(new char[][]{
            {'1', '1', '1', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
        });

        // ==========================================
        // 03 - 07: Minimal Boundaries
        // ==========================================
        // TC 03: 1x1 water -> 0
        testCases.add(new char[][]{{'0'}});

        // TC 04: 1x1 land -> 1
        testCases.add(new char[][]{{'1'}});

        // TC 05: 1x5 alternating -> 3
        testCases.add(new char[][]{{'1', '0', '1', '0', '1'}});

        // TC 06: 5x1 all land -> 1
        testCases.add(new char[][]{{'1'}, {'1'}, {'1'}, {'1'}, {'1'}});

        // TC 07: 3x3 all water -> 0
        testCases.add(makeFilledGrid(3, 3, '0'));

        // ==========================================
        // 08 - 25: Small Canonical Grids (N, M <= 25)
        // ==========================================
        testCases.add(makeFilledGrid(4, 4, '1')); // all land -> 1
        testCases.add(makeCheckerboard(5, 5));     // 5x5 checkerboard
        testCases.add(makeDonut(7, 7));           // 7x7 donut with water center
        testCases.add(makeConcentricRings(9, 9));  // rings
        testCases.add(makeIsolatedDots(8, 8, 2)); // spaced by 2
        testCases.add(makeHorizontalStripes(6, 8));
        testCases.add(makeVerticalStripes(8, 6));
        testCases.add(makeDiagonalStripes(7, 7));
        testCases.add(makeSnakeGrid(10, 10));
        testCases.add(makeRandomGrid(10, 10, 0.2, rng));
        testCases.add(makeRandomGrid(12, 12, 0.5, rng));
        testCases.add(makeRandomGrid(15, 15, 0.8, rng));
        testCases.add(makeRectangularBlocks(16, 16, 3, 3));
        testCases.add(makeRandomGrid(20, 5, 0.4, rng));
        testCases.add(makeRandomGrid(5, 20, 0.4, rng));
        testCases.add(makeFilledGrid(20, 20, '1'));
        testCases.add(makeCheckerboard(20, 20));
        testCases.add(makeIsolatedDots(25, 25, 3));

        // ==========================================
        // 26 - 55: Medium Grids (N, M = 30 to 120)
        // ==========================================
        for (int i = 26; i <= 55; i++) {
            int n = 30 + (i - 26) * 3;
            int m = 30 + (i - 26) * 3;
            double p;
            if (i % 5 == 0) p = 0.08;      // Sparse -> many small islands
            else if (i % 5 == 1) p = 0.25; // Medium
            else if (i % 5 == 2) p = 0.50; // Moderate
            else if (i % 5 == 3) p = 0.75; // Dense
            else p = 0.92;                 // Very dense
            testCases.add(makeRandomGrid(n, m, p, rng));
        }

        // ==========================================
        // 56 - 80: Larger Topologies (N, M = 130 to 250)
        // ==========================================
        for (int i = 56; i <= 80; i++) {
            int n = 130 + (i - 56) * 5;
            int m = 130 + (i - 56) * 5;
            if (i % 4 == 0) {
                testCases.add(makeHorizontalStripes(n, m));
            } else if (i % 4 == 1) {
                testCases.add(makeVerticalStripes(n, m));
            } else if (i % 4 == 2) {
                testCases.add(makeRectangularBlocks(n, m, 8, 8));
            } else {
                testCases.add(makeRandomGrid(n, m, 0.35, rng));
            }
        }

        // ==========================================
        // 81 - 99: Max Constraints & Stress Tests (N, M up to 300)
        // ==========================================
        // TC 81: 300x300, all land -> 1 island
        testCases.add(makeFilledGrid(300, 300, '1'));

        // TC 82: 300x300, all water -> 0 islands
        testCases.add(makeFilledGrid(300, 300, '0'));

        // TC 83: 300x300, checkerboard
        testCases.add(makeCheckerboard(300, 300));

        // TC 84: 300x300, isolated dots spaced by 2 -> ~22,500 islands
        testCases.add(makeIsolatedDots(300, 300, 2));

        // TC 85: 300x300, concentric square rings
        testCases.add(makeConcentricRings(300, 300));

        // TC 86: 300x300, horizontal stripes
        testCases.add(makeHorizontalStripes(300, 300));

        // TC 87: 300x300, vertical stripes
        testCases.add(makeVerticalStripes(300, 300));

        // TC 88: 300x300, diagonal stripes
        testCases.add(makeDiagonalStripes(300, 300));

        // TC 89: 300x300, random sparse (5% land)
        testCases.add(makeRandomGrid(300, 300, 0.05, rng));

        // TC 90: 300x300, random medium (25% land)
        testCases.add(makeRandomGrid(300, 300, 0.25, rng));

        // TC 91: 300x300, random percolation (40% land)
        testCases.add(makeRandomGrid(300, 300, 0.40, rng));

        // TC 92: 300x300, random dense (65% land)
        testCases.add(makeRandomGrid(300, 300, 0.65, rng));

        // TC 93: 300x300, random very dense (85% land)
        testCases.add(makeRandomGrid(300, 300, 0.85, rng));

        // TC 94: 1x300 linear row
        testCases.add(makeRandomGrid(1, 300, 0.6, rng));

        // TC 95: 300x1 linear column
        testCases.add(makeRandomGrid(300, 1, 0.6, rng));

        // TC 96: 250x300 blocks
        testCases.add(makeRectangularBlocks(250, 300, 10, 10));

        // TC 97: 300x250 blocks
        testCases.add(makeRectangularBlocks(300, 250, 12, 12));

        // TC 98: 300x300 snake maze
        testCases.add(makeSnakeGrid(300, 300));

        // TC 99: 300x300 mixed clusters + scattered
        testCases.add(makeMixedGrid(300, 300, rng));

        System.out.println("Total test cases prepared: " + testCases.size());
        if (testCases.size() != 100) {
            throw new RuntimeException("Expected 100 testcases, but found: " + testCases.size());
        }

        // Write all 100 testcases to Input and Output
        for (int i = 0; i < testCases.size(); i++) {
            String fileName = String.format("%02d", i);
            File inFile = new File(inputDir, "input" + fileName + ".txt");
            File outFile = new File(outputDir, "output" + fileName + ".txt");

            char[][] grid = testCases.get(i);
            int N = grid.length;
            int M = grid[0].length;

            // Write input
            try (PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)))) {
                inWriter.println(N + " " + M);
                for (int r = 0; r < N; r++) {
                    StringBuilder sb = new StringBuilder();
                    for (int c = 0; c < M; c++) {
                        if (c > 0) sb.append(' ');
                        sb.append(grid[r][c]);
                    }
                    inWriter.println(sb.toString());
                }
            }

            int ans = numIslands(grid);

            try (PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)))) {
                outWriter.println(ans);
            }
        }

        System.out.println("All 100 test cases generated successfully in Testcases/Input and Testcases/Output!");
    }

    // ==========================================
    // Solver Method (Self-Contained)
    // ==========================================
    private static final int[] dr = {-1, -1, -1,  0, 0,  1, 1, 1};
    private static final int[] dc = {-1,  0,  1, -1, 1, -1, 0, 1};

    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int N = grid.length;
        int M = grid[0].length;
        boolean[][] visited = new boolean[N][M];
        int islands = 0;

        int totalCells = N * M;
        int[] queueR = new int[totalCells];
        int[] queueC = new int[totalCells];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (grid[r][c] == '1' && !visited[r][c]) {
                    islands++;
                    int head = 0, tail = 0;
                    visited[r][c] = true;
                    queueR[tail] = r;
                    queueC[tail] = c;
                    tail++;

                    while (head < tail) {
                        int currR = queueR[head];
                        int currC = queueC[head];
                        head++;

                        for (int k = 0; k < 8; k++) {
                            int nr = currR + dr[k];
                            int nc = currC + dc[k];

                            if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
                                if (grid[nr][nc] == '1' && !visited[nr][nc]) {
                                    visited[nr][nc] = true;
                                    queueR[tail] = nr;
                                    queueC[tail] = nc;
                                    tail++;
                                }
                            }
                        }
                    }
                }
            }
        }

        return islands;
    }

    // ==========================================
    // Helper Grid Builders
    // ==========================================
    static char[][] makeFilledGrid(int N, int M, char fill) {
        char[][] g = new char[N][M];
        for (int r = 0; r < N; r++) {
            Arrays.fill(g[r], fill);
        }
        return g;
    }

    static char[][] makeCheckerboard(int N, int M) {
        char[][] g = new char[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = ((r + c) % 2 == 0) ? '1' : '0';
            }
        }
        return g;
    }

    static char[][] makeDonut(int N, int M) {
        char[][] g = makeFilledGrid(N, M, '1');
        for (int r = 2; r < N - 2; r++) {
            for (int c = 2; c < M - 2; c++) {
                g[r][c] = '0';
            }
        }
        return g;
    }

    static char[][] makeConcentricRings(int N, int M) {
        char[][] g = makeFilledGrid(N, M, '0');
        int layers = Math.min(N, M) / 2;
        for (int l = 0; l < layers; l += 2) {
            for (int c = l; c < M - l; c++) {
                g[l][c] = '1';
                g[N - 1 - l][c] = '1';
            }
            for (int r = l; r < N - l; r++) {
                g[r][l] = '1';
                g[r][M - 1 - l] = '1';
            }
        }
        return g;
    }

    static char[][] makeIsolatedDots(int N, int M, int step) {
        char[][] g = makeFilledGrid(N, M, '0');
        for (int r = 0; r < N; r += step) {
            for (int c = 0; c < M; c += step) {
                g[r][c] = '1';
            }
        }
        return g;
    }

    static char[][] makeHorizontalStripes(int N, int M) {
        char[][] g = new char[N][M];
        for (int r = 0; r < N; r++) {
            char fill = (r % 2 == 0) ? '1' : '0';
            Arrays.fill(g[r], fill);
        }
        return g;
    }

    static char[][] makeVerticalStripes(int N, int M) {
        char[][] g = new char[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = (c % 2 == 0) ? '1' : '0';
            }
        }
        return g;
    }

    static char[][] makeDiagonalStripes(int N, int M) {
        char[][] g = new char[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = ((r + c) % 3 == 0) ? '1' : '0';
            }
        }
        return g;
    }

    static char[][] makeSnakeGrid(int N, int M) {
        char[][] g = makeFilledGrid(N, M, '0');
        for (int r = 0; r < N; r += 2) {
            for (int c = 0; c < M; c++) g[r][c] = '1';
            if (r + 1 < N) {
                int col = ((r / 2) % 2 == 0) ? (M - 1) : 0;
                g[r + 1][col] = '1';
            }
        }
        return g;
    }

    static char[][] makeRectangularBlocks(int N, int M, int blockR, int blockC) {
        char[][] g = makeFilledGrid(N, M, '0');
        int stepR = blockR + 2;
        int stepC = blockC + 2;
        for (int r = 0; r < N; r += stepR) {
            for (int c = 0; c < M; c += stepC) {
                for (int dr = 0; dr < blockR && r + dr < N; dr++) {
                    for (int dc = 0; dc < blockC && c + dc < M; dc++) {
                        g[r + dr][c + dc] = '1';
                    }
                }
            }
        }
        return g;
    }

    static char[][] makeRandomGrid(int N, int M, double probLand, Random rng) {
        char[][] g = new char[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = (rng.nextDouble() < probLand) ? '1' : '0';
            }
        }
        return g;
    }

    static char[][] makeMixedGrid(int N, int M, Random rng) {
        char[][] g = makeRandomGrid(N, M, 0.3, rng);
        // Add 5 large land patches
        for (int k = 0; k < 5; k++) {
            int topR = rng.nextInt(N);
            int topC = rng.nextInt(M);
            int h = 10 + rng.nextInt(30);
            int w = 10 + rng.nextInt(30);
            for (int r = topR; r < Math.min(N, topR + h); r++) {
                for (int c = topC; c < Math.min(M, topC + w); c++) {
                    g[r][c] = '1';
                }
            }
        }
        return g;
    }
}
