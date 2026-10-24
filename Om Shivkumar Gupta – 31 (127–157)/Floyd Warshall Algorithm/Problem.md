### Problem 127: Floyd Warshall Algorithm
**Difficulty:** Medium | **Tags:** Graph, Dynamic Programming, Shortest Path

**Problem Statement:**
The problem is to find the shortest distances between every pair of vertices in a given edge-weighted directed graph. The graph is represented as an adjacency matrix of size `V x V`. 

If there is no direct edge between vertex `i` and vertex `j`, `matrix[i][j] = -1`. If an edge exists, `matrix[i][j]` represents the weight of that edge. The diagonal values `matrix[i][i]` are `0`.

Your task is to compute the shortest distance between every pair of vertices in-place. If vertex `j` is unreachable from vertex `i`, the value should remain `-1`.

### Input Format
* The first line contains an integer `V`, representing the number of vertices in the graph.
* The next `V` lines each contain `V` space-separated integers representing the adjacency matrix of the graph.

### Output Format
* Print `V` lines, each containing `V` space-separated integers representing the shortest distances between all pairs of vertices.

### Constraints
* 1 <= V <= 100
* -1 <= matrix[i][j] <= 10^4
* matrix[i][i] = 0

### Example
#### Input:
```text
4
0 2 -1 -1
1 0 3 -1
-1 -1 0 -1
3 5 4 0
```

#### Output:
```text
0 2 5 -1
1 0 3 -1
-1 -1 0 -1
3 5 4 0
```

#### Explanation
* From vertex 0:
  - Distance to 0 is 0.
  - Distance to 1 is 2.
  - Distance to 2 is 0 -> 1 -> 2 with cost 2 + 3 = 5.
  - Vertex 3 is unreachable from 0, so distance is -1.
* The matrix is updated with all-pairs shortest path values.
