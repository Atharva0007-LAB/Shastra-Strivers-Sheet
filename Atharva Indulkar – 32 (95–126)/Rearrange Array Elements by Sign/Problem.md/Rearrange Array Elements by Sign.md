### Problem: Rearrange Array Elements by Sign
**Difficulty:** Medium | **Tags:** Arrays, Two Pointers

**Problem Statement**
You are given an integer array nums of even length n, which consists of an equal number of positive and negative integers.

Rearrange the elements of nums so that all of the following hold:
* Every consecutive pair of integers has opposite signs.
* For all integers with the same sign, the order in which they appear in nums is preserved.
* The rearranged array begins with a positive integer.

Print the rearranged array.

**Input Format**
* The first line contains an integer t, the number of test cases.
* For each test case:
  * The first line contains an even integer n, the size of the array.
  * The second line contains n integers, the elements of nums.

**Constraints**
* 1 ≤ t ≤ 1000
* 2 ≤ n ≤ 2 × 10^5, n is even
* Sum of n over all test cases ≤ 2 × 10^5
* 1 ≤ |nums[i]| ≤ 10^5
* nums consists of an equal number of positive and negative integers

**Output Format**
For each test case, print the rearranged array as n space-separated integers on one line.

**Example**
**Input:**
```text
2
6
3 1 -2 -5 2 -4
2
-1 1
```
**Output:**
```text
3 -2 1 -5 2 -4
1 -1
```
**Explanation:**
Test case 1: the positive integers are 3, 1, 2 and the negative integers are -2, -5, -4, each in their original order. Placing them alternately, starting with a positive one, gives 3 -2 1 -5 2 -4.

Test case 2: the only positive integer is 1 and the only negative integer is -1. The array must start with a positive integer, so the answer is 1 -1.
