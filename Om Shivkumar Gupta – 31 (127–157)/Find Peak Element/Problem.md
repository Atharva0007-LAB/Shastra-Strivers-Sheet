### Problem 129: Find Peak Element
**Difficulty:** Medium | **Tags:** Array, Binary Search

**Problem Statement:**
A peak element is an element that is strictly greater than its neighbors.

Given a 0-indexed integer array `nums`, find a peak element, and return its index. If the array contains multiple peaks, return the index to any of the peaks.

You may imagine that `nums[-1] = nums[n] = -infinity`. In other words, an element is always considered to be strictly greater than a neighbor that is outside the array.

You must write an algorithm that runs in `O(log n)` time.

### Input Format
* The first line contains an integer `N`, representing the size of the array.
* The second line contains `N` space-separated integers representing the array elements.

### Output Format
* Print a single integer representing the 0-based index of any peak element.

### Constraints
* 1 <= N <= 10^5
* -2^31 <= nums[i] <= 2^31 - 1
* nums[i] != nums[i + 1] for all valid i

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

#### Explanation
* 3 is a peak element because it is strictly greater than its neighbors 2 and 1.
* Its index is 2.
