### Problem: Number of Islands
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Depth-First Search, Matrix, Connected Components

**Problem Statement**
Given a 2D grid of size $N \times M$ ($N$ rows and $M$ columns) consisting of `'0'`s (Water) and `'1'`s (Land).

An **island** is surrounded by water and is formed by connecting adjacent lands horizontally, vertically, or diagonally (**in all 8 directions**).

Find the total number of **islands** in the grid.

**Input Format**
* The first line contains two space-separated integers $N$ (number of rows) and $M$ (number of columns).
* The next $N$ lines each contain $M$ space-separated characters (`'0'` or `'1'`), representing the grid.

**Output Format**
* Print a single integer representing the total number of islands.

**Constraints**
* $1 \le N, M \le 300$
* $\text{grid}[i][j] \in \{'0', '1'\}$
* Two land cells belong to the same island if they are adjacent horizontally, vertically, or diagonally (8-direction connectivity).

**Example 1**
**Input:**
```text
4 5
1 1 1 0 1
1 0 0 0 0
1 1 1 0 1
0 0 0 1 1
```

**Output:**
```text
2
```

**Explanation:**
- Island 1: The 'C'-shaped landmass on the left: rows 0, 1, 2.
- Island 2: The land cells on the right: cell (0, 4) is connected diagonally to (2, 4) through (1, ?) wait, let's look at row 2 col 4 and row 3 cols 3-4 which are all connected. The cells at `(0, 4)` and `(2, 4)` are not connected to the left island. Total islands = $2$.

---

**Example 2**
**Input:**
```text
4 5
1 0 0 0 1
0 1 0 1 0
0 0 1 0 0
0 1 0 1 0
```

**Output:**
```text
1
```

**Explanation:**
All the `'1'`s are diagonally adjacent to each other forming an 'X'-like pattern. Because diagonal connectivity (8 directions) is permitted, all land cells belong to **1** single contiguous island.

---

**Example 3**
**Input:**
```text
4 5
1 1 1 1 0
1 1 0 1 0
1 1 0 0 0
0 0 0 0 0
```

**Output:**
```text
1
```

**Explanation:**
All `'1'` cells are connected, forming a single island.
