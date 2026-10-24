# 146. Capacity to Ship Packages Within D Days - Editorial

## Problem Statement
Find the minimum capacity required to ship all conveyor belt packages within `days` days.

---

## Sample Example

### Sample Input
```text
10 5
1 2 3 4 5 6 7 8 9 10
```

### Sample Output
```text
15
```

---

## Intuition & Approach
The search space for capacity:
- **Minimum capacity:** `max(weights)`, because the ship must at least be able to carry the heaviest individual package.
- **Maximum capacity:** `sum(weights)`, which delivers everything in 1 day.

Notice the monotonic property: higher capacity always allows shipping within fewer or equal days.
1. Binary search: `low = max(weights)`, `high = sum(weights)`.
2. For `mid`:
   - Simulate the shipping day by day greedily.
   - If `daysNeeded <= days`, record `ans = mid` and search left (`high = mid - 1`).
   - Else, search right (`low = mid + 1`).
3. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(N * log(sum - max)) - O(N) daily packing simulation.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private int countDays(int[] weights, int capacity) {
        int days = 1, load = 0;
        for (int w : weights) {
            if (load + w > capacity) { days++; load = w; }
            else load += w;
        }
        return days;
    }

    public int shipWithinDays(int[] weights, int days) {
        int max = 0, sum = 0;
        for (int w : weights) { max = Math.max(max, w); sum += w; }

        int low = max, high = sum, ans = sum;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (countDays(weights, mid) <= days) {
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
        int days = sc.nextInt();
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) weights[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.shipWithinDays(weights, days));
        sc.close();
    }
}
```
