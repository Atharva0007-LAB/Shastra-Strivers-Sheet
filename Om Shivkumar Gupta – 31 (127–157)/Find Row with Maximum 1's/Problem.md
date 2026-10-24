### Problem 156: Find Row with Maximum 1's
**Difficulty:** Medium | **Tags:** Array, Binary Search, Matrix

**Problem Statement:**
You are given a 2D binary matrix `mat` of dimensions `n x m` consisting of only 0s and 1s, where each row is sorted in non-decreasing order (all 0s appear before 1s).
Find the 0-based index of the first row that contains the maximum number of 1's. If no row has any 1's, return `-1`.

### Input Format
* The first line contains two integers `n` and `m`.
* The next `n` lines each contain `m` space-separated integers (0 or 1).

### Output Format
* Print a single integer representing the 0-based index of the row with the maximum number of 1's, or `-1`.

### Constraints
* 1 <= n, m <= 1000
* 0 <= mat[i][j] <= 1
* Each row of `mat` is sorted in non-decreasing order.

### Example
#### Input:
```text
3 3
0 1 1
0 0 1
0 0 0
```

#### Output:
```text
0
```

#### Explanation:
Row 0 has 2 ones, row 1 has 1 one, row 2 has 0 ones. The row with the maximum number of 1s is row 0.
