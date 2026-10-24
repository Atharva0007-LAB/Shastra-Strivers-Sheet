# Editorial: Flood Fill Algorithm

## Difficulty
Easy-Medium

## Prerequisites
- 2D Matrix Traversal
- Breadth-First Search (Queue) / Depth-First Search (Recursion or Stack)
- 4-Directional Delta Arrays

---

## Problem Statement
Given an $N \times M$ 2D grid `image`, a starting pixel coordinate $(sr, sc)$, and a replacement color `newColor`. Replace the color of the starting pixel and all pixels connected 4-directionally to it that possess the same original color with `newColor`.

---

## Detailed Approaches

### 1. The Critical Guard Condition
Before initiating any traversal, check:
```java
int originalColor = image[sr][sc];
if (originalColor == newColor) {
    return image;
}
```
If the starting pixel's color is already equal to `newColor`, no changes are needed. Failing to check this can cause an infinite loop in DFS/BFS because recoloring a pixel will not prevent it from matching `originalColor` again!

---

### 2. Traversal Algorithm (BFS / Iterative DFS)
1. Store `originalColor = image[sr][sc]`.
2. Set `image[sr][sc] = newColor`.
3. Push coordinate `(sr, sc)` to a queue or stack.
4. While the queue/stack is not empty:
   - Pop `(r, c)`.
   - Explore the 4 orthogonal neighbors using directions:
     ```java
     int[] dr = {-1, 1, 0, 0};
     int[] dc = {0, 0, -1, 1};
     ```
   - For each neighbor `(nr, nc)`:
     - Check boundary conditions: $0 \le nr < N$ and $0 \le nc < M$.
     - Check color match: If `image[nr][nc] == originalColor`:
       - Overwrite `image[nr][nc] = newColor`.
       - Enqueue / Push `(nr, nc)`.
5. Return the modified `image`.

---

## Step-by-Step Dry Run (Example 1)

Input:
`image = [[1, 1, 1], [1, 1, 0], [1, 0, 1]]`, $(sr, sc) = (1, 1)$, $newColor = 2$.
`originalColor = image[1][1] = 1`.

| Step | Current Cell | Action | Queue State |
|---|---|---|---|
| Init | - | Recolored `(1,1) = 2` | `[(1, 1)]` |
| 1 | `(1, 1)` | Check 4 neighbors: `(0, 1)`=1, `(2, 1)`=0, `(1, 0)`=1, `(1, 2)`=0. <br>Recolor `(0, 1)=2`, `(1, 0)=2`. | `[(0, 1), (1, 0)]` |
| 2 | `(0, 1)` | Neighbors: `(0, 0)`=1, `(0, 2)`=1. <br>Recolor `(0, 0)=2`, `(0, 2)=2`. | `[(1, 0), (0, 0), (0, 2)]` |
| 3 | `(1, 0)` | Neighbor: `(2, 0)`=1. <br>Recolor `(2, 0)=2`. | `[(0, 0), (0, 2), (2, 0)]` |
| 4 | `(0, 0)` | All neighbors already visited or out of bounds. | `[(0, 2), (2, 0)]` |
| 5 | `(0, 2)` | Neighbor `(1, 2)` is 0 (mismatch). | `[(2, 0)]` |
| 6 | `(2, 0)` | Neighbors are 0 or out of bounds. | `[]` |

Queue is empty. Pixel `(2, 2)` remains `1` because it is diagonally adjacent, not 4-directionally.
Final matrix:
```text
2 2 2
2 2 0
2 0 1
```

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N \times M)$
  - Each cell in the grid is visited and recolored at most once.
  - From each cell, at most 4 directional transitions are evaluated in $\mathcal{O}(1)$ time.
  - For $N, M \le 50$, total cells $N \times M \le 2500$, executing in $< 0.001$ seconds.

- **Space Complexity:** $\mathcal{O}(N \times M)$
  - In the worst case (where the whole image is monochromatic), the queue or call stack holds $\mathcal{O}(N \times M)$ elements.
