# Editorial: Next Permutation


## Difficulty

Medium


## Prerequisites
- Arrays
- Two Pointers


## Problem Statement

Given an array of integers `nums`, rearrange it into the lexicographically next greater permutation. If no greater permutation is possible, rearrange it into ascending order (the lowest possible permutation).


## Explanation & Approach

### Step 1: Find the Breakpoint (Pivot)

Traverse the array from right to left to find the first index `i` such that:
$$nums[i] < nums[i + 1]$$
- If no such index exists, the entire array is sorted in descending order (e.g., `[5, 4, 3, 2, 1]`). This is the last permutation, so reversing the entire array gives the first (lowest) permutation.

### Step 2: Find the Next Greater Element

If a breakpoint `i` was found:
- Scan from the right end of the array to find the first element `nums[j]` ($j > i$) that is strictly greater than `nums[i]`:
  $$nums[j] > nums[i]$$
- Swap `nums[i]` and `nums[j]`.

### Step 3: Reverse the Suffix

Since all elements from index $i + 1$ to $n - 1$ were in descending order, reversing that subarray puts them in ascending order, ensuring the minimal lexicographical increase.


## Example

### Input:

```text
3
3
1 2 3
3
3 2 1
3
1 1 5
```

### Trace:

Test case 1 (`1 2 3`):
- From right, `nums[1] = 2 < nums[2] = 3`, so breakpoint is index 1.
- Scanning from right, `nums[2] = 3 > nums[1] = 2`. Swap them -> `[1, 3, 2]`.
- Reverse suffix after index 1 (single element 2) -> `[1, 3, 2]`.

Test case 2 (`3 2 1`):
- Array is in non-increasing order. Breakpoint not found.
- Reverse entire array -> `[1, 2, 3]`.

Output:
```text
1 3 2
1 2 3
1 5 1
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
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) nums[i] = scanner.nextInt();

            nextPermutation(nums);

            for (int i = 0; i < n; i++) {
                sb.append(nums[i]);
                if (i != n - 1) sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    static void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            int j = n - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }

        reverse(nums, i + 1, n - 1);
    }

    static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start++, end--);
        }
    }
}
```


## Time Complexity

- Finding the pivot: at most $O(n)$ comparisons.
- Finding the successor and swapping: at most $O(n)$ comparisons.
- Reversing suffix: at most $O(n)$ swaps.

### Total Time Complexity:
$O(\sum n)$ over all test cases.

### Space Complexity:
$O(1)$ auxiliary space as permutations are performed in-place.
