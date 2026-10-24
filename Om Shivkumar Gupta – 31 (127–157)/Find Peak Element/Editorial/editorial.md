# 129. Find Peak Element - Editorial

## Problem Statement
A peak element is an element that is strictly greater than its neighbors.

Given a 0-indexed integer array `nums`, find a peak element, and return its index. If the array contains multiple peaks, return the index to any of the peaks.

You may imagine that `nums[-1] = nums[n] = -infinity`. In other words, an element is always considered to be strictly greater than a neighbor that is outside the array.

You must write an algorithm that runs in `O(log n)` time.

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

### Explanation
* 3 is a peak element because it is strictly greater than its neighbors 2 and 1.
* Its index is 2.

---

## Intuition
A linear scan would find a peak in O(N), but the problem requires O(log N). 

Since `nums[-1] = nums[N] = -`infinity, if we look at the midpoint `mid` and compare it with `mid + 1`:
- If `nums[mid] < nums[mid + 1]`, we are on an ascending slope. Even if numbers keep ascending until the end, `nums[N-1]` will be greater than `nums[N] = -`infinity, so there MUST be at least one peak in the right half.
- If `nums[mid] > nums[mid + 1]`, we are on a descending slope, meaning a peak must exist at `mid` or to its left.
Thus, binary search is applicable!

## Approach
1. Initialize pointers `low = 0` and `high = N - 1`.
2. While `low < high`:
   - Compute `mid = low + (high - low) / 2`.
   - If `nums[mid] < nums[mid + 1]`, set `low = mid + 1`.
   - Else set `high = mid`.
3. When `low == high`, return `low`.

---

## Complexity Analysis
* **Time Complexity:** O(log N) as the search space halves on every comparison.
* **Space Complexity:** O(1) auxiliary space as only pointer variables are stored.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    /**
     * Finds a peak element in O(log N) using Binary Search.
     * 
     * @param nums Integer array with no two adjacent equal elements.
     * @return Index of any peak element.
     */
    public int findPeakElement(int[] nums) {
        int low = 0;
        int high = nums.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;
            // If mid element is smaller than its right neighbor,
            // an upward slope exists to the right, guaranteeing a peak on the right.
            if (nums[mid] < nums[mid + 1]) {
                low = mid + 1;
            } else {
                // Otherwise, a peak exists on the left side (including mid itself).
                high = mid;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }

        Solution sol = new Solution();
        int peakIndex = sol.findPeakElement(nums);
        System.out.println(peakIndex);
        sc.close();
    }
}
```
