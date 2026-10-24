# 148. Painter's Partition - Editorial

## Problem Statement
Given `n` boards and `k` painters, find the minimum maximum time allocated to any painter such that all boards are painted continuously.

---

## Sample Example

### Sample Input
```text
4 2
10 20 30 40
```

### Sample Output
```text
60
```

---

## Intuition & Approach
This is a classic "binary search on answer" problem:
- Lower bound = `max(boards)` (the longest single board must be painted by some painter).
- Upper bound = `sum(boards)` (one painter paints everything).

Algorithm:
1. Search range: `low = max(boards)`, `high = sum(boards)`.
2. For midpoint `mid`:
   - Greedily assign boards to a painter until adding the next board exceeds `mid`.
   - When it exceeds, start a new painter.
   - If total painters needed `<= k`, `mid` is feasible: record `ans = mid` and search left (`high = mid - 1`).
   - Otherwise, search right (`low = mid + 1`).
3. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(N * log(sum(boards))) - O(N) check per binary search iteration.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private int countPainters(int[] boards, long maxAllowed) {
        int painters = 1;
        long currentLoad = 0;
        for (int b : boards) {
            if (currentLoad + b > maxAllowed) { painters++; currentLoad = b; }
            else currentLoad += b;
        }
        return painters;
    }

    public long findLargestMinDistance(int[] boards, int k) {
        long low = 0, high = 0;
        for (int b : boards) { low = Math.max(low, b); high += b; }
        long ans = high;

        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (countPainters(boards, mid) <= k) {
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
        int k = sc.nextInt();
        int[] boards = new int[n];
        for (int i = 0; i < n; i++) boards[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.findLargestMinDistance(boards, k));
        sc.close();
    }
}
```
