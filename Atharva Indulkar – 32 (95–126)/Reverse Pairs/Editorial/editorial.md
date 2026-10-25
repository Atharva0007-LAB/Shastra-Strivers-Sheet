# Editorial: Reverse Pairs


## Difficulty

Hard


## Prerequisites
- Arrays
- Modified Merge Sort (Divide and Conquer)


## Problem Statement

Given an integer array `nums`, count pairs $(i, j)$ such that $i < j$ and $nums[i] > 2 \times nums[j]$.


## Explanation & Approach

### Enhanced Merge Sort ($O(n \log n)$ Time)

A brute-force check requires $O(n^2)$ time. We can achieve $O(n \log n)$ by augmenting **Merge Sort**:

1. **Divide**: Split `nums[left ... right]` into two halves: `[left, mid]` and `[mid + 1, right]`.
2. **Conquer**: Recursively count reverse pairs in both halves.
3. **Count Pairs Across Halves**:
   Because both halves are already sorted in ascending order:
   For each pointer `i` in the left half, advance pointer `j` in the right half as long as:
   $$(long) nums[i] > 2 \times (long) nums[j]$$
   Since `nums[i]` is increasing as `i` advances, `j` only moves forward (it does not reset!).
   The number of valid pairs for `nums[i]` is `j - (mid + 1)`.
4. **Standard Merge**: Merge the two sorted halves into a temporary array.

Note: Cast to `long` before multiplying `2 * nums[j]` to avoid 32-bit signed integer overflow.


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

            int[] temp = new int[n];
            long count = mergeSortAndCount(nums, temp, 0, n - 1);
            sb.append(count).append("\n");
        }
        System.out.print(sb);
    }

    static long mergeSortAndCount(int[] nums, int[] temp, int left, int right) {
        if (left >= right) return 0;
        int mid = left + (right - left) / 2;
        long count = mergeSortAndCount(nums, temp, left, mid)
                   + mergeSortAndCount(nums, temp, mid + 1, right);

        int j = mid + 1;
        for (int i = left; i <= mid; i++) {
            while (j <= right && (long) nums[i] > 2L * nums[j]) {
                j++;
            }
            count += (j - (mid + 1));
        }

        int i = left, k = left;
        j = mid + 1;
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) temp[k++] = nums[i++];
            else temp[k++] = nums[j++];
        }
        while (i <= mid) temp[k++] = nums[i++];
        while (j <= right) temp[k++] = nums[j++];
        for (i = left; i <= right; i++) nums[i] = temp[i];

        return count;
    }
}
```


## Time Complexity

The recursion tree has $\log n$ levels, and each level performs $O(n)$ work for counting and merging.

### Total Time Complexity:
$O(\sum n \log n)$ over all test cases.

### Space Complexity:
$O(n)$ temporary array for merging.
