# 132. Find the MST Weight - Editorial

## Problem Statement
Given a weighted, undirected, and connected graph of `V` vertices and `E` edges, find the sum of weights of the edges in the Minimum Spanning Tree (MST).

An MST is a subset of the edges of a connected edge-weighted undirected graph that connects all the vertices together, without any cycles and with the minimum possible total edge weight.

---

## Sample Example

### Sample Input
```text
3 3
0 1 5
1 2 3
0 2 1
```

### Sample Output
```text
4
```

### Explanation
* The Minimum Spanning Tree selects:
  - Edge (0, 2) with weight 1
  - Edge (1, 2) with weight 3
* Total weight = 1 + 3 = 4.

---

## Intuition
Kruskal's algorithm finds the Minimum Spanning Tree using a Greedy strategy:
1. By sorting all edges in ascending order of their weights, we always evaluate the cheapest available connection first.
2. To ensure no cycle is formed, we use a Disjoint Set Union (DSU) data structure. If two endpoints of an edge already belong to the same connected component, adding the edge would create a cycle, so we skip it.
3. Once exactly `V - 1` edges are chosen, all vertices are connected with minimal weight.

## Approach
1. Define an Edge class holding `(u, v, weight)` and implement comparable sorting by weight.
2. Store all edges in a list and sort them in non-decreasing order.
3. Initialize Disjoint Set with `V` elements using path compression and union by rank.
4. Iterate through sorted edges:
   - For edge `(u, v, w)`, if `find(u) != find(v)`, join the sets using `unionByRank(u, v)` and accumulate `w` into `mstWeight`.
   - Increment `edgesCount`. If `edgesCount == V - 1`, terminate early.
5. Return `mstWeight`.

---

## Complexity Analysis
* **Time Complexity:** O(E log E) or O(E log V) to sort the edges, with near O(1) DSU operations.
* **Space Complexity:** O(V + E) to store the edges and the DSU parent/rank arrays.

---

## Reference Implementation

### Java Solution
```java
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
```
