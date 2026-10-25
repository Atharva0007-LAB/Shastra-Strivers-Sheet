# Editorial: Pascal's Triangle II


## Difficulty

Easy


## Prerequisites
- Arrays
- Combinatorics ($nCr$)


## Problem Statement

Given an integer `rowIndex`, compute and return only the `rowIndex`-th row of Pascal's triangle using $O(rowIndex)$ extra space.


## Explanation & Approach

### Combinatorial Approach ($O(n)$ Time, $O(1)$ Extra Space)

The elements of the $n$-th row (0-indexed) in Pascal's triangle correspond to:
$$\binom{n}{0}, \binom{n}{1}, \dots, \binom{n}{n}$$

We know that:
- $\binom{n}{0} = 1$
- Each subsequent element can be computed from the previous element:
$$\binom{n}{i} = \binom{n}{i - 1} \times \frac{n - i + 1}{i}$$

Using 64-bit integer arithmetic (`long`) prevents integer overflow during intermediate multiplications.


## Java Solution

```java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int rowIndex = scanner.nextInt();
            long val = 1;
            sb.append(1);
            for (int i = 1; i <= rowIndex; i++) {
                val = val * (rowIndex - i + 1) / i;
                sb.append(" ").append(val);
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Computing each element takes $O(1)$ operations, and there are $rowIndex + 1$ elements in the row.

### Total Time Complexity:
$O(\sum rowIndex)$ over all test cases.

### Space Complexity:
$O(1)$ auxiliary space beyond the output.
