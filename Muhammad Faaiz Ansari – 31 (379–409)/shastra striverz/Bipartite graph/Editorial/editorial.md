# Editorial: Bipartite Graph

## Difficulty
Medium

## Prerequisites
- Breadth-First Search (BFS) / Depth-First Search (DFS)
- Graph Coloring (2-Coloring)
- Graph Theory Properties (Bipartite $\iff$ No Odd Cycles)

---

## What Makes a Graph Bipartite?

A graph is **bipartite** if its vertices can be partitioned into two sets $A$ and $B$ such that no two vertices within the same set share an edge. 

This is mathematically equivalent to the **2-Coloring Problem**:
> Can we color every vertex using only 2 colors (say, `0` and `1`) such that no two adjacent vertices have the same color?

### Fundamental Property: Odd Cycles vs. Even Cycles
- **Acyclic Graphs (Trees & Forests):** Always bipartite. We can color nodes level-by-level alternately.
- **Even-Length Cycles (e.g. 4, 6, 8 edges):** Always bipartite. Alternating colors around the cycle wrap around without conflicts.
- **Odd-Length Cycles (e.g. 3, 5, 7 edges):** **Never bipartite.** When traversing an odd cycle, the first and last vertices must have the same color, but they are directly connected, creating a conflict.

---

## BFS 2-Coloring Algorithm

1. Initialize a `color` array of size $V$ with `-1` (representing uncolored).
2. Because the graph may have multiple disconnected components, loop over all vertices $i \in [0, V - 1]$:
   - If `color[i] == -1` (a new component):
     - Assign `color[i] = 0`.
     - Initialize a queue $Q$ and push $i$.
     - While $Q$ is not empty:
       - Dequeue vertex $u$.
       - For each adjacent neighbor $v \in \text{adj}[u]$:
         - If $v$ is uncolored (`color[v] == -1`):
           - Assign it the opposite color: `color[v] = 1 - color[u]`.
           - Enqueue $v$.
         - Else if `color[v] == color[u]`:
           - Two adjacent vertices share the exact same color! An odd cycle exists.
           - Return `false` immediately.
3. If all vertices and components are successfully 2-colored without conflict, return `true`.

---

## Step-by-Step Dry Run

### Case A: Even Cycle (Example 1)
$V = 4$, edges: $(0, 1), (0, 3), (1, 2), (2, 3)$ (Square).

| Step | Node | Action | Colors Array `[0, 1, 2, 3]` | Queue |
|:---:|:---:|:---|:---:|:---:|
| 1 | 0 | Start component, color 0 | `[0, -1, -1, -1]` | `[0]` |
| 2 | 0 | Pop 0. Neighbors 1, 3 color 1 | `[0, 1, -1, 1]` | `[1, 3]` |
| 3 | 1 | Pop 1. Neighbor 2 colors 0 | `[0, 1, 0, 1]` | `[3, 2]` |
| 4 | 3 | Pop 3. Neighbors 0 (color 0), 2 (color 0) $\to$ match opposite color! | `[0, 1, 0, 1]` | `[2]` |
| 5 | 2 | Pop 2. All neighbors colored opposite. | `[0, 1, 0, 1]` | `[]` |

Queue empty with no conflicts $\to$ **`true`**.

---

### Case B: Odd Cycle (Triangle)
Edges: $(0, 1), (1, 2), (2, 0)$.
1. Node 0 colored `0`, pushes 0.
2. Node 0 pops, colors neighbor 1 with `1`, neighbor 2 with `1`.
3. Node 1 pops, checks neighbor 2.
   - `color[2] == color[1] == 1` $\implies$ **Conflict!**
4. Conflict detected $\to$ **`false`**.

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(V + E)$
  - Each vertex is colored and enqueued at most once.
  - Every edge is checked twice (once from each endpoint).
  - For $V, E \le 10^4$, total operations $\le 30,000$, executing in $< 0.01$ seconds.

- **Space Complexity:** $\mathcal{O}(V)$
  - The `color` array takes $\mathcal{O}(V)$ space.
  - The queue stores at most $\mathcal{O}(V)$ vertices.
