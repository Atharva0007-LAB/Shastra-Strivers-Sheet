import java.io.*;
import java.util.*;

public class Solution {

    public static List<Integer> dfsOfGraph(int V, List<List<Integer>> adj) {
        List<Integer> dfsResult = new ArrayList<>(V);
        if (V == 0) return dfsResult;

        boolean[] visited = new boolean[V];
        int[] stack = new int[V];
        int[] edgePtr = new int[V];
        int top = 0;

        stack[top] = 0;
        edgePtr[top] = 0;
        visited[0] = true;
        dfsResult.add(0);

        while (top >= 0) {
            int u = stack[top];
            List<Integer> neighbors = adj.get(u);
            if (edgePtr[top] < neighbors.size()) {
                int v = neighbors.get(edgePtr[top]++);
                if (!visited[v]) {
                    visited[v] = true;
                    dfsResult.add(v);
                    top++;
                    stack[top] = v;
                    edgePtr[top] = 0;
                }
            } else {
                top--;
            }
        }
        return dfsResult;
    }

    public static List<Integer> bfsOfGraph(int V, List<List<Integer>> adj) {
        List<Integer> bfsResult = new ArrayList<>(V);
        if (V == 0) return bfsResult;

        boolean[] visited = new boolean[V];
        int[] queue = new int[V];
        int head = 0, tail = 0;

        visited[0] = true;
        queue[tail++] = 0;

        while (head < tail) {
            int u = queue[head++];
            bfsResult.add(u);
            for (int v : adj.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    queue[tail++] = v;
                }
            }
        }
        return bfsResult;
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

        List<Integer> dfsOrder = dfsOfGraph(V, adj);
        List<Integer> bfsOrder = bfsOfGraph(V, adj);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dfsOrder.size(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(dfsOrder.get(i));
        }
        sb.append('\n');

        for (int i = 0; i < bfsOrder.size(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(bfsOrder.get(i));
        }
        System.out.println(sb.toString());
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
