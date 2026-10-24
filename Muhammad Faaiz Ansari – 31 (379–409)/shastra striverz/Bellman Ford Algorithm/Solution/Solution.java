import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.Arrays;

public class Solution {

    static final int INF = 1000000000;

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

        int V = fs.nextInt();
        if (V == -1) {
            out.flush();
            return;
        }
        int E = fs.nextInt();
        int S = fs.nextInt();

        int[] u = new int[E];
        int[] v = new int[E];
        int[] w = new int[E];

        for (int i = 0; i < E; i++) {
            u[i] = fs.nextInt();
            v[i] = fs.nextInt();
            w[i] = fs.nextInt();
        }

        int[] dist = new int[V];
        Arrays.fill(dist, INF);
        dist[S] = 0;

        // Relax V - 1 times
        for (int iter = 0; iter < V - 1; iter++) {
            boolean updated = false;
            for (int i = 0; i < E; i++) {
                int src = u[i];
                int dst = v[i];
                int wt = w[i];

                if (dist[src] != INF && dist[src] + wt < dist[dst]) {
                    dist[dst] = dist[src] + wt;
                    updated = true;
                }
            }
            if (!updated) break;
        }

        // V-th iteration: Check for negative cycle
        boolean hasNegCycle = false;
        for (int i = 0; i < E; i++) {
            int src = u[i];
            int dst = v[i];
            int wt = w[i];

            if (dist[src] != INF && dist[src] + wt < dist[dst]) {
                hasNegCycle = true;
                break;
            }
        }

        if (hasNegCycle) {
            out.println("-1");
        } else {
            for (int i = 0; i < V; i++) {
                out.print(dist[i]);
                if (i < V - 1) out.print(" ");
            }
            out.println();
        }

        out.flush();
    }
}
