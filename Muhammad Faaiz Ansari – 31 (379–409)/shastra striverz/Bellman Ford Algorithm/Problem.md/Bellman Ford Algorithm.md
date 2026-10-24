### Problem: Bellman Ford Algorithm
**Difficulty:** Medium | **Tags:** Graphs, Bellman-Ford, Shortest Path, Negative Cycle

---

### Problem Statement
Given a weighted and directed graph with $V$ vertices numbered from $0$ to $V - 1$ and $E$ edges. Each edge is represented as $[u, v, w]$, denoting a directed edge from vertex $u$ to vertex $v$ with weight $w$.

Find the **shortest distance** from the source vertex $S$ to all vertices $0, 1, \dots, V - 1$.
* If a vertex cannot be reached from $S$, its shortest distance should be marked as $10^9$ ($1000000000$).
* If the graph contains a **negative weight cycle** reachable from $S$, return `-1`.

---

### Input Format
* The first line contains three space-separated integers $V$ (number of vertices), $E$ (number of directed edges), and $S$ (the source vertex).
* The next $E$ lines each contain three space-separated integers $u$, $v$, and $w$, describing a directed edge from vertex $u$ to vertex $v$ with weight $w$.
* If $E = 0$, only the first line is provided.

---

### Output Format
* If a negative weight cycle is detected, print a single integer `-1`.
* Otherwise, print $V$ space-separated integers representing the shortest path distances from source vertex $S$ to vertices $0, 1, \dots, V - 1$. Unreachable vertices must be represented as `1000000000`.

---

### Constraints
* $1 \le V \le 500$
* $0 \le E \le V \times (V - 1)$
* $0 \le u, v < V$ and $u \ne v$
* $-1000 \le w \le 1000$
* $0 \le S < V$

---

### Examples

#### Example 1
**Input:**
```text
6 7 0
3 2 6
5 3 1
0 1 5
1 5 -3
1 2 -2
3 4 -2
2 4 3
```

**Output:**
```text
0 5 3 3 1 2
```

**Explanation:**
The shortest path distances from source $0$ are:
* Vertex 0: $0$ (source)
* Vertex 1: $0 \to 1$ with cost $5$
* Vertex 2: $0 \to 1 \to 2$ with cost $5 + (-2) = 3$
* Vertex 3: $0 \to 1 \to 5 \to 3$ with cost $5 + (-3) + 1 = 3$
* Vertex 4: $0 \to 1 \to 5 \to 3 \to 4$ with cost $3 + (-2) = 1$
* Vertex 5: $0 \to 1 \to 5$ with cost $5 + (-3) = 2$

Output: `0 5 3 3 1 2`.

---

#### Example 2
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
Shortest distance to $0$ is $0$, and to $1$ is $9$.

---

#### Example 3
**Input:**
```text
3 3 0
0 1 -1
1 2 -2
2 0 -3
```

**Output:**
```text
-1
```

**Explanation:**
The cycle $0 \to 1 \to 2 \to 0$ has total weight $(-1) + (-2) + (-3) = -6 < 0$. This is a negative weight cycle reachable from $0$, so the output is `-1`.
