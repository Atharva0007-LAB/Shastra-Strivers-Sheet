# 151. Best time to buy and sell stock II - Editorial

## Problem Statement
You are given an integer array `prices` where `prices[i]` is the price of a given stock on the `i-th` day.

On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time. However, you can buy it then immediately sell it on the same day.

Find and return the maximum profit you can achieve.

---

## Sample Example

### Sample Input
```text
6
7 1 5 3 6 4
```

### Sample Output
```text
7
```

### Explanation
* Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5 - 1 = 4.
* Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6 - 3 = 3.
* Total profit is 4 + 3 = 7.

---

## Intuition
Because we can buy and sell on consecutive days without transaction fees or holding limits, every upward price movement (`prices[i] > prices[i-1]`) represents a profit opportunity that we can capture.
Any multi-day uptrend from day `A` to day `C` can be decomposed into daily gains:
`prices[C] - prices[A] = (prices[C] - prices[B]) + (prices[B] - prices[A])`
Hence, greedily collecting all positive differences yields the exact global maximum profit!

## Approach
1. Initialize `maxProfit = 0`.
2. Iterate from day `i = 1` to `N - 1`:
   - If `prices[i] > prices[i - 1]`:
     `maxProfit += prices[i] - prices[i - 1]`
3. Return `maxProfit`.

---

## Complexity Analysis
* **Time Complexity:** O(N) for a single pass through the array.
* **Space Complexity:** O(1) auxiliary space.

---

## Reference Implementation

### Java Solution
```java
import java.util.Scanner;

public class Solution {
    /**
     * Calculates maximum profit with unlimited transactions using Greedy slope accumulation.
     */
    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxProfit += prices[i] - prices[i - 1];
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        int[] prices = new int[n];
        for (int i = 0; i < n; i++) prices[i] = sc.nextInt();

        Solution sol = new Solution();
        int ans = sol.maxProfit(prices);
        System.out.println(ans);
        sc.close();
    }
}
```
