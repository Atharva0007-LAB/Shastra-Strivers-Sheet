### Problem 139: Aggressive Cows
**Difficulty:** Hard | **Tags:** Array, Binary Search

**Problem Statement:**
You are given an array `stalls` of size `N` representing the coordinates of stalls on a straight line. You are also given an integer `K`, which represents the number of aggressive cows.

Assign cows to the stalls such that the minimum distance between any two of them is as large as possible. Return the maximum possible minimum distance.

### Input Format
* The first line contains two integers `N` and `K`, representing the number of stalls and the number of cows.
* The second line contains `N` space-separated integers representing the stall coordinates.

### Output Format
* Print a single integer representing the maximum possible minimum distance.

### Constraints
* 2 <= N <= 10^5
* 2 <= K <= N
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

#### Explanation
* Sort the stalls: [0, 3, 4, 7, 9, 10]
* Place 4 cows at coordinates 0, 3, 7, 10:
  - Distances: (3-0)=3, (7-3)=4, (10-7)=3
* Minimum distance between any two cows is 3, which is the maximum possible.
