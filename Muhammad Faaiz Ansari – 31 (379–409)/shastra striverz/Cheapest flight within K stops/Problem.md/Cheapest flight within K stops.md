### Problem: Cheapest Flight Within K Stops
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Dijkstra's Algorithm, Bellman-Ford, Shortest Path

---

### Problem Statement
There are $n$ cities numbered from $0$ to $n - 1$ connected by $m$ directed flights. You are given a list of flights where each flight is represented as $[u, v, \text{price}]$, indicating a directed flight from city $u$ to city $v$ with a flight cost of $\text{price}$.

Given three integers $src$, $dst$, and $K$, find the **cheapest price** to travel from city $src$ to city $dst$ with **at most $K$ stops**.
* A "stop" refers to an intermediate city visited along the path between $src$ and $dst$.
* Having at most $K$ stops means the path can use at most $K + 1$ flight segments (edges).
* If there is no possible route from $src$ to $dst$ within $K$ stops, return `-1`.
* If $src = dst$, the cost is $0$.

---

### Input Format
* The first line contains two space-separated integers $n$ (the number of cities) and $m$ (the number of flights).
* The second line contains three space-separated integers $src$, $dst$, and $K$.
* The next $m$ lines each contain three space-separated integers $u$, $v$, and $\text{price}$, denoting a directed flight from city $u$ to city $v$ with cost $\text{price}$.
* If $m = 0$, only the first two lines are provided.

---

### Output Format
* Print a single integer representing the cheapest price from $src$ to $dst$ within at most $K$ stops, or `-1` if no such route exists.

---

### Constraints
* $1 \le n \le 100$
* $0 \le m \le \frac{n(n - 1)}{2}$
* $0 \le u, v < n$ and $u \ne v$
* $1 \le \text{price} \le 10^4$
* There are no duplicate flights between the same ordered pair of cities.
* $0 \le src, dst, K < n$

---

### Examples

#### Example 1
**Input:**
```text
4 5
0 3 1
0 1 100
1 2 100
2 0 100
1 3 600
2 3 200
```

**Output:**
```text
700
```

**Explanation:**
Paths from city $0$ to city $3$:
* $0 \to 1 \to 3$ with cost $100 + 600 = 700$ ($1$ stop at city $1$).
* $0 \to 1 \to 2 \to 3$ with cost $100 + 100 + 200 = 400$ ($2$ stops at cities $1$ and $2$).

Since $K = 1$, we are allowed at most $1$ stop. The path $0 \to 1 \to 2 \to 3$ is invalid because it uses $2$ stops.
The cheapest valid route is $0 \to 1 \to 3$ with cost $700$.

---

#### Example 2
**Input:**
```text
3 3
0 2 1
0 1 100
1 2 100
0 2 500
```

**Output:**
```text
200
```

**Explanation:**
* Direct flight $0 \to 2$ costs $500$ ($0$ stops).
* Route $0 \to 1 \to 2$ costs $100 + 100 = 200$ ($1$ stop).
With $K = 1$, the optimal path is $0 \to 1 \to 2$ with cost $200$.

---

#### Example 3
**Input:**
```text
3 3
0 2 0
0 1 100
1 2 100
0 2 500
```

**Output:**
```text
500
```

**Explanation:**
With $K = 0$, no intermediate stops are permitted. The only valid path is the direct flight $0 \to 2$ with cost $500$.
