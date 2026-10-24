# 151. Find Peak Element - Editorial

## Problem Statement
Find any peak element (strictly greater than its neighbors) in an array in O(log N) time.

---

## Sample Example

### Sample Input
```text
4
1 2 3 1
```

### Sample Output
```text
2
```

---

## Intuition & Approach
Imagine the array as a mountain landscape:
- Check boundary indices `0` and `n - 1` first.
- For indices in range `[1, n - 2]`:
  - If `nums[mid] > nums[mid - 1]` and `nums[mid] > nums[mid + 1]`, `mid` is a peak.
  - If `nums[mid] > nums[mid - 1]`, we are on an ascending slope, so at least one peak must exist to the right (`low = mid + 1`).
  - Else, we are on a descending slope, so at least one peak must exist to the left (`high = mid - 1`).

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
    public int findPeakElement(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;
        if (nums[0] > nums[1]) return 0;
        if (nums[n - 1] > nums[n - 2]) return n - 1;

        int low = 1, high = n - 2;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) return mid;
            else if (nums[mid] > nums[mid - 1]) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.findPeakElement(nums));
        sc.close();
    }
}
```
