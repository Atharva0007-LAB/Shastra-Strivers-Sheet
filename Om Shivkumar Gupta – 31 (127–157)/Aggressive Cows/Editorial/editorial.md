# 149. Aggressive Cows - Editorial

## Problem Statement
Given stall positions and `k` cows, maximize the minimum distance between any two assigned cows.

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

---

## Intuition & Approach
If we can place `k` cows with minimum distance `D`, we can definitely place them with any distance `< D`.
This monotonic property enables Binary Search on the Answer:
1. Sort the stall positions in ascending order.
2. Search range: `low = 1`, `high = stalls[n-1] - stalls[0]`.
3. For `mid`:
   - Place cow 1 at `stalls[0]`.
   - Greedily place subsequent cows at the first stall whose coordinate is at least `lastPos + mid`.
   - If `count >= k`, record `ans = mid` and search right (`low = mid + 1`).
   - Otherwise, search left (`high = mid - 1`).
4. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(N log N + N log(max_distance)).
* **Space Complexity:** O(1) auxiliary space beyond the sorted stalls.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private boolean canPlace(int[] stalls, int dist, int k) {
        int count = 1, lastPos = stalls[0];
        for (int i = 1; i < stalls.length; i++) {
            if (stalls[i] - lastPos >= dist) {
                count++;
                lastPos = stalls[i];
            }
            if (count >= k) return true;
        }
        return false;
    }

    public int aggressiveCows(int[] stalls, int k) {
        Arrays.sort(stalls);
        int low = 1, high = stalls[stalls.length - 1] - stalls[0], ans = 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (canPlace(stalls, mid, k)) { ans = mid; low = mid + 1; }
            else high = mid - 1;
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
        System.out.println(sol.aggressiveCows(stalls, k));
        sc.close();
    }
}
```
