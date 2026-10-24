# 150. Minimum number of platforms required for a railway - Editorial

## Problem Statement
Given arrival and departure times of all trains that reach a railway station, find the minimum number of platforms required for the railway station so that no train is kept waiting.

Consider that all trains arrive and depart on the same day. At any given instance of time, the same platform cannot be used for both departure of a train and arrival of another train (if arrival == departure, 2 platforms are required).

---

## Sample Example

### Sample Input
```text
6
900 940 950 1100 1500 1800
910 1200 1120 1130 1900 2000
```

### Sample Output
```text
3
```

### Explanation
* Maximum overlap occurs between 11:00 and 11:20 when 3 trains are present at the station simultaneously (trains arriving at 940, 950, and 1100).
* Hence, minimum 3 platforms are needed.

---

## Intuition
We do not need to track which specific train is on which platform.
Instead, we only need to track the instantaneous count of trains currently present at the station.
Whenever a train arrives, the count increases by 1. Whenever a train departs, the count decreases by 1.
The maximum count at any moment is the minimum number of platforms required.

## Approach
1. Sort the arrival times array `arr` and departure times array `dep` independently in ascending order.
2. Maintain two pointers `i = 0` (for `arr`) and `j = 0` (for `dep`).
3. While `i < N` and `j < N`:
   - If `arr[i] <= dep[j]`: a train has arrived; increment `platformsNeeded`, update `maxPlatforms`, and increment `i`.
   - Else: a train has departed; decrement `platformsNeeded` and increment `j`.
4. Return `maxPlatforms`.

---

## Complexity Analysis
* **Time Complexity:** O(N log N) dominated by sorting the arrival and departure arrays.
* **Space Complexity:** O(1) auxiliary space beyond the input arrays.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    /**
     * Finds the minimum number of platforms required using Two Pointers on sorted events.
     */
    public int findPlatform(int[] arr, int[] dep, int n) {
        Arrays.sort(arr);
        Arrays.sort(dep);

        int platformsNeeded = 0;
        int maxPlatforms = 0;
        int i = 0, j = 0;

        while (i < n && j < n) {
            // If another train arrives before or at the departure of the earliest departing train
            if (arr[i] <= dep[j]) {
                platformsNeeded++;
                maxPlatforms = Math.max(maxPlatforms, platformsNeeded);
                i++;
            } else {
                platformsNeeded--;
                j++;
            }
        }

        return maxPlatforms;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] arr = new int[n];
        int[] dep = new int[n];

        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();
        for (int i = 0; i < n; i++) dep[i] = sc.nextInt();

        Solution sol = new Solution();
        int ans = sol.findPlatform(arr, dep, n);
        System.out.println(ans);
        sc.close();
    }
}
```
