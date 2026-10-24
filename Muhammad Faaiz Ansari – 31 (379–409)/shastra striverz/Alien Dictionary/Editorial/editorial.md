# Editorial: Alien Dictionary

## Problem Overview
We are given a list of $N$ words sorted lexicographically according to the alphabet rules of an alien language. The alphabet contains the first $K$ lowercase letters of the English alphabet (`'a'` to the $K$-th letter).

Our task is to deduce a valid character ordering of the alien alphabet. If the dictionary is inconsistent (contradictory dependencies or invalid prefix ordering), we return an empty string.

---

## Lexicographical Comparison Rules
In any dictionary ordering:
1. When comparing two consecutive words $w_1$ and $w_2$, find the **first index** $j$ where they differ ($w_1[j] \ne w_2[j]$):
   - The character $w_1[j]$ must appear before $w_2[j]$ in the alien alphabet.
   - Any characters appearing after index $j$ carry **no ordering information** and must be ignored.
2. **Prefix Invalidation Rule**:
   - If $w_2$ is a prefix of $w_1$ and $w_1$ is strictly longer than $w_2$ (e.g. `"apple"` appears before `"app"`), this is a fundamental lexicographical contradiction. In this case, no valid ordering can exist, so we immediately return `""`.

---

## Graph Formulation & Topological Sort
We construct a directed graph where:
- **Vertices**: $K$ characters, mapped to integers $0 \dots K-1$ where index $i$ represents character `(char)('a' + i)`.
- **Directed Edges**: For each consecutive pair of words $(w_i, w_{i+1})$, the first differing character $w_i[j]$ and $w_{i+1}[j]$ creates a directed edge:
  $$w_i[j] \to w_{i+1}[j]$$
  meaning $w_i[j]$ must appear before $w_{i+1}[j]$ in the alien alphabet.

Once all edges are extracted, finding the character sequence is equivalent to computing a **topological sort** on the $K$ vertices:
- If the graph contains no cycles, the topological order gives a valid alien alphabet sequence.
- If the graph contains a directed cycle (e.g. $a \to b \to a$), there is a contradiction, and no valid order exists.

---

## Kahn's Algorithm (BFS) Implementation

1. **Graph Construction**:
   - Initialize an adjacency list for $K$ vertices.
   - Initialize an `inDegree` array of size $K$ with 0.
   - For each adjacent pair $(w_i, w_{i+1})$:
     - Find the first mismatched index $j$.
     - If a mismatch is found: add directed edge $w_i[j] \to w_{i+1}[j]$ and increment `inDegree[w_{i+1}[j]]++`.
     - If no mismatch is found and $w_i.\text{length}() > w_{i+1}.\text{length}()$, return `""` (prefix violation).
2. **Initialize Queue**:
   - Add all vertices $0 \dots K-1$ that have `inDegree == 0` to a queue.
3. **Process BFS Queue**:
   - Maintain a list `order`.
   - While the queue is not empty:
     - Dequeue character $u$.
     - Append $u$ to `order`.
     - For each outgoing neighbor $v$ of $u$:
       - Decrement `inDegree[v]--`.
       - If `inDegree[v] == 0`, enqueue $v$.
4. **Validation**:
   - If `order.size() == K`, all $K$ characters have been placed in a valid order.
   - If `order.size() < K`, a cycle exists $\implies$ return `""`.

---

## Step-by-Step Dry Run

### Example 1:
- $N = 5, K = 4$, Words: `["baa", "abcd", "abca", "cab", "cad"]`
- Characters: `'a', 'b', 'c', 'd'` ($K = 4$)

1. **Comparing Adjacent Words**:
   - `"baa"` vs `"abcd"`: first difference at index 0 $\implies$ `'b' \to 'a'`.
   - `"abcd"` vs `"abca"`: first difference at index 3 $\implies$ `'d' \to 'a'`.
   - `"abca"` vs `"cab"`: first difference at index 0 $\implies$ `'a' \to 'c'`.
   - `"cab"` vs `"cad"`: first difference at index 2 $\implies$ `'b' \to 'd'`.

2. **In-Degrees**:
   - `inDegree['a'] = 2` (from 'b', 'd')
   - `inDegree['b'] = 0`
   - `inDegree['c'] = 1` (from 'a')
   - `inDegree['d'] = 1` (from 'b')

3. **Queue**: `['b']`
   - Pop `'b'`: order = `['b']`.
     - Neighbor `'a'`: `inDegree['a']` becomes $2 - 1 = 1$.
     - Neighbor `'d'`: `inDegree['d']` becomes $1 - 1 = 0 \implies$ Enqueue `'d'`.
   - Pop `'d'`: order = `['b', 'd']`.
     - Neighbor `'a'`: `inDegree['a']` becomes $1 - 1 = 0 \implies$ Enqueue `'a'`.
   - Pop `'a'`: order = `['b', 'd', 'a']`.
     - Neighbor `'c'`: `inDegree['c']` becomes $1 - 1 = 0 \implies$ Enqueue `'c'`.
   - Pop `'c'`: order = `['b', 'd', 'a', 'c']`.

4. Result has length $4 == K$.
   Valid Ordering: `b d a c`.

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N \cdot L + K)$ | Comparing $N-1$ adjacent word pairs takes $\mathcal{O}(N \cdot L)$ where $L$ is the maximum word length. Kahn's Algorithm on $K$ vertices and $E \le N$ edges takes $\mathcal{O}(K + E)$ time. Total time is $\mathcal{O}(N \cdot L + K)$. |
| **Space Complexity** | $\mathcal{O}(K + E)$ | Storing the adjacency list and in-degree array for $K$ characters takes $\mathcal{O}(K + E) = \mathcal{O}(K + \min(N, K^2))$ space. |

---

## Edge Cases
- **Prefix Violation (`["abc", "ab"]`)**: A longer word appears before its prefix; immediately triggers failure $\implies$ outputs empty line.
- **Single Word ($N = 1$)**: No constraints between characters; any permutation of the $K$ alphabet characters is valid.
- **Isolated Characters**: Characters that never participate in pairwise differences have `inDegree == 0` and are placed at valid positions in the ordering.
- **Cyclic Contradiction (`["ab", "ba", "ab"]`)**: Produces edges $a \to b$ and $b \to a$; Kahn's algorithm detects cycle $\implies$ outputs empty line.
- **Identical Adjacent Words**: Skipped without adding false constraints.
