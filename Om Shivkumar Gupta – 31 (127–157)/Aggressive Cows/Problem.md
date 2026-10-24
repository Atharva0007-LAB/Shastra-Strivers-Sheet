### Problem 149: Aggressive Cows
**Difficulty:** Hard | **Tags:** Array, Binary Search

**Problem Statement:**
You are given an array `stalls` of size `n` representing the coordinates of stalls on a straight line.
You are also given an integer `k`, which represents the number of aggressive cows.
Assign cows to the stalls such that the minimum distance between any two of them is as large as possible.
Return the maximum possible minimum distance.

### Input Format
* The first line contains two integers `n` and `k`.
* The second line contains `n` space-separated integers representing stall coordinates.

### Output Format
* Print a single integer representing the maximum possible minimum distance.

### Constraints
* 2 <= n <= 10^5
* 2 <= k <= n
* 0 <= stalls[i] <= 10^9

### Example
#### Input:
```text
6 4
0 3 4 7 10 9
```

#### Output:
```text
3
```

#### Explanation:
Sort stalls: [0, 3, 4, 7, 9, 10]. Place cows at 0, 3, 7, 10. The min distance is min(3, 4, 3) = 3.
