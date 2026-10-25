### Problem: Rotate Image
**Difficulty:** Medium | **Tags:** Arrays, Math, Matrix

**Problem Statement**
You are given an `n x n` 2D matrix representing an image, rotate the image by **90 degrees (clockwise)**.

You have to rotate the image in-place, which means you have to modify the input 2D matrix directly without allocating another 2D matrix.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the dimension of the square matrix.
  * The next `n` lines each contain `n` space-separated integers, representing the rows of `matrix`.

**Constraints**
* 1 ≤ t ≤ 100
* 1 ≤ n ≤ 200
* -1000 ≤ matrix[i][j] ≤ 1000

**Output Format**
For each test case, print the rotated matrix row by row with space-separated integers.

**Example**
**Input:**
```text
2
3
1 2 3
4 5 6
7 8 9
4
5 1 9 11
2 4 8 10
13 3 6 7
15 14 12 16
```
**Output:**
```text
7 4 1
8 5 2
9 6 3
15 13 2 5
14 3 4 1
12 6 8 9
16 7 10 11
```
**Explanation:**
Test case 1: The 3x3 matrix rotated 90 degrees clockwise becomes the output matrix.
Test case 2: The 4x4 matrix rotated 90 degrees clockwise becomes the output matrix.
