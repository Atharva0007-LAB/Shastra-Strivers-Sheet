### Problem: Move Zeroes
**Difficulty:** Easy | **Tags:** Arrays, Two Pointers

**Problem Statement**
Given an integer array nums of size n, move all 0's to the end of it while maintaining the relative order of the non-zero elements. Print the resulting array.

**Input Format**
* The first line contains an integer t, the number of test cases.
* For each test case:
  * The first line contains an integer n, the size of the array.
  * The second line contains n integers, the elements of nums.

**Constraints**
* 1 ≤ t ≤ 1000
* 1 ≤ n ≤ 10^4
* Sum of n over all test cases ≤ 2 × 10^5
* -2^31 ≤ nums[i] ≤ 2^31 - 1

**Output Format**
For each test case, print the resulting array as n space-separated integers on one line.

**Example**
**Input:**
```text
2
5
0 1 0 3 12
1
0
```
**Output:**
```text
1 3 12 0 0
0
```
**Explanation:**
Test case 1: the non-zero elements 1, 3, 12 keep their order and the two zeros move to the end.

Test case 2: the array has a single element 0, so it stays the same.
