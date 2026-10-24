# Editorial: Find Eventual Safe States

## Problem Overview
Given a directed graph with $V$ vertices labeled $0$ to $V-1$ and $E$ directed edges, we need to find all **eventual safe nodes** sorted in ascending order.
- A **terminal node** is a node with zero outgoing edges ($\text{out-degree} = 0$).
- A node is **safe** if every possible directed path starting from that node terminates at a terminal node.
- If any path from a node leads to a directed cycle (or is part of a directed cycle), that path can loop indefinitely and never reach a terminal node. Such a node is **unsafe**.

Therefore:
$$\text{A node } u \text{ is safe} \iff \text{No path starting from } u \text{ can reach a cycle}.$$

---

## Approach 1: Kahn's Algorithm on Reversed Graph (Recommended)

In a standard topological sort (Kahn's Algorithm), we start with nodes having in-degree 0 and eliminate dependencies forward. However, in our problem, safe nodes naturally originate at the **sinks** (terminal nodes) where $\text{out-degree} = 0$.

### The Inversion Insight:
If we reverse the direction of every edge in the graph:
1. For every edge $u \to v$ in the original graph, create a reverse edge $v \to u$.
2. The **out-degree** of a node $u$ in the original graph becomes its **in-degree** in the reversed graph.
3. Terminal nodes ($\text{out-degree} = 0$) now have $\text{in-degree} = 0$ in the reversed graph!
4. We can now run standard Kahn's Algorithm on the reversed graph:
   - Initialize a queue with all nodes having $\text{in-degree} = 0$ in the reversed graph.
   - When we pop node $v$, it is guaranteed to be safe.
   - For every neighbor $u$ in the reversed graph (which means $u \to v$ in the original graph):
     - Decrement $\text{in-degree}[u]--$.
     - If $\text{in-degree}[u] == 0$, it means **all** outgoing paths from $u$ in the original graph have been confirmed to lead to safe nodes!
     - Enqueue $u$.
5. After the queue is empty, all popped nodes are safe. We can mark them in a boolean array `isSafe[]` and collect them in ascending order from $0$ to $V-1$.

---

## Approach 2: DFS with 3 States (Cycle Detection & Memoization)

We can also use Depth-First Search with a 3-state tracking array:
- `State 0 (UNVISITED)`: Not yet explored.
- `State 1 (VISITING)`: Currently in the recursion call stack (part of active exploration).
- `State 2 (SAFE)`: Confirmed to reach only terminal nodes without any cycles.

### DFS Rules:
1. When exploring node $u$, set `state[u] = 1`.
2. For each outgoing neighbor $v$ of $u$:
   - If `state[v] == 1`, we found a back-edge to an ancestor in the current path $\implies$ cycle detected $\implies$ return `false`.
   - If `state[v] == 0`: recursively call `dfs(v)`. If it returns `false`, then $u$ leads to a cycle $\implies$ return `false`.
   - If `state[v] == 2`: neighbor $v$ is already proven safe; continue checking other neighbors.
3. If all outgoing neighbors are confirmed safe, mark `state[u] = 2` and return `true`.

### Why BFS on the Reversed Graph is Preferred:
- Completely iterative: no risk of `StackOverflowError` on deep paths of length $10^4$.
- Automatically handles self-loops without special cases.
- Time and space are strictly linear: $\mathcal{O}(V + E)$.

---

## Step-by-Step Dry Run (Kahn's on Reversed Graph)

### Example 1:
- $V = 7, E = 7$
- Original edges:
  - $0 \to 1, 0 \to 2$
  - $1 \to 2, 1 \to 3$
  - $2 \to 5$
  - $3 \to 0$
  - $4 \to 5$
  - Nodes $5$ and $6$ have out-degree $0$.

### 1. Reverse Graph & In-Degrees:
- In-degree in reversed graph (= out-degree in original graph):
  - `inDegree[0] = 2` (edges to 1, 2)
  - `inDegree[1] = 2` (edges to 2, 3)
  - `inDegree[2] = 1` (edge to 5)
  - `inDegree[3] = 1` (edge to 0)
  - `inDegree[4] = 1` (edge to 5)
  - `inDegree[5] = 0` (terminal)
  - `inDegree[6] = 0` (terminal)

- Reversed edges:
  - $1 \to 0$
  - $2 \to 0, 2 \to 1$
  - $3 \to 1$
  - $0 \to 3$
  - $5 \to 2, 5 \to 4$

### 2. Queue Initialization:
- Enqueue nodes with `inDegree == 0`: `[5, 6]`.

### 3. BFS Processing:
1. Dequeue `5`:
   - Outgoing edges in reverse: $5 \to 2$ and $5 \to 4$.
   - Decrement `inDegree[2]`: $1 \to 0 \implies$ Enqueue `2`.
   - Decrement `inDegree[4]`: $1 \to 0 \implies$ Enqueue `4`.
   - Queue: `[6, 2, 4]`
2. Dequeue `6`:
   - No outgoing edges in reverse.
   - Queue: `[2, 4]`
3. Dequeue `2`:
   - Outgoing edges in reverse: $2 \to 0$ and $2 \to 1$.
   - Decrement `inDegree[0]`: $2 \to 1$.
   - Decrement `inDegree[1]`: $2 \to 1$.
   - Queue: `[4]`
4. Dequeue `4`:
   - No outgoing edges in reverse.
   - Queue: `[]`

### 4. Remaining Nodes:
- Nodes $0, 1, 3$ have remaining `inDegree = 1` due to the cycle $0 \to 1 \to 3 \to 0$. None of them ever reached `inDegree == 0`.
- Safe nodes collected: `{5, 6, 2, 4}`.
- Sorted in ascending order: `2 4 5 6`.

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(V + E)$ | Building the reversed graph takes $\mathcal{O}(V + E)$. BFS visits each node and traverses each reverse edge at most once. Collecting and sorting safe nodes takes $\mathcal{O}(V)$ using a boolean array. |
| **Space Complexity** | $\mathcal{O}(V + E)$ | Storing the reversed adjacency list requires $\mathcal{O}(V + E)$. The `inDegree` array, queue, and `isSafe` boolean array take $\mathcal{O}(V)$ space. |

---

## Edge Cases
- **No Edges ($E = 0$)**: All vertices are terminal nodes with out-degree 0; all are safe: `0 1 2 ... V-1`.
- **Single Vertex ($V = 1, E = 0$)**: Output: `0`.
- **Self-Loop ($u \to u$)**: Node $u$ has an edge to itself; its in-degree in the reversed graph will never drop to 0 unless it has no self-cycle. Thus $u$ is correctly identified as unsafe.
- **Disconnected Components**: Multiple disconnected subgraphs (some DAGs, some cycles) are handled seamlessly by multi-source BFS.
- **Star Sinks**: Central sink $V-1$ connects from all nodes; all nodes resolve as safe in reverse order.
