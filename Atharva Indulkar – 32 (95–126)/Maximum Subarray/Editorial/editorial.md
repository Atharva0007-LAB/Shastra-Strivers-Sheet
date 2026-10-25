# Editorial: Maximum Subarray


## Difficulty

Medium


## Prerequisites
- Arrays
- Kadane's Algorithm


## Problem Statement

Given an integer array `nums`, find the contiguous subarray that has the largest sum and return its sum.


## Explanation & Approach

### Kadane's Algorithm

Kadane's algorithm works by iterating through the array while maintaining two values:
1. `maxSum`: The maximum subarray sum found so far across the entire array.
2. `curSum`: The maximum subarray sum ending at the current position.

For each element `x` in `nums`:
- Add `x` to `curSum`: `curSum += x`.
- Update `maxSum = max(maxSum, curSum)`.
- If `curSum < 0`, carrying this running sum forward would only reduce the sum of future subarrays. Therefore, reset `curSum = 0`.

### Handling All-Negative Arrays

By initializing `maxSum` to the first element (or $-\infty$) and checking `maxSum = max(maxSum, curSum)` before resetting negative `curSum` to 0, Kadane's algorithm correctly handles cases where every number in the array is negative (the answer is simply the maximum single element).


## Example

### Input:

```text
3
9
-2 1 -3 4 -1 2 1 -5 4
1
1
5
5 4 -1 7 8
```

### Trace:

Test case 1 (`[-2, 1, -3, 4, -1, 2, 1, -5, 4]`):
- x = -2: curSum = -2, maxSum = -2, curSum < 0 -> curSum = 0
- x = 1: curSum = 1, maxSum = 1
- x = -3: curSum = -2, maxSum = 1, curSum < 0 -> curSum = 0
- x = 4: curSum = 4, maxSum = 4
- x = -1: curSum = 3, maxSum = 4
- x = 2: curSum = 5, maxSum = 5
- x = 1: curSum = 6, maxSum = 6
- x = -5: curSum = 1, maxSum = 6
- x = 4: curSum = 5, maxSum = 6
Result: `6`

Output:
```text
6
1
23
```


## Java Solution

```java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = scanner.nextInt();
            long maxSum = Long.MIN_VALUE;
            long curSum = 0;

            for (int i = 0; i < n; i++) {
                long x = scanner.nextLong();
                curSum += x;
                if (curSum > maxSum) {
                    maxSum = curSum;
                }
                if (curSum < 0) {
                    curSum = 0;
                }
            }

            sb.append(maxSum).append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Kadane's algorithm visits each element exactly once and performs $O(1)$ operations at each step.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(1)$ extra space.
