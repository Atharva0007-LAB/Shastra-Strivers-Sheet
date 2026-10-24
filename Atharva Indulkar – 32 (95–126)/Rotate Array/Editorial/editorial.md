# Editorial: Rotate Array


## Difficulty

Medium


## Prerequisites
- Arrays
- Two Pointers


## Problem Statement

Given an array of n integers, rotate it to the right by k steps, where k is non-negative.


## Explanation & Approach

### Step 1: Reduce k

Rotating by n steps brings the array back to its original form, so only `k % n` matters. Set `k = k % n`. If k becomes 0, the array is unchanged.

### Step 2: Reverse the Whole Array

Reversing the entire array moves the last k elements to the front, but in reversed order. The first n-k elements end up at the back, also reversed.

### Step 3: Reverse the Two Parts Separately

Reverse the first k elements, then the remaining n-k elements. Each part returns to its original internal order, giving the right-rotated array. This works in place with no extra array.


## Example

### Input:

```text
2
7 3
1 2 3 4 5 6 7
4 2
-1 -100 3 99
```

### Trace:

Test case 1 (k = 3):
- Reverse all: `7 6 5 4 3 2 1`
- Reverse first 3: `5 6 7 4 3 2 1`
- Reverse last 4: `5 6 7 1 2 3 4`

Test case 2 (k = 2):
- Reverse all: `99 3 -100 -1`
- Reverse first 2: `3 99 -100 -1`
- Reverse last 2: `3 99 -1 -100`

Output:
```text
5 6 7 1 2 3 4
3 99 -1 -100
```


## Java Solution

```java
import java.util.Scanner;

public class Solution {
    static void reverse(int[] a, int l, int r) {
        while (l < r) {
            int tmp = a[l];
            a[l] = a[r];
            a[r] = tmp;
            l++;
            r--;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = scanner.nextInt();
            int k = scanner.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = scanner.nextInt();

            k = k % n;
            reverse(a, 0, n - 1);
            reverse(a, 0, k - 1);
            reverse(a, k, n - 1);

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

Let $n$ be the size of the array in a test case. Each element is moved a constant number of times by the three reversals.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(1)$ extra space beyond the input array.
