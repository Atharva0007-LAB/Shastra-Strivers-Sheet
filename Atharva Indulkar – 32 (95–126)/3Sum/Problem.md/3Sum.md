### Problem: 3Sum
**Difficulty:** Medium | **Tags:** Arrays, Two Pointers, Sorting

**Problem Statement**
Given an integer array `nums`, return all the unique triplets `[nums[i], nums[j], nums[k]]` such that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.

Notice that the solution set must not contain duplicate triplets.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the size of `nums`.
  * The second line contains `n` space-separated integers, representing `nums`.

**Constraints**
* 1 ≤ t ≤ 100
* 3 ≤ n ≤ 3000
* -10^5 ≤ nums[i] ≤ 10^5

**Output Format**
For each test case, print each unique triplet on a new line with elements sorted in ascending order and separated by a space. The triplets themselves must be sorted lexicographically. If no triplet sums to 0, print `-1`.

**Example**
**Input:**
```text
2
6
-1 0 1 2 -1 -4
3
0 1 1
```
**Output:**
```text
-1 -1 2
-1 0 1
-1
```
**Explanation:**
Test case 1: The distinct triplets summing to 0 are `[-1, -1, 2]` and `[-1, 0, 1]`.
Test case 2: The only possible triplet does not sum to 0, so output `-1`.
