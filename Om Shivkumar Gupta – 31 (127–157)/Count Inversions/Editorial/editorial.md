# 131. Count Inversions - Editorial

## Problem Statement
Given an array of integers `nums`, find the inversion count in the array.

Two elements `nums[i]` and `nums[j]` form an inversion if:
- `nums[i] > nums[j]` and
- `i < j`

Inversion count indicates how far (or close) the array is from being sorted. A sorted array has an inversion count of 0, whereas an array sorted in reverse order has the maximum inversion count.

---

## Sample Example

### Sample Input
```text
5
5 3 2 4 1
```

### Sample Output
```text
8
```

### Explanation
* The inversions are:
  (5, 3), (5, 2), (5, 4), (5, 1),
  (3, 2), (3, 1),
  (2, 1),
  (4, 1)
* Total inversion count = 8.

---

## Intuition
A brute-force check of all pairs takes O(N^2), which is too slow for N = 10^5.

We can solve this problem in O(N log N) using a Divide and Conquer approach adapted from Merge Sort:
Suppose we divide the array into left and right halves.
Total inversions = (inversions in left half) + (inversions in right half) + (split inversions where i is in left half and j is in right half).

During the merge step, both halves are sorted. When an element `arr[j]` from the right half is smaller than `arr[i]` from the left half, then EVERY remaining element from index `i` to `mid` in the left half is also strictly greater than `arr[j]`. Hence, exactly `(mid - i + 1)` inversions are contributed!

## Approach
1. Implement recursive `mergeSortAndCount(arr, temp, left, right)`.
2. Divide array at `mid = (left + right) / 2`.
3. Recursively sum counts from left half and right half.
4. In `mergeAndCount`:
   - Maintain pointers `i = left` and `j = mid + 1`.
   - If `arr[i] <= arr[j]`, copy `arr[i]` to `temp` and increment `i`.
   - Else, copy `arr[j]` to `temp`, increment `j`, and add `(mid - i + 1)` to `invCount`.
5. Copy sorted elements back to `arr`.
6. Return total inversion count (stored in a 64-bit integer `long` to prevent overflow).

---

## Complexity Analysis
* **Time Complexity:** O(N log N) due to the standard divide-and-conquer tree of depth log N.
* **Space Complexity:** O(N) auxiliary space for the temporary merge buffer array.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    /**
     * Counts inversions using modified Merge Sort in O(N log N).
     * 
     * @param arr Array of integers.
     * @return Total number of inversions.
     */
    public long countInversions(long[] arr) {
        long[] temp = new long[arr.length];
        return mergeSortAndCount(arr, temp, 0, arr.length - 1);
    }

    private long mergeSortAndCount(long[] arr, long[] temp, int left, int right) {
        long count = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;
            count += mergeSortAndCount(arr, temp, left, mid);
            count += mergeSortAndCount(arr, temp, mid + 1, right);
            count += mergeAndCount(arr, temp, left, mid, right);
        }
        return count;
    }

    private long mergeAndCount(long[] arr, long[] temp, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;
        long invCount = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                // arr[i] > arr[j] implies all elements from arr[i...mid] are greater than arr[j]
                temp[k++] = arr[j++];
                invCount += (mid - i + 1);
            }
        }

        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        while (j <= right) {
            temp[k++] = arr[j++];
        }

        for (i = left; i <= right; i++) {
            arr[i] = temp[i];
        }

        return invCount;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextLong();
        }

        Solution sol = new Solution();
        long ans = sol.countInversions(arr);
        System.out.println(ans);
        sc.close();
    }
}
```
