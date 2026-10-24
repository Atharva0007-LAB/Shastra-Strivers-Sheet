### Problem 157: Search in a 2D Matrix
**Difficulty:** Medium | **Tags:** Array, Binary Search, Matrix

**Problem Statement:**
You are given an `m x n` integer matrix `matrix` with the following two properties:
1. Each row is sorted in non-decreasing order.
2. The first integer of each row is greater than the last integer of the previous row.

Given an integer `target`, return `true` if `target` is in `matrix` or `false` otherwise.

You must write a solution in `O(log(m * n))` time complexity.

### Input Format
* The first line contains three integers `m`, `n`, and `target`.
* The next `m` lines each contain `n` space-separated integers representing the matrix rows.

### Output Format
* Print `true` if `target` is found in `matrix`, otherwise print `false`.

### Constraints
* 1 <= m, n <= 1000
* -10^9 <= matrix[i][j], target <= 10^9

### Example
#### Input:
```text
3 4 3
1 3 5 7
10 11 16 20
23 30 34 60
```

#### Output:
```text
true
```

#### Explanation:
3 is present in the first row.
