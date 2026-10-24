# Editorial: Connected Components

## Difficulty
Medium

## Prerequisites
- Graph Representation (Adjacency Lists)
- Graph Traversal (DFS or BFS)
- Disjoint Set Union (Union-Find)
- Visited Array State Tracking

---

## Problem Statement
You are given an undirected graph with $V$ vertices (labeled $0$ through $V-1$) and $E$ edges. Two vertices belong to the same connected component if there exists a path between them. You need to calculate the total number of connected components in the graph.

---

## Detailed Approaches

### Approach 1: Graph Traversal (DFS / BFS) — Preferred
The most direct way to count connected components is to iterate through all vertices $0, 1, \dots, V-1$:
1. Maintain a boolean array `visited` of size $V$, initialized to `false`.
2. Initialize `componentCount = 0`.
3. Iterate from `i = 0` to `V - 1`:
   - If `!visited[i]`:
     - Increment `componentCount++`.
     - Launch a traversal (DFS or BFS) starting from vertex `i`.
     - During the traversal, mark all reachable vertices as `visited = true`.
4. After checking all $V$ vertices, `componentCount` holds the exact number of connected components.

#### Why does this work?
A connected component is maximal: every vertex reachable from $i$ belongs to the same component as $i$. When the traversal terminates, every vertex in that component is marked `visited`. Hence, any subsequent unvisited vertex must belong to a completely distinct component.

#### Implementation (Iterative DFS for Stack Safety):
```java
int components = 0;
boolean[] visited = new boolean[V];
int[] stack = new int[V];

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
```

---

### Approach 2: Disjoint Set Union (DSU / Union-Find)
An alternative approach processes edges dynamically using a Disjoint Set:
1. Initialize each of the $V$ vertices into its own set (`parent[i] = i`). Set `components = V`.
2. For each undirected edge $(u, v)$:
   - Find the representatives (roots) of $u$ and $v$ using path compression.
   - If $\text{find}(u) \ne \text{find}(v)$:
     - Union their sets (using union by rank/size).
     - Decrement `components--`.
3. The remaining value of `components` is the final answer.

---

## Step-by-Step Dry Run (Example 2)

Input: $V = 7$, edges = `[[0, 1], [1, 2], [2, 3], [4, 5]]`

1. Start at `i = 0`:
   - `visited[0] == false` $\to$ `components = 1`.
   - Traversal marks: $\{0, 1, 2, 3\}$ as `visited = true`.
2. Check `i = 1, 2, 3`: Already visited.
3. Check `i = 4`:
   - `visited[4] == false` $\to$ `components = 2`.
   - Traversal marks: $\{4, 5\}$ as `visited = true`.
4. Check `i = 5`: Already visited.
5. Check `i = 6`:
   - `visited[6] == false` $\to$ `components = 3`.
   - Traversal marks: $\{6\}$ as `visited = true`.
6. Loop ends. Output = **3**.

---

## Complexity Analysis

### Graph Traversal (DFS / BFS)
- **Time Complexity:** $\mathcal{O}(V + E)$
  - Building the adjacency list: $\mathcal{O}(V + E)$.
  - Traversals: Every vertex is visited once, and each undirected edge is checked twice across the entire loop. Total traversal time is $\mathcal{O}(V + 2E) = \mathcal{O}(V + E)$.
  - For $V, E \le 10^4$, total operations $\approx 2 \times 10^4$, executing in under $0.02$ seconds.
- **Space Complexity:** $\mathcal{O}(V + E)$
  - Adjacency list: $\mathcal{O}(V + 2E)$.
  - Visited array and traversal stack: $\mathcal{O}(V)$.

### DSU Approach
- **Time Complexity:** $\mathcal{O}(V + E \cdot \alpha(V))$, where $\alpha$ is the Inverse Ackermann function ($\approx \mathcal{O}(V + E)$).
- **Space Complexity:** $\mathcal{O}(V)$ for the parent and rank arrays.
