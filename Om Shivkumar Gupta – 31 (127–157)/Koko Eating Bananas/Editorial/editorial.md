# 144. Koko Eating Bananas - Editorial

## Problem Statement
Find the minimum eating speed `k` such that Koko finishes all banana piles within `h` hours.

---

## Sample Example

### Sample Input
```text
4 8
3 6 7 11
```

### Sample Output
```text
4
```

---

## Intuition & Approach
The total time required to eat all bananas at speed `k` is:
`Total Hours = sum(ceil(piles[i] / k))`
As speed `k` increases, total hours monotonically decreases.
Thus, we can binary search the speed `k`:
1. Search range: `low = 1`, `high = max(piles)`.
2. For midpoint `mid`:
   - Calculate total hours required using integer ceil arithmetic: `(pile + mid - 1) / mid`.
   - If `totalHours <= h`, record `ans = mid` and search left (`high = mid - 1`).
   - If `totalHours > h`, search right (`low = mid + 1`).
3. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(N * log(max(piles))) - O(N) evaluation per binary search iteration.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private boolean canEatAll(int[] piles, int speed, int h) {
        long totalHours = 0;
        for (int pile : piles) {
            totalHours += (pile + speed - 1) / speed;
            if (totalHours > h) return false;
        }
        return totalHours <= h;
    }

    public int minEatingSpeed(int[] piles, int h) {
        int low = 1, high = 0;
        for (int p : piles) high = Math.max(high, p);
        int ans = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (canEatAll(piles, mid, h)) {
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
        int h = sc.nextInt();
        int[] piles = new int[n];
        for (int i = 0; i < n; i++) piles[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.minEatingSpeed(piles, h));
        sc.close();
    }
}
```
