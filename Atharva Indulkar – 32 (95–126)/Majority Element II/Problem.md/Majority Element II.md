### Problem: Majority Element II
**Difficulty:** Medium | **Tags:** Arrays, Voting Algorithm

**Problem Statement**
Given an integer array `nums` of size `n`, find all elements that appear more than `⌊n / 3⌋` times.

Return the elements in sorted (ascending) order separated by a space. If no element appears more than `⌊n / 3⌋` times, print `-1`.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the size of the array.
  * The second line contains `n` space-separated integers, the elements of `nums`.

**Constraints**
* 1 ≤ t ≤ 1000
* 1 ≤ n ≤ 5 × 10^4
* Sum of n over all test cases ≤ 2 × 10^5
* -10^9 ≤ nums[i] ≤ 10^9

**Output Format**
For each test case, print the majority elements in increasing order on a single line separated by a space. If no majority element exists, print `-1`.

**Example**
**Input:**
```text
3
3
3 2 3
1
1
2
1 2
```
**Output:**
```text
3
1
1 2
```
**Explanation:**
Test case 1: `3` appears 2 times, which is > ⌊3 / 3⌋ = 1.
Test case 2: `1` appears 1 time, which is > ⌊1 / 3⌋ = 0.
Test case 3: `1` appears 1 time and `2` appears 1 time, both > ⌊2 / 3⌋ = 0.
