# 127. Floyd Warshall Algorithm - Editorial

## Problem Statement
The problem is to find the shortest distances between every pair of vertices in a given edge-weighted directed graph. The graph is represented as an adjacency matrix of size `V x V`. 

If there is no direct edge between vertex `i` and vertex `j`, `matrix[i][j] = -1`. If an edge exists, `matrix[i][j]` represents the weight of that edge. The diagonal values `matrix[i][i]` are `0`.

Your task is to compute the shortest distance between every pair of vertices in-place. If vertex `j` is unreachable from vertex `i`, the value should remain `-1`.

---

## Sample Example

### Sample Input
```text
4
0 2 -1 -1
1 0 3 -1
-1 -1 0 -1
3 5 4 0
```

### Sample Output
```text
0 2 5 -1
1 0 3 -1
-1 -1 0 -1
3 5 4 0
```

### Explanation
* From vertex 0:
  - Distance to 0 is 0.
  - Distance to 1 is 2.
  - Distance to 2 is 0 -> 1 -> 2 with cost 2 + 3 = 5.
  - Vertex 3 is unreachable from 0, so distance is -1.
* The matrix is updated with all-pairs shortest path values.

---

## Intuition
The Floyd-Warshall algorithm is a classic Dynamic Programming algorithm used to solve the All-Pairs Shortest Path (APSP) problem on directed/undirected edge-weighted graphs.

Instead of running single-source algorithms (like Dijkstra) V times, Floyd-Warshall builds shortest paths incrementally by considering which vertices can serve as intermediate stepping stones.

## Approach
1. Initialize the adjacency matrix:
   - For all `i, j`, if `matrix[i][j] == -1`, treat the edge weight as `infinity` (`1e9`).
   - For all `i`, `matrix[i][i] = 0`.
2. Iterate through each vertex `k` from `0` to `V - 1`. Vertex `k` serves as an intermediate transit point.
3. For each pair `(i, j)`, check if path `i -> k -> j` is shorter than the currently recorded distance `matrix[i][j]`:
   `matrix[i][j] = min(matrix[i][j], matrix[i][k] + matrix[k][j])`
4. After considering all intermediate vertices, replace all entries that remain `>= 1e9` back with `-1` to signify unreachability.

---

## Complexity Analysis
* **Time Complexity:** O(V^3) where V is the number of vertices, due to the 3 nested loops running V iterations each.
* **Space Complexity:** O(1) auxiliary space beyond the given adjacency matrix, as computation is done in-place.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    /**
     * Computes all-pairs shortest paths using the Floyd-Warshall Algorithm.
     * 
     * @param matrix Adjacency matrix of size V x V where -1 denotes no edge.
     */
    public void shortestDistance(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Replace unreachable (-1) with a large sentinel value,
        // while keeping 0 on the diagonal.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == -1) {
                    matrix[i][j] = (int) 1e9;
                }
                if (i == j) {
                    matrix[i][j] = 0;
                }
            }
        }

        // Step 2: Floyd-Warshall dynamic programming step
        // Try every vertex k as an intermediate point between i and j
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][k] < 1e9 && matrix[k][j] < 1e9) {
                        matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                    }
                }
            }
        }

        // Step 3: Convert unreachable entries back to -1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] >= 1e9) {
                    matrix[i][j] = -1;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

        Solution sol = new Solution();
        sol.shortestDistance(matrix);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + (j == n - 1 ? "" : " "));
            }
            System.out.println();
        }
        sc.close();
    }
}
```
