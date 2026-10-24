# 153. Kth Element of Two Sorted Arrays - Editorial

## Problem Statement
Find the `k`-th element of two sorted arrays in `O(log(min(m, n)))` time.

---

## Sample Example

### Sample Input
```text
5 4 5
2 3 6 7 9
1 4 8 10
```

### Sample Output
```text
6
```

---

## Intuition & Approach
Similar to finding the median of two sorted arrays, partition `arr1` and `arr2` such that the left partition contains exactly `k` elements:
- Range for picking elements from `arr1`:
  - Minimum elements: `Math.max(0, k - n)` (if `k > n`, we must take at least `k - n` from `arr1`).
  - Maximum elements: `Math.min(k, m)` (we cannot take more than `m` or more than `k`).
- If `l1 <= r2` and `l2 <= r1`, the `k`-th element is `Math.max(l1, l2)`.
- If `l1 > r2`, too many elements taken from `arr1`: search left (`high = mid1 - 1`).
- Else, search right (`low = mid1 + 1`).

---

## Complexity Analysis
* **Time Complexity:** O(log(min(M, N))) - binary search on partition bounds.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    public long kthElement(int[] arr1, int[] arr2, int m, int n, int k) {
        if (m > n) return kthElement(arr2, arr1, n, m, k);
        int low = Math.max(0, k - n), high = Math.min(k, m);

        while (low <= high) {
            int mid1 = (low + high) / 2;
            int mid2 = k - mid1;

            int l1 = (mid1 > 0) ? arr1[mid1 - 1] : Integer.MIN_VALUE;
            int l2 = (mid2 > 0) ? arr2[mid2 - 1] : Integer.MIN_VALUE;
            int r1 = (mid1 < m) ? arr1[mid1] : Integer.MAX_VALUE;
            int r2 = (mid2 < n) ? arr2[mid2] : Integer.MAX_VALUE;

            if (l1 <= r2 && l2 <= r1) return Math.max(l1, l2);
            else if (l1 > r2) high = mid1 - 1;
            else low = mid1 + 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int m = sc.nextInt();
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] arr1 = new int[m];
        for (int i = 0; i < m; i++) arr1[i] = sc.nextInt();
        int[] arr2 = new int[n];
        for (int i = 0; i < n; i++) arr2[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.kthElement(arr1, arr2, m, n, k));
        sc.close();
    }
}
```
