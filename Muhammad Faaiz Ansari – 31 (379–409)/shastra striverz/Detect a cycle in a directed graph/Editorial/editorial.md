# Editorial: Detect a Cycle in a Directed Graph

## Problem Overview
Given a directed graph with $V$ vertices labeled $0$ to $V-1$ and $E$ directed edges ($u \to v$), determine whether the graph contains at least one **directed cycle**.

A directed cycle is a non-empty sequence of directed edges that begins and ends at the exact same vertex:
$$v_0 \to v_1 \to v_2 \to \dots \to v_k \to v_0$$

---

## Key Differences: Directed vs. Undirected Cycle Detection
In an undirected graph, encountering an already visited neighbor (that is not the direct parent) immediately signals a cycle. 

In a directed graph, this is **not** true:
- A vertex could be visited via multiple distinct paths without forming a cycle (e.g. $A \to B \to D$ and $A \to C \to D$). Here, edge $C \to D$ is a **cross-edge**, not a back-edge.
- A directed cycle requires a **back-edge**, which points back to an ancestor currently in the active traversal path.

---

## Approach 1: Kahn's Algorithm (BFS via Topological Sort) — Recommended

Topological sorting is strictly valid only on Directed Acyclic Graphs (DAGs). Kahn's Algorithm can be repurposed to detect cycles:

### Core Intuition:
1. In any Directed Acyclic Graph (DAG), there is always at least one vertex with in-degree 0.
2. If we repeatedly remove vertices with in-degree 0 and reduce the in-degrees of their outgoing neighbors, every vertex in a DAG will eventually be processed (total processed vertices = $V$).
3. If the graph contains a directed cycle, all vertices within the cycle have an in-degree of at least 1 at all times. None of them can ever be enqueued.
4. Therefore, if the total number of processed vertices $\text{count} < V$, the graph **must contain at least one directed cycle**.

### Algorithm Steps:
1. Initialize an array `inDegree` of size $V$ with 0.
2. For every directed edge $u \to v$, increment `inDegree[v]++`.
3. Initialize an array-based queue with all vertices having `inDegree[i] == 0`.
4. Maintain a counter `processedCount = 0`.
5. While the queue is not empty:
   - Dequeue vertex $u$ and increment `processedCount++`.
   - For each neighbor $v$ of $u$:
     - Decrement `inDegree[v]--`.
     - If `inDegree[v] == 0`, enqueue $v$.
6. Return `true` if `processedCount < V` (cycle detected), else `false`.

### Why Kahn's BFS is Optimal:
- **Stack Safety**: Completely iterative, avoiding Java recursion depth limits on graphs with $10^4$ chained nodes.
- **Disconnected Graphs**: Automatically handles all disconnected components simultaneously without needing an outer loop or component tracking.

---

## Approach 2: Depth-First Search (DFS) with 3 States

Alternatively, DFS can detect back-edges using a state array:
- `State 0 (UNVISITED)`: Vertex has not yet been visited.
- `State 1 (VISITING)`: Vertex is currently in the active recursion call stack.
- `State 2 (VISITED)`: Vertex and all its descendants have been fully processed.

### DFS Steps:
1. For each vertex $i \in [0, V-1]$:
   - If `state[i] == 0`, invoke `dfs(i)`.
2. Inside `dfs(u)`:
   - Set `state[u] = 1`.
   - For each neighbor $v$ of $u$:
     - If `state[v] == 1`, a **back-edge** is detected $\implies$ return `true`.
     - If `state[v] == 0`: recursively call `dfs(v)`. If it returns `true`, propagate `true`.
   - Set `state[u] = 2` (backtrack).
3. If no cycle is found across all components, return `false`.

---

## Step-by-Step Dry Run (Kahn's Algorithm)

### Example 1:
- $V = 6, E = 6$
- Edges: $(0 \to 1), (1 \to 2), (1 \to 5), (2 \to 3), (3 \to 4), (4 \to 1)$

1. **Calculate In-Degrees**:
   - `0`: 0
   - `1`: 2 (from 0, 4)
   - `2`: 1 (from 1)
   - `3`: 1 (from 2)
   - `4`: 1 (from 3)
   - `5`: 1 (from 1)

2. **Initialize Queue**:
   - Only vertex `0` has `inDegree == 0`.
   - Queue: `[0]`
   - `processedCount = 0`

3. **BFS Traversal**:
   - Pop `0`: `processedCount = 1`.
     - Edge $0 \to 1$: `inDegree[1]` becomes $2 - 1 = 1$.
   - Queue is now empty!
   - Vertices 1, 2, 3, 4, 5 are never processed because the cycle $1 \to 2 \to 3 \to 4 \to 1$ prevents their in-degrees from reaching 0.

4. **Result**:
   - `processedCount = 1 < 6`.
   - Output: `true` (cycle exists).

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(V + E)$ | Every vertex is enqueued at most once, and every directed edge is traversed exactly once. |
| **Space Complexity** | $\mathcal{O}(V + E)$ | $\mathcal{O}(V + E)$ for the adjacency list, $\mathcal{O}(V)$ for the `inDegree` array and queue. |

---

## Edge Cases
- **No Edges ($E = 0$)**: All vertices have in-degree 0; all are enqueued and processed; returns `false`.
- **Single Vertex ($V = 1, E = 0$)**: Returns `false`.
- **Self-Contained Cycle Disconnected from Source**: Handled seamlessly because nodes in the cycle never reach in-degree 0.
- **Multiple Disconnected Components**: Correctly handles graphs where one component is a DAG and another component contains a cycle.
- **Large Chains and Deep DAGs ($V = 10^4$)**: Iterative BFS avoids stack overflow.
