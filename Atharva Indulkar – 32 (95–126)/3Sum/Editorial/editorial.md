# Editorial: 3Sum


## Difficulty

Medium


## Prerequisites
- Arrays
- Two Pointers
- Sorting


## Problem Statement

Given an integer array `nums`, find all unique triplets `[nums[i], nums[j], nums[k]]` that sum to 0.


## Explanation & Approach

### Sorting + Two Pointers

1. **Sort the array** in non-decreasing order. Sorting groups duplicate elements together and allows using the two-pointer technique.
2. **Iterate with the first pointer `i`** from `0` to `n - 3`:
   - If `i > 0` and `nums[i] == nums[i - 1]`, skip `nums[i]` to avoid duplicate triplets.
   - If `nums[i] > 0`, break early because all subsequent elements are $\ge nums[i] > 0$, making a sum of 0 impossible.
3. **Use two pointers `left = i + 1` and `right = n - 1`**:
   - Compute `sum = nums[i] + nums[left] + nums[right]`.
   - If `sum == 0`:
     - Record the triplet `[nums[i], nums[left], nums[right]]`.
     - Skip all duplicate elements for `left` and `right`:
       `while (left < right && nums[left] == nums[left + 1]) left++;`
       `while (left < right && nums[right] == nums[right - 1]) right--;`
     - Advance `left++` and decrement `right--`.
   - If `sum < 0`: increment `left++` to increase the sum.
   - If `sum > 0`: decrement `right--` to decrease the sum.


## Example

### Input:

```text
2
6
-1 0 1 2 -1 -4
3
0 1 1
```

### Trace:

Sorted array: `[-4, -1, -1, 0, 1, 2]`
- i = 0 (val = -4): left = 1, right = 5. No pair sums to 4.
- i = 1 (val = -1): left = 2, right = 5.
  - left = 2 (val = -1), right = 5 (val = 2): sum = -1 + (-1) + 2 = 0 -> Triplet `[-1, -1, 2]`.
  - left = 3 (val = 0), right = 4 (val = 1): sum = -1 + 0 + 1 = 0 -> Triplet `[-1, 0, 1]`.
- i = 2: skipped (duplicate -1).
- i = 3 (val = 0): no further triplets.

Output:
```text
-1 -1 2
-1 0 1
-1
```


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
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) nums[i] = scanner.nextInt();

            Arrays.sort(nums);
            boolean found = false;

            for (int i = 0; i < n - 2; i++) {
                if (i > 0 && nums[i] == nums[i - 1]) continue;
                if (nums[i] > 0) break;

                int left = i + 1, right = n - 1;
                while (left < right) {
                    int sum = nums[i] + nums[left] + nums[right];
                    if (sum == 0) {
                        found = true;
                        sb.append(nums[i]).append(" ").append(nums[left]).append(" ").append(nums[right]).append("\n");
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;
                        left++;
                        right--;
                    } else if (sum < 0) {
                        left++;
                    } else {
                        right--;
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

- Sorting the array: $O(n \log n)$.
- Outer loop runs $O(n)$ times, and the two-pointer inner scan takes $O(n)$.
- Overall: $O(n^2)$.

### Total Time Complexity:
$O(\sum n^2)$ over all test cases.

### Space Complexity:
$O(1)$ auxiliary space beyond storing the input and output buffer.
