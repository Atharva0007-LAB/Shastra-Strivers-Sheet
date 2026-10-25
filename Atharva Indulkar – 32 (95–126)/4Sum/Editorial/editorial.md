# Editorial: 4Sum


## Difficulty

Medium


## Prerequisites
- Arrays
- Two Pointers
- Sorting


## Problem Statement

Given an array `nums` of `n` integers and an integer `target`, find all unique quadruplets `[a, b, c, d]` summing to `target`.


## Explanation & Approach

### Two Nested Loops + Two Pointers

1. **Sort the array** in ascending order.
2. **Loop `i` from 0 to `n - 4`**:
   - Skip duplicate `nums[i] == nums[i - 1]`.
3. **Loop `j` from `i + 1` to `n - 3`**:
   - Skip duplicate `nums[j] == nums[j - 1]` (when `j > i + 1`).
4. **Use Two Pointers `left = j + 1` and `right = n - 1`**:
   - Compute `sum = (long)nums[i] + nums[j] + nums[left] + nums[right]`. Note the use of `long` to avoid integer overflow when numbers are around $\pm 10^9$.
   - If `sum == target`:
     - Record the quadruplet.
     - Advance `left` skipping duplicate values.
     - Decrement `right` skipping duplicate values.
   - If `sum < target`: advance `left++`.
   - If `sum > target`: decrement `right--`.


## Java Solution

```java
import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = scanner.nextInt();
            long target = scanner.nextLong();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) nums[i] = scanner.nextInt();

            Arrays.sort(nums);
            boolean found = false;

            for (int i = 0; i < n - 3; i++) {
                if (i > 0 && nums[i] == nums[i - 1]) continue;

                for (int j = i + 1; j < n - 2; j++) {
                    if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                    int left = j + 1, right = n - 1;
                    while (left < right) {
                        long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                        if (sum == target) {
                            found = true;
                            sb.append(nums[i]).append(" ").append(nums[j]).append(" ")
                              .append(nums[left]).append(" ").append(nums[right]).append("\n");
                            while (left < right && nums[left] == nums[left + 1]) left++;
                            while (left < right && nums[right] == nums[right - 1]) right--;
                            left++;
                            right--;
                        } else if (sum < target) {
                            left++;
                        } else {
                            right--;
                        }
                    }
                }
            }

            if (!found) {
                sb.append("-1\n");
            }
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

- Sorting takes $O(n \log n)$.
- Outer loops take $O(n^2)$ and two-pointer search takes $O(n)$, giving $O(n^3)$ overall.

### Total Time Complexity:
$O(\sum n^3)$ over all test cases.

### Space Complexity:
$O(1)$ auxiliary space.
