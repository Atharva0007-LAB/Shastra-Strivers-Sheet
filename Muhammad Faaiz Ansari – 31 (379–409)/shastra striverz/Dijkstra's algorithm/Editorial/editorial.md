# Editorial: Dijkstra's Algorithm

## Problem Overview
Given an undirected graph with $V$ vertices ($0 \dots V - 1$) and $E$ edges with non-negative weights, find the shortest distance from a specified source vertex $S$ to every vertex in the graph. 

If a vertex is unreachable from $S$, its shortest distance must be set to $10^9$ ($1000000000$).

---

## Core Intuition: Greedy Frontier Expansion
Dijkstra's Algorithm is a greedy single-source shortest path algorithm:
1. **Distance Invariant**:
   - Maintain a distance array `dist[]` initialized to $10^9$, with `dist[S] = 0`.
2. **Greedy Selection**:
   - At each step, select the vertex $u$ among all unvisited vertices that has the **minimum known distance** from $S$.
3. **Why Non-Negative Weights Guarantee Correctness**:
   - Because all edge weights $w \ge 0$, any other path to $u$ via other unexplored vertices must pass through edges with non-negative costs.
   - Since those unexplored vertices already have distances greater than or equal to `dist[u]`, extending paths through them can never yield a path shorter than `dist[u]`.
   - Therefore, the moment vertex $u$ is popped from the min-heap with minimal distance, its shortest path is **permanently optimal and finalized**.

---

## Min-Heap (Priority Queue) Implementation

Using a min-priority queue allows retrieving the closest unfinalized node in $\mathcal{O}(\log V)$ time:

### Algorithm Steps:
1. **Adjacency List**:
   - Store undirected weighted edges in `adj[u]` as pairs `(v, weight)`.
2. **Priority Queue**:
   - Create a min-heap storing pairs `(distance, vertex)`, ordered by distance.
   - Enqueue `(0, S)` and initialize `dist[i] = 10^9` for all $i \ne S$, with `dist[S] = 0`.
3. **Exploration & Relaxation**:
   - While the priority queue is not empty:
     - Pop `(d, u)`.
     - **Stale Entry Pruning**: If $d > \text{dist}[u]$, this pair was superseded by a shorter path found earlier; discard and continue.
     - For each neighbor `(v, wt)` of $u$:
       - If $\text{dist}[u] + wt < \text{dist}[v]$:
         - Update $\text{dist}[v] = \text{dist}[u] + wt$.
         - Enqueue `(dist[v], v)`.
4. **Final Result**:
   - The array `dist` now holds the exact shortest distance to all vertices. Unreachable nodes naturally retain $10^9$.

---

## Step-by-Step Dry Run

### Example 2:
- $V = 3$, $S = 2$
- Edges: $(0, 1, 1), (0, 2, 6), (1, 2, 3)$

1. **Initialization**:
   - `dist = [10^9, 10^9, 0]`
   - Priority Queue: `[(0, 2)]`

2. **Step 1: Pop (0, 2)**:
   - Node 2 is finalized with distance `0`.
   - Explore neighbors of 2:
     - Neighbor 0 ($wt = 6$): $0 + 6 = 6 < 10^9 \implies$ `dist[0] = 6`, push `(6, 0)`.
     - Neighbor 1 ($wt = 3$): $0 + 3 = 3 < 10^9 \implies$ `dist[1] = 3`, push `(3, 1)`.
   - Priority Queue: `[(3, 1), (6, 0)]`

3. **Step 2: Pop (3, 1)**:
   - Node 1 is finalized with distance `3`.
   - Explore neighbors of 1:
     - Neighbor 0 ($wt = 1$): $3 + 1 = 4 < 6 \implies$ `dist[0] = 4`, push `(4, 0)`.
     - Neighbor 2 ($wt = 3$): $3 + 3 = 6 \ge 0$ (no update).
   - Priority Queue: `[(4, 0), (6, 0)]`

4. **Step 3: Pop (4, 0)**:
   - Node 0 is finalized with distance `4`.
   - Neighbors: all already optimal.

5. **Step 4: Pop (6, 0)**:
   - Stale entry! $6 > \text{dist}[0] = 4 \implies$ discarded immediately.
   - Priority queue empty.

6. **Final Result**:
   `[dist[0], dist[1], dist[2]] = [4, 3, 0]`.

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}((V + E) \log V)$ | Each vertex is extracted from the priority queue at most once. Each edge triggers at most one heap insertion ($\mathcal{O}(\log V)$). Total time is $\mathcal{O}((V + E) \log V)$. |
| **Space Complexity** | $\mathcal{O}(V + E)$ | $\mathcal{O}(V + E)$ to store the adjacency list. The distance array takes $\mathcal{O}(V)$, and the priority queue holds at most $\mathcal{O}(E)$ elements simultaneously. |

---

## Edge Cases
- **Unreachable Nodes**: Vertices not in the same connected component as $S$ retain $10^9$.
- **Source Vertex Isolation ($E = 0$)**: `dist[S] = 0`; all other vertices are $10^9$.
- **Zero-Weight Edges ($weight = 0$)**: Handled correctly without cycles because distance updates only occur when strictly smaller.
- **Multiple Alternative Paths**: The min-heap always selects the path with minimum total weight.
