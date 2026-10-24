### Problem 132: Find the MST Weight
**Difficulty:** Medium | **Tags:** Graph, Minimum Spanning Tree, Disjoint Set, Greedy

**Problem Statement:**
Given a weighted, undirected, and connected graph of `V` vertices and `E` edges, find the sum of weights of the edges in the Minimum Spanning Tree (MST).

An MST is a subset of the edges of a connected edge-weighted undirected graph that connects all the vertices together, without any cycles and with the minimum possible total edge weight.

### Input Format
* The first line contains two integers `V` and `E`, representing the number of vertices and edges.
* The next `E` lines each contain three space-separated integers `u`, `v`, and `w`, representing an undirected edge between vertex `u` and vertex `v` with weight `w` (0 <= u, v < V).

### Output Format
* Print a single integer representing the sum of edge weights in the MST.

### Constraints
* 1 <= V <= 10^5
* 0 <= E <= 2 * 10^5
* -10^5 <= w <= 10^5

### Example
#### Input:
```text
3 3
0 1 5
1 2 3
0 2 1
```

#### Output:
```text
4
```

#### Explanation
* The Minimum Spanning Tree selects:
  - Edge (0, 2) with weight 1
  - Edge (1, 2) with weight 3
* Total weight = 1 + 3 = 4.
