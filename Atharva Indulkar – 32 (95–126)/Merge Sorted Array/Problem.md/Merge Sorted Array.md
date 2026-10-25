### Problem: Merge Sorted Array
**Difficulty:** Easy | **Tags:** Arrays, Two Pointers, Sorting

**Problem Statement**
You are given two integer arrays `nums1` and `nums2`, sorted in non-decreasing order, and two integers `m` and `n`, representing the number of elements in `nums1` and `nums2` respectively.

Merge `nums1` and `nums2` into a single array sorted in non-decreasing order.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains two integers `m` and `n`.
  * The second line contains `m` space-separated integers, representing `nums1` (omitted if `m = 0`).
  * The third line contains `n` space-separated integers, representing `nums2` (omitted if `n = 0`).

**Constraints**
* 1 ≤ t ≤ 100
* 0 ≤ m, n ≤ 5 × 10^4
* 1 ≤ m + n ≤ 10^5
* -10^9 ≤ nums1[i], nums2[j] ≤ 10^9
* Both arrays are sorted in non-decreasing order

**Output Format**
For each test case, print the merged sorted array as space-separated integers on a single line.

**Example**
**Input:**
```text
3
3 3
1 2 3
2 5 6
1 0
1

0 1

1
```
**Output:**
```text
1 2 2 3 5 6
1
1
```
**Explanation:**
Test case 1: Merging `[1, 2, 3]` and `[2, 5, 6]` gives `[1, 2, 2, 3, 5, 6]`.
Test case 2: `nums2` is empty, result is `[1]`.
Test case 3: `nums1` is empty, result is `[1]`.
