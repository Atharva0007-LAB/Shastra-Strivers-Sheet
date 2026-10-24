import java.io.*;
import java.util.*;

public class gen {

    public static void main(String[] args) throws IOException {
        File baseDir = new File("d:/MF Shastra/shastra striverz/Rotten Oranges/Testcases");
        if (!baseDir.isAbsolute()) {
            baseDir = new File("../Testcases");
        }
        baseDir.mkdirs();

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<int[][]> testCases = new ArrayList<>();
        Random rng = new Random(536);

        // ==========================================
        // 00 - 02: Platform Samples
        // ==========================================
        // Sample 1: 3x3 -> -1
        testCases.add(new int[][]{
            {2, 1, 1},
            {0, 1, 1},
            {1, 0, 1}
        });

        // Sample 2: 3x3 -> 4
        testCases.add(new int[][]{
            {2, 1, 1},
            {1, 1, 0},
            {0, 1, 1}
        });

        // Sample 3: 3x3 -> 1
        testCases.add(new int[][]{
            {0, 1, 2},
            {0, 1, 2},
            {2, 1, 1}
        });

        // ==========================================
        // 03 - 12: Minimal Boundaries & Trivial Cases
        // ==========================================
        // TC 03: 1x1 empty -> 0
        testCases.add(new int[][]{{0}});

        // TC 04: 1x1 fresh only -> -1
        testCases.add(new int[][]{{1}});

        // TC 05: 1x1 rotten only -> 0
        testCases.add(new int[][]{{2}});

        // TC 06: 1x2 rotten + fresh -> 1
        testCases.add(new int[][]{{2, 1}});

        // TC 07: 1x2 fresh + rotten -> 1
        testCases.add(new int[][]{{1, 2}});

        // TC 08: 1x2 empty + fresh -> -1
        testCases.add(new int[][]{{0, 1}});

        // TC 09: 1x2 empty + rotten -> 0
        testCases.add(new int[][]{{2, 0}});

        // TC 10: 1x5 linear chain -> 4
        testCases.add(new int[][]{{2, 1, 1, 1, 1}});

        // TC 11: 5x1 vertical chain -> 4
        testCases.add(new int[][]{{2}, {1}, {1}, {1}, {1}});

        // TC 12: 2x2 all 1s -> -1
        testCases.add(new int[][]{
            {1, 1},
            {1, 1}
        });

        // ==========================================
        // 13 - 19: Small Specific Topologies
        // ==========================================
        // TC 13: 3x3 center 2, surrounded by 1s -> 2
        testCases.add(new int[][]{
            {1, 1, 1},
            {1, 2, 1},
            {1, 1, 1}
        });

        // TC 14: 3x3 rotten at (0,0), fresh at (2,2) walled by 0s -> -1
        testCases.add(new int[][]{
            {2, 0, 0},
            {0, 0, 0},
            {0, 0, 1}
        });

        // TC 15: 4x4 two rotten at opposite corners
        testCases.add(new int[][]{
            {2, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 2}
        });

        // TC 16: 5x5 snake of 1s with 2 at head
        testCases.add(makeSnakeGrid(5, 5));

        // TC 17: 5x5 spiral of 1s with 2 at outer corner
        testCases.add(makeSpiralGrid(5, 5));

        // TC 18: 5x5 concentric rings: outer 2, middle 0, inner 1 -> -1
        testCases.add(new int[][]{
            {2, 2, 2, 2, 2},
            {2, 0, 0, 0, 2},
            {2, 0, 1, 0, 2},
            {2, 0, 0, 0, 2},
            {2, 2, 2, 2, 2}
        });

        // TC 19: 5x5 concentric rings: outer 2, middle 1, inner 1 -> 2
        testCases.add(new int[][]{
            {2, 2, 2, 2, 2},
            {2, 1, 1, 1, 2},
            {2, 1, 1, 1, 2},
            {2, 1, 1, 1, 2},
            {2, 2, 2, 2, 2}
        });

        // ==========================================
        // 20 - 39: Medium Grids (8x8 to 50x50)
        // ==========================================
        // TC 20: 8x8 checkerboard of 2 and 1 -> 1
        testCases.add(makeCheckerboard21(8, 8));

        // TC 21: 10x10 maze solvable
        testCases.add(makeSolvableMaze(10, 10, rng));

        // TC 22: 10x10 maze with unreachable fresh island -> -1
        testCases.add(makeUnreachableMaze(10, 10, rng));

        // TC 23: 12x12 snake chain
        testCases.add(makeSnakeGrid(12, 12));

        // TC 24: 15x15 single center rotten orange in sea of fresh
        testCases.add(makeCenterRotten(15, 15));

        // TC 25: 16x16 corner rotten orange in sea of fresh
        testCases.add(makeCornerRotten(16, 16));

        // TC 26: 20x20 random sparse solvable
        testCases.add(makeRandomSolvable(20, 20, 0.4, 0.05, rng));

        // TC 27: 20x20 random with unreachable 1 -> -1
        testCases.add(makeRandomWithUnreachable(20, 20, 0.4, 0.05, rng));

        // TC 28: 25x25 cross shape propagation
        testCases.add(makeCrossGrid(25, 25));

        // TC 29: 30x30 diagonal band of fresh with 2 at ends
        testCases.add(makeDiagonalGrid(30, 30));

        // TC 30: 30x30 spiral
        testCases.add(makeSpiralGrid(30, 30));

        // TC 31: 35x35 multi-source grid (10 rotten seeds)
        testCases.add(makeMultiSource(35, 35, 10, rng));

        // TC 32: 40x20 asymmetric solvable
        testCases.add(makeRandomSolvable(40, 20, 0.5, 0.08, rng));

        // TC 33: 20x40 asymmetric unsolvable -> -1
        testCases.add(makeRandomWithUnreachable(20, 40, 0.5, 0.08, rng));

        // TC 34: 45x45 cellular automata cave with 2 at start
        testCases.add(makeCaveGrid(45, 45, rng, true));

        // TC 35: 45x45 cellular automata cave with trapped 1 -> -1
        testCases.add(makeCaveGrid(45, 45, rng, false));

        // TC 36: 50x50 snake path
        testCases.add(makeSnakeGrid(50, 50));

        // TC 37: 50x50 checkerboard of 0, 1, 2
        testCases.add(makeMixedCheckerboard(50, 50));

        // TC 38: 50x50 diamond propagation
        testCases.add(makeDiamondGrid(50, 50));

        // TC 39: 50x50 dense fresh with single rotten at (0, 0)
        testCases.add(makeCornerRotten(50, 50));

        // ==========================================
        // 40 - 69: Large Grids (60x60 to 180x180)
        // ==========================================
        for (int i = 40; i <= 69; i++) {
            int dimR = 60 + (i - 40) * 4;
            int dimC = 60 + ((i * 7) % 50);

            if (i % 5 == 0) {
                testCases.add(makeSnakeGrid(dimR, dimC));
            } else if (i % 5 == 1) {
                testCases.add(makeCenterRotten(dimR, dimC));
            } else if (i % 5 == 2) {
                testCases.add(makeMultiSource(dimR, dimC, 15 + i, rng));
            } else if (i % 5 == 3) {
                testCases.add(makeRandomSolvable(dimR, dimC, 0.45, 0.06, rng));
            } else {
                testCases.add(makeRandomWithUnreachable(dimR, dimC, 0.45, 0.06, rng));
            }
        }

        // ==========================================
        // 70 - 89: Very Large Grids (200x200 to 420x420)
        // ==========================================
        for (int i = 70; i <= 89; i++) {
            int dimR = 200 + (i - 70) * 11;
            int dimC = 200 + ((i * 13) % 150);

            if (i % 4 == 0) {
                testCases.add(makeCornerRotten(dimR, dimC));
            } else if (i % 4 == 1) {
                testCases.add(makeMultiSource(dimR, dimC, 40, rng));
            } else if (i % 4 == 2) {
                testCases.add(makeRandomSolvable(dimR, dimC, 0.55, 0.05, rng));
            } else {
                testCases.add(makeRandomWithUnreachable(dimR, dimC, 0.55, 0.05, rng));
            }
        }

        // ==========================================
        // 90 - 99: Extreme Constraints (Up to 500x500)
        // ==========================================
        // TC 90: 500x500 all 0s -> 0
        testCases.add(makeFilledGrid(500, 500, 0));

        // TC 91: 500x500 all 2s -> 0
        testCases.add(makeFilledGrid(500, 500, 2));

        // TC 92: 500x500 all 1s -> -1 (no rotten orange exists)
        testCases.add(makeFilledGrid(500, 500, 1));

        // TC 93: 500x500 single rotten orange at (0, 0), rest all 1s -> 499 + 499 = 998
        testCases.add(makeCornerRotten(500, 500));

        // TC 94: 500x500 single rotten orange at center (250, 250), rest all 1s -> 500
        testCases.add(makeCenterRotten(500, 500));

        // TC 95: 500x500 four corners are 2, rest all 1s
        testCases.add(makeFourCornersRotten(500, 500));

        // TC 96: 500x500 massive serpentine snake path of fresh with 2 at start
        testCases.add(makeSnakeGrid(500, 500));

        // TC 97: 500x500 multi-source with 100 scattered rotten oranges
        testCases.add(makeMultiSource(500, 500, 100, rng));

        // TC 98: 500x500 random dense grid with guaranteed unreachable fresh orange -> -1
        testCases.add(makeRandomWithUnreachable(500, 500, 0.60, 0.04, rng));

        // TC 99: 500x500 complex contagion simulation (all reachable)
        testCases.add(makeRandomSolvable(500, 500, 0.65, 0.08, rng));

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

            int ans = orangesRotting(grid);

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

    public static int orangesRotting(int[][] inputGrid) {
        if (inputGrid == null || inputGrid.length == 0 || inputGrid[0].length == 0) return 0;

        int N = inputGrid.length;
        int M = inputGrid[0].length;
        int totalCells = N * M;

        int[][] grid = new int[N][M];
        for (int r = 0; r < N; r++) {
            System.arraycopy(inputGrid[r], 0, grid[r], 0, M);
        }

        int[] queueR = new int[totalCells];
        int[] queueC = new int[totalCells];
        int head = 0;
        int tail = 0;
        int freshCount = 0;

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (grid[r][c] == 2) {
                    queueR[tail] = r;
                    queueC[tail] = c;
                    tail++;
                } else if (grid[r][c] == 1) {
                    freshCount++;
                }
            }
        }

