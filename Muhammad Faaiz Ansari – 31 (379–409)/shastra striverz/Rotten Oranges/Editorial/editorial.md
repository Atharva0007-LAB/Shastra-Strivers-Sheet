# Editorial: Rotten Oranges

## Difficulty
Medium

## Prerequisites
- Breadth-First Search (BFS)
- Multi-Source Level-Order Traversal
- 2D Matrix Traversals & 4-Directional Vectors

---

## Why BFS and NOT DFS?

In this problem, **all rotten oranges spread their rot simultaneously** every minute. 
- A standard Depth-First Search (DFS) follows a single branch as deeply as possible, which corresponds to sequential rotting rather than simultaneous rotting.
- Breadth-First Search (BFS), on the other hand, explores all nodes equidistant from starting points layer by layer (level by level). Each BFS "wave" corresponds exactly to **one elapsed minute**.
- Therefore, **Multi-Source BFS** is the natural and optimal choice.

---

## Detailed Algorithm

1. **Initial Grid Scan:**
   - Maintain a counter `freshCount = 0`.
   - Maintain a queue $Q$ to store cell coordinates `(r, c)`.
   - Traverse the entire grid:
     - If `grid[r][c] == 2` (rotten), push `(r, c)` into $Q$.
     - If `grid[r][c] == 1` (fresh), increment `freshCount++`.
   - **Base Case:** If `freshCount == 0`, no oranges need to rot, return `0`.

2. **Simultaneous Level-Order BFS:**
   - Maintain a variable `minutesElapsed = 0`.
   - While $Q$ is not empty and `freshCount > 0`:
     - Determine the current number of rotten oranges in the queue: `levelSize = Q.size()`.
     - Increment `minutesElapsed++`.
     - Process exactly `levelSize` elements:
       - Dequeue `(r, c)`.
       - For each of the 4 orthogonal directions $(\Delta r, \Delta c) \in \{(-1, 0), (1, 0), (0, -1), (0, 1)\}$:
         - $nr = r + \Delta r$, $nc = c + \Delta c$.
         - If $0 \le nr < N$ and $0 \le nc < M$ and `grid[nr][nc] == 1`:
           - Turn it rotten: `grid[nr][nc] = 2`.
           - Decrement fresh count: `freshCount--`.
           - Enqueue `(nr, nc)`.

3. **Final Verification:**
   - After BFS terminates, check `freshCount`.
   - If `freshCount == 0`: all fresh oranges rotted, return `minutesElapsed`.
   - If `freshCount > 0`: at least one fresh orange was unreachable by rot, return `-1`.

---

## Step-by-Step Dry Run (Example 2)

**Input Grid ($3 \times 3$):**
```text
2 1 1
1 1 0
0 1 1
```

- Initial state:
  - Rotten oranges at: `[(0, 0)]`.
  - Fresh oranges count: `freshCount = 6`.
  - `minutesElapsed = 0`.

| Minute | Processing Cells | Rot Spread To Neighbors | Fresh Left | Queue for Next Minute |
|:---:|:---|:---|:---:|:---|
| **0** | Initial Queue | - | 6 | `[(0, 0)]` |
| **1** | `(0, 0)` | `(0, 1)`, `(1, 0)` turn 2 | 4 | `[(0, 1), (1, 0)]` |
| **2** | `(0, 1)`, `(1, 0)` | `(0, 2)` (from `(0, 1)`), `(1, 1)` (from both) | 2 | `[(0, 2), (1, 1)]` |
| **3** | `(0, 2)`, `(1, 1)` | `(2, 1)` (from `(1, 1)`) | 1 | `[(2, 1)]` |
| **4** | `(2, 1)` | `(2, 2)` (from `(2, 1)`) | 0 | `[(2, 2)]` |

- Since `freshCount == 0`, loop terminates.
- **Result:** `4` minutes.

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N \times M)$
  - Scanning the grid initially takes $\mathcal{O}(N \times M)$.
  - In BFS, each cell is pushed and popped from the queue at most once.
  - From each cell, 4 adjacent directions are verified in $\mathcal{O}(1)$ time.
  - For maximum constraints $N, M \le 500$, total cells $N \times M \le 250,000$, executing in $< 0.05$ seconds.

- **Space Complexity:** $\mathcal{O}(N \times M)$
  - In the worst case, the queue contains $\mathcal{O}(N \times M)$ coordinates.
  - In-place modification of `grid` avoids allocating an extra `visited` array, keeping memory overhead minimal.
