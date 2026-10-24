### Problem 138: Find Out How Many Times the Array is Rotated
**Difficulty:** Easy | **Tags:** Array, Binary Search

**Problem Statement:**
Given an integer array `arr` of size `n`, sorted in ascending order with distinct values. The array has been right-rotated `k` times.
Find the value of `k`. (Note: The number of rotations is equal to the index of the minimum element in the array).

### Input Format
* The first line contains an integer `n`.
* The second line contains `n` space-separated integers representing `arr`.

### Output Format
* Print a single integer representing the rotation count `k`.

### Constraints
* 1 <= n <= 10^5
* -10^9 <= arr[i] <= 10^9
* All integers in `arr` are distinct.

### Example
#### Input:
```text
5
5 1 2 3 4
```

#### Output:
```text
1
```

#### Explanation:
The original sorted array was [1, 2, 3, 4, 5]. It was right-rotated 1 time to become [5, 1, 2, 3, 4].
