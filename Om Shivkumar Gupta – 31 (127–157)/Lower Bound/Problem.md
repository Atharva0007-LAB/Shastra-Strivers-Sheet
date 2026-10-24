### Problem 130: Lower Bound
**Difficulty:** Easy | **Tags:** Array, Binary Search

**Problem Statement:**
Given a sorted array of integers `arr` of size `n` and an integer `x`, write a program to find the lower bound of `x`.
The lower bound of a value `x` is defined as the smallest index `idx` such that `arr[idx] >= x`.
If all elements of the array are smaller than `x`, then the lower bound is `n`.

### Input Format
* The first line contains two integers `n` and `x`.
* The second line contains `n` space-separated integers representing the sorted array `arr`.

### Output Format
* Print a single integer representing the lower bound index (from `0` to `n`).

### Constraints
* 1 <= n <= 10^5
* -10^9 <= arr[i], x <= 10^9
* Array `arr` is sorted in non-decreasing order.

### Example
#### Input:
```text
5 7
3 5 8 15 19
```

#### Output:
```text
2
```

#### Explanation:
The smallest index where `arr[idx] >= 7` is index 2 (value 8).
