### Problem 153: Kth Element of Two Sorted Arrays
**Difficulty:** Medium | **Tags:** Array, Binary Search, Divide and Conquer

**Problem Statement:**
Given two sorted arrays `arr1` and `arr2` of sizes `m` and `n` respectively, and an element `k`.
Find the element that would be at the `k`-th position of the final combined sorted array (1-indexed).

You must write an algorithm with `O(log(min(m, n)))` time complexity.

### Input Format
* The first line contains three integers `m`, `n`, and `k`.
* The second line contains `m` space-separated integers representing `arr1`.
* The third line contains `n` space-separated integers representing `arr2`.

### Output Format
* Print a single integer representing the `k`-th element.

### Constraints
* 1 <= m, n <= 10^5
* 1 <= k <= m + n
* -10^9 <= arr1[i], arr2[i] <= 10^9
* `arr1` and `arr2` are sorted in ascending order.

### Example
#### Input:
```text
5 4 5
2 3 6 7 9
1 4 8 10
```

#### Output:
```text
6
```

#### Explanation:
Merged sorted array is [1, 2, 3, 4, 6, 7, 8, 9, 10]. The 5th element is 6.
