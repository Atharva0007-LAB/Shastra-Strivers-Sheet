### Problem: Rotten Oranges
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Matrix

**Problem Statement**
You are given an $N \times M$ grid, where each cell can have one of three values:
* `0`: represents an empty cell.
* `1`: represents a fresh orange.
* `2`: represents a rotten orange.

Every minute, any fresh orange that is **4-directionally adjacent** (up, down, left, right) to a rotten orange becomes rotten.

Return the **minimum number of minutes** that must elapse until no cell has a fresh orange. If this is impossible (i.e. one or more fresh oranges can never rot), return `-1`.

---

**Input Format**
* The first line contains two space-separated integers $N$ (number of rows) and $M$ (number of columns).
* The next $N$ lines each contain $M$ space-separated integers (`0`, `1`, or `2`) representing the grid.

**Output Format**
* Print a single integer representing the minimum number of minutes needed for all fresh oranges to rot, or `-1` if some fresh orange can never become rotten.

---

**Constraints**
* $1 \le N, M \le 500$
* $\text{grid}[i][j] \in \{0, 1, 2\}$
* Movements and rot propagation are strictly **4-directional** (horizontal and vertical).

---

**Example 1**
**Input:**
```text
3 3
2 1 1
0 1 1
1 0 1
```

**Output:**
```text
-1
```

**Explanation:**
* The orange at the bottom left $(2, 0)$ is surrounded by empty cells `0` at $(1, 0)$ and $(2, 1)$.
* Because rot can only travel across 4-directionally adjacent fresh oranges, the rot can never reach cell $(2, 0)$.
* Since at least one fresh orange never rots, the answer is `-1`.

---

**Example 2**
**Input:**
```text
3 3
2 1 1
1 1 0
0 1 1
```

**Output:**
```text
4
```

**Explanation:**
* At minute 0: Rotten at $(0, 0)$.
* At minute 1: Oranges at $(0, 1)$ and $(1, 0)$ become rotten.
* At minute 2: Oranges at $(0, 2)$ and $(1, 1)$ become rotten.
* At minute 3: Orange at $(2, 1)$ becomes rotten.
* At minute 4: Orange at $(2, 2)$ becomes rotten.
* All fresh oranges are now rotten after 4 minutes.

---

**Example 3**
**Input:**
```text
3 3
0 1 2
0 1 2
2 1 1
```

**Output:**
```text
1
```

**Explanation:**
* All four fresh oranges at $(0, 1)$, $(1, 1)$, $(2, 1)$, and $(2, 2)$ are directly adjacent to at least one rotten orange (`2`).
* Hence, in minute 1, all fresh oranges rot simultaneously. Minimum time = `1`.

---

**Example 4**
**Input:**
```text
1 1
0
```

**Output:**
```text
0
```

**Explanation:**
* There are no fresh oranges in the grid from the beginning.
* It takes 0 minutes for all fresh oranges to rot.