        if (freshCount == 0) return 0;

        int minutes = 0;
        while (head < tail && freshCount > 0) {
            int levelSize = tail - head;
            minutes++;

            for (int i = 0; i < levelSize; i++) {
                int r = queueR[head];
                int c = queueC[head];
                head++;

                for (int k = 0; k < 4; k++) {
                    int nr = r + dr[k];
                    int nc = c + dc[k];

                    if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
                        if (grid[nr][nc] == 1) {
                            grid[nr][nc] = 2;
                            freshCount--;
                            queueR[tail] = nr;
                            queueC[tail] = nc;
                            tail++;
                        }
                    }
                }
            }
        }

        return freshCount == 0 ? minutes : -1;
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

    static int[][] makeCornerRotten(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 1);
        g[0][0] = 2;
        return g;
    }

    static int[][] makeCenterRotten(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 1);
        g[N / 2][M / 2] = 2;
        return g;
    }

    static int[][] makeFourCornersRotten(int N, int M) {
        int[][] g = makeFilledGrid(N, M, 1);
        g[0][0] = 2;
        g[0][M - 1] = 2;
        g[N - 1][0] = 2;
        g[N - 1][M - 1] = 2;
        return g;
    }

    static int[][] makeSnakeGrid(int N, int M) {
        int[][] g = new int[N][M];
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
        g[0][0] = 2;
        return g;
    }

    static int[][] makeSpiralGrid(int N, int M) {
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
        g[0][0] = 2;
        return g;
    }

    static int[][] makeCheckerboard21(int N, int M) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = ((r + c) % 2 == 0) ? 2 : 1;
            }
        }
        return g;
    }

    static int[][] makeMixedCheckerboard(int N, int M) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = (r + c) % 3;
            }
        }
        return g;
    }

    static int[][] makeCrossGrid(int N, int M) {
        int[][] g = new int[N][M];
        int midR = N / 2;
        int midC = M / 2;
        for (int r = 0; r < N; r++) g[r][midC] = 1;
        for (int c = 0; c < M; c++) g[midR][c] = 1;
        g[midR][midC] = 2;
        return g;
    }

    static int[][] makeDiagonalGrid(int N, int M) {
        int[][] g = new int[N][M];
        for (int i = 0; i < Math.min(N, M); i++) {
            g[i][i] = 1;
            if (i + 1 < M) g[i][i + 1] = 1;
        }
        g[0][0] = 2;
        return g;
    }

    static int[][] makeDiamondGrid(int N, int M) {
        int[][] g = new int[N][M];
        int midR = N / 2;
        int midC = M / 2;
        int maxDist = Math.min(midR, midC);
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (Math.abs(r - midR) + Math.abs(c - midC) <= maxDist) {
                    g[r][c] = 1;
                }
            }
        }
        g[midR][midC] = 2;
        return g;
    }

    static int[][] makeMultiSource(int N, int M, int numRotten, Random rng) {
        int[][] g = makeFilledGrid(N, M, 1);
        for (int k = 0; k < numRotten; k++) {
            int r = rng.nextInt(N);
            int c = rng.nextInt(M);
            g[r][c] = 2;
        }
        return g;
    }

    static int[][] makeSolvableMaze(int N, int M, Random rng) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = (rng.nextDouble() < 0.7) ? 1 : 0;
            }
        }
        g[0][0] = 2;
        for (int r = 0; r < N - 1; r++) g[r][0] = 1;
        for (int c = 0; c < M; c++) g[N - 1][c] = 1;
        g[0][0] = 2;
        return g;
    }

    static int[][] makeUnreachableMaze(int N, int M, Random rng) {
        int[][] g = makeSolvableMaze(N, M, rng);
        // Isolate bottom right cell with 0s
        g[N - 1][M - 1] = 1;
        if (N > 1) g[N - 2][M - 1] = 0;
        if (M > 1) g[N - 1][M - 2] = 0;
        return g;
    }

    static int[][] makeRandomSolvable(int N, int M, double probFresh, double probRotten, Random rng) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                double p = rng.nextDouble();
                if (p < probRotten) {
                    g[r][c] = 2;
                } else if (p < probRotten + probFresh) {
                    g[r][c] = 1;
                } else {
                    g[r][c] = 0;
                }
            }
        }
        g[0][0] = 2;
        // Make sure all 1s have a path to a 2 by turning isolated 1s to 2s
        int[] queueR = new int[N * M];
        int[] queueC = new int[N * M];
        boolean[][] vis = new boolean[N][M];
        int head = 0, tail = 0;
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (g[r][c] == 2) {
                    vis[r][c] = true;
                    queueR[tail] = r; queueC[tail] = c; tail++;
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
                    if (g[nr][nc] == 1 && !vis[nr][nc]) {
                        vis[nr][nc] = true;
                        queueR[tail] = nr; queueC[tail] = nc; tail++;
                    }
                }
            }
        }
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (g[r][c] == 1 && !vis[r][c]) {
                    g[r][c] = 0; // Remove unreachable 1 to keep it solvable
                }
            }
        }
        return g;
    }

    static int[][] makeRandomWithUnreachable(int N, int M, double probFresh, double probRotten, Random rng) {
        int[][] g = makeRandomSolvable(N, M, probFresh, probRotten, rng);
        // Deliberately plant an isolated fresh orange surrounded by empty cells
        int r = Math.min(N - 1, 3);
        int c = Math.min(M - 1, 3);
        g[r][c] = 1;
        if (r > 0) g[r - 1][c] = 0;
        if (r + 1 < N) g[r + 1][c] = 0;
        if (c > 0) g[r][c - 1] = 0;
        if (c + 1 < M) g[r][c + 1] = 0;
        return g;
    }

    static int[][] makeCaveGrid(int N, int M, Random rng, boolean solvable) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = (rng.nextDouble() < 0.6) ? 1 : 0;
            }
        }
        g[0][0] = 2;
        if (!solvable) {
            g[N - 1][M - 1] = 1;
            if (N > 1) g[N - 2][M - 1] = 0;
            if (M > 1) g[N - 1][M - 2] = 0;
        }
        return g;
    }
}
