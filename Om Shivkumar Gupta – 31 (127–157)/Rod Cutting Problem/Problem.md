### Problem 135: Rod Cutting Problem
**Difficulty:** Medium | **Tags:** Dynamic Programming, Unbounded Knapsack

**Problem Statement:**
Given a rod of length `N` inches and an array of prices `price[]` where `price[i]` represents the price of a rod piece of length `i + 1`.

Determine the maximum value obtainable by cutting up the rod and selling the pieces. You can make any number of cuts (including zero cuts).

### Input Format
* The first line contains an integer `N`, representing the length of the rod.
* The second line contains `N` space-separated integers representing the prices of pieces of lengths 1 to `N`.

### Output Format
* Print a single integer representing the maximum revenue obtainable.

### Constraints
* 1 <= N <= 1000
* 1 <= price[i] <= 10^5

### Example
#### Input:
```text
8
1 5 8 9 10 17 17 20
```

#### Output:
```text
22
```

#### Explanation
* Cut the rod of length 8 into two pieces of lengths 2 and 6:
  - Price of length 2 = 5
  - Price of length 6 = 17
* Total revenue = 5 + 17 = 22, which is optimal.
