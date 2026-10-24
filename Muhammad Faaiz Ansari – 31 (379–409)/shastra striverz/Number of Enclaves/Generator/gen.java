import java.io.*;
import java.util.*;

public class gen {

    public static void main(String[] args) throws IOException {
        File baseDir = new File("d:/MF Shastra/shastra striverz/Number of Enclaves/Testcases");
        if (!baseDir.isAbsolute()) {
            baseDir = new File("../Testcases");
        }
        baseDir.mkdirs();

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<int[][]> testCases = new ArrayList<>();
        Random rng = new Random(533);

        // ==========================================
        // 00 - 02: Platform Samples
        // ==========================================
        // Sample 1: 4x4 -> 3
        testCases.add(new int[][]{
            {0, 0, 0, 0},
            {1, 0, 1, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0}
        });

        // Sample 2: 5x4 -> 3
        testCases.add(new int[][]{
            {0, 0, 0, 1},
            {0, 0, 0, 1},
            {0, 1, 1, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 0}
        });

        // Sample 3: 4x4 -> 4
        testCases.add(new int[][]{
            {0, 0, 0, 1},
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0}
        });

        // ==========================================
        // 03 - 09: Minimal Boundaries & Trivial Grids
        // ==========================================
        // TC 03: 1x1 water -> 0
        testCases.add(new int[][]{{0}});

        // TC 04: 1x1 land -> 0 (on boundary)
        testCases.add(new int[][]{{1}});

        // TC 05: 1x5 all land -> 0
        testCases.add(new int[][]{{1, 1, 1, 1, 1}});

        // TC 06: 5x1 alternating -> 0
        testCases.add(new int[][]{{1}, {0}, {1}, {0}, {1}});

        // TC 07: 2x2 all land -> 0
        testCases.add(new int[][]{
            {1, 1},
            {1, 1}
        });

        // TC 08: 2x2 all sea -> 0
        testCases.add(new int[][]{
            {0, 0},
            {0, 0}
        });

        // TC 09: 3x3 isolated center land -> 1
        testCases.add(new int[][]{
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
        });

        // ==========================================
        // 10 - 19: Small Grids & Enclave Patterns
        // ==========================================
        // TC 10: 3x3 center 0, perimeter 1 -> 0
        testCases.add(new int[][]{
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
        });

        // TC 11: 3x3 all land -> 0
        testCases.add(makeFilledGrid(3, 3, 1));

        // TC 12: 4x4 two isolated center land cells -> 2
        testCases.add(new int[][]{
            {0, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 0}
        });

        // TC 13: 4x4 land block reaching top boundary -> 0 enclaves
        testCases.add(new int[][]{
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
        });

        // TC 14: 5x5 solid 3x3 square enclosed by sea -> 9
        testCases.add(new int[][]{
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
        });

        // TC 15: 5x5 hollow square of land in center -> 8
        testCases.add(new int[][]{
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 0},
            {0, 1, 0, 1, 0},
            {0, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
        });

        // TC 16: 5x5 cross touching all boundaries -> 0
        testCases.add(new int[][]{
            {0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0},
            {1, 1, 1, 1, 1},
            {0, 0, 1, 0, 0},
            {0, 0, 1, 0, 0}
        });

        // TC 17: 6x6 two distinct enclave islands -> 2 + 3 = 5
        testCases.add(new int[][]{
            {0, 0, 0, 0, 0, 0},
            {0, 1, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 0},
            {0, 0, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0}
        });

        // TC 18: 7x7 concentric square rings: boundary 1s, ring of 0s, core 3x3 of 1s -> 9
        testCases.add(makeConcentricRings(7, 7));

        // TC 19: 7x7 spiral of land starting at boundary reaching center -> 0
        testCases.add(makeSpiralTouchingBoundary(7, 7));

        // ==========================================
        // 20 - 39: Medium Structured Grids (8x8 to 50x50)
        // ==========================================
        // TC 20: 8x8 checkerboard surrounded by water moat
        testCases.add(makeCheckerboardWithMoat(8, 8));

