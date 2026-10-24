### Problem 152: Reverse Pairs
**Difficulty:** Hard | **Tags:** Array, Divide and Conquer, Merge Sort

**Problem Statement:**
Given an integer array `nums`, return the number of reverse pairs in the array.

A reverse pair is defined as a pair `(i, j)` such that:
- `0 <= i < j < nums.length` and
- `nums[i] > 2 * nums[j]`

### Input Format
* The first line contains an integer `N`, representing the size of the array.
* The second line contains `N` space-separated integers representing the array elements.

### Output Format
* Print a single integer representing the total count of reverse pairs.

### Constraints
* 1 <= N <= 5 * 10^4
* -2^31 <= nums[i] <= 2^31 - 1

### Example
#### Input:
```text
5
1 3 2 3 1
```

#### Output:
```text
2
```

#### Explanation
* The reverse pairs are:
  - (1, 4): nums[1] = 3, nums[4] = 1, and 3 > 2 * 1
  - (3, 4): nums[3] = 3, nums[4] = 1, and 3 > 2 * 1
* Total reverse pairs = 2.
