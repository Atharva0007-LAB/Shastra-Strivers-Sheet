# Editorial: Shortest Distance in a Binary Maze

## 1. Problem Overview
We are given an $n \times m$ grid of $0$s and $1$s, alongside source coordinates $(src_r, src_c)$ and destination coordinates $(dst_r, dst_c)$. We may move in 4 cardinal directions (up, down, left, right) between adjacent cells containing $1$.
Our objective is to compute the minimum number of steps to travel from the source cell to the destination cell, returning `-1` if the destination cannot be reached.

---

## 2. Intuition & Approach
Since every valid transition between adjacent traversable cells costs exactly $1$ unit of distance, this grid problem is an unweighted shortest path problem on an implicit graph where:
* Vertices $V$ are all cells $(r, c)$ where $\text{grid}[r][c] = 1$.
* Edges $E$ connect adjacent cells with $\text{grid}[r'][c'] = 1$.

### Why Queue BFS over Dijkstra?
* In a graph where every edge has identical unit weight $w = 1$, standard **Breadth-First Search (BFS)** using a simple First-In-First-Out (FIFO) queue guarantees that cells are discovered in non-decreasing order of their shortest distance from the source.
* BFS avoids the $\mathcal{O}(\log (n \times m))$ priority queue overhead of Dijkstra's algorithm, completing in pure linear time $\mathcal{O}(n \times m)$.

---

## 3. Algorithm Step-by-Step
1. **Edge Case**:
   If $src_r = dst_r$ and $src_c = dst_c$, return $0$ immediately.
2. **Data Structures**:
   * A 2D distance array `dist[n][m]` initialized to $\infty$ (or `-1` to represent unvisited).
   * A queue storing cell coordinates $(r, c)$.
3. **Initialization**:
   * Set $\text{dist}[src_r][src_c] = 0$.
   * Enqueue $(src_r, src_c)$.
4. **BFS Traversal**:
   While the queue is not empty:
   * Dequeue $(r, c)$.
   * If $(r, c) = (dst_r, dst_c)$, return $\text{dist}[r][c]$.
   * For each of the 4 directional offsets $(-1, 0), (1, 0), (0, -1), (0, 1)$:
     * Calculate neighbor coordinates $(nr, nc) = (r + dr, c + dc)$.
     * Check valid boundaries: $0 \le nr < n$ and $0 \le nc < m$.
     * Check traversability: $\text{grid}[nr][nc] == 1$.
     * If $\text{dist}[r][c] + 1 < \text{dist}[nr][nc]$:
       * Set $\text{dist}[nr][nc] = \text{dist}[r][c] + 1$.
       * If $(nr, nc) = (dst_r, dst_c)$, we can even terminate early and return $\text{dist}[nr][nc]$.
       * Enqueue $(nr, nc)$.
5. **Termination**:
   If the queue becomes empty without reaching $(dst_r, dst_c)$, return `-1`.

---

## 4. Dry Run (Example 1)
* Grid $5 \times 4$, Source $(0, 1)$, Destination $(2, 2)$

| Step | Current Cell $(r, c)$ | Distance | Neighbors Visited | Queue State |
| :--- | :--- | :--- | :--- | :--- |
| Init | - | - | $(0, 1)$ set to $0$ | $[(0, 1)]$ |
| 1 | $(0, 1)$ | $0$ | $(0, 0)$ dist 1, $(0, 2)$ dist 1, $(1, 1)$ dist 1 | $[(0, 0), (0, 2), (1, 1)]$ |
| 2 | $(0, 0)$ | $1$ | $(1, 0)$ dist 2 | $[(0, 2), (1, 1), (1, 0)]$ |
| 3 | $(0, 2)$ | $1$ | $(0, 3)$ dist 2 | $[(1, 1), (1, 0), (0, 3)]$ |
| 4 | $(1, 1)$ | $1$ | $(2, 1)$ dist 2 | $[(1, 0), (0, 3), (2, 1)]$ |
| 5 | $(1, 0)$ | $2$ | $(2, 0)$ dist 3 | $[(0, 3), (2, 1), (2, 0)]$ |
| 6 | $(0, 3)$ | $2$ | $(1, 3)$ dist 3 | $[(2, 1), (2, 0), (1, 3)]$ |
| 7 | $(2, 1)$ | $2$ | $(2, 2)$ (destination!) dist $2 + 1 = 3$ | Destination reached! |

Return $3$.

---

## 5. Complexity Analysis

### Time Complexity
* Each cell $(r, c)$ in the $n \times m$ grid enters the BFS queue at most once.
* For each cell, at most $4$ neighbors are evaluated in $\mathcal{O}(1)$ time.
* Total Time Complexity:
  $$\mathcal{O}(n \times m)$$
  For $n, m \le 500$, $n \times m \le 2.5 \times 10^5$, executing in under $30\text{ ms}$.

### Space Complexity
* Distance array: $\mathcal{O}(n \times m)$ space.
* Queue: at most $\mathcal{O}(n \times m)$ elements.
* Total Auxiliary Space:
  $$\mathcal{O}(n \times m)$$
