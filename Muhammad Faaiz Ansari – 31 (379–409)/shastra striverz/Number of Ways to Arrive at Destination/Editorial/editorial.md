# Editorial: Number of Ways to Arrive at Destination

## 1. Problem Overview
We are given a connected, undirected weighted graph with $n$ intersections and $m$ roads. The travel times on roads are positive integers up to $10^9$.
We need to count how many distinct paths from intersection $0$ to intersection $n - 1$ achieve the minimum possible total travel time.
Because this count can be very large, we compute the result modulo $10^9 + 7$.

---

## 2. Intuition & Dynamic Programming with Dijkstra
This problem combines **Dijkstra's Shortest Path Algorithm** with **Dynamic Programming on a Directed Acyclic Graph (DAG)** of shortest paths:

1. **Shortest Distance Array (`dist[]`)**:
   `dist[u]` records the minimum time needed to reach intersection $u$ from $0$.
   Since individual edges can have weight $10^9$, total travel time can reach $200 \times 10^9 = 2 \times 10^{11}$, which exceeds the 32-bit signed integer limit. We must use **64-bit integers (`long`)** for `dist[]`.

2. **Ways Array (`ways[]`)**:
   `ways[u]` stores the number of distinct paths that achieve distance `dist[u]` from $0$, modulo $10^9 + 7$.
   Initially:
   $$\text{dist}[0] = 0, \quad \text{ways}[0] = 1$$
   $$\text{dist}[i] = \infty, \quad \text{ways}[i] = 0 \quad (\forall i \ne 0)$$

### Relaxation Logic
When relaxing an edge $(u, v)$ with weight $\text{time}$:
* **Case 1: A strictly shorter path to $v$ is discovered**:
  $$\text{dist}[u] + \text{time} < \text{dist}[v]$$
  We update the optimal distance: $\text{dist}[v] = \text{dist}[u] + \text{time}$.
  All previous paths to $v$ were suboptimal, so we reset the count:
  $$\text{ways}[v] = \text{ways}[u]$$
  We then push $(v, \text{dist}[v])$ into our min-heap priority queue.

* **Case 2: Another path of identical optimal length is found**:
  $$\text{dist}[u] + \text{time} == \text{dist}[v]$$
  A new distinct route to $v$ with the same shortest duration has been discovered through $u$. We add the number of ways:
  $$\text{ways}[v] = (\text{ways}[v] + \text{ways}[u]) \pmod{10^9 + 7}$$
  *(Note: We do not need to re-insert $v$ into the priority queue in Case 2 since its distance did not decrease.)*

---

## 3. Algorithm Step-by-Step
1. **Edge Case**:
   If $n = 1$, intersection $0$ is already the destination. Return $1$.
2. **Graph Construction**:
   Build an adjacency list `adj` storing `(neighbor, time)` for each undirected edge.
3. **Data Structures**:
   * `dist`: array of size $n$, initialized to $\infty$ (`Long.MAX_VALUE / 2`).
   * `ways`: array of size $n$, initialized to $0$.
   * `pq`: Min-Heap Priority Queue storing `(distance, node)` ordered by distance ascending.
4. **Dijkstra Traversal**:
   * Insert $(0, 0)$ into `pq`.
   * While `pq` is not empty:
     * Pop $(d, u)$.
     * If $d > \text{dist}[u]$, continue (skip stale entries).
     * For each neighbor $(v, \text{time})$ of $u$:
       * If $d + \text{time} < \text{dist}[v]$:
         * Update $\text{dist}[v] = d + \text{time}$.
         * Update $\text{ways}[v] = \text{ways}[u]$.
         * Push $(\text{dist}[v], v)$ into `pq`.
       * Else if $d + \text{time} == \text{dist}[v]$:
         * Update $\text{ways}[v] = (\text{ways}[v] + \text{ways}[u]) \pmod{10^9 + 7}$.
5. **Result**:
   Return $\text{ways}[n - 1]$.

---

## 4. Dry Run (Example 1)
* $n = 7, m = 10$, Roads connecting nodes $0$ to $6$.
* Destination is node $6$.
* As Dijkstra executes, nodes are finalized in increasing order of their shortest distance:
  1. $\text{dist}[0] = 0, \text{ways}[0] = 1$
  2. Node $1$: $\text{dist}[1] = 2, \text{ways}[1] = 1$ (via $0 \to 1$)
  3. Node $2$: $\text{dist}[2] = 2 + 3 = 5, \text{ways}[2] = 1$ (via $0 \to 1 \to 2$)
  4. Node $3$: $\text{dist}[3] = 2 + 3 = 5, \text{ways}[3] = 1$ (via $0 \to 1 \to 3$)
  5. Node $4$: $\text{dist}[4] = 5, \text{ways}[4] = 1$ (via $0 \to 4$)
  6. Node $5$:
     * Via $2$: $5 + 1 = 6 \implies \text{dist}[5] = 6, \text{ways}[5] = \text{ways}[2] = 1$.
     * Via $3$: $5 + 1 = 6 == \text{dist}[5] \implies \text{ways}[5] = \text{ways}[5] + \text{ways}[3] = 1 + 1 = 2$.
  7. Node $6$:
     * Direct from $0$: cost $7 \implies \text{dist}[6] = 7, \text{ways}[6] = 1$.
     * Via $4$: cost $5 + 2 = 7 == \text{dist}[6] \implies \text{ways}[6] = 1 + 1 = 2$.
     * Via $5$: cost $6 + 1 = 7 == \text{dist}[6] \implies \text{ways}[6] = 2 + \text{ways}[5] = 2 + 2 = 4$.

Total ways to reach node $6$ in $7$ minutes is $4$.

---

## 5. Complexity Analysis

### Time Complexity
* Each node is popped from the priority queue at most once when its shortest distance is finalized.
* Each edge is traversed at most twice (once from each endpoint).
* Heap operations take $\mathcal{O}(\log n)$.
* Total Time Complexity:
  $$\mathcal{O}((n + m) \log n)$$
  Given $n \le 200$ and $m \le \frac{200 \times 199}{2} \approx 20000$, total operations are $\le 1.5 \times 10^5$, executing in under $5\text{ ms}$.

### Space Complexity
* Adjacency list: $\mathcal{O}(n + m)$ entries.
* Distance and ways arrays: $\mathcal{O}(n)$ space.
* Priority queue: at most $\mathcal{O}(n + m)$ elements.
* Total Auxiliary Space:
  $$\mathcal{O}(n + m)$$
