# Editorial: Missing Number


## Difficulty

Easy


## Prerequisites
- Arrays
- Math


## Problem Statement

Given n distinct numbers taken from the range [0, n], find the one number from the range that is missing.


## Explanation & Approach

### Step 1: The Full Range Has a Known Sum

If no number were missing, the array would hold every value 0, 1, 2, ..., n. Their sum is `n * (n + 1) / 2`.

### Step 2: Compare with the Actual Sum

Add up the elements actually present in the array. Since exactly one number from the range is absent and all the others appear exactly once, the actual sum is smaller than the full sum by exactly the missing number.

### Step 3: Subtract

The answer is `n * (n + 1) / 2 - (sum of the array)`. With n up to 10^4 the full sum is at most about 5 × 10^7, which fits in a normal `int`.


## Example

### Input:

```text
3
3
3 0 1
2
0 1
9
9 6 4 2 3 5 7 0 1
```

### Trace:

- Test case 1: n = 3, full sum = 3 * 4 / 2 = 6, array sum = 3 + 0 + 1 = 4, missing = 6 - 4 = 2
- Test case 2: n = 2, full sum = 2 * 3 / 2 = 3, array sum = 0 + 1 = 1, missing = 3 - 1 = 2
- Test case 3: n = 9, full sum = 9 * 10 / 2 = 45, array sum = 9 + 6 + 4 + 2 + 3 + 5 + 7 + 0 + 1 = 37, missing = 45 - 37 = 8

Output:
```text
2
2
8
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
            int sum = 0;
            for (int i = 0; i < n; i++) sum += scanner.nextInt();

            int expected = n * (n + 1) / 2; // sum of 0, 1, ..., n
            sb.append(expected - sum).append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Let $n$ be the size of the array in a test case. The array is read and summed once.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(1)$ extra space, since the elements are added up as they are read.
