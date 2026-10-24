# Editorial: Shortest Path in Undirected Graph with Unit Weights

## Problem Overview
Given an undirected graph with $N$ vertices ($0 \dots N - 1$) and $M$ edges, where every edge has a uniform weight of $1$, find the shortest distance from the source vertex $0$ to every vertex in the graph. If a vertex is unreachable from $0$, return `-1` for that vertex.

---

## Why BFS Guarantees Shortest Path for Unit-Weight Graphs
In a general weighted graph, finding shortest paths requires Dijkstra's Algorithm ($\mathcal{O}((N + M) \log N)$). 

However, when all edges have an identical weight of **1 (unit weight)**:
1. **Level-by-Level Exploration**: Breadth-First Search (BFS) discovers vertices in strictly increasing order of their distance from the starting node:
   - First, all vertices at distance 0 (the source node itself).
   - Next, all vertices at distance 1 (immediate neighbors of source).
   - Then, all vertices at distance 2, and so forth.
2. **First Visit Optimality**: Because vertices are visited in non-decreasing order of distance, the first time an unvisited vertex $v$ is encountered from vertex $u$, the path from the source to $v$ via $u$ is guaranteed to be the shortest possible path:
   $$\text{dist}[v] = \text{dist}[u] + 1$$
3. **Queue Simplicity**: A simple First-In First-Out (FIFO) queue naturally maintains this monotonic distance ordering without requiring a priority queue.
4. **Linear Time**: This allows solving the problem in optimal $\mathcal{O}(N + M)$ time and $\mathcal{O}(N + M)$ space.

---

## Algorithm Steps

### Step 1: Adjacency List Construction
- Build an undirected adjacency list `adj` of size $N$.
- For each undirected edge $(u, v)$:
  - `adj[u].add(v)`
  - `adj[v].add(u)`

### Step 2: Distance Array Initialization
- Create an integer array `dist` of size $N$ and fill all entries with `-1`.
- Set `dist[0] = 0` (distance from the source node to itself is 0).

### Step 3: Queue-based BFS Traversal
- Initialize an array-based queue with source vertex `0`.
- While the queue is not empty:
  - Dequeue vertex $u$.
  - For each neighbor $v$ of $u$:
    - If `dist[v] == -1` (vertex $v$ has not yet been visited):
      - Update `dist[v] = dist[u] + 1`.
      - Enqueue $v$.

### Step 4: Output
- Any vertex that remained unvisited will naturally retain `dist[i] == -1`.
- Print `dist[0], dist[1], ..., dist[N - 1]` separated by spaces.

---

## Step-by-Step Dry Run

### Example 1:
- $N = 9, M = 10$, Source = 0
- Edges: $(0, 1), (0, 3), (3, 4), (4, 5), (5, 6), (1, 2), (2, 6), (6, 7), (7, 8), (6, 8)$

1. **Initialization**:
   - `dist = [0, -1, -1, -1, -1, -1, -1, -1, -1]`
   - Queue: `[0]`

2. **BFS Expansion**:
   - **Pop 0** (`dist[0] = 0`):
     - Neighbors: `1, 3`
     - `dist[1] = 1`, Enqueue `1`
     - `dist[3] = 1`, Enqueue `3`
     - Queue: `[1, 3]`

   - **Pop 1** (`dist[1] = 1`):
     - Neighbor `2`: `dist[2] = 2`, Enqueue `2`
     - Queue: `[3, 2]`

   - **Pop 3** (`dist[3] = 1`):
     - Neighbor `4`: `dist[4] = 2`, Enqueue `4`
     - Queue: `[2, 4]`

   - **Pop 2** (`dist[2] = 2`):
     - Neighbor `6`: `dist[6] = 3`, Enqueue `6`
     - Queue: `[4, 6]`

   - **Pop 4** (`dist[4] = 2`):
     - Neighbor `5`: `dist[5] = 3`, Enqueue `5`
     - Queue: `[6, 5]`

   - **Pop 6** (`dist[6] = 3`):
     - Neighbor `7`: `dist[7] = 4`, Enqueue `7`
     - Neighbor `8`: `dist[8] = 4`, Enqueue `8`
     - (Notice: from 5, edge $(5, 6)$ is ignored because 6 was already reached at distance 3 via 2!)
     - Queue: `[5, 7, 8]`

   - **Pop 5** (`dist[5] = 3`):
     - All neighbors already visited.
     - Queue: `[7, 8]`

   - **Pop 7, 8**:
     - All neighbors already visited.

3. **Final Distances**:
   `[0, 1, 2, 1, 2, 3, 3, 4, 4]`.

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N + M)$ | Building the adjacency list takes $\mathcal{O}(N + M)$. In the BFS traversal, each vertex is enqueued and dequeued at most once ($\mathcal{O}(N)$), and each edge is inspected twice ($\mathcal{O}(M)$). Total time is strictly $\mathcal{O}(N + M)$. |
| **Space Complexity** | $\mathcal{O}(N + M)$ | Storing the graph as an adjacency list requires $\mathcal{O}(N + M)$ space. The distance array and the array-based BFS queue take $\mathcal{O}(N)$ space. |

---

## Edge Cases
- **Completely Isolated Source ($M = 0$)**: `dist[0] = 0`; all other vertices retain `-1`.
- **Single Vertex ($N = 1, M = 0$)**: Trivial base case; output: `0`.
- **Multiple Disconnected Components**: Any components not connected to source $0$ retain `-1`.
- **Long Linear Chain ($0 - 1 - 2 - \dots - N-1$)**: Handled without recursion, preventing stack overflow on large inputs ($N = 10^4$).
- **Dense Graphs and Multiple Alternative Paths**: BFS automatically captures the minimal-hop path.
