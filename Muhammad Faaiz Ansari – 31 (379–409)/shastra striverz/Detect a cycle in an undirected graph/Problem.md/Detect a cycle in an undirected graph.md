### Problem: Detect a Cycle in an Undirected Graph
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Depth-First Search, Disjoint Set Union

**Problem Statement**
Given an undirected graph with $V$ vertices numbered from $0$ to $V - 1$ and $E$ edges. 

The graph is represented using an adjacency list where each vertex connects to zero or more neighboring vertices. Determine whether the graph contains any **cycles**.

A cycle in an undirected graph is a path of at least 3 distinct edges that starts and ends at the same vertex, with no vertex repeated other than the starting and ending vertex.

**Note:**
* The graph is undirected and may consist of one or multiple disconnected components.
* The graph does not contain any self-loops or duplicate edges.

---

**Input Format**
* The first line contains two space-separated integers $V$ (number of vertices) and $E$ (number of edges).
* The next $E$ lines each contain two space-separated integers $u$ and $v$, representing an undirected edge between vertex $u$ and vertex $v$.

**Output Format**
* Print `true` if the graph contains at least one cycle, or `false` otherwise.

---

**Constraints**
* $1 \le V \le 10^4$
* $0 \le E \le 10^4$
* $0 \le u, v < V$ and $u \ne v$
* Graph is undirected with no self-loops.

---

**Example 1**
**Input:**
```text
6 7
0 1
0 3
1 2
1 4
2 5
3 4
4 5
```

**Output:**
```text
true
```

**Explanation:**
* The vertices form a cycle: $0 \to 1 \to 4 \to 3 \to 0$ (as well as $1 \to 2 \to 5 \to 4 \to 1$).
* Since at least one cycle exists, the output is `true`.

---

**Example 2**
**Input:**
```text
4 3
0 1
0 2
2 3
```

**Output:**
```text
false
```

**Explanation:**
* The edges form a tree structure without any closed loops.
* Therefore, the output is `false`.

---

**Example 3**
**Input:**
```text
4 4
0 1
0 2
1 2
2 3
```

**Output:**
```text
true
```

**Explanation:**
* The vertices $0, 1, 2$ form a triangle cycle: $0 - 1 - 2 - 0$.
* Thus, the output is `true`.
