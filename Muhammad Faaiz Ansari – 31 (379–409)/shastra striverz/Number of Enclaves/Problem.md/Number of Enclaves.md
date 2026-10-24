### Problem: Number of Enclaves
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Depth-First Search, Matrix

**Problem Statement**
You are given an $N \times M$ binary matrix `grid`, where:
* `0` represents a sea cell.
* `1` represents a land cell.

A move consists of walking from one land cell to another adjacent **(4-directionally)** land cell or walking off the boundary of the grid.

Find the number of **land cells** in the grid for which we **cannot** walk off the boundary of the grid in any number of moves.

---

**Input Format**
* The first line contains two space-separated integers $N$ (number of rows) and $M$ (number of columns).
* The next $N$ lines each contain $M$ space-separated integers (`0` or `1`) representing the grid.

**Output Format**
* Print a single integer representing the number of land cells from which it is impossible to walk off the boundary of the grid.

---

**Constraints**
* $1 \le N, M \le 500$
* $\text{grid}[i][j] \in \{0, 1\}$
* Connectivity is strictly **4-directional** (up, down, left, right).

---

**Example 1**
**Input:**
```text
4 4
0 0 0 0
1 0 1 0
0 1 1 0
0 0 0 0
```

**Output:**
```text
3
```

**Explanation:**
* The cell at $(1, 0)$ is on the left boundary, so we can walk off the grid boundary from it.
* The land cells at $(1, 2)$, $(2, 1)$, and $(2, 2)$ are connected to each other but completely surrounded by sea cells (`0`s), with no path connecting them to any boundary cell.
* Thus, there are $3$ enclave land cells.

---

**Example 2**
**Input:**
```text
5 4
0 0 0 1
0 0 0 1
0 1 1 0
0 0 1 0
0 0 0 0
```

**Output:**
```text
3
```

**Explanation:**
* The cells at $(0, 3)$ and $(1, 3)$ touch the right/top boundary.
* The cells at $(2, 1)$, $(2, 2)$, and $(3, 2)$ form an isolated island surrounded by water.
* Therefore, the number of enclaves is $3$.

---

**Example 3**
**Input:**
```text
4 4
0 0 0 1
0 1 1 0
0 1 1 0
0 0 0 0
```

**Output:**
```text
4
```

**Explanation:**
* The cell $(0, 3)$ is directly on the boundary.
* The $2 \times 2$ square of land cells from $(1, 1)$ to $(2, 2)$ cannot reach any boundary.
* Hence, the answer is $4$.
