import java.io.*;
import java.util.*;

public class gen {

    public static void main(String[] args) throws IOException {
        File baseDir = new File("d:/MF Shastra/shastra striverz/Surrounded Regions/Testcases");
        if (!baseDir.isAbsolute()) {
            baseDir = new File("../Testcases");
        }
        baseDir.mkdirs();

        File inputDir = new File(baseDir, "Input");
        File outputDir = new File(baseDir, "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        List<char[][]> testCases = new ArrayList<>();
        Random rng = new Random(537);

        // ==========================================
        // 00 - 02: Platform Samples
        // ==========================================
        // Sample 1: 4x4
        testCases.add(new char[][]{
            {'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'X'},
            {'X', 'X', 'O', 'X'},
            {'X', 'O', 'X', 'X'}
        });

        // Sample 2: 3x3
        testCases.add(new char[][]{
            {'X', 'X', 'X'},
            {'X', 'O', 'X'},
            {'X', 'X', 'X'}
        });

        // Sample 3: 4x4
        testCases.add(new char[][]{
            {'X', 'X', 'X', 'O'},
            {'X', 'X', 'X', 'X'},
            {'O', 'X', 'X', 'X'},
            {'X', 'X', 'X', 'X'}
        });

        // ==========================================
        // 03 - 12: Minimal Boundaries & Trivial Cases
        // ==========================================
        // TC 03: 1x1 O -> O
        testCases.add(new char[][]{{'O'}});

        // TC 04: 1x1 X -> X
        testCases.add(new char[][]{{'X'}});

        // TC 05: 1x2 O, X -> O, X
        testCases.add(new char[][]{{'O', 'X'}});

        // TC 06: 2x1 X, O -> X, O
        testCases.add(new char[][]{{'X'}, {'O'}});

        // TC 07: 1x5 all O -> all O
        testCases.add(new char[][]{{'O', 'O', 'O', 'O', 'O'}});

        // TC 08: 5x1 alternating -> all on boundary
        testCases.add(new char[][]{{'X'}, {'O'}, {'X'}, {'O'}, {'X'}});

        // TC 09: 2x2 all O -> none enclosed
        testCases.add(new char[][]{
            {'O', 'O'},
            {'O', 'O'}
        });

        // TC 10: 2x2 all X
        testCases.add(new char[][]{
            {'X', 'X'},
            {'X', 'X'}
        });

        // TC 11: 3x3 center O, border X -> center flips
        testCases.add(new char[][]{
            {'X', 'X', 'X'},
            {'X', 'O', 'X'},
            {'X', 'X', 'X'}
        });

        // TC 12: 3x3 center X, border O -> none flips
        testCases.add(new char[][]{
            {'O', 'O', 'O'},
            {'O', 'X', 'O'},
            {'O', 'O', 'O'}
        });

        // ==========================================
        // 13 - 24: Small Structured Patterns (4x4 to 8x8)
        // ==========================================
        // TC 13: 4x4 center 2x2 of O -> all flipped
        testCases.add(new char[][]{
            {'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'X'},
            {'X', 'O', 'O', 'X'},
            {'X', 'X', 'X', 'X'}
        });

        // TC 14: 4x4 2x2 of O touching top-left -> none flipped
        testCases.add(new char[][]{
            {'O', 'O', 'X', 'X'},
            {'O', 'O', 'X', 'X'},
            {'X', 'X', 'X', 'X'},
            {'X', 'X', 'X', 'X'}
        });

        // TC 15: 5x5 solid 3x3 of O in center -> flipped
        testCases.add(new char[][]{
            {'X', 'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'O', 'X'},
            {'X', 'O', 'O', 'O', 'X'},
            {'X', 'O', 'O', 'O', 'X'},
            {'X', 'X', 'X', 'X', 'X'}
        });

        // TC 16: 5x5 hollow square of O in center -> flipped
        testCases.add(new char[][]{
            {'X', 'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'O', 'X'},
            {'X', 'O', 'X', 'O', 'X'},
            {'X', 'O', 'O', 'O', 'X'},
            {'X', 'X', 'X', 'X', 'X'}
        });

        // TC 17: 5x5 cross of O touching borders -> none flipped
        testCases.add(new char[][]{
            {'X', 'X', 'O', 'X', 'X'},
            {'X', 'X', 'O', 'X', 'X'},
            {'O', 'O', 'O', 'O', 'O'},
            {'X', 'X', 'O', 'X', 'X'},
            {'X', 'X', 'O', 'X', 'X'}
        });

        // TC 18: 6x6 two distinct enclosed O islands
        testCases.add(new char[][]{
            {'X', 'X', 'X', 'X', 'X', 'X'},
            {'X', 'O', 'O', 'X', 'X', 'X'},
            {'X', 'X', 'X', 'X', 'O', 'X'},
            {'X', 'X', 'X', 'O', 'O', 'X'},
            {'X', 'X', 'X', 'X', 'X', 'X'},
            {'X', 'X', 'X', 'X', 'X', 'X'}
        });

        // TC 19: 7x7 concentric: outer O, ring X, inner 3x3 O (inner flips!)
        testCases.add(makeConcentricRings(7, 7, 'O', 'X'));

        // TC 20: 7x7 concentric: outer X, ring O (flips!), inner X
        testCases.add(makeConcentricRings(7, 7, 'X', 'O'));

        // TC 21: 7x7 spiral of O from boundary to center -> stays O
        testCases.add(makeSpiralFromBorder(7, 7));

        // TC 22: 8x8 checkerboard with border X -> all interior O flip
        testCases.add(makeCheckerboardWithMoat(8, 8));

        // TC 23: 8x8 stripes of O with border X
        testCases.add(makeStripesWithMoat(8, 8, true));

        // TC 24: 8x8 diagonal stripes of O with border X
        testCases.add(makeDiagonalWithMoat(8, 8));

        // ==========================================
        // 25 - 44: Medium Grids (10x10 to 50x50)
        // ==========================================
        // TC 25: 10x10 bridge connecting interior O to boundary
        testCases.add(makeBridgeToBorder(10, 10));

        // TC 26: 10x10 same bridge broken with X (all flips!)
        testCases.add(makeBrokenBridge(10, 10));

        // TC 27: 12x12 snake of O enclosed in X border
        testCases.add(makeSnakeInsideMoat(12, 12));

        // TC 28: 15x15 snake of O connected to boundary
        testCases.add(makeSnakeFromBorder(15, 15));

        // TC 29: 16x16 concentric rings
        testCases.add(makeConcentricRings(16, 16, 'X', 'O'));

        // TC 30: 20x20 random sparse O with border X
        testCases.add(makeRandomWithBorderX(20, 20, 0.20, rng));

        // TC 31: 20x20 random dense O with border X
        testCases.add(makeRandomWithBorderX(20, 20, 0.70, rng));

        // TC 32: 25x25 cellular automata cave of O enclosed
        testCases.add(makeCaveWithBorderX(25, 25, rng));

        // TC 33: 30x30 checkerboard with border X
        testCases.add(makeCheckerboardWithMoat(30, 30));

        // TC 34: 30x30 multiple rectangular blocks of O
        testCases.add(makeBlockRegions(30, 30, 3, 3));

        // TC 35: 35x35 random with border X (prob 0.45)
        testCases.add(makeRandomWithBorderX(35, 35, 0.45, rng));

        // TC 36: 40x20 asymmetric grid
        testCases.add(makeRandomWithBorderX(40, 20, 0.35, rng));

        // TC 37: 20x40 asymmetric grid
        testCases.add(makeRandomWithBorderX(20, 40, 0.50, rng));

        // TC 38: 45x45 labyrinth with trapped pockets of O
        testCases.add(makeLabyrinth(45, 45, rng));

        // TC 39: 50x50 snake inside moat
        testCases.add(makeSnakeInsideMoat(50, 50));

        // TC 40: 50x50 snake from border
        testCases.add(makeSnakeFromBorder(50, 50));

        // TC 41: 50x50 checkerboard with border X
        testCases.add(makeCheckerboardWithMoat(50, 50));

        // TC 42: 50x50 cave with border X
        testCases.add(makeCaveWithBorderX(50, 50, rng));

        // TC 43: 50x50 random with boundary intrusions of O
        testCases.add(makeComplexGrid(50, 50, rng));

        // TC 44: 50x50 concentric rings
        testCases.add(makeConcentricRings(50, 50, 'X', 'O'));

        // ==========================================
        // 45 - 69: Large Grids (60x60 to 150x150)
        // ==========================================
        for (int i = 45; i <= 69; i++) {
            int dimR = 60 + (i - 45) * 4;
            int dimC = 60 + ((i * 7) % 40);

            if (i % 6 == 0) {
                testCases.add(makeCheckerboardWithMoat(dimR, dimC));
            } else if (i % 6 == 1) {
                testCases.add(makeConcentricRings(dimR, dimC, 'X', 'O'));
            } else if (i % 6 == 2) {
                testCases.add(makeSnakeInsideMoat(dimR, dimC));
            } else if (i % 6 == 3) {
                testCases.add(makeCaveWithBorderX(dimR, dimC, rng));
            } else if (i % 6 == 4) {
                testCases.add(makeComplexGrid(dimR, dimC, rng));
            } else {
                testCases.add(makeLabyrinth(dimR, dimC, rng));
            }
        }

        // ==========================================
        // 70 - 89: Very Large Grids (160x160 to 250x250)
        // ==========================================
        for (int i = 70; i <= 89; i++) {
            int dimR = 160 + (i - 70) * 5;
            int dimC = 160 + ((i * 11) % 80);

            if (i % 5 == 0) {
                testCases.add(makeCheckerboardWithMoat(dimR, dimC));
            } else if (i % 5 == 1) {
                testCases.add(makeConcentricRings(dimR, dimC, 'X', 'O'));
            } else if (i % 5 == 2) {
                testCases.add(makeSnakeInsideMoat(dimR, dimC));
            } else if (i % 5 == 3) {
                testCases.add(makeComplexGrid(dimR, dimC, rng));
            } else {
                testCases.add(makeCaveWithBorderX(dimR, dimC, rng));
            }
        }

        // ==========================================
        // 90 - 99: Extreme Constraints (Up to 300x300)
        // ==========================================
        // TC 90: 300x300 all X
        testCases.add(makeFilledGrid(300, 300, 'X'));

        // TC 91: 300x300 all O (none flipped!)
        testCases.add(makeFilledGrid(300, 300, 'O'));

        // TC 92: 300x300 border X, interior all O (all 298*298 flipped to X)
        testCases.add(makeInteriorAllO(300, 300));

        // TC 93: 300x300 checkerboard with border X
        testCases.add(makeCheckerboardWithMoat(300, 300));

        // TC 94: 300x300 concentric rings
        testCases.add(makeConcentricRings(300, 300, 'X', 'O'));

        // TC 95: 300x300 massive serpentine snake of O enclosed in X
        testCases.add(makeSnakeInsideMoat(300, 300));

        // TC 96: 300x300 massive serpentine snake of O connected to border
        testCases.add(makeSnakeFromBorder(300, 300));

        // TC 97: 300x300 random sparse with border X (prob 0.15)
        testCases.add(makeRandomWithBorderX(300, 300, 0.15, rng));

        // TC 98: 300x300 random dense with border X (prob 0.70)
        testCases.add(makeRandomWithBorderX(300, 300, 0.70, rng));

        // TC 99: 300x300 complex grid with boundary intrusions and enclosed lakes
        testCases.add(makeComplexGrid(300, 300, rng));

        // ==========================================
        // Generate Files
        // ==========================================
        System.out.println("Total test cases queued: " + testCases.size());

        for (int i = 0; i < testCases.size(); i++) {
            char[][] mat = testCases.get(i);
            int N = mat.length;
            int M = mat[0].length;

            String indexStr = String.format("%02d", i);
            File inFile = new File(inputDir, "input" + indexStr + ".txt");
            File outFile = new File(outputDir, "output" + indexStr + ".txt");

            try (PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)))) {
                inWriter.println(N + " " + M);
                for (int r = 0; r < N; r++) {
                    StringBuilder sb = new StringBuilder();
                    for (int c = 0; c < M; c++) {
                        if (c > 0) sb.append(' ');
                        sb.append(mat[r][c]);
                    }
                    inWriter.println(sb.toString());
                }
            }

            char[][] ans = fill(mat);

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

    public static char[][] fill(char[][] inputMat) {
        if (inputMat == null || inputMat.length == 0 || inputMat[0].length == 0) return inputMat;

        int N = inputMat.length;
        int M = inputMat[0].length;
        int totalCells = N * M;

        char[][] mat = new char[N][M];
        for (int r = 0; r < N; r++) {
            System.arraycopy(inputMat[r], 0, mat[r], 0, M);
        }

        int[] queueR = new int[totalCells];
        int[] queueC = new int[totalCells];
        int head = 0, tail = 0;

        for (int r = 0; r < N; r++) {
            if (mat[r][0] == 'O') {
                mat[r][0] = '#';
                queueR[tail] = r; queueC[tail] = 0; tail++;
            }
            if (M > 1 && mat[r][M - 1] == 'O') {
                mat[r][M - 1] = '#';
                queueR[tail] = r; queueC[tail] = M - 1; tail++;
            }
        }

        for (int c = 0; c < M; c++) {
            if (mat[0][c] == 'O') {
                mat[0][c] = '#';
                queueR[tail] = 0; queueC[tail] = c; tail++;
            }
            if (N > 1 && mat[N - 1][c] == 'O') {
                mat[N - 1][c] = '#';
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
                    if (mat[nr][nc] == 'O') {
                        mat[nr][nc] = '#';
                        queueR[tail] = nr;
                        queueC[tail] = nc;
                        tail++;
                    }
                }
            }
        }

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (mat[r][c] == 'O') {
                    mat[r][c] = 'X';
                } else if (mat[r][c] == '#') {
                    mat[r][c] = 'O';
                }
            }
        }

        return mat;
    }

    // ==========================================
    // Grid Construction Helpers
    // ==========================================
    static char[][] makeFilledGrid(int N, int M, char fill) {
        char[][] g = new char[N][M];
        for (int r = 0; r < N; r++) {
            Arrays.fill(g[r], fill);
        }
        return g;
    }

    static char[][] makeInteriorAllO(int N, int M) {
        char[][] g = makeFilledGrid(N, M, 'X');
        for (int r = 1; r < N - 1; r++) {
            for (int c = 1; c < M - 1; c++) {
                g[r][c] = 'O';
            }
        }
        return g;
    }

    static char[][] makeCheckerboardWithMoat(int N, int M) {
        char[][] g = makeFilledGrid(N, M, 'X');
        for (int r = 1; r < N - 1; r++) {
            for (int c = 1; c < M - 1; c++) {
                g[r][c] = ((r + c) % 2 == 0) ? 'O' : 'X';
            }
        }
        return g;
    }

    static char[][] makeConcentricRings(int N, int M, char outer, char inner) {
        char[][] g = makeFilledGrid(N, M, outer);
        int layers = Math.min(N, M) / 2;
        for (int l = 1; l < layers; l += 2) {
            for (int c = l; c < M - l; c++) {
                g[l][c] = inner;
                g[N - 1 - l][c] = inner;
            }
            for (int r = l; r < N - l; r++) {
                g[r][l] = inner;
                g[r][M - 1 - l] = inner;
            }
        }
        return g;
    }

    static char[][] makeSpiralFromBorder(int N, int M) {
        char[][] g = makeFilledGrid(N, M, 'X');
        int top = 0, bottom = N - 1, left = 0, right = M - 1;
        while (top <= bottom && left <= right) {
            for (int c = left; c <= right; c++) g[top][c] = 'O';
            top += 2;
            for (int r = top - 1; r <= bottom; r++) g[r][right] = 'O';
            right -= 2;
            if (top <= bottom) {
                for (int c = right + 1; c >= left; c--) g[bottom][c] = 'O';
                bottom -= 2;
            }
            if (left <= right) {
                for (int r = bottom + 1; r >= top; r--) g[r][left] = 'O';
                left += 2;
            }
        }
        return g;
    }

    static char[][] makeStripesWithMoat(int N, int M, boolean horizontal) {
        char[][] g = makeFilledGrid(N, M, 'X');
        if (horizontal) {
            for (int r = 2; r < N - 2; r += 2) {
                for (int c = 2; c < M - 2; c++) g[r][c] = 'O';
            }
        } else {
            for (int c = 2; c < M - 2; c += 2) {
                for (int r = 2; r < N - 2; r++) g[r][c] = 'O';
            }
        }
        return g;
    }

    static char[][] makeDiagonalWithMoat(int N, int M) {
        char[][] g = makeFilledGrid(N, M, 'X');
        for (int r = 2; r < N - 2; r++) {
            for (int c = 2; c < M - 2; c++) {
                if ((r + c) % 3 == 0) g[r][c] = 'O';
            }
        }
        return g;
    }

    static char[][] makeBlockRegions(int N, int M, int blockR, int blockC) {
        char[][] g = makeFilledGrid(N, M, 'X');
        int stepR = blockR + 2;
        int stepC = blockC + 2;
        for (int r = 2; r + blockR < N - 1; r += stepR) {
            for (int c = 2; c + blockC < M - 1; c += stepC) {
                for (int dr = 0; dr < blockR; dr++) {
                    for (int dc = 0; dc < blockC; dc++) {
                        g[r + dr][c + dc] = 'O';
                    }
                }
            }
        }
        return g;
    }

    static char[][] makeBridgeToBorder(int N, int M) {
        char[][] g = makeFilledGrid(N, M, 'X');
        int midR = N / 2;
        int midC = M / 2;
        for (int r = midR - 2; r <= midR + 2; r++) {
            for (int c = midC - 2; c <= midC + 2; c++) {
                g[r][c] = 'O';
            }
        }
        for (int c = 0; c < midC; c++) {
            g[midR][c] = 'O';
        }
        return g;
    }

    static char[][] makeBrokenBridge(int N, int M) {
        char[][] g = makeBridgeToBorder(N, M);
        g[N / 2][1] = 'X';
        return g;
    }

    static char[][] makeSnakeInsideMoat(int N, int M) {
        char[][] g = makeFilledGrid(N, M, 'X');
        int startR = 2, endR = N - 3;
        int startC = 2, endC = M - 3;
        int count = 0;
        for (int r = startR; r <= endR; r += 2) {
            for (int c = startC; c <= endC; c++) {
                g[r][c] = 'O';
            }
            if (r + 1 <= endR) {
                int connectCol = (count % 2 == 0) ? endC : startC;
                g[r + 1][connectCol] = 'O';
                count++;
            }
        }
        return g;
    }

    static char[][] makeSnakeFromBorder(int N, int M) {
        char[][] g = makeSnakeInsideMoat(N, M);
        g[2][0] = 'O';
        g[2][1] = 'O';
        return g;
    }

    static char[][] makeRandomWithBorderX(int N, int M, double probO, Random rng) {
        char[][] g = makeFilledGrid(N, M, 'X');
        for (int r = 1; r < N - 1; r++) {
            for (int c = 1; c < M - 1; c++) {
                if (rng.nextDouble() < probO) {
                    g[r][c] = 'O';
                }
            }
        }
        return g;
    }

    static char[][] makeCaveWithBorderX(int N, int M, Random rng) {
        char[][] g = makeFilledGrid(N, M, 'X');
        for (int r = 1; r < N - 1; r++) {
            for (int c = 1; c < M - 1; c++) {
                g[r][c] = (rng.nextDouble() < 0.45) ? 'O' : 'X';
            }
        }
        for (int step = 0; step < 2; step++) {
            char[][] next = makeFilledGrid(N, M, 'X');
            for (int r = 1; r < N - 1; r++) {
                for (int c = 1; c < M - 1; c++) {
                    int count = 0;
                    for (int dr = -1; dr <= 1; dr++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            if (g[r + dr][c + dc] == 'O') count++;
                        }
                    }
                    next[r][c] = (count >= 5) ? 'O' : 'X';
                }
            }
            g = next;
        }
        return g;
    }

