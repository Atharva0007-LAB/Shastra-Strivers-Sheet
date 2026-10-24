import java.io.*;
import java.util.*;

public class Solution {

    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, -1, 1};

    public static int countDistinctIslands(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

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
                    int head = 0;
                    int tail = 0;

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
                        // Pack diffR and diffC into a unique long
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

        int result = countDistinctIslands(grid);
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
