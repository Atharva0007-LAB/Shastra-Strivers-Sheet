# Editorial: Pascal's Triangle III


## Difficulty

Easy


## Prerequisites
- Arrays
- Dynamic Programming / Combinatorics


## Problem Statement

Given an integer `numRows`, generate the first `numRows` of Pascal's triangle.


## Explanation & Approach

### Dynamic Programming Formulation

Every element in Pascal's triangle corresponds to combinations:
$$triangle[i][j] = \binom{i}{j}$$
For $j = 0$ or $j = i$, the value is always 1.
For any interior element:
$$triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j]$$

We iteratively build each row $i$ from 0 up to $numRows - 1$ using the previously generated row.


## Java Solution

```java
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            int n = scanner.nextInt();
            int[][] triangle = new int[n][];

            for (int i = 0; i < n; i++) {
                triangle[i] = new int[i + 1];
                triangle[i][0] = 1;
                triangle[i][i] = 1;
                for (int j = 1; j < i; j++) {
                    triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
                }
                for (int j = 0; j <= i; j++) {
                    sb.append(triangle[i][j]);
                    if (j != i) sb.append(" ");
                }
                sb.append("\n");
            }
        }
        System.out.print(sb);
    }
}
```


## Time Complexity

Building row $i$ takes $i + 1$ operations. The total number of elements generated is:
$$\sum_{i=1}^{numRows} i = \frac{numRows(numRows + 1)}{2} = O(numRows^2)$$

### Space Complexity:
$O(1)$ auxiliary space beyond the output triangle.
