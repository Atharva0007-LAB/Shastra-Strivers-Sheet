# 137. Find Minimum in Rotated Sorted Array - Editorial

## Problem Statement
Given a rotated sorted array of unique integers, find its minimum element in O(log N) time.

---

## Sample Example

### Sample Input
```text
5
3 4 5 1 2
```

### Sample Output
```text
1
```

---

## Intuition & Approach
Compare `nums[mid]` with `nums[high]`:
1. If `nums[mid] > nums[high]`:
   - The inflection point (minimum) lies to the right of `mid`, so set `low = mid + 1`.
2. Else (`nums[mid] <= nums[high]`):
   - The right half is sorted, so the minimum is either at `mid` or to the left of `mid`. Set `high = mid`.
3. When `low == high`, that index holds the minimum element. Return `nums[low]`.

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
    public int findMin(int[] nums) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] > nums[high]) low = mid + 1;
            else high = mid;
        }
        return nums[low];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.findMin(nums));
        sc.close();
    }
}
```
