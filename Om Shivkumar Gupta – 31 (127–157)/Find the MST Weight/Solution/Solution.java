import java.util.*;

public class Solution {
    static class Edge implements Comparable<Edge> {
        int u, v, weight;
        Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    static class DisjointSet {
        int[] parent;
        int[] rank;

        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int node) {
            if (node == parent[node]) return node;
            return parent[node] = find(parent[node]); // Path compression
        }

        boolean unionByRank(int u, int v) {
            int rootU = find(u);
            int rootV = find(v);
            if (rootU == rootV) return false;

            if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else if (rank[rootV] < rank[rootU]) {
                parent[rootV] = rootU;
            } else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }
            return true;
        }
    }

    /**
     * Computes the total weight of the MST using Kruskal's algorithm.
     */
    public long spanningTreeWeight(int V, List<Edge> edges) {
        Collections.sort(edges);
        DisjointSet ds = new DisjointSet(V);

        long mstWeight = 0;
        int edgesCount = 0;

        for (Edge edge : edges) {
            if (ds.unionByRank(edge.u, edge.v)) {
                mstWeight += edge.weight;
                edgesCount++;
                if (edgesCount == V - 1) break;
            }
        }
        return mstWeight;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int v = sc.nextInt();
        int e = sc.nextInt();

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < e; i++) {
            int u = sc.nextInt();
            int to = sc.nextInt();
            int w = sc.nextInt();
            edges.add(new Edge(u, to, w));
        }

        Solution sol = new Solution();
        long ans = sol.spanningTreeWeight(v, edges);
        System.out.println(ans);
        sc.close();
    }
}
