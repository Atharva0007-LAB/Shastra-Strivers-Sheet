### Problem 133: Floor and Ceil in Sorted Array
**Difficulty:** Easy | **Tags:** Array, Binary Search

**Problem Statement:**
Given a sorted array of integers `arr` of size `n` and an integer `x`, write a program to find the floor and ceil of `x` in the array.
- **Floor** of `x` is the largest element in the array which is smaller than or equal to `x`. If no such element exists, return `-1`.
- **Ceil** of `x` is the smallest element in the array which is greater than or equal to `x`. If no such element exists, return `-1`.

### Input Format
* The first line contains two integers `n` and `x`.
* The second line contains `n` space-separated integers representing the sorted array `arr`.

### Output Format
* Print two space-separated integers representing the floor and ceil values.

### Constraints
* 1 <= n <= 10^5
* 1 <= arr[i], x <= 10^9
* Array `arr` is sorted in non-decreasing order.

### Example
#### Input:
```text
6 5
3 4 7 8 8 10
```

#### Output:
```text
4 7
```

#### Explanation:
Floor of 5 is 4 (largest element <= 5). Ceil of 5 is 7 (smallest element >= 5).
