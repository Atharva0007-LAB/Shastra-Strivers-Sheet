# 135. Search in Rotated Sorted Array I - Editorial

## Problem Statement
Given a rotated sorted array of distinct integers and a `target`, return the index of `target` or `-1` in O(log N) time.

---

## Sample Example

### Sample Input
```text
7 0
4 5 6 7 0 1 2
```

### Sample Output
```text
4
```

---

## Intuition & Approach
In any rotated sorted array, dividing the array at `mid` always produces **at least one strictly sorted half**:
1. Compute `mid = low + (high - low) / 2`.
2. If `nums[mid] == target`, return `mid`.
3. Check which half is sorted:
   - If `nums[low] <= nums[mid]`, the left half is sorted:
     - If `nums[low] <= target < nums[mid]`, target is inside the left half: search left (`high = mid - 1`).
     - Otherwise, search right (`low = mid + 1`).
   - Otherwise, the right half is sorted:
     - If `nums[mid] < target <= nums[high]`, target is inside the right half: search right (`low = mid + 1`).
     - Otherwise, search left (`high = mid - 1`).
4. If loop terminates without match, return `-1`.

---

## Complexity Analysis
* **Time Complexity:** O(log N) - binary search.
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

            if (nums[low] <= nums[mid]) {
                if (nums[low] <= target && target < nums[mid]) high = mid - 1;
                else low = mid + 1;
            } else {
                if (nums[mid] < target && target <= nums[high]) low = mid + 1;
                else high = mid - 1;
            }
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
