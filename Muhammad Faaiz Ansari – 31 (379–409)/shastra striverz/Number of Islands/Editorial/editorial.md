# Editorial: Number of Islands

## Difficulty
Medium

## Prerequisites
- 2D Grid / Matrix Representation
- Breadth First Search (BFS) / Depth First Search (DFS)
- 8-Directional Delta Arrays

---

## Problem Statement
Given an $N \times M$ grid containing `'0'`s (water) and `'1'`s (land), find the total number of islands. An island is a maximal connected component of land cells reachable by traveling **in all 8 directions** (up, down, left, right, and 4 diagonals).

> [!IMPORTANT]
> Unlike standard 4-directional grid problems, this problem allows **diagonal connections**. Cells touching diagonally belong to the same island!

---

## Algorithmic Approaches

### Approach 1: Breadth First Search (BFS) / Iterative DFS
We maintain a boolean 2D array `visited[N][M]` to keep track of processed cells:
1. Initialize `islandCount = 0`.
2. Iterate through every cell `(r, c)` in the grid:
   - If `grid[r][c] == '1'` and `!visited[r][c]`:
     - We have discovered a new island $\to$ `islandCount++`.
     - Start a traversal (BFS or stack-based DFS) from `(r, c)`.
     - In each step of the traversal:
       - Explore all $8$ adjacent cells using directional offsets:
         $$\Delta r \in \{-1, 0, 1\}, \quad \Delta c \in \{-1, 0, 1\} \quad (\text{excluding } \Delta r = \Delta c = 0)$$
       - For each valid neighbor $(nr, nc)$ within grid boundaries:
         - If `grid[nr][nc] == '1'` and `!visited[nr][nc]`:
           - Mark `visited[nr][nc] = true`.
           - Push $(nr, nc)$ to the queue/stack.
3. Return `islandCount`.

#### Direction Vectors:
```java
static final int[] dr = {-1, -1, -1,  0, 0,  1, 1, 1};
static final int[] dc = {-1,  0,  1, -1, 1, -1, 0, 1};
```

---

## Step-by-Step Dry Run (Example 2)

Input:
```text
1 0 0 0 1
0 1 0 1 0
0 0 1 0 0
0 1 0 1 0
```

1. Start at `(0, 0)`: It is `'1'` and unvisited. `islandCount = 1`.
2. From `(0, 0)`, the diagonal neighbor is `(1, 1)` $\to$ visited.
3. From `(1, 1)`, the diagonal neighbor is `(2, 2)` $\to$ visited.
4. From `(2, 2)`:
   - Diagonally reaches `(1, 3)` and `(3, 1)` $\to$ both visited.
5. From `(1, 3)`:
   - Diagonally reaches `(0, 4)` and `(2, 2)` (visited) and `(2, 4)`.
6. From `(3, 1)`:
   - Diagonally reaches `(3, 3)`.
7. All 8 land cells are connected through diagonals, forming a single contiguous landmass.
8. Output = **1**.

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N \times M)$
  - Every cell $(r, c)$ is checked in the outer loops.
  - Each land cell `'1'` is added to the traversal queue/stack at most once.
  - From each cell, exactly $8$ neighboring coordinates are verified in $\mathcal{O}(1)$ time.
  - Total operations $\approx 8 \times N \times M$. With $N, M \le 300$, $N \times M \le 9 \times 10^4$, which executes in under $0.02$ seconds.

- **Space Complexity:** $\mathcal{O}(N \times M)$
  - `visited` boolean matrix of size $N \times M$.
  - Queue / Stack can hold at most $\mathcal{O}(N \times M)$ elements in the worst case (when the entire grid is land).
