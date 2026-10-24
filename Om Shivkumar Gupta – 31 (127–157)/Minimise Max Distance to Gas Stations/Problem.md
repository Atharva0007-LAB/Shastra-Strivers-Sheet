### Problem 154: Minimise Max Distance to Gas Stations
**Difficulty:** Hard | **Tags:** Array, Binary Search

**Problem Statement:**
You are given a sorted integer array `stations` of size `n` which represents each gas station on the horizontal line.
You are also given an integer `k`, the number of additional gas stations to add.
You must add `k` gas stations such that the maximum distance between adjacent gas stations is minimized.
Return the minimum possible value of the maximum distance between adjacent gas stations.

Answers within `10^-6` of the actual answer will be accepted.

### Input Format
* The first line contains two integers `n` and `k`.
* The second line contains `n` space-separated integers representing `stations`.

### Output Format
* Print a floating-point number representing the answer rounded to 6 decimal places.

### Constraints
* 2 <= n <= 10^5
* 1 <= k <= 10^6
* 0 <= stations[i] <= 10^9
* `stations` is sorted in strictly increasing order.

### Example
#### Input:
```text
10 1
1 2 3 4 5 6 7 8 9 10
```

#### Output:
```text
1.000000
```

#### Explanation:
Adding 1 station anywhere between any adjacent pair doesn't reduce the existing 1.0 unit maximum gap.
