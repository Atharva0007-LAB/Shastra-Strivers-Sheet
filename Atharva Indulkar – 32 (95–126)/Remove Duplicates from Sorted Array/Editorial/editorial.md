# Editorial: Remove Duplicates from Sorted Array


## Difficulty

Easy


## Prerequisites
- Arrays
- Two Pointers


## Problem Statement

Given a sorted array of n integers, keep only one copy of each value, preserving order. Print the count k of unique values and the first k elements of the result.


## Explanation & Approach

### Step 1: Equal Values Sit Next to Each Other

Because the array is sorted, all copies of a value are adjacent. So a value is new exactly when it differs from the last unique value we kept.

### Step 2: Keep a Write Pointer

Keep a pointer `k` that marks how many unique values have been kept. The first element is always unique, so start with `k = 1`. Then scan `i` from 1 to n-1. If `a[i] != a[k-1]`, this is a new value, so write it at `a[k]` and increase `k`. Otherwise it is a duplicate and is skipped.

### Step 3: Read Off the Answer

After the scan, `k` is the number of unique elements and `a[0..k-1]` holds them in sorted order. The rewrite happens in place, with no extra array.


## Example

### Input:

```text
2
3
1 1 2
10
0 0 1 1 1 2 2 3 3 4
```

### Trace:

Test case 1 (`1 1 2`), k starts at 1:
- i = 1: a[1] = 1 equals a[0] = 1, skip
- i = 2: a[2] = 2 differs from a[0] = 1, so a[1] = 2 and k = 2

Test case 2 (`0 0 1 1 1 2 2 3 3 4`), k starts at 1:
- i = 1: 0 equals 0, skip
- i = 2: 1 is new, a[1] = 1, k = 2
- i = 3, 4: 1 equals 1, skip
- i = 5: 2 is new, a[2] = 2, k = 3
- i = 6: 2 equals 2, skip
- i = 7: 3 is new, a[3] = 3, k = 4
- i = 8: 3 equals 3, skip
- i = 9: 4 is new, a[4] = 4, k = 5

Output:
```text
2
1 2
5
0 1 2 3 4
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

            int k = 1; // a[0..k-1] holds the unique elements found so far
            for (int i = 1; i < n; i++) {
                if (a[i] != a[k - 1]) {
                    a[k] = a[i];
                    k++;
                }
            }

            sb.append(k).append("\n");
            for (int i = 0; i < k; i++) {
                sb.append(a[i]);
                sb.append(i != k - 1 ? " " : "\n");
            }
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Let $n$ be the size of the array in a test case. The array is scanned once, doing $O(1)$ work per element.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(1)$ extra space beyond the input array.
