# Editorial: Word Ladder II

## Problem Overview
Given two distinct words `startWord` and `targetWord`, and a dictionary of unique words `wordList` (all words having equal length $M$), we need to find **all shortest transformation sequences** from `startWord` to `targetWord`.

Each transformation step can only modify **exactly one letter**, and every intermediate word (including `targetWord`) must exist in `wordList`. If no valid sequence exists, return an empty list.

---

## Why Storing Paths in BFS Fails
In a simple BFS for Word Ladder I, we only need to track the distance/level. However, if we naively store complete paths in the BFS queue (e.g. `Queue<List<String>>`):
- Memory usage explodes rapidly because countless duplicate subpaths are allocated and copied in memory.
- In dense graphs, this approach leads to `MemoryLimitExceeded` or `TimeLimitExceeded`.

---

## Optimal Two-Phase Strategy: BFS Distance Map + Reverse DFS Backtracking

To find all shortest paths efficiently without memory bloat, we decouple the process into two phases:

### Phase 1: BFS Distance Mapping (Forward from `startWord`)
1. Create a `Map<String, Integer> dist` to record the shortest step count from `startWord` to each word.
2. Store all words from `wordList` in a `HashSet<String>`.
3. Set `dist.put(startWord, 0)`.
4. Enqueue `startWord` into a standard FIFO queue.
5. While the queue is not empty:
   - Dequeue word $u$ with distance $d = dist.get(u)$.
   - If $u$ equals `targetWord`, we can stop expanding to deeper levels (we only finish words at the current depth $d$).
   - For each character position in $u$, substitute letters from `'a'` to `'z'`:
     - If the neighbor $v$ is in `wordSet` and not yet present in `dist`:
       - `dist.put(v, d + 1)`
       - Enqueue $v$.
6. If `targetWord` was never reached in `dist`, return an empty list immediately.

---

### Phase 2: Reverse DFS Backtracking (Backward from `targetWord` to `startWord`)
Why backtrack in reverse?
- If we search forward from `startWord`, many paths might lead to dead ends that never reach `targetWord`.
- By searching **backward** from `targetWord` down to `startWord`, we only follow edges $(u, v)$ where:
  $$\text{dist}[u] = \text{dist}[v] - 1$$
- This guarantees that **every step we explore belongs to a valid shortest path**! Zero wasted branches or dead ends.

#### Backtracking Procedure:
- Start at `curr = targetWord`. Add `curr` to the current path.
- If `curr.equals(startWord)`:
  - We have completed a valid path! Create a copy, reverse it (so it reads `startWord -> ... -> targetWord`), and add to the results list.
- Otherwise, generate all 1-letter substitutions $u$ of `curr`:
  - If `dist.containsKey(u)` and `dist.get(u) == dist.get(curr) - 1`:
    - Recursively call DFS with $u$.
- Backtrack: remove `curr` from the current path.

---

## Step-by-Step Dry Run

### Example 1:
- `startWord = "der"`, `targetWord = "dfs"`
- `wordList = ["des", "der", "dfr", "dgt", "dfs"]`

#### Phase 1: BFS Distance Mapping
- `dist["der"] = 0`
- From `"der"`:
  - 1-letter transitions: `"des"`, `"dfr"`
  - `dist["des"] = 1`
  - `dist["dfr"] = 1`
- From level 1 (`"des"`, `"dfr"`):
  - From `"des"`: `"dfs"` reaches target! `dist["dfs"] = 2`
  - From `"dfr"`: `"dfs"` reaches target! `dist["dfs"] = 2`
- Level 2 contains `targetWord`, BFS completes.

#### Phase 2: Reverse Backtracking from `"dfs"`
- `curr = "dfs"` (`dist = 2`):
  - Neighbors at `dist = 1`: `"des"` and `"dfr"`.
  - Branch 1: visit `"des"` (`dist = 1`):
    - Neighbors of `"des"` at `dist = 0`: `"der"`.
    - `"der" == startWord` $\implies$ Path found: `["dfs", "des", "der"]`.
    - Reversing yields: `["der", "des", "dfs"]`.
  - Branch 2: visit `"dfr"` (`dist = 1`):
    - Neighbors of `"dfr"` at `dist = 0`: `"der"`.
    - `"der" == startWord` $\implies$ Path found: `["dfs", "dfr", "der"]`.
    - Reversing yields: `["der", "dfr", "dfs"]`.

Sort the paths lexicographically:
1. `der des dfs`
2. `der dfr dfs`

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N \cdot M \cdot 26 + P \cdot L)$ | Phase 1 visits each word once, performing $26 \cdot M$ character replacements in $\mathcal{O}(N \cdot M \cdot 26)$ time. Phase 2 generates only valid shortest paths, taking $\mathcal{O}(P \cdot L)$ where $P$ is the number of shortest paths and $L$ is path length. |
| **Space Complexity** | $\mathcal{O}(N \cdot M)$ | The `dist` map, `wordSet`, and queue store at most $N$ words of length $M$. The recursion stack depth is at most $N$. |

---

## Edge Cases
- **Target Missing**: If `targetWord` is not in `wordList`, no transformation can end at `targetWord`; returns empty list.
- **Direct Transformation (Length 2)**: `startWord` and `targetWord` differ by 1 character $\implies$ single path `[startWord, targetWord]`.
- **Multiple Disjoint Shortest Paths**: Correctly reconstructed and sorted in lexicographical order.
- **Unreachable Words**: BFS never reaches `targetWord` $\implies$ returns empty list.
