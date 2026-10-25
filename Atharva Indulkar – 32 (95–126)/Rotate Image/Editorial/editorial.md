# Editorial: Rotate Image


## Difficulty

Medium


## Prerequisites
- 2D Arrays / Matrices
- Matrix Transposition


## Problem Statement

Given an $n \times n$ 2D matrix, rotate it 90 degrees clockwise in-place.


## Explanation & Approach

### Two-Step In-Place Rotation

A 90-degree clockwise rotation can be decomposed into two fundamental geometric transformations:
1. **Transpose the matrix**: Swap `matrix[i][j]` with `matrix[j][i]` for all $j > i$.
   - This turns rows into columns: element at $(i, j)$ goes to $(j, i)$.
2. **Reverse each row**: For every row $i$, reverse its elements horizontally.
   - Element at $(j, i)$ is moved to $(j, n - 1 - i)$.

Combining both steps moves every element $(i, j)$ directly to $(j, n - 1 - i)$, which is the exact definition of a 90-degree clockwise rotation!


## Java Solution

```java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = scanner.nextInt();
            int[][] matrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }

            // Transpose
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    int tmp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = tmp;
                }
            }

            // Reverse rows
            for (int i = 0; i < n; i++) {
                int left = 0, right = n - 1;
                while (left < right) {
                    int tmp = matrix[i][left];
                    matrix[i][left] = matrix[i][right];
                    matrix[i][right] = tmp;
                    left++;
                    right--;
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    sb.append(matrix[i][j]);
                    if (j != n - 1) sb.append(" ");
                }
                sb.append("\n");
            }
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

- Transposing visits $\frac{n(n-1)}{2}$ pairs $\rightarrow O(n^2)$.
- Reversing rows takes $n \times \frac{n}{2}$ swaps $\rightarrow O(n^2)$.

### Total Time Complexity:
$O(\sum n^2)$ over all test cases.

### Space Complexity:
$O(1)$ auxiliary space as the rotation is performed in-place.
