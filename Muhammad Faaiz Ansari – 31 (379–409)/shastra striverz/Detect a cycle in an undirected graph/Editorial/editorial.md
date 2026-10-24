# Editorial: Detect a Cycle in an Undirected Graph

## Difficulty
Medium

## Prerequisites
- Breadth-First Search (BFS) / Depth-First Search (DFS)
- Graph Representation (Adjacency List)
- Disjoint Set Union (DSU / Kruskal's cycle property)

---

## The Undirected Graph Cycle Invariant

In a directed graph, any back-edge implies a cycle. In an **undirected graph**, however:
- Every edge is bidirectional: if $u$ is connected to $v$, then $v$ is also connected to $u$.
- When traversing from $u$ to $v$, the reverse edge $(v, u)$ simply leads back to the vertex we just came from (the **parent**).
- **Key Invariant:** A cycle exists if and only if, during traversal from vertex $u$, we encounter an already-visited neighbor $v$ such that **$v \ne \text{parent}$**.

---

## BFS Algorithm with Parent Tracking

1. Maintain a boolean array `visited` of size $V$, initially all `false`.
2. Since the graph may be disconnected into multiple components, loop over all vertices $i$ from $0$ to $V - 1$:
   - If `!visited[i]`, start a BFS from $i$:
     - Enqueue pair `(i, -1)` (node, parent).
     - Mark `visited[i] = true`.
     - While the queue is not empty:
       - Dequeue `(u, parent)`.
       - For each neighbor $v \in \text{adj}[u]$:
         - If `!visited[v]`:
           - Mark `visited[v] = true`.
           - Enqueue `(v, u)` (setting $u$ as $v$'s parent).
         - Else if $v \ne \text{parent}$:
           - Vertex $v$ was visited through another path, meaning multiple independent paths connect $i$ to $v$.
           - A cycle is detected! Return `true`.
3. If all components are fully traversed without encountering any back-edge, return `false`.

---

## Step-by-Step Dry Run (Example 3)

**Vertices:** $V = 4$, **Edges:** $[(0, 1), (0, 2), (1, 2), (2, 3)]$

1. **Start Component at vertex 0:**
   - Mark `visited[0] = true`.
   - Enqueue `(0, -1)`.

2. **Process `(0, -1)`:**
   - Neighbors of 0: `1, 2`.
   - Neighbor 1: unvisited $\to$ `visited[1] = true`, enqueue `(1, 0)`.
   - Neighbor 2: unvisited $\to$ `visited[2] = true`, enqueue `(2, 0)`.
   - Queue: `[(1, 0), (2, 0)]`.

3. **Process `(1, 0)`:**
   - Neighbors of 1: `0, 2`.
   - Neighbor 0: visited, and $0 == \text{parent}$ (trivial back edge $\to$ ignored).
   - Neighbor 2: visited! Check parent: $2 \ne 0$.
   - **Condition $v \ne \text{parent}$ met ($2 \ne 0$)!**
   - Cycle detected ($0 - 1 - 2 - 0$). Return `true`.

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(V + E)$
  - In the worst case (acyclic graph or tree/forest), every vertex is visited once and every edge is examined twice (once from each endpoint).
  - For $V, E \le 10^4$, total operations $\le 30,000$, executing in $< 0.01$ seconds.

- **Space Complexity:** $\mathcal{O}(V)$
  - The `visited` array consumes $\mathcal{O}(V)$ auxiliary space.
  - The BFS queue holds at most $\mathcal{O}(V)$ nodes at any point.
  - The adjacency list consumes $\mathcal{O}(V + E)$ space.
