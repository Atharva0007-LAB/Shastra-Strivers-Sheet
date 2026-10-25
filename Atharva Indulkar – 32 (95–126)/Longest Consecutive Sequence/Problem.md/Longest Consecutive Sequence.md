### Problem: Longest Consecutive Sequence
**Difficulty:** Medium | **Tags:** Arrays, Hash Table, Union Find

**Problem Statement**
Given an unsorted array of integers `nums`, return the length of the longest consecutive elements sequence.

You must write an algorithm that runs in $O(n)$ time.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the size of `nums`.
  * The second line contains `n` space-separated integers, representing `nums` (empty if `n = 0`).

**Constraints**
* 1 ≤ t ≤ 100
* 0 ≤ n ≤ 10^5
* Sum of n over all test cases ≤ 2 × 10^5
* -10^9 ≤ nums[i] ≤ 10^9

**Output Format**
For each test case, print the length of the longest consecutive sequence on its own line.

**Example**
**Input:**
```text
2
6
100 4 200 1 3 2
10
0 3 7 2 5 8 4 6 0 1
```
**Output:**
```text
4
9
```
**Explanation:**
Test case 1: The longest consecutive elements sequence is `[1, 2, 3, 4]`. Its length is 4.
Test case 2: The longest consecutive elements sequence is `[0, 1, 2, 3, 4, 5, 6, 7, 8]`. Its length is 9.
