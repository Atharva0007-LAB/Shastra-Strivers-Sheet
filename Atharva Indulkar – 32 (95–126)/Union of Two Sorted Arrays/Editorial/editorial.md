# Editorial: Union of Two Sorted Arrays


## Difficulty

Easy


## Prerequisites
- Arrays
- Two Pointers


## Problem Statement

Given two sorted arrays that may contain duplicates, print the distinct elements that are present in either of the two arrays, in sorted order.


## Explanation & Approach

### Step 1: Walk Both Arrays Using Two Pointers

Since both arrays are already sorted in ascending order, we can maintain two pointers: `i` for array `a` and `j` for array `b`, both initialized to 0. At each iteration, compare `a[i]` and `b[j]`:
- If `a[i] < b[j]`: The smaller element `a[i]` must appear next in the union. We consider `a[i]` and increment `i`.
- If `a[i] > b[j]`: The smaller element `b[j]` must appear next in the union. We consider `b[j]` and increment `j`.
- If `a[i] == b[j]`: The value appears in both arrays. We take this value and increment both `i` and `j`.

### Step 2: Avoid Printing Duplicates

To ensure only unique elements appear in the union, keep track of the last element added to the result. Before appending a candidate element, check if it equals the last added element:
- If it is equal, skip it.
- If it is different (or if it is the first element being added), append it to the result and update `last`.

### Step 3: Process Remaining Elements

When one pointer reaches the end of its array, the other array might still have elements remaining. We continue scanning through the remaining elements one by one, adding any value that is not equal to `last`.


## Example

### Input:

```text
2
5 5
1 2 3 4 5
1 2 3 6 7
2 4
2 2
1 2 3 4
```

### Trace:

Test case 1 (`a = 1 2 3 4 5`, `b = 1 2 3 6 7`):
- `a[0] == b[0] == 1`: append 1, `last = 1`, i = 1, j = 1
- `a[1] == b[1] == 2`: append 2, `last = 2`, i = 2, j = 2
- `a[2] == b[2] == 3`: append 3, `last = 3`, i = 3, j = 3
- `a[3] = 4 < b[3] = 6`: append 4, `last = 4`, i = 4, j = 3
- `a[4] = 5 < b[3] = 6`: append 5, `last = 5`, i = 5 (a exhausted), j = 3
- Drain `b`:
  - `b[3] = 6`: append 6, `last = 6`, j = 4
  - `b[4] = 7`: append 7, `last = 7`, j = 5 (b exhausted)

Test case 2 (`a = 2 2`, `b = 1 2 3 4`):
- `b[0] = 1 < a[0] = 2`: append 1, `last = 1`, j = 1
- `a[0] == b[1] == 2`: append 2, `last = 2`, i = 1, j = 2
- `a[1] = 2 < b[2] = 3`: `a[1] == last (2)`, skip duplicate, i = 2 (a exhausted)
- Drain `b`:
  - `b[2] = 3`: append 3, `last = 3`, j = 3
  - `b[3] = 4`: append 4, `last = 4`, j = 4

Output:
```text
1 2 3 4 5 6 7
1 2 3 4
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
            int i = 0, j = 0;
            boolean hasLast = false;
            int last = 0;

            while (i < n && j < m) {
                int val;
                if (a[i] < b[j]) {
                    val = a[i++];
                } else if (a[i] > b[j]) {
                    val = b[j++];
                } else {
                    val = a[i];
                    i++;
                    j++;
                }
                if (!hasLast || val != last) {
                    if (hasLast) line.append(" ");
                    line.append(val);
                    last = val;
                    hasLast = true;
                }
            }

            while (i < n) {
                int val = a[i++];
                if (!hasLast || val != last) {
                    if (hasLast) line.append(" ");
                    line.append(val);
                    last = val;
                    hasLast = true;
                }
            }

            while (j < m) {
                int val = b[j++];
                if (!hasLast || val != last) {
                    if (hasLast) line.append(" ");
                    line.append(val);
                    last = val;
                    hasLast = true;
                }
            }

            sb.append(line).append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Let $n$ and $m$ be the sizes of the two arrays in a test case. Each step advances at least one pointer, resulting in at most $n + m$ steps.

### Total Time Complexity:
$O(\sum (n + m))$ over all test cases.

### Space Complexity:
$O(1)$ extra space beyond the input arrays and the output buffer.
