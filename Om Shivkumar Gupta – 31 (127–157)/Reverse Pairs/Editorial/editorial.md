# 152. Reverse Pairs - Editorial

## Problem Statement
Given an integer array `nums`, return the number of reverse pairs in the array.

A reverse pair is defined as a pair `(i, j)` such that:
- `0 <= i < j < nums.length` and
- `nums[i] > 2 * nums[j]`

---

## Sample Example

### Sample Input
```text
5
1 3 2 3 1
```

### Sample Output
```text
2
```

### Explanation
* The reverse pairs are:
  - (1, 4): nums[1] = 3, nums[4] = 1, and 3 > 2 * 1
  - (3, 4): nums[3] = 3, nums[4] = 1, and 3 > 2 * 1
* Total reverse pairs = 2.

---

## Intuition
A brute-force solution checks all O(N^2) pairs, which triggers TLE for N = 50,000.

We can solve this problem in O(N log N) using Divide and Conquer via Merge Sort:
Suppose we split the array into two sorted halves: `nums[low...mid]` and `nums[mid+1...high]`.
Before merging, because both halves are already sorted, we can count all valid pairs across the two halves using a Two-Pointer technique in linear O(N) time:
For each element in the left half, advance the pointer in the right half as long as `nums[i] > 2 * nums[right]`.

## Approach
1. In `mergeSort(low, high)`:
   - Compute `mid = (low + high) / 2`.
   - Recursively count and sort left and right halves.
   - Run `countPairs(low, mid, high)`:
     - Maintain pointer `right = mid + 1`.
     - For each `i` from `low` to `mid`, advance `right` while `nums[i] > 2L * nums[right]`.
     - Accumulate `(right - (mid + 1))` into `count`.
   - Standard merge step to combine the two sorted halves.
2. Return total count.

---

## Complexity Analysis
* **Time Complexity:** O(N log N) since the array is halved at each recursion level and counting + merging takes O(N) per level.
* **Space Complexity:** O(N) for the temporary merge buffer array.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    private int countPairs(int[] nums, int low, int mid, int high) {
        int right = mid + 1;
        int count = 0;
        for (int i = low; i <= mid; i++) {
            while (right <= high && (long) nums[i] > 2L * nums[right]) {
                right++;
            }
            count += (right - (mid + 1));
        }
        return count;
    }

    private void merge(int[] nums, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int left = low, right = mid + 1, k = 0;

        while (left <= mid && right <= high) {
            if (nums[left] <= nums[right]) {
                temp[k++] = nums[left++];
            } else {
                temp[k++] = nums[right++];
            }
        }

        while (left <= mid) temp[k++] = nums[left++];
        while (right <= high) temp[k++] = nums[right++];

        for (int i = 0; i < temp.length; i++) {
            nums[low + i] = temp[i];
        }
    }

    private int mergeSort(int[] nums, int low, int high) {
        int count = 0;
        if (low >= high) return count;
        int mid = low + (high - low) / 2;
        count += mergeSort(nums, low, mid);
        count += mergeSort(nums, mid + 1, high);
        count += countPairs(nums, low, mid, high);
        merge(nums, low, mid, high);
        return count;
    }

    public int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) nums[i] = sc.nextInt();

        Solution sol = new Solution();
        int ans = sol.reversePairs(nums);
        System.out.println(ans);
        sc.close();
    }
}
```
