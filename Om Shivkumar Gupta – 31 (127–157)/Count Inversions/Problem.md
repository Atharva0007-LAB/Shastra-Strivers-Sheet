### Problem 131: Count Inversions
**Difficulty:** Medium | **Tags:** Array, Divide and Conquer, Merge Sort

**Problem Statement:**
Given an array of integers `nums`, find the inversion count in the array.

Two elements `nums[i]` and `nums[j]` form an inversion if:
- `nums[i] > nums[j]` and
- `i < j`

Inversion count indicates how far (or close) the array is from being sorted. A sorted array has an inversion count of 0, whereas an array sorted in reverse order has the maximum inversion count.

### Input Format
* The first line contains an integer `N`, representing the size of the array.
* The second line contains `N` space-separated integers representing the array elements.

### Output Format
* Print a single integer representing the total inversion count.

### Constraints
* 1 <= N <= 10^5
* -10^9 <= nums[i] <= 10^9

### Example
#### Input:
```text
5
5 3 2 4 1
```

#### Output:
```text
8
```

#### Explanation
* The inversions are:
  (5, 3), (5, 2), (5, 4), (5, 1),
  (3, 2), (3, 1),
  (2, 1),
  (4, 1)
* Total inversion count = 8.
