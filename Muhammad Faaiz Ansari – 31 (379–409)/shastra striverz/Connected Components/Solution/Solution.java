import java.io.*;
import java.util.*;

public class Solution {

    public static int findNumberOfComponent(int V, List<List<Integer>> edges) {
        List<List<Integer>> adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for (List<Integer> edge : edges) {
            int u = edge.get(0);
            int v = edge.get(1);
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        return countComponents(V, adj);
    }

    public static int countComponents(int V, List<List<Integer>> adj) {
        if (V <= 1) return V;

        boolean[] visited = new boolean[V];
        int[] stack = new int[V];
        int components = 0;

        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                components++;
                int top = 0;
                stack[top] = i;
                visited[i] = true;

                while (top >= 0) {
                    int u = stack[top--];
                    for (int v : adj.get(u)) {
                        if (!visited[v]) {
                            visited[v] = true;
                            stack[++top] = v;
                        }
                    }
                }
            }
        }
        return components;
    }

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner(System.in);
        if (!fs.hasNext()) return;

        int V = fs.nextInt();
        int E = fs.nextInt();

        List<List<Integer>> adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < E; i++) {
            int u = fs.nextInt();
            int v = fs.nextInt();
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int result = countComponents(V, adj);
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
