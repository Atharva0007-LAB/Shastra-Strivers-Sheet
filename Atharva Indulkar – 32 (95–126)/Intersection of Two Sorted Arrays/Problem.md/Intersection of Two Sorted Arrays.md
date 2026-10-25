### Problem: Intersection of Two Sorted Arrays
**Difficulty:** Easy | **Tags:** Arrays, Two Pointers

**Problem Statement**
Given two sorted arrays a[] and b[], where each array may contain duplicate elements, print the elements in the intersection of the two arrays in sorted order.

The intersection of two arrays is the set of distinct common elements that are present in both arrays. If the two arrays have no common element, print -1.

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
* 1 ≤ a[i], b[i] ≤ 10^9
* Both arrays are sorted in non-decreasing order

**Output Format**
For each test case, print the distinct common elements in increasing order as space-separated integers on one line. If there is no common element, print -1.

**Example**
**Input:**
```text
2
6 4
1 1 2 2 2 4
2 2 4 4
4 4
4 4 4 4
3 3 3 3
```
**Output:**
```text
2 4
-1
```
**Explanation:**
Test case 1: the values 2 and 4 appear in both arrays. Each is printed once, even though it appears several times.

Test case 2: a[] only has 4 and b[] only has 3, so there is no common element and the answer is -1.
