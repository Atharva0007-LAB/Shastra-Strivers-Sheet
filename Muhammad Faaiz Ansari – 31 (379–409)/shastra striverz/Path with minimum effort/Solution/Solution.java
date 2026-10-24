import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution {

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

        int rows = fs.nextInt();
        if (rows == -1) {
            out.flush();
            return;
        }
        int cols = fs.nextInt();

        int[][] heights = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                heights[r][c] = fs.nextInt();
            }
        }

        if (rows == 1 && cols == 1) {
            out.println(0);
            out.flush();
            return;
        }

        int INF = Integer.MAX_VALUE / 2;
        int[][] effort = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            Arrays.fill(effort[r], INF);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        effort[0][0] = 0;
        pq.add(new Node(0, 0, 0));

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int ans = 0;
        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int e = curr.effort;
            int r = curr.r;
            int c = curr.c;

            if (e > effort[r][c]) continue;
            if (r == rows - 1 && c == cols - 1) {
                ans = e;
                break;
            }

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

        out.println(ans);
        out.flush();
    }
}
