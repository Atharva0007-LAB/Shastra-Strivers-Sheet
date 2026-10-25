# Editorial: Intersection of Two Sorted Arrays


## Difficulty

Easy


## Prerequisites
- Arrays
- Two Pointers


## Problem Statement

Given two sorted arrays that may contain duplicates, print the distinct elements that are present in both arrays, in sorted order. Print -1 if there are none.


## Explanation & Approach

### Step 1: Walk Both Arrays Together

Both arrays are sorted, so keep one pointer `i` on `a` and one pointer `j` on `b`, both starting at 0. Compare `a[i]` and `b[j]`.
- If `a[i] < b[j]`, then `a[i]` cannot appear anywhere in `b` from `j` onward, so move `i` forward.
- If `a[i] > b[j]`, then `b[j]` cannot appear in `a` from `i` onward, so move `j` forward.
- If they are equal, the value is in both arrays.

### Step 2: Avoid Printing a Value Twice

When the values are equal, add the value to the answer only if it differs from the last value that was added. Since both arrays are sorted, the common values are found in increasing order, so comparing with the last added value is enough to skip repeats. Then move both pointers forward.

### Step 3: Handle the Empty Case

Stop when either pointer reaches the end of its array. If nothing was added, print -1, otherwise print the collected values.


## Example

### Input:

```text
2
6 4
1 1 2 2 2 4
2 2 4 4
4 4
4 4 4 4
3 3 3 3
```

### Trace:

Test case 1 (`a = 1 1 2 2 2 4`, `b = 2 2 4 4`):
- a[0] = 1 < b[0] = 2, move i
- a[1] = 1 < 2, move i
- a[2] = 2 equals b[0] = 2, add 2, move both (i = 3, j = 1)
- a[3] = 2 equals b[1] = 2, already added, move both (i = 4, j = 2)
- a[4] = 2 < b[2] = 4, move i
- a[5] = 4 equals b[2] = 4, add 4, move both (i = 6, so stop)

Test case 2 (`a = 4 4 4 4`, `b = 3 3 3 3`):
- a[0] = 4 > b[0] = 3, move j, and so on until j reaches the end. Nothing is added.

Output:
```text
2 4
-1
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
            int m = scanner.nextInt();
            int[] a = new int[n];
            int[] b = new int[m];
            for (int i = 0; i < n; i++) a[i] = scanner.nextInt();
            for (int j = 0; j < m; j++) b[j] = scanner.nextInt();

            StringBuilder line = new StringBuilder();
            int count = 0;
            int last = 0; // values are >= 1, so 0 means "nothing added yet"
            int i = 0, j = 0;
            while (i < n && j < m) {
                if (a[i] < b[j]) {
                    i++;
                } else if (a[i] > b[j]) {
                    j++;
                } else {
                    if (a[i] != last) {
                        if (count > 0) line.append(" ");
                        line.append(a[i]);
                        last = a[i];
                        count++;
                    }
                    i++;
                    j++;
                }
            }

            if (count == 0) sb.append("-1\n");
            else sb.append(line).append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Let $n$ and $m$ be the sizes of the two arrays in a test case. Each step moves at least one pointer forward, so there are at most $n + m$ steps.

### Total Time Complexity:
$O(\sum (n + m))$ over all test cases.

### Space Complexity:
$O(1)$ extra space besides the input arrays and the output.
