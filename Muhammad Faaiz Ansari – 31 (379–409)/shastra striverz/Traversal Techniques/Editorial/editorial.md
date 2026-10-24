# Editorial: Traversal Techniques

## Difficulty
Medium

## Prerequisites
- Graph Representation (Adjacency Lists)
- Queue Data Structure (FIFO)
- Recursion / Call Stack & Explicit Stacks (LIFO)
- Visited Array State Management

---

## Problem Statement
You are given an undirected connected graph containing $V$ vertices numbered from $0$ to $V-1$ and $E$ edges. 
The task is to generate both:
1. **Depth First Search (DFS)** traversal starting from vertex $0$.
2. **Breadth First Search (BFS)** traversal starting from vertex $0$.

Adjacent vertices must be explored in the exact sequence they are added to the adjacency list from the input edges.

---

## Detailed Intuition & Approaches

### 1. Graph Representation
Because edges are given as pairs $[u, v]$, we construct an adjacency list `adj` of size $V$, where `adj[i]` is a list of integers.
For each edge $[u, v]$:
```java
adj[u].add(v);
adj[v].add(u);
```
Preserving the order of insertion guarantees that neighbor exploration follows the required order.

---

### 2. Depth First Search (DFS)

#### Concept
DFS dives as deep as possible down each branch before backtracking. Starting at node $0$, we mark it visited, record it, and recursively visit its first unvisited neighbor. When all neighbors of a node have been visited, the algorithm backtracks to the previous node and continues with the next unvisited neighbor.

#### Algorithm Steps
1. Maintain a boolean array `visited` of size $V$, initially all `false`.
2. Create a list `dfsOrder` to store the traversal.
3. Start recursion at node $0$:
   - Set `visited[node] = true`.
   - Add `node` to `dfsOrder`.
   - For each neighbor `nbr` in `adj[node]`:
     - If `!visited[nbr]`, call `dfs(nbr)`.
4. Return `dfsOrder`.

*Note on Recursion Depth:* Since $V \le 10^4$, recursion depth can reach $10^4$ on linear graphs. Java default thread stack size (often 1MB) can comfortably handle $10^4$ frames without stack overflow, or an explicit stack can be used.

---

### 3. Breadth First Search (BFS)

#### Concept
BFS explores vertices level by level (in concentric layers starting from node $0$). It uses a **Queue (FIFO)**: nodes are processed in the exact order they are discovered, guaranteeing all distance-$d$ vertices are visited before any distance-$(d+1)$ vertex.

#### Algorithm Steps
1. Maintain a boolean array `visited` of size $V$, initially all `false`.
2. Create an empty `Queue<Integer>` and a list `bfsOrder`.
3. Mark `visited[0] = true` and enqueue $0$.
4. While the queue is not empty:
   - Dequeue `curr = queue.poll()`.
   - Add `curr` to `bfsOrder`.
   - For each neighbor `nbr` in `adj[curr]`:
     - If `!visited[nbr]`:
       - Mark `visited[nbr] = true` immediately upon enqueuing (to prevent duplicate entries into the queue).
       - Enqueue `nbr`.
5. Return `bfsOrder`.

---

## Step-by-Step Dry Run (Example 1)

Input: $V = 5$, edges = `[[0, 2], [0, 3], [0, 1], [2, 4]]`

Adjacency List:
- `adj[0] = [2, 3, 1]`
- `adj[1] = [0]`
- `adj[2] = [0, 4]`
- `adj[3] = [0]`
- `adj[4] = [2]`

### DFS Execution:
| Step | Action | Visited Set | Result List |
|---|---|---|---|
| 1 | Visit `0` | `{0}` | `[0]` |
| 2 | First unvisited neighbor of `0` is `2` $\to$ DFS(2) | `{0, 2}` | `[0, 2]` |
| 3 | In `2`, `0` is visited. Next unvisited is `4` $\to$ DFS(4) | `{0, 2, 4}` | `[0, 2, 4]` |
| 4 | In `4`, neighbor `2` is visited. Backtrack to `2`. | `{0, 2, 4}` | `[0, 2, 4]` |
| 5 | In `2`, no more neighbors. Backtrack to `0`. | `{0, 2, 4}` | `[0, 2, 4]` |
| 6 | In `0`, next unvisited is `3` $\to$ DFS(3) | `{0, 2, 4, 3}` | `[0, 2, 4, 3]` |
| 7 | In `3`, neighbor `0` is visited. Backtrack to `0`. | `{0, 2, 4, 3}` | `[0, 2, 4, 3]` |
| 8 | In `0`, next unvisited is `1` $\to$ DFS(1) | `{0, 2, 4, 3, 1}` | `[0, 2, 4, 3, 1]` |
| 9 | In `1`, neighbor `0` is visited. Backtrack to `0`. Complete. | `{0, 2, 4, 3, 1}` | `[0, 2, 4, 3, 1]` |

**DFS Result:** `0 2 4 3 1`

### BFS Execution:
| Step | Current Node | Queue State | Visited Set | Result List |
|---|---|---|---|---|
| Init | - | `[0]` | `{0}` | `[]` |
| 1 | Pop `0` | `[]` $\to$ Push `2, 3, 1` $\to$ `[2, 3, 1]` | `{0, 2, 3, 1}` | `[0]` |
| 2 | Pop `2` | `[3, 1]` $\to$ Push unvisited neighbor `4` $\to$ `[3, 1, 4]` | `{0, 2, 3, 1, 4}` | `[0, 2]` |
| 3 | Pop `3` | `[1, 4]` $\to$ No unvisited neighbors | `{0, 2, 3, 1, 4}` | `[0, 2, 3]` |
| 4 | Pop `1` | `[4]` $\to$ No unvisited neighbors | `{0, 2, 3, 1, 4}` | `[0, 2, 3, 1]` |
| 5 | Pop `4` | `[]` $\to$ No unvisited neighbors | `{0, 2, 3, 1, 4}` | `[0, 2, 3, 1, 4]` |

**BFS Result:** `0 2 3 1 4`

---

## Complexity Analysis

- **Time Complexity:**
  - Graph Construction: $\mathcal{O}(V + E)$ to allocate adjacency lists and add edges.
  - DFS: Every vertex is visited once, and each undirected edge is scanned twice (once from each endpoint): $\mathcal{O}(V + 2E) = \mathcal{O}(V + E)$.
  - BFS: Every vertex is enqueued/dequeued once, and each edge is examined twice: $\mathcal{O}(V + 2E) = \mathcal{O}(V + E)$.
  - Total Time: $\mathcal{O}(V + E)$. With $V, E \le 10^4$, total operations $\approx 2 \times 10^4$, completing in $< 0.05$s.

- **Space Complexity:**
  - Adjacency List: $\mathcal{O}(V + 2E)$ storage.
  - Visited Arrays: $\mathcal{O}(V)$.
  - BFS Queue & DFS Recursion Stack: $\mathcal{O}(V)$.
  - Total Space: $\mathcal{O}(V + E)$.
