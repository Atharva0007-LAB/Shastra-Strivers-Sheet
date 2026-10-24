### Problem 152: Median of Two Sorted Arrays
**Difficulty:** Hard | **Tags:** Array, Binary Search, Divide and Conquer

**Problem Statement:**
Given two sorted arrays `nums1` and `nums2` of size `m` and `n` respectively, return the median of the two sorted arrays.

The overall run time complexity should be `O(log (m+n))`.

### Input Format
* The first line contains two integers `m` and `n`.
* The second line contains `m` space-separated integers representing `nums1` (omitted if m=0).
* The third line contains `n` space-separated integers representing `nums2` (omitted if n=0).

### Output Format
* Print a floating-point number representing the median formatted to 5 decimal places.

### Constraints
* 0 <= m <= 10^5
* 0 <= n <= 10^5
* 1 <= m + n <= 2 * 10^5
* -10^6 <= nums1[i], nums2[i] <= 10^6

### Example
#### Input:
```text
2 2
1 3
2 4
```

#### Output:
```text
2.50000
```

#### Explanation:
Merged array is [1, 2, 3, 4] and median is (2 + 3) / 2 = 2.5.
