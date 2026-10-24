# Editorial: Course Schedule I

## Problem Overview
There are $N$ courses numbered from $0$ to $N - 1$. We are given an array of prerequisite pairs where each pair $[a, b]$ indicates that course $b$ must be taken before course $a$.

The task is to determine whether it is possible to finish all $N$ courses.

---

## Graph Modeling
This problem can naturally be modeled as a **directed graph**:
- **Vertices**: Each of the $N$ courses corresponds to a node $0, 1, \dots, N-1$.
- **Directed Edges**: For each prerequisite pair $[a, b]$, taking $b$ before $a$ translates to a directed edge:
  $$b \to a$$
  meaning $b$ is a prerequisite of $a$, or completing $b$ unlocks $a$.

### Fundamental Condition:
Can all courses be completed?
- If there is a circular dependency (e.g., $A \to B \to C \to A$), then course $A$ requires $C$, which requires $B$, which requires $A$. None of the courses in this cycle can ever be started.
- Conversely, if there are no directed cycles, the graph is a **Directed Acyclic Graph (DAG)**, and there is guaranteed to exist at least one valid linear sequence (a **topological sort**) in which all courses can be taken.

Therefore:
$$\text{All courses can be finished} \iff \text{The graph is a DAG (has no directed cycles)}.$$

---

## Approach 1: Kahn's Algorithm (BFS Topological Sort) — Recommended

Kahn's Algorithm is an intuitive Breadth-First Search approach based on dependency in-degrees:

### Algorithm Steps:
1. **Build Adjacency List & In-Degrees**:
   - Construct directed edges $b \to a$ for every pair $[a, b]$.
   - Compute `inDegree[u]` for each course $u$ (the number of pending prerequisites for course $u$).
2. **Initialize Queue**:
   - Add all courses with `inDegree[u] == 0` (courses with no prerequisites) to a queue.
3. **Process Completed Courses**:
   - Maintain a counter `completed = 0`.
   - While the queue is not empty:
     - Dequeue a course $u$ and increment `completed++`.
     - For each course $v$ where $u \to v$ (course $v$ depends on $u$):
       - Decrement `inDegree[v]--`.
       - If `inDegree[v] == 0`, all prerequisites for $v$ are now fulfilled $\implies$ enqueue $v$.
4. **Conclusion**:
   - If `completed == N`, all courses were successfully taken $\implies$ return `true`.
   - If `completed < N`, a cycle exists preventing some courses from ever reaching zero prerequisites $\implies$ return `false`.

### Advantages:
- **Stack-Safe**: Completely iterative, avoiding Java recursion limits.
- **Natural**: Directly simulates taking courses as their prerequisites are fulfilled.

---

## Approach 2: DFS with 3-State Cycle Detection

Alternatively, we can use Depth-First Search with a 3-state tracking array:
- `State 0 (UNVISITED)`: Course has not been checked.
- `State 1 (VISITING)`: Course is currently on the active prerequisite path.
- `State 2 (VISITED)`: Course has been completely explored and verified to have no cycles.

If DFS encounters a neighbor in `State 1`, a **back-edge** (circular dependency) is found, making it impossible to finish all courses.

---

## Step-by-Step Dry Run

### Example 1:
- $N = 4$, Prerequisites: `[1, 0]`, `[2, 1]`, `[3, 2]`
- Edges: $0 \to 1, 1 \to 2, 2 \to 3$

1. **In-Degrees**:
   - `inDegree[0] = 0`
   - `inDegree[1] = 1`
   - `inDegree[2] = 1`
   - `inDegree[3] = 1`

2. **Queue**: `[0]`, `completed = 0`

3. **Transitions**:
   - Pop `0`: `completed = 1`. Decrement `inDegree[1]` ($1 \to 0$) $\implies$ Enqueue `1`.
   - Pop `1`: `completed = 2`. Decrement `inDegree[2]` ($1 \to 0$) $\implies$ Enqueue `2`.
   - Pop `2`: `completed = 3`. Decrement `inDegree[3]` ($1 \to 0$) $\implies$ Enqueue `3`.
   - Pop `3`: `completed = 4`. No outgoing edges.

4. `completed = 4 == N` $\implies$ Output: `true`.

---

### Example 2:
- $N = 4$, Prerequisites: `[0, 1]`, `[3, 2]`, `[1, 3]`, `[3, 0]`
- Edges: $1 \to 0, 2 \to 3, 3 \to 1, 0 \to 3$

1. **In-Degrees**:
   - `inDegree[0] = 1` (from 1)
   - `inDegree[1] = 1` (from 3)
   - `inDegree[2] = 0` (no incoming edge)
   - `inDegree[3] = 2` (from 2, 0)

2. **Queue**: `[2]`, `completed = 0`

3. **Transitions**:
   - Pop `2`: `completed = 1`. Decrement `inDegree[3]` ($2 \to 1$).
   - Queue is now empty!
   - Courses $0, 1, 3$ form the cycle $1 \to 0 \to 3 \to 1$, keeping their in-degrees $\ge 1$.

4. `completed = 1 < 4` $\implies$ Output: `false`.

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N + M)$ | Building the adjacency list and in-degrees scans all $M$ prerequisite pairs. Each of the $N$ courses is enqueued and dequeued at most once. Each directed edge is traversed once to decrement in-degrees. |
| **Space Complexity** | $\mathcal{O}(N + M)$ | $\mathcal{O}(N + M)$ for the adjacency list. $\mathcal{O}(N)$ for the `inDegree` array and BFS queue. |

---

## Edge Cases
- **No Prerequisites ($M = 0$)**: All courses have 0 prerequisites; all are immediately enqueued; returns `true`.
- **Single Course ($N = 1, M = 0$)**: Trivial base case; returns `true`.
- **Completely Disconnected Components**: Courses with no prerequisites in separate components are handled simultaneously.
- **Large Graphs ($N = 2000, M = 5000$)**: Iterative BFS executes in under 10 milliseconds.
