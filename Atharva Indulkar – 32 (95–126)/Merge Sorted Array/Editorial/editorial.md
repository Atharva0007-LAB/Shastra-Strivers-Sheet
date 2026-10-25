# Editorial: Merge Sorted Array


## Difficulty

Easy


## Prerequisites
- Arrays
- Two Pointers


## Problem Statement

Merge two sorted arrays `nums1` of size `m` and `nums2` of size `n` into a single sorted array of size $m + n$.


## Explanation & Approach

### Two Pointers Merge ($O(m + n)$ Time)

Since both arrays are already sorted:
1. Initialize pointer `i = 0` for `nums1` and `j = 0` for `nums2`.
2. Compare `nums1[i]` and `nums2[j]`:
   - If `nums1[i] <= nums2[j]`, append `nums1[i]` and increment `i++`.
   - Otherwise, append `nums2[j]` and increment `j++`.
3. After the loop, append any remaining elements from `nums1` or `nums2`.

This achieves the linear time complexity of $O(m + n)$ in a single forward pass.


## Java Solution

```java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int m = scanner.nextInt();
            int n = scanner.nextInt();
            int[] nums1 = new int[m];
            int[] nums2 = new int[n];
            for (int i = 0; i < m; i++) nums1[i] = scanner.nextInt();
            for (int j = 0; j < n; j++) nums2[j] = scanner.nextInt();

            int i = 0, j = 0;
            boolean first = true;
            while (i < m && j < n) {
                if (!first) sb.append(" ");
                if (nums1[i] <= nums2[j]) {
                    sb.append(nums1[i++]);
                } else {
                    sb.append(nums2[j++]);
                }
                first = false;
            }

            while (i < m) {
                if (!first) sb.append(" ");
                sb.append(nums1[i++]);
                first = false;
            }

            while (j < n) {
                if (!first) sb.append(" ");
                sb.append(nums2[j++]);
                first = false;
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Each element of `nums1` and `nums2` is inspected and appended once.

### Total Time Complexity:
$O(\sum (m + n))$ over all test cases.

### Space Complexity:
$O(1)$ auxiliary space beyond the input arrays and output buffer.
