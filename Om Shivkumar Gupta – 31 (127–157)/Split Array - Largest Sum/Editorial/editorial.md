# 155. Split Array - Largest Sum - Editorial

## Problem Statement
Split `nums` into `k` contiguous subarrays such that the largest sum among them is minimized.

---

## Sample Example

### Sample Input
```text
5 2
7 2 5 10 8
```

### Sample Output
```text
18
```

---

## Intuition & Approach
This is mathematically equivalent to the Painter's Partition / Book Allocation problem:
- Lower bound = `max(nums)`.
- Upper bound = `sum(nums)`.
- Monotonicity: larger max sum allows splitting into fewer subarrays.
Binary search:
1. Range: `low = max(nums)`, `high = sum(nums)`.
2. For `mid`, greedily pack elements into the current subarray until adding another exceeds `mid`.
3. If `subarraysNeeded <= k`, record `ans = mid` and search left (`high = mid - 1`).
4. Else, search right (`low = mid + 1`).
5. Return `ans`.

---

## Complexity Analysis
* **Time Complexity:** O(N * log(sum(nums))) - linear evaluation per step.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.*;

public class Solution {
    private int countSubarrays(int[] nums, long maxSum) {
        int count = 1;
        long current = 0;
        for (int x : nums) {
            if (current + x > maxSum) { count++; current = x; }
            else current += x;
        }
        return count;
    }

    public int splitArray(int[] nums, int k) {
        long low = 0, high = 0;
        for (int x : nums) { low = Math.max(low, x); high += x; }
        long ans = high;

        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (countSubarrays(nums, mid) <= k) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return (int) ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        System.out.println(sol.splitArray(nums, k));
        sc.close();
    }
}
```
