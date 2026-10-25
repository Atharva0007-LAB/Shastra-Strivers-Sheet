### Problem: Largest Subarray with 0 Sum
**Difficulty:** Medium | **Tags:** Arrays, Hash Table, Prefix Sum

**Problem Statement**
Given an array `arr` of integers, find the length of the largest subarray with sum equal to `0`.

If no such subarray exists, return `0`.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the size of `arr`.
  * The second line contains `n` space-separated integers, representing `arr`.

**Constraints**
* 1 ≤ t ≤ 100
* 1 ≤ n ≤ 10^5
* Sum of n over all test cases ≤ 2 × 10^5
* -10^9 ≤ arr[i] ≤ 10^9

**Output Format**
For each test case, print the length of the largest subarray with sum 0 on its own line.

**Example**
**Input:**
```text
3
8
15 -2 2 -8 1 7 10 23
5
2 10 4 8 6
3
1 0 -1
```
**Output:**
```text
5
0
3
```
**Explanation:**
Test case 1: The largest subarray with sum 0 is `[-2, 2, -8, 1, 7]` which has length 5.
Test case 2: No subarray has sum 0, output is 0.
Test case 3: The whole array `[1, 0, -1]` has sum 0 and length 3.
