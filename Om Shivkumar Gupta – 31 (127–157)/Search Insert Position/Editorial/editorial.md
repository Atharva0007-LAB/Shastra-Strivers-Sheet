# 132. Search Insert Position - Editorial

## Problem Statement
Given a sorted array of distinct integers `nums` and a target `target`, return its index if found; otherwise, return the index where it should be inserted in order.

---

## Sample Example

### Sample Input
```text
4 5
1 3 5 6
```

### Sample Output
```text
2
```

---

## Intuition & Approach
The index where `target` should be inserted is the smallest index `idx` such that `nums[idx] >= target`.
This is exactly the **lower bound** of `target`!
1. Set `low = 0`, `high = n - 1`, `ans = n`.
2. Binary search:
   - If `nums[mid] >= target`, record `ans = mid` and search left (`high = mid - 1`).
   - If `nums[mid] < target`, search right (`low = mid + 1`).
3. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(log N) - halving the search space in each iteration.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    public int searchInsert(int[] nums, int target) {
        int low = 0, high = nums.length - 1, ans = nums.length;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] >= target) {
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
        int target = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.searchInsert(nums, target));
        sc.close();
    }
}
```
