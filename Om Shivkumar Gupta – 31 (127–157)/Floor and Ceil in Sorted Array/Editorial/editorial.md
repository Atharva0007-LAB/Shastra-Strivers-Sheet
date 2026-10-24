# 133. Floor and Ceil in Sorted Array - Editorial

## Problem Statement
Given a sorted array `arr` and an integer `x`, find the floor (largest element `<= x`) and ceil (smallest element `>= x`). Return `-1` if either does not exist.

---

## Sample Example

### Sample Input
```text
6 5
3 4 7 8 8 10
```

### Sample Output
```text
4 7
```

---

## Intuition & Approach
Both floor and ceil can be found independently using Binary Search in O(log N):
1. **Floor:**
   - When `arr[mid] <= x`, `arr[mid]` is a candidate for floor. Record `floor = arr[mid]` and search right (`low = mid + 1`) for a potentially larger valid value.
   - When `arr[mid] > x`, search left (`high = mid - 1`).
2. **Ceil:**
   - When `arr[mid] >= x`, `arr[mid]` is a candidate for ceil. Record `ceil = arr[mid]` and search left (`high = mid - 1`) for a potentially smaller valid value.
   - When `arr[mid] < x`, search right (`low = mid + 1`).

---

## Complexity Analysis
* **Time Complexity:** O(log N) for two binary searches.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    public int[] getFloorAndCeil(int[] arr, int n, int x) {
        int floor = -1, ceil = -1;
        int low = 0, high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] <= x) { floor = arr[mid]; low = mid + 1; }
            else high = mid - 1;
        }
        low = 0; high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= x) { ceil = arr[mid]; high = mid - 1; }
            else low = mid + 1;
        }
        return new int[]{floor, ceil};
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int x = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        Solution sol = new Solution();
        int[] res = sol.getFloorAndCeil(arr, n, x);
        System.out.println(res[0] + " " + res[1]);
        sc.close();
    }
}
```
