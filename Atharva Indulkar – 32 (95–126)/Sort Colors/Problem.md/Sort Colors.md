### Problem: Sort Colors
**Difficulty:** Medium | **Tags:** Arrays, Two Pointers, Sorting

**Problem Statement**
Given an array `nums` with `n` objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.

We will use the integers `0`, `1`, and `2` to represent the color red, white, and blue, respectively.

You must solve this problem without using the library's sort function and using only constant extra space.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the size of the array.
  * The second line contains `n` space-separated integers, where each element is `0`, `1`, or `2`.

**Constraints**
* 1 ≤ t ≤ 1000
* 1 ≤ n ≤ 5 × 10^4
* Sum of n over all test cases ≤ 2 × 10^5
* nums[i] is either 0, 1, or 2

**Output Format**
For each test case, print the sorted array as space-separated integers on a single line.

**Example**
**Input:**
```text
2
6
2 0 2 1 1 0
3
2 0 1
```
**Output:**
```text
0 0 1 1 2 2
0 1 2
```
**Explanation:**
Test case 1: Sorting `[2, 0, 2, 1, 1, 0]` in ascending order yields `[0, 0, 1, 1, 2, 2]`.
Test case 2: Sorting `[2, 0, 1]` in ascending order yields `[0, 1, 2]`.
