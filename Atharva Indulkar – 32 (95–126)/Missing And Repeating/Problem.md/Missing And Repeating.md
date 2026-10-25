### Problem: Missing And Repeating
**Difficulty:** Medium | **Tags:** Arrays, Hash Table, Math

**Problem Statement**
Given an unsorted array `arr` of size `n` containing positive integers from `1` to `n`. One number `B` from the set `{1, 2, ..., n}` appears twice and one number `A` is missing.

Find the repeating number `B` and the missing number `A`.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the size of `arr`.
  * The second line contains `n` space-separated integers, representing `arr`.

**Constraints**
* 1 ≤ t ≤ 100
* 2 ≤ n ≤ 10^5
* Sum of n over all test cases ≤ 2 × 10^5
* 1 ≤ arr[i] ≤ n

**Output Format**
For each test case, print two space-separated integers on a single line: the repeating number followed by the missing number (`B A`).

**Example**
**Input:**
```text
2
2
2 2
3
1 3 3
```
**Output:**
```text
2 1
3 2
```
**Explanation:**
Test case 1: 2 occurs twice and 1 is missing. Output is `2 1`.
Test case 2: 3 occurs twice and 2 is missing. Output is `3 2`.
