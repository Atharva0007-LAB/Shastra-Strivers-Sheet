# Editorial: Cheapest Flight Within K Stops

## 1. Problem Overview
We are given $n$ cities and $m$ directed flights with associated ticket prices. We want to find the minimum cost to travel from city $src$ to city $dst$ using **at most $K$ stops** (which means at most $K + 1$ flight segments/edges). If no such path exists, return `-1`.

---

## 2. Why Standard Dijkstra Keyed by Cost Fails
In classical Dijkstra's Algorithm, a priority queue extracts the path with the smallest total accumulated cost:
* A path $P_1$ with cost $150$ and $4$ stops will be prioritized over path $P_2$ with cost $200$ and $1$ stop.
* If $P_1$ reaches a node $u$ first, it might update $\text{dist}[u] = 150$. If the stop limit is $K = 2$, $P_1$ may exceed the allowed stops later, while $P_2$ (which had only $1$ stop) could have successfully reached the destination within the limit!
* Therefore, greedy prioritization purely by cost can discard valid, stop-constrained paths prematurely.

---

## 3. The Core Intuition: Prioritize by Number of Stops
Notice that every flight segment increases the number of intermediate stops by exactly $1$:
* Step 0: paths using 0 stops (direct flights from $src$).
* Step 1: paths using 1 stop.
* ...
* Step $K$: paths using $K$ stops.

Because the number of stops increases monotonically by $1$ at each step, a simple **FIFO Queue** naturally processes paths in non-decreasing order of stops:
* We store tuples `(stops, node, cost)` in a standard BFS queue.
* We maintain an array `cost[n]` tracking the minimum cost to reach each city found so far within the current stop bound.
* When evaluating edge $(u, v, \text{price})$:
  $$\text{if } \text{cost} + \text{price} < \text{dist}[v] \text{ and } \text{stops} \le K:$$
  $$\text{dist}[v] = \text{cost} + \text{price}$$
  $$\text{queue.push}((\text{stops} + 1, v, \text{dist}[v]))$$
* If $\text{stops} > K$, we do not push further transitions from that node.

This BFS approach runs in $\mathcal{O}(K \cdot m)$ time without any heap overhead!

---

## 4. Algorithm Step-by-Step
1. **Edge Case**:
   If $src = dst$, return $0$.
2. **Graph Construction**:
   Build an adjacency list `adj` where each node $u$ stores pairs $(v, \text{price})$.
3. **Data Structures**:
   * An array `dist[n]` initialized to $\infty$, with $\text{dist}[src] = 0$.
   * A FIFO queue storing elements $(\text{stops}, u, \text{cost})$.
4. **BFS Traversal**:
   * Enqueue $(0, src, 0)$.
   * While `queue` is not empty:
     * Pop $(\text{stops}, u, c)$.
     * If $\text{stops} > K$, continue (stop limit exceeded).
     * For each flight $(v, \text{price})$ from $u$:
       * If $c + \text{price} < \text{dist}[v]$:
         * $\text{dist}[v] = c + \text{price}$.
         * Enqueue $(\text{stops} + 1, v, \text{dist}[v])$.
5. **Result**:
   If $\text{dist}[dst] = \infty$, return `-1`; otherwise return $\text{dist}[dst]$.

---

## 5. Dry Run (Example 1)
* $n = 4$, $src = 0, dst = 3, K = 1$
* Flights: $(0, 1, 100), (1, 2, 100), (2, 0, 100), (1, 3, 600), (2, 3, 200)$

| Step | Current State (stops, u, cost) | Action & Transitions | Queue Contents | $\text{dist}$ Array |
| :--- | :--- | :--- | :--- | :--- |
| Init | - | $\text{dist}[0] = 0$, all others $\infty$ | $[(0, 0, 0)]$ | $[0, \infty, \infty, \infty]$ |
| 1 | $(0, 0, 0)$ | Relax $0 \to 1$: $\text{dist}[1] = 100$ | $[(1, 1, 100)]$ | $[0, 100, \infty, \infty]$ |
| 2 | $(1, 1, 100)$ | Relax $1 \to 2$: $\text{dist}[2] = 200$; Relax $1 \to 3$: $\text{dist}[3] = 700$ | $[(2, 2, 200), (2, 3, 700)]$ | $[0, 100, 200, 700]$ |
| 3 | $(2, 2, 200)$ | $\text{stops} = 2 > K = 1 \implies$ Stop exploring | $[(2, 3, 700)]$ | $[0, 100, 200, 700]$ |
| 4 | $(2, 3, 700)$ | $\text{stops} = 2 > K = 1 \implies$ Stop exploring | $[\,]$ | $[0, 100, 200, 700]$ |

Queue is empty. Result is $\text{dist}[3] = 700$.

---

## 6. Complexity Analysis

### Time Complexity
* Each city enters the queue at most $K + 1$ times (at most once per stop level).
* Across each level, every flight edge is relaxed at most once.
* Total Time Complexity:
  $$\mathcal{O}(K \cdot m)$$
  Given $K \le 100$ and $m \le \frac{100 \times 99}{2} \approx 5000$, the maximum number of edge checks is $\le 5 \times 10^5$, executing in under $10\text{ ms}$.

### Space Complexity
* Adjacency list: $\mathcal{O}(n + m)$ space.
* Queue and distance array: $\mathcal{O}(n \cdot K)$ space in the worst case.
* Total Auxiliary Space:
  $$\mathcal{O}(n + m)$$
