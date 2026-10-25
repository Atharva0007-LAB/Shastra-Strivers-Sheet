# Editorial: Missing And Repeating


## Difficulty

Medium


## Prerequisites
- Arrays
- Mathematics (Sum and Sum of Squares)


## Problem Statement

Given an array of size $n$ with numbers from $1$ to $n$, find the repeating number $B$ and the missing number $A$.


## Explanation & Approach

### Mathematical Formulation ($O(n)$ Time, $O(1)$ Space)

Let $B$ be the repeating number and $A$ be the missing number.
Let:
- $S = \sum_{i=0}^{n-1} arr[i]$
- $S_n = \frac{n(n + 1)}{2}$
- $S_2 = \sum_{i=0}^{n-1} arr[i]^2$
- $S_{2n} = \frac{n(n + 1)(2n + 1)}{6}$

We can set up two equations:
1. $S - S_n = B - A$  (let this be $diff_1$)
2. $S_2 - S_{2n} = B^2 - A^2 = (B - A)(B + A)$ (let this be $diff_2$)

Dividing equation 2 by equation 1:
$$B + A = \frac{diff_2}{diff_1}$$

Now we have:
- $B - A = diff_1$
- $B + A = sum$

Adding both gives:
$$2B = diff_1 + sum \implies B = \frac{diff_1 + sum}{2}$$
And:
$$A = sum - B$$

This calculates both values in a single pass using $O(1)$ auxiliary space and standard 64-bit integer arithmetic (`long`).


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
            long s = 0;
            long s2 = 0;

            for (int i = 0; i < n; i++) {
                long x = scanner.nextLong();
                s += x;
                s2 += x * x;
            }

            long sn = (long) n * (n + 1) / 2;
            long s2n = (long) n * (n + 1) * (2L * n + 1) / 6;

            long diff1 = s - sn; // B - A
            long diff2 = s2 - s2n; // B^2 - A^2
            long sum = diff2 / diff1; // B + A

            long b = (diff1 + sum) / 2;
            long a = sum - b;

            sb.append(b).append(" ").append(a).append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Computing the sums requires a single linear scan of $n$ elements.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(1)$ auxiliary space.
