# 131. Upper Bound - Editorial

## Problem Statement
Given a sorted array `arr` and a target `x`, find the smallest index `i` such that `arr[i] > x`. If every element is `<= x`, return `n`.

---

## Sample Example

### Sample Input
```text
5 7
3 5 8 15 19
```

### Sample Output
```text
2
```

---

## Intuition & Approach
Binary Search efficiently finds the first element strictly greater than `x`:
1. Initialize `low = 0`, `high = n - 1`, and `ans = n`.
2. While `low <= high`:
   - Compute `mid = low + (high - low) / 2`.
   - If `arr[mid] > x`:
     This `mid` could be the answer. Record `ans = mid` and search left (`high = mid - 1`).
   - Else (`arr[mid] <= x`):
     Element at `mid` is not strictly greater, so search right (`low = mid + 1`).
3. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(log N) - binary search over the array.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    public int upperBound(int[] arr, int n, int x) {
        int low = 0, high = n - 1, ans = n;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > x) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int x = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.upperBound(arr, n, x));
        sc.close();
    }
}
```
