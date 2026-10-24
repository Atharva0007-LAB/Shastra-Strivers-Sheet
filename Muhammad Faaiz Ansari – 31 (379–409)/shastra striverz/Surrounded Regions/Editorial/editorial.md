# Editorial: Surrounded Regions

## Difficulty
Medium

## Prerequisites
- Multi-Source Breadth-First Search (BFS) / Depth-First Search (DFS)
- Matrix Boundary Traversals
- Connected Components

---

## The Boundary Flood Fill Insight

Directly trying to determine if an arbitrary interior `'O'` is surrounded requires searching paths until either hitting `'X'` everywhere or escaping through a boundary. Doing this for every interior `'O'` is complex and can be inefficient.

Instead, we **reverse the logic**:
> **Which `'O'` cells CANNOT be surrounded?**
> An `'O'` cell cannot be surrounded if and only if there is a 4-directional path of `'O'`s connecting it to at least one border cell of the matrix.

Therefore:
1. Every `'O'` on the boundary is inherently **safe** (not surrounded).
2. Every `'O'` connected 4-directionally to a boundary `'O'` is also **safe**.
3. All other `'O'` cells in the interior are completely enclosed by `'X'`s and must be flipped to `'X'`.

---

## 3-Phase Boundary BFS Algorithm

1. **Phase 1: Identify & Traverse from Boundaries**
   - Initialize a queue $Q$.
   - Scan all 4 boundaries:
     - Row $0$ and Row $N - 1$ for all columns $0 \le c < M$.
     - Column $0$ and Column $M - 1$ for all rows $0 \le r < N$.
   - Whenever `mat[r][c] == 'O'`:
     - Mark it as safe by replacing it with a temporary symbol: `mat[r][c] = '#'` (or using a `visited` array).
     - Push `(r, c)` into $Q$.

2. **Phase 2: Multi-Source BFS Expansion**
   - While $Q$ is not empty:
     - Pop `(r, c)`.
     - For each of the 4 orthogonal directions $(\Delta r, \Delta c) \in \{(-1, 0), (1, 0), (0, -1), (0, 1)\}$:
       - $nr = r + \Delta r$, $nc = c + \Delta c$.
       - If $0 \le nr < N$ and $0 \le nc < M$ and `mat[nr][nc] == 'O'`:
         - Mark it safe: `mat[nr][nc] = '#'`.
         - Enqueue `(nr, nc)`.

3. **Phase 3: Flip and Restore**
   - Traverse the entire grid:
     - If `mat[r][c] == 'O'`: It was never reached from any border, so it is completely surrounded $\to$ set `mat[r][c] = 'X'`.
     - If `mat[r][c] == '#'`: It is safe and connected to a border $\to$ restore `mat[r][c] = 'O'`.

---

## Step-by-Step Dry Run (Example 1)

**Input Matrix ($4 \times 4$):**
```text
X X X X
X O O X
X X O X
X O X X
```

1. **Boundary Scan:**
   - Top row: all `'X'`.
   - Bottom row: `mat[3][1] == 'O'`. Replace with `'#'` and enqueue `(3, 1)`.
   - Left col & Right col: all `'X'`.
   - Queue: `[(3, 1)]`.

2. **BFS Expansion:**
   - Pop `(3, 1)`:
     - Check neighbors: Up is `mat[2][1] == 'X'`, Left is `'X'`, Right is `'X'`, Down is out of bounds.
     - No adjacent `'O'`s.
   - Queue becomes empty.

3. **Matrix State after Phase 2:**
```text
X X X X
X O O X
X X O X
X # X X
```

4. **Phase 3 Flip & Restore:**
   - Interior `'O'`s at $(1, 1), (1, 2), (2, 2)$ $\to$ converted to `'X'`.
   - `'#'` at $(3, 1)$ $\to$ restored to `'O'`.

**Final Output:**
```text
X X X X
X X X X
X X X X
X O X X
```

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N \times M)$
  - Scanning boundary cells takes $\mathcal{O}(N + M)$.
  - Each cell is enqueued and dequeued at most once, and each transition takes $\mathcal{O}(1)$ time.
  - Final traversal over the grid takes $\mathcal{O}(N \times M)$.
  - Total Time: $\mathcal{O}(N \times M)$. For $N, M \le 300$, $N \times M \le 90,000$, executing in $< 0.02$ seconds.

- **Space Complexity:** $\mathcal{O}(N \times M)$
  - The queue holds at most $\mathcal{O}(N \times M)$ cells in the worst case.
  - Modifying the matrix in-place with the `'#'` marker requires $\mathcal{O}(1)$ additional memory beyond the queue.
