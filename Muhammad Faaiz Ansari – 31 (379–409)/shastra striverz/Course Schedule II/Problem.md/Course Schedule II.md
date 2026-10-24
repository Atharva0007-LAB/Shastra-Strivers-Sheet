### Problem: Course Schedule II
**Difficulty:** Medium | **Tags:** Graphs, Topological Sort, Breadth-First Search, Depth-First Search, Directed Acyclic Graph

**Problem Statement**
There are a total of $N$ tasks labeled from $0$ to $N - 1$. 

You are given an array `prerequisites` where each element `[a, b]` indicates that you must take course $b$ first before you are allowed to take course $a$ ($b \to a$).

Find the **order of tasks** you should pick to finish all tasks.

* If no such ordering exists (i.e. there is a circular dependency), return an empty array.
* If there are multiple valid orderings, any valid topological order is accepted.

---

**Input Format**
* The first line contains two space-separated integers $N$ (number of courses/tasks) and $M$ (number of prerequisite pairs).
* The next $M$ lines each contain two space-separated integers $a$ and $b$, representing the condition that course $b$ must be completed before course $a$ ($b \to a$).
* If $M = 0$, only the first line is given.

**Output Format**
* Print a single line with space-separated integers representing a valid task execution order.
* If it is impossible to finish all tasks, print an empty line.

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
0 1 2 3
```

**Explanation:**
* First finish task 0, as it has no prerequisites.
* Then finish task 1, since it depends only on task 0.
* Next finish task 2, since it depends only on task 1.
* Finally finish task 3, since it depends only on task 2.
* A valid order is `0 1 2 3`.

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

```

**Explanation:**
* The prerequisite pairs create a cycle: $1 \to 0 \to 3 \to 1$.
* Because of this circular dependency, it is impossible to finish all tasks.
* Output is an empty line.

---

**Example 3**
**Input:**
```text
2 1
1 0
```

**Output:**
```text
0 1
```

**Explanation:**
* Course 0 must be completed before course 1.
* The only valid order is `0 1`.
