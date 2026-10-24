# Editorial: Number of Enclaves

## Difficulty
Medium

## Prerequisites
- Multi-Source Breadth-First Search (BFS) / Depth-First Search (DFS)
- 2D Grid Traversals & 4-Directional Vectors
- Connected Components

---

## Problem Intuition

In this problem, we are asked to count the number of land cells (`1`s) that **cannot** escape off the boundaries of the grid by traversing adjacent land cells.

Key observations:
1. Any land cell that lies on the boundary (i.e., row $0$, row $N-1$, column $0$, column $M-1$) can escape off the boundary in 1 move.
2. Any land cell connected 4-directionally to a boundary land cell can also escape by following that path to the boundary.
3. Therefore, all land cells that can escape belong to connected components that touch at least one boundary land cell.
4. Conversely, an **enclave** is any connected component of land cells that does **not** touch any boundary cell. All cells within such components are completely enclosed by sea (`0`s) and the interior.

Instead of running BFS/DFS from every interior land cell (which could be costly and require tracking boundary-reachability per component), we can **reverse the perspective**:
> **Start a multi-source traversal (BFS or DFS) from all boundary land cells!**
> Mark all land cells reachable from the boundary as visited / non-enclaves.
> Afterwards, any `1` remaining unvisited must be an enclave cell.

---

## Multi-Source BFS Algorithm

1. Initialize a `visited[N][M]` boolean matrix to `false`.
2. Initialize a queue $Q$.
3. Scan all 4 boundaries of the grid:
   - Row $0$ and Row $N - 1$ for all columns $0 \le c < M$.
   - Column $0$ and Column $M - 1$ for all rows $0 \le r < N$.
   - If `grid[r][c] == 1` and `!visited[r][c]`:
     - Mark `visited[r][c] = true`.
     - Enqueue `(r, c)`.
4. While $Q$ is not empty:
   - Dequeue `(r, c)`.
   - For each of the 4 orthogonal directions $(\Delta r, \Delta c) \in \{(-1, 0), (1, 0), (0, -1), (0, 1)\}$:
     - $nr = r + \Delta r$, $nc = c + \Delta c$.
     - If $0 \le nr < N$ and $0 \le nc < M$:
       - If `grid[nr][nc] == 1` and `!visited[nr][nc]`:
         - Mark `visited[nr][nc] = true`.
         - Enqueue `(nr, nc)`.
5. Count enclaves:
   - Iterate through every cell $(i, j)$ in the grid.
   - If `grid[i][j] == 1` and `!visited[i][j]`, increment the enclave counter.
6. Return the total enclave count.

---

## Step-by-Step Dry Run (Example 1)

**Input Grid ($4 \times 4$):**
```text
0 0 0 0
1 0 1 0
0 1 1 0
0 0 0 0
```

1. **Identify Boundary Land Cells:**
   - Row 0: all 0s.
   - Row 3: all 0s.
   - Col 0: cell $(1, 0)$ is `1`. Mark visited, push $(1, 0)$ to queue.
   - Col 3: all 0s.
   - Initial Queue: `[(1, 0)]`.

2. **BFS Expansion from Boundary:**
   - Pop `(1, 0)`:
     - Check neighbors:
       - Up: `(0, 0)` is 0.
       - Down: `(2, 0)` is 0.
       - Left: out of bounds.
       - Right: `(1, 1)` is 0.
     - No adjacent `1`s. Queue empty.

3. **Count Remaining Unvisited `1`s:**
   - Cell $(1, 2)$: `grid == 1`, `!visited` $\to$ Count = 1.
   - Cell $(2, 1)$: `grid == 1`, `!visited` $\to$ Count = 2.
   - Cell $(2, 2)$: `grid == 1`, `!visited` $\to$ Count = 3.
   - All other cells are 0 or visited.

**Final Answer:** `3`.

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N \times M)$
  - Enqueueing boundary cells takes $\mathcal{O}(N + M)$ time.
  - Each cell is enqueued and dequeued at most once during BFS traversal, taking $\mathcal{O}(1)$ time per transition across 4 neighbors.
  - Final grid scan takes $\mathcal{O}(N \times M)$.
  - Total Time: $\mathcal{O}(N \times M)$. For $N, M \le 500$, $N \times M \le 250,000$ operations, which easily executes within $0.05$ seconds.

- **Space Complexity:** $\mathcal{O}(N \times M)$
  - The `visited` array consumes $\mathcal{O}(N \times M)$ auxiliary space.
  - The queue holds at most $N \times M$ coordinates in the worst case.
  - Linear in grid dimensions, easily fitting within typical memory limits (256MB).
