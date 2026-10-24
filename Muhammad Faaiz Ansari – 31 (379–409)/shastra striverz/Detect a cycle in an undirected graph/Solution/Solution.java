import java.io.*;
import java.util.*;

public class Solution {

    public static boolean isCycle(int V, List<Integer>[] adj) {
        if (V <= 2 && adj == null) return false;

        boolean[] visited = new boolean[V];
        int[] queueNode = new int[V];
        int[] queueParent = new int[V];

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                int head = 0;
                int tail = 0;

                visited[i] = true;
                queueNode[tail] = i;
                queueParent[tail] = -1;
                tail++;

                while (head < tail) {
                    int u = queueNode[head];
                    int p = queueParent[head];
                    head++;

                    if (adj[u] != null) {
                        for (int v : adj[u]) {
                            if (!visited[v]) {
                                visited[v] = true;
                                queueNode[tail] = v;
                                queueParent[tail] = u;
                                tail++;
                            } else if (v != p) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
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

        boolean result = isCycle(V, adj);
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
