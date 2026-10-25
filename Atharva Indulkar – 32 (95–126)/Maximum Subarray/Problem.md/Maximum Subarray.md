### Problem: Maximum Subarray
**Difficulty:** Medium | **Tags:** Arrays, Dynamic Programming, Divide and Conquer

**Problem Statement**
Given an integer array `nums`, find the subarray with the largest sum, and return its sum.

A subarray is a contiguous non-empty sequence of elements within an array.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the size of the array.
  * The second line contains `n` space-separated integers, representing the elements of `nums`.

**Constraints**
* 1 ≤ t ≤ 1000
* 1 ≤ n ≤ 10^5
* Sum of n over all test cases ≤ 2 × 10^5
* -10^4 ≤ nums[i] ≤ 10^4

**Output Format**
For each test case, print the maximum subarray sum on a single line.

**Example**
**Input:**
```text
3
9
-2 1 -3 4 -1 2 1 -5 4
1
1
5
5 4 -1 7 8
```
**Output:**
```text
6
1
23
```
**Explanation:**
Test case 1: The subarray `[4, -1, 2, 1]` has the largest sum `6`.
Test case 2: The subarray `[1]` has the largest sum `1`.
Test case 3: The subarray `[5, 4, -1, 7, 8]` has the largest sum `23`.
