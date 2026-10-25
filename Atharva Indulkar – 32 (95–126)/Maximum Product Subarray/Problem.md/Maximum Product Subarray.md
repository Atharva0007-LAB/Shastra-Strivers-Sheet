### Problem: Maximum Product Subarray
**Difficulty:** Medium | **Tags:** Arrays, Dynamic Programming

**Problem Statement**
Given an integer array `nums`, find a subarray that has the largest product, and return the product.

A subarray is a contiguous non-empty sequence of elements within an array.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the size of `nums`.
  * The second line contains `n` space-separated integers, representing `nums`.

**Constraints**
* 1 ≤ t ≤ 100
* 1 ≤ n ≤ 2 × 10^4
* Sum of n over all test cases ≤ 10^5
* -10 ≤ nums[i] ≤ 10
* The product of any prefix or suffix of `nums` is guaranteed to fit in a 32-bit integer.

**Output Format**
For each test case, print the maximum product on its own line.

**Example**
**Input:**
```text
2
4
2 3 -2 4
3
-2 0 -1
```
**Output:**
```text
6
0
```
**Explanation:**
Test case 1: `[2, 3]` has the largest product `6`.
Test case 2: The result cannot be 2, because `[-2, -1]` is not a contiguous subarray. Max product is 0 (from `[0]`).
