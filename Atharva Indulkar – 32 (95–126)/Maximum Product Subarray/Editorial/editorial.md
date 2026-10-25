# Editorial: Maximum Product Subarray


## Difficulty

Medium


## Prerequisites
- Arrays
- Dynamic Programming


## Problem Statement

Given an integer array `nums`, find the contiguous subarray with the maximum product.


## Explanation & Approach

### Dynamic Programming with Max & Min Tracking

Because multiplying two negative numbers yields a positive number, a minimum product (large negative number) could become the maximum product if the next number is negative!

We maintain two variables at each step:
- `maxProd`: maximum product ending at current index.
- `minProd`: minimum product ending at current index.

For each element `x` in `nums`:
1. If `x < 0`, multiplying by a negative number inverts signs: swap `maxProd` and `minProd`.
2. Update both:
   $$\begin{aligned}
   maxProd &= \max(x, maxProd \times x) \\
   minProd &= \min(x, minProd \times x)
   \end{aligned}$$
3. Update global answer: `ans = max(ans, maxProd)`.


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
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) nums[i] = scanner.nextInt();

            long maxProd = nums[0];
            long minProd = nums[0];
            long ans = nums[0];

            for (int i = 1; i < n; i++) {
                int x = nums[i];
                if (x < 0) {
                    long tmp = maxProd;
                    maxProd = minProd;
                    minProd = tmp;
                }
                maxProd = Math.max(x, maxProd * x);
                minProd = Math.min(x, minProd * x);
                ans = Math.max(ans, maxProd);
            }
            sb.append(ans).append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

A single linear pass through the array.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(1)$ auxiliary space.
