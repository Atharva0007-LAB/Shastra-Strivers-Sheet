# 136. Search in Rotated Sorted Array II - Editorial

## Problem Statement
Given a rotated sorted array that may contain duplicates and a target value `target`, determine whether `target` exists in the array (return `true` or `false`).

---

## Sample Example

### Sample Input
```text
7 0
2 5 6 0 0 1 2
```

### Sample Output
```text
true
```

---

## Intuition & Approach
With duplicates, `nums[low] == nums[mid] == nums[high]` can occur (e.g. `[3, 1, 2, 3, 3, 3, 3]`), making it impossible to determine which half is sorted.
To overcome this:
1. If `nums[low] == nums[mid] && nums[mid] == nums[high]`:
   - Since `nums[mid] != target`, neither `nums[low]` nor `nums[high]` can be the target.
   - Safely increment `low++` and decrement `high--` to bypass the duplicate edge elements.
2. Otherwise, check which half is sorted:
   - If `nums[low] <= nums[mid]`: left half is sorted.
     - If `nums[low] <= target < nums[mid]`, search left; otherwise right.
   - Else: right half is sorted.
     - If `nums[mid] < target <= nums[high]`, search right; otherwise left.
3. If search space is exhausted, return `false`.

---

## Complexity Analysis
* **Time Complexity:** O(log N) on average; O(N/2) worst-case when all elements are duplicates.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    public boolean search(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) return true;

            if (nums[low] == nums[mid] && nums[mid] == nums[high]) {
                low++;
                high--;
                continue;
            }

            if (nums[low] <= nums[mid]) {
                if (nums[low] <= target && target < nums[mid]) high = mid - 1;
                else low = mid + 1;
            } else {
                if (nums[mid] < target && target <= nums[high]) low = mid + 1;
                else high = mid - 1;
            }
        }
        return false;
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
