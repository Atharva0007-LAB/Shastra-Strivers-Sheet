### Problem: Rotate Array
**Difficulty:** Medium | **Tags:** Arrays, Two Pointers

**Problem Statement**
Given an integer array nums of size n, rotate the array to the right by k steps, where k is non-negative.

**Input Format**
* The first line contains an integer t, the number of test cases.
* For each test case:
  * The first line contains two integers n and k.
  * The second line contains n integers, the elements of nums.

**Constraints**
* 1 ≤ t ≤ 1000
* 1 ≤ n ≤ 10^5
* Sum of n over all test cases ≤ 2 × 10^5
* -2^31 ≤ nums[i] ≤ 2^31 - 1
* 0 ≤ k ≤ 10^5

**Output Format**
For each test case, print the rotated array as n space-separated integers on one line.

**Example**
**Input:**
```text
2
7 3
1 2 3 4 5 6 7
4 2
-1 -100 3 99
```
**Output:**
```text
5 6 7 1 2 3 4
3 99 -1 -100
```
**Explanation:**
Test case 1: rotating right by 1 step gives 7 1 2 3 4 5 6, by 2 steps gives 6 7 1 2 3 4 5, and by 3 steps gives 5 6 7 1 2 3 4.

Test case 2: rotating right by 1 step gives 99 -1 -100 3, and by 2 steps gives 3 99 -1 -100.
