# Editorial: Course Schedule II

## Problem Overview
There are $N$ courses labeled $0$ to $N - 1$. We are given an array of prerequisite pairs where each pair $[a, b]$ specifies that course $b$ must be taken before course $a$.

Our objective is to find a valid **course sequence** (execution ordering) that satisfies all prerequisite constraints. If no such ordering exists (e.g. because of a circular dependency), we return an empty array.

---

## Graph Formulation & Topological Sort
We represent the problem as a directed graph:
- **Vertices**: The $N$ courses ($0, 1, \dots, N-1$).
- **Directed Edges**: For each prerequisite pair $[a, b]$, course $b$ must precede course $a$, creating a directed edge:
  $$b \to a$$

A valid sequence of courses is a permutation $P$ of $\{0, 1, \dots, N-1\}$ such that for every directed edge $b \to a$, $b$ appears before $a$ in $P$.

This is precisely the definition of a **topological sort** on a **Directed Acyclic Graph (DAG)**.
- If the graph has **no directed cycles**, at least one topological ordering exists.
- If the graph contains a **cycle**, it is impossible to finish all courses, and we must return an empty list.

---

## Approach 1: Kahn's Algorithm (BFS Topological Sort) — Recommended

Kahn's Algorithm is an iterative BFS method that directly constructs the topological order from sources to sinks:

### Algorithm Steps:
1. **Compute In-Degrees**:
   - For each course $u$, `inDegree[u]` stores the number of outstanding prerequisites for $u$.
   - For each pair $[a, b]$, add directed edge $b \to a$ and increment `inDegree[a]++`.
2. **Initialize Queue**:
   - Add all courses with `inDegree[u] == 0` (courses with no prerequisites) to a queue.
   - Maintain an array `order[]` of size $N$ and a pointer `tail = 0`.
3. **BFS Traversal**:
   - While the queue is not empty:
     - Dequeue course $u$.
     - Place $u$ in `order[tail++] = u`.
     - For each course $v$ where $u \to v$:
       - Decrement `inDegree[v]--`.
       - If `inDegree[v] == 0`, all prerequisites of course $v$ have been fulfilled $\implies$ enqueue $v$.
4. **Cycle Validation**:
   - If `tail == N`, all $N$ courses have been scheduled in a valid sequence $\implies$ return `order`.
   - If `tail < N`, a cycle exists preventing some courses from ever being unlocked $\implies$ return an empty array.

### Why Kahn's BFS is Optimal:
- **Direct Output Array**: The queue itself or a flat array serves as the output container without extra allocations.
- **Stack-Safe**: Completely iterative, immune to Java recursion depth limits.

---

## Approach 2: Depth-First Search (DFS) with Stack

An alternative approach uses DFS:
1. Maintain a state array `state[]`:
   - `0 (UNVISITED)`
   - `1 (VISITING)` (currently on recursion stack)
   - `2 (VISITED)`
2. For each unvisited course $i \in [0, N-1]$, call `dfs(i)`.
3. Inside `dfs(u)`:
   - Set `state[u] = 1`.
   - For each neighbor $v$ of $u$:
     - If `state[v] == 1`, a cycle is detected $\implies$ return `false`.
     - If `state[v] == 0` and `!dfs(v)`, return `false`.
   - Set `state[u] = 2` and push $u$ to a stack.
4. If any component returns `false`, a cycle exists $\implies$ return empty array.
5. Otherwise, pop elements from the stack to produce the topological order.

---

## Step-by-Step Dry Run

### Example 1:
- $N = 4$, Prerequisites: `[1, 0]`, `[2, 1]`, `[3, 2]`
- Edges: $0 \to 1, 1 \to 2, 2 \to 3$

1. **Calculate In-Degrees**:
   - `inDegree[0] = 0`
   - `inDegree[1] = 1`
   - `inDegree[2] = 1`
   - `inDegree[3] = 1`

2. **Initialize Queue**:
   - Course `0` has `inDegree == 0`.
   - Queue: `[0]`
   - `order = []`

3. **Processing Steps**:
   - Dequeue `0`: `order = [0]`. Decrement `inDegree[1]` to $0 \implies$ Enqueue `1`.
   - Dequeue `1`: `order = [0, 1]`. Decrement `inDegree[2]` to $0 \implies$ Enqueue `2`.
   - Dequeue `2`: `order = [0, 1, 2]`. Decrement `inDegree[3]` to $0 \implies$ Enqueue `3`.
   - Dequeue `3`: `order = [0, 1, 2, 3]`. No outgoing edges.

4. `tail = 4 == N` $\implies$ Output: `0 1 2 3`.

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N + M)$ | Initializing in-degrees and the adjacency list takes $\mathcal{O}(N + M)$. Each course is enqueued and dequeued once ($\mathcal{O}(N)$), and each prerequisite edge is traversed once ($\mathcal{O}(M)$). |
| **Space Complexity** | $\mathcal{O}(N + M)$ | The adjacency list uses $\mathcal{O}(N + M)$ memory. The `inDegree` array, queue, and result order array each use $\mathcal{O}(N)$ memory. |

---

## Edge Cases
- **No Prerequisites ($M = 0$)**: All courses have 0 prerequisites; any permutation (e.g. `0 1 2 ... N-1`) is valid.
- **Single Course ($N = 1, M = 0$)**: Output: `0`.
- **Pure Cycle ($1 \leftrightarrow 0$)**: Queue remains empty; outputs empty line.
- **Independent Disconnected Components**: Courses from separate components are interleaved without conflict.
- **Maximum Scale ($N = 2000, M = 5000$)**: Operates efficiently within milliseconds.
