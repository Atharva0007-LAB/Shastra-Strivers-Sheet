### Problem: Print Shortest Path
**Difficulty:** Medium | **Tags:** Graphs, Dijkstra's Algorithm, Shortest Path, Priority Queue, Undirected Graph

---

### Problem Statement
Given a **weighted**, **undirected** graph having $n$ vertices numbered from $1$ to $n$ and $m$ edges, where each edge is given as $[u, v, w]$, representing an undirected edge between vertex $u$ and vertex $v$ with weight $w$.

Find the **shortest path** between vertex $1$ and vertex $n$.
* If no path exists between vertex $1$ and vertex $n$, output `-1`.
* If a path exists, output the total weight of the shortest path followed by the sequence of vertices visited on the path from vertex $1$ to vertex $n$, all separated by spaces:
  $$\text{weight} \quad 1 \quad v_2 \quad v_3 \quad \dots \quad n$$

---

### Input Format
* The first line contains two space-separated integers $n$ (the number of vertices) and $m$ (the number of edges).
* The next $m$ lines each contain three space-separated integers $u$, $v$, and $w$, describing an undirected edge between vertex $u$ and vertex $v$ with weight $w$.
* If $m = 0$, only the first line is provided.

---

### Output Format
* If vertex $n$ is unreachable from vertex $1$, print a single integer `-1`.
* Otherwise, print the total weight of the path followed by the sequence of vertices from vertex $1$ to vertex $n$ along the shortest path, separated by spaces.

---

### Constraints
* $2 \le n \le 10^4$
* $0 \le m \le 2 \times 10^4$
* $1 \le u, v \le n$
* $1 \le w \le 10^5$
* The graph may contain disconnected components, self-loops, and multiple edges between the same pair of vertices.

---

### Examples

#### Example 1
**Input:**
```text
5 6
1 2 2
2 5 5
2 3 4
1 4 1
4 3 3
3 5 1
```

**Output:**
```text
5 1 4 3 5
```

**Explanation:**
The source vertex is $1$ and destination is $5$.
The paths from $1$ to $5$ are:
* $1 \to 2 \to 5$ with cost $2 + 5 = 7$
* $1 \to 4 \to 3 \to 5$ with cost $1 + 3 + 1 = 5$
* $1 \to 2 \to 3 \to 5$ with cost $2 + 4 + 1 = 7$

The shortest path has a total weight of $5$ and visits vertices $1 \to 4 \to 3 \to 5$.
The output begins with the path weight $5$, followed by the path vertices `1 4 3 5`.

---

#### Example 2
**Input:**
```text
4 4
1 2 2
2 3 4
1 4 1
4 3 3
```

**Output:**
```text
1 1 4
```

**Explanation:**
Vertex $4$ is directly connected to vertex $1$ via an edge of weight $1$. This is the shortest path of weight $1$. The output is `1 1 4`.

---

#### Example 3
**Input:**
```text
3 1
1 2 2
```

**Output:**
```text
-1
```

**Explanation:**
Vertex $3$ has no connection to vertex $1$, so it is unreachable. Output `-1`.
