# Editorial: Word Ladder I

## Problem Overview
Given two distinct words `startWord` and `targetWord`, and a dictionary of unique words `wordList` (all words having equal length $M$), we need to find the length of the shortest transformation sequence from `startWord` to `targetWord`.

Each transformation step can only modify **exactly one letter**, and every intermediate word (including `targetWord`) must exist in `wordList`. If no valid sequence exists, return `0`.

---

## Graph Modeling & BFS
This problem maps directly to finding the **shortest path in an unweighted graph**:
- **Vertices**: Each valid word in `wordList` (plus `startWord`).
- **Edges**: An undirected edge exists between two words if and only if their Hamming distance is exactly 1 (they differ by exactly one letter).
- **Edge Weights**: Every edge has a uniform cost of 1 transformation step.

In an unweighted graph, **Breadth-First Search (BFS)** is guaranteed to find the shortest path level by level.

---

## Efficient Neighbor Generation
How do we find all valid adjacent words for a word $W$ of length $M$?

### Naive Approach:
Compare $W$ against all $N$ words in `wordList`.
- Time to check one word against $N$ words: $\mathcal{O}(N \cdot M)$.
- Total time across BFS: $\mathcal{O}(N^2 \cdot M)$.

### Optimized Character-Substitution Approach:
1. Store all words from `wordList` in a `HashSet<String>` for $\mathcal{O}(M)$ average lookup time.
2. For the current word of length $M$:
   - For each of the $M$ character positions:
     - Replace the character with each of the 26 lowercase English letters `'a' \dots 'z'`.
     - Check if the newly formed word exists in the `HashSet`.
   - Number of candidate words generated per node: $M \times 26$.
3. When a candidate word is found in the set:
   - **Immediately remove it** from the `HashSet`! Removing it serves as marking it as visited, preventing infinite cycles and redundant queue entries.
   - Enqueue the new word along with `currentLevel + 1`.
4. If the new word equals `targetWord`, immediately return `currentLevel + 1`.

---

## Step-by-Step Dry Run

### Example 1:
- `startWord = "der"`, `targetWord = "dfs"`
- `wordList = ["des", "der", "dfr", "dgt", "dfs"]`

1. **Initialization**:
   - `wordSet = {"des", "der", "dfr", "dgt", "dfs"}`
   - `targetWord` is in `wordSet` $\implies$ proceed.
   - Queue: `[("der", 1)]`
   - Remove `"der"` from `wordSet`.

2. **Step 1: Process ("der", 1)**:
   - Generate words differing by 1 letter:
     - Change index 0: `"aer"`, `"ber"`, ...
     - Change index 1: `"dfr"` $\in wordSet$!
       - Remove `"dfr"`, Enqueue `("dfr", 2)`
     - Change index 2: `"des"` $\in wordSet$!
       - Remove `"des"`, Enqueue `("des", 2)`
   - Queue: `[("dfr", 2), ("des", 2)]`

3. **Step 2: Process ("dfr", 2)**:
   - From `"dfr"`:
     - Change index 2 to `'s'`: `"dfs"` $\in wordSet$!
     - `"dfs" == targetWord`!
     - Shortest sequence length = `2 + 1 = 3`.
   - Return `3`.

Transformation sequence: `"der" -> "dfr" -> "dfs"` (Length = 3).

---

## Complexity Analysis

| Metric | Complexity | Explanation |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N \cdot M^2 \cdot 26)$ | There are at most $N$ words visited. For each word of length $M$, we generate $26 \cdot M$ candidate strings. Creating each candidate string takes $\mathcal{O}(M)$ time, and set lookup takes $\mathcal{O}(M)$ time. Total time is $\mathcal{O}(N \cdot M^2 \cdot 26)$, which takes just a few milliseconds for $N \le 1000, M \le 10$. |
| **Space Complexity** | $\mathcal{O}(N \cdot M)$ | Storing all words in the `HashSet` takes $\mathcal{O}(N \cdot M)$ space. The BFS queue holds at most $N$ words of length $M$. |

---

## Edge Cases
- **Target Word Missing**: If `targetWord` is not in `wordList`, no valid sequence can end at `targetWord`. Return `0` immediately.
- **One-Step Transformation**: `startWord` and `targetWord` differ by 1 letter $\implies$ output is `2`.
- **Target Unreachable**: All reachable paths hit a dead end without reaching `targetWord` $\implies$ return `0`.
- **Word Length 1**: Trivial single character transitions (`"a" -> "b"`).
