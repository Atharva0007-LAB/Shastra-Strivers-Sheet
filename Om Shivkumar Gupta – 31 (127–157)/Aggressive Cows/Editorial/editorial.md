# 139. Aggressive Cows - Editorial

## Problem Statement
You are given an array `stalls` of size `N` representing the coordinates of stalls on a straight line. You are also given an integer `K`, which represents the number of aggressive cows.

Assign cows to the stalls such that the minimum distance between any two of them is as large as possible. Return the maximum possible minimum distance.

---

## Sample Example

### Sample Input
```text
6 4
0 3 4 7 10 9
```

### Sample Output
```text
3
```

### Explanation
* Sort the stalls: [0, 3, 4, 7, 9, 10]
* Place 4 cows at coordinates 0, 3, 7, 10:
  - Distances: (3-0)=3, (7-3)=4, (10-7)=3
* Minimum distance between any two cows is 3, which is the maximum possible.

---

## Intuition
We want to maximize the minimum distance between any two cows.
Notice the monotonic property:
If it is possible to place K cows such that every pair is at least D units apart, then it is also possible for any distance d < D.
Conversely, if we cannot place K cows at distance D, we certainly cannot place them at any distance > D.
This monotonicity allows us to apply Binary Search on the Answer!

## Approach
1. Sort the stall coordinates in ascending order.
2. The search space for the answer is:
   - `low = 1` (minimum possible positive distance)
   - `high = stalls[N - 1] - stalls[0]` (maximum possible distance)
3. For each midpoint `mid`, greedily check with `canWePlace(stalls, mid, k)`:
   - Place the first cow at `stalls[0]`.
   - Iterate through the remaining stalls. If `stalls[i] - lastPos >= mid`, place another cow and update `lastPos = stalls[i]`.
   - If at least `K` cows are placed, return `true`.
4. If `canWePlace` is true, record `ans = mid` and search the right half (`low = mid + 1`).
   Otherwise, search the left half (`high = mid - 1`).
5. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(N log N + N log(max_distance)) where N log N is for sorting and each binary search step takes O(N) verification.
* **Space Complexity:** O(1) auxiliary space beyond the sorted input array.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    /**
     * Checks if K cows can be placed with at least 'minDist' distance apart.
     */
    private boolean canWePlace(int[] stalls, int minDist, int k) {
        int countCows = 1;
        int lastPos = stalls[0];

        for (int i = 1; i < stalls.length; i++) {
            if (stalls[i] - lastPos >= minDist) {
                countCows++;
                lastPos = stalls[i];
            }
            if (countCows >= k) return true;
        }
        return false;
    }

    /**
     * Finds the maximum possible minimum distance between K aggressive cows.
     */
    public int aggressiveCows(int[] stalls, int k) {
        Arrays.sort(stalls);
        int n = stalls.length;
        int low = 1;
        int high = stalls[n - 1] - stalls[0];
        int ans = 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (canWePlace(stalls, mid, k)) {
                ans = mid;
                low = mid + 1; // Try larger distance
            } else {
                high = mid - 1; // Distance too large
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] stalls = new int[n];
        for (int i = 0; i < n; i++) stalls[i] = sc.nextInt();

        Solution sol = new Solution();
        int res = sol.aggressiveCows(stalls, k);
        System.out.println(res);
        sc.close();
    }
}
```
