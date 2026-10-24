### Problem 128: Count Subarrays with Given XOR K
**Difficulty:** Medium | **Tags:** Array, Hash Table, Bit Manipulation

**Problem Statement:**
Given an array of integers `A` and an integer `B`, find the total number of continuous subarrays having bitwise XOR equal to `B`.

### Input Format
* The first line contains two space-separated integers `n` and `b`, representing the number of elements and target XOR value.
* The second line contains `n` space-separated integers representing array `A`.

### Output Format
* Print a single integer representing the count of subarrays with XOR equal to `b`.

### Constraints
* 1 <= n <= 10^5
* 0 <= A[i] <= 10^9
* 0 <= b <= 10^9

### Example
#### Input:
```text
5 6
4 2 2 6 4
```

#### Output:
```text
4
```

#### Explanation:
The subarrays having XOR 6 are:
- [4, 2] (index 0 to 1) -> 4 ^ 2 = 6
- [4, 2, 2, 6, 4] (index 0 to 4) -> 4 ^ 2 ^ 2 ^ 6 ^ 4 = 6
- [2, 2, 6] (index 1 to 3) -> 2 ^ 2 ^ 6 = 6
- [6] (index 3 to 3) -> 6
Total count = 4.
