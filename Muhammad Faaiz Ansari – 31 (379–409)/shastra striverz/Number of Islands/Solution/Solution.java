import java.io.*;
import java.util.*;

public class Solution {

    private static final int[] dr = {-1, -1, -1,  0, 0,  1, 1, 1};
    private static final int[] dc = {-1,  0,  1, -1, 1, -1, 0, 1};

    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int N = grid.length;
        int M = grid[0].length;
        boolean[][] visited = new boolean[N][M];
        int islands = 0;

        int totalCells = N * M;
        int[] queueR = new int[totalCells];
        int[] queueC = new int[totalCells];

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < M; c++) {
                if (grid[r][c] == '1' && !visited[r][c]) {
                    islands++;
                    int head = 0, tail = 0;
                    visited[r][c] = true;
                    queueR[tail] = r;
                    queueC[tail] = c;
                    tail++;

                    while (head < tail) {
                        int currR = queueR[head];
                        int currC = queueC[head];
                        head++;

                        for (int k = 0; k < 8; k++) {
                            int nr = currR + dr[k];
                            int nc = currC + dc[k];

                            if (nr >= 0 && nr < N && nc >= 0 && nc < M) {
                                if (grid[nr][nc] == '1' && !visited[nr][nc]) {
                                    visited[nr][nc] = true;
                                    queueR[tail] = nr;
                                    queueC[tail] = nc;
                                    tail++;
                                }
                            }
                        }
                    }
                }
            }
        }

        return islands;
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);
        if (!fs.hasNext()) return;

        int N = fs.nextInt();
        int M = fs.nextInt();

        char[][] grid = new char[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                String s = fs.next();
                grid[i][j] = s.charAt(0);
            }
        }

        int result = numIslands(grid);
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

        public String next() {
            int b = readByte();
            while (b != -1 && b <= ' ') {
                b = readByte();
            }
            if (b == -1) return null;
            StringBuilder sb = new StringBuilder();
            while (b > ' ') {
                sb.append((char) b);
                b = readByte();
            }
            return sb.toString();
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
