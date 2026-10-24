### Problem 136: Search in Rotated Sorted Array II
**Difficulty:** Medium | **Tags:** Array, Binary Search

**Problem Statement:**
There is an integer array `nums` sorted in non-decreasing order (not necessarily with distinct values).
Before being passed to your function, `nums` is rotated at an unknown pivot index.
Given the array `nums` after the rotation and an integer `target`, return `true` if `target` is in `nums`, or `false` if it is not in `nums`.

### Input Format
* The first line contains two integers `n` and `target`.
* The second line contains `n` space-separated integers representing `nums`.

### Output Format
* Print `true` if `target` exists in `nums`, otherwise print `false`.

### Constraints
* 1 <= n <= 10^5
* -10^4 <= nums[i], target <= 10^4
* Array may contain duplicate elements.

### Example
#### Input:
```text
7 0
2 5 6 0 0 1 2
```

#### Output:
```text
true
```

#### Explanation:
0 is present in the array, so output is `true`.
