### Problem: Distance of Nearest Cell Having 1
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Matrix

**Problem Statement**
Given a binary grid of size $N \times M$. Find the distance of the nearest cell having value `1` in the grid for each cell.

The distance between two cells $(i_1, j_1)$ and $(i_2, j_2)$ is defined as the **Manhattan distance**:
$$\text{dist} = |i_1 - i_2| + |j_1 - j_2|$$

Return an $N \times M$ matrix `dist` where `dist[i][j]` represents the minimum distance from cell $(i, j)$ to any cell containing `1`.

---

**Input Format**
* The first line contains two space-separated integers $N$ (number of rows) and $M$ (number of columns).
* The next $N$ lines each contain $M$ space-separated integers (`0` or `1`) representing the binary grid.

**Output Format**
* Print $N$ lines, each containing $M$ space-separated integers representing the distance matrix.

---

**Constraints**
* $1 \le N, M \le 500$
* $\text{grid}[i][j] \in \{0, 1\}$
* There is at least one `1` in the grid.
* Movement is 4-directional (up, down, left, right).

---

**Example 1**
**Input:**
```text
3 4
0 1 1 0
1 1 0 0
0 0 1 1
```

**Output:**
```text
1 0 0 1
0 0 1 1
1 1 0 0
```

**Explanation:**
* Cells with value `1` have distance `0`: $(0, 1), (0, 2), (1, 0), (1, 1), (2, 2), (2, 3)$.
* The cell at $(0, 0)$ has distance `1` (adjacent to $(0, 1)$ and $(1, 0)$).
* The cell at $(0, 3)$ has distance `1` (adjacent to $(0, 2)$).
* The cells at $(1, 2)$ and $(1, 3)$ have distance `1` (adjacent to $(0, 2)$ and $(2, 3)$ respectively).
* The cells at $(2, 0)$ and $(2, 1)$ have distance `1` (adjacent to $(1, 0)$ and $(1, 1)$ respectively).

---

**Example 2**
**Input:**
```text
3 3
1 0 1
1 1 0
1 0 0
```

**Output:**
```text
0 1 0
0 0 1
0 1 2
```

**Explanation:**
* Cells with `1` have distance `0`.
* Cell $(0, 1)$ is adjacent to $(0, 0)$ and $(0, 2)$, distance = `1`.
* Cell $(1, 2)$ is adjacent to $(0, 2)$ and $(1, 1)$, distance = `1`.
* Cell $(2, 1)$ is adjacent to $(2, 0)$ and $(1, 1)$, distance = `1`.
* Cell $(2, 2)$ has nearest `1`s at $(1, 1)$ or $(2, 0)$, Manhattan distance $|2-1| + |2-1| = 2$.

---

**Example 3**
**Input:**
```text
2 2
0 1
1 0
```

**Output:**
```text
1 0
0 1
```

**Explanation:**
* Cells $(0, 1)$ and $(1, 0)$ contain `1`, distance = `0`.
* Cells $(0, 0)$ and $(1, 1)$ contain `0` and are adjacent to `1`s, distance = `1`.
