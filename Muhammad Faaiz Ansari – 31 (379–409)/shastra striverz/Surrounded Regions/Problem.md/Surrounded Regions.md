### Problem: Surrounded Regions
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Depth-First Search, Matrix

**Problem Statement**
You are given a matrix `mat` of size $N \times M$ where each cell contains either `'O'` or `'X'`.

Your task is to replace all `'O'` cells that are completely surrounded by `'X'` with `'X'`.

**Rules:**
* An `'O'` (or a connected component of `'O'`s) is considered **surrounded** if it cannot reach any border of the matrix by moving 4-directionally across adjacent `'O'` cells.
* Two `'O'` cells are considered connected if they are adjacent horizontally or vertically (**4-directionally**, not diagonally).
* Any region of connected `'O'` cells that touches the border (i.e., first row, last row, first column, or last column) is **not surrounded** and must remain `'O'`.

---

**Input Format**
* The first line contains two space-separated integers $N$ (number of rows) and $M$ (number of columns).
* The next $N$ lines each contain $M$ space-separated characters (`'X'` or `'O'`) representing the matrix.

**Output Format**
* Print $N$ lines, each containing $M$ space-separated characters representing the modified matrix.

---

**Constraints**
* $1 \le N, M \le 300$
* $\text{mat}[i][j] \in \{'X', 'O'\}$
* Connectivity is strictly **4-directional** (up, down, left, right).

---

**Example 1**
**Input:**
```text
4 4
X X X X
X O O X
X X O X
X O X X
```

**Output:**
```text
X X X X
X X X X
X X X X
X O X X
```

**Explanation:**
* The `'O'` cells at $(1, 1), (1, 2)$, and $(2, 2)$ form a connected component completely enclosed by `'X'` cells, so they are flipped to `'X'`.
* The `'O'` cell at $(3, 1)$ lies on the bottom boundary row, so it cannot be surrounded and remains `'O'`.

---

**Example 2**
**Input:**
```text
3 3
X X X
X O X
X X X
```

**Output:**
```text
X X X
X X X
X X X
```

**Explanation:**
* The solitary `'O'` at $(1, 1)$ is surrounded by `'X'` on all 4 sides, so it is replaced with `'X'`.

---

**Example 3**
**Input:**
```text
4 4
X X X O
X X X X
O X X X
X X X X
```

**Output:**
```text
X X X O
X X X X
O X X X
X X X X
```

**Explanation:**
* Both `'O'` cells lie on the boundary of the board ($(0, 3)$ and $(2, 0)$), so neither is surrounded. The matrix remains unchanged.
