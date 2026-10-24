### Problem: Flood Fill Algorithm
**Difficulty:** Easy-Medium | **Tags:** Graphs, Breadth-First Search, Depth-First Search, Matrix

**Problem Statement**
An image is represented by a 2D grid of size $N \times M$ of integers, where each integer represents the pixel value (color) at that coordinate.

Given a coordinate $(sr, sc)$ representing the starting pixel (row and column) of the flood fill, and a new color value $newColor$, perform a **flood fill** on the image.

To perform a flood fill:
1. Consider the starting pixel at $(sr, sc)$.
2. Plus any pixels connected **4-directionally** (up, down, left, right) to the starting pixel that share the **exact same original color** as the starting pixel.
3. Plus any pixels connected 4-directionally to those pixels (also having the same original color), and so on.
4. Replace the color of all of these connected matching pixels with $newColor$.

Return the modified image after the flood fill.

**Input Format**
* The first line contains two space-separated integers $N$ (number of rows) and $M$ (number of columns).
* The next $N$ lines each contain $M$ space-separated integers representing the pixels of `image`.
* The last line contains three space-separated integers: $sr$, $sc$, and $newColor$.

**Output Format**
* Print $N$ lines, each containing $M$ space-separated integers representing the flood-filled image.

**Constraints**
* $1 \le N, M \le 50$
* $0 \le \text{image}[i][j], newColor < 2^{16} = 65536$
* $0 \le sr < N$
* $0 \le sc < M$
* Connectivity is strictly **4-directional** (horizontal and vertical).

**Example 1**
**Input:**
```text
3 3
1 1 1
1 1 0
1 0 1
1 1 2
```

**Output:**
```text
2 2 2
2 2 0
2 0 1
```

**Explanation:**
Starting at $(sr, sc) = (1, 1)$, the starting color is `1`. 
All 4-directionally connected pixels with color `1` are recolored to `2`. 
Note that pixel $(2, 2)$ at the bottom right is `1`, but it is only diagonally connected to the rest, so it is **not** recolored.

---

**Example 2**
**Input:**
```text
3 3
0 1 0
1 1 0
0 0 1
2 2 3
```

**Output:**
```text
0 1 0
1 1 0
0 0 3
```

**Explanation:**
Starting at $(sr, sc) = (2, 2)$, the starting pixel has color `1`. Its adjacent neighbors are all `0`, so only pixel $(2, 2)$ itself is recolored to `3`.

---

**Example 3**
**Input:**
```text
3 3
1 1 1
1 1 0
1 0 1
1 1 0
```

**Output:**
```text
0 0 0
0 0 0
0 0 1
```

**Explanation:**
Starting at $(sr, sc) = (1, 1)$, starting color is `1`, and $newColor = 0$. All connected `1`s turn into `0`.
