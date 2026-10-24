### Problem: Dijkstra's Algorithm
**Difficulty:** Medium | **Tags:** Graphs, Shortest Path, Heap, Priority Queue

**Problem Statement**
Given a weighted, undirected graph with $V$ vertices numbered from $0$ to $V - 1$ and $E$ edges. 

Each edge is represented by `[u, v, weight]`, where:
* $u, v$ represent the vertices connected by an undirected edge.
* $weight$ is the non-negative weight of the edge between $u$ and $v$.

Given a source vertex $S$, find the shortest distance from the source vertex $S$ to all vertices $0, 1, \dots, V - 1$.

If a vertex is not reachable from the source vertex $S$, its distance should be reported as $10^9$ ($1000000000$).

---

**Input Format**
* The first line contains three space-separated integers $V$ (number of vertices), $E$ (number of undirected edges), and $S$ (source vertex).
* The next $E$ lines each contain three space-separated integers $u$, $v$, and $weight$, representing an undirected edge between $u$ and $v$ with weight $weight$.
* If $E = 0$, only the first line is given.

**Output Format**
* Print $V$ space-separated integers representing the shortest distances from source vertex $S$ to vertices $0, 1, \dots, V - 1$.
* Unreachable vertices should be printed as `1000000000`.

---

**Constraints**
* $1 \le V \le 10^4$
* $0 \le E \le 2 \times 10^4$
* $0 \le u, v < V$ and $u \ne v$
* $0 \le weight \le 10^4$
* $0 \le S < V$

---

**Example 1**
**Input:**
```text
2 1 0
0 1 9
```

**Output:**
```text
0 9
```

**Explanation:**
The shortest distance from node 0 (source) to 0 is 0, and to node 1 is 9.

---

**Example 2**
**Input:**
```text
3 3 2
0 1 1
0 2 6
1 2 3
```

**Output:**
```text
4 3 0
```

**Explanation:**
* Source vertex is 2:
  * For node 0: shortest path is $2 \to 1 \to 0$ with distance $3 + 1 = 4$.
  * For node 1: shortest path is $2 \to 1$ with distance $3$.
  * For node 2: distance to itself is $0$.
* Output: `4 3 0`.

---

**Example 3**
**Input:**
```text
4 4 0
0 1 1
0 3 2
1 2 4
2 3 3
```

**Output:**
```text
0 1 5 2
```

**Explanation:**
* Source vertex is 0:
  * `dist[0] = 0`
  * `dist[1] = 1`
  * `dist[2] = 5` via $0 \to 3 \to 2$ ($2 + 3 = 5$, shorter than $0 \to 1 \to 2$ which is $1 + 4 = 5$)
  * `dist[3] = 2` via $0 \to 3$
* Output: `0 1 5 2`.
