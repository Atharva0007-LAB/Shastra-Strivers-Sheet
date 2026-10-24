import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class gen {

    private static int solve(int n, int m, int srcR, int srcC, int dstR, int dstC, int[][] grid) {
        if (srcR == dstR && srcC == dstC) return 0;
        if (grid[srcR][srcC] == 0 || grid[dstR][dstC] == 0) return -1;

        int[][] dist = new int[n][m];
        for (int i = 0; i < n; i++) Arrays.fill(dist[i], -1);

        int total = n * m;
        int[] qR = new int[total];
        int[] qC = new int[total];
        int head = 0, tail = 0;

        dist[srcR][srcC] = 0;
        qR[tail] = srcR;
        qC[tail] = srcC;
        tail++;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (head < tail) {
            int r = qR[head];
            int c = qC[head];
            head++;

            if (r == dstR && c == dstC) return dist[r][c];

            int d = dist[r][c];
            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k];
                int nc = c + dc[k];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m && grid[nr][nc] == 1 && dist[nr][nc] == -1) {
                    dist[nr][nc] = d + 1;
                    if (nr == dstR && nc == dstC) return dist[nr][nc];
                    qR[tail] = nr;
                    qC[tail] = nc;
                    tail++;
                }
            }
        }
        return -1;
    }

    private static void writeTestCase(int index, int n, int m, int srcR, int srcC, int dstR, int dstC, int[][] grid) throws Exception {
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
            pw.println(n + " " + m);
            pw.println(srcR + " " + srcC);
            pw.println(dstR + " " + dstC);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    pw.print(grid[i][j]);
                    if (j < m - 1) pw.print(" ");
                }
                pw.println();
            }
        }

        int ans = solve(n, m, srcR, srcC, dstR, dstC, grid);
        try (PrintWriter pw = new PrintWriter(outFile)) {
            pw.println(ans);
        }
    }

    public static void main(String[] args) throws Exception {
        Random rng = new Random(5272026L);
        int tc = 0;

        // Sample 1: 5x4
        {
            int[][] grid = {
                {1, 1, 1, 1},
                {1, 1, 0, 1},
                {1, 1, 1, 1},
                {1, 1, 0, 0},
                {1, 0, 0, 1}
            };
            writeTestCase(tc++, 5, 4, 0, 1, 2, 2, grid);
        }

        // Sample 2: 4x5
        {
            int[][] grid = {
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 0},
                {1, 0, 1, 0, 1}
            };
            writeTestCase(tc++, 4, 5, 0, 0, 3, 4, grid);
        }

        // Sample 3: 3x3
        {
            int[][] grid = {
                {1, 0, 1},
                {1, 1, 0},
                {1, 1, 1}
            };
            writeTestCase(tc++, 3, 3, 0, 0, 2, 2, grid);
        }

        // TC 03: Minimal 1x1
        {
            int[][] grid = {{1}};
            writeTestCase(tc++, 1, 1, 0, 0, 0, 0, grid);
        }

        // TC 04: 1x2 connected
        {
            int[][] grid = {{1, 1}};
            writeTestCase(tc++, 1, 2, 0, 0, 0, 1, grid);
        }

        // TC 05: 1x3 disconnected in middle
        {
            int[][] grid = {{1, 0, 1}};
            writeTestCase(tc++, 1, 3, 0, 0, 0, 2, grid);
        }

        // TC 06: 2x1 connected
        {
            int[][] grid = {{1}, {1}};
            writeTestCase(tc++, 2, 1, 0, 0, 1, 0, grid);
        }

        // TC 07: 2x2 all 1s
        {
            int[][] grid = {{1, 1}, {1, 1}};
            writeTestCase(tc++, 2, 2, 0, 0, 1, 1, grid);
        }

        // TC 08: 2x2 obstacle diagonal
        {
            int[][] grid = {{1, 0}, {0, 1}};
            writeTestCase(tc++, 2, 2, 0, 0, 1, 1, grid);
        }

        // TC 09: 1x20 all 1s
        {
            int[][] grid = new int[1][20];
            Arrays.fill(grid[0], 1);
            writeTestCase(tc++, 1, 20, 0, 0, 0, 19, grid);
        }

        // TC 10: 20x1 with obstacle at row 10
        {
            int[][] grid = new int[20][1];
            for (int i = 0; i < 20; i++) grid[i][0] = 1;
            grid[10][0] = 0;
            writeTestCase(tc++, 20, 1, 0, 0, 19, 0, grid);
        }

        // TC 11: 5x5 src == dst at (2, 2)
        {
            int[][] grid = new int[5][5];
            for (int i = 0; i < 5; i++) Arrays.fill(grid[i], 1);
            writeTestCase(tc++, 5, 5, 2, 2, 2, 2, grid);
        }

        // TC 12: 10x10 all 1s (Manhattan dist)
        {
            int[][] grid = new int[10][10];
            for (int i = 0; i < 10; i++) Arrays.fill(grid[i], 1);
            writeTestCase(tc++, 10, 10, 0, 0, 9, 9, grid);
        }

        // TC 13: Checkerboard pattern (unreachable)
        {
            int n = 8, m = 8;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    grid[i][j] = (i + j) % 2 == 0 ? 1 : 0;
                }
            }
            writeTestCase(tc++, n, m, 0, 0, 6, 6, grid);
        }

        // TC 14: Snake corridor 7x7
        {
            int n = 7, m = 7;
            int[][] grid = new int[n][m];
            for (int r = 0; r < n; r++) {
                if (r % 2 == 0) {
                    for (int c = 0; c < m; c++) grid[r][c] = 1;
                } else {
                    int turnC = (r % 4 == 1) ? m - 1 : 0;
                    grid[r][turnC] = 1;
                }
            }
            writeTestCase(tc++, n, m, 0, 0, n - 1, (n - 1) % 4 == 0 ? 0 : m - 1, grid);
        }

        // TC 15 - 20: Concentric rings / walled enclosures
        for (int k = 0; k < 6; k++) {
            int n = 15, m = 15;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) Arrays.fill(grid[i], 1);
            // make a box wall
            int margin = 3 + k;
            for (int c = margin; c < m - margin; c++) {
                grid[margin][c] = 0;
                grid[n - 1 - margin][c] = 0;
            }
            for (int r = margin; r < n - margin; r++) {
                grid[r][margin] = 0;
                grid[r][m - 1 - margin] = 0;
            }
            // in some, punch a door; in others leave closed
            if (k % 2 == 0) {
                grid[margin][m / 2] = 1; // door
            }
            writeTestCase(tc++, n, m, 0, 0, n / 2, m / 2, grid);
        }

        // TC 21 - 30: Small/medium randomized grids with varying obstacle densities
        for (int k = 0; k < 10; k++) {
            int n = 15 + k * 2;
            int m = 15 + k * 2;
            double p = 0.65 + (k % 4) * 0.08;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    grid[i][j] = rng.nextDouble() < p ? 1 : 0;
                }
            }
            grid[0][0] = 1;
            grid[n - 1][m - 1] = 1;
            writeTestCase(tc++, n, m, 0, 0, n - 1, m - 1, grid);
        }

        // TC 31 - 40: Mazes with guaranteed paths
        for (int k = 0; k < 10; k++) {
            int n = 25, m = 25;
            int[][] grid = new int[n][m];
            // fill mostly with 0
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    grid[i][j] = rng.nextDouble() < 0.35 ? 1 : 0;
                }
            }
            // carve a random walk path from (0,0) to (n-1, m-1)
            int cr = 0, cc = 0;
            grid[cr][cc] = 1;
            while (cr < n - 1 || cc < m - 1) {
                if (cr == n - 1) cc++;
                else if (cc == m - 1) cr++;
                else if (rng.nextBoolean()) cr++;
                else cc++;
                grid[cr][cc] = 1;
            }
            writeTestCase(tc++, n, m, 0, 0, n - 1, m - 1, grid);
        }

        // TC 41 - 50: Grid with bottlenecks & chokepoints
        for (int k = 0; k < 10; k++) {
            int n = 30, m = 30;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) Arrays.fill(grid[i], 1);
            // vertical wall at col = 15
            for (int r = 0; r < n; r++) grid[r][15] = 0;
            // chokepoint opening
            if (k % 3 != 0) {
                int gate = rng.nextInt(n);
                grid[gate][15] = 1;
            }
            // horizontal wall at row = 15
            for (int c = 0; c < m; c++) grid[15][c] = 0;
            if (k % 3 != 1) {
                int gate = rng.nextInt(m);
                grid[15][gate] = 1;
            }
            grid[0][0] = 1;
            grid[n - 1][m - 1] = 1;
            writeTestCase(tc++, n, m, 0, 0, n - 1, m - 1, grid);
        }

        // TC 51 - 65: Medium scale grids (n, m from 50 to 150)
        for (int k = 0; k < 15; k++) {
            int n = 50 + k * 7;
            int m = 60 + k * 6;
            double p = 0.70 + (k % 3) * 0.1;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    grid[i][j] = rng.nextDouble() < p ? 1 : 0;
                }
            }
            int srcR = rng.nextInt(n / 4);
            int srcC = rng.nextInt(m / 4);
            int dstR = n - 1 - rng.nextInt(n / 4);
            int dstC = m - 1 - rng.nextInt(m / 4);
            grid[srcR][srcC] = 1;
            grid[dstR][dstC] = 1;
            writeTestCase(tc++, n, m, srcR, srcC, dstR, dstC, grid);
        }

        // TC 66 - 74: Large serpentine snake mazes
        for (int k = 0; k < 9; k++) {
            int n = 100 + k * 20;
            int m = 100 + k * 20;
            int[][] grid = new int[n][m];
            for (int r = 0; r < n; r++) {
                if (r % 2 == 0) {
                    for (int c = 0; c < m; c++) grid[r][c] = 1;
                } else {
                    int turnC = (r % 4 == 1) ? m - 1 : 0;
                    grid[r][turnC] = 1;
                }
            }
            // in some, add small shortcuts
            for (int s = 0; s < k * 10; s++) {
                int r = rng.nextInt(n);
                int c = rng.nextInt(m);
                grid[r][c] = 1;
            }
            int dstRow = n - 1;
            int dstCol = ((n - 1) % 4 == 0) ? 0 : m - 1;
            grid[0][0] = 1;
            grid[dstRow][dstCol] = 1;
            writeTestCase(tc++, n, m, 0, 0, dstRow, dstCol, grid);
        }

        // TC 75 - 84: High resolution grids (n, m from 200 to 350)
        for (int k = 0; k < 10; k++) {
            int n = 200 + k * 15;
            int m = 200 + k * 15;
            double p = 0.72 + (k % 2) * 0.15;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    grid[i][j] = rng.nextDouble() < p ? 1 : 0;
                }
            }
            int srcR = 0, srcC = 0;
            int dstR = n - 1, dstC = m - 1;
            grid[srcR][srcC] = 1;
            grid[dstR][dstC] = 1;
            writeTestCase(tc++, n, m, srcR, srcC, dstR, dstC, grid);
        }

        // TC 85 - 89: Tall & Wide extreme aspect ratio grids
        // TC 85: 1 x 500
        {
            int n = 1, m = 500;
            int[][] grid = new int[n][m];
            Arrays.fill(grid[0], 1);
            writeTestCase(tc++, n, m, 0, 0, 0, m - 1, grid);
        }
        // TC 86: 500 x 1
        {
            int n = 500, m = 1;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) grid[i][0] = 1;
            writeTestCase(tc++, n, m, 0, 0, n - 1, 0, grid);
        }
        // TC 87: 2 x 500 ladder
        {
            int n = 2, m = 500;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) Arrays.fill(grid[i], 1);
            // obstacle in row 0
            for (int c = 100; c < 400; c++) grid[0][c] = 0;
            writeTestCase(tc++, n, m, 0, 0, 0, m - 1, grid);
        }
        // TC 88: 500 x 2 ladder
        {
            int n = 500, m = 2;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) Arrays.fill(grid[i], 1);
            for (int r = 50; r < 350; r++) grid[r][1] = 0;
            writeTestCase(tc++, n, m, 0, 0, n - 1, 1, grid);
        }
        // TC 89: 10 x 500 with multiple rivers
        {
            int n = 10, m = 500;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) Arrays.fill(grid[i], 1);
            for (int col = 50; col < 450; col += 50) {
                for (int r = 0; r < n; r++) grid[r][col] = 0;
                grid[rng.nextInt(n)][col] = 1; // 1 bridge
            }
            writeTestCase(tc++, n, m, 0, 0, n - 1, m - 1, grid);
        }

        // TC 90 - 99: Full scale 500x500 stress tests (250,000 cells)
        // TC 90: 500x500 all 1s
        {
            int n = 500, m = 500;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) Arrays.fill(grid[i], 1);
            writeTestCase(tc++, n, m, 0, 0, n - 1, m - 1, grid);
        }

        // TC 91: 500x500 full snake/serpentine (longest possible path ~250,000 steps)
        {
            int n = 500, m = 500;
            int[][] grid = new int[n][m];
            for (int r = 0; r < n; r++) {
                if (r % 2 == 0) {
                    for (int c = 0; c < m; c++) grid[r][c] = 1;
                } else {
                    int turnC = (r % 4 == 1) ? m - 1 : 0;
                    grid[r][turnC] = 1;
                }
            }
            int dstCol = ((n - 1) % 4 == 0) ? 0 : m - 1;
            writeTestCase(tc++, n, m, 0, 0, n - 1, dstCol, grid);
        }

        // TC 92: 500x500 horizontal impassable wall (unreachable -> -1)
        {
            int n = 500, m = 500;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) Arrays.fill(grid[i], 1);
            Arrays.fill(grid[250], 0); // complete wall
            writeTestCase(tc++, n, m, 0, 0, n - 1, m - 1, grid);
        }

        // TC 93: 500x500 vertical impassable wall (unreachable -> -1)
        {
            int n = 500, m = 500;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) Arrays.fill(grid[i], 1);
            for (int r = 0; r < n; r++) grid[r][250] = 0; // vertical wall
            writeTestCase(tc++, n, m, 0, 0, n - 1, m - 1, grid);
        }

        // TC 94: 500x500 single bridge in massive wall
        {
            int n = 500, m = 500;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) Arrays.fill(grid[i], 1);
            Arrays.fill(grid[250], 0);
            grid[250][499] = 1; // single passage at far right
            writeTestCase(tc++, n, m, 0, 0, n - 1, 0, grid);
        }

        // TC 95 - 99: 500x500 random high density percolation
        for (int k = 0; k < 5; k++) {
            int n = 500, m = 500;
            double p = 0.70 + k * 0.05;
            int[][] grid = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    grid[i][j] = rng.nextDouble() < p ? 1 : 0;
                }
            }
            grid[0][0] = 1;
            grid[n - 1][m - 1] = 1;
            writeTestCase(tc++, n, m, 0, 0, n - 1, m - 1, grid);
        }

        System.out.println("SUCCESS: Generated " + tc + " test cases for Shortest Distance in a Binary Maze.");
    }
}
