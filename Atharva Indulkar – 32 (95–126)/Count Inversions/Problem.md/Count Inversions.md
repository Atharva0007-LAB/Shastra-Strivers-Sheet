### Problem: Count Inversions
**Difficulty:** Medium | **Tags:** Arrays, Divide and Conquer, Sorting

**Problem Statement**
Given an array of integers `arr`, find the **Inversion Count** in the array.

Two elements `arr[i]` and `arr[j]` form an inversion if `arr[i] > arr[j]` and `i < j`.

Inversion Count indicates how far (or close) the array is from being sorted. If the array is already sorted, then the inversion count is 0. If an array is sorted in the reverse order, the inversion count is maximum.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the size of `arr`.
  * The second line contains `n` space-separated integers, representing `arr`.

**Constraints**
* 1 ≤ t ≤ 50
* 1 ≤ n ≤ 10^5
* Sum of n over all test cases ≤ 2 × 10^5
* 1 ≤ arr[i] ≤ 10^9

**Output Format**
For each test case, print the inversion count as a single integer on its own line.

**Example**
**Input:**
```text
3
5
2 4 1 3 5
5
2 3 4 5 6
3
10 10 10
```
**Output:**
```text
3
0
0
```
**Explanation:**
Test case 1: Inversions are (2, 1), (4, 1), and (4, 3), so count is 3.
Test case 2: The array is sorted, so inversion count is 0.
Test case 3: All elements are equal, so inversion count is 0.
