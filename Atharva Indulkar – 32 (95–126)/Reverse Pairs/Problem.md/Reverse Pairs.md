### Problem: Reverse Pairs
**Difficulty:** Hard | **Tags:** Arrays, Binary Indexed Tree, Segment Tree, Divide and Conquer, Merge Sort

**Problem Statement**
Given an integer array `nums`, return the number of **reverse pairs** in the array.

A reverse pair is a pair `(i, j)` where:
- `0 <= i < j < nums.length`
- `nums[i] > 2 * nums[j]`

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the size of `nums`.
  * The second line contains `n` space-separated integers, representing `nums`.

**Constraints**
* 1 ≤ t ≤ 50
* 1 ≤ n ≤ 5 × 10^4
* Sum of n over all test cases ≤ 10^5
* -2^31 ≤ nums[i] ≤ 2^31 - 1

**Output Format**
For each test case, print the number of reverse pairs on its own line.

**Example**
**Input:**
```text
2
5
1 3 2 3 1
5
2 4 3 5 1
```
**Output:**
```text
2
3
```
**Explanation:**
Test case 1: The reverse pairs are `(1, 4)` because `3 > 2 * 1` and `(3, 4)` because `3 > 2 * 1`.
Test case 2: The reverse pairs are `(1, 4)`, `(2, 4)`, and `(3, 4)`.
