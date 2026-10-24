import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;

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
            int res = 0;
            while (c > 32) {
                if (c < '0' || c > '9') {
                    throw new NumberFormatException();
                }
                res = res * 10 + (c - '0');
                c = read();
            }
            return res;
        }
    }

    @SuppressWarnings("unchecked")
    public static int[] findOrder(int N, List<Integer>[] adj, int[] inDegree) {
        int[] order = new int[N];
        int head = 0;
        int tail = 0;

        for (int i = 0; i < N; i++) {
            if (inDegree[i] == 0) {
                order[tail++] = i;
            }
        }

        while (head < tail) {
            int u = order[head++];
            for (int v : adj[u]) {
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    order[tail++] = v;
                }
            }
        }

        if (tail == N) {
            return order;
        } else {
            return new int[0];
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            FastScanner sc = new FastScanner(System.in);
            int N = sc.nextInt();
            if (N == -1) return;
            int M = sc.nextInt();

            List<Integer>[] adj = new ArrayList[N];
            for (int i = 0; i < N; i++) {
                adj[i] = new ArrayList<>();
            }
            int[] inDegree = new int[N];

            for (int i = 0; i < M; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                // b must be taken before a (b -> a)
                adj[b].add(a);
                inDegree[a]++;
            }

            int[] order = findOrder(N, adj, inDegree);

            PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
            if (order.length == N) {
                for (int i = 0; i < N; i++) {
                    out.print(order[i]);
                    if (i + 1 < N) {
                        out.print(" ");
                    }
                }
            }
            out.println();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
