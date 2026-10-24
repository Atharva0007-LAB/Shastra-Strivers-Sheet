# Editorial: Set Matrix Zeroes

## Difficulty

Medium

## Approach: In-Place Marking

### Step 1: Identify the problem

Given an `m x n` matrix, whenever an original element is zero, its entire row and column must become zero.

Changing elements immediately while scanning is incorrect because newly created zeroes could cause additional rows and columns to be cleared.

### Step 2: Track the first row and column

Use two boolean variables:

- `firstRowZero`: whether the original first row contains a zero.
- `firstColZero`: whether the original first column contains a zero.

These flags are necessary because the first cell belongs to both the first row and first column.

### Step 3: Mark rows and columns

Traverse the matrix, excluding the first row and first column.

Whenever `matrix[i][j] == 0`, mark its row and column:

```cpp
matrix[i][0] = 0;
matrix[0][j] = 0;
```

The first cell of each row and column now records whether that row or column needs to be cleared.

### Step 4: Update the inner matrix

Traverse the inner matrix again.

If either marker is zero, set the current element to zero:

```cpp
if (matrix[i][0] == 0 || matrix[0][j] == 0) {
    matrix[i][j] = 0;
}
```

### Step 5: Update the first row and column

Finally:

- If `firstRowZero` is true, set every element in the first row to zero.
- If `firstColZero` is true, set every element in the first column to zero.

These operations must happen after using the markers.

## Example

**Input**
```text
3 3
1 1 1
1 0 1
1 1 1
```

The original zero is located at row `1`, column `1`.

The algorithm marks the corresponding row and column and then updates the matrix.

**Output**
```text
1 0 1
0 0 0
1 0 1
```

## C++ Solution
```cpp
class Solution {
public:
    void setZeroes(vector<vector<int>>& matrix) {
        int m = matrix.size();
        int n = matrix[0].size();

        bool firstRowZero = false;
        bool firstColZero = false;

        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                firstRowZero = true;
                break;
            }
        }

        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstColZero = true;
                break;
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 ||
                    matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        if (firstRowZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }

        if (firstColZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }
};
```

## Complexity Analysis

Let `m` be the number of rows and `n` be the number of columns.

**Time Complexity: `O(m × n)`**

The algorithm performs a constant number of traversals of the matrix.

**Auxiliary Space Complexity: `O(1)`**

Only two boolean variables and a few integer variables are used beyond the input matrix. The matrix is modified in place.

## Conclusion

Using the first row and first column as markers avoids allocating separate row and column arrays. Two boolean flags preserve the original state of the first row and column, achieving linear time in the matrix size and constant auxiliary space.
