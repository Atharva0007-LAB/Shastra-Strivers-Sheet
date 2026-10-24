# 130. Lower Bound - Editorial

## Problem Statement
Given a sorted array `arr` of size `n` and a target `x`, find the smallest index `i` such that `arr[i] >= x`. If every element is strictly less than `x`, return `n`.

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
Since the array is sorted, we can use binary search:
1. Initialize `low = 0`, `high = n - 1`, and `ans = n`.
2. While `low <= high`:
   - Compute `mid = low + (high - low) / 2`.
   - If `arr[mid] >= x`:
     This `mid` could be the answer. Record `ans = mid` and search left (`high = mid - 1`) for a potentially smaller index.
   - Else (`arr[mid] < x`):
     Target is in the right half, so search right (`low = mid + 1`).
3. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(log N) - halving search space at each iteration.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    public int lowerBound(int[] arr, int n, int x) {
        int low = 0, high = n - 1, ans = n;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= x) {
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
        System.out.println(sol.lowerBound(arr, n, x));
        sc.close();
    }
}
```
