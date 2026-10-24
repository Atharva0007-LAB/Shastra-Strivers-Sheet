### Problem 146: Capacity to Ship Packages Within D Days
**Difficulty:** Medium | **Tags:** Array, Binary Search

**Problem Statement:**
A conveyor belt has packages that must be shipped from one port to another within `days` days.
The `i`-th package on the conveyor belt has a weight of `weights[i]`. Each day, we load the ship with packages on the conveyor belt (in the order given by `weights`). We may not load more weight than the maximum weight capacity of the ship.

Return the least weight capacity of the ship that will result in all the packages on the conveyor belt being shipped within `days` days.

### Input Format
* The first line contains two integers `n` and `days`.
* The second line contains `n` space-separated integers representing `weights`.

### Output Format
* Print a single integer representing the minimum ship capacity.

### Constraints
* 1 <= days <= n <= 10^5
* 1 <= weights[i] <= 500

### Example
#### Input:
```text
10 5
1 2 3 4 5 6 7 8 9 10
```

#### Output:
```text
15
```

#### Explanation:
A ship capacity of 15 is the minimum to ship all packages in 5 days like this:
1st day: 1, 2, 3, 4, 5 (sum 15)
2nd day: 6, 7 (sum 13)
3rd day: 8 (sum 8)
4th day: 9 (sum 9)
5th day: 10 (sum 10)
