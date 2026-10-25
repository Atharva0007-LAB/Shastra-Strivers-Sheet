### Problem: Next Permutation
**Difficulty:** Medium | **Tags:** Arrays, Two Pointers

**Problem Statement**
A permutation of an array of integers is an arrangement of its members into a sequence or linear order.

The next permutation of an array of integers is the next lexicographically greater permutation of its integers. More formally, if all the permutations of the array are sorted in one container according to their lexicographical order, then the next permutation of that array is the permutation that follows it in the sorted container.

If such an arrangement is not possible, the array must be rearranged as the lowest possible order (i.e., sorted in ascending order).

The replacement must be in place and use only constant extra memory.

**Input Format**
* The first line contains an integer `t`, the number of test cases.
* For each test case:
  * The first line contains an integer `n`, the size of the array.
  * The second line contains `n` space-separated integers, representing the array `nums`.

**Constraints**
* 1 ≤ t ≤ 1000
* 1 ≤ n ≤ 10^5
* Sum of n over all test cases ≤ 2 × 10^5
* 0 ≤ nums[i] ≤ 100

**Output Format**
For each test case, print the next permutation as space-separated integers on a single line.

**Example**
**Input:**
```text
3
3
1 2 3
3
3 2 1
3
1 1 5
```
**Output:**
```text
1 3 2
1 2 3
1 5 1
```
**Explanation:**
Test case 1: The next permutation of [1, 2, 3] is [1, 3, 2].
Test case 2: [3, 2, 1] does not have a lexicographically larger rearrangement, so it is rearranged into [1, 2, 3].
Test case 3: The next permutation of [1, 1, 5] is [1, 5, 1].
