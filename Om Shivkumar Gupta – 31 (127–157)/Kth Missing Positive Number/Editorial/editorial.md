# 147. Kth Missing Positive Number - Editorial

## Problem Statement
Given a strictly increasing array of positive integers and `k`, find the `k`-th missing positive integer in O(log N) time.

---

## Sample Example

### Sample Input
```text
5 5
2 3 4 7 11
```

### Sample Output
```text
9
```

---

## Intuition & Approach
For any index `i`, the expected value in a complete sequence starting at 1 is `i + 1`.
Thus, the count of missing numbers before index `i` is:
`missing = arr[i] - (i + 1)`

Since `arr` is strictly increasing, `missing` is non-decreasing with respect to `i`.
We can binary search to find the position where the `k`-th missing number falls:
1. Search range: `low = 0`, `high = n - 1`.
2. Compute `mid`:
   - If `arr[mid] - (mid + 1) < k`, the `k`-th missing number lies to the right: `low = mid + 1`.
   - Else, it lies to the left: `high = mid - 1`.
3. When the loop ends, `high` is the largest index with fewer than `k` missing numbers.
   The answer is:
   `arr[high] + (k - missing) = arr[high] + k - (arr[high] - (high + 1)) = high + 1 + k = low + k`.

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
    public int findKthPositive(int[] arr, int k) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int missing = arr[mid] - (mid + 1);
            if (missing < k) low = mid + 1;
            else high = mid - 1;
        }
        return low + k;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.findKthPositive(arr, k));
        sc.close();
    }
}
```
