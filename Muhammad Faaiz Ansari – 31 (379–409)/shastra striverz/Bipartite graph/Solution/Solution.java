import java.io.*;
import java.util.*;

public class Solution {

    public static boolean isBipartite(int V, List<Integer>[] adj) {
        if (V <= 2 && adj == null) return true;

        int[] color = new int[V];
        Arrays.fill(color, -1);

        int[] queue = new int[V];

        for (int i = 0; i < V; i++) {
            if (color[i] == -1) {
                int head = 0;
                int tail = 0;

                color[i] = 0;
                queue[tail++] = i;

                while (head < tail) {
                    int u = queue[head++];

                    if (adj[u] != null) {
                        for (int v : adj[u]) {
                            if (color[v] == -1) {
                                color[v] = 1 - color[u];
                                queue[tail++] = v;
                            } else if (color[v] == color[u]) {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    public static boolean isBipartite(int V, List<List<Integer>> edges) {
        @SuppressWarnings("unchecked")
        List<Integer>[] adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
        for (List<Integer> edge : edges) {
            int u = edge.get(0);
            int v = edge.get(1);
            adj[u].add(v);
            adj[v].add(u);
        }
        return isBipartite(V, adj);
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);
        if (!fs.hasNext()) return;

        int V = fs.nextInt();
        int E = fs.nextInt();

        @SuppressWarnings("unchecked")
        List<Integer>[] adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < E; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        boolean result = isBipartite(V, adj);
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
