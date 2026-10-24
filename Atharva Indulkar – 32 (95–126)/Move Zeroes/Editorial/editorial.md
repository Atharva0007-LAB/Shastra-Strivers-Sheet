# Editorial: Move Zeroes


## Difficulty

Easy


## Prerequisites
- Arrays
- Two Pointers


## Problem Statement

Given an array of n integers, move all 0's to the end while keeping the relative order of the non-zero elements.


## Explanation & Approach

### Step 1: Track Where the Next Non-Zero Belongs

Keep a pointer `pos` that marks the position where the next non-zero element should be placed. Everything before `pos` is already the non-zero elements seen so far, in their original order.

### Step 2: Scan and Swap

Walk through the array with index `i`. Whenever `a[i]` is non-zero, swap it with `a[pos]` and move `pos` forward by one. If `i` and `pos` are the same position, the swap does nothing. If they differ, `a[pos]` is a zero, so the swap pushes that zero further right.

### Step 3: Zeros End Up at the Back

After one pass, all non-zero elements sit at the front in their original order, and every position from `pos` onward holds a zero. This is done in place, with no extra array.


## Example

### Input:

```text
2
5
0 1 0 3 12
1
0
```

### Trace:

Test case 1 (`0 1 0 3 12`), pos starts at 0:
- i = 0: value 0, skip. Array: `0 1 0 3 12`, pos = 0
- i = 1: value 1, swap with index 0. Array: `1 0 0 3 12`, pos = 1
- i = 2: value 0, skip. Array: `1 0 0 3 12`, pos = 1
- i = 3: value 3, swap with index 1. Array: `1 3 0 0 12`, pos = 2
- i = 4: value 12, swap with index 2. Array: `1 3 12 0 0`, pos = 3

Test case 2 (`0`): the only element is 0, so nothing moves.

Output:
```text
1 3 12 0 0
0
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
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = scanner.nextInt();

            int pos = 0; // where the next non-zero element should go
            for (int i = 0; i < n; i++) {
                if (a[i] != 0) {
                    int tmp = a[pos];
                    a[pos] = a[i];
                    a[i] = tmp;
                    pos++;
                }
            }

            for (int i = 0; i < n; i++) {
                sb.append(a[i]);
                sb.append(i != n - 1 ? " " : "\n");
            }
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Let $n$ be the size of the array in a test case. The array is scanned once, and each swap takes $O(1)$.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(1)$ extra space beyond the input array.
