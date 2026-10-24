# 143. Find the Smallest Divisor - Editorial

## Problem Statement
Find the smallest integer `divisor` such that the sum of `ceil(nums[i] / divisor)` is `<= threshold`.

---

## Sample Example

### Sample Input
```text
4 6
1 2 5 9
```

### Sample Output
```text
5
```

---

## Intuition & Approach
Notice the monotonic nature of the sum:
- As `divisor` increases, the sum of quotients decreases.
- If a divisor `d` yields `sum <= threshold`, all divisors `> d` will also yield `sum <= threshold`.
This allows us to binary search for the smallest valid divisor:
1. Search range: `low = 1`, `high = max(nums)`.
2. For midpoint `mid`:
   - Compute `sum = sum((num + mid - 1) / mid)`.
   - If `sum <= threshold`, record `ans = mid` and search left (`high = mid - 1`).
   - Otherwise, search right (`low = mid + 1`).
3. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(N * log(max(nums))) - each verification takes O(N) over log(max) binary search iterations.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private boolean isPossible(int[] nums, int divisor, int threshold) {
        long sum = 0;
        for (int num : nums) sum += (num + divisor - 1) / divisor;
        return sum <= threshold;
    }

    public int smallestDivisor(int[] nums, int threshold) {
        int low = 1, high = 0;
        for (int x : nums) high = Math.max(high, x);
        int ans = high;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (isPossible(nums, mid, threshold)) {
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
        int threshold = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.smallestDivisor(nums, threshold));
        sc.close();
    }
}
```
