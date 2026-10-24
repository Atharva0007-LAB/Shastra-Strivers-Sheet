# Editorial: Path With Minimum Effort

## 1. Problem Overview
We are given an $R \times C$ matrix `heights` where each cell represents an elevation.
We start at $(0, 0)$ and want to reach $(R - 1, C - 1)$ moving in four cardinal directions (up, down, left, right).
The **effort** of a route is defined as the maximum absolute difference between consecutive cell heights along the path:
$$\text{Effort}(\text{route}) = \max_{(u, v) \in \text{route}} |\text{heights}[u] - \text{heights}[v]|$$
We must determine the **minimum effort** across all possible routes from $(0, 0)$ to $(R - 1, C - 1)$ (a classic minimax path problem).

---

## 2. Intuition: Modified Dijkstra's Algorithm
In standard shortest path problems, path length is the *sum* of edge weights:
$$\text{dist}[v] = \min(\text{dist}[v], \text{dist}[u] + w(u, v))$$

In this minimax path problem, the cost of extending a path from cell $(r, c)$ to an adjacent cell $(nr, nc)$ is the maximum of:
1. The effort already required to reach $(r, c)$, and
2. The step difference between $(r, c)$ and $(nr, nc)$: $|\text{heights}[r][c] - \text{heights}[nr][nc]|$.

Thus, the modified relaxation condition becomes:
$$\text{newEffort} = \max(\text{effort}[r][c], |\text{heights}[r][c] - \text{heights}[nr][nc]|)$$
$$\text{if } \text{newEffort} < \text{effort}[nr][nc]: \quad \text{effort}[nr][nc] = \text{newEffort}$$

Because edge transitions are non-negative and subpaths exhibit optimal substructure under the $\max$ operator, **Dijkstra's Greedy Priority Queue** guarantees that when a cell is popped from the min-heap, its minimal effort has been finalized.

---

## 3. Algorithm Step-by-Step
1. **Edge Case**:
   If $R = 1$ and $C = 1$, the hiker is already at the destination with zero moves. Return $0$.
2. **Data Structures**:
   * A 2D array `effort[R][C]` initialized to $\infty$, with $\text{effort}[0][0] = 0$.
   * A Min-Heap Priority Queue storing tuples $(e, r, c)$ ordered by effort $e$ ascending.
3. **Traversal**:
   * Insert $(0, 0, 0)$ into `pq`.
   * While `pq` is not empty:
     * Pop $(e, r, c)$.
     * If $e > \text{effort}[r][c]$, skip this stale entry.
     * If $r = R - 1$ and $c = C - 1$, return $e$ immediately!
     * For each of the $4$ neighbors $(nr, nc) \in \{(r-1, c), (r+1, c), (r, c-1), (r, c+1)\}$ within bounds:
       * Calculate step difference: $\Delta = |\text{heights}[r][c] - \text{heights}[nr][nc]|$.
       * Calculate candidate effort: $\text{cand} = \max(e, \Delta)$.
       * If $\text{cand} < \text{effort}[nr][nc]$:
         * Update $\text{effort}[nr][nc] = \text{cand}$.
         * Push $(\text{cand}, nr, nc)$ into `pq`.
4. **Result**:
   Return $\text{effort}[R - 1][C - 1]$.

---

## 4. Dry Run (Example 1)
**Input**:
$$\begin{pmatrix} 1 & 2 & 2 \\ 3 & 8 & 2 \\ 5 & 3 & 5 \end{pmatrix}$$
* Start at $(0, 0)$ with effort $0$.

| Step | Current Cell $(r, c)$ | Current Effort | Neighbors Evaluated | Next Heap Elements |
| :--- | :--- | :--- | :--- | :--- |
| Init | - | - | $\text{effort}[0][0] = 0$ | $[(0, 0, 0)]$ |
| 1 | $(0, 0)$ | $0$ | $(0, 1): \max(0, \|1-2\|) = 1$; $(1, 0): \max(0, \|1-3\|) = 2$ | $[(1, 0, 1), (2, 1, 0)]$ |
| 2 | $(0, 1)$ | $1$ | $(0, 2): \max(1, \|2-2\|) = 1$; $(1, 1): \max(1, \|2-8\|) = 6$ | $[(1, 0, 2), (2, 1, 0), (6, 1, 1)]$ |
| 3 | $(0, 2)$ | $1$ | $(1, 2): \max(1, \|2-2\|) = 1$ | $[(1, 1, 2), (2, 1, 0), (6, 1, 1)]$ |
| 4 | $(1, 2)$ | $1$ | $(2, 2): \max(1, \|2-5\|) = 3$ | $[(2, 1, 0), (3, 2, 2), (6, 1, 1)]$ |
| 5 | $(1, 0)$ | $2$ | $(2, 0): \max(2, \|3-5\|) = 2$; $(1, 1): \max(2, \|3-8\|) = 5 < 6$ | $[(2, 2, 0), (3, 2, 2), (5, 1, 1)]$ |
| 6 | $(2, 0)$ | $2$ | $(2, 1): \max(2, \|5-3\|) = 2$ | $[(2, 2, 1), (3, 2, 2), (5, 1, 1)]$ |
| 7 | $(2, 1)$ | $2$ | $(2, 2): \max(2, \|3-5\|) = 2 < 3$ | $[(2, 2, 2), (5, 1, 1)]$ |
| 8 | $(2, 2)$ | $2$ | Destination cell reached! | **Return 2** |

---

## 5. Complexity Analysis

### Time Complexity
* Each cell has at most $4$ outgoing edges.
* The total number of vertices is $V = R \times C$ and edges $E \le 4 \times R \times C$.
* Each priority queue insertion takes $\mathcal{O}(\log(R \times C))$.
* Total Time Complexity:
  $$\mathcal{O}(R \cdot C \log(R \cdot C))$$
  With $R, C \le 100$, $R \cdot C \le 10^4$. This executes in fewer than $150,000$ operations ($\approx 5\text{ ms}$), far within the standard $2.0\text{ s}$ limit.

### Space Complexity
* 2D `effort[][]` matrix: $\mathcal{O}(R \times C)$ space.
* Min-Heap priority queue: at most $\mathcal{O}(R \times C)$ active entries.
* Total Auxiliary Space:
  $$\mathcal{O}(R \times C)$$
