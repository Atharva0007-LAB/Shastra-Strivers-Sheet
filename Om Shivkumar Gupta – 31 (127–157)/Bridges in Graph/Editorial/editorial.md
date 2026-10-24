# 143. Bridges in Graph - Editorial

## Problem Statement
Given an undirected connected graph with `V` vertices and `E` edges. A bridge is an edge whose removal causes the graph to become disconnected (increases number of connected components).

Find all bridges in the graph. Print each bridge on a new line with the smaller vertex first (`u < v`), and sort all bridges in ascending lexicographical order. If no bridges exist, print `-1`.

---

## Sample Example

### Sample Input
```text
4 4
0 1
1 2
2 0
1 3
```

### Sample Output
```text
1 3
```

### Explanation
* Removing edge (1, 3) disconnects vertex 3 from the rest of the graph.
* Removing (0, 1), (1, 2), or (2, 0) leaves the graph connected via the remaining cycle.
* Therefore, the only bridge is 1 3.

---

## Intuition
A brute-force bridge finder removes each edge one by one and checks connectivity with BFS/DFS in O(E * (V + E)), which will TLE for large graphs.

Tarjan's Bridge-Finding Algorithm discovers all bridges in a single DFS pass O(V + E) by tracking two timestamps for each vertex `u`:
- `tin[u]`: Time of entry (discovery time)
- `low[u]`: Lowest discovery time reachable from `u` using at most one back-edge

An edge `(u, v)` is a bridge if and only if `low[v] > tin[u]`, which proves that subtree rooted at `v` has no alternate path to `u` or any ancestor of `u`.

## Approach
1. Build the adjacency list for the undirected graph.
2. Maintain `tin[]`, `low[]`, and `vis[]` arrays.
3. In DFS from `u` with parent `p`:
   - Set `tin[u] = low[u] = timer++`.
   - For neighbor `v`:
     - If `v == p`, continue.
     - If already visited: `low[u] = min(low[u], tin[v])`.
     - Else:
       - Recurse on `v`.
       - Update `low[u] = min(low[u], low[v])`.
       - If `low[v] > tin[u]`, add `(min(u, v), max(u, v))` to bridges.
4. Sort bridges lexicographically and print (or print `-1` if none).

---

## Complexity Analysis
* **Time Complexity:** O(V + E) for the DFS traversal + O(B log B) to sort the bridges found.
* **Space Complexity:** O(V + E) to store the adjacency list and DFS recursion state.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private int timer = 1;

    private void dfs(int u, int p, int[] tin, int[] low, boolean[] vis, List<List<Integer>> adj, List<List<Integer>> bridges) {
        vis[u] = true;
        tin[u] = low[u] = timer++;

        for (int v : adj.get(u)) {
            if (v == p) continue; // Skip edge to parent

            if (vis[v]) {
                // Back-edge: update lowest time
                low[u] = Math.min(low[u], tin[v]);
            } else {
                // Forward edge
                dfs(v, u, tin, low, vis, adj, bridges);
                low[u] = Math.min(low[u], low[v]);

                // Bridge condition
                if (low[v] > tin[u]) {
                    List<Integer> bridge = new ArrayList<>();
                    bridge.add(Math.min(u, v));
                    bridge.add(Math.max(u, v));
                    bridges.add(bridge);
                }
            }
        }
    }

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (List<Integer> edge : connections) {
            int u = edge.get(0);
            int v = edge.get(1);
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int[] tin = new int[n];
        int[] low = new int[n];
        boolean[] vis = new boolean[n];
        List<List<Integer>> bridges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!vis[i]) {
                dfs(i, -1, tin, low, vis, adj, bridges);
            }
        }

        // Sort bridges lexicographically
        Collections.sort(bridges, (a, b) -> {
            if (!a.get(0).equals(b.get(0))) return Integer.compare(a.get(0), b.get(0));
            return Integer.compare(a.get(1), b.get(1));
        });

        return bridges;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int v = sc.nextInt();
        int e = sc.nextInt();

        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < e; i++) {
            int u = sc.nextInt();
            int to = sc.nextInt();
            connections.add(Arrays.asList(u, to));
        }

        Solution sol = new Solution();
        List<List<Integer>> bridges = sol.criticalConnections(v, connections);

        if (bridges.isEmpty()) {
            System.out.println("-1");
        } else {
            for (List<Integer> b : bridges) {
                System.out.println(b.get(0) + " " + b.get(1));
            }
        }
        sc.close();
    }
}
```
