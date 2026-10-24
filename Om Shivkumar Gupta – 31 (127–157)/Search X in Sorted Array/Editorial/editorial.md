# 129. Search X in Sorted Array - Editorial

## Problem Statement
Given a sorted array `nums` and an integer `target`, return the index of `target` in `nums`, or `-1` if not found.

---

## Sample Example

### Sample Input
```text
6 9
-1 0 3 5 9 12
```

### Sample Output
```text
4
```

---

## Intuition & Approach
Since the array is sorted, we can divide the search interval in half at each step:
1. Maintain two pointers: `low = 0`, `high = n - 1`.
2. Compute `mid = low + (high - low) / 2`.
3. If `nums[mid] == target`, return `mid`.
4. If `nums[mid] < target`, target must lie in the right half, so set `low = mid + 1`.
5. If `nums[mid] > target`, target must lie in the left half, so set `high = mid - 1`.
6. If the search space is exhausted (`low > high`), return `-1`.

---

## Complexity Analysis
* **Time Complexity:** O(log N) - search space halves in each iteration.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    public int search(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int target = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.search(nums, target));
        sc.close();
    }
}
```
