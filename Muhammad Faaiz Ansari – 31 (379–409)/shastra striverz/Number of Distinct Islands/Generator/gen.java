import java.io.*;
import java.util.*;

public class gen {

    public static void main(String[] args) throws IOException {
        File baseDir = new File("d:/MF Shastra/shastra striverz/Number of Distinct Islands/Testcases");
        if (!baseDir.isAbsolute()) {
            baseDir = new File("../Testcases");
        }
        baseDir.mkdirs();

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<int[][]> testCases = new ArrayList<>();
        Random rng = new Random(532);

        // ==========================================
        // 00 - 02: Platform Samples
        // ==========================================
        // Sample 1: 4x5 -> 1
        testCases.add(new int[][]{
            {1, 1, 0, 0, 0},
            {1, 1, 0, 0, 0},
            {0, 0, 0, 1, 1},
            {0, 0, 0, 1, 1}
        });

        // Sample 2: 4x5 -> 3
        testCases.add(new int[][]{
            {1, 1, 0, 1, 1},
            {1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1},
            {1, 1, 0, 1, 1}
        });

        // Sample 3: 4x5 -> 2
        testCases.add(new int[][]{
            {1, 1, 0, 0, 0},
            {1, 1, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 1, 1}
        });

        // ==========================================
        // 03 - 12: Minimal Boundaries & Trivial Cases
        // ==========================================
        // TC 03: 1x1 water -> 0
        testCases.add(new int[][]{{0}});

        // TC 04: 1x1 land -> 1
        testCases.add(new int[][]{{1}});

        // TC 05: 1x2 [1, 0] -> 1
        testCases.add(new int[][]{{1, 0}});

        // TC 06: 1x2 [1, 1] -> 1
        testCases.add(new int[][]{{1, 1}});

        // TC 07: 1x5 three 1x1 islands -> 1 (all same shape!)
        testCases.add(new int[][]{{1, 0, 1, 0, 1}});

        // TC 08: 1x6 two 1x2 islands -> 1
        testCases.add(new int[][]{{1, 1, 0, 1, 1, 0}});

        // TC 09: 5x1 vertical islands -> 1
        testCases.add(new int[][]{{1}, {0}, {1}, {0}, {1}});

        // TC 10: 2x2 all 0 -> 0
        testCases.add(new int[][]{
            {0, 0},
            {0, 0}
        });

        // TC 11: 2x2 all 1 -> 1
        testCases.add(new int[][]{
            {1, 1},
            {1, 1}
        });

        // TC 12: 2x2 diagonal -> 1 (two 1x1 islands)
        testCases.add(new int[][]{
            {1, 0},
            {0, 1}
        });

        // ==========================================
        // 13 - 24: Polyominoes & Orientation Tests (3x3 to 8x8)
        // ==========================================
        // TC 13: 4x4 horizontal vs vertical domino -> 2
        testCases.add(new int[][]{
            {1, 1, 0, 0},
            {0, 0, 0, 1},
            {0, 0, 0, 1},
            {0, 0, 0, 0}
        });

        // TC 14: 6x6 four rotations/reflections of "L" shape -> 4
        testCases.add(new int[][]{
            {1, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0}
        });

        // TC 15: 5x5 five tetrominoes (I, O, T, L, Z)
        testCases.add(makeTetrominoGrid(5, 5));

        // TC 16: 6x6 checkerboard -> 1 (all eighteen 1s are 1x1 islands)
        testCases.add(makeCheckerboard(6, 6));

        // TC 17: 7x7 three concentric square rings -> 3
        testCases.add(makeConcentricRings(7, 7));

        // TC 18: 8x8 multiple identical 2x2 squares placed randomly -> 1
        testCases.add(makeStampedIdentical(8, 8, new int[][]{{1, 1}, {1, 1}}));

        // TC 19: 8x8 multiple identical 3-cell plus (+) shapes -> 1
        testCases.add(makeStampedIdentical(8, 8, new int[][]{{0, 1, 0}, {1, 1, 1}, {0, 1, 0}}));

        // TC 20: 8x8 two shapes: 2x2 square and 1x3 line -> 2
        testCases.add(makeTwoShapesGrid(8, 8));

        // TC 21: 8x8 stairs shape vs inverse stairs -> 2
        testCases.add(new int[][]{
            {1, 0, 0, 0, 0, 1},
            {1, 1, 0, 0, 1, 1},
            {0, 1, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0}
        });

        // TC 22: 7x7 single serpentine snake -> 1
        testCases.add(makeSnakeGrid(7, 7));

        // TC 23: 8x8 four identical diagonal triominoes -> 1
        testCases.add(makeDiagonalTriominoes(8, 8));

        // TC 24: 8x8 random sparse
        testCases.add(makeRandomGrid(8, 8, 0.25, rng));

        // ==========================================
        // 25 - 44: Medium Grids (10x10 to 50x50)
        // ==========================================
        // TC 25: 10x10 stamped crosses -> 1
        testCases.add(makeStampedIdentical(10, 10, new int[][]{{0, 1, 0}, {1, 1, 1}, {0, 1, 0}}));

        // TC 26: 12x12 stamped 2x2 squares -> 1
        testCases.add(makeStampedIdentical(12, 12, new int[][]{{1, 1}, {1, 1}}));

        // TC 27: 15x15 stamped 3 distinct shapes (cross, square, L) -> 3
        testCases.add(makeThreeShapesGrid(15, 15));

        // TC 28: 16x16 concentric rings -> 4
        testCases.add(makeConcentricRings(16, 16));

        // TC 29: 20x20 checkerboard -> 1
        testCases.add(makeCheckerboard(20, 20));

        // TC 30: 20x20 isolated dots at step 3 -> 1
        testCases.add(makeIsolatedDots(20, 20, 3));

        // TC 31: 25x25 horizontal stripes -> 1 (all stripes are 1xM rectangles)
        testCases.add(makeHorizontalStripes(25, 25));

        // TC 32: 25x25 vertical stripes -> 1
        testCases.add(makeVerticalStripes(25, 25));

        // TC 33: 30x30 diagonal lines of length 4 -> 1
        testCases.add(makeDiagonalSegments(30, 30, 4));

        // TC 34: 30x30 stamped 5 polyominoes scattered -> 5
        testCases.add(makeFiveShapesGrid(30, 30));

        // TC 35: 35x35 random sparse (prob 0.15)
        testCases.add(makeRandomGrid(35, 35, 0.15, rng));

        // TC 36: 40x20 asymmetric stamped grid
        testCases.add(makeStampedIdentical(40, 20, new int[][]{{1, 1, 1}, {0, 1, 0}}));

        // TC 37: 20x40 asymmetric stamped grid
        testCases.add(makeThreeShapesGrid(20, 40));

        // TC 38: 45x45 cellular automata islands
        testCases.add(makeCaveGrid(45, 45, rng));

        // TC 39: 50x50 checkerboard -> 1
        testCases.add(makeCheckerboard(50, 50));

        // TC 40: 50x50 snake path -> 1
        testCases.add(makeSnakeGrid(50, 50));

        // TC 41: 50x50 concentric rings
        testCases.add(makeConcentricRings(50, 50));

        // TC 42: 50x50 100 identical stamped stars -> 1
        testCases.add(makeStampedIdentical(50, 50, new int[][]{{0, 1, 0}, {1, 1, 1}, {0, 1, 0}}));

        // TC 43: 50x50 random medium (prob 0.35)
        testCases.add(makeRandomGrid(50, 50, 0.35, rng));

        // TC 44: 50x50 five shapes scattered
        testCases.add(makeFiveShapesGrid(50, 50));

        // ==========================================
        // 45 - 69: Large Grids (60x60 to 180x180)
        // ==========================================
        for (int i = 45; i <= 69; i++) {
            int dimR = 60 + (i - 45) * 5;
            int dimC = 60 + ((i * 11) % 60);

            if (i % 6 == 0) {
                testCases.add(makeCheckerboard(dimR, dimC));
            } else if (i % 6 == 1) {
                testCases.add(makeConcentricRings(dimR, dimC));
            } else if (i % 6 == 2) {
                testCases.add(makeStampedIdentical(dimR, dimC, new int[][]{{1, 1, 1}, {1, 0, 1}}));
            } else if (i % 6 == 3) {
                testCases.add(makeThreeShapesGrid(dimR, dimC));
            } else if (i % 6 == 4) {
                testCases.add(makeFiveShapesGrid(dimR, dimC));
            } else {
                testCases.add(makeCaveGrid(dimR, dimC, rng));
            }
        }

        // ==========================================
        // 70 - 89: Very Large Grids (200x200 to 420x420)
        // ==========================================
        for (int i = 70; i <= 89; i++) {
            int dimR = 200 + (i - 70) * 11;
            int dimC = 200 + ((i * 13) % 150);

            if (i % 5 == 0) {
                testCases.add(makeCheckerboard(dimR, dimC));
            } else if (i % 5 == 1) {
                testCases.add(makeConcentricRings(dimR, dimC));
            } else if (i % 5 == 2) {
                testCases.add(makeFiveShapesGrid(dimR, dimC));
            } else if (i % 5 == 3) {
                testCases.add(makeStampedIdentical(dimR, dimC, new int[][]{{0, 1, 0}, {1, 1, 1}, {0, 1, 0}}));
            } else {
                testCases.add(makeCaveGrid(dimR, dimC, rng));
            }
        }

        // ==========================================
        // 90 - 99: Extreme Constraints (Up to 500x500)
        // ==========================================
        // TC 90: 500x500 all 0s -> 0
        testCases.add(makeFilledGrid(500, 500, 0));

        // TC 91: 500x500 all 1s -> 1 (one single giant island)
        testCases.add(makeFilledGrid(500, 500, 1));

        // TC 92: 500x500 checkerboard -> 1 (125,000 isolated 1x1 islands, all same shape!)
        testCases.add(makeCheckerboard(500, 500));

        // TC 93: 500x500 concentric rings (approx 125 distinct rings)
        testCases.add(makeConcentricRings(500, 500));

        // TC 94: 500x500 stamped identical crosses across entire board -> 1
        testCases.add(makeStampedIdentical(500, 500, new int[][]{{0, 1, 0}, {1, 1, 1}, {0, 1, 0}}));

        // TC 95: 500x500 stamped 3 distinct shapes (cross, square, L) -> 3
        testCases.add(makeThreeShapesGrid(500, 500));

        // TC 96: 500x500 stamped 5 distinct shapes -> 5
        testCases.add(makeFiveShapesGrid(500, 500));

        // TC 97: 500x500 massive single serpentine snake -> 1
        testCases.add(makeSnakeGrid(500, 500));

        // TC 98: 500x500 random sparse (prob 0.12)
        testCases.add(makeRandomGrid(500, 500, 0.12, rng));

        // TC 99: 500x500 random dense (prob 0.45)
        testCases.add(makeRandomGrid(500, 500, 0.45, rng));

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

            int ans = countDistinctIslands(grid);

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

    public static int countDistinctIslands(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int N = grid.length;
        int M = grid[0].length;
        boolean[][] visited = new boolean[N][M];

        Set<String> distinctIslands = new HashSet<>();

        int totalCells = N * M;
        int[] queueR = new int[totalCells];
        int[] queueC = new int[totalCells];

        for (int r0 = 0; r0 < N; r0++) {
            for (int c0 = 0; c0 < M; c0++) {
                if (grid[r0][c0] == 1 && !visited[r0][c0]) {
                    visited[r0][c0] = true;
                    int head = 0, tail = 0;

                    queueR[tail] = r0;
                    queueC[tail] = c0;
                    tail++;

                    List<Long> shape = new ArrayList<>();

                    while (head < tail) {
                        int r = queueR[head];
                        int c = queueC[head];
                        head++;

                        int diffR = r - r0;
                        int diffC = c - c0;
                        long packed = (((long) diffR) << 32) | (((long) diffC) & 0xFFFFFFFFL);
                        shape.add(packed);

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

                    Collections.sort(shape);

                    StringBuilder sb = new StringBuilder();
                    for (long val : shape) {
                        int diffR = (int) (val >> 32);
                        int diffC = (int) val;
                        sb.append('(').append(diffR).append(',').append(diffC).append(')');
                    }

                    distinctIslands.add(sb.toString());
                }
            }
        }

        return distinctIslands.size();
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

    static int[][] makeCheckerboard(int N, int M) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = ((r + c) % 2 == 0) ? 1 : 0;
            }
        }
        return g;
    }

