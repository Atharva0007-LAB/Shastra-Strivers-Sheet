### Problem 135: Search in Rotated Sorted Array I
**Difficulty:** Medium | **Tags:** Array, Binary Search

**Problem Statement:**
There is an integer array `nums` sorted in ascending order with distinct values.
Prior to being passed to your function, `nums` is possibly rotated at an unknown pivot index `k` (`1 <= k < nums.length`).
Given the array `nums` after the possible rotation and an integer `target`, return the index of `target` if it is in `nums`, or `-1` if it is not in `nums`.

You must write an algorithm with `O(log n)` runtime complexity.

### Input Format
* The first line contains two integers `n` and `target`.
* The second line contains `n` space-separated integers representing `nums`.

### Output Format
* Print a single integer representing the 0-based index of `target`, or `-1`.

### Constraints
* 1 <= n <= 10^5
* -10^4 <= nums[i], target <= 10^4
* All values of `nums` are distinct.

### Example
#### Input:
```text
7 0
4 5 6 7 0 1 2
```

#### Output:
```text
4
```

#### Explanation:
Target 0 is present at index 4.
