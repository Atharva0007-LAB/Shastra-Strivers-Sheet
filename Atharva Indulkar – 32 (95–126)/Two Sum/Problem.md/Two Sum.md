### Problem: Two Sum
**Difficulty:** Easy | **Tags:** Arrays, Hash Table

**Problem Statement**
Given an array of integers `nums` and an integer `target`, return the 0-based indices of the two numbers such that they add up to `target`.

You may assume that each input would have exactly one solution, and you may not use the same element twice. If no solution exists, print `-1`.

Print the two indices in ascending order separated by a space (`i j` where `i < j`).

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains two integers `n` and `target`.
  * The second line contains `n` space-separated integers, representing `nums`.

**Constraints**
* 1 ≤ t ≤ 1000
* 2 ≤ n ≤ 5 × 10^4
* Sum of n over all test cases ≤ 2 × 10^5
* -10^9 ≤ nums[i] ≤ 10^9
* -2 × 10^9 ≤ target ≤ 2 × 10^9

**Output Format**
For each test case, print the two indices in ascending order separated by a space on a single line. If no such pair exists, print `-1`.

**Example**
**Input:**
```text
3
4 9
2 7 11 15
3 6
3 2 4
2 6
3 3
```
**Output:**
```text
0 1
1 2
0 1
```
**Explanation:**
Test case 1: nums[0] + nums[1] = 2 + 7 = 9. Output is `0 1`.
Test case 2: nums[1] + nums[2] = 2 + 4 = 6. Output is `1 2`.
Test case 3: nums[0] + nums[1] = 3 + 3 = 6. Output is `0 1`.
