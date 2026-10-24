# Editorial: Print Shortest Path

## 1. Problem Overview
We are given an undirected, weighted graph with $n$ vertices (numbered $1$ to $n$) and $m$ edges with non-negative edge weights $w \ge 1$.
Our task is to determine the shortest path from vertex $1$ to vertex $n$.
* If vertex $n$ is unreachable from vertex $1$, we output `-1`.
* Otherwise, we output the total weight of this path, followed by the complete vertex sequence from $1$ to $n$:
  $$\text{weight} \quad 1 \quad v_2 \quad v_3 \quad \dots \quad n$$

---

## 2. Intuition & Path Reconstruction
Standard Dijkstra's Algorithm computes the single-source shortest path distances from a source vertex (here, vertex $1$) to all other vertices using a greedy priority queue approach.

To not only compute the minimum distance $\text{dist}[n]$ but also **reconstruct the actual path**, we maintain a predecessor tracking array `parent[]`:
* `parent[i]` records the vertex from which vertex $i$ was relaxed to achieve its current optimal distance $\text{dist}[i]$.
* Initially, `parent[i] = i` for all $1 \le i \le n$.
* When relaxing an edge $(u, v)$ with weight $w$:
  $$\text{if } \text{dist}[u] + w < \text{dist}[v]: \quad \text{dist}[v] = \text{dist}[u] + w, \quad \text{parent}[v] = u$$
  We then push $(v, \text{dist}[v])$ into our min-heap priority queue.

### Backtracking to Reconstruct the Path
Once Dijkstra's algorithm finishes:
1. If $\text{dist}[n] = \infty$, vertex $n$ was never reached. We immediately return `-1`.
2. Otherwise, we start from destination vertex $n$ and trace backward:
   $$\text{curr} = n$$
   $$\text{while parent}[\text{curr}] \ne \text{curr}:$$
   $$\quad \text{path.append}(\text{curr})$$
   $$\quad \text{curr} = \text{parent}[\text{curr}]$$
   $$\text{path.append}(1)$$
3. Reversing the reconstructed list yields the sequence from $1$ to $n$: $[1, v_2, \dots, n]$.
4. The final output prepends $\text{dist}[n]$:
   $$\text{dist}[n] \quad 1 \quad v_2 \quad \dots \quad n$$

---

## 3. Algorithm Step-by-Step
1. **Graph Representation**:
   Construct an adjacency list `adj` where each node $u$ stores pairs $(v, w)$ for every edge between $u$ and $v$.
2. **Data Structures**:
   * `dist[]`: an array of size $n + 1$ initialized to $\infty$ (`Long.MAX_VALUE / 2`), with $\text{dist}[1] = 0$.
   * `parent[]`: an array of size $n + 1$ where `parent[i] = i`.
   * `pq`: a Min-Heap Priority Queue storing `(vertex, distance)` ordered by distance ascending.
3. **Dijkstra Traversal**:
   * Insert $(1, 0)$ into `pq`.
   * While `pq` is not empty:
     * Pop $(u, d)$.
     * If $d > \text{dist}[u]$, continue (stale queue entry pruning).
     * If $u = n$, we can stop early, as the shortest distance to $n$ is finalized!
     * For each neighbor $(v, w)$ of $u$:
       * If $\text{dist}[u] + w < \text{dist}[v]$:
         * Update $\text{dist}[v] = \text{dist}[u] + w$.
         * Update $\text{parent}[v] = u$.
         * Push $(v, \text{dist}[v])$ into `pq`.
4. **Result Formation**:
   * If $\text{dist}[n] = \infty$, print `-1`.
   * Else backtrack using `parent[]`, reverse the list, prepend $\text{dist}[n]$, and print space-separated.

---

## 4. Dry Run (Example 1)
**Input**:
* $n = 5, m = 6$
* Edges: $(1,2,2), (2,5,5), (2,3,4), (1,4,1), (4,3,3), (3,5,1)$

| Step | Current Node $u$ | $d$ | Relaxation Updates | Priority Queue State |
| :--- | :--- | :--- | :--- | :--- |
| Init | - | - | $\text{dist}[1]=0$, all others $\infty$ | $[(1, 0)]$ |
| 1 | $1$ | $0$ | $\text{dist}[2]=2, \text{parent}[2]=1$; $\text{dist}[4]=1, \text{parent}[4]=1$ | $[(4, 1), (2, 2)]$ |
| 2 | $4$ | $1$ | $\text{dist}[3]=1+3=4, \text{parent}[3]=4$ | $[(2, 2), (3, 4)]$ |
| 3 | $2$ | $2$ | $\text{dist}[5]=2+5=7, \text{parent}[5]=2$; $2 \to 3$ cost $6 > 4$ (no update) | $[(3, 4), (5, 7)]$ |
| 4 | $3$ | $4$ | $\text{dist}[5]=\min(7, 4+1=5)=5, \text{parent}[5]=3$ | $[(5, 5), (5, 7)]$ |
| 5 | $5$ | $5$ | Destination reached! Optimal distance is $5$. | Done |

**Path Backtracking**:
* $\text{curr} = 5 \implies \text{parent}[5] = 3$
* $\text{curr} = 3 \implies \text{parent}[3] = 4$
* $\text{curr} = 4 \implies \text{parent}[4] = 1$
* $\text{curr} = 1 \implies \text{parent}[1] = 1$ (stop)
* Reversed path: $1 \to 4 \to 3 \to 5$.
* Output: `5 1 4 3 5`.

---

## 5. Complexity Analysis

### Time Complexity
* Graph construction: $\mathcal{O}(m)$.
* Priority queue operations: Each vertex is popped at most once with its optimal distance, and each edge is examined at most twice (undirected). Each PQ insertion takes $\mathcal{O}(\log n)$.
* Total Time Complexity:
  $$\mathcal{O}((n + m) \log n)$$
  Given $n \le 10^4$ and $m \le 2 \times 10^4$, this executes in under $20\text{ ms}$, well within the $2.0\text{ s}$ time limit.

### Space Complexity
* Adjacency list: $\mathcal{O}(n + m)$ entries.
* Distance and parent arrays: $\mathcal{O}(n)$ space.
* Priority Queue: at most $\mathcal{O}(m)$ entries in the worst case.
* Total Auxiliary Space:
  $$\mathcal{O}(n + m)$$
