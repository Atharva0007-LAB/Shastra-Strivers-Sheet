### Problem: Pascal's Triangle III
**Difficulty:** Easy | **Tags:** Arrays, Dynamic Programming

**Problem Statement**
Given an integer `numRows`, return the first `numRows` of **Pascal's triangle**.

In Pascal's triangle, each number is the sum of the two numbers directly above it.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * A single line containing an integer `numRows`.

**Constraints**
* 1 ≤ t ≤ 100
* 1 ≤ numRows ≤ 30

**Output Format**
For each test case, print each row of Pascal's triangle on a new line with elements separated by a space.

**Example**
**Input:**
```text
2
5
1
```
**Output:**
```text
1
1 1
1 2 1
1 3 3 1
1 4 6 4 1
1
```
**Explanation:**
For numRows = 5, the first five rows of Pascal's triangle are printed.
