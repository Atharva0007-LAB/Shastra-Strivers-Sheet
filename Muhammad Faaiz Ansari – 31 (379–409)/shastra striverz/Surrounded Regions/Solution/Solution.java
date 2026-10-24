import java.io.*;
import java.util.*;

public class Solution {

    private static final int[] dr = {-1, 1, 0, 0};
    private static final int[] dc = {0, 0, -1, 1};

    public static char[][] fill(char[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return mat;
        }

        int N = mat.length;
        int M = mat[0].length;
        int totalCells = N * M;

        int[] queueR = new int[totalCells];
        int[] queueC = new int[totalCells];
        int head = 0;
        int tail = 0;

        // Push all boundary 'O' cells to queue and mark as '#'
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

        // BFS to mark all boundary-connected 'O's as '#'
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

        // Flip remaining 'O' to 'X' (enclosed), restore '#' to 'O' (safe)
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

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);
        if (!fs.hasNext()) return;

        int N = fs.nextInt();
        int M = fs.nextInt();

        char[][] mat = new char[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                mat[i][j] = fs.next().charAt(0);
            }
        }

        char[][] result = fill(mat);

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
