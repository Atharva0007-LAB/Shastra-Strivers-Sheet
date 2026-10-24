### Problem: Shortest Path in DAG
**Difficulty:** Medium | **Tags:** Graphs, Dynamic Programming, Topological Sort, Shortest Path, Directed Acyclic Graph

**Problem Statement**
Given a Directed Acyclic Graph (DAG) with $N$ vertices labeled from $0$ to $N - 1$ and $M$ directed edges. 

You are given a list of edges where each directed edge connects vertex $u$ to vertex $v$ with a positive weight $wt$ ($u \to v$ with weight $wt$).

Find the **shortest path distance** from the source vertex $0$ to all vertices $0, 1, \dots, N - 1$. If a vertex is unreachable from the source vertex $0$, its distance should be `-1`.

---

**Input Format**
* The first line contains two space-separated integers $N$ (number of vertices) and $M$ (number of directed edges).
* The next $M$ lines each contain three space-separated integers $u$, $v$, and $wt$, representing a directed edge from $u$ to $v$ with weight $wt$.
* If $M = 0$, only the first line is given.

**Output Format**
* Print $N$ space-separated integers representing the shortest path distances from vertex $0$ to vertices $0, 1, \dots, N - 1$.

---

**Constraints**
* $1 \le N \le 10^4$
* $0 \le M \le 2 \times 10^4$
* $0 \le u, v < N$ and $u \ne v$
* $1 \le wt \le 10^4$
* The graph is a Directed Acyclic Graph (DAG).

---

**Example 1**
**Input:**
```text
4 2
0 1 2
0 2 1
```

**Output:**
```text
0 2 1 -1
```

**Explanation:**
* Shortest path to 0 is `0`.
* Shortest path to 1 is $0 \to 1$ with weight `2`.
* Shortest path to 2 is $0 \to 2$ with weight `1`.
* Vertex 3 is unreachable from 0, so its distance is `-1`.
* Output: `0 2 1 -1`.

---

**Example 2**
**Input:**
```text
6 7
0 1 2
0 4 1
4 5 4
4 2 2
1 2 3
2 3 6
5 3 1
```

**Output:**
```text
0 2 3 6 1 5
```

**Explanation:**
* Shortest path to 0: `0`
* Shortest path to 1: $0 \to 1$ (weight 2)
* Shortest path to 2: $0 \to 4 \to 2$ ($1 + 2 = 3$)
* Shortest path to 3: $0 \to 4 \to 5 \to 3$ ($1 + 4 + 1 = 6$)
* Shortest path to 4: $0 \to 4$ (weight 1)
* Shortest path to 5: $0 \to 4 \to 5$ ($1 + 4 = 5$)
* Output: `0 2 3 6 1 5`.

---

**Example 3**
**Input:**
```text
3 3
0 1 4
0 2 2
1 2 5
```

**Output:**
```text
0 4 2
```

**Explanation:**
* Path to 0 is `0`.
* Path to 1 is $0 \to 1$ (weight 4).
* Path to 2 directly is $0 \to 2$ (weight 2), which is shorter than $0 \to 1 \to 2$ (weight 9).
* Output: `0 4 2`.
