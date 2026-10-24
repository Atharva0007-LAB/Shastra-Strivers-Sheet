# 139. Single Element in Sorted Array - Editorial

## Problem Statement
Given a sorted array where every element appears twice except one, find the unique single element in O(log N) time.

---

## Sample Example

### Sample Input
```text
9
1 1 2 3 3 4 4 8 8
```

### Sample Output
```text
2
```

---

## Intuition & Approach
Notice the index pattern of duplicate pairs:
- **To the left of the single element:** Pairs start at even indices and end at odd indices `(even, odd)`.
- **To the right of the single element:** The insertion of the unique element shifts everything by 1, so pairs start at odd indices and end at even indices `(odd, even)`.

Using Binary Search:
1. Handle boundary cases (`n == 1`, `nums[0] != nums[1]`, `nums[n-1] != nums[n-2]`).
2. Search range: `low = 1`, `high = n - 2`.
3. If `nums[mid] != nums[mid-1] && nums[mid] != nums[mid+1]`, `nums[mid]` is the single element.
4. If `(mid % 2 == 1 && nums[mid] == nums[mid - 1]) || (mid % 2 == 0 && nums[mid] == nums[mid + 1])`:
   - We are in the left half; single element is to the right (`low = mid + 1`).
5. Otherwise:
   - We are in the right half; single element is to the left (`high = mid - 1`).

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
    public int singleNonDuplicate(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        if (nums[0] != nums[1]) return nums[0];
        if (nums[n - 1] != nums[n - 2]) return nums[n - 1];

        int low = 1, high = n - 2;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] != nums[mid - 1] && nums[mid] != nums[mid + 1]) return nums[mid];

            if ((mid % 2 == 1 && nums[mid] == nums[mid - 1]) ||
                (mid % 2 == 0 && nums[mid] == nums[mid + 1])) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
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
        System.out.println(sol.singleNonDuplicate(nums));
        sc.close();
    }
}
```
