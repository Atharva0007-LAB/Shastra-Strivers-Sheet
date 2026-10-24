# Editorial: Shortest Path in Directed Acyclic Graph (DAG)

## Problem Overview
Given a Directed Acyclic Graph (DAG) with $N$ vertices ($0 \dots N - 1$) and $M$ weighted directed edges, find the shortest path distance from the source vertex $0$ to all other vertices. If a vertex is unreachable from $0$, its distance must be reported as `-1`.

---

## Why DAGs Enable $\mathcal{O}(N + M)$ Shortest Paths
In general graphs:
- Dijkstra's algorithm uses a priority queue and runs in $\mathcal{O}((N + M) \log N)$ time.
- Bellman-Ford handles negative weights and runs in $\mathcal{O}(N \cdot M)$ time.

However, in a **Directed Acyclic Graph (DAG)**:
1. Every DAG can be linearly ordered into a **topological sort** such that for every directed edge $u \to v$, vertex $u$ appears before vertex $v$.
2. This implies there are **no back-edges** or loops.
3. When we process vertices in topological order, by the time we process vertex $u$, **all possible incoming paths to $u$ have already been relaxed and finalized**.
4. No future vertex can ever loop back to offer a shorter route to $u$.
5. Therefore, a **single linear pass** over the topological order is sufficient to compute the exact shortest paths to all reachable vertices in optimal $\mathcal{O}(N + M)$ time!

---

## Algorithm Steps

### Step 1: Compute Topological Sort
Using Kahn's Algorithm (BFS) or DFS:
- Compute in-degrees of all $N$ vertices.
- Enqueue all vertices with in-degree 0 into a queue.
- Dequeue vertex $u$, append to `topoOrder`, and decrement in-degrees of all outgoing neighbors $v$.
- When a neighbor's in-degree drops to 0, enqueue it.

### Step 2: Distance Array Initialization
- Create a distance array `dist` of size $N$.
- Initialize `dist[i] = INF` (e.g. $10^{15}$) for all $i \in [0, N - 1]$.
- Set `dist[0] = 0` (the source vertex distance to itself is 0).

### Step 3: Edge Relaxation in Topological Order
- Iterate through each vertex $u$ in `topoOrder`:
  - If `dist[u] != INF` (vertex $u$ is reachable from source $0$):
    - For each directed edge $u \to v$ with weight $wt$:
      $$\text{dist}[v] = \min(\text{dist}[v], \text{dist}[u] + wt)$$

### Step 4: Formatting Unreachable Vertices
- For any vertex $i$ where `dist[i] == INF`, set `dist[i] = -1`.

---

## Step-by-Step Dry Run

### Example 2:
- $N = 6, M = 7$, Source = 0
- Edges:
  - $(0 \to 1, 2), (0 \to 4, 1)$
  - $(4 \to 5, 4), (4 \to 2, 2)$
  - $(1 \to 2, 3)$
  - $(2 \to 3, 6)$
  - $(5 \to 3, 1)$

1. **Topological Order**:
   - One valid topological order is: `[0, 1, 4, 2, 5, 3]`.

2. **Distance Initialization**:
   - `dist = [0, INF, INF, INF, INF, INF]`

3. **Relaxation Pass**:
   - **Process 0** (`dist[0] = 0`):
     - Edge $0 \to 1$ ($wt=2$): `dist[1] = min(INF, 0 + 2) = 2`
     - Edge $0 \to 4$ ($wt=1$): `dist[4] = min(INF, 0 + 1) = 1`
   - **Process 1** (`dist[1] = 2`):
     - Edge $1 \to 2$ ($wt=3$): `dist[2] = min(INF, 2 + 3) = 5`
   - **Process 4** (`dist[4] = 1`):
     - Edge $4 \to 5$ ($wt=4$): `dist[5] = min(INF, 1 + 4) = 5`
     - Edge $4 \to 2$ ($wt=2$): `dist[2] = min(5, 1 + 2) = 3` (shorter!)
   - **Process 2** (`dist[2] = 3`):
     - Edge $2 \to 3$ ($wt=6$): `dist[3] = min(INF, 3 + 6) = 9`
   - **Process 5** (`dist[5] = 5`):
     - Edge $5 \to 3$ ($wt=1$): `dist[3] = min(9, 5 + 1) = 6` (shorter!)
   - **Process 3** (`dist[3] = 6`):
     - No outgoing edges.

4. **Final Distances**:
   `[0, 2, 3, 6, 1, 5]`.

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N + M)$ | Finding the topological order takes $\mathcal{O}(N + M)$. Iterating through vertices in topological order and relaxing each edge takes $\mathcal{O}(N + M)$. |
| **Space Complexity** | $\mathcal{O}(N + M)$ | The adjacency list stores $M$ directed weighted edges. The distance array, in-degree array, and topological queue use $\mathcal{O}(N)$ memory. |

---

## Edge Cases
- **Unreachable Vertices**: Vertices that cannot be reached from source $0$ retain `dist = -1`.
- **Source Node with In-Degree > 0**: Handled correctly; only nodes downstream of source $0$ in the DAG will be updated.
- **Disconnected Vertices ($M = 0$)**: Source $0$ has distance `0`; all other vertices have distance `-1`.
- **Diamond Paths with Shortcuts**: Handled naturally by evaluating all paths prior to finalizing downstream nodes.
