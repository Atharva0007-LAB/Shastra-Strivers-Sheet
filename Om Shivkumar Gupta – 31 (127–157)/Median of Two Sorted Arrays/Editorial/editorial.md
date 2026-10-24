# 152. Median of Two Sorted Arrays - Editorial

## Problem Statement
Given two sorted arrays, find their combined median in `O(log(min(m, n)))` time.

---

## Sample Example

### Sample Input
```text
2 2
1 3
2 4
```

### Sample Output
```text
2.50000
```

---

## Intuition & Approach
To find the median without merging, partition both arrays into left and right halves such that:
- Total elements in left half = `(n1 + n2 + 1) / 2`.
- All elements in left half are `<= ` all elements in right half (`l1 <= r2` and `l2 <= r1`).

Binary search on partition size `mid1` of the smaller array:
1. Ensure `nums1` is the smaller array (`n1 <= n2`).
2. Search range: `low = 0`, `high = n1`.
3. `mid1 = (low + high) / 2`, `mid2 = left - mid1`.
4. Define:
   - `l1 = nums1[mid1 - 1]` (or -INF if `mid1 == 0`)
   - `l2 = nums2[mid2 - 1]` (or -INF if `mid2 == 0`)
   - `r1 = nums1[mid1]` (or +INF if `mid1 == n1`)
   - `r2 = nums2[mid2]` (or +INF if `mid2 == n2`)
5. If `l1 <= r2` and `l2 <= r1`:
   - If `(n1 + n2)` is odd: median is `max(l1, l2)`.
   - If even: median is `(max(l1, l2) + min(r1, r2)) / 2.0`.
6. Else if `l1 > r2`: search left (`high = mid1 - 1`).
7. Else: search right (`low = mid1 + 1`).

---

## Complexity Analysis
* **Time Complexity:** O(log(min(M, N))) - binary search on the shorter array.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);
        int n1 = nums1.length, n2 = nums2.length;
        int low = 0, high = n1, left = (n1 + n2 + 1) / 2;

        while (low <= high) {
            int mid1 = (low + high) / 2;
            int mid2 = left - mid1;

            int l1 = (mid1 > 0) ? nums1[mid1 - 1] : Integer.MIN_VALUE;
            int l2 = (mid2 > 0) ? nums2[mid2 - 1] : Integer.MIN_VALUE;
            int r1 = (mid1 < n1) ? nums1[mid1] : Integer.MAX_VALUE;
            int r2 = (mid2 < n2) ? nums2[mid2] : Integer.MAX_VALUE;

            if (l1 <= r2 && l2 <= r1) {
                if ((n1 + n2) % 2 == 1) return Math.max(l1, l2);
                return ((double) Math.max(l1, l2) + Math.min(r1, r2)) / 2.0;
            } else if (l1 > r2) {
                high = mid1 - 1;
            } else {
                low = mid1 + 1;
            }
        }
        return 0.0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int m = sc.nextInt();
        int n = sc.nextInt();
        int[] nums1 = new int[m];
        for (int i = 0; i < m; i++) nums1[i] = sc.nextInt();
        int[] nums2 = new int[n];
        for (int i = 0; i < n; i++) nums2[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.printf(Locale.US, "%.5f\n", sol.findMedianSortedArrays(nums1, nums2));
        sc.close();
    }
}
```
