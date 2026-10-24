# 141. Find Square Root of a Number - Editorial

## Problem Statement
Given an integer `n`, calculate `floor(sqrt(n))` using Binary Search.

---

## Sample Example

### Sample Input
```text
28
```

### Sample Output
```text
5
```

---

## Intuition & Approach
The search space for `sqrt(n)` is `[1, n]`.
Since `f(x) = x * x` is monotonically increasing for `x >= 0`, we can binary search:
1. Handle base cases: `n = 0` or `n = 1`.
2. Set `low = 1`, `high = n`, `ans = 1`.
3. Compute `mid = low + (high - low) / 2`.
4. If `mid <= n / mid` (avoiding overflow):
   - `mid` is a valid candidate. Record `ans = mid` and search right (`low = mid + 1`).
5. Else:
   - `mid` is too large: search left (`high = mid - 1`).
6. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(log N) - binary search.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    public long floorSqrt(long n) {
        if (n <= 1) return n;
        long low = 1, high = n, ans = 1;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (mid <= n / mid) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLong()) return;
        long n = sc.nextLong();

        Solution sol = new Solution();
        System.out.println(sol.floorSqrt(n));
        sc.close();
    }
}
```
