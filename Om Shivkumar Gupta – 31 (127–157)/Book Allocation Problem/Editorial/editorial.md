# 150. Book Allocation Problem - Editorial

## Problem Statement
Allocate `n` books among `m` students contiguously such that the maximum pages assigned to any student is minimized. Return `-1` if `m > n`.

---

## Sample Example

### Sample Input
```text
4 2
12 34 67 90
```

### Sample Output
```text
113
```

---

## Intuition & Approach
1. If `m > n`, it is impossible to give each student at least one book. Return `-1`.
2. Binary search on answer range `[max(arr), sum(arr)]`:
   - At capacity `mid`, greedily allocate books to current student.
   - If adding the next book exceeds `mid`, assign to a new student.
   - If `studentsNeeded <= m`, this limit is feasible: record `ans = mid` and search left (`high = mid - 1`).
   - Otherwise, search right (`low = mid + 1`).
3. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(N * log(sum(arr))) - linear scan per binary search iteration.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private int countStudents(int[] arr, long maxPages) {
        int students = 1;
        long pages = 0;
        for (int p : arr) {
            if (pages + p > maxPages) { students++; pages = p; }
            else pages += p;
        }
        return students;
    }

    public long findPages(int[] arr, int n, int m) {
        if (m > n) return -1;
        long low = 0, high = 0;
        for (int p : arr) { low = Math.max(low, p); high += p; }

        long ans = high;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (countStudents(arr, mid) <= m) {
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
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.findPages(arr, n, m));
        sc.close();
    }
}
```
