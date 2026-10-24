### Problem: Course Schedule I
**Difficulty:** Medium | **Tags:** Graphs, Topological Sort, Breadth-First Search, Depth-First Search, Directed Acyclic Graph

**Problem Statement**
There are a total of $N$ tasks (or courses) labeled from $0$ to $N - 1$. 

You are given an array `prerequisites` where each element `[a, b]` indicates that you must complete course $b$ first before you are allowed to take course $a$ ($b \to a$).

Determine if it is possible to finish all tasks.

Return `true` if you can finish all tasks, or `false` otherwise.

---

**Input Format**
* The first line contains two space-separated integers $N$ (number of courses/tasks) and $M$ (number of prerequisite pairs).
* The next $M$ lines each contain two space-separated integers $a$ and $b$, representing the condition that course $b$ must be completed before course $a$ ($b \to a$).
* If $M = 0$, only the first line is given.

**Output Format**
* Print `true` if it is possible to finish all tasks, or `false` otherwise.

---

**Constraints**
* $1 \le N \le 2000$
* $0 \le M \le 5000$
* $0 \le a, b < N$ and $a \ne b$
* All pairs $[a, b]$ are unique.

---

**Example 1**
**Input:**
```text
4 3
1 0
2 1
3 2
```

**Output:**
```text
true
```

**Explanation:**
* Pair `[1, 0]` means take 0 before 1 ($0 \to 1$).
* Pair `[2, 1]` means take 1 before 2 ($1 \to 2$).
* Pair `[3, 2]` means take 2 before 3 ($2 \to 3$).
* The tasks can be completed in the order: `0 -> 1 -> 2 -> 3`.
* Therefore, all tasks can be finished. Output: `true`.

---

**Example 2**
**Input:**
```text
4 4
0 1
3 2
1 3
3 0
```

**Output:**
```text
false
```

**Explanation:**
* Pair `[0, 1]` means $1 \to 0$.
* Pair `[1, 3]` means $3 \to 1$.
* Pair `[3, 0]` means $0 \to 3$.
* Notice the circular dependency: $1 \to 0 \to 3 \to 1$. To take course 1, you need 3; to take 3, you need 0; to take 0, you need 1.
* Because of this cycle, none of these courses can ever be started. Output: `false`.

---

**Example 3**
**Input:**
```text
2 1
1 0
```

**Output:**
```text
true
```

**Explanation:**
* Course 0 must be taken before course 1. Take course 0 first, then course 1.
* Output: `true`.
