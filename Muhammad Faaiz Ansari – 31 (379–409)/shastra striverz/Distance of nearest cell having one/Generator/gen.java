import java.io.*;
import java.util.*;

public class gen {

    public static void main(String[] args) throws IOException {
        File baseDir = new File("d:/MF Shastra/shastra striverz/Distance of nearest cell having one/Testcases");
        if (!baseDir.isAbsolute()) {
            baseDir = new File("../Testcases");
        }
        baseDir.mkdirs();

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<int[][]> testCases = new ArrayList<>();
        Random rng = new Random(530);

        // ==========================================
        // 00 - 02: Platform Samples
        // ==========================================
        // Sample 1: 3x4
        testCases.add(new int[][]{
            {0, 1, 1, 0},
            {1, 1, 0, 0},
            {0, 0, 1, 1}
        });

        // Sample 2: 3x3
        testCases.add(new int[][]{
            {1, 0, 1},
            {1, 1, 0},
            {1, 0, 0}
        });

        // Sample 3: 2x2
        testCases.add(new int[][]{
            {0, 1},
            {1, 0}
        });

        // ==========================================
        // 03 - 12: Minimal Boundaries & Trivial Cases
        // ==========================================
        // TC 03: 1x1 [1]
        testCases.add(new int[][]{{1}});

        // TC 04: 1x2 [1, 0]
        testCases.add(new int[][]{{1, 0}});

        // TC 05: 1x2 [0, 1]
        testCases.add(new int[][]{{0, 1}});

        // TC 06: 1x2 [1, 1]
        testCases.add(new int[][]{{1, 1}});

        // TC 07: 2x1 [1], [0]
        testCases.add(new int[][]{{1}, {0}});

        // TC 08: 1x5 single 1 at end
        testCases.add(new int[][]{{0, 0, 0, 0, 1}});

        // TC 09: 1x5 single 1 at center
        testCases.add(new int[][]{{0, 0, 1, 0, 0}});

        // TC 10: 5x1 single 1 at center
        testCases.add(new int[][]{{0}, {0}, {1}, {0}, {0}});

        // TC 11: 2x2 all 1s
        testCases.add(new int[][]{
            {1, 1},
            {1, 1}
        });

        // TC 12: 2x2 single 1 at top-left
        testCases.add(new int[][]{
            {1, 0},
            {0, 0}
        });

        // ==========================================
        // 13 - 24: Small Structured Patterns (3x3 to 7x7)
        // ==========================================
        // TC 13: 3x3 center 1
        testCases.add(new int[][]{
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        });

        // TC 14: 3x3 corners 1
        testCases.add(new int[][]{
            {1, 0, 1},
            {0, 0, 0},
            {1, 0, 1}
        });

        // TC 15: 4x4 main diagonal 1
        testCases.add(makeDiagonalGrid(4, 4, true));

        // TC 16: 4x4 anti diagonal 1
        testCases.add(makeDiagonalGrid(4, 4, false));

        // TC 17: 5x5 center 1
        testCases.add(makeSingle1At(5, 5, 2, 2));

        // TC 18: 5x5 corner 1 at (0, 0)
        testCases.add(makeSingle1At(5, 5, 0, 0));

        // TC 19: 5x5 perimeter 1s, interior 0s
        testCases.add(makePerimeter1s(5, 5));

        // TC 20: 5x5 cross of 1s
        testCases.add(makeCross1s(5, 5));

        // TC 21: 6x6 checkerboard
        testCases.add(makeCheckerboard(6, 6));

        // TC 22: 7x7 concentric rings of 1s
        testCases.add(makeConcentricRings(7, 7));

        // TC 23: 7x7 horizontal stripes
        testCases.add(makeStripes(7, 7, true));

        // TC 24: 7x7 vertical stripes
        testCases.add(makeStripes(7, 7, false));

        // ==========================================
        // 25 - 44: Medium Grids (8x8 to 50x50)
        // ==========================================
        // TC 25: 10x10 single 1 at (0, 0)
        testCases.add(makeSingle1At(10, 10, 0, 0));

        // TC 26: 10x10 sparse random (5% 1s)
        testCases.add(makeRandomGrid(10, 10, 0.05, rng));

        // TC 27: 12x12 dense random (80% 1s)
        testCases.add(makeRandomGrid(12, 12, 0.80, rng));

        // TC 28: 15x15 snake of 1s
        testCases.add(makeSnakeGrid(15, 15));

        // TC 29: 16x16 4 corners 1
        testCases.add(makeFourCorners1s(16, 16));

        // TC 30: 20x20 perimeter 1s
        testCases.add(makePerimeter1s(20, 20));

        // TC 31: 20x20 single 1 at center
        testCases.add(makeSingle1At(20, 20, 10, 10));

        // TC 32: 25x25 cross of 1s
        testCases.add(makeCross1s(25, 25));

        // TC 33: 30x30 diagonal 1s
        testCases.add(makeDiagonalGrid(30, 30, true));

        // TC 34: 30x30 checkerboard
        testCases.add(makeCheckerboard(30, 30));

        // TC 35: 35x35 multi-cluster of 1s
        testCases.add(makeClusters(35, 35, 8, rng));

        // TC 36: 40x20 asymmetric sparse
        testCases.add(makeRandomGrid(40, 20, 0.08, rng));

        // TC 37: 20x40 asymmetric dense
        testCases.add(makeRandomGrid(20, 40, 0.65, rng));

        // TC 38: 45x45 concentric square rings
        testCases.add(makeConcentricRings(45, 45));

        // TC 39: 50x50 single 1 at (0, 0) -> max distance 98
        testCases.add(makeSingle1At(50, 50, 0, 0));

        // TC 40: 50x50 snake of 1s
        testCases.add(makeSnakeGrid(50, 50));

        // TC 41: 50x50 random 10%
        testCases.add(makeRandomGrid(50, 50, 0.10, rng));

        // TC 42: 50x50 random 50%
        testCases.add(makeRandomGrid(50, 50, 0.50, rng));

        // TC 43: 50x50 four corners 1s
        testCases.add(makeFourCorners1s(50, 50));

        // TC 44: 50x50 perimeter 1s
        testCases.add(makePerimeter1s(50, 50));

        // ==========================================
        // 45 - 69: Large Grids (60x60 to 180x180)
        // ==========================================
        for (int i = 45; i <= 69; i++) {
            int dimR = 60 + (i - 45) * 5;
            int dimC = 60 + ((i * 11) % 60);

            if (i % 6 == 0) {
                testCases.add(makeSingle1At(dimR, dimC, 0, 0));
            } else if (i % 6 == 1) {
                testCases.add(makeSingle1At(dimR, dimC, dimR / 2, dimC / 2));
            } else if (i % 6 == 2) {
                testCases.add(makeConcentricRings(dimR, dimC));
            } else if (i % 6 == 3) {
                testCases.add(makeCheckerboard(dimR, dimC));
            } else if (i % 6 == 4) {
                testCases.add(makeClusters(dimR, dimC, 12, rng));
            } else {
                double p = 0.05 + rng.nextDouble() * 0.40;
                testCases.add(makeRandomGrid(dimR, dimC, p, rng));
            }
        }

        // ==========================================
        // 70 - 89: Very Large Grids (200x200 to 420x420)
        // ==========================================
        for (int i = 70; i <= 89; i++) {
            int dimR = 200 + (i - 70) * 11;
            int dimC = 200 + ((i * 13) % 150);

            if (i % 5 == 0) {
                testCases.add(makeSingle1At(dimR, dimC, 0, 0));
            } else if (i % 5 == 1) {
                testCases.add(makeFourCorners1s(dimR, dimC));
            } else if (i % 5 == 2) {
                testCases.add(makeSnakeGrid(dimR, dimC));
            } else if (i % 5 == 3) {
                testCases.add(makeClusters(dimR, dimC, 25, rng));
            } else {
                testCases.add(makeRandomGrid(dimR, dimC, 0.20, rng));
            }
        }

        // ==========================================
        // 90 - 99: Extreme Maximum Constraints (500x500)
        // ==========================================
        // TC 90: 500x500 all 1s -> all 0s
        testCases.add(makeFilledGrid(500, 500, 1));

        // TC 91: 500x500 single 1 at (0, 0) -> distance up to 998
        testCases.add(makeSingle1At(500, 500, 0, 0));

        // TC 92: 500x500 single 1 at center (250, 250) -> distance up to 500
        testCases.add(makeSingle1At(500, 500, 250, 250));

        // TC 93: 500x500 four corners are 1s
        testCases.add(makeFourCorners1s(500, 500));

        // TC 94: 500x500 perimeter is 1s, interior all 0s
        testCases.add(makePerimeter1s(500, 500));

        // TC 95: 500x500 main diagonal of 1s
        testCases.add(makeDiagonalGrid(500, 500, true));

        // TC 96: 500x500 checkerboard (distances strictly 0 and 1)
        testCases.add(makeCheckerboard(500, 500));

        // TC 97: 500x500 serpentine snake of 1s
        testCases.add(makeSnakeGrid(500, 500));

        // TC 98: 500x500 sparse random with 50 scattered 1s
        testCases.add(makeScatteredPoints(500, 500, 50, rng));

        // TC 99: 500x500 dense random (50% 1s)
        testCases.add(makeRandomGrid(500, 500, 0.50, rng));

        // ==========================================
        // Generate Files
        // ==========================================
        System.out.println("Total test cases queued: " + testCases.size());

        for (int i = 0; i < testCases.size(); i++) {
            int[][] grid = testCases.get(i);
            int N = grid.length;
            int M = grid[0].length;

            String indexStr = String.format("%02d", i);
            File inFile = new File(inputDir, "input" + indexStr + ".txt");
            File outFile = new File(outputDir, "output" + indexStr + ".txt");

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

            int[][] ans = nearest(grid);

            try (PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)))) {
                for (int r = 0; r < N; r++) {
                    StringBuilder sb = new StringBuilder();
                    for (int c = 0; c < M; c++) {
                        if (c > 0) sb.append(' ');
                        sb.append(ans[r][c]);
                    }
                    outWriter.println(sb.toString());
                }
            }
        }

        System.out.println("All 100 test cases generated successfully in Testcases/Input and Testcases/Output!");
    }

    // ==========================================
    // Solver Method (Self-Contained)
    // ==========================================
    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, -1, 1};

    public static int[][] nearest(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return new int[0][0];

        int N = grid.length;
        int M = grid[0].length;
        int totalCells = N * M;

        int[][] dist = new int[N][M];
        for (int r = 0; r < N; r++) {
            Arrays.fill(dist[r], -1);
        }

        int[] queueR = new int[totalCells];
        int[] queueC = new int[totalCells];
        int head = 0;
        int tail = 0;

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (grid[r][c] == 1) {
                    dist[r][c] = 0;
                    queueR[tail] = r;
                    queueC[tail] = c;
                    tail++;
                }
            }
        }

        while (head < tail) {
            int r = queueR[head];
            int c = queueC[head];
            head++;

            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k];
                int nc = c + dc[k];

                if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
                    if (dist[nr][nc] == -1) {
                        dist[nr][nc] = dist[r][c] + 1;
                        queueR[tail] = nr;
                        queueC[tail] = nc;
                        tail++;
                    }
                }
            }
        }

        return dist;
    }

    // ==========================================
    // Grid Construction Helpers
    // ==========================================
    static int[][] makeFilledGrid(int N, int M, int fill) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            Arrays.fill(g[r], fill);
        }
        return g;
    }

    static int[][] makeSingle1At(int N, int M, int r, int c) {
        int[][] g = makeFilledGrid(N, M, 0);
        g[Math.min(r, N - 1)][Math.min(c, M - 1)] = 1;
        return g;
    }

    static int[][] makeFourCorners1s(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 0);
        g[0][0] = 1;
        g[0][M - 1] = 1;
        g[N - 1][0] = 1;
        g[N - 1][M - 1] = 1;
        return g;
    }

    static int[][] makePerimeter1s(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 0);
        for (int c = 0; c < M; c++) {
            g[0][c] = 1;
            g[N - 1][c] = 1;
        }
        for (int r = 0; r < N; r++) {
            g[r][0] = 1;
            g[r][M - 1] = 1;
        }
        return g;
    }

    static int[][] makeCross1s(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 0);
        int midR = N / 2;
        int midC = M / 2;
        for (int r = 0; r < N; r++) g[r][midC] = 1;
        for (int c = 0; c < M; c++) g[midR][c] = 1;
        return g;
    }

    static int[][] makeDiagonalGrid(int N, int M, boolean mainDiag) {
        int[][] g = makeFilledGrid(N, M, 0);
        for (int r = 0; r < N; r++) {
            int c = mainDiag ? (r * M / N) : ((N - 1 - r) * M / N);
            if (c >= 0 && c < M) g[r][c] = 1;
        }
        return g;
    }

    static int[][] makeCheckerboard(int N, int M) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = ((r + c) % 2 == 0) ? 1 : 0;
            }
        }
        return g;
    }

    static int[][] makeStripes(int N, int M, boolean horizontal) {
        int[][] g = new int[N][M];
        if (horizontal) {
            for (int r = 0; r < N; r += 2) {
                Arrays.fill(g[r], 1);
            }
        } else {
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c += 2) {
                    g[r][c] = 1;
                }
            }
        }
        return g;
    }

    static int[][] makeConcentricRings(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 0);
        int layers = Math.min(N, M) / 2;
        for (int l = 0; l < layers; l += 2) {
            for (int c = l; c < M - l; c++) {
                g[l][c] = 1;
                g[N - 1 - l][c] = 1;
            }
            for (int r = l; r < N - l; r++) {
                g[r][l] = 1;
                g[r][M - 1 - l] = 1;
            }
        }
        g[0][0] = 1; // ensure at least one 1
        return g;
    }

    static int[][] makeSnakeGrid(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 0);
        int count = 0;
        for (int r = 0; r < N; r += 2) {
            for (int c = 0; c < M; c++) {
                g[r][c] = 1;
            }
            if (r + 1 < N) {
                int connectCol = (count % 2 == 0) ? (M - 1) : 0;
                g[r + 1][connectCol] = 1;
                count++;
            }
        }
        return g;
    }

    static int[][] makeClusters(int N, int M, int numClusters, Random rng) {
        int[][] g = makeFilledGrid(N, M, 0);
        for (int k = 0; k < numClusters; k++) {
            int topR = rng.nextInt(N);
            int topC = rng.nextInt(M);
            int h = 2 + rng.nextInt(6);
            int w = 2 + rng.nextInt(6);
            for (int r = topR; r < Math.min(N, topR + h); r++) {
                for (int c = topC; c < Math.min(M, topC + w); c++) {
                    g[r][c] = 1;
                }
            }
        }
        g[0][0] = 1; // ensure at least one 1
        return g;
    }

    static int[][] makeScatteredPoints(int N, int M, int count, Random rng) {
        int[][] g = makeFilledGrid(N, M, 0);
        for (int k = 0; k < count; k++) {
            int r = rng.nextInt(N);
            int c = rng.nextInt(M);
            g[r][c] = 1;
        }
        g[0][0] = 1; // ensure at least one 1
        return g;
    }

    static int[][] makeRandomGrid(int N, int M, double prob1, Random rng) {
        int[][] g = new int[N][M];
        boolean has1 = false;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (rng.nextDouble() < prob1) {
                    g[r][c] = 1;
                    has1 = true;
                }
            }
        }
        if (!has1) {
            g[rng.nextInt(N)][rng.nextInt(M)] = 1;
        }
        return g;
    }
}
