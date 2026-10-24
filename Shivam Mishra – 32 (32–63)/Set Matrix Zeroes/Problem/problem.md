# Problem: Set Matrix Zeroes

**Difficulty:** Medium  
**Tags:** Arrays, Matrix, In-place Algorithm

## Problem Statement

You are given an `m x n` integer matrix. If any element is `0`, set every element in its entire row and column to `0`.

Modify the matrix **in place**.

Only zeroes present in the original matrix should determine which rows and columns must be cleared. Zeroes created during the transformation must not trigger additional changes.

## Input Format

- The first line contains two space-separated integers `m` and `n`, representing the number of rows and columns.
- Each of the next `m` lines contains `n` space-separated integers representing one row of the matrix.

## Output Format

Print the resulting matrix using `m` lines, with `n` space-separated integers on each line.

## Constraints

- `1 <= m, n <= 200`
- Matrix elements are 32-bit signed integers.

## Example 1

**Input**
```text
3 3
1 1 1
1 0 1
1 1 1
```

**Output**
```text
1 0 1
0 0 0
1 0 1
```

**Explanation**

The element at row `1`, column `1` (zero-based indexing) is originally zero. Therefore, the entire second row and second column become zero.

## Example 2

**Input**
```text
3 4
0 1 2 0
3 4 5 2
1 3 1 5
```

**Output**
```text
0 0 0 0
0 4 5 0
0 3 1 0
```

**Explanation**

The original zeroes are in the first row, at columns `0` and `3`. The first row and those two columns are set to zero.
