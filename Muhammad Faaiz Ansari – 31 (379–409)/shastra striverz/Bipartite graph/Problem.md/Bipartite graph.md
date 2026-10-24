### Problem: Bipartite Graph
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Depth-First Search, Two-Coloring

**Problem Statement**
Given an undirected graph with $V$ vertices labeled from $0$ to $V - 1$ and $E$ edges. 

The graph is given as an edge list where each edge connects vertex $u$ and vertex $v$. Determine whether the graph is **bipartite**.

A graph is bipartite if its vertices can be partitioned into two disjoint independent sets $A$ and $B$ such that every edge connects a vertex in $A$ to a vertex in $B$. In other words, no two adjacent vertices belong to the same set (the graph is **2-colorable**).

**Key Theorem:** An undirected graph is bipartite if and only if it contains **no odd-length cycles**. Even-length cycles and acyclic components (trees/forests) are always bipartite.

---

**Input Format**
* The first line contains two space-separated integers $V$ (number of vertices) and $E$ (number of edges).
* The next $E$ lines each contain two space-separated integers $u$ and $v$, representing an undirected edge between vertex $u$ and vertex $v$.

**Output Format**
* Print `true` if the graph is bipartite, or `false` otherwise.

---

**Constraints**
* $1 \le V \le 10^4$
* $0 \le E \le 10^4$
* $0 \le u, v < V$ and $u \ne v$
* Graph is undirected with no self-loops.
* The graph may consist of multiple disconnected components.

---

**Example 1**
**Input:**
```text
4 4
0 1
0 3
1 2
2 3
```

**Output:**
```text
true
```

**Explanation:**
* The graph is a 4-cycle ($0 - 1 - 2 - 3 - 0$), which has even length (4 edges).
* We can partition vertices into set $A = \{0, 2\}$ and set $B = \{1, 3\}$.
* Every edge connects a vertex in $A$ with a vertex in $B$. Thus, the output is `true`.

---

**Example 2**
**Input:**
```text
4 5
0 1
0 2
0 3
2 1
3 2
```

**Output:**
```text
false
```

**Explanation:**
* Vertices $0, 1, 2$ form an odd-length cycle of size 3 (a triangle: $0 - 1 - 2 - 0$).
* It is impossible to color a triangle with only 2 colors without two adjacent vertices sharing the same color.
* Therefore, the output is `false`.

---

**Example 3**
**Input:**
```text
5 5
0 1
0 3
1 2
2 4
4 3
```

**Output:**
```text
false
```

**Explanation:**
* Vertices $0 - 1 - 2 - 4 - 3 - 0$ form a 5-cycle (pentagon).
* A 5-cycle is an odd cycle, which cannot be 2-colored.
* Hence, the output is `false`.
