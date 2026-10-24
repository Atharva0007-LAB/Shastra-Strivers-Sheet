# Editorial: Number of Distinct Islands

## Difficulty
Medium

## Prerequisites
- Connected Components (Breadth-First Search or Depth-First Search)
- Shape Canonicalization & Translation Invariance
- Hash Sets / Hashing Tuples

---

## The Core Challenge: Representing Shapes

In standard "Number of Islands", we simply count how many connected components of `1`s exist. Here, however, we must determine how many **geometrically unique** shapes exist under translation.

Key Properties:
1. **Translation Invariant:** Two islands are identical if shifting all coordinates by a fixed $(\Delta x, \Delta y)$ aligns them perfectly.
2. **Rotation & Reflection are NOT Invariant:** An "L" shape rotated $90^\circ$ or flipped is considered a **different** shape.

---

## Canonical Shape Representation via Relative Coordinates

When scanning the grid in standard row-major order (top to bottom, left to right):
1. The first cell encountered for any island, say $(r_0, c_0)$, is uniquely defined as its **top-most, left-most anchor cell**.
2. For every cell $(r, c)$ belonging to this island, compute its relative coordinate offset:
   $$\Delta r = r - r_0, \quad \Delta c = c - c_0$$
3. Notice that:
   - Since $(r_0, c_0)$ is the first row encountered, $\Delta r \ge 0$ for all cells in the island.
   - $\Delta c$ can be positive, zero, or negative (if cells extend to the left of $c_0$ in lower rows).
4. **Sort** all relative coordinate pairs $(\Delta r, \Delta c)$ belonging to the island lexicographically.
5. Serialize the sorted list into a string (e.g. `(0,0)(1,-1)(1,0)`) or an integer tuple list, and insert it into a `HashSet`.
6. Since a `HashSet` only retains unique entries, its size at the end represents the exact number of distinct island shapes.

---

## Step-by-Step Dry Run (Example 2)

**Input Grid ($4 \times 5$):**
```text
1 1 0 1 1
1 0 0 0 0
0 0 0 0 1
1 1 0 1 1
```

1. **Island 1 (Top-Left):**
   - Anchor: $(0, 0)$.
   - Island cells: $(0, 0), (0, 1), (1, 0)$.
   - Relative offsets:
     - $(0-0, 0-0) = (0, 0)$
     - $(0-0, 1-0) = (0, 1)$
     - $(1-0, 0-0) = (1, 0)$
   - Canonical shape string: `(0,0)(0,1)(1,0)`
   - Insert into HashSet $\to$ Set: `["(0,0)(0,1)(1,0)"]`.

2. **Island 2 (Top-Right):**
   - Anchor: $(0, 3)$.
   - Island cells: $(0, 3), (0, 4)$.
   - Relative offsets:
     - $(0-0, 3-3) = (0, 0)$
     - $(0-0, 4-3) = (0, 1)$
   - Canonical shape string: `(0,0)(0,1)`
   - Insert into HashSet $\to$ Set: `["(0,0)(0,1)(1,0)", "(0,0)(0,1)"]`.

3. **Island 3 (Right Side):**
   - Anchor: $(2, 4)$.
   - Island cells: $(2, 4), (3, 3), (3, 4)$ (all 4-directionally connected).
   - Relative offsets:
     - $(2-2, 4-4) = (0, 0)$
     - $(3-2, 3-4) = (1, -1)$
     - $(3-2, 4-4) = (1, 0)$
   - Sorted canonical shape string: `(0,0)(1,-1)(1,0)`
   - Insert into HashSet $\to$ Set: `["(0,0)(0,1)(1,0)", "(0,0)(0,1)", "(0,0)(1,-1)(1,0)"]`.

4. **Island 4 (Bottom-Left):**
   - Anchor: $(3, 0)$.
   - Island cells: $(3, 0), (3, 1)$.
   - Relative offsets: `(0, 0), (0, 1)`.
   - Canonical shape string: `(0,0)(0,1)`
   - Already in HashSet! Set size remains 3.

**Final Answer:** `3` distinct islands.

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N \times M \log K)$ where $K$ is the average island size.
  - Every cell is visited at most once during traversal: $\mathcal{O}(N \times M)$.
  - For each island of size $S$, sorting its relative coordinates takes $\mathcal{O}(S \log S)$.
  - Summing across all islands: $\sum S \log S \le (N \times M) \log(N \times M)$.
  - For $N, M \le 500$, $N \times M \le 250,000$, executing comfortably within $< 0.15$ seconds.

- **Space Complexity:** $\mathcal{O}(N \times M)$
  - A `visited` boolean matrix of size $N \times M$.
  - Queue/Stack holding at most $N \times M$ elements.
  - `HashSet` holding the serialized canonical representations of all unique islands.
