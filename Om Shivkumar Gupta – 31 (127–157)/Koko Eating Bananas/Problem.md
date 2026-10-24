### Problem 144: Koko Eating Bananas
**Difficulty:** Medium | **Tags:** Array, Binary Search

**Problem Statement:**
Koko loves to eat bananas. There are `n` piles of bananas, where the `i`-th pile has `piles[i]` bananas.
The guards will come back in `h` hours.
Koko can decide her banana-eating speed of `k` bananas per hour. Each hour, she chooses some pile of bananas and eats `k` bananas from that pile. If the pile has less than `k` bananas, she eats all of them and will not eat any more bananas during that hour.

Return the minimum integer `k` such that she can eat all the bananas within `h` hours.

### Input Format
* The first line contains two integers `n` and `h`.
* The second line contains `n` space-separated integers representing the piles of bananas.

### Output Format
* Print a single integer representing the minimum eating speed `k`.

### Constraints
* 1 <= n <= 10^5
* n <= h <= 10^9
* 1 <= piles[i] <= 10^9

### Example
#### Input:
```text
4 8
3 6 7 11
```

#### Output:
```text
4
```

#### Explanation:
At eating speed 4, Koko can finish:
- 3 bananas in 1 hour
- 6 bananas in 2 hours
- 7 bananas in 2 hours
- 11 bananas in 3 hours
Total hours = 1 + 2 + 2 + 3 = 8 <= 8.