    static int[][] makeIsolatedDots(int N, int M, int step) {
        int[][] g = makeFilledGrid(N, M, 0);
        for (int r = 0; r < N; r += step) {
            for (int c = 0; c < M; c += step) {
                g[r][c] = 1;
            }
        }
        return g;
    }

    static int[][] makeHorizontalStripes(int N, int M) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r += 2) {
            Arrays.fill(g[r], 1);
        }
        return g;
    }

    static int[][] makeVerticalStripes(int N, int M) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c += 2) {
                g[r][c] = 1;
            }
        }
        return g;
    }

    static int[][] makeDiagonalSegments(int N, int M, int len) {
        int[][] g = makeFilledGrid(N, M, 0);
        for (int r = 0; r + len < N; r += len + 2) {
            for (int c = 0; c + len < M; c += len + 2) {
                for (int d = 0; d < len; d++) {
                    g[r + d][c + d] = 1;
                }
            }
        }
        return g;
    }

    static int[][] makeDiagonalTriominoes(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 0);
        for (int r = 0; r + 2 < N; r += 4) {
            for (int c = 0; c + 2 < M; c += 4) {
                g[r][c] = 1;
                g[r + 1][c + 1] = 1;
                g[r + 2][c + 2] = 1;
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

    static int[][] makeTetrominoGrid(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 0);
        // I shape
        if (N >= 4 && M >= 1) { g[0][0] = 1; g[1][0] = 1; g[2][0] = 1; g[3][0] = 1; }
        // O shape
        if (N >= 2 && M >= 4) { g[0][2] = 1; g[0][3] = 1; g[1][2] = 1; g[1][3] = 1; }
        // T shape
        if (N >= 5 && M >= 3) { g[3][2] = 1; g[4][1] = 1; g[4][2] = 1; g[4][3] = 1; }
        return g;
    }

    static int[][] makeStampedIdentical(int N, int M, int[][] shape) {
        int[][] g = makeFilledGrid(N, M, 0);
        int shH = shape.length;
        int shW = shape[0].length;
        int stepR = shH + 2;
        int stepC = shW + 2;

        for (int r = 0; r + shH <= N; r += stepR) {
            for (int c = 0; c + shW <= M; c += stepC) {
                for (int dr = 0; dr < shH; dr++) {
                    for (int dc = 0; dc < shW; dc++) {
                        g[r + dr][c + dc] = shape[dr][dc];
                    }
                }
            }
        }
        return g;
    }

    static int[][] makeTwoShapesGrid(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 0);
        // Stamp 2x2 square in top half
        for (int c = 0; c + 2 <= M; c += 4) {
            g[0][c] = 1; g[0][c + 1] = 1;
            g[1][c] = 1; g[1][c + 1] = 1;
        }
        // Stamp 1x3 horizontal bar in bottom half
        if (N >= 5) {
            for (int c = 0; c + 3 <= M; c += 5) {
                g[4][c] = 1; g[4][c + 1] = 1; g[4][c + 2] = 1;
            }
        }
        return g;
    }

    static int[][] makeThreeShapesGrid(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 0);
        int rowStep = 6;
        for (int r = 0; r + 4 <= N; r += rowStep) {
            // Shape 1: 2x2
            for (int c = 0; c + 2 <= M / 3; c += 4) {
                g[r][c] = 1; g[r][c + 1] = 1;
                g[r + 1][c] = 1; g[r + 1][c + 1] = 1;
            }
            // Shape 2: Cross
            int startC2 = M / 3 + 1;
            for (int c = startC2; c + 3 <= 2 * M / 3; c += 5) {
                g[r][c + 1] = 1;
                g[r + 1][c] = 1; g[r + 1][c + 1] = 1; g[r + 1][c + 2] = 1;
                g[r + 2][c + 1] = 1;
            }
            // Shape 3: L shape
            int startC3 = 2 * M / 3 + 1;
            for (int c = startC3; c + 2 <= M; c += 4) {
                g[r][c] = 1;
                g[r + 1][c] = 1;
                g[r + 2][c] = 1; g[r + 2][c + 1] = 1;
            }
        }
        return g;
    }

    static int[][] makeFiveShapesGrid(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 0);
        int colWidth = M / 5;
        for (int sec = 0; sec < 5; sec++) {
            int c0 = sec * colWidth + 1;
            for (int r = 1; r + 4 <= N; r += 6) {
                if (c0 + 3 < M) {
                    if (sec == 0) { // single dot
                        g[r][c0] = 1;
                    } else if (sec == 1) { // 2x2 square
                        g[r][c0] = 1; g[r][c0 + 1] = 1;
                        g[r + 1][c0] = 1; g[r + 1][c0 + 1] = 1;
                    } else if (sec == 2) { // 3x1 line
                        g[r][c0] = 1; g[r + 1][c0] = 1; g[r + 2][c0] = 1;
                    } else if (sec == 3) { // plus (+)
                        g[r][c0 + 1] = 1;
                        g[r + 1][c0] = 1; g[r + 1][c0 + 1] = 1; g[r + 1][c0 + 2] = 1;
                        g[r + 2][c0 + 1] = 1;
                    } else { // corner L
                        g[r][c0] = 1;
                        g[r + 1][c0] = 1; g[r + 1][c0 + 1] = 1;
                    }
                }
            }
        }
        return g;
    }

    static int[][] makeRandomGrid(int N, int M, double prob1, Random rng) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = (rng.nextDouble() < prob1) ? 1 : 0;
            }
        }
        return g;
    }

    static int[][] makeCaveGrid(int N, int M, Random rng) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = (rng.nextDouble() < 0.45) ? 1 : 0;
            }
        }
        for (int step = 0; step < 2; step++) {
            int[][] next = new int[N][M];
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    int count = 0;
                    for (int dr = -1; dr <= 1; dr++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            int nr = r + dr, nc = c + dc;
                            if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
                                if (g[nr][nc] == 1) count++;
                            }
                        }
                    }
                    next[r][c] = (count >= 5) ? 1 : 0;
                }
            }
            g = next;
        }
        return g;
    }
}
