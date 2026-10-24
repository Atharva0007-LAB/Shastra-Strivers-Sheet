import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    static class Node implements Comparable<Node> {
        int effort;
        int r, c;

        Node(int effort, int r, int c) {
            this.effort = effort;
            this.r = r;
            this.c = c;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.effort, o.effort);
        }
    }

    private static int solve(int rows, int cols, int[][] heights) {
        if (rows == 1 && cols == 1) return 0;

        int INF = Integer.MAX_VALUE / 2;
        int[][] effort = new int[rows][cols];
        for (int r = 0; r < rows; r++) Arrays.fill(effort[r], INF);

        PriorityQueue<Node> pq = new PriorityQueue<>();
        effort[0][0] = 0;
        pq.add(new Node(0, 0, 0));

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int e = curr.effort;
            int r = curr.r;
            int c = curr.c;

            if (e > effort[r][c]) continue;
            if (r == rows - 1 && c == cols - 1) return e;

            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k];
                int nc = c + dc[k];

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                    int diff = Math.abs(heights[r][c] - heights[nr][nc]);
                    int nextEffort = Math.max(e, diff);

                    if (nextEffort < effort[nr][nc]) {
                        effort[nr][nc] = nextEffort;
                        pq.add(new Node(nextEffort, nr, nc));
                    }
                }
            }
        }
        return effort[rows - 1][cols - 1];
    }

    private static void writeTestCase(int index, int rows, int cols, int[][] heights) throws Exception {
        String baseDir = new File(System.getProperty("user.dir")).getParent();
        File inputDir = new File(baseDir, "Testcases" + File.separator + "Input");
        File outputDir = new File(baseDir, "Testcases" + File.separator + "Output");
        inputDir.mkdirs();
        outputDir.mkdirs();

        String inFileName = String.format("input%02d.txt", index);
        String outFileName = String.format("output%02d.txt", index);

        File inFile = new File(inputDir, inFileName);
        File outFile = new File(outputDir, outFileName);

        try (PrintWriter pw = new PrintWriter(inFile)) {
            pw.println(rows + " " + cols);
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    pw.print(heights[r][c]);
                    if (c < cols - 1) pw.print(" ");
                }
                pw.println();
            }
        }

        int ans = solve(rows, cols, heights);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5252026L);
        int tc = 0;

        // Sample 1: 3x3
        {
            int[][] heights = {
                {1, 2, 2},
                {3, 8, 2},
                {5, 3, 5}
            };
            writeTestCase(tc++, 3, 3, heights);
        }

        // Sample 2: 3x3
        {
            int[][] heights = {
                {1, 2, 3},
                {3, 8, 4},
                {5, 3, 5}
            };
            writeTestCase(tc++, 3, 3, heights);
        }

        // Sample 3: 5x5
        {
            int[][] heights = {
                {1, 2, 1, 1, 1},
                {1, 2, 1, 2, 1},
                {1, 2, 1, 2, 1},
                {1, 2, 1, 2, 1},
                {1, 1, 1, 2, 1}
            };
            writeTestCase(tc++, 5, 5, heights);
        }

        // TC 03: 1x1
        {
            int[][] heights = {{42}};
            writeTestCase(tc++, 1, 1, heights);
        }

        // TC 04: 1x2 diff 9
        {
            int[][] heights = {{1, 10}};
            writeTestCase(tc++, 1, 2, heights);
        }

        // TC 05: 1x2 max diff 1 to 10^6
        {
            int[][] heights = {{1, 1000000}};
            writeTestCase(tc++, 1, 2, heights);
        }

        // TC 06: 2x1 identical heights
        {
            int[][] heights = {{500}, {500}};
            writeTestCase(tc++, 2, 1, heights);
        }

        // TC 07: 2x2 with choice
        {
            int[][] heights = {
                {1, 10},
                {3, 4}
            };
            writeTestCase(tc++, 2, 2, heights);
        }

        // TC 08: 2x2 all identical
        {
            int[][] heights = {
                {7, 7},
                {7, 7}
            };
            writeTestCase(tc++, 2, 2, heights);
        }

        // TC 09: 1x10 monotonic increasing
        {
            int[][] heights = new int[1][10];
            for (int c = 0; c < 10; c++) heights[0][c] = c + 1;
            writeTestCase(tc++, 1, 10, heights);
        }

        // TC 10: 10x1 alternating jumps
        {
            int[][] heights = new int[10][1];
            for (int r = 0; r < 10; r++) heights[r][0] = (r % 2 == 0) ? 10 : 50;
            writeTestCase(tc++, 10, 1, heights);
        }

        // TC 11: 3x3 detour vs jump
        {
            int[][] heights = {
                {1, 1000, 1000},
                {2, 1000, 1000},
                {3, 4, 5}
            };
            writeTestCase(tc++, 3, 3, heights);
        }

        // TC 12: 5x5 chess pattern
        {
            int n = 5;
            int[][] heights = new int[n][n];
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    heights[r][c] = ((r + c) % 2 == 0) ? 10 : 25;
                }
            }
            writeTestCase(tc++, n, n, heights);
        }

        // TC 13: 5x5 high elevation plateau
        {
            int n = 5;
            int[][] heights = new int[n][n];
            for (int r = 0; r < n; r++) Arrays.fill(heights[r], 5000);
            heights[0][0] = 100;
            heights[n - 1][n - 1] = 100;
            writeTestCase(tc++, n, n, heights);
        }

        // TC 14: 6x6 max values 10^6
        {
            int n = 6;
            int[][] heights = new int[n][n];
            for (int r = 0; r < n; r++) Arrays.fill(heights[r], 1000000);
            writeTestCase(tc++, n, n, heights);
        }

        // TC 15 - 20: Smooth staircases & slopes
        for (int k = 0; k < 6; k++) {
            int R = 8 + k * 2;
            int C = 8 + k * 2;
            int step = 2 + k * 3;
            int[][] heights = new int[R][C];
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    heights[r][c] = (r + c) * step + 1;
                }
            }
            writeTestCase(tc++, R, C, heights);
        }

        // TC 21 - 28: Serpentine winding paths of low effort in sea of mountains
        for (int k = 0; k < 8; k++) {
            int R = 15, C = 15;
            int[][] heights = new int[R][C];
            for (int r = 0; r < R; r++) Arrays.fill(heights[r], 999999);
            // carve snake
            for (int r = 0; r < R; r++) {
                if (r % 2 == 0) {
                    for (int c = 0; c < C; c++) heights[r][c] = 5 + k;
                } else {
                    int turnC = (r % 4 == 1) ? C - 1 : 0;
                    heights[r][turnC] = 5 + k;
                }
            }
            writeTestCase(tc++, R, C, heights);
        }

        // TC 29 - 36: Concentric elevation walls with single openings
        for (int k = 0; k < 8; k++) {
            int n = 21;
            int[][] heights = new int[n][n];
            for (int r = 0; r < n; r++) Arrays.fill(heights[r], 10);
            // build concentric ring walls
            for (int ring = 3; ring < n / 2; ring += 3) {
                int wallH = 10000 * ring;
                for (int c = ring; c < n - ring; c++) {
                    heights[ring][c] = wallH;
                    heights[n - 1 - ring][c] = wallH;
                }
                for (int r = ring; r < n - ring; r++) {
                    heights[r][ring] = wallH;
                    heights[r][n - 1 - ring] = wallH;
                }
                // leave a gate opening
                heights[ring][n / 2] = 10;
            }
            writeTestCase(tc++, n, n, heights);
        }

        // TC 37 - 44: Spiral elevation mazes
        for (int k = 0; k < 8; k++) {
            int n = 20 + k;
            int[][] heights = new int[n][n];
            for (int r = 0; r < n; r++) Arrays.fill(heights[r], 500000);
            int top = 0, bottom = n - 1, left = 0, right = n - 1;
            int curH = 10;
            while (top <= bottom && left <= right) {
                for (int c = left; c <= right; c++) heights[top][c] = curH;
                top++;
                for (int r = top; r <= bottom; r++) heights[r][right] = curH;
                right--;
                if (top <= bottom) {
                    for (int c = right; c >= left; c--) heights[bottom][c] = curH;
                    bottom--;
                }
                if (left <= right) {
                    for (int r = bottom; r >= top; r--) heights[r][left] = curH;
                    left++;
                }
                curH += 2;
            }
            writeTestCase(tc++, n, n, heights);
        }

        // TC 45 - 60: Medium scale random grids with various distributions
        for (int k = 0; k < 16; k++) {
            int R = 30 + k * 2;
            int C = 35 + k * 2;
            int[][] heights = new int[R][C];
            int maxH = (k % 4 == 0) ? 50 : ((k % 4 == 1) ? 5000 : 1000000);
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    heights[r][c] = rng.nextInt(maxH) + 1;
                }
            }
            writeTestCase(tc++, R, C, heights);
        }

        // TC 61 - 74: Random grids with guaranteed smooth paths
        for (int k = 0; k < 14; k++) {
            int R = 50 + k * 2;
            int C = 50 + k * 2;
            int[][] heights = new int[R][C];
            // random high noise
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    heights[r][c] = rng.nextInt(900000) + 100000;
                }
            }
            // carve a random smooth walk with small step variance
            int cr = 0, cc = 0;
            int curVal = 100;
            heights[cr][cc] = curVal;
            while (cr < R - 1 || cc < C - 1) {
                if (cr == R - 1) cc++;
                else if (cc == C - 1) cr++;
                else if (rng.nextBoolean()) cr++;
                else cc++;
                curVal += rng.nextInt(5) - 2;
                if (curVal < 1) curVal = 1;
                heights[cr][cc] = curVal;
            }
            writeTestCase(tc++, R, C, heights);
        }

        // TC 75 - 84: Extreme aspect ratio strips
        // TC 75: 1 x 100 smooth
        {
            int[][] heights = new int[1][100];
            for (int c = 0; c < 100; c++) heights[0][c] = c * 3 + 1;
            writeTestCase(tc++, 1, 100, heights);
        }
        // TC 76: 100 x 1 smooth
        {
            int[][] heights = new int[100][1];
            for (int r = 0; r < 100; r++) heights[r][0] = r * 5 + 1;
            writeTestCase(tc++, 100, 1, heights);
        }
        // TC 77: 1 x 100 random huge spikes
        {
            int[][] heights = new int[1][100];
            for (int c = 0; c < 100; c++) heights[0][c] = rng.nextInt(1000000) + 1;
            writeTestCase(tc++, 1, 100, heights);
        }
        // TC 78: 100 x 1 random huge spikes
        {
            int[][] heights = new int[100][1];
            for (int r = 0; r < 100; r++) heights[r][0] = rng.nextInt(1000000) + 1;
            writeTestCase(tc++, 100, 1, heights);
        }
        // TC 79: 2 x 100 ladder
        {
            int[][] heights = new int[2][100];
            for (int c = 0; c < 100; c++) {
                heights[0][c] = c + 1;
                heights[1][c] = (c % 2 == 0) ? c + 1 : 1000000;
            }
            writeTestCase(tc++, 2, 100, heights);
        }
        // TC 80: 100 x 2 ladder
        {
            int[][] heights = new int[100][2];
            for (int r = 0; r < 100; r++) {
                heights[r][0] = r + 1;
                heights[r][1] = (r % 2 == 0) ? r + 1 : 1000000;
            }
            writeTestCase(tc++, 100, 2, heights);
        }
        // TC 81 - 84: 5 x 100 and 100 x 5 stripes
        for (int k = 0; k < 4; k++) {
            int R = (k % 2 == 0) ? 5 : 100;
            int C = (k % 2 == 0) ? 100 : 5;
            int[][] heights = new int[R][C];
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    heights[r][c] = rng.nextInt(500000) + 1;
                }
            }
            writeTestCase(tc++, R, C, heights);
        }

        // TC 85 - 94: Large 80x80 to 95x95 grids
        for (int k = 0; k < 10; k++) {
            int R = 80 + k;
            int C = 80 + k;
            int[][] heights = new int[R][C];
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    heights[r][c] = rng.nextInt(1000000) + 1;
                }
            }
            writeTestCase(tc++, R, C, heights);
        }

        // TC 95 - 99: Max constraint 100x100 stress tests (10,000 cells)
        // TC 95: 100x100 uniform random [1, 10^6]
        {
            int R = 100, C = 100;
            int[][] heights = new int[R][C];
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    heights[r][c] = rng.nextInt(1000000) + 1;
                }
            }
            writeTestCase(tc++, R, C, heights);
        }

        // TC 96: 100x100 flat (effort 0)
        {
            int R = 100, C = 100;
            int[][] heights = new int[R][C];
            for (int r = 0; r < R; r++) Arrays.fill(heights[r], 777777);
            writeTestCase(tc++, R, C, heights);
        }

        // TC 97: 100x100 linear gradient (r + c) -> effort 1
        {
            int R = 100, C = 100;
            int[][] heights = new int[R][C];
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    heights[r][c] = r + c + 1;
                }
            }
            writeTestCase(tc++, R, C, heights);
        }

        // TC 98: 100x100 full serpentine route of effort 0 in mountain of 10^6
        {
            int R = 100, C = 100;
            int[][] heights = new int[R][C];
            for (int r = 0; r < R; r++) Arrays.fill(heights[r], 1000000);
            for (int r = 0; r < R; r++) {
                if (r % 2 == 0) {
                    for (int c = 0; c < C; c++) heights[r][c] = 1;
                } else {
                    int turnC = (r % 4 == 1) ? C - 1 : 0;
                    heights[r][turnC] = 1;
                }
            }
            writeTestCase(tc++, R, C, heights);
        }

        // TC 99: 100x100 adversarial Dijkstra updates
        {
            int R = 100, C = 100;
            int[][] heights = new int[R][C];
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    heights[r][c] = ((r * 31 + c * 17) % 500) * 1000 + rng.nextInt(500);
                }
            }
            writeTestCase(tc++, R, C, heights);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Path With Minimum Effort.");
    }
}
