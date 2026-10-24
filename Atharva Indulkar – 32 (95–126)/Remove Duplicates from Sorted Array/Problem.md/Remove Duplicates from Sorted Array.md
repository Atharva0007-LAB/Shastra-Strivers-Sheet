### Problem: Remove Duplicates from Sorted Array
**Difficulty:** Easy | **Tags:** Arrays, Two Pointers

**Problem Statement**
Given an integer array nums sorted in non-decreasing order, remove the duplicates so that each unique element appears only once, keeping the relative order of the elements. Print the number of unique elements k, followed by the first k elements of the resulting array.

**Input Format**
* The first line contains an integer t, the number of test cases.
* For each test case:
  * The first line contains an integer n, the size of the array.
  * The second line contains n integers, the elements of nums in non-decreasing order.

**Constraints**
* 1 ≤ t ≤ 1000
* 1 ≤ n ≤ 3 × 10^4
* Sum of n over all test cases ≤ 2 × 10^5
* -100 ≤ nums[i] ≤ 100
* nums is sorted in non-decreasing order

**Output Format**
For each test case, print two lines:
* The first line contains the integer k, the number of unique elements.
* The second line contains the k unique elements as space-separated integers.

**Example**
**Input:**
```text
2
3
1 1 2
10
0 0 1 1 1 2 2 3 3 4
```
**Output:**
```text
2
1 2
5
0 1 2 3 4
```
**Explanation:**
Test case 1: the unique elements are 1 and 2, so k = 2 and the result is 1 2.

Test case 2: the unique elements are 0, 1, 2, 3 and 4, so k = 5 and the result is 0 1 2 3 4.
