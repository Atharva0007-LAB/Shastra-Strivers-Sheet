### Problem 143: Bridges in Graph
**Difficulty:** Hard | **Tags:** Graph, DFS, Tarjan's Algorithm, Bridges

**Problem Statement:**
Given an undirected connected graph with `V` vertices and `E` edges. A bridge is an edge whose removal causes the graph to become disconnected (increases number of connected components).

Find all bridges in the graph. Print each bridge on a new line with the smaller vertex first (`u < v`), and sort all bridges in ascending lexicographical order. If no bridges exist, print `-1`.

### Input Format
* The first line contains two integers `V` and `E`, representing the number of vertices and edges.
* The next `E` lines each contain two space-separated integers `u` and `v` representing an undirected edge.

### Output Format
* Print each bridge `u v` (with `u < v`) on a new line, sorted lexicographically. If no bridges exist, print `-1`.

### Constraints
* 1 <= V <= 10^5
* 0 <= E <= 2 * 10^5
* 0 <= u, v < V

### Example
#### Input:
```text
4 4
0 1
1 2
2 0
1 3
```

#### Output:
```text
1 3
```

#### Explanation
* Removing edge (1, 3) disconnects vertex 3 from the rest of the graph.
* Removing (0, 1), (1, 2), or (2, 0) leaves the graph connected via the remaining cycle.
* Therefore, the only bridge is 1 3.
