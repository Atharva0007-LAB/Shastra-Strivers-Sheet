### Problem: Shortest Path in Undirected Graph with Unit Weights
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Shortest Path, Undirected Graph

**Problem Statement**
Given an undirected graph with $N$ vertices numbered from $0$ to $N - 1$ and $M$ undirected edges, where each edge has a unit weight of $1$.

Find the **shortest path distance** from the source vertex to all other vertices in the graph. The source vertex is assumed to be **$0$**.

If a vertex is unreachable from the source vertex, return `-1` for that vertex.

---

**Input Format**
* The first line contains two space-separated integers $N$ (number of vertices) and $M$ (number of undirected edges).
* The next $M$ lines each contain two space-separated integers $u$ and $v$, representing an undirected edge between vertex $u$ and vertex $v$.
* If $M = 0$, only the first line is given.

**Output Format**
* Print $N$ space-separated integers representing the shortest path distances from source vertex $0$ to vertices $0, 1, \dots, N - 1$.

---

**Constraints**
* $1 \le N \le 10^4$
* $0 \le M \le 2 \times 10^4$
* $0 \le u, v < N$ and $u \ne v$
* The graph is undirected and may contain disconnected components.

---

**Example 1**
**Input:**
```text
9 10
0 1
0 3
3 4
4 5
5 6
1 2
2 6
6 7
7 8
6 8
```

**Output:**
```text
0 1 2 1 2 3 3 4 4
```

**Explanation:**
* `dist[0] = 0` (source)
* `dist[1] = 1` via $0 - 1$
* `dist[2] = 2` via $0 - 1 - 2$
* `dist[3] = 1` via $0 - 3$
* `dist[4] = 2` via $0 - 3 - 4$
* `dist[5] = 3` via $0 - 3 - 4 - 5$
* `dist[6] = 3` via $0 - 1 - 2 - 6$ (shorter than $0 - 3 - 4 - 5 - 6$ which is 4)
* `dist[7] = 4` via $0 - 1 - 2 - 6 - 7$
* `dist[8] = 4` via $0 - 1 - 2 - 6 - 8$

---

**Example 2**
**Input:**
```text
8 10
1 0
2 1
0 3
3 7
3 4
7 4
7 6
4 5
4 6
6 5
```

**Output:**
```text
0 1 2 1 2 3 3 2
```

**Explanation:**
* `dist[0] = 0`
* `dist[1] = 1`
* `dist[2] = 2`
* `dist[3] = 1`
* `dist[4] = 2` (via $0 - 3 - 4$)
* `dist[5] = 3` (via $0 - 3 - 4 - 5$)
* `dist[6] = 3` (via $0 - 3 - 7 - 6$ or $0 - 3 - 4 - 6$)
* `dist[7] = 2` (via $0 - 3 - 7$)

---

**Example 3**
**Input:**
```text
3 1
1 2
```

**Output:**
```text
0 -1 -1
```

**Explanation:**
* Source vertex $0$ has no edges connected to it.
* Vertices 1 and 2 are in a separate disconnected component and cannot be reached from 0.
* Output: `0 -1 -1`.
