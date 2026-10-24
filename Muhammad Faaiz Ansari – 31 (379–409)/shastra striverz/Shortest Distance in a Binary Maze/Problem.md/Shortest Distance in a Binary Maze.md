### Problem: Shortest Distance in a Binary Maze
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Matrix, Shortest Path

---

### Problem Statement
Given an $n \times m$ binary matrix `grid` where each cell contains either $0$ (blocked obstacle) or $1$ (open traversable path), determine the shortest distance (in terms of number of steps/edges) from a given source cell to a destination cell.

You can move to an adjacent cell (up, down, left, or right) if that adjacent cell lies within matrix boundaries and has a value of $1$.

* If the destination cell is not reachable from the source cell, return `-1`.
* If the source cell is the same as the destination cell, the distance is $0$.

---

### Input Format
* The first line contains two space-separated integers $n$ and $m$ representing the number of rows and columns of the matrix.
* The second line contains two space-separated integers $src_r$ and $src_c$ denoting the 0-indexed coordinates of the source cell.
* The third line contains two space-separated integers $dst_r$ and $dst_c$ denoting the 0-indexed coordinates of the destination cell.
* The next $n$ lines each contain $m$ space-separated integers ($0$ or $1$) representing the matrix `grid`.

---

### Output Format
* Print a single integer representing the shortest path distance (minimum number of steps) from the source cell to the destination cell, or `-1` if unreachable.

---

### Constraints
* $1 \le n, m \le 500$
* $\text{grid}[i][j] \in \{0, 1\}$
* $0 \le src_r, dst_r < n$
* $0 \le src_c, dst_c < m$
* $\text{grid}[src_r][src_c] = 1$ and $\text{grid}[dst_r][dst_c] = 1$

---

### Examples

#### Example 1
**Input:**
```text
5 4
0 1
2 2
1 1 1 1
1 1 0 1
1 1 1 1
1 1 0 0
1 0 0 1
```

**Output:**
```text
3
```

**Explanation:**
The source is at $(0, 1)$ and destination is at $(2, 2)$.
A shortest path is:
1. $(0, 1) \to (1, 1)$ (distance 1)
2. $(1, 1) \to (2, 1)$ (distance 2)
3. $(2, 1) \to (2, 2)$ (distance 3)
The shortest distance is $3$.

---

#### Example 2
**Input:**
```text
4 5
0 0
3 4
1 1 1 1 1
1 1 1 1 1
1 1 1 1 0
1 0 1 0 1
```

**Output:**
```text
-1
```

**Explanation:**
The destination $(3, 4)$ is surrounded by $0$s or matrix borders and has no path of $1$s connecting it to $(0, 0)$. Output `-1`.

---

#### Example 3
**Input:**
```text
3 3
0 0
2 2
1 0 1
1 1 0
1 1 1
```

**Output:**
```text
4
```

**Explanation:**
The path from $(0, 0)$ to $(2, 2)$ travels $(0, 0) \to (1, 0) \to (2, 0) \to (2, 1) \to (2, 2)$ taking $4$ steps.
