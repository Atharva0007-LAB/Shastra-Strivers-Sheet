### Problem: Union of Two Sorted Arrays
**Difficulty:** Easy | **Tags:** Arrays, Two Pointers

**Problem Statement**
Given two sorted arrays a[] and b[], where each array may contain duplicate elements, print the elements in the union of the two arrays in sorted order.

The union of two arrays can be defined as that set containing distinct common and non-common elements of both arrays in ascending order.

**Input Format**
* The first line contains an integer t, the number of test cases.
* For each test case:
  * The first line contains two integers n and m, the sizes of a[] and b[].
  * The second line contains n integers, the elements of a[] in non-decreasing order.
  * The third line contains m integers, the elements of b[] in non-decreasing order.

**Constraints**
* 1 ≤ t ≤ 1000
* 1 ≤ n, m ≤ 10^5
* Sum of (n + m) over all test cases ≤ 2 × 10^5
* -10^9 ≤ a[i], b[i] ≤ 10^9
* Both arrays are sorted in non-decreasing order

**Output Format**
For each test case, print the distinct elements in increasing order as space-separated integers on one line.

**Example**
**Input:**
```text
2
5 5
1 2 3 4 5
1 2 3 6 7
2 4
2 2
1 2 3 4
```
**Output:**
```text
1 2 3 4 5 6 7
1 2 3 4
```
**Explanation:**
Test case 1: Distinct elements present in both arrays are 1, 2, 3, 4, 5, 6, 7.

Test case 2: Distinct elements present in both arrays are 1, 2, 3, 4. Even though 2 appears multiple times, it is printed only once.
