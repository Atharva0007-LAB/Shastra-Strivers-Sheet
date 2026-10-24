### Problem 145: Minimum Days to Make M Bouquets
**Difficulty:** Medium | **Tags:** Array, Binary Search

**Problem Statement:**
You are given an integer array `bloomDay`, an integer `m` and an integer `k`.
You want to make `m` bouquets. To make a bouquet, you need to use `k` adjacent flowers from the garden.
The garden consists of `n` flowers, the `i`-th flower will bloom in the `bloomDay[i]` and then can be used in exactly one bouquet.

Return the minimum number of days you need to wait to be able to make `m` bouquets from the garden. If it is impossible to make `m` bouquets, return `-1`.

### Input Format
* The first line contains three integers `n`, `m`, and `k`.
* The second line contains `n` space-separated integers representing `bloomDay`.

### Output Format
* Print a single integer representing the minimum number of days, or `-1`.

### Constraints
* 1 <= n <= 10^5
* 1 <= m <= 10^6
* 1 <= k <= n
* 1 <= bloomDay[i] <= 10^9

### Example
#### Input:
```text
5 3 1
1 10 3 10 2
```

#### Output:
```text
3
```

#### Explanation:
On day 3, flowers bloom at days [1, 3, 2] which can form 3 bouquets with k=1 adjacent flowers.
