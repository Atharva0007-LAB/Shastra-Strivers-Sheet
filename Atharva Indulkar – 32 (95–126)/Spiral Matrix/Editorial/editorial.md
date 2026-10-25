# Editorial: Spiral Matrix


## Difficulty

Medium


## Prerequisites
- 2D Arrays / Matrices
- Simulation with Boundaries


## Problem Statement

Given an `m x n` matrix, traverse and return all elements in clockwise spiral order.


## Explanation & Approach

### 4-Boundary Simulation

We maintain four boundary pointers:
- `top = 0`
- `bottom = m - 1`
- `left = 0`
- `right = n - 1`

At each loop iteration, we traverse along the four borders of the current perimeter:
1. **Move Right**: traverse row `top` from column `left` to `right`. Then increment `top++`.
2. **Move Down**: traverse column `right` from row `top` to `bottom`. Then decrement `right--`.
3. **Move Left**: if `top <= bottom`, traverse row `bottom` from column `right` down to `left`. Then decrement `bottom--`.
4. **Move Up**: if `left <= right`, traverse column `left` from row `bottom` up to `top`. Then increment `left++`.

Repeat while `top <= bottom && left <= right`.


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

            int top = 0, bottom = m - 1, left = 0, right = n - 1;
            boolean first = true;

            while (top <= bottom && left <= right) {
                for (int j = left; j <= right; j++) {
                    if (!first) sb.append(" ");
                    sb.append(matrix[top][j]);
                    first = false;
                }
                top++;

                for (int i = top; i <= bottom; i++) {
                    if (!first) sb.append(" ");
                    sb.append(matrix[i][right]);
                    first = false;
                }
                right--;

                if (top <= bottom) {
                    for (int j = right; j >= left; j--) {
                        if (!first) sb.append(" ");
                        sb.append(matrix[bottom][j]);
                        first = false;
                    }
                    bottom--;
                }

                if (left <= right) {
                    for (int i = bottom; i >= top; i--) {
                        if (!first) sb.append(" ");
                        sb.append(matrix[i][left]);
                        first = false;
                    }
                    left++;
                }
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Every element of the matrix is visited exactly once.

### Total Time Complexity:
$O(\sum (m \times n))$ over all test cases.

### Space Complexity:
$O(1)$ auxiliary space beyond the output buffer.
