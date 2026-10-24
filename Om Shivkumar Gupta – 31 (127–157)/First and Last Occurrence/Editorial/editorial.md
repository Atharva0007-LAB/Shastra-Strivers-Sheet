# 134. First and Last Occurrence - Editorial

## Problem Statement
Given a non-decreasing array `nums` and a target `target`, find the starting and ending index of `target`. If not found, return `-1 -1`.

---

## Sample Example

### Sample Input
```text
6 8
5 7 7 8 8 10
```

### Sample Output
```text
3 4
```

---

## Intuition & Approach
Instead of a linear scan taking O(N), we can find both boundaries independently using two binary searches:
1. **First Occurrence:**
   - When `nums[mid] == target`, record `ans = mid` and search left (`high = mid - 1`).
   - If `nums[mid] < target`, search right (`low = mid + 1`).
   - If `nums[mid] > target`, search left (`high = mid - 1`).
2. **Last Occurrence:**
   - When `nums[mid] == target`, record `ans = mid` and search right (`low = mid + 1`).
   - Adjust other conditions identically to standard binary search.

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
    private int findFirst(int[] nums, int target) {
        int low = 0, high = nums.length - 1, ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) { ans = mid; high = mid - 1; }
            else if (nums[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return ans;
    }

    private int findLast(int[] nums, int target) {
        int low = 0, high = nums.length - 1, ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) { ans = mid; low = mid + 1; }
            else if (nums[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return ans;
    }

    public int[] searchRange(int[] nums, int target) {
        return new int[]{findFirst(nums, target), findLast(nums, target)};
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int target = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        int[] res = sol.searchRange(nums, target);
        System.out.println(res[0] + " " + res[1]);
        sc.close();
    }
}
```
