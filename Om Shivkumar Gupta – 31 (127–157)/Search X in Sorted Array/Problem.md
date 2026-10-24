### Problem 129: Search X in Sorted Array
**Difficulty:** Easy | **Tags:** Array, Binary Search

**Problem Statement:**
Given a sorted array of integers `nums` of size `n` in non-decreasing order and an integer `target`, search `target` in `nums`. If `target` exists, return its 0-based index. Otherwise, return `-1`.

### Input Format
* The first line contains two integers `n` and `target`.
* The second line contains `n` space-separated integers representing `nums`.

### Output Format
* Print a single integer representing the index of `target` if present, otherwise `-1`.

### Constraints
* 1 <= n <= 10^5
* -10^9 <= nums[i], target <= 10^9
* Array is sorted in non-decreasing order.

### Example
#### Input:
```text
6 9
-1 0 3 5 9 12
```

#### Output:
```text
4
```

#### Explanation:
9 exists in `nums` at index 4.
