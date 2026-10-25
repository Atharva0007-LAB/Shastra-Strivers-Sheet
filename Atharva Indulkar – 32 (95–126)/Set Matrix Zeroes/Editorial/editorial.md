# Editorial: Set Matrix Zeroes


## Difficulty

Medium


## Prerequisites
- 2D Arrays
- In-place Matrix Marking


## Problem Statement

Given an `m x n` integer matrix, if any element is 0, set its entire row and column to 0 in place.


## Explanation & Approach

### In-Place Constant Space Solution

Instead of using extra arrays or matrices of size $O(m + n)$ to mark zero rows and columns, we can use the **first row and first column** of the matrix itself as the marker storage!

1. **Track First Column Status**:
   The cell `matrix[0][0]` is shared by both row 0 and column 0. We use a variable `col0` (initially 1) to specifically track if the first column needs to be zeroed.
2. **First Pass (Marking)**:
   Traverse all cells `(i, j)`:
   - If `matrix[i][0] == 0`, set `col0 = 0`.
   - For `j > 0`, if `matrix[i][j] == 0`, mark its row and column:
     `matrix[i][0] = 0` and `matrix[0][j] = 0`.
3. **Second Pass (Updating inner cells)**:
   Traverse backwards from `i = m - 1` down to 0, and `j = n - 1` down to 1:
   - If `matrix[i][0] == 0` or `matrix[0][j] == 0`, set `matrix[i][j] = 0`.
   - Finally, if `col0 == 0`, set `matrix[i][0] = 0`.

Traversing backwards ensures that marker rows and columns are not overwritten prematurely.


## Java Solution

```java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int m = scanner.nextInt();
            int n = scanner.nextInt();
            int[][] matrix = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }

            int col0 = 1;
            for (int i = 0; i < m; i++) {
                if (matrix[i][0] == 0) col0 = 0;
                for (int j = 1; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        matrix[i][0] = 0;
                        matrix[0][j] = 0;
                    }
                }
            }

            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 1; j--) {
                    if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                        matrix[i][j] = 0;
                    }
                }
                if (col0 == 0) matrix[i][0] = 0;
            }

            for (int i = 0; i < m; i++) {
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

Traversing the matrix twice takes $O(m \times n)$.

### Total Time Complexity:
$O(\sum (m \times n))$ over all test cases.

### Space Complexity:
$O(1)$ auxiliary space as the first row and column are used for storage.
