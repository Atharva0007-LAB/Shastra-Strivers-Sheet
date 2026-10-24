### Problem 155: Split Array - Largest Sum
**Difficulty:** Hard | **Tags:** Array, Binary Search, Dynamic Programming

**Problem Statement:**
Given an integer array `nums` and an integer `k`, split `nums` into `k` non-empty subarrays such that the largest sum of any subarray is minimized.
Return the minimized largest sum of the split.

A subarray is a contiguous part of the array.

### Input Format
* The first line contains two integers `n` and `k`.
* The second line contains `n` space-separated integers representing `nums`.

### Output Format
* Print a single integer representing the minimized largest subarray sum.

### Constraints
* 1 <= k <= min(50, n)
* 1 <= n <= 10^5
* 0 <= nums[i] <= 10^9

### Example
#### Input:
```text
5 2
7 2 5 10 8
```

#### Output:
```text
18
```

#### Explanation:
There are four ways to split nums into two subarrays.
The best way is to split it into [7, 2, 5] and [10, 8], where the largest sum among the two subarrays is only 18.
