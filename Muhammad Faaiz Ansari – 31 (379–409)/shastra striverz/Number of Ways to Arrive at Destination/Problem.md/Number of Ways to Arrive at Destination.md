### Problem: Number of Ways to Arrive at Destination
**Difficulty:** Medium | **Tags:** Graphs, Dijkstra's Algorithm, Dynamic Programming, Shortest Path

---

### Problem Statement
You are in a city consisting of $n$ intersections numbered from $0$ to $n - 1$ with bi-directional roads between some intersections. The inputs are generated such that you can reach any intersection from any other intersection, and there is at most one road between any two intersections.

You are given an integer $n$ and an array `roads` where $\text{roads}[i] = [u_i, v_i, \text{time}_i]$ indicates a bi-directional road between intersections $u_i$ and $v_i$ that takes $\text{time}_i$ minutes to travel.

Determine the **number of ways** to travel from intersection $0$ to intersection $n - 1$ in the **shortest amount of time**.

Since the answer may be large, return it modulo $10^9 + 7$.

---

### Input Format
* The first line contains two space-separated integers $n$ (the number of intersections) and $m$ (the number of roads).
* The next $m$ lines each contain three space-separated integers $u$, $v$, and $\text{time}$, describing a bi-directional road between intersection $u$ and intersection $v$ requiring $\text{time}$ minutes.
* If $m = 0$, only the first line is provided.

---

### Output Format
* Print a single integer representing the number of distinct paths from intersection $0$ to intersection $n - 1$ that achieve the minimum total travel time, modulo $10^9 + 7$.

---

### Constraints
* $1 \le n \le 200$
* $n - 1 \le m \le \frac{n(n - 1)}{2}$
* $0 \le u, v < n$ and $u \ne v$
* $1 \le \text{time} \le 10^9$
* All pairs $(u, v)$ are distinct (no multi-edges).
* The graph is connected.

---

### Examples

#### Example 1
**Input:**
```text
7 10
0 6 7
0 1 2
1 2 3
1 3 3
6 3 3
3 5 1
6 5 1
2 5 1
0 4 5
4 6 2
```

**Output:**
```text
4
```

**Explanation:**
The shortest total travel time from intersection $0$ to intersection $6$ is $7$ minutes.
The $4$ routes achieving this minimum time are:
* $0 \to 6$ (time 7)
* $0 \to 4 \to 6$ ($5 + 2 = 7$)
* $0 \to 1 \to 2 \to 5 \to 6$ ($2 + 3 + 1 + 1 = 7$)
* $0 \to 1 \to 3 \to 5 \to 6$ ($2 + 3 + 1 + 1 = 7$)

---

#### Example 2
**Input:**
```text
6 8
0 5 8
0 2 2
0 1 1
1 3 3
1 2 3
2 5 6
3 4 2
4 5 2
```

**Output:**
```text
3
```

**Explanation:**
The shortest calculated time from $0$ to $5$ is $8$ minutes.
The $3$ shortest paths are:
* $0 \to 5$ (time 8)
* $0 \to 2 \to 5$ ($2 + 6 = 8$)
* $0 \to 1 \to 3 \to 4 \to 5$ ($1 + 3 + 2 + 2 = 8$)

---

#### Example 3
**Input:**
```text
4 4
0 1 10
1 2 7
2 3 4
0 3 3
```

**Output:**
```text
1
```

**Explanation:**
The direct route $0 \to 3$ takes $3$ minutes. All other routes take significantly longer. Thus, there is only $1$ shortest route.