        // TC 21: 10x10 pure checkerboard (all touch boundary or connect diagonal)
        testCases.add(makeCheckerboard(10, 10));

        // TC 22: 10x10 with 4 quadrant enclaves
        testCases.add(makeQuadrantEnclaves(10, 10));

        // TC 23: 12x12 horizontal stripes enclosed by water
        testCases.add(makeStripesWithMoat(12, 12, true));

        // TC 24: 12x12 vertical stripes enclosed by water
        testCases.add(makeStripesWithMoat(12, 12, false));

        // TC 25: 15x15 labyrinth with dead ends inside water moat
        testCases.add(makeLabyrinth(15, 15, rng));

        // TC 26: 16x16 concentric rings (outer boundary 1, sea, land ring, sea, core land)
        testCases.add(makeConcentricRings(16, 16));

        // TC 27: 20x20 random sparse with moat (prob 0.20)
        testCases.add(makeRandomWithMoat(20, 20, 0.20, rng));

        // TC 28: 20x20 random medium with moat (prob 0.40)
        testCases.add(makeRandomWithMoat(20, 20, 0.40, rng));

        // TC 29: 20x20 random dense with moat (prob 0.70)
        testCases.add(makeRandomWithMoat(20, 20, 0.70, rng));

        // TC 30: 25x25 thin land bridge connecting interior to boundary
        testCases.add(makeBridgeToBoundary(25, 25));

        // TC 31: 25x25 almost identical bridge broken by 1 water cell (becomes enclave!)
        testCases.add(makeBrokenBridge(25, 25));

        // TC 32: 30x30 diagonal stripes surrounded by water
        testCases.add(makeDiagonalWithMoat(30, 30));

        // TC 33: 30x30 multiple rectangular enclave blocks
        testCases.add(makeBlockEnclaves(30, 30, 3, 3));

        // TC 34: 35x35 random with moat (prob 0.50)
        testCases.add(makeRandomWithMoat(35, 35, 0.50, rng));

        // TC 35: 40x20 asymmetric grid with enclaves
        testCases.add(makeRandomWithMoat(40, 20, 0.35, rng));

        // TC 36: 20x40 asymmetric grid with enclaves
        testCases.add(makeRandomWithMoat(20, 40, 0.45, rng));

        // TC 37: 45x45 cellular automata cave-like enclaves
        testCases.add(makeCaveWithMoat(45, 45, rng));

        // TC 38: 50x50 snake path enclosed inside water moat
        testCases.add(makeSnakeInsideMoat(50, 50));

        // TC 39: 50x50 snake path starting from boundary
        testCases.add(makeSnakeFromBoundary(50, 50));

        // ==========================================
        // 40 - 69: Large Grids (60x60 to 180x180)
        // ==========================================
        for (int i = 40; i <= 69; i++) {
            int dimR = 60 + (i - 40) * 4;
            int dimC = 60 + ((i * 7) % 50);

            if (i % 6 == 0) {
                testCases.add(makeCheckerboardWithMoat(dimR, dimC));
            } else if (i % 6 == 1) {
                testCases.add(makeConcentricRings(dimR, dimC));
            } else if (i % 6 == 2) {
                testCases.add(makeBlockEnclaves(dimR, dimC, 4, 4));
            } else if (i % 6 == 3) {
                testCases.add(makeCaveWithMoat(dimR, dimC, rng));
            } else if (i % 6 == 4) {
                double p = 0.25 + (rng.nextDouble() * 0.45);
                testCases.add(makeRandomWithMoat(dimR, dimC, p, rng));
            } else {
                testCases.add(makeLabyrinth(dimR, dimC, rng));
            }
        }

