# Editorial: Bellman Ford Algorithm

## 1. Problem Overview
We are given a directed graph with $V$ vertices and $E$ edges. Each edge carries an integer weight $w \in [-1000, 1000]$, which may be negative.
Our objective is to find the shortest path distances from source $S$ to all other vertices.
* If a vertex is unreachable, we report its distance as $10^9$.
* If the graph contains a negative weight cycle reachable from $S$, we output `-1`.

---

## 2. Why Dijkstra Fails on Negative Weights
Dijkstra's greedy choice assumes that once a vertex is extracted from the priority queue, its shortest distance is finalized and cannot be decreased further.
With negative edge weights, this greedy invariant breaks: a longer path with many edges can achieve a lower total cost through negative weights after a vertex has already been finalized.
Furthermore, Dijkstra's algorithm cannot detect negative weight cycles and may enter an infinite loop.

---

## 3. The Bellman-Ford Principle
In any graph without negative weight cycles, the shortest simple path between any pair of vertices visits at most $V$ vertices, meaning it contains at most $V - 1$ edges.

### Edge Relaxation
If we relax every edge in the graph:
$$\text{if } \text{dist}[u] \ne 10^9 \text{ and } \text{dist}[u] + w < \text{dist}[v]: \quad \text{dist}[v] = \text{dist}[u] + w$$
* After $1$ iteration, all shortest paths consisting of $1$ edge are finalized.
* After $2$ iterations, all shortest paths consisting of up to $2$ edges are finalized.
* After $V - 1$ iterations, all shortest simple paths of up to $V - 1$ edges are guaranteed to be finalized!

### Negative Cycle Detection
If we perform a $V$-th iteration:
* If any distance $\text{dist}[v]$ decreases further ($\text{dist}[u] + w < \text{dist}[v]$), the path to $v$ must contain at least $V$ edges.
* By the Pigeonhole Principle, a path with $V$ edges must visit at least one vertex twice, forming a cycle.
* Since traversing this cycle reduces the total distance, the cycle must have a strictly negative total weight!
* Hence, if any edge relaxes on the $V$-th iteration, we report a negative cycle by returning `-1`.

---

## 4. Algorithm Step-by-Step
1. **Initialization**:
   * Create an array `dist` of size $V$ initialized to $10^9$ (`1000000000`).
   * Set $\text{dist}[S] = 0$.
2. **$V - 1$ Iterations of Relaxation**:
   * Repeat $V - 1$ times:
     * Set a boolean flag `updated = false`.
     * For each edge $(u, v, w)$ in `edges`:
       * If $\text{dist}[u] \ne 10^9$ and $\text{dist}[u] + w < \text{dist}[v]$:
         * $\text{dist}[v] = \text{dist}[u] + w$.
         * `updated = true`.
     * **Optimization**: If `updated == false`, no distances changed; we can stop early!
3. **$V$-th Iteration (Cycle Detection)**:
   * For each edge $(u, v, w)$ in `edges`:
     * If $\text{dist}[u] \ne 10^9$ and $\text{dist}[u] + w < \text{dist}[v]$:
       * Return `-1` (negative weight cycle exists).
4. **Return Results**:
   * Return the `dist` array.

---

## 5. Dry Run (Example 1)
* $V = 6, S = 0$
* Edges: $(0,1,5), (1,5,-3), (1,2,-2), (2,4,3), (3,2,6), (3,4,-2), (5,3,1)$

| Iteration | Relaxations Performed | Resulting `dist` Array |
| :--- | :--- | :--- |
| Init | $\text{dist}[0] = 0$, all others $10^9$ | $[0, 10^9, 10^9, 10^9, 10^9, 10^9]$ |
| 1 | $0 \to 1$ ($5$) | $[0, 5, 10^9, 10^9, 10^9, 10^9]$ |
| 2 | $1 \to 5$ ($5 + (-3) = 2$), $1 \to 2$ ($5 + (-2) = 3$) | $[0, 5, 3, 10^9, 10^9, 2]$ |
| 3 | $5 \to 3$ ($2 + 1 = 3$), $2 \to 4$ ($3 + 3 = 6$) | $[0, 5, 3, 3, 6, 2]$ |
| 4 | $3 \to 4$ ($3 + (-2) = 1$) | $[0, 5, 3, 3, 1, 2]$ |
| 5 | No updates occur (`updated = false`) | $[0, 5, 3, 3, 1, 2]$ |
| 6 (Check) | No edges relax | Verified no negative cycle |

Final answer: `0 5 3 3 1 2`.

---

## 6. Complexity Analysis

### Time Complexity
* Each iteration traverses all $E$ edges.
* We perform $V - 1$ relaxation iterations plus $1$ negative cycle detection check.
* Total Time Complexity:
  $$\mathcal{O}(V \times E)$$
  For $V \le 500$ and $E \le 2.5 \times 10^5$, worst-case operations are well within execution bounds and run in under $30\text{ ms}$.

### Space Complexity
* We only store the distance array of size $V$ and the edge list of size $E$.
* Total Auxiliary Space:
  $$\mathcal{O}(V + E)$$
