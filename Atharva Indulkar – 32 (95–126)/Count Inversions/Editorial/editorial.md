# Editorial: Count Inversions


## Difficulty

Medium


## Prerequisites
- Arrays
- Merge Sort (Divide and Conquer)


## Problem Statement

Given an array `arr`, count the number of pairs $(i, j)$ such that $i < j$ and $arr[i] > arr[j]$.


## Explanation & Approach

### Enhanced Merge Sort ($O(n \log n)$)

The standard merge sort divides the array into two halves, sorts them recursively, and merges them. We can count inversions during the merge step:

1. **Divide**: Split the array into left half `[left, mid]` and right half `[mid + 1, right]`.
2. **Conquer**: Recursively count inversions in both halves.
3. **Combine (Merge)**:
   - When merging two sorted halves, if `arr[i] <= arr[j]`, take `arr[i]`.
   - If `arr[i] > arr[j]`:
     - Since the left subarray is sorted, every element from index `i` to `mid` is strictly greater than `arr[j]`!
     - Therefore, this single comparison accounts for `mid - i + 1` inversions!
     - Add `mid - i + 1` to the inversion count and advance `j`.

Use `long` for the inversion count, as it can be as large as $\frac{n(n - 1)}{2} \approx 5 \times 10^9$ for $n = 10^5$.


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
            long[] arr = new long[n];
            for (int i = 0; i < n; i++) arr[i] = scanner.nextLong();

            long[] temp = new long[n];
            long count = mergeSortAndCount(arr, temp, 0, n - 1);
            sb.append(count).append("\n");
        }
        System.out.print(sb);
    }

    static long mergeSortAndCount(long[] arr, long[] temp, int left, int right) {
        long count = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;
            count += mergeSortAndCount(arr, temp, left, mid);
            count += mergeSortAndCount(arr, temp, mid + 1, right);
            count += mergeAndCount(arr, temp, left, mid, right);
        }
        return count;
    }

    static long mergeAndCount(long[] arr, long[] temp, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;
        long count = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
                count += (mid - i + 1);
            }
        }

        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];
        for (i = left; i <= right; i++) arr[i] = temp[i];

        return count;
    }
}
```


## Time Complexity

Merge sort splits the array into $\log_2 n$ levels, and each level does $O(n)$ work during merge.

### Total Time Complexity:
$O(\sum n \log n)$ over all test cases.

### Space Complexity:
$O(n)$ auxiliary array for merging.
