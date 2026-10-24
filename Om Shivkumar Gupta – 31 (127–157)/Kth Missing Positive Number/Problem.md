### Problem 147: Kth Missing Positive Number
**Difficulty:** Easy | **Tags:** Array, Binary Search

**Problem Statement:**
Given an array `arr` of positive integers sorted in a strictly increasing order, and an integer `k`.
Return the `k`-th positive integer that is missing from this array.

### Input Format
* The first line contains two integers `n` and `k`.
* The second line contains `n` space-separated integers representing `arr`.

### Output Format
* Print a single integer representing the `k`-th missing positive integer.

### Constraints
* 1 <= n <= 10^5
* 1 <= arr[i] <= 10^9
* 1 <= k <= 10^9
* `arr` is strictly increasing.

### Example
#### Input:
```text
5 5
2 3 4 7 11
```

#### Output:
```text
9
```

#### Explanation:
The missing positive integers are [1, 5, 6, 8, 9, 10, 12, ...].
The 5th missing positive integer is 9.
