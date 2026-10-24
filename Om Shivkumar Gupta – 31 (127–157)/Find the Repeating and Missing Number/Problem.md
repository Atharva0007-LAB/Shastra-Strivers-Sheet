### Problem 138: Find the Repeating and Missing Number
**Difficulty:** Medium | **Tags:** Array, Math, Bit Manipulation

**Problem Statement:**
Given an unsorted array `nums` of size `N` containing numbers from `1` to `N`. One number from the set `{1, 2, ..., N}` is missing and one number occurs twice in the array.

Find the repeating number and the missing number.

Output the two numbers space-separated on a single line: `repeating missing`.

### Input Format
* The first line contains an integer `N`, representing the size of the array.
* The second line contains `N` space-separated integers representing the array elements.

### Output Format
* Print two space-separated integers: `repeating` and `missing`.

### Constraints
* 2 <= N <= 10^5
* 1 <= nums[i] <= N

### Example
#### Input:
```text
5
3 1 2 5 3
```

#### Output:
```text
3 4
```

#### Explanation
* In the array [3, 1, 2, 5, 3]:
  - 3 occurs twice (repeating)
  - 4 is missing from {1, 2, 3, 4, 5}
