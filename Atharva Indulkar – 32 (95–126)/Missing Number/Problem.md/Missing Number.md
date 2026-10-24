### Problem: Missing Number
**Difficulty:** Easy | **Tags:** Arrays, Math

**Problem Statement**
Given an array nums containing n distinct numbers taken from the range [0, n], find and print the only number in the range that is missing from the array.

**Input Format**
* The first line contains an integer t, the number of test cases.
* For each test case:
  * The first line contains an integer n, the size of the array.
  * The second line contains n integers, the elements of nums.

**Constraints**
* 1 ≤ t ≤ 1000
* 1 ≤ n ≤ 10^4
* Sum of n over all test cases ≤ 2 × 10^5
* 0 ≤ nums[i] ≤ n
* All the numbers of nums are unique

**Output Format**
For each test case, print the missing number on its own line.

**Example**
**Input:**
```text
3
3
3 0 1
2
0 1
9
9 6 4 2 3 5 7 0 1
```
**Output:**
```text
2
2
8
```
**Explanation:**
Test case 1: n = 3, so all numbers are in the range [0, 3]. The number 2 does not appear in nums.

Test case 2: n = 2, so all numbers are in the range [0, 2]. The number 2 does not appear in nums.

Test case 3: n = 9, so all numbers are in the range [0, 9]. The number 8 does not appear in nums.
