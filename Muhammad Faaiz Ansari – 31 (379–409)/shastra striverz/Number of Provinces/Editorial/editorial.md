# Editorial: Number of Provinces

## Difficulty
Medium

## Prerequisites
- Graph Representation (Adjacency Matrix)
- Graph Traversal (DFS or BFS)
- Disjoint Set Union (Union-Find)

---

## Problem Statement
Given an undirected graph with $V$ cities represented by a $V \times V$ binary adjacency matrix `adj`. If `adj[i][j] == 1`, city $i$ and city $j$ are directly connected. A **province** is a connected component of cities. Compute the total number of provinces.

---

## Explanation & Approaches

### Approach 1: Depth First Search (DFS) / Breadth First Search (BFS) — Preferred
Because the graph is given as an adjacency matrix of size $V \times V$ with $V \le 300$, scanning all rows and columns takes $\mathcal{O}(V^2)$ operations, which is $\le 9 \times 10^4$ operations (under 1 millisecond).

1. Maintain a boolean array `visited` of size $V$, initialized to `false`.
2. Initialize `provinces = 0`.
3. For each city `i` from $0$ to $V-1$:
   - If `!visited[i]`:
     - Increment `provinces++`.
     - Traverse the entire connected component starting from city `i`:
       - Mark `visited[i] = true`.
       - For every city `j` from $0$ to $V-1$:
         - If `adj[curr][j] == 1 && !visited[j]`, mark `visited[j] = true` and continue visiting `j`.
4. Return `provinces`.

#### Iterative Traversal Algorithm:
```java
int provinces = 0;
boolean[] visited = new boolean[V];
int[] stack = new int[V];

for (int i = 0; i < V; i++) {
    if (!visited[i]) {
        provinces++;
        int top = 0;
        stack[top] = i;
        visited[i] = true;

        while (top >= 0) {
            int u = stack[top--];
            for (int v = 0; v < V; v++) {
                if (adj[u][v] == 1 && !visited[v]) {
                    visited[v] = true;
                    stack[++top] = v;
                }
            }
        }
    }
}
```

---

### Approach 2: Disjoint Set Union (Union-Find)
We can also use a DSU data structure:
1. Initialize each city in its own set with `parent[i] = i`. Set `provinces = V`.
2. Iterate through all pairs $(i, j)$ with $0 \le i < j < V$:
   - If `adj[i][j] == 1`:
     - If $\text{find}(i) \ne \text{find}(j)$:
       - $\text{union}(i, j)$
       - `provinces--`
3. Return `provinces`.

---

## Step-by-Step Dry Run (Example 1)

Input: $V = 4$, matrix:
```text
1 0 0 1
0 1 1 0
0 1 1 0
1 0 0 1
```

1. **City 0:** `!visited[0]` is true.
   - `provinces = 1`.
   - Traversal from 0 scans row 0: finds connection to city 3.
   - Visits city 3: scans row 3: finds connection to city 0 (already visited).
   - Component finished: cities $\{0, 3\}$ marked `visited`.
2. **City 1:** `!visited[1]` is true.
   - `provinces = 2`.
   - Traversal from 1 scans row 1: finds connection to city 2.
   - Visits city 2: scans row 2: finds connection to city 1 (already visited).
   - Component finished: cities $\{1, 2\}$ marked `visited`.
3. **City 2:** Already visited (`visited[2] == true`).
4. **City 3:** Already visited (`visited[3] == true`).
5. Loop finishes. Result = **2**.

---

## Complexity Analysis

- **Time Complexity:** $\mathcal{O}(V^2)$
  - Scanning the $V \times V$ matrix takes $\mathcal{O}(V^2)$ steps.
  - Across the entire traversal, each city is pushed to the stack once, and its row of $V$ entries is scanned once.
  - For $V = 300$, $V^2 = 9 \times 10^4$ operations, running in $< 0.01$ seconds.

- **Space Complexity:** $\mathcal{O}(V)$ auxiliary space
  - The `visited` array and traversal stack use $\mathcal{O}(V)$ space.
  - Matrix storage: $\mathcal{O}(V^2)$.
