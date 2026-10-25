### Problem: 4Sum
**Difficulty:** Medium | **Tags:** Arrays, Two Pointers, Sorting

**Problem Statement**
Given an array `nums` of `n` integers and an integer `target`, return all unique quadruplets `[nums[a], nums[b], nums[c], nums[d]]` such that:
- `0 <= a, b, c, d < n`
- `a`, `b`, `c`, and `d` are distinct.
- `nums[a] + nums[b] + nums[c] + nums[d] == target`

The solution set must not contain duplicate quadruplets.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains two integers `n` and `target`.
  * The second line contains `n` space-separated integers, representing `nums`.

**Constraints**
* 1 ≤ t ≤ 50
* 4 ≤ n ≤ 500
* -10^9 ≤ nums[i] ≤ 10^9
* -10^9 ≤ target ≤ 10^9

**Output Format**
For each test case, print each unique quadruplet on a new line with elements in sorted order separated by a space. The quadruplets must be sorted lexicographically. If no quadruplet exists, print `-1`.

**Example**
**Input:**
```text
2
6 0
1 0 -1 0 -2 2
5 9
2 2 2 2 2
```
**Output:**
```text
-2 -1 1 2
-2 0 0 2
-1 0 0 1
-1
```
**Explanation:**
Test case 1: Distinct quadruplets summing to 0 are printed in sorted order.
Test case 2: Any 4 numbers sum to 8, not 9. Output `-1`.
