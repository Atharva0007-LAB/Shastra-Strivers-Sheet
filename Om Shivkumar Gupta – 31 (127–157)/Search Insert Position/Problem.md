### Problem 132: Search Insert Position
**Difficulty:** Easy | **Tags:** Array, Binary Search

**Problem Statement:**
Given a sorted array of distinct integers `nums` and a target value `target`, return the index if the target is found. If not, return the index where it would be if it were inserted in order.

You must write an algorithm with `O(log n)` runtime complexity.

### Input Format
* The first line contains two integers `n` and `target`.
* The second line contains `n` space-separated distinct integers in ascending order.

### Output Format
* Print a single integer representing the insertion index.

### Constraints
* 1 <= n <= 10^5
* -10^4 <= nums[i], target <= 10^4
* `nums` contains distinct values sorted in ascending order.

### Example
#### Input:
```text
4 5
1 3 5 6
```

#### Output:
```text
2
```

#### Explanation:
5 is found at index 2.
