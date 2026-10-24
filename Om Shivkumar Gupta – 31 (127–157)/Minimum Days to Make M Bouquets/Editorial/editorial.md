# 145. Minimum Days to Make M Bouquets - Editorial

## Problem Statement
Determine the minimum days to make `m` bouquets with `k` adjacent flowers each from `bloomDay`. Return `-1` if impossible.

---

## Sample Example

### Sample Input
```text
5 3 1
1 10 3 10 2
```

### Sample Output
```text
3
```

---

## Intuition & Approach
1. If `(long)m * k > n`, we do not have enough flowers, so return `-1` immediately.
2. The number of bouquets we can form on day `D` is monotonically non-decreasing with respect to `D`.
3. Binary search on the answer range `[min(bloomDay), max(bloomDay)]`:
   - At day `mid`, greedily count consecutive bloomed flowers (`bloomDay[i] <= mid`). Every time `count == k`, increment bouquet count and reset `count = 0`.
   - If `bouquets >= m`, record `ans = mid` and search left (`high = mid - 1`).
   - Otherwise, search right (`low = mid + 1`).
4. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(N * log(max - min)) - O(N) linear scan per binary search step.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private boolean possible(int[] bloomDay, int day, int m, int k) {
        int count = 0, noOfB = 0;
        for (int b : bloomDay) {
            if (b <= day) count++;
            else { noOfB += count / k; count = 0; }
        }
        noOfB += count / k;
        return noOfB >= m;
    }

    public int minDays(int[] bloomDay, int m, int k) {
        if ((long) m * k > bloomDay.length) return -1;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int b : bloomDay) { min = Math.min(min, b); max = Math.max(max, b); }

        int low = min, high = max, ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (possible(bloomDay, mid, m, k)) {
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
        int m = sc.nextInt();
        int k = sc.nextInt();
        int[] bloomDay = new int[n];
        for (int i = 0; i < n; i++) bloomDay[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.minDays(bloomDay, m, k));
        sc.close();
    }
}
```
