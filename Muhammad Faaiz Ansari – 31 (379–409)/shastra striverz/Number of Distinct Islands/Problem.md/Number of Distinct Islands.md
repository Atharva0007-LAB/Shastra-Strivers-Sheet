### Problem: Number of Distinct Islands
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Depth-First Search, Matrix, Hash Set

**Problem Statement**
You are given a 2D binary matrix `grid` of size $N \times M$, where:
* `1` represents land.
* `0` represents water.

An island is a maximum connected component of `1`s formed by connecting adjacent land cells **4-directionally** (horizontal or vertical, not diagonal).

Find the total number of **distinct island shapes**. 

Two islands are considered identical if and only if one island can be exactly translated to coincide with the other. **Rotations and reflections do not count as identical.**

---

**Input Format**
* The first line contains two space-separated integers $N$ (number of rows) and $M$ (number of columns).
* The next $N$ lines each contain $M$ space-separated integers (`0` or `1`) representing the grid.

**Output Format**
* Print a single integer representing the number of distinct island shapes.

---

**Constraints**
* $1 \le N, M \le 500$
* $\text{grid}[i][j] \in \{0, 1\}$
* Connectivity is strictly **4-directional** (up, down, left, right).

---

**Example 1**
**Input:**
```text
4 5
1 1 0 0 0
1 1 0 0 0
0 0 0 1 1
0 0 0 1 1
```

**Output:**
```text
1
```

**Explanation:**
* There are two islands: one at the top-left ($2 \times 2$ block) and one at the bottom-right ($2 \times 2$ block).
* Both islands have the exact same shape and dimensions ($2 \times 2$ block of `1`s).
* Hence, there is only $1$ distinct island shape.

---

**Example 2**
**Input:**
```text
4 5
1 1 0 1 1
1 0 0 0 0
0 0 0 0 1
1 1 0 1 1
```

**Output:**
```text
3
```

**Explanation:**
There are four separate islands:
1. Top-left: cells $(0,0), (0,1), (1,0)$ — an "L" shape.
2. Top-right: cells $(0,3), (0,4)$ — a $1 \times 2$ horizontal domino.
3. Bottom-left: cells $(3,0), (3,1)$ — a $1 \times 2$ horizontal domino (same shape as #2).
4. Bottom-right: cells $(2,4), (3,3), (3,4)$ — an inverted "L" shape.
* Although Island 4 is a reflection/rotation of Island 1, reflections and rotations are not considered identical.
* Thus, there are $3$ distinct island shapes.

---

**Example 3**
**Input:**
```text
4 5
1 1 0 0 0
1 1 0 0 0
0 0 0 0 0
0 0 0 1 1
```

**Output:**
```text
2
```

**Explanation:**
* The top-left island is a $2 \times 2$ square.
* The bottom-right island is a $1 \times 2$ rectangle.
* They have different shapes, giving $2$ distinct islands.
