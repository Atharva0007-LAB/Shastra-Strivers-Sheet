### Problem: Traversal Techniques
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Depth-First Search, Queue, Recursion

**Problem Statement**
Given an undirected, connected graph with $V$ vertices numbered from $0$ to $V-1$ and $E$ undirected edges.

The graph is represented using an array of edges, where each edge is an undirected pair $[u, v]$ connecting vertex $u$ and vertex $v$.

Your task is to implement and output both:
1. **Depth First Search (DFS)** traversal starting from vertex $0$.
2. **Breadth First Search (BFS)** traversal starting from vertex $0$.

**Adjacency Order Rule:**
In both DFS and BFS, when exploring adjacent neighbors of any vertex, neighbors must be visited in the order they appear in the adjacency list constructed from the input edge sequence (i.e. for each edge $[u, v]$, $v$ is appended to $u$'s neighbor list, and $u$ is appended to $v$'s neighbor list in the exact order the edges are read).

**Input Format**
* The first line contains two space-separated integers $V$ (the number of vertices) and $E$ (the number of edges).
* The next $E$ lines each contain two space-separated integers $u$ and $v$, representing an undirected edge between vertex $u$ and vertex $v$.

**Output Format**
* Print two lines:
  * The first line contains $V$ space-separated integers representing the **DFS** traversal sequence starting from vertex $0$.
  * The second line contains $V$ space-separated integers representing the **BFS** traversal sequence starting from vertex $0$.

**Constraints**
* $1 \le V \le 10^4$
* $0 \le E \le 10^4$
* $0 \le u, v < V$ and $u \ne v$
* The graph is connected and undirected.
* There are no self-loops.

**Example 1**
**Input:**
```text
5 4
0 2
0 3
0 1
2 4
```

**Output:**
```text
0 2 4 3 1
0 2 3 1 4
```

**Explanation:**
- Adjacency lists built from the edge order:
  - $\text{adj}[0] = [2, 3, 1]$
  - $\text{adj}[1] = [0]$
  - $\text{adj}[2] = [0, 4]$
  - $\text{adj}[3] = [0]$
  - $\text{adj}[4] = [2]$
- **DFS:** Starting at $0$, visit first neighbor $2$, then from $2$ visit $4$. Backtrack to $0$, visit $3$. Backtrack to $0$, visit $1$. The traversal is `0 2 4 3 1`.
- **BFS:** Starting at $0$, visit level 1 neighbors: $2, 3, 1$. Then from $2$, visit neighbor $4$. The traversal is `0 2 3 1 4`.

---

**Example 2**
**Input:**
```text
4 3
0 1
0 3
1 2
```

**Output:**
```text
0 1 2 3
0 1 3 2
```

**Explanation:**
- Adjacency lists:
  - $\text{adj}[0] = [1, 3]$
  - $\text{adj}[1] = [0, 2]$
  - $\text{adj}[2] = [1]$
  - $\text{adj}[3] = [0]$
- **DFS:** $0 \to 1 \to 2$ (backtrack to $0$) $\to 3$. Output: `0 1 2 3`.
- **BFS:** Queue begins with $[0]$. Pop $0$ and enqueue unvisited neighbors $[1, 3]$. Pop $1$ and enqueue unvisited neighbor $[2]$. Pop $3$, pop $2$. Output: `0 1 3 2`.

---

**Example 3**
**Input:**
```text
3 2
0 1
0 2
```

**Output:**
```text
0 1 2
0 1 2
```

**Explanation:**
Both DFS and BFS visit node $1$ first, then backtrack / proceed to node $2$. Output is `0 1 2` for both.
