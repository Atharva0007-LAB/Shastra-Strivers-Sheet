### Problem: Minimum Multiplications to Reach End
**Difficulty:** Medium | **Tags:** Graphs, Breadth-First Search, Shortest Path, Modular Arithmetic

---

### Problem Statement
Given two positive integers `start` and `end`, and an array `arr` of $n$ positive integers.

In each step, you can multiply your current number by any number present in `arr`, and then take the result modulo $100000$ ($10^5$) to obtain the new number:
$$\text{next} = (\text{current} \times \text{arr}[i]) \pmod{100000}$$

Find the **minimum number of multiplications** (steps) required to reach `end` starting from `start`.
* If `start == end`, the minimum number of multiplications is $0$.
* If it is not possible to reach `end`, return `-1`.

---

### Input Format
* The first line contains a single integer $n$, representing the size of the array `arr`.
* The second line contains two space-separated integers `start` and `end`.
* The third line contains $n$ space-separated integers representing the array `arr`.

---

### Output Format
* Print a single integer representing the minimum number of steps to reach `end` from `start`, or `-1` if unreachable.

---

### Constraints
* $1 \le n \le 10^4$
* $1 \le \text{arr}[i] \le 10^4$
* $1 \le \text{start}, \text{end} < 10^5$

---

### Examples

#### Example 1
**Input:**
```text
3
3 30
2 5 7
```

**Output:**
```text
2
```

**Explanation:**
* Step 1: $3 \times 2 = 6 \pmod{100000} = 6$
* Step 2: $6 \times 5 = 30 \pmod{100000} = 30$
In $2$ multiplications, we reach $30$.

---

#### Example 2
**Input:**
```text
3
7 66175
3 4 65
```

**Output:**
```text
4
```

**Explanation:**
* Step 1: $7 \times 3 = 21 \pmod{100000} = 21$
* Step 2: $21 \times 3 = 63 \pmod{100000} = 63$
* Step 3: $63 \times 65 = 4095 \pmod{100000} = 4095$
* Step 4: $4095 \times 65 = 266175 \pmod{100000} = 66175$
In $4$ multiplications, we reach $66175$.

---

#### Example 3
**Input:**
```text
3
7 21
3 4 65
```

**Output:**
```text
1
```

**Explanation:**
In $1$ step: $7 \times 3 = 21 \pmod{100000} = 21$.
