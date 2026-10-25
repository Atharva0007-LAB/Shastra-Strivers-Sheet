### Problem: Set Matrix Zeroes
**Difficulty:** Medium | **Tags:** Arrays, Hash Table, Matrix

**Problem Statement**
Given an `m x n` integer matrix, if an element is `0`, set its entire row and column to `0`'s.

You must do it in place with $O(1)$ extra memory space.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains two integers `m` and `n`, the dimensions of the matrix.
  * The next `m` lines each contain `n` space-separated integers, representing the rows of `matrix`.

**Constraints**
* 1 ≤ t ≤ 50
* 1 ≤ m, n ≤ 200
* -2^31 ≤ matrix[i][j] ≤ 2^31 - 1

**Output Format**
For each test case, print the modified matrix row by row, with elements on each row separated by a space.

**Example**
**Input:**
```text
2
3 3
1 1 1
1 0 1
1 1 1
3 4
0 1 2 0
3 4 5 2
1 3 1 5
```
**Output:**
```text
1 0 1
0 0 0
1 0 1
0 0 0 0
0 4 5 0
0 3 1 0
```
**Explanation:**
Test case 1: The element at (1, 1) is 0, so row 1 and column 1 are both set to 0.
Test case 2: The elements at (0, 0) and (0, 3) are 0, so rows and columns containing them are set to 0.
