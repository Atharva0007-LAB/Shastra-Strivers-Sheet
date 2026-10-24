# 142. Find Nth Root of a Number - Editorial

## Problem Statement
Given `n` and `m`, return integer `r` such that `r^n == m`, or `-1` if no such integer exists.

---

## Sample Example

### Sample Input
```text
3 27
```

### Sample Output
```text
3
```

---

## Intuition & Approach
The search range for the root is `[1, m]`.
Since exponentiation grows monotonically, use binary search:
1. Define a helper function `checkPower(mid, n, m)` that multiplies `mid` by itself `n` times, aborting early if the product exceeds `m` to prevent integer overflow.
2. In the binary search loop:
   - If `mid^n == m`, return `mid`.
   - If `mid^n < m`, search right (`low = mid + 1`).
   - If `mid^n > m`, search left (`high = mid - 1`).
3. If search completes without exact match, return `-1`.

---

## Complexity Analysis
* **Time Complexity:** O(N * log M) - each check takes O(N) operations over O(log M) binary search iterations.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private int checkPower(int mid, int n, int m) {
        long ans = 1;
        for (int i = 1; i <= n; i++) {
            ans = ans * mid;
            if (ans > m) return 2;
        }
        if (ans == m) return 1;
        return 0;
    }

    public int nthRoot(int n, int m) {
        int low = 1, high = m;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int code = checkPower(mid, n, m);
            if (code == 1) return mid;
            if (code == 0) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int m = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.nthRoot(n, m));
        sc.close();
    }
}
```
