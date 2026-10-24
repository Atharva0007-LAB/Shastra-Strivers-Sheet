import java.io.*;
import java.util.*;

public class gen {

    public static void main(String[] args) throws IOException {
        File baseDir = new File("d:/MF Shastra/shastra striverz/Flood Fill Algorithm/Testcases");
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
        Random rng = new Random(531);

        // ==========================================
        // 00 - 02: Platform Samples
        // ==========================================
        // Sample 1: 3x3, sr=1, sc=1, newColor=2
        testCases.add(new TestCase(new int[][]{
            {1, 1, 1},
            {1, 1, 0},
            {1, 0, 1}
        }, 1, 1, 2));

        // Sample 2: 3x3, sr=2, sc=2, newColor=3
        testCases.add(new TestCase(new int[][]{
            {0, 1, 0},
            {1, 1, 0},
            {0, 0, 1}
        }, 2, 2, 3));

        // Sample 3: 3x3, sr=1, sc=1, newColor=0
        testCases.add(new TestCase(new int[][]{
            {1, 1, 1},
            {1, 1, 0},
            {1, 0, 1}
        }, 1, 1, 0));

        // ==========================================
        // 03 - 07: Critical Edge Cases
        // ==========================================
        // TC 03: 1x1, newColor != originalColor
        testCases.add(new TestCase(new int[][]{{1}}, 0, 0, 5));

        // TC 04: 1x1, newColor == originalColor (no-op)
        testCases.add(new TestCase(new int[][]{{7}}, 0, 0, 7));

        // TC 05: 3x3, newColor == originalColor (infinite loop trap test)
        testCases.add(new TestCase(new int[][]{
            {2, 2, 2},
            {2, 2, 0},
            {2, 0, 1}
        }, 1, 1, 2));

        // TC 06: 1x10 row vector
        testCases.add(new TestCase(new int[][]{
            {1, 1, 1, 1, 2, 1, 1, 1, 1, 1}
        }, 0, 0, 9));

        // TC 07: 10x1 column vector
        testCases.add(new TestCase(new int[][]{
            {1}, {1}, {1}, {0}, {1}, {1}, {1}, {1}, {1}, {1}
        }, 0, 0, 4));

        // ==========================================
        // 08 - 25: Small Canonical Images (N, M <= 15)
        // ==========================================
        testCases.add(new TestCase(makeCheckerboard(5, 5), 2, 2, 9)); // 4-dir only recolors (2,2)
        testCases.add(new TestCase(makeDonut(7, 7, 1, 0), 0, 0, 3));  // frame recolored, center untouched
        testCases.add(new TestCase(makeDonut(7, 7, 1, 0), 3, 3, 4));  // center hole recolored
        testCases.add(new TestCase(makeCross(9, 9, 1, 0), 4, 4, 8));  // '+' cross
        testCases.add(new TestCase(makeFilled(6, 6, 42), 0, 0, 99));  // whole grid recolored
        testCases.add(new TestCase(makeFilled(6, 6, 50), 3, 3, 50));  // guard check
        testCases.add(new TestCase(makeStripes(8, 8, 2), 0, 0, 7));
        testCases.add(new TestCase(makeSpiral(9, 9), 0, 0, 5));
        testCases.add(new TestCase(makeRandom(10, 10, 3, rng), 0, 0, 8));
        testCases.add(new TestCase(makeRandom(10, 10, 3, rng), 9, 9, 2));
        testCases.add(new TestCase(makeRandom(12, 12, 4, rng), 6, 6, 1));
        testCases.add(new TestCase(makeRandom(15, 15, 2, rng), 0, 14, 3));
        testCases.add(new TestCase(makeFilled(12, 12, 65000), 0, 0, 65535)); // large color values
        testCases.add(new TestCase(makeCheckerboard(10, 10), 0, 0, 1));
        testCases.add(new TestCase(makeDonut(15, 15, 5, 2), 7, 7, 5));
        testCases.add(new TestCase(makeSpiral(15, 15), 0, 0, 2));
        testCases.add(new TestCase(makeRandom(15, 15, 5, rng), 7, 7, 9));
        testCases.add(new TestCase(makeRandom(15, 15, 1, rng), 0, 0, 2));

        // ==========================================
        // 26 - 55: Medium Grids (N, M = 20 to 35)
        // ==========================================
        for (int i = 26; i <= 55; i++) {
            int n = 20 + (i - 26) / 2;
            int m = 20 + (i - 26) / 2;
            int numColors = 2 + (i % 5);
            int[][] img = makeRandom(n, m, numColors, rng);
            int sr = rng.nextInt(n);
            int sc = rng.nextInt(m);
            int newColor = (i % 7 == 0) ? img[sr][sc] : rng.nextInt(numColors + 5);
            testCases.add(new TestCase(img, sr, sc, newColor));
        }

        // ==========================================
        // 56 - 80: Larger Topologies (N, M = 36 to 48)
        // ==========================================
        for (int i = 56; i <= 80; i++) {
            int n = 36 + (i - 56) / 2;
            int m = 36 + (i - 56) / 2;
            if (i % 4 == 0) {
                testCases.add(new TestCase(makeFilled(n, m, 100), 0, 0, 200));
            } else if (i % 4 == 1) {
                testCases.add(new TestCase(makeSpiral(n, m), 0, 0, 9));
            } else if (i % 4 == 2) {
                testCases.add(new TestCase(makeStripes(n, m, 3), 0, 0, 15));
            } else {
                testCases.add(new TestCase(makeRandom(n, m, 4, rng), n / 2, m / 2, 7));
            }
        }

        // ==========================================
        // 81 - 99: Max Constraints (N = 50, M = 50)
        // ==========================================
        // TC 81: 50x50 all 1s -> whole grid recolored to 2
        testCases.add(new TestCase(makeFilled(50, 50, 1), 0, 0, 2));

        // TC 82: 50x50 all 1s with newColor == 1 (instant no-op)
        testCases.add(new TestCase(makeFilled(50, 50, 1), 25, 25, 1));

        // TC 83: 50x50 checkerboard (4-direction connectivity test)
        testCases.add(new TestCase(makeCheckerboard(50, 50), 25, 25, 7));

        // TC 84: 50x50 spiral snake path (deep recursion / queue test)
        testCases.add(new TestCase(makeSpiral(50, 50), 0, 0, 3));

        // TC 85: 50x50 binary random (lots of connected components)
        testCases.add(new TestCase(makeRandom(50, 50, 2, rng), 0, 0, 5));

        // TC 86: 50x50 10-color random
        testCases.add(new TestCase(makeRandom(50, 50, 10, rng), 25, 25, 99));

        // TC 87: 50x50 concentric rings
        testCases.add(new TestCase(makeConcentric(50, 50), 0, 0, 8));

        // TC 88: 50x50 horizontal stripes
        testCases.add(new TestCase(makeStripes(50, 50, 2), 0, 0, 4));

        // TC 89: 50x50 large color values near 65535
        int[][] maxColorGrid = makeFilled(50, 50, 65530);
        maxColorGrid[25][25] = 65534;
        testCases.add(new TestCase(maxColorGrid, 0, 0, 65535));

        // TC 90: 1x50 row
        testCases.add(new TestCase(makeRandom(1, 50, 2, rng), 0, 25, 3));

        // TC 91: 50x1 column
        testCases.add(new TestCase(makeRandom(50, 1, 2, rng), 25, 0, 3));

        // TC 92-99: 50x50 varied patterns
        for (int i = 92; i <= 99; i++) {
            int[][] g = makeRandom(50, 50, 3 + (i % 4), rng);
            int sr = rng.nextInt(50);
            int sc = rng.nextInt(50);
            int newColor = rng.nextInt(20);
            testCases.add(new TestCase(g, sr, sc, newColor));
        }

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
            int N = tc.image.length;
            int M = tc.image[0].length;

            // Deep copy of image for input writing
            int[][] originalCopy = new int[N][M];
            for (int r = 0; r < N; r++) {
                originalCopy[r] = tc.image[r].clone();
            }

            // Write input
            try (PrintWriter inWriter = new PrintWriter(new BufferedWriter(new FileWriter(inFile)))) {
                inWriter.println(N + " " + M);
                for (int r = 0; r < N; r++) {
                    StringBuilder sb = new StringBuilder();
                    for (int c = 0; c < M; c++) {
                        if (c > 0) sb.append(' ');
                        sb.append(originalCopy[r][c]);
                    }
                    inWriter.println(sb.toString());
                }
                inWriter.println(tc.sr + " " + tc.sc + " " + tc.newColor);
            }

            // Solve
            int[][] result = floodFill(tc.image, tc.sr, tc.sc, tc.newColor);

            try (PrintWriter outWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)))) {
                for (int r = 0; r < N; r++) {
                    StringBuilder sb = new StringBuilder();
                    for (int c = 0; c < M; c++) {
                        if (c > 0) sb.append(' ');
                        sb.append(result[r][c]);
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

    public static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (image == null || image.length == 0 || image[0].length == 0) return image;

        int originalColor = image[sr][sc];
        if (originalColor == newColor) return image;

        int N = image.length;
        int M = image[0].length;

        int total = N * M;
        int[] queueR = new int[total];
        int[] queueC = new int[total];
        int head = 0, tail = 0;

        image[sr][sc] = newColor;
        queueR[tail] = sr;
        queueC[tail] = sc;
        tail++;

        while (head < tail) {
            int r = queueR[head];
            int c = queueC[head];
            head++;

            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k];
                int nc = c + dc[k];

                if (nr >= 0 && nr < N && nc >= 0 && nc < M && image[nr][nc] == originalColor) {
                    image[nr][nc] = newColor;
                    queueR[tail] = nr;
                    queueC[tail] = nc;
                    tail++;
                }
            }
        }

        return image;
    }

    // ==========================================
    // Helpers
    // ==========================================
    static class TestCase {
        int[][] image;
        int sr, sc, newColor;

        TestCase(int[][] image, int sr, int sc, int newColor) {
            this.image = image;
            this.sr = sr;
            this.sc = sc;
            this.newColor = newColor;
        }
    }

    static int[][] makeFilled(int N, int M, int val) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) Arrays.fill(g[r], val);
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

    static int[][] makeDonut(int N, int M, int frameColor, int holeColor) {
        int[][] g = makeFilled(N, M, frameColor);
        for (int r = 2; r < N - 2; r++) {
            for (int c = 2; c < M - 2; c++) {
                g[r][c] = holeColor;
            }
        }
        return g;
    }

    static int[][] makeCross(int N, int M, int crossColor, int bg) {
        int[][] g = makeFilled(N, M, bg);
        int midR = N / 2;
        int midC = M / 2;
        for (int c = 0; c < M; c++) g[midR][c] = crossColor;
        for (int r = 0; r < N; r++) g[r][midC] = crossColor;
        return g;
    }

    static int[][] makeStripes(int N, int M, int numColors) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            Arrays.fill(g[r], r % numColors);
        }
        return g;
    }

    static int[][] makeConcentric(int N, int M) {
        int[][] g = new int[N][M];
        int layers = Math.min(N, M) / 2;
        for (int l = 0; l < layers; l++) {
            for (int c = l; c < M - l; c++) {
                g[l][c] = l % 2;
                g[N - 1 - l][c] = l % 2;
            }
            for (int r = l; r < N - l; r++) {
                g[r][l] = l % 2;
                g[r][M - 1 - l] = l % 2;
            }
        }
        return g;
    }

    static int[][] makeSpiral(int N, int M) {
        int[][] g = makeFilled(N, M, 0);
        for (int r = 0; r < N; r += 2) {
            for (int c = 0; c < M; c++) g[r][c] = 1;
            if (r + 1 < N) {
                int col = ((r / 2) % 2 == 0) ? (M - 1) : 0;
                g[r + 1][col] = 1;
            }
        }
        return g;
    }

    static int[][] makeRandom(int N, int M, int colors, Random rng) {
        int[][] g = new int[N][M];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                g[r][c] = rng.nextInt(colors);
            }
        }
        return g;
    }
}
