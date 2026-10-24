import java.io.*;
import java.util.*;

public class Solution {

    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, -1, 1};

    public static int numEnclaves(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int N = grid.length;
        int M = grid[0].length;
        boolean[][] visited = new boolean[N][M];

        int totalCells = N * M;
        int[] queueR = new int[totalCells];
        int[] queueC = new int[totalCells];
        int head = 0;
        int tail = 0;

        // Push all boundary 1s to the queue
        for (int r = 0; r < N; r++) {
            // First column
            if (grid[r][0] == 1 && !visited[r][0]) {
                visited[r][0] = true;
                queueR[tail] = r;
                queueC[tail] = 0;
                tail++;
            }
            // Last column
            if (M > 1 && grid[r][M - 1] == 1 && !visited[r][M - 1]) {
                visited[r][M - 1] = true;
                queueR[tail] = r;
                queueC[tail] = M - 1;
                tail++;
            }
        }

        for (int c = 0; c < M; c++) {
            // First row
            if (grid[0][c] == 1 && !visited[0][c]) {
                visited[0][c] = true;
                queueR[tail] = 0;
                queueC[tail] = c;
                tail++;
            }
            // Last row
            if (N > 1 && grid[N - 1][c] == 1 && !visited[N - 1][c]) {
                visited[N - 1][c] = true;
                queueR[tail] = N - 1;
                queueC[tail] = c;
                tail++;
            }
        }

        // BFS to mark all boundary-connected land cells
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

        // Count remaining unvisited land cells
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

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);
        if (!fs.hasNext()) return;

        int N = fs.nextInt();
        int M = fs.nextInt();

        int[][] grid = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                grid[i][j] = fs.nextInt();
            }
        }

        int result = numEnclaves(grid);
        System.out.println(result);
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0;
        private int len = 0;

        public FastScanner(InputStream is) {
            this.in = is;
        }

        private int readByte() {
            if (ptr >= len) {
                ptr = 0;
                try {
                    len = in.read(buffer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        public boolean hasNext() {
            int b = readByte();
            while (b != -1 && b <= ' ') {
                b = readByte();
            }
            if (b == -1) return false;
            ptr--;
            return true;
        }

        public int nextInt() {
            int b = readByte();
            while (b != -1 && b <= ' ') {
                b = readByte();
            }
            if (b == -1) return -1;
            int sign = 1;
            if (b == '-') {
                sign = -1;
                b = readByte();
            }
            int res = 0;
            while (b >= '0' && b <= '9') {
                res = res * 10 + (b - '0');
                b = readByte();
            }
            return res * sign;
        }
    }
}