        // ==========================================
        // 70 - 89: Very Large Grids (200x200 to 420x420)
        // ==========================================
        for (int i = 70; i <= 89; i++) {
            int dimR = 200 + (i - 70) * 11;
            int dimC = 200 + ((i * 13) % 150);

            if (i % 5 == 0) {
                testCases.add(makeConcentricRings(dimR, dimC));
            } else if (i % 5 == 1) {
                testCases.add(makeCheckerboardWithMoat(dimR, dimC));
            } else if (i % 5 == 2) {
                testCases.add(makeSnakeInsideMoat(dimR, dimC));
            } else if (i % 5 == 3) {
                testCases.add(makeRandomWithMoat(dimR, dimC, 0.40, rng));
            } else {
                testCases.add(makeCaveWithMoat(dimR, dimC, rng));
            }
        }

        // ==========================================
        // 90 - 99: Extreme Constraints (Up to 500x500)
        // ==========================================
        // TC 90: 500x500 all 0s -> 0
        testCases.add(makeFilledGrid(500, 500, 0));

        // TC 91: 500x500 all 1s -> 0 (all boundary-connected)
        testCases.add(makeFilledGrid(500, 500, 1));

        // TC 92: 500x500 water boundary, interior completely land -> 498 * 498 = 248004
        testCases.add(makeInteriorAllLand(500, 500));

        // TC 93: 500x500 water boundary, interior checkerboard
        testCases.add(makeCheckerboardWithMoat(500, 500));

        // TC 94: 500x500 concentric rings
        testCases.add(makeConcentricRings(500, 500));

        // TC 95: 500x500 massive serpentine snake inside water moat
        testCases.add(makeSnakeInsideMoat(500, 500));

        // TC 96: 500x500 massive serpentine snake touching boundary
        testCases.add(makeSnakeFromBoundary(500, 500));

        // TC 97: 500x500 random sparse (prob 0.15) with water moat
        testCases.add(makeRandomWithMoat(500, 500, 0.15, rng));

        // TC 98: 500x500 random dense (prob 0.70) with water moat
        testCases.add(makeRandomWithMoat(500, 500, 0.70, rng));

        // TC 99: 500x500 mixed complex grid with lakes, enclave islands, and boundary fingers
        testCases.add(makeComplexMaxGrid(500, 500, rng));

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

            int ans = numEnclaves(grid);

