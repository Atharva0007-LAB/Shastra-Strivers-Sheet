### Problem: Connected Components
**Difficulty:** Medium | **Tags:** Graphs, Depth-First Search, Breadth-First Search, Disjoint Set Union

**Problem Statement**
Given an undirected graph consisting of $V$ vertices numbered from $0$ to $V-1$ and $E$ edges. 

The $i$-th edge is represented by $[u, v]$, denoting an undirected edge between vertex $u$ and vertex $v$.

We say two vertices $u$ and $v$ belong to the same component if there is a path from $u$ to $v$ (or $v$ to $u$). A **connected component** is a maximal subgraph in which any two vertices are connected to each other by paths, and which is connected to no additional vertices in the supergraph.

Your task is to determine the total number of **connected components** in the graph.

**Input Format**
* The first line contains two space-separated integers $V$ (the number of vertices) and $E$ (the number of edges).
* The next $E$ lines each contain two space-separated integers $u$ and $v$, representing an undirected edge between vertex $u$ and vertex $v$.

**Output Format**
* Print a single integer representing the number of connected components in the graph.

**Constraints**
* $1 \le V \le 10^4$
* $0 \le E \le 10^4$
* $0 \le u, v < V$ and $u \ne v$
* All edges are unique and undirected.
* There are no self-loops.

**Example 1**
**Input:**
```text
4 2
0 1
1 2
```

**Output:**
```text
2
```

**Explanation:**
- Component 1: Vertices $\{0, 1, 2\}$ form a connected component through edges $(0, 1)$ and $(1, 2)$.
- Component 2: Vertex $\{3\}$ is isolated and forms its own connected component.
Total components = $2$.

---

**Example 2**
**Input:**
```text
7 4
0 1
1 2
2 3
4 5
```

**Output:**
```text
3
```

**Explanation:**
- Component 1: $\{0, 1, 2, 3\}$
- Component 2: $\{4, 5\}$
- Component 3: $\{6\}$ (isolated vertex)
Total components = $3$.

---

**Example 3**
**Input:**
```text
5 3
0 1
1 2
3 4
```

**Output:**
```text
2
```

**Explanation:**
- Component 1: $\{0, 1, 2\}$
- Component 2: $\{3, 4\}$
Total components = $2$.
