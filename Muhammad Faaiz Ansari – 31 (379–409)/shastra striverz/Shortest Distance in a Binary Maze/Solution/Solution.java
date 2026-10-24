import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayDeque;
import java.util.Arrays;

public class Solution {

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[32768];
        private int head = 0;
        private int tail = 0;

        public FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (head >= tail) {
                head = 0;
                tail = in.read(buffer, 0, buffer.length);
                if (tail <= 0) return -1;
            }
            return buffer[head++];
        }

        public int nextInt() throws IOException {
            int c = read();
            while (c <= 32) {
                if (c == -1) return -1;
                c = read();
            }
            boolean neg = false;
            if (c == '-') {
                neg = true;
                c = read();
            }
            int res = 0;
            while (c >= '0' && c <= '9') {
                res = res * 10 + (c - '0');
                c = read();
            }
            return neg ? -res : res;
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));

        int n = fs.nextInt();
        if (n == -1) {
            out.flush();
            return;
        }
        int m = fs.nextInt();

        int srcR = fs.nextInt();
        int srcC = fs.nextInt();
        int dstR = fs.nextInt();
        int dstC = fs.nextInt();

        int[][] grid = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                grid[i][j] = fs.nextInt();
            }
        }

        if (srcR == dstR && srcC == dstC) {
            out.println(0);
            out.flush();
            return;
        }

        if (grid[srcR][srcC] == 0 || grid[dstR][dstC] == 0) {
            out.println(-1);
            out.flush();
            return;
        }

        int[][] dist = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], -1);
        }

        // BFS using an int array queue for maximum speed
        int totalCells = n * m;
        int[] qR = new int[totalCells];
        int[] qC = new int[totalCells];
        int qHead = 0;
        int qTail = 0;

        dist[srcR][srcC] = 0;
        qR[qTail] = srcR;
        qC[qTail] = srcC;
        qTail++;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int ans = -1;
        while (qHead < qTail) {
            int r = qR[qHead];
            int c = qC[qHead];
            qHead++;

            if (r == dstR && c == dstC) {
                ans = dist[r][c];
                break;
            }

            int curDist = dist[r][c];
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m && grid[nr][nc] == 1 && dist[nr][nc] == -1) {
                    dist[nr][nc] = curDist + 1;
                    if (nr == dstR && nc == dstC) {
                        ans = dist[nr][nc];
                        qHead = qTail; // break outer loop
                        break;
                    }
                    qR[qTail] = nr;
                    qC[qTail] = nc;
                    qTail++;
                }
            }
        }

        out.println(ans);
        out.flush();
    }
}
