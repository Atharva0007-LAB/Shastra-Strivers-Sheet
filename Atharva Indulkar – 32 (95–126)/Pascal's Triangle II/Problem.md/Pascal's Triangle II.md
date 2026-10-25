### Problem: Pascal's Triangle II
**Difficulty:** Easy | **Tags:** Arrays, Math, Dynamic Programming

**Problem Statement**
Given an integer `rowIndex`, return the `rowIndex`-th (**0-indexed**) row of Pascal's triangle.

In Pascal's triangle, each number is the sum of the two numbers directly above it.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * A single line containing an integer `rowIndex`.

**Constraints**
* 1 ≤ t ≤ 100
* 0 ≤ rowIndex ≤ 33

**Output Format**
For each test case, print the elements of the `rowIndex`-th row separated by a space on a single line.

**Example**
**Input:**
```text
3
3
0
1
```
**Output:**
```text
1 3 3 1
1
1 1
```
**Explanation:**
Test case 1: Row 3 of Pascal's triangle is `[1, 3, 3, 1]`.
Test case 2: Row 0 is `[1]`.
Test case 3: Row 1 is `[1, 1]`.
