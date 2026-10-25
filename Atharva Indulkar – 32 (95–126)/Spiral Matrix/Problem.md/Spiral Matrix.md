### Problem: Spiral Matrix
**Difficulty:** Medium | **Tags:** Arrays, Matrix, Simulation

**Problem Statement**
Given an `m x n` matrix, return all elements of the matrix in spiral order.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains two integers `m` and `n`, the dimensions of the matrix.
  * The next `m` lines each contain `n` space-separated integers, representing the rows of `matrix`.

**Constraints**
* 1 ≤ t ≤ 100
* 1 ≤ m, n ≤ 100
* -100 ≤ matrix[i][j] ≤ 100

**Output Format**
For each test case, print all elements in spiral order as space-separated integers on a single line.

**Example**
**Input:**
```text
2
3 3
1 2 3
4 5 6
7 8 9
3 4
1 2 3 4
5 6 7 8
9 10 11 12
```
**Output:**
```text
1 2 3 6 9 8 7 4 5
1 2 3 4 8 12 11 10 9 5 6 7
```
**Explanation:**
Test case 1: Traversal visits top row (1, 2, 3), right column (6, 9), bottom row (8, 7), left column (4), and center (5).
