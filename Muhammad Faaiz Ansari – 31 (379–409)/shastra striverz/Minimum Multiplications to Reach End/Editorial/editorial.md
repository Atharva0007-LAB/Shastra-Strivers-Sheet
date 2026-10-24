# Editorial: Minimum Multiplications to Reach End

## 1. Problem Overview
We are given two integers `start` and `end`, and an array `arr` of multipliers.
At each step, we multiply our current value by any element from `arr` and take the result modulo $100000$:
$$\text{next} = (\text{current} \times x) \pmod{100000}, \quad x \in \text{arr}$$
We must find the minimum number of multiplications to transform `start` into `end`, or return `-1` if `end` is unreachable.

---

## 2. Graph Modeling & Intuition
This problem can be directly modeled as a **shortest path problem on an unweighted directed graph**:
* **Vertices**: The state space consists of all integers modulo $100000$:
  $$V = \{0, 1, 2, \dots, 99999\}$$
  Thus, there are at most $10^5$ possible unique nodes.
* **Edges**: From each state $u$, there are directed edges to:
  $$v = (u \times x) \pmod{100000} \quad \text{for each } x \in \text{arr}$$
* **Edge Weights**: Every multiplication step takes exactly $1$ operation (unit weight).

### Why Breadth-First Search (BFS)?
Because all edges have uniform weight $1$, standard **BFS** with a FIFO queue guarantees that we explore all states reachable in $k$ steps before considering any state at step $k + 1$.
* Using an array `dist[100000]` initialized to `-1`, we record the minimum steps to reach each remainder.
* When `dist[v]` is already set, we know an equal or shorter path has already discovered $v$; we skip it to prevent cycles and redundant transitions.

---

## 3. Algorithm Step-by-Step
1. **Edge Case**:
   If $\text{start} = \text{end}$, return $0$ immediately.
2. **Deduplication**:
   Remove duplicate values from `arr` (or filter out factors that are congruent modulo $100000$) to minimize branching factor.
3. **Data Structures**:
   * An integer distance array `dist` of size $100000$ initialized to `-1`.
   * A FIFO queue storing state values $u \in [0, 99999]$.
4. **BFS Initialization**:
   * Set $\text{dist}[\text{start}] = 0$.
   * Enqueue `start`.
5. **Level-Order Expansion**:
   While `queue` is not empty:
   * Pop $u$.
   * For each multiplier $x \in \text{arr}$:
     * Calculate $v = (u \times x) \pmod{100000}$.
     * If $\text{dist}[v] = -1$:
       * $\text{dist}[v] = \text{dist}[u] + 1$.
       * If $v = \text{end}$, return $\text{dist}[v]$ (early exit).
       * Enqueue $v$.
6. **Termination**:
   If the queue becomes empty without reaching `end`, return `-1`.

---

## 4. Dry Run (Example 1)
* $\text{start} = 3$, $\text{end} = 30$, $\text{arr} = [2, 5, 7]$
* $\text{MOD} = 100000$

| Step | State $u$ | Multiplier $x$ | Next State $v = (u \cdot x) \bmod 100000$ | $\text{dist}[v]$ | Queue |
| :--- | :--- | :--- | :--- | :--- | :--- |
| Init | - | - | $\text{start} = 3$ | $\text{dist}[3] = 0$ | $[3]$ |
| 1 | $3$ | $2$ | $(3 \times 2) \bmod 100000 = 6$ | $0 + 1 = 1$ | $[6]$ |
| 1 | $3$ | $5$ | $(3 \times 5) \bmod 100000 = 15$ | $0 + 1 = 1$ | $[6, 15]$ |
| 1 | $3$ | $7$ | $(3 \times 7) \bmod 100000 = 21$ | $0 + 1 = 1$ | $[6, 15, 21]$ |
| 2 | $6$ | $2$ | $(6 \times 2) \bmod 100000 = 12$ | $1 + 1 = 2$ | $[15, 21, 12]$ |
| 2 | $6$ | $5$ | $(6 \times 5) \bmod 100000 = 30$ | $1 + 1 = 2$ | **Reached $\text{end} = 30$!** |

The search terminates and returns $2$.

---

## 5. Complexity Analysis

### Time Complexity
* There are at most $M = 10^5$ distinct vertices.
* Each vertex is pushed to the queue at most once.
* From each vertex, we examine at most $n$ outgoing edges (where $n = |\text{arr}|$).
* Total Time Complexity:
  $$\mathcal{O}(M \times n) = \mathcal{O}(10^5 \times n)$$
  In practice, pruning duplicate multipliers and early termination on reaching `end` results in execution times under $15\text{ ms}$.

### Space Complexity
* Distance array: fixed size $10^5$ integers.
* Queue: at most $10^5$ elements.
* Total Auxiliary Space:
  $$\mathcal{O}(M) = \mathcal{O}(10^5) \approx \text{a few hundred kilobytes}$$
