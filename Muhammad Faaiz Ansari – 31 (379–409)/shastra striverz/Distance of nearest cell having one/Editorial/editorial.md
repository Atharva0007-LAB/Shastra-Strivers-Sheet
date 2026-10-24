# Editorial: Distance of Nearest Cell Having 1

## Difficulty
Medium

## Prerequisites
- Breadth-First Search (BFS)
- Multi-Source Traversal
- Manhattan Distance & 4-Directional Vectors

---

## The Power of Multi-Source BFS

A naive approach would be to iterate over every cell containing `0` and run an independent BFS to find the closest `1`.
- For an $N \times M$ grid, there can be up to $N \times M$ zeroes.
- Each BFS explores up to $\mathcal{O}(N \times M)$ cells.
- **Naive Time Complexity:** $\mathcal{O}((N \times M)^2) \approx (250,000)^2 \approx 6.25 \times 10^{10}$ operations, which causes a **Time Limit Exceeded (TLE)**.

### The Inversion Insight: Start from the 1s!
Instead of searching from each `0` toward `1`, we **reverse the problem**:
1. All cells that already contain `1` have a distance of `0` to themselves.
2. We treat **all cells containing `1` as multi-source starting nodes** at time step 0.
3. We push all `1`s into a queue with initial distance `0`.
4. We expand outward layer-by-layer (BFS) into neighboring unvisited `0` cells:
   - When a neighbor `(nr, nc)` is first reached from cell `(r, c)`, its shortest distance is guaranteed to be $\text{dist}[nr][nc] = \text{dist}[r][c] + 1$.
   - Each cell is visited **at most once**, guaranteeing an optimal $\mathcal{O}(N \times M)$ total time complexity.

---

## Step-by-Step Algorithm

1. Initialize a 2D integer array `dist` of size $N \times M$, filled with `-1` (indicating unvisited).
2. Initialize a queue $Q$ to store cell coordinates `(r, c)`.
3. Traverse the grid:
   - For every cell $(r, c)$ where `grid[r][c] == 1`:
     - Set `dist[r][c] = 0`.
     - Push `(r, c)` into $Q$.
4. While $Q$ is not empty:
   - Pop front coordinate `(r, c)`.
   - For each of the 4 orthogonal directions $(\Delta r, \Delta c) \in \{(-1, 0), (1, 0), (0, -1), (0, 1)\}$:
     - $nr = r + \Delta r$, $nc = c + \Delta c$.
     - If $0 \le nr < N$ and $0 \le nc < M$ and `dist[nr][nc] == -1`:
       - Set `dist[nr][nc] = dist[r][c] + 1`.
       - Push `(nr, nc)` into $Q$.
5. Return the completed `dist` matrix.

---

## Step-by-Step Dry Run (Example 2)

**Input Grid ($3 \times 3$):**
```text
1 0 1
1 1 0
1 0 0
```

1. **Initialization:**
   - 1s are at: `(0, 0), (0, 2), (1, 0), (1, 1), (2, 0)`.
   - Set their distances to `0` and enqueue all of them.
   - Initial distance matrix:
     ```text
      0 -1  0
      0  0 -1
      0 -1 -1
     ```

2. **BFS Wave 1 (Distance 1):**
   - Pop `(0, 0)`: neighbor `(0, 1)` set to `1`, enqueued.
   - Pop `(0, 2)`: neighbor `(1, 2)` set to `1`, enqueued.
   - Pop `(1, 0)`: all neighbors already visited.
   - Pop `(1, 1)`: neighbor `(2, 1)` set to `1`, enqueued.
   - Pop `(2, 0)`: all neighbors already visited.
   - Queue now contains: `[(0, 1), (1, 2), (2, 1)]`.
   - Distance matrix:
     ```text
      0  1  0
      0  0  1
      0  1 -1
     ```

3. **BFS Wave 2 (Distance 2):**
   - Pop `(0, 1)`: no unvisited neighbors.
   - Pop `(1, 2)`: neighbor `(2, 2)` set to `dist[1][2] + 1 = 2`, enqueued.
   - Pop `(2, 1)`: neighbor `(2, 2)` already visited.
   - Pop `(2, 2)`: no unvisited neighbors.
   - Queue is now empty.

**Final Distance Matrix:**
```text
0 1 0
0 0 1
0 1 2
```

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N \times M)$
  - Initial scan takes $\mathcal{O}(N \times M)$ to enqueue all `1`s.
  - Every cell is enqueued and dequeued exactly once in the multi-source BFS.
  - Exactly 4 neighbors are examined per cell in $\mathcal{O}(1)$ time.
  - Total Time: $\mathcal{O}(N \times M)$. For $N, M \le 500$, $N \times M \le 250,000$, running in $< 0.05$ seconds.

- **Space Complexity:** $\mathcal{O}(N \times M)$
  - The `dist` matrix stores the answer and serves as the visited tracker, using $\mathcal{O}(N \times M)$ space.
  - The queue holds at most $N \times M$ elements at any given time.
