### Problem 150: Book Allocation Problem
**Difficulty:** Hard | **Tags:** Array, Binary Search

**Problem Statement:**
Given an array `arr` of `n` integers, where `arr[i]` represents the number of pages in the `i`-th book, and an integer `m` representing the number of students.
Allocate books such that:
1. Each student gets at least one book.
2. Each book is allocated to exactly one student.
3. Book allocation is contiguous.
4. The maximum number of pages assigned to a student is minimized.

If allocation is not possible (e.g. `m > n`), return `-1`.

### Input Format
* The first line contains two integers `n` and `m`.
* The second line contains `n` space-separated integers representing `arr`.

### Output Format
* Print a single integer representing the minimized maximum pages, or `-1`.

### Constraints
* 1 <= n <= 10^5
* 1 <= m <= 10^5
* 1 <= arr[i] <= 10^9

### Example
#### Input:
```text
4 2
12 34 67 90
```

#### Output:
```text
113
```

#### Explanation:
Allocation: [12, 34, 67] (sum = 113) to student 1, and [90] (sum = 90) to student 2. The maximum is 113.
