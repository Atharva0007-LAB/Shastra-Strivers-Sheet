### Problem: Longest Subarray with Sum K
**Difficulty:** Medium | **Tags:** Arrays, Hash Table, Prefix Sum

**Problem Statement**
Given an array `arr` containing integers and an integer `k`, find the length of the longest sub-array with sum equal to `k`.

If no such sub-array exists, return `0`.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains two integers `n` and `k`.
  * The second line contains `n` space-separated integers, representing `arr`.

**Constraints**
* 1 ≤ t ≤ 100
* 1 ≤ n ≤ 10^5
* Sum of n over all test cases ≤ 2 × 10^5
* -10^9 ≤ k ≤ 10^9
* -10^9 ≤ arr[i] ≤ 10^9

**Output Format**
For each test case, print the length of the longest subarray with sum `k` on its own line.

**Example**
**Input:**
```text
3
6 15
10 5 2 7 1 9
3 5
-1 2 3
3 1
-1 1 1
```
**Output:**
```text
4
2
3
```
**Explanation:**
Test case 1: The subarray `[5, 2, 7, 1]` has sum 15 and length 4.
Test case 2: The subarray `[2, 3]` has sum 5 and length 2.
Test case 3: The whole array `[-1, 1, 1]` has sum 1 and length 3.
