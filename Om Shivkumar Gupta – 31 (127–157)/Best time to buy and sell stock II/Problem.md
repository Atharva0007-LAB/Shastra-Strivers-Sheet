### Problem 151: Best time to buy and sell stock II
**Difficulty:** Medium | **Tags:** Array, Dynamic Programming, Greedy

**Problem Statement:**
You are given an integer array `prices` where `prices[i]` is the price of a given stock on the `i-th` day.

On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time. However, you can buy it then immediately sell it on the same day.

Find and return the maximum profit you can achieve.

### Input Format
* The first line contains an integer `N`, representing the number of days.
* The second line contains `N` space-separated integers representing the daily prices.

### Output Format
* Print a single integer representing the maximum total profit.

### Constraints
* 1 <= N <= 10^5
* 0 <= prices[i] <= 10^4

### Example
#### Input:
```text
6
7 1 5 3 6 4
```

#### Output:
```text
7
```

#### Explanation
* Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5 - 1 = 4.
* Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6 - 3 = 3.
* Total profit is 4 + 3 = 7.
