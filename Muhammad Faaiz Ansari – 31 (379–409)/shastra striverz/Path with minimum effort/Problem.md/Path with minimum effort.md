### Problem: Path With Minimum Effort
**Difficulty:** Medium | **Tags:** Graphs, Dijkstra's Algorithm, Priority Queue, Matrix, Binary Search

---

### Problem Statement
A hiker is preparing for an upcoming hike. You are given `heights`, a 2D matrix of size $\text{rows} \times \text{columns}$, where $\text{heights}[r][c]$ represents the elevation of cell $(r, c)$.

The hiker starts at the top-left cell $(0, 0)$ and wishes to reach the bottom-right cell $(\text{rows} - 1, \text{columns} - 1)$. From any cell, the hiker can move in four directions: **up**, **down**, **left**, or **right**.

The hiker wants to choose a route that requires the **minimum effort**.
* A route's **effort** is defined as the **maximum absolute difference** in heights between any two consecutive cells along the route.

Find the minimum effort required to travel from $(0, 0)$ to $(\text{rows} - 1, \text{columns} - 1)$.

---

### Input Format
* The first line contains two space-separated integers $\text{rows}$ and $\text{columns}$, representing the grid dimensions.
* The next $\text{rows}$ lines each contain $\text{columns}$ space-separated integers representing the 2D matrix `heights`.

---

### Output Format
* Print a single integer representing the minimum effort required to travel from $(0, 0)$ to $(\text{rows} - 1, \text{columns} - 1)$.

---

### Constraints
* $1 \le \text{rows}, \text{columns} \le 100$
* $1 \le \text{heights}[r][c] \le 10^6$

---

### Examples

#### Example 1
**Input:**
```text
3 3
1 2 2
3 8 2
5 3 5
```

**Output:**
```text
2
```

**Explanation:**
The route $(0, 0) \to (1, 0) \to (2, 0) \to (2, 1) \to (2, 2)$ visits heights $[1, 3, 5, 3, 5]$.
The absolute differences between consecutive cells are:
* $|1 - 3| = 2$
* $|3 - 5| = 2$
* $|5 - 3| = 2$
* $|3 - 5| = 2$

The maximum difference along this route is $2$, which is better than the alternative route $[1, 2, 2, 2, 5]$ which has a maximum difference of $|2 - 5| = 3$.

---

#### Example 2
**Input:**
```text
3 3
1 2 3
3 8 4
5 3 5
```

**Output:**
```text
1
```

**Explanation:**
The route $(0, 0) \to (0, 1) \to (0, 2) \to (1, 2) \to (2, 2)$ visits heights $[1, 2, 3, 4, 5]$.
The maximum absolute difference between consecutive cells along this path is $1$.

---

#### Example 3
**Input:**
```text
5 5
1 2 1 1 1
1 2 1 2 1
1 2 1 2 1
1 2 1 2 1
1 1 1 2 1
```

**Output:**
```text
0
```

**Explanation:**
There exists a winding route following only cells of height $1$ from $(0, 0)$ to $(4, 4)$. The maximum difference along this route is $0$.
