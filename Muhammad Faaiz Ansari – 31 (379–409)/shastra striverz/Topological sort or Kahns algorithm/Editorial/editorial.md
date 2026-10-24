# Editorial: Topological Sort or Kahn's Algorithm

## Problem Overview
Given a Directed Acyclic Graph (DAG) with $V$ vertices (labeled from $0$ to $V-1$) and $E$ directed edges, the objective is to produce a **topological sort** of the vertices.

A topological sort is a linear ordering of vertices such that for every directed edge $u \to v$, vertex $u$ appears before vertex $v$ in the ordering.

---

## Key Observations
1. **DAG Requirement**: A topological sorting exists if and only if the directed graph is acyclic (a DAG). If there were a cycle (e.g. $A \to B \to C \to A$), $A$ must precede $B$, $B$ must precede $C$, and $C$ must precede $A$, which creates a contradiction.
2. **In-Degree Property**: 
   - The **in-degree** of a vertex is the count of incoming directed edges pointing to it.
   - Any vertex with an in-degree of $0$ has no incoming dependencies. It can safely appear first in the topological ordering.
3. **Dependency Resolution**: Once a vertex $u$ is placed in the topological order, its outgoing edges $u \to v$ can be considered "satisfied". Removing $u$ and its outgoing edges reduces the in-degree of each neighbor $v$ by $1$. When a neighbor's in-degree drops to $0$, all its prerequisites have been fulfilled, making it eligible to be placed next.

---

## Method 1: Kahn's Algorithm (BFS-based Approach)

Kahn's Algorithm is a Breadth-First Search (BFS) approach that repeatedly removes vertices with in-degree 0.

### Algorithm Steps:
1. **Compute In-Degrees**:
   - Initialize an array `inDegree` of size $V$ with zeros.
   - Traverse all directed edges $u \to v$ and increment `inDegree[v]++`.
2. **Initialize Queue**:
   - Enqueue all vertices $i \in [0, V-1]$ that have `inDegree[i] == 0`.
3. **Process Queue**:
   - While the queue is not empty:
     - Dequeue the front vertex $u$.
     - Append $u$ to the topological sort result array.
     - For each directed neighbor $v$ of $u$:
       - Decrement `inDegree[v]--`.
       - If `inDegree[v] == 0`, enqueue $v$.
4. **Verification / Cycle Detection**:
   - If the number of vertices added to the topological order equals $V$, then a valid topological ordering has been found.
   - If the result contains fewer than $V$ vertices, the graph contains at least one cycle (not a DAG).

---

## Method 2: Depth-First Search (DFS) with Stack

An alternative canonical approach uses DFS traversal:
1. Maintain a boolean `visited` array.
2. For each unvisited vertex $i \in [0, V-1]$, start a DFS.
3. During `dfs(u)`:
   - Mark $u$ as visited.
   - Recursively call `dfs(v)` for all unvisited directed neighbors $v$.
   - After visiting all outgoing neighbors, push $u$ onto a stack (or prepend to a list).
4. Pop elements from the stack one by one to obtain the topological ordering.

Both methods are optimal and achieve $\mathcal{O}(V + E)$ time complexity. Kahn's Algorithm is often preferred in competitive programming because:
- It is iterative and completely stack-safe (no risk of `StackOverflowError` on deep graphs of $10^4$ vertices).
- It naturally detects cycles without requiring a three-state (`VISITING`, `VISITED`, `UNVISITED`) array.

---

## Step-by-Step Dry Run (Kahn's Algorithm)

Consider Example 1:
- Vertices: $V = 6$, Edges: $E = 6$
- Edges: $(2 \to 3), (3 \to 1), (4 \to 0), (4 \to 1), (5 \to 0), (5 \to 2)$

### Step 1: Compute In-Degrees
- `inDegree[0] = 2` (from 4, 5)
- `inDegree[1] = 2` (from 3, 4)
- `inDegree[2] = 1` (from 5)
- `inDegree[3] = 1` (from 2)
- `inDegree[4] = 0`
- `inDegree[5] = 0`

### Step 2: Queue Initialization
Vertices with `inDegree == 0` are `4` and `5`.
Queue: `[4, 5]`
Result: `[]`

### Step 3: BFS Transitions
1. Dequeue `4`:
   - Result: `[4]`
   - Neighbor `0`: `inDegree[0]` becomes $2 - 1 = 1$.
   - Neighbor `1`: `inDegree[1]` becomes $2 - 1 = 1$.
   - Queue: `[5]`

2. Dequeue `5`:
   - Result: `[4, 5]`
   - Neighbor `0`: `inDegree[0]` becomes $1 - 1 = 0 \to$ enqueue `0`.
   - Neighbor `2`: `inDegree[2]` becomes $1 - 1 = 0 \to$ enqueue `2`.
   - Queue: `[0, 2]`

3. Dequeue `0`:
   - Result: `[4, 5, 0]`
   - No outgoing neighbors.
   - Queue: `[2]`

4. Dequeue `2`:
   - Result: `[4, 5, 0, 2]`
   - Neighbor `3`: `inDegree[3]` becomes $1 - 1 = 0 \to$ enqueue `3`.
   - Queue: `[3]`

5. Dequeue `3`:
   - Result: `[4, 5, 0, 2, 3]`
   - Neighbor `1`: `inDegree[1]` becomes $1 - 1 = 0 \to$ enqueue `1`.
   - Queue: `[1]`

6. Dequeue `1`:
   - Result: `[4, 5, 0, 2, 3, 1]`
   - No outgoing neighbors.
   - Queue: `[]`

Queue is empty. Total processed vertices = 6 = $V$.
Valid Topological Order: `4 5 0 2 3 1`.

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(V + E)$ | Computing in-degrees scans all $E$ edges. Each vertex is enqueued and dequeued at most once ($\mathcal{O}(V)$). Each edge is traversed once to decrement in-degree ($\mathcal{O}(E)$). |
| **Space Complexity** | $\mathcal{O}(V + E)$ | $\mathcal{O}(V + E)$ for adjacency list, $\mathcal{O}(V)$ for `inDegree` array, $\mathcal{O}(V)$ for queue, and $\mathcal{O}(V)$ for output array. |

---

## Edge Cases
- **No Edges ($E = 0$)**: All vertices have in-degree 0. Any permutation of $0 \dots V-1$ (e.g. `0 1 2 ... V-1`) is valid.
- **Single Vertex ($V = 1, E = 0$)**: Directly outputs `0`.
- **Linear Chain ($0 \to 1 \to 2 \dots \to V-1$)**: Exactly one unique topological sort exists (`0 1 2 ... V-1`).
- **Star Out-tree (Source $0 \to$ all other nodes)**: $0$ must be first, followed by any ordering of remaining nodes.
- **Star In-tree (All nodes $\to$ Sink $V-1$)**: Sink $V-1$ must be last, preceded by any ordering of remaining nodes.
- **Disconnected DAG Components**: Independent components can interleave arbitrarily; Kahn's algorithm handles disconnected components seamlessly.