    static char[][] makeLabyrinth(int N, int M, Random rng) {
        char[][] g = makeFilledGrid(N, M, 'X');
        for (int r = 2; r < N - 2; r++) {
            for (int c = 2; c < M - 2; c++) {
                if ((r % 2 == 0) || (c % 2 == 0)) {
                    if (rng.nextDouble() < 0.6) {
                        g[r][c] = 'O';
                    }
                }
            }
        }
        return g;
    }

    static char[][] makeComplexGrid(int N, int M, Random rng) {
        char[][] g = makeRandomWithBorderX(N, M, 0.40, rng);
        // Add multiple enclosed dense O regions
        for (int p = 0; p < 6; p++) {
            int topR = 10 + rng.nextInt(Math.max(1, N - 50));
            int topC = 10 + rng.nextInt(Math.max(1, M - 50));
            int h = 10 + rng.nextInt(20);
            int w = 10 + rng.nextInt(20);
            for (int r = topR; r < Math.min(N - 1, topR + h); r++) {
                for (int c = topC; c < Math.min(M - 1, topC + w); c++) {
                    g[r][c] = 'O';
                }
            }
        }
        // Add boundary intrusions (O fingers extending from border that remain O)
        for (int k = 0; k < 4; k++) {
            int rStart = rng.nextInt(N);
            for (int c = 0; c < Math.min(M, 20 + rng.nextInt(20)); c++) {
                g[rStart][c] = 'O';
            }
        }
        return g;
    }
}
