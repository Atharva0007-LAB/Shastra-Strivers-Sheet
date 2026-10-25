### Problem: Majority Element
**Difficulty:** Easy | **Tags:** Arrays, Voting Algorithm

**Problem Statement**
Given an array nums of size n, print the majority element. The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.

**Input Format**
* The first line contains an integer t, the number of test cases.
* For each test case:
  * The first line contains an integer n, the size of the array.
  * The second line contains n integers, the elements of nums.

**Constraints**
* 1 ≤ t ≤ 1000
* 1 ≤ n ≤ 5 × 10^4
* Sum of n over all test cases ≤ 2 × 10^5
* -10^9 ≤ nums[i] ≤ 10^9
* The majority element always exists in the array

**Output Format**
For each test case, print the majority element on its own line.

**Example**
**Input:**
```text
2
3
3 2 3
7
2 2 1 1 1 2 2
```
**Output:**
```text
3
2
```
**Explanation:**
Test case 1: the value 3 appears 2 times, which is more than ⌊3 / 2⌋ = 1.

Test case 2: the value 2 appears 4 times, which is more than ⌊7 / 2⌋ = 3.
