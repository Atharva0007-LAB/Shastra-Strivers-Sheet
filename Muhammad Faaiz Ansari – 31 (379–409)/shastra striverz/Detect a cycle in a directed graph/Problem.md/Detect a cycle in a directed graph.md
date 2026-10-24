### Problem: Detect a Cycle in a Directed Graph
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Depth-First Search, Topological Sort, Directed Acyclic Graph

**Problem Statement**
Given a **directed** graph with $V$ vertices labeled from $0$ to $V - 1$ and $E$ directed edges. 

The graph is represented using an adjacency list where each directed edge connects vertex $u$ to vertex $v$ ($u \to v$). Determine if the graph contains any **cycles**.

A cycle in a directed graph is a sequence of directed edges $v_0 \to v_1 \to v_2 \to \dots \to v_k \to v_0$ where the starting and ending vertex are the same ($k \ge 1$).

---

**Input Format**
* The first line contains two space-separated integers $V$ (number of vertices) and $E$ (number of directed edges).
* The next $E$ lines each contain two space-separated integers $u$ and $v$, representing a directed edge from $u$ to $v$ ($u \to v$).

**Output Format**
* Print `true` if the directed graph contains at least one cycle, or `false` otherwise.

---

**Constraints**
* $1 \le V \le 10^4$
* $0 \le E \le 10^4$
* $0 \le u, v < V$ and $u \ne v$
* The graph is directed and may contain disconnected components.

---

**Example 1**
**Input:**
```text
6 6
0 1
1 2
1 5
2 3
3 4
4 1
```

**Output:**
```text
true
```

**Explanation:**
* Vertex 1 connects to 2, 2 connects to 3, 3 connects to 4, and 4 connects back to 1.
* This forms the directed cycle: $1 \to 2 \to 3 \to 4 \to 1$.
* Therefore, the output is `true`.

---

**Example 2**
**Input:**
```text
4 5
0 1
0 2
1 2
3 0
3 2
```

**Output:**
```text
false
```

**Explanation:**
* All directed paths terminate at vertex 2 without looping back.
* The graph is a Directed Acyclic Graph (DAG), so the output is `false`.

---

**Example 3**
**Input:**
```text
3 3
0 1
1 2
2 0
```

**Output:**
```text
true
```

**Explanation:**
* The edges form a directed cycle: $0 \to 1 \to 2 \to 0$.
* Thus, the output is `true`.
