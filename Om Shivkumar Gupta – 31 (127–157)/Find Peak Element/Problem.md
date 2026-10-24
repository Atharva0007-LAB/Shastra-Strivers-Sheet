### Problem 151: Find Peak Element
**Difficulty:** Medium | **Tags:** Array, Binary Search

**Problem Statement:**
A peak element is an element that is strictly greater than its neighbors.
Given a 0-indexed integer array `nums`, find a peak element, and return its index. If the array contains multiple peaks, return the index to any of the peaks.

You may imagine that `nums[-1] = nums[n] = -∞`. In other words, an element is always considered to be strictly greater than a neighbor that is outside the array.

You must write an algorithm that runs in `O(log n)` time.

### Input Format
* The first line contains an integer `n`.
* The second line contains `n` space-separated integers representing `nums`.

### Output Format
* Print a single integer representing the 0-based index of any peak element.

### Constraints
* 1 <= n <= 10^5
* -10^9 <= nums[i] <= 10^9
* `nums[i] != nums[i + 1]` for all valid `i`.

### Example
#### Input:
```text
4
1 2 3 1
```

#### Output:
```text
2
```

#### Explanation:
3 is a peak element and your function should return the index number 2.