            try (PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)))) {
                outWriter.println(ans);
            }
        }

        System.out.println("All 100 test cases generated successfully in Testcases/Input and Testcases/Output!");
    }

    // ==========================================
    // Solver Method (Self-Contained)
    // ==========================================
    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, -1, 1};

    public static int numEnclaves(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int N = grid.length;
        int M = grid[0].length;
        boolean[][] visited = new boolean[N][M];

        int totalCells = N * M;
        int[] queueR = new int[totalCells];
        int[] queueC = new int[totalCells];
        int head = 0, tail = 0;

        for (int r = 0; r < N; r++) {
            if (grid[r][0] == 1 && !visited[r][0]) {
                visited[r][0] = true;
                queueR[tail] = r; queueC[tail] = 0; tail++;
            }
            if (M > 1 && grid[r][M - 1] == 1 && !visited[r][M - 1]) {
                visited[r][M - 1] = true;
                queueR[tail] = r; queueC[tail] = M - 1; tail++;
            }
        }

        for (int c = 0; c < M; c++) {
            if (grid[0][c] == 1 && !visited[0][c]) {
                visited[0][c] = true;
                queueR[tail] = 0; queueC[tail] = c; tail++;
            }
            if (N > 1 && grid[N - 1][c] == 1 && !visited[N - 1][c]) {
                visited[N - 1][c] = true;
                queueR[tail] = N - 1; queueC[tail] = c; tail++;
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
                    if (grid[nr][nc] == 1 && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        queueR[tail] = nr;
                        queueC[tail] = nc;
                        tail++;
                    }
                }
            }
        }

        int enclaves = 0;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (grid[r][c] == 1 && !visited[r][c]) {
                    enclaves++;
                }
            }
        }

        return enclaves;
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

    static int[][] makeInteriorAllLand(int N, int M) {
        int[][] g = new int[N][M];
        for (int r = 1; r < N - 1; r++) {
            for (int c = 1; c < M - 1; c++) {
                g[r][c] = 1;
            }
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

    static int[][] makeCheckerboardWithMoat(int N, int M) {
        int[][] g = new int[N][M];
        for (int r = 1; r < N - 1; r++) {
            for (int c = 1; c < M - 1; c++) {
                g[r][c] = ((r + c) % 2 == 0) ? 1 : 0;
            }
        }
        return g;
    }

    static int[][] makeConcentricRings(int N, int M) {
        int[][] g = new int[N][M];
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
        return g;
    }

    static int[][] makeSpiralTouchingBoundary(int N, int M) {
        int[][] g = new int[N][M];
        int top = 0, bottom = N - 1, left = 0, right = M - 1;
        while (top <= bottom && left <= right) {
            for (int c = left; c <= right; c++) g[top][c] = 1;
            top += 2;
            for (int r = top - 1; r <= bottom; r++) g[r][right] = 1;
            right -= 2;
            if (top <= bottom) {
                for (int c = right + 1; c >= left; c--) g[bottom][c] = 1;
                bottom -= 2;
            }
            if (left <= right) {
                for (int r = bottom + 1; r >= top; r--) g[r][left] = 1;
                left += 2;
            }
        }
        return g;
    }

    static int[][] makeQuadrantEnclaves(int N, int M) {
        int[][] g = new int[N][M];
        int midR = N / 2;
        int midC = M / 2;
        for (int r = 1; r < midR - 1; r++) {
            for (int c = 1; c < midC - 1; c++) g[r][c] = 1;
        }
        for (int r = 1; r < midR - 1; r++) {
            for (int c = midC + 1; c < M - 1; c++) g[r][c] = 1;
        }
        for (int r = midR + 1; r < N - 1; r++) {
            for (int c = 1; c < midC - 1; c++) g[r][c] = 1;
        }
        for (int r = midR + 1; r < N - 1; r++) {
            for (int c = midC + 1; c < M - 1; c++) g[r][c] = 1;
        }
        return g;
    }

    static int[][] makeStripesWithMoat(int N, int M, boolean horizontal) {
        int[][] g = new int[N][M];
        if (horizontal) {
            for (int r = 2; r < N - 2; r += 2) {
                for (int c = 2; c < M - 2; c++) g[r][c] = 1;
            }
        } else {
            for (int c = 2; c < M - 2; c += 2) {
                for (int r = 2; r < N - 2; r++) g[r][c] = 1;
            }
        }
        return g;
    }

    static int[][] makeDiagonalWithMoat(int N, int M) {
        int[][] g = new int[N][M];
        for (int r = 2; r < N - 2; r++) {
            for (int c = 2; c < M - 2; c++) {
                if ((r + c) % 3 == 0) g[r][c] = 1;
            }
        }
        return g;
    }

    static int[][] makeBlockEnclaves(int N, int M, int blockR, int blockC) {
        int[][] g = new int[N][M];
        int stepR = blockR + 2;
        int stepC = blockC + 2;
        for (int r = 2; r + blockR < N - 1; r += stepR) {
            for (int c = 2; c + blockC < M - 1; c += stepC) {
                for (int dr = 0; dr < blockR; dr++) {
                    for (int dc = 0; dc < blockC; dc++) {
                        g[r + dr][c + dc] = 1;
                    }
                }
            }
        }
        return g;
    }

    static int[][] makeBridgeToBoundary(int N, int M) {
        int[][] g = new int[N][M];
        int midR = N / 2;
        int midC = M / 2;
        for (int r = midR - 2; r <= midR + 2; r++) {
            for (int c = midC - 2; c <= midC + 2; c++) {
                g[r][c] = 1;
            }
        }
        // Bridge connecting to column 0
        for (int c = 0; c < midC; c++) {
            g[midR][c] = 1;
        }
        return g;
    }

    static int[][] makeBrokenBridge(int N, int M) {
        int[][] g = makeBridgeToBoundary(N, M);
        // Break bridge at column 1 with water
        int midR = N / 2;
        g[midR][1] = 0;
        return g;
    }

    static int[][] makeSnakeInsideMoat(int N, int M) {
        int[][] g = new int[N][M];
        int startR = 2, endR = N - 3;
        int startC = 2, endC = M - 3;
        int count = 0;
        for (int r = startR; r <= endR; r += 2) {
            for (int c = startC; c <= endC; c++) {
                g[r][c] = 1;
            }
            if (r + 1 <= endR) {
                int connectCol = (count % 2 == 0) ? endC : startC;
                g[r + 1][connectCol] = 1;
                count++;
            }
        }
        return g;
    }

    static int[][] makeSnakeFromBoundary(int N, int M) {
        int[][] g = makeSnakeInsideMoat(N, M);
        // Connect the start of the snake at row 2, col 2 directly to col 0
        g[2][0] = 1;
        g[2][1] = 1;
        return g;
    }

    static int[][] makeRandomWithMoat(int N, int M, double probLand, Random rng) {
        int[][] g = new int[N][M];
        for (int r = 1; r < N - 1; r++) {
            for (int c = 1; c < M - 1; c++) {
                if (rng.nextDouble() < probLand) {
                    g[r][c] = 1;
                }
            }
        }
        return g;
    }

    static int[][] makeCaveWithMoat(int N, int M, Random rng) {
        int[][] g = new int[N][M];
        // Cellular automata step
        for (int r = 1; r < N - 1; r++) {
            for (int c = 1; c < M - 1; c++) {
                g[r][c] = (rng.nextDouble() < 0.45) ? 1 : 0;
            }
        }
        for (int step = 0; step < 2; step++) {
            int[][] next = new int[N][M];
            for (int r = 1; r < N - 1; r++) {
                for (int c = 1; c < M - 1; c++) {
                    int count = 0;
                    for (int dr = -1; dr <= 1; dr++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            if (g[r + dr][c + dc] == 1) count++;
                        }
                    }
                    next[r][c] = (count >= 5) ? 1 : 0;
                }
            }
            g = next;
        }
        // Ensure boundaries are strictly 0
        for (int r = 0; r < N; r++) {
            g[r][0] = 0; g[r][M - 1] = 0;
        }
        for (int c = 0; c < M; c++) {
            g[0][c] = 0; g[N - 1][c] = 0;
        }
        return g;
    }

    static int[][] makeLabyrinth(int N, int M, Random rng) {
        int[][] g = new int[N][M];
        for (int r = 2; r < N - 2; r++) {
            for (int c = 2; c < M - 2; c++) {
                if ((r % 2 == 0) || (c % 2 == 0)) {
                    if (rng.nextDouble() < 0.6) {
                        g[r][c] = 1;
                    }
                }
            }
        }
        return g;
    }

    static int[][] makeComplexMaxGrid(int N, int M, Random rng) {
        int[][] g = makeRandomWithMoat(N, M, 0.40, rng);
        // Add multiple dense enclave land patches
        for (int p = 0; p < 8; p++) {
            int topR = 10 + rng.nextInt(N - 80);
            int topC = 10 + rng.nextInt(M - 80);
            int h = 15 + rng.nextInt(35);
            int w = 15 + rng.nextInt(35);
            for (int r = topR; r < topR + h; r++) {
                for (int c = topC; c < topC + w; c++) {
                    g[r][c] = 1;
                }
            }
        }
        // Add boundary intrusions (land fingers extending from boundary that escape)
        for (int k = 0; k < 5; k++) {
            int rStart = rng.nextInt(N);
            for (int c = 0; c < 30 + rng.nextInt(40); c++) {
                g[rStart][c] = 1;
            }
        }
        return g;
    }
}
