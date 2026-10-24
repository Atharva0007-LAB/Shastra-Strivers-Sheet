# Longest Increasing Subsequence

**Difficulty:** Medium  
**Topics:** Arrays, Dynamic Programming, Binary Search

## Problem Statement

Given an integer array `nums`, return the length of the longest strictly increasing subsequence.

A subsequence is obtained by deleting zero or more elements without changing the relative order of the remaining elements. The selected elements do not need to be contiguous.

## Input Format

- The first line contains an integer `n`, the size of the array.
- The second line contains `n` space-separated integers.

## Output Format

Print the length of the longest strictly increasing subsequence.

## Constraints

- `1 <= n <= 10^5`
- `-10^6 <= nums[i] <= 10^6`

## Example 1

**Input**
```text
8
10 9 2 5 3 7 101 18
```

**Output**
```text
4
```

**Explanation:** One longest increasing subsequence is `[2, 3, 7, 101]`, whose length is `4`.

## Example 2

**Input**
```text
6
0 1 0 3 2 3
```

**Output**
```text
4
```

**Explanation:** One longest increasing subsequence is `[0, 1, 2, 3]`.

## Example 3

**Input**
```text
7
7 7 7 7 7 7 7
```

**Output**
```text
1
```

**Explanation:** Equal values cannot extend a strictly increasing subsequence.