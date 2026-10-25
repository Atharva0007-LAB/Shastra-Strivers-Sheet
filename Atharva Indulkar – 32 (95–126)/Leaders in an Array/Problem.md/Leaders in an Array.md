### Problem: Leaders in an Array
**Difficulty:** Easy | **Tags:** Arrays, Two Pointers

**Problem Statement**
Given an integer array `nums` of size `n`, find all the leaders in the array.

An element is considered a **leader** if it is greater than or equal to all the elements to its right side. The rightmost element is always a leader.

Return the leaders in the same relative order as they appear in the original array.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the size of `nums`.
  * The second line contains `n` space-separated integers, representing `nums`.

**Constraints**
* 1 ≤ t ≤ 1000
* 1 ≤ n ≤ 10^5
* Sum of n over all test cases ≤ 2 × 10^5
* -10^9 ≤ nums[i] ≤ 10^9

**Output Format**
For each test case, print the leaders separated by a space on a single line in their original left-to-right order.

**Example**
**Input:**
```text
3
6
16 17 4 3 5 2
5
1 2 3 4 0
4
5 4 3 2
```
**Output:**
```text
17 5 2
4 0
5 4 3 2
```
**Explanation:**
Test case 1: 17 is greater than all elements to its right (4, 3, 5, 2). 5 is greater than 2. 2 is the rightmost element.
Test case 2: 4 is greater than 0, and 0 is the rightmost element.
Test case 3: Every element is greater than all elements to its right.
