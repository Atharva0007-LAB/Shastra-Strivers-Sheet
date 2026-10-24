# 154. Minimise Max Distance to Gas Stations - Editorial

## Problem Statement
Add `k` gas stations between existing stations to minimize the maximum adjacent distance.

---

## Sample Example

### Sample Input
```text
10 1
1 2 3 4 5 6 7 8 9 10
```

### Sample Output
```text
1.000000
```

---

## Intuition & Approach
Binary Search on Answer with floating point precision:
- If we aim for a maximum distance `dist` between any two adjacent stations, the number of stations required between `stations[i-1]` and `stations[i]` is:
  `floor((stations[i] - stations[i-1]) / dist)`
  (adjusted if exact integer multiple).
- If the total required stations `> k`, `dist` is too small: `low = mid`.
- If the total required stations `<= k`, `dist` is achievable: `high = mid`.
- Repeat until `high - low < 1e-6`.

---

## Complexity Analysis
* **Time Complexity:** O(N * log(max_dist / 1e-6)) ~ O(80 * N).
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private int numberOfGasStationsRequired(double dist, int[] stations) {
        int count = 0;
        for (int i = 1; i < stations.length; i++) {
            int numberInBetween = (int) ((stations[i] - stations[i - 1]) / dist);
            if ((stations[i] - stations[i - 1]) == numberInBetween * dist) numberInBetween--;
            count += numberInBetween;
        }
        return count;
    }

    public double findSmallestMaxDist(int[] stations, int k) {
        double low = 0, high = 0;
        for (int i = 0; i < stations.length - 1; i++) {
            high = Math.max(high, (double) (stations[i + 1] - stations[i]));
        }

        while (high - low > 1e-6) {
            double mid = (low + high) / 2.0;
            if (numberOfGasStationsRequired(mid, stations) > k) low = mid;
            else high = mid;
        }
        return high;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] stations = new int[n];
        for (int i = 0; i < n; i++) stations[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.printf(Locale.US, "%.6f\n", sol.findSmallestMaxDist(stations, k));
        sc.close();
    }
}
```
