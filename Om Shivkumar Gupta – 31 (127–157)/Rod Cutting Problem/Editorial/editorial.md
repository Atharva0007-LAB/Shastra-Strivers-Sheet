# 135. Rod Cutting Problem - Editorial

## Problem Statement
Given a rod of length `N` inches and an array of prices `price[]` where `price[i]` represents the price of a rod piece of length `i + 1`.

Determine the maximum value obtainable by cutting up the rod and selling the pieces. You can make any number of cuts (including zero cuts).

---

## Sample Example

### Sample Input
```text
8
1 5 8 9 10 17 17 20
```

### Sample Output
```text
22
```

### Explanation
* Cut the rod of length 8 into two pieces of lengths 2 and 6:
  - Price of length 2 = 5
  - Price of length 6 = 17
* Total revenue = 5 + 17 = 22, which is optimal.

---

## Intuition
This problem is a variant of the Unbounded Knapsack DP:
Given a capacity `N` (rod length) and available item weights `1, 2, ..., N` with corresponding values `price[0], ..., price[N-1]`, we can pick any piece length as many times as we want to maximize the total value without exceeding total length `N`.

## Approach
1. Initialize an array `dp` of size `N + 1` with 0s, where `dp[len]` denotes max revenue for a rod of length `len`.
2. Base case: `dp[0] = 0`.
3. For each rod length `i` from `1` to `N`:
   - Try every feasible cut length `j` from `1` to `i`:
     `dp[i] = max(dp[i], price[j - 1] + dp[i - j])`
4. Return `dp[N]`.

---

## Complexity Analysis
* **Time Complexity:** O(N^2) because for each length i from 1 to N, we iterate j from 1 to i.
* **Space Complexity:** O(N) to store the 1D DP table.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    /**
     * Finds maximum obtainable revenue by cutting the rod using 1D Dynamic Programming.
     */
    public int cutRod(int[] price, int n) {
        int[] dp = new int[n + 1];

        // dp[i] stores the maximum revenue achievable for rod of length i
        for (int i = 1; i <= n; i++) {
            int maxVal = Integer.MIN_VALUE;
            // Try every possible first cut of length j
            for (int j = 1; j <= i; j++) {
                maxVal = Math.max(maxVal, price[j - 1] + dp[i - j]);
            }
            dp[i] = maxVal;
        }

        return dp[n];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] price = new int[n];
        for (int i = 0; i < n; i++) {
            price[i] = sc.nextInt();
        }

        Solution sol = new Solution();
        int ans = sol.cutRod(price, n);
        System.out.println(ans);
        sc.close();
    }
}
```
