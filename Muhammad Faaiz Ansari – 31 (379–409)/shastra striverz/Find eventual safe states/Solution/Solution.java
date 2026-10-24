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
    public static List<Integer> eventualSafeNodes(int V, List<Integer>[] revAdj, int[] inDegree) {
        int[] queue = new int[V];
        int head = 0;
        int tail = 0;

        for (int i = 0; i < V; i++) {
            if (inDegree[i] == 0) {
                queue[tail++] = i;
            }
        }

        boolean[] isSafe = new boolean[V];

        while (head < tail) {
            int v = queue[head++];
            isSafe[v] = true;
            for (int u : revAdj[v]) {
                inDegree[u]--;
                if (inDegree[u] == 0) {
                    queue[tail++] = u;
                }
            }
        }

        List<Integer> safeNodes = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            if (isSafe[i]) {
                safeNodes.add(i);
            }
        }

        return safeNodes;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            FastScanner sc = new FastScanner(System.in);
            int V = sc.nextInt();
            if (V == -1) return;
            int E = sc.nextInt();

            List<Integer>[] revAdj = new ArrayList[V];
            for (int i = 0; i < V; i++) {
                revAdj[i] = new ArrayList<>();
            }
            int[] inDegree = new int[V];

            for (int i = 0; i < E; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                revAdj[v].add(u);
                inDegree[u]++;
            }

            List<Integer> safeNodes = eventualSafeNodes(V, revAdj, inDegree);

            PrintWriter out = new PrintWriter(new BufferedOutputStream(System.out));
            for (int i = 0; i < safeNodes.size(); i++) {
                out.print(safeNodes.get(i));
                if (i + 1 < safeNodes.size()) {
                    out.print(" ");
                }
            }
            out.println();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
