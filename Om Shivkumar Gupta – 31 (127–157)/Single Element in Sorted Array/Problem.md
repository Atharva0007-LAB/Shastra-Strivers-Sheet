### Problem 139: Single Element in Sorted Array
**Difficulty:** Medium | **Tags:** Array, Binary Search

**Problem Statement:**
You are given a sorted array consisting of only integers where every element appears exactly twice, except for one element which appears exactly once.
Return the single element that appears only once.

Your solution must run in `O(log n)` time and `O(1)` space.

### Input Format
* The first line contains an integer `n`.
* The second line contains `n` space-separated integers representing the sorted array.

### Output Format
* Print a single integer representing the element that appears exactly once.

### Constraints
* 1 <= n <= 10^5
* n is odd.
* 0 <= nums[i] <= 10^9

### Example
#### Input:
```text
9
1 1 2 3 3 4 4 8 8
```

#### Output:
```text
2
```

#### Explanation:
2 appears only once, while all other numbers appear twice.
