# 157. Search in a 2D Matrix - Editorial

## Problem Statement
Search for `target` in an `m x n` matrix where each row is sorted and each row's first element exceeds the previous row's last element in O(log(M * N)) time.

---

## Sample Example

### Sample Input
```text
3 4 3
1 3 5 7
10 11 16 20
23 30 34 60
```

### Sample Output
```text
true
```

---

## Intuition & Approach
The two matrix properties imply that if we flatten the matrix row by row into a 1D array of size `m * n`, the resulting array is strictly sorted in ascending order!
Instead of creating a 1D copy, we can use virtual indexing:
- An index `mid` in the range `[0, m * n - 1]` maps to coordinates:
  - `row = mid / n`
  - `col = mid % n`
- Standard Binary Search:
  - If `matrix[row][col] == target`, return `true`.
  - If `matrix[row][col] < target`, search right: `low = mid + 1`.
  - If `matrix[row][col] > target`, search left: `high = mid - 1`.
  - If search space is exhausted, return `false`.

---

## Complexity Analysis
* **Time Complexity:** O(log(M * N)) - binary search across all M * N elements.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length, n = matrix[0].length;
        int low = 0, high = m * n - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int val = matrix[mid / n][mid % n];
            if (val == target) return true;
            else if (val < target) low = mid + 1;
            else high = mid - 1;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int m = sc.nextInt(), n = sc.nextInt(), target = sc.nextInt();
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) matrix[i][j] = sc.nextInt();
        }

        Solution sol = new Solution();
        System.out.println(sol.searchMatrix(matrix, target));
        sc.close();
    }
}
```
