# 156. Find Row with Maximum 1's - Editorial

## Problem Statement
Given an `n x m` binary matrix where each row is sorted, find the row index with the maximum number of 1's.

---

## Sample Example

### Sample Input
```text
3 3
0 1 1
0 0 1
0 0 0
```

### Sample Output
```text
0
```

---

## Intuition & Approach
Since every row is sorted with 0s preceding 1s:
- The count of 1's in row `i` is `m - firstOneIndex`, where `firstOneIndex` is the lower bound of 1 in that row.
- For each row, binary search finds the first occurrence of 1 in O(log M) time.
- Track the maximum count and the row index. If no 1s are encountered, return `-1`.

---

## Complexity Analysis
* **Time Complexity:** O(N * log M) - binary search across N rows.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private int lowerBound(int[] row, int m) {
        int low = 0, high = m - 1, ans = m;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (row[mid] >= 1) { ans = mid; high = mid - 1; }
            else low = mid + 1;
        }
        return ans;
    }

    public int rowWithMax1s(int[][] mat, int n, int m) {
        int maxOnes = 0, rowIdx = -1;
        for (int i = 0; i < n; i++) {
            int count = m - lowerBound(mat[i], m);
            if (count > maxOnes) { maxOnes = count; rowIdx = i; }
        }
        return rowIdx;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt(), m = sc.nextInt();
        int[][] mat = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) mat[i][j] = sc.nextInt();
        }
        Solution sol = new Solution();
        System.out.println(sol.rowWithMax1s(mat, n, m));
        sc.close();
    }
}
```
