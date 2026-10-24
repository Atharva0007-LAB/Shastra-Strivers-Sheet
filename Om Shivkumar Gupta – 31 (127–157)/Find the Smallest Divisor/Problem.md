### Problem 143: Find the Smallest Divisor
**Difficulty:** Medium | **Tags:** Array, Binary Search

**Problem Statement:**
Given an array of integers `nums` and an integer `threshold`, we will choose a positive integer `divisor`, divide all the array by it, and sum the division's result.
Find the smallest `divisor` such that the result mentioned above is less than or equal to `threshold`.

Each result of division is rounded to the nearest integer greater than or equal to that element (ceiling division: `ceil(num / divisor)`).

### Input Format
* The first line contains two integers `n` and `threshold`.
* The second line contains `n` space-separated integers representing `nums`.

### Output Format
* Print a single integer representing the smallest divisor.

### Constraints
* 1 <= n <= 10^5
* n <= threshold <= 10^6
* 1 <= nums[i] <= 10^6

### Example
#### Input:
```text
4 6
1 2 5 9
```

#### Output:
```text
5
```

#### Explanation:
If divisor is 5:
ceil(1/5) + ceil(2/5) + ceil(5/5) + ceil(9/5) = 1 + 1 + 1 + 2 = 5 <= 6.
If divisor is 4: sum is 1 + 1 + 2 + 3 = 7 > 6.
Hence 5 is the smallest valid divisor.
