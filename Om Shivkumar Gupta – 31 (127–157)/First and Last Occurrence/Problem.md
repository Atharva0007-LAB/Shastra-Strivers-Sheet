### Problem 134: First and Last Occurrence
**Difficulty:** Medium | **Tags:** Array, Binary Search

**Problem Statement:**
Given an array of integers `nums` sorted in non-decreasing order, find the starting and ending position of a given `target` value.
If `target` is not found in the array, return `-1 -1`.

You must write an algorithm with `O(log n)` runtime complexity.

### Input Format
* The first line contains two integers `n` and `target`.
* The second line contains `n` space-separated integers representing `nums`.

### Output Format
* Print two space-separated integers representing the first and last occurrence indices.

### Constraints
* 0 <= n <= 10^5
* -10^9 <= nums[i], target <= 10^9
* `nums` is a non-decreasing array.

### Example
#### Input:
```text
6 8
5 7 7 8 8 10
```

#### Output:
```text
3 4
```

#### Explanation:
The target 8 first appears at index 3 and last appears at index 4.
