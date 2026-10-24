### Problem 146: Merge Two Sorted Arrays Without Extra Space
**Difficulty:** Hard | **Tags:** Array, Two Pointers, Gap Method

**Problem Statement:**
Given two sorted arrays `arr1` of size `N` and `arr2` of size `M`. Merge the two arrays in-place so that `arr1` contains the smallest `N` elements in sorted non-decreasing order and `arr2` contains the remaining `M` elements in sorted non-decreasing order.

Do not use any extra auxiliary arrays ($mathcal{O}(1)$ auxiliary space).

### Input Format
* The first line contains two integers `N` and `M`.
* The second line contains `N` space-separated integers representing `arr1`.
* The third line contains `M` space-separated integers representing `arr2`.

### Output Format
* The first line should contain `N` space-separated integers of the modified `arr1`.
* The second line should contain `M` space-separated integers of the modified `arr2`.

### Constraints
* 1 <= N, M <= 10^5
* -10^9 <= arr1[i], arr2[j] <= 10^9

### Example
#### Input:
```text
4 5
1 3 5 7
0 2 6 8 9
```

#### Output:
```text
0 1 2 3
5 6 7 8 9
```

#### Explanation
* Merging both arrays in sorted order yields: [0, 1, 2, 3, 5, 6, 7, 8, 9]
* arr1 gets the first 4 elements: [0, 1, 2, 3]
* arr2 gets the remaining 5 elements: [5, 6, 7, 8, 9]
