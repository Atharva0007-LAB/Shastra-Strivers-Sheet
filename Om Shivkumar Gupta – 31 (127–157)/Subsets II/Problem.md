### Problem 128: Subsets II
**Difficulty:** Medium | **Tags:** Array, Backtracking, Recursion

**Problem Statement:**
Given an integer array `nums` that may contain duplicate elements, return all possible subsets (the power set).

The solution set must not contain duplicate subsets. Subsets can be generated in any order, but each subset's elements must be sorted in non-decreasing order, and subsets are printed in standard lexicographical order.

### Input Format
* The first line contains an integer `N`, representing the number of elements in the array.
* The second line contains `N` space-separated integers representing the array elements. (If N = 0, this line may be empty).

### Output Format
* Print each unique subset on a separate line in the format `[elem1, elem2, ...]` or `[]` for an empty subset.

### Constraints
* 0 <= N <= 10
* -10 <= nums[i] <= 10

### Example
#### Input:
```text
3
1 2 2
```

#### Output:
```text
[]
[1]
[1, 2]
[1, 2, 2]
[2]
[2, 2]
```

#### Explanation
* The subsets of [1, 2, 2] without duplicates are:
  - Empty set: []
  - 1-element sets: [1], [2]
  - 2-element sets: [1, 2], [2, 2]
  - 3-element set: [1, 2, 2]
