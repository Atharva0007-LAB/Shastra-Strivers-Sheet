### Problem 127: Count Subarrays with Given Sum
**Difficulty:** Medium | **Tags:** Array, Hash Table, Prefix Sum

**Problem Statement:**
Given an array of integers `nums` and an integer `k`, return the total number of continuous subarrays whose sum equals to `k`.

### Input Format
* The first line contains two space-separated integers `n` and `k`, representing the number of elements in the array and the target sum.
* The second line contains `n` space-separated integers representing the elements of `nums`.

### Output Format
* Print a single integer representing the total number of continuous subarrays with sum equal to `k`.

### Constraints
* 1 <= n <= 10^5
* -10^4 <= nums[i] <= 10^4
* -10^9 <= k <= 10^9

### Example
#### Input:
```text
3 2
1 1 1
```

#### Output:
```text
2
```

#### Explanation:
The subarrays [nums[0...1]] (i.e. [1, 1]) and [nums[1...2]] (i.e. [1, 1]) both have a sum of 2.
