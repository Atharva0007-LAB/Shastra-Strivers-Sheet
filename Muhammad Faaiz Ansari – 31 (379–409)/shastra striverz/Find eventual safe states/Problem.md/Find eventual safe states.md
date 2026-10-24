### Problem: Find Eventual Safe States
**Difficulty:** Medium | **Tags:** Graphs, Topological Sort, Breadth-First Search, Depth-First Search, Directed Graph

**Problem Statement**
Given a directed graph with $V$ vertices labeled from $0$ to $V - 1$ and $E$ directed edges.

A node is a **terminal node** if there are no outgoing edges leaving it. 
A node is an **eventual safe node** if every possible directed path starting from that node leads to a terminal node (or another safe node). A node that can reach a cycle or is part of a cycle can never be safe, because an infinite path along the cycle never reaches a terminal node.

Return an array containing all the **safe nodes** of the graph in **ascending order**.

---

**Input Format**
* The first line contains two space-separated integers $V$ (number of vertices) and $E$ (number of directed edges).
* The next $E$ lines each contain two space-separated integers $u$ and $v$, representing a directed edge from $u$ to $v$ ($u \to v$).
* If $E = 0$, only the first line is given.

**Output Format**
* Print a single line containing space-separated integers representing all eventual safe nodes sorted in **ascending order**.
* If there are no safe nodes, print an empty line.

---

**Constraints**
* $1 \le V \le 10^4$
* $0 \le E \le 4 \times 10^4$
* $0 \le u, v < V$
* The graph may contain self-loops ($u = v$) or multiple edges.
* Disconnected components may be present.

---

**Example 1**
**Input:**
```text
7 7
0 1
0 2
1 2
1 3
2 5
3 0
4 5
```

**Output:**
```text
2 4 5 6
```

**Explanation:**
* Vertices 5 and 6 have no outgoing edges, so they are terminal nodes (and therefore safe).
* Vertex 4 only connects to 5 (terminal), so 4 is safe.
* Vertex 2 only connects to 5 (terminal), so 2 is safe.
* Vertices 0, 1, and 3 form a cycle: $0 \to 1 \to 3 \to 0$. Any path entering this cycle can loop infinitely and never reach a terminal node.
* Therefore, the safe nodes in ascending order are `2 4 5 6`.

---

**Example 2**
**Input:**
```text
4 4
0 1
1 2
2 0
2 3
```

**Output:**
```text
3
```

**Explanation:**
* Vertex 3 has no outgoing edges (terminal node) $\implies$ safe.
* From vertices 0, 1, and 2, a path can follow the directed cycle $0 \to 1 \to 2 \to 0$ infinitely.
* Only vertex 3 is guaranteed to always terminate.
* Output: `3`.

---

**Example 3**
**Input:**
```text
4 3
0 1
1 2
2 0
```

**Output:**
```text
3
```

**Explanation:**
* Vertices 0, 1, 2 form a cycle and can never reach a terminal state.
* Vertex 3 has no outgoing edges and is an isolated terminal node.
* Output: `3`.
