### Problem 137: Find Minimum in Rotated Sorted Array
**Difficulty:** Medium | **Tags:** Array, Binary Search

**Problem Statement:**
Suppose an array of length `n` sorted in ascending order is rotated between 1 and `n` times.
Given the sorted rotated array `nums` of unique elements, return the minimum element of this array.

You must write an algorithm that runs in `O(log n)` time.

### Input Format
* The first line contains an integer `n`.
* The second line contains `n` space-separated integers representing `nums`.

### Output Format
* Print a single integer representing the minimum element in the array.

### Constraints
* 1 <= n <= 10^5
* -10^9 <= nums[i] <= 10^9
* All integers in `nums` are unique.

### Example
#### Input:
```text
5
3 4 5 1 2
```

#### Output:
```text
1
```

#### Explanation:
The original array was [1, 2, 3, 4, 5] rotated 3 times, and the minimum element is 1.
