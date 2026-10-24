### Problem: Number of Provinces
**Difficulty:** Medium | **Tags:** Graphs, Depth-First Search, Breadth-First Search, Disjoint Set Union, Matrix

**Problem Statement**
Given an undirected graph with $V$ vertices (cities). Two vertices $u$ and $v$ belong to a single province if there is a path from $u$ to $v$ or $v$ to $u$. 

A **province** is defined as a group of directly or indirectly connected cities, with no connections to cities outside of the group.

The graph is given as a $V \times V$ adjacency matrix `adj`, where:
* `adj[i][j] = 1` if the $i$-th city and the $j$-th city are directly connected.
* `adj[i][j] = 0` otherwise.
* `adj[i][i] = 1` for all $0 \le i < V$.

Find the total number of **provinces**.

**Input Format**
* The first line contains an integer $V$, denoting the number of cities.
* The next $V$ lines each contain $V$ space-separated integers ($0$ or $1$), representing the adjacency matrix `adj`.

**Output Format**
* Print a single integer representing the number of provinces.

**Constraints**
* $1 \le V \le 300$
* $\text{adj}[i][j] \in \{0, 1\}$
* $\text{adj}[i][i] = 1$
* $\text{adj}[i][j] = \text{adj}[j][i]$

**Example 1**
**Input:**
```text
4
1 0 0 1
0 1 1 0
0 1 1 0
1 0 0 1
```

**Output:**
```text
2
```

**Explanation:**
- Cities $0$ and $3$ are directly connected (`adj[0][3] = 1`), forming province 1: $\{0, 3\}$.
- Cities $1$ and $2$ are directly connected (`adj[1][2] = 1`), forming province 2: $\{1, 2\}$.
Total provinces = $2$.

---

**Example 2**
**Input:**
```text
3
1 0 1
0 1 0
1 0 1
```

**Output:**
```text
2
```

**Explanation:**
- Cities $0$ and $2$ are connected (`adj[0][2] = 1`), forming province $\{0, 2\}$.
- City $1$ is isolated, forming province $\{1\}$.
Total provinces = $2$.

---

**Example 3**
**Input:**
```text
2
1 1
1 1
```

**Output:**
```text
1
```

**Explanation:**
Both cities $0$ and $1$ are connected, forming a single province $\{0, 1\}$.
