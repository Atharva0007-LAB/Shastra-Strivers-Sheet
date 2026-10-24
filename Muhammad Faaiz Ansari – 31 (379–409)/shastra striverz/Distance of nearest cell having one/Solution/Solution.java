import java.io.*;
import java.util.*;

public class Solution {

    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, -1, 1};

    public static int[][] nearest(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return new int[0][0];
        }

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

        // Push all 1s into the multi-source queue
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

        // Multi-Source BFS
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

        int[][] result = nearest(grid);

        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        for (int i = 0; i < N; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < M; j++) {
                if (j > 0) sb.append(' ');
                sb.append(result[i][j]);
            }
            out.println(sb.toString());
        }
        out.flush();
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
