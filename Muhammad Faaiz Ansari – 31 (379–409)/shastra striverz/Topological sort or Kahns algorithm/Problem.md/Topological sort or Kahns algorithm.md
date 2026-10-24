### Problem: Topological Sort or Kahn's Algorithm
**Difficulty:** Medium | **Tags:** Graphs, Topological Sort, Breadth-First Search, Depth-First Search, Directed Acyclic Graph

**Problem Statement**
Given a Directed Acyclic Graph (DAG) with $V$ vertices labeled from $0$ to $V - 1$ and $E$ directed edges.

Find a **topological sorting** of the vertices.

A topological sorting of a directed graph is a linear ordering of its vertices such that for every directed edge $u \to v$, vertex $u$ appears before vertex $v$ in the ordering.

Topological sorting is only possible for **Directed Acyclic Graphs (DAGs)**. If the graph has multiple valid topological sorts, any valid ordering is accepted.

---

**Input Format**
* The first line contains two space-separated integers $V$ (number of vertices) and $E$ (number of directed edges).
* The next $E$ lines each contain two space-separated integers $u$ and $v$, representing a directed edge from $u$ to $v$ ($u \to v$).

**Output Format**
* Print $V$ space-separated integers representing a valid topological sort order of the graph.

---

**Constraints**
* $1 \le V \le 10^4$
* $0 \le E \le 10^4$
* $0 \le u, v < V$ and $u \ne v$
* The graph is a **Directed Acyclic Graph (DAG)**.

---

**Example 1**
**Input:**
```text
6 6
2 3
3 1
4 0
4 1
5 0
5 2
```

**Output:**
```text
4 5 0 2 3 1
```

**Explanation:**
* Edge $2 \to 3$ means 2 appears before 3.
* Edge $3 \to 1$ means 3 appears before 1.
* Edge $4 \to 0$ and $4 \to 1$ means 4 appears before 0 and 1.
* Edge $5 \to 0$ and $5 \to 2$ means 5 appears before 0 and 2.
* A valid topological ordering satisfying all directed constraints is: `4 5 0 2 3 1` (or `5 4 2 3 1 0`).

---

**Example 2**
**Input:**
```text
4 3
1 0
2 0
3 0
```

**Output:**
```text
1 2 3 0
```

**Explanation:**
* Vertices 1, 2, and 3 all have directed edges pointing to vertex 0.
* Therefore, 1, 2, and 3 must appear before 0.
* Any permutation where 0 is last (e.g. `1 2 3 0`, `3 2 1 0`, etc.) is valid.

---

**Example 3**
**Input:**
```text
3 2
0 1
1 2
```

**Output:**
```text
0 1 2
```

**Explanation:**
* Edge $0 \to 1$ and $1 \to 2$ uniquely determines the order: 0 must precede 1, which must precede 2.
* The only valid topological sort is: `0 1 2`.
