import java.io.*;
import java.util.*;

public class Solution {

    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, -1, 1};

    public static int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int N = grid.length;
        int M = grid[0].length;
        int totalCells = N * M;

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

        // If there are no fresh oranges initially, 0 minutes required
        if (freshCount == 0) {
            return 0;
        }

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

        int result = orangesRotting(grid);
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
